package pja.edu.s22326.itemsAndVehicles;

public class CityCar extends Vehicle {
    private boolean isCabriolet; // Specific feature for CityCar

    public CityCar(String name, double volume, String engineType, boolean isCabriolet) {
        super(name, volume, engineType);
        this.isCabriolet = isCabriolet;
        this.vehicleType = this.getClass().getSimpleName();
    }

    public CityCar(String name, double length, double width, double height, String engineType, boolean isCabriolet) {
        super(name, length, width, height, engineType);
        this.isCabriolet = isCabriolet;
        this.vehicleType = this.getClass().getSimpleName();
    }

    public boolean isCabriolet() {
        return isCabriolet;
    }

}
