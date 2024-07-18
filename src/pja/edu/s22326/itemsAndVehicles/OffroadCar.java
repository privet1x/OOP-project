package pja.edu.s22326.itemsAndVehicles;

public class OffroadCar extends Vehicle {
    private double groundClearance; // Specific attribute for OffroadCar

    // Constructor for directly setting the volume
    public OffroadCar(String name, double volume, String engineType, double groundClearance) {
        super(name, volume, engineType);
        this.groundClearance = groundClearance;
        this.vehicleType = this.getClass().getSimpleName();
    }

    // Constructor for calculating the volume from dimensions
    public OffroadCar(String name, double length, double width, double height, String engineType, double groundClearance) {
        super(name, length, width, height, engineType);
        this.groundClearance = groundClearance;
        this.vehicleType = this.getClass().getSimpleName();
    }

    public double getGroundClearance() {
        return groundClearance;
    }

}

