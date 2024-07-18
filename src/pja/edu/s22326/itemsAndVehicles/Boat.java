package pja.edu.s22326.itemsAndVehicles;

public class Boat extends Vehicle {
    private double waterDisplacement;

    public Boat(String name, double volume, String engineType, double waterDisplacement) {
        super(name, volume, engineType);
        this.waterDisplacement = waterDisplacement;
        this.vehicleType = this.getClass().getSimpleName();
    }

    public Boat(String name, double length, double width, double height, String engineType, double waterDisplacement) {
        super(name, length, width, height, engineType);
        this.waterDisplacement = waterDisplacement;
        this.vehicleType = this.getClass().getSimpleName();
    }

    public double getWaterDisplacement() {
        return waterDisplacement;
    }

}
