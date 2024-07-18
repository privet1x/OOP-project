package pja.edu.s22326.itemsAndVehicles;

public class Item {
    private String name;
    private double volume; // Volume in cubic meters

    // Constructor for directly setting the volume
    public Item(String name, double volume) {
        this.name = name;
        this.volume = volume;
    }

    // Constructor for calculating the volume from dimensions
    public Item(String name, double length, double width, double height) {
        this.name = name;
        this.volume = length * width * height; // Calculating the volume of a cuboid
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }
}
