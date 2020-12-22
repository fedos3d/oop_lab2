import java.util.*;

public class Bank{
    private int IDsForClients = 1;
    private int IDsForTransactions = 1;
    private int IDsForAccounts = 1;

    private String name;
    private double max_sus_transation;
    private double comission;
    private double percent;
    private double[] diversePercnetage;

    private ArrayList<Client> clients = new ArrayList<>();
    private ArrayList<Account> accounts = new ArrayList<>();
    private Map<Integer, Transaction> transactions = new HashMap<>();

    Bank(String name, double max_sus_transation, double comission, double percent, double[] diversePercentage) {
        this.name = name;
        this.max_sus_transation = max_sus_transation;
        this.comission = comission;
        this.percent = percent;
        this.diversePercnetage = diversePercentage;
    }
    public void addClient(Client client) {
        if (!clients.contains(client)) {
            client.setid(IDsForClients++);
            clients.add(client);
        } else {
            throw new ClientAlreadyExistsException();
        }
    }
    public Account createDebitAccount(Client client) {
        if (clients.contains(client)) {
            Account acc = new DebitAccount(IDsForAccounts++, client, max_sus_transation, percent);
            accounts.add(acc);
            return acc;
        } else {
            throw new NoSuchClientInBankException();
        }
    }
    public Account createDepositAccount(Client client, double initSum, Date expireDate) {
        if (clients.contains(client)) {
            Account acc = new DepositAccount(IDsForAccounts++, client, max_sus_transation, diversePercnetage, expireDate, initSum);
            accounts.add(acc);
            return acc;
        } else {
            throw new NoSuchClientInBankException();
        }
    }
    public Account createCreditAccount(Client client, double initSum, double creditLimit) {
        if (clients.contains(client)) {
            Account acc = new CreditAccount(IDsForAccounts++, client, max_sus_transation, comission, creditLimit, initSum);
            accounts.add(acc);
            return acc;
        } else {
            throw new NoSuchClientInBankException();
        }
    }
    Transaction putMoneyOnAcc(Account acc,double amount) {
        if (!accounts.contains(acc)) {
            throw new NoSuchAccountException();
        } else {
            Transaction newtr = acc.putMoney(IDsForTransactions, amount);
            transactions.put(IDsForTransactions++, newtr);
            return newtr;
        }
    }
    Transaction getMoneyFromAcc(Account acc, double amount) {
        if (!accounts.contains(acc)) {
            throw new NoSuchAccountException();
        } else {
            Transaction newrt = acc.getMoney(IDsForTransactions, amount);
            transactions.put(IDsForTransactions++, newrt);
            return newrt;
        }
    }
    Transaction transferMoney(Account from, Account to, double amount) {
        if (!accounts.contains(from) || !accounts.contains(to)) {
            throw new NoSuchAccountException();
        }
        if (from.money >= amount) {
            Transaction newtr = new Transaction(IDsForTransactions, from, to, amount);
            transactions.put(IDsForTransactions++, newtr);
            return newtr;
        } else {
            throw new NotEnoughMoneyToProceedException();
        }
    }
    void cancelTransation(int id) {
        if (!transactions.containsKey(id)) {
            throw new NoSuchTransactionException();
        }
        transactions.get(id).cancelTransaction();
    }
    void endofthedaysetpercentage() {
        for (int i = 0; i < accounts.size(); i++) {
            Account cur = accounts.get(i);
            if (cur instanceof DebitAccount) {
                    DebitAccount db = (DebitAccount) accounts.get(i);
                    db.calcpercents();
            } else if (cur instanceof DepositAccount) {
                    DepositAccount dp = (DepositAccount) accounts.get(i);
                    dp.calcpercents();
                } else if (cur instanceof CreditAccount) {
                CreditAccount cr = (CreditAccount) accounts.get(i);
                cr.calccomission();
            }
            }
    }
    void endofmonthpercentage() {
        for (int i = 0; i < accounts.size(); i++) {
            Account cur = accounts.get(i);
            if (cur instanceof DebitAccount) {
                DebitAccount db = (DebitAccount) accounts.get(i);
                db.addpercentstosum();
            } else if (cur instanceof DepositAccount) {
                DepositAccount dp = (DepositAccount) accounts.get(i);
                dp.addpercentstosum();
            } else if (cur instanceof CreditAccount) {
                CreditAccount cr = (CreditAccount) accounts.get(i);
                cr.addcomission();
            }
        }
        }
    }


class Client {
    private int id;
    private String name;
    private String surName;
    private String address;
    private String passport;

