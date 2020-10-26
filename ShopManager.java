import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShopManager {
    private int shopIDCounter = 0;
    private int goodIDCounter = 0;
    private ArrayList<Shop> shops = new ArrayList<>();
    private ArrayList<Good> allGoods = new ArrayList<>();
    ShopManager(){}

    int addShop(String nme, String ad) {
        Shop shop1 = new Shop(this.shopIDCounter, nme, ad);
        int cur = shopIDCounter;
        shops.add(shop1);
        shopIDCounter = shopIDCounter +1;
        return cur;
    }
    int addGood(String nme) {
        Good good1 = new Good(goodIDCounter, nme);
        int cur = goodIDCounter;
        allGoods.add(good1);
        goodIDCounter = goodIDCounter +1;
        return cur;
    }
    void addGoodsToShop(int shopID, int gd, int am, int price) {
        shops.get(shopID).addGoods(allGoods.get(gd), am, price);
    }
    int getShopID(String shopName) {
        for (int i = 0; i < shops.size(); i++) {
            if (shops.get(i).getShopName().equals(shopName)) {
                return shops.get(i).getShopID();
            }
        }
        throw new NoShopException();
    }
    int buyGoodsFromShop(int shopID, int gd, int am) {
        return shops.get(shopID).buyGoods(allGoods.get(gd), am);
    }
    boolean checkItemExistsInShop(int shopID, Good gd) {
        if (shops.get(shopID).checkItemExists(gd)) {
            return true;
        }
        else {
            return false;
        }
    }
    String calculateMinPrice(int gd) { //how to return it in a way so that i pass shopname and minprice? Should i return an array...?
        int minPrice = 0;
        Good good = allGoods.get(gd);
        Shop cheapestShop = shops.get(0);
        for (int i = 0; i < shops.size(); i++) {
            if (i == 0) {
                minPrice = shops.get(i).priceOfItem(good);
                cheapestShop = shops.get(i);
            } else if (shops.get(i).priceOfItem(good) < minPrice) {
                minPrice = shops.get(i).priceOfItem(good);
                cheapestShop = shops.get(i);
            }
        }
        return "The cheapest shop is: " + cheapestShop.getShopName() + " with " + good.getName() + " price of " + minPrice;
    }
    String calculateMinPriceBetweenShops(int[] gd, int[] am) { //Same problem as with previous method
        int curPrice = 0;
        Shop minShop = shops.get(0);
        for (int i = 0; i < shops.size(); i++) {
            Shop curShop = shops.get(i);
            int kek = buyGoodsFromShop(curShop.getShopID(), gd, am);
            if (i == 0) {
                curPrice = kek;
                minShop = curShop;
            }
            else {
                if (kek < curPrice) {
                    curPrice = kek;
                    minShop = curShop;
                }
            }
        }
        return "The total price is: " + curPrice + " in shop with name: " + minShop.getShopName();
    }
    String possiblePurchases(int shopID, int sum) {
        return shops.get(shopID).possible(sum);
    }
    int buyGoodsFromShop(int shopID, int[] gd, int[] am) {
        if (gd.length != am.length) {
            throw new WhrongBuyingRequestException();
        }
        Good[] memi = new Good[gd.length];
        for (int i = 0; i < memi.length; i++) {
            memi[i] = allGoods.get(gd[i]);
        }
        return shops.get(shopID).buyGoods(memi, am);
    }
}
class Shop {
    private int ID;
    private String name;
    private String addr;
    private Map<Good, Integer[]> goods = new HashMap<>();

    Shop(int ID, String nme, String ad) {
        this.ID = ID;
        this.name = nme;
        this.addr = ad;
    }
    public void addGoods(Good gd, int am, int price) {
        if (goods.containsKey(gd)) {
            goods.get(gd)[0] += am;
            goods.get(gd)[1] = price;
        } else {
            Integer[] kek = {am, price};
            goods.put(gd, kek);
        }
    }
    public int buyGoods(Good gd, int am) {
        if (goods.containsKey(gd)) {
            int curAmount = goods.get(gd)[0];
            int curPrice = goods.get(gd)[1];
            if (am <= curAmount) {
                return am * curPrice;
            } else {
                throw new NotEnoughGoodsException();
            }
        } else {
            throw new NoSuchGoodException();
        }
    }
    int buyGoods(Good[] gd, int[] am) {
        int totalPrice = 0;
        for (int i = 0; i < gd.length; i++) {
            Good cur = gd[i];
            int oam = am[i];
            if (goods.containsKey(cur)) {
                int curAmount = goods.get(cur)[0];
                int curPrice = goods.get(cur)[1];
                if (oam <= curAmount) {
                    totalPrice += oam * curPrice;
                } else {
                    throw new NotEnoughGoodsException();
                }
            } else {
                throw new NoOneOfGoodsRequestedException();
            }
        } return totalPrice;
    }
    public boolean checkItemExists(Good gd) {
        if (goods.containsKey(gd)) {
            return true;
        } else {
            return false;
        }
    }
    public int priceOfItem(Good gd) {
        if (goods.containsKey(gd)) {
            return goods.get(gd)[1];
        } else {
            throw new NoSuchGoodException();
        }
    }
    public String getShopName() {
        return this.name;
    }
    public int getShopID() {
        return this.ID;
    }
    String possible(int price) {
        String ans = "You can buy:\n";
        for ( Good key : goods.keySet() ) {
            Integer[] curArray = goods.get(key);
            if ((curArray[0] * curArray[1]) != 0) {
                int curPrice = 0;
                int curAmount = curArray[0];
                int amount = 0;
                while (curPrice <= price && curAmount != 0) {
                    if (curAmount > 0) {
                        curPrice += curArray[1];
                        amount++;
                    }
                    curAmount--;
                }
                if (curPrice > price) {
                    curPrice = curPrice - curArray[1];
                    amount--;
                }
                if (amount == 0) {
                    continue;
                }
                ans += amount + " of " + key.getName() + " for: " + curPrice + " or" + "\n";
            }
        }
        if (ans == "") {
            return "You can buy nothing";
        }
        if (ans.endsWith("or\n")) {
            ans = ans.substring(0, ans.length() - 4);
        }
        return ans;
    }
}

class Good {
    private int ID;
    private String name;
    Good(int ID, String nme) {
        this.ID = ID;
        this.name = nme;
    }
    String getName() {
        return this.name;
    }
    int getID() {
        return this.ID;
    }
}

class NoSuchGoodException extends RuntimeException {
    public NoSuchGoodException() {
        System.out.println("No such item exception");
    }
}
class NotEnoughGoodsException extends RuntimeException {
    public NotEnoughGoodsException() {
        System.out.println("This shop does not have one of the items you requested");
    }
}
class WhrongBuyingRequestException extends RuntimeException {
    public WhrongBuyingRequestException() {
        System.out.println("Buying request you entered is wriong(amount of prices and amount of goods is not equal");
    }
}
class NoOneOfGoodsRequestedException extends RuntimeException {
    public NoOneOfGoodsRequestedException() {
        System.out.println("Not enough goods if shop exception");
    }
}
class NoShopException extends RuntimeException {
    public NoShopException() {
        System.out.println("No shop exception");
    }
}