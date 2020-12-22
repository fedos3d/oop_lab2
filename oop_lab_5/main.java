import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class main {
    public static void main(String[] args) throws ParseException {
        //create bank
        Bank sberbank = new Bank("Sber", 50000, 200, 0.03, new double[]{0.03, 0.035, 0.04});

        //create client 1
        //for now he is sus

        Client client1 = new ClientBuilder().SetName("Oleg").SetSecondName("Strelkov").Build();
        sberbank.addClient(client1);

        //create and check deposit account
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = format.parse("2021/11/11 00:00:00");
        Account dp1 = sberbank.createDepositAccount(client1, 50000, date);

        for (int j = 0; j < 12; j++) {
            for (int i = 0; i < 31; i++) {
                sberbank.endofthedaysetpercentage();
            }
            sberbank.endofmonthpercentage();
        }
        System.out.println(dp1.getAmountOfMoney());

        //create credit account
        Account cr1 = sberbank.createCreditAccount(client1, 20000, 20000);
        sberbank.getMoneyFromAcc(cr1, 40000);
        System.out.println(cr1.getAmountOfMoney());
        for (int j = 0; j < 12; j++) {
            for (int i = 0; i < 31; i++) {
                sberbank.endofthedaysetpercentage();
            }
            sberbank.endofmonthpercentage();
        }
        System.out.println(cr1.getAmountOfMoney());

        //create and check debit account
        Account db1 = sberbank.createDebitAccount(client1);
        sberbank.putMoneyOnAcc(db1, 10000);
        sberbank.getMoneyFromAcc(db1, 5000);
        System.out.println(db1.getAmountOfMoney());
        for (int j = 0; j < 12; j++) {
            for (int i = 0; i < 31; i++) {
                sberbank.endofthedaysetpercentage();
            }
            sberbank.endofmonthpercentage();
        }
        System.out.println(db1.getAmountOfMoney());

        //check cancel transaction
        Transaction tr2 = sberbank.transferMoney(db1, cr1, 1000);
        sberbank.cancelTransation(tr2.getId());
    }
}