    //public Client(String name, String surName) {
    //    this.name = name;
    //    this.surName = surName;
    //}
    public Client(final String name, final String secondName,
                  final String address, final String passport) {
        this.name = Objects.requireNonNull(name, "Клиент должен иметь имя");
        this.surName = Objects.requireNonNull(secondName, "Клиент должен иметь фамилию");
        this.address = address;
        this.passport = passport;
    }
    public Client setAddress(String adr) {
        this.address = adr;
        return this;
    }
    public Client setPassport(String pas) {
        this.passport = pas;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public String getPassport() {
        return passport;
    }

    public String getSurName() {
        return surName;
    }
    protected void setid(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
class ClientBuilder{
    private String name;
    private String surname;
    private String address;
    private String passport;

    public ClientBuilder SetName(final String name) {
        this.name = name;
        return this;
    }
    public ClientBuilder SetSecondName(final String secondName) {
        this.surname = secondName;
        return this;
    }

    public ClientBuilder SetAddress(final String address) {
        this.address = address;
        return this;
    }

    public ClientBuilder SetPassport(final String passport) {
        this.passport = passport;
        return this;
    }
    public Client Build(){
        return new Client(name, surname, address, passport);
    }

}
abstract class Account {
    protected Client owner;
    protected double money;
    protected int id;
    protected double percentage;
    protected double comission;
    protected double sus_amount;
    protected boolean susStatus;

    protected Account(int id, Client owner, double sus_amount) {
        this.sus_amount = sus_amount;
        this.id = id;
        this.owner = owner;
        susStatus = true;
    }

    protected Account() {
    }

    protected Transaction putMoney(int id, double amount) {
        if (susStatus) {
            checkOwnerSusStatus();
        }
        if (amount > sus_amount) {
            if (susStatus) {
                throw new ExceedSusLimitException();
            }
        }
        return new Transaction(id, null, this, amount);
    }
    protected Transaction getMoney(int id, double amount) {
        if (susStatus) {
            checkOwnerSusStatus();
        }
        if (amount > money) {
            throw new NotEnoughMoneyToProceedException();
        }
        if (amount > sus_amount) {
            if (susStatus) {
                throw new ExceedSusLimitException();
            }
        }
        return new Transaction(id, this, null, amount);
    }
    void addMoney(double amount) {
        this.money += amount;
    }
    void removeMoney(double amount) {
        this.money -= amount;
    }
    protected Transaction transferMoney(int id, Account toacc, double sum) {
        if (susStatus) {
            checkOwnerSusStatus();
        }
        if (sum > money) {
            throw new NotEnoughMoneyToProceedException();
        }
        if (sum > sus_amount) {
            if (susStatus) {
                throw new ExceedSusLimitException();
            }
        }

        return new Transaction(id, this, toacc, sum);
    }
    void checkOwnerSusStatus() {
        if (owner.getPassport() != null && owner.getAddress() != null) {
            susStatus = false;
        } else {
            susStatus = true;
        }
    }
    double getAmountOfMoney() {
        return money;
    }

    public int getId() {
        return id;
    }

    public Client getOwner() {
        return owner;
    }
}
class DebitAccount extends Account {
    private double totaladdpercents;
    protected void calcpercents() {
        totaladdpercents += (money * percentage)/365;
    }
    protected void addpercentstosum() {
        money += totaladdpercents;
    }
    protected DebitAccount(int id, Client owner, double sus_amount, double percentage) {
        this.sus_amount = sus_amount;
        this.id = id;
        this.owner = owner;
        susStatus = true;
        this.percentage = percentage;
    }
}
class DepositAccount extends Account {
    private Date expireDate;
    private double totaladdpercents;
    private double[] diversePercnetage;
    private double initsum;
    protected void calcpercents() {
        if (diversePercnetage != null) {
            if (initsum < 50000) {
                percentage = diversePercnetage[0];
            } else if (initsum <= 100000) {
                percentage = diversePercnetage[1];
            } else if (initsum > 100000) {
                percentage = diversePercnetage[2];
            }
            totaladdpercents += (initsum * percentage) / 365;
        } else {
            throw new DepositDiversePercentageIsNoSetException();
        }
    }
    protected void addpercentstosum() {
        money += totaladdpercents;
        totaladdpercents = 0;
    }
    protected DepositAccount(int id, Client owner, double sus_amount, double[] diversePercnetage, Date expireDate, double initsum) {
        if (diversePercnetage.length != 3) {
            throw new WrongDepositDiversePercentageSetException();
        }
        if (initsum > sus_amount) {
            throw new ExceedSusLimitException();
        }
        this.initsum = initsum;
        this.sus_amount = sus_amount;
        this.id = id;
        this.owner = owner;
        susStatus = true;
        this.diversePercnetage = diversePercnetage;
        this.expireDate = expireDate;
        this.money = initsum;

    }
    protected Transaction getMoney(int id, double amount) {
        if (expireDate.before(new Date())) {
            if (susStatus) {
                checkOwnerSusStatus();
            }
            if (amount > money) {
                throw new NotEnoughMoneyToProceedException();
            }
            if (amount > sus_amount) {
                if (susStatus) {
                    throw new ExceedSusLimitException();
                }
            }
            return new Transaction(id, this, null, amount);
        } else {
            throw new DepositExpireDateHasNotComeException();
        }
    }
    protected void removeMoney(double amount) {
        if (expireDate.before(new Date())) {
            this.money -= amount;
        } else {
            throw new DepositExpireDateHasNotComeException();
        }
    }

    public Date getExpireDate() {
        return expireDate;
    }
}
class CreditAccount extends Account {
    private double maxlimit;
    private double minlimit;
    private double usagecomission;

    protected CreditAccount(int id, Client owner, double sus_amount, double comission, double limit, double initsum) {
        super(id, owner, sus_amount);
        if (initsum > sus_amount) {
            throw new ExceedSusLimitException();
        }
        this.comission = comission;
        this.maxlimit = limit;
        this.minlimit = -limit;
        this.money = initsum;
    }
    protected Transaction putMoney(int id, double amount) {
        if (susStatus) {
            checkOwnerSusStatus();
        }
        if (amount > sus_amount) {
            if (susStatus) {
                throw new ExceedSusLimitException();
            }
        }
        return new Transaction(id, null, this, amount);
    }
    protected Transaction getMoney(int id, double amount) {
        if (susStatus) {
            checkOwnerSusStatus();
        }
        if (money - amount > minlimit) {
            throw new CreditMinAmountExceededLimitException();
        }
        if (amount > sus_amount) {
            if (susStatus) {
                throw new ExceedSusLimitException();
            }
        }
        return new Transaction(id, this, null, amount);
    }
    protected void calccomission() {
        if (money < 0) {
            usagecomission += comission;
        }
    }
    protected void addcomission() {
        money -= usagecomission;
        usagecomission = 0;
    }
    protected void addMoney(double amount) {
        this.money += amount;
    }
    protected void removeMoney(double amount) {
        this.money -= amount;
    }
}

class Transaction {
    private int id;
    private Date date;
    private Account from;
    private Account to;
    private double amount;

    Transaction(int id, Account from, Account to, double amount) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.amount = amount;
        if (from != null) {
            from.removeMoney(amount);
        }
        if (to != null) {
            to.addMoney(amount);
        }
        date = new Date();
    }
    protected void cancelTransaction() {
        if (from != null) {
            from.addMoney(amount);
        }
        if (to != null) {
            to.removeMoney(amount);
        }
    }

    public Account getFrom() {
        return from;
    }

    public Account getTo() {
        return to;
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }
}

class NoSuchTransactionException extends RuntimeException{
    public NoSuchTransactionException() {
        System.out.println("NoSuchTransactionException");
    }
}
class ExceedSusLimitException extends RuntimeException {
    public ExceedSusLimitException() {
        System.out.println("ExceedSusLimitException");
    }
}
class NotEnoughMoneyToProceedException extends RuntimeException {
    public NotEnoughMoneyToProceedException() {
        System.out.println("NotEnoughMoneyToProceedException");
    }
}
class ClientAlreadyExistsException extends RuntimeException {
    public ClientAlreadyExistsException() {
        System.out.println("ClientAlreadyExistsException");
    }
}
class NoSuchClientInBankException extends RuntimeException {
    public NoSuchClientInBankException() {
        System.out.println("NoSuchClientInBankException");
    }
}
class NoSuchAccountException extends RuntimeException {
    public NoSuchAccountException() {
        System.out.println("NoSuchAccountException");
    }
}
class DepositDiversePercentageIsNoSetException extends RuntimeException {
    public DepositDiversePercentageIsNoSetException() {
        System.out.println("DepositDiversePercentageIsNoSetException");
    }
}
class WrongDepositDiversePercentageSetException extends RuntimeException {
    public WrongDepositDiversePercentageSetException() {
        System.out.println("WrongDepositDiversePercentageSetException");
    }
}
class DepositExpireDateHasNotComeException extends RuntimeException {
    public DepositExpireDateHasNotComeException() {
        System.out.println("DepositExpireDateHasNotComeException");
    }
}
class CreditMinAmountExceededLimitException extends RuntimeException {
    public CreditMinAmountExceededLimitException() {
        System.out.println("CreditMinAmountExceededLimitException");
    }
}