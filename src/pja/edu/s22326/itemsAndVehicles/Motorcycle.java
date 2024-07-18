package pja.edu.s22326.itemsAndVehicles;

public class Motorcycle extends Vehicle {
    private boolean hasTrolley;

    public Motorcycle(String name, double volume, String engineType, boolean hasTrolley) {
        super(name, volume, engineType);
        this.hasTrolley = hasTrolley;
        this.vehicleType = this.getClass().getSimpleName();
    }

    public Motorcycle(String name, double length, double width, double height, String engineType, boolean hasTrolley) {
        super(name, length, width, height, engineType);
        this.hasTrolley = hasTrolley;
        this.vehicleType = this.getClass().getSimpleName();
    }

    public boolean hasTrolley() {
        return hasTrolley;
    }

}