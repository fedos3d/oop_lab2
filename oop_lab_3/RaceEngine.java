import java.util.*;
enum Types {
    ALL,
    LAND,
    AIR;
}
class Race {
    int raceLength;
    ArrayList<Vehicle> vehicles = new ArrayList<>();
    Types raceType;
    Race(Types type, int raceLength) {
        this.raceType = type;
        this.raceLength = raceLength;
    }
    void addVehicle(Vehicle vh) {
        if (raceType == vh.type || raceType == Types.ALL) {
            vehicles.add(vh);
        } else {
            throw new WrongVehicleType();
        }

        /*if (raceType == Types.LAND) {
            if (raceType == vh.type) {
                vehicles.add(vh);
            } else {
                throw new WrongVehicleType();
            }
        }
        else if (raceType == Types.AIR) {
                if (raceType == vh.type) {
                    vehicles.add(vh);
                } else {
                    throw new WrongVehicleType();
                }
        }
        else {
            vehicles.add(vh);
        }

         */
    }
    String startRace() {
        double fastesttime = -1;
        String fastestvehicle = "Lul";
        for (int i = 0; i < vehicles.size(); i++) {
            double totaltime = vehicles.get(i).Race(raceLength);
            if (i == 0) {
                fastesttime = totaltime;
                fastestvehicle = vehicles.get(i).vehicleName;
            }
            if (fastesttime > totaltime) {
                fastesttime = totaltime;
                fastestvehicle = vehicles.get(i).vehicleName;
            }
        }
        return "Fastest vehicle is: " + fastestvehicle + " with speed of " + fastesttime;
    }

}
abstract class Vehicle {
    int speed;
    Types type;
    String vehicleName;
    abstract double Race(double distance);
}
class LandCraft extends Vehicle {
    int restInterval;
    double[] restDuration;
    LandCraft(String name, int speed, int restInterval, double[] restDuration) {
        this.vehicleName = name;
        this.type = Types.LAND;
        this.speed = speed;
        this.restInterval = restInterval;
        this.restDuration = restDuration;
    }
    LandCraft(String name, int speed, int restInterval, double restDuration) {
        this.vehicleName = name;
        this.type = Types.LAND;
        this.speed = speed;
        this.restInterval = restInterval;
        double[] temp = {restDuration};
        this.restDuration = temp;
    }
    double Race(double distance) {
        double totaltime = 0;
        int onezebegdist = speed * restInterval;
        int lpassed = 0;
        int k = 0;
        while (distance > lpassed) {
            if (lpassed + onezebegdist > distance) {
                double lulz = distance - lpassed;
                totaltime += (lulz / speed);
                break;
            }
            lpassed = lpassed + onezebegdist;
            totaltime += restInterval;
            if (restDuration.length != 1) {
                if (k <= restDuration.length - 1) {
                    totaltime = totaltime + restDuration[k];
                }
                else {
                    totaltime = totaltime + restDuration[restDuration.length - 1];
                }
            }
            else {
                totaltime = totaltime + restDuration[0];
            }
            k++;

        }
        return totaltime;
    }
}
class AirCraft extends Vehicle {
    Map<String, Integer> distanceReducer = new HashMap();
    AirCraft(String name, int speed, HashMap distanceReducer) {
        this.type = Types.AIR;
        this.vehicleName = name;
        this.speed = speed;
        this.distanceReducer = distanceReducer;
    }
    double Race(double distance) {
        double totaltime = 0;
        int lpassed = 0;
        int calctype = -1;
        if (distanceReducer.containsKey("more")) {
            calctype = 1;
        } else if (distanceReducer.containsKey("Evenly")) {
            calctype = 3;
        } else if (distanceReducer.containsKey("Always")) {
            calctype = 2;
        } else {
            throw new IncrorrectReduceMapException();
        }
        if (calctype == 2) {
            totaltime = (distance * (100 - (distanceReducer.get("Always"))) / 100) / speed;
        } else if (calctype == 1) {
            int[] dists = new int[distanceReducer.size()];
            String[] lulz = distanceReducer.keySet().toArray(new String[0]);
            for (int f = 0; f < dists.length; f++) {
                if (lulz[f] == "more") {
                    dists[f] = Integer.MAX_VALUE;
                } else {
                    dists[f] = Integer.parseInt(lulz[f]);
                }
            }
            if (dists[0] <= distance) {
                totaltime = distance / speed;
            } else {
                int curskidka = 0;
                for (int f = 0; f < dists.length; f++) {
                    if (distance <= dists[f]) {
                        curskidka = distanceReducer.get(lulz[f]);
                    }
                }
                totaltime = (distance * (100 - curskidka) / 100) / speed;
            }
        } else if (calctype == 3) {
            while (distance > lpassed) {
                if (distance <= 1000) {
                    totaltime = distance / speed;
                    break;
                }
                if (lpassed + 1000 > distance) {
                    double lulz = distance - lpassed;
                    totaltime += (lulz / speed);
                    break;
                }
                lpassed += 1000;
                totaltime = (double) 1000 / 8;
                distance = (distance * 99) / 100;
            }
        }
        return totaltime;
    }
}
class NoSuchRaceException extends RuntimeException {
    public NoSuchRaceException() {
        System.out.println("No such race exception");
    }
}
class WrongVehicleType extends RuntimeException {
    public WrongVehicleType() {
        System.out.println("Wrong vehicle type passed to a race");
    }
}
class IncrorrectReduceMapException extends RuntimeException {
    public IncrorrectReduceMapException() {
        System.out.println("Incorrect hashmap has been passed");
    }
}
