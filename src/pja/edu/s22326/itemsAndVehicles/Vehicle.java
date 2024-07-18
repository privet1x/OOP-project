package pja.edu.s22326.itemsAndVehicles;

public abstract class Vehicle extends Item {
    protected String engineType;
    protected String vehicleType;// Common attribute for all vehicles

    // Constructor for directly setting the volume
    public Vehicle(String name, double volume, String engineType) {
        super(name, volume);
        this.engineType = engineType;
        this.vehicleType = this.getClass().getSimpleName();
    }

    // Constructor for calculating the volume from dimensions
    public Vehicle(String name, double length, double width, double height, String engineType) {
        super(name, length, width, height);
        this.engineType = engineType;
        this.vehicleType = this.getClass().getSimpleName();
    }

    public String getEngineType() {
        return engineType;
    }

    public String getVehicleType() {
        return vehicleType;
    }

}
