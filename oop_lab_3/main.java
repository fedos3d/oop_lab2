import java.util.HashMap;
import java.util.Map;

public class main {
    public static void main(String args[]) {
        //here we create landcrafts
        Vehicle twoGorbCamel = new LandCraft("двугорбый верблюд", 10, 30, new double[] {5, 8});
        Vehicle fastCamel = new LandCraft("верблюд-быстроход", 40, 10, new double[] {5, 6.5, 8});
        Vehicle centavr = new LandCraft("ĸентавр", 15, 18, 2);
        Vehicle everywhereBoots = new LandCraft("ботинĸи-вездеходы", 6, 60 , new double[] {10, 5});

        //here we create our raceengine and race
        //RaceEngine raceEngine = new RaceEngine();
        Race race1 = new Race(Types.LAND, 1000);

        //here we register our landrafts vehicle
        race1.addVehicle(twoGorbCamel);
        race1.addVehicle(fastCamel);
        race1.addVehicle(centavr);
        race1.addVehicle(everywhereBoots);

        //here we start our race and print the winner
        System.out.println("Race1: " + race1.startRace());

        //here we create aircrafts
        //here we create carpet
        Map<String, Integer> carpetDistance = new HashMap<>();
        carpetDistance.put("1000", 0);
        carpetDistance.put("5000", 3);
        carpetDistance.put("10000", 10);
        carpetDistance.put("more", 5);
        Vehicle carpetPlane = new AirCraft("ĸовер-самолет", 10, (HashMap) carpetDistance);

        //here we create stupa
        Map<String, Integer> stupaDistance = new HashMap<>();
        stupaDistance.put("Always", 6);
        Vehicle stupa = new AirCraft("ступа", 8, (HashMap) stupaDistance);

        //here we create metla
        Map<String, Integer> metlaDistance = new HashMap<>();
        metlaDistance.put("Evenly", 1);
        Vehicle metla = new AirCraft("метла", 20, (HashMap) metlaDistance);

        //Here we create out only air race
        Race race2 = new Race(Types.AIR, 1000);
        race2.addVehicle(carpetPlane);
        race2.addVehicle(stupa);
        race2.addVehicle(metla);
        System.out.println("Race2: " + race2.startRace());

        //Here we create race for every vehicle
        Race race3 = new Race(Types.ALL, 1000);
        race3.addVehicle(stupa);
        race3.addVehicle(metla);
        race3.addVehicle(twoGorbCamel);
        race3.addVehicle(fastCamel);
        race3.addVehicle(centavr);
        race3.addVehicle(everywhereBoots);

        //here we start our race for everyone
        System.out.println("Race3: " + race3.startRace());
    }
}

