public class main {
    public static void main(String args[]) {
        //init of shopmgm
        ShopManager shopmgm = new ShopManager();

        //init of goods in shopmanager and assigning its ids
        int good1 = shopmgm.addGood("Coffe Package");
        int good2 = shopmgm.addGood("Tea Package");
        int good3 = shopmgm.addGood("kilo of cucumbers");
        int good4 = shopmgm.addGood("Coke(0,5)");
        int good5 = shopmgm.addGood("kilo of bread");
        int good6 = shopmgm.addGood("Cigarrets");
        int good7 = shopmgm.addGood("kilo of Dumplings");
        int good8 = shopmgm.addGood("Bottle of vine");
        int good9 = shopmgm.addGood("Sour Cream(200g)");
        int good10 = shopmgm.addGood("bottle of beer(500ml)");

        //init shops in shop manager and assigning its ids
        int plovid = shopmgm.addShop("Plovdiv", "Petergosfloe shosse 24");
        int pyatid = shopmgm.addShop("Pyatorochka", "Memnaya ulitsa 11");
        int perid = shopmgm.addShop("Perekrestok", "Kronveskiy prospekt 47");

        //adding goods to Plovdiv
        shopmgm.addGoodsToShop(plovid, good1, 10, 100);
        shopmgm.addGoodsToShop(plovid, good4, 20, 40);
        shopmgm.addGoodsToShop(plovid, good7, 5, 150);
        shopmgm.addGoodsToShop(plovid, good10, 50, 60);
        shopmgm.addGoodsToShop(plovid, good2, 3, 40);

        //adding goods to Pyatorochka
        shopmgm.addGoodsToShop(pyatid, good1, 10, 80);
        shopmgm.addGoodsToShop(pyatid, good2, 10, 80);
        shopmgm.addGoodsToShop(pyatid, good5, 10, 20);
        shopmgm.addGoodsToShop(pyatid, good8, 20, 200);

        //adding goods to Perekrestok
        shopmgm.addGoodsToShop(perid, good1, 10, 35);
        shopmgm.addGoodsToShop(perid, good3, 10, 35);
        shopmgm.addGoodsToShop(perid, good6, 20, 150);
        shopmgm.addGoodsToShop(perid, good9, 10, 40);
        shopmgm.addGoodsToShop(perid, good2, 9, 40);

        //testing our shops

        //testing min price of good1
        System.out.println("Min price of good1: " + shopmgm.calculateMinPrice(good1));
        System.out.println();

        //testing buying good3 from store
        System.out.println("Buying goods: " + shopmgm.buyGoodsFromShop(perid, good3, 10));
        System.out.println();

        //testing buying more than one good
        int[] smeta_good = {good2, good5, good8};
        int[] smeta_am = {3, 4, 5};
        System.out.println("Buying more than one good: " + shopmgm.buyGoodsFromShop(pyatid, smeta_good, smeta_am));
        System.out.println();

        //testing possiblepurchases
        System.out.println(shopmgm.possiblePurchases(perid, 100));
        System.out.println();

        //testing min price when buying more than one good
        int[] smeta2_good = {good1, good2};
        int[] smeta2_am = {2, 2};
        System.out.println(shopmgm.calculateMinPriceBetweenShops(smeta2_good, smeta2_am));
    }
}