package pja.edu.s22326.itemsAndVehicles;

public class Amphibious extends Vehicle {
    private int numberOfWheels;

    public Amphibious(String name, double volume, String engineType, int numberOfWheels) {
        super(name, volume, engineType);
        this.numberOfWheels = numberOfWheels;
        this.vehicleType = this.getClass().getSimpleName();
    }

    public Amphibious(String name, double length, double width, double height, String engineType, int numberOfWheels) {
        super(name, length, width, height, engineType);
        this.numberOfWheels = numberOfWheels;
        this.vehicleType = this.getClass().getSimpleName();
    }

    public int getNumberOfWheels() {
        return numberOfWheels;
    }

}
