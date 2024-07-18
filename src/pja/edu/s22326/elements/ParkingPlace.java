package pja.edu.s22326.elements;

import pja.edu.s22326.exceptions.TooManyThingsException;
import pja.edu.s22326.itemsAndVehicles.Item;
import pja.edu.s22326.interfaces.interfaceIdentify;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ParkingPlace implements interfaceIdentify {
    private int id;
    private double volumeParkingPlace;
    private double currentUsedVolumeParkingPlace;
    private List<Item> storedItems;
    private boolean isRented;
    private LocalDate rentStartDate;
    private LocalDate rentEndDate;
    private LocalDate lastRenewalDate;
    private Person responsibleTenant;

    public ParkingPlace(double volumeParkingPlace) {
        this.volumeParkingPlace = volumeParkingPlace;
        this.id = IdGenerator.getNextId();
        this.storedItems = new ArrayList<>();
        this.currentUsedVolumeParkingPlace = 0;
    }

    public ParkingPlace(double length, double width, double height) {
        this.volumeParkingPlace = length * width * height;
        this.id = IdGenerator.getNextId();
        this.storedItems = new ArrayList<>();
        this.currentUsedVolumeParkingPlace = 0;
    }

    @Override
    public int getId() {
        return id;
    }

    public void checkInItem(Item item) throws TooManyThingsException {
        double itemVolume = item.getVolume();
        if (currentUsedVolumeParkingPlace + itemVolume > volumeParkingPlace) {
            throw new TooManyThingsException("Remove some old items to insert a new item");
        }
        storedItems.add(item);
        currentUsedVolumeParkingPlace += itemVolume; // Update the used volume
    }

    public void checkOutItem(Item item) {
        storedItems.remove(item);
            currentUsedVolumeParkingPlace -= item.getVolume(); // Update the used volume
            if (currentUsedVolumeParkingPlace < 0) {
                currentUsedVolumeParkingPlace = 0; // Prevent negative volume
            }
    }

    public void clearStoredItems() {
        storedItems.clear();
        this.setRented(false);
        this.setResponsibleTenant(null);
    }
    public LocalDate getRentStartDate() {
        return rentStartDate;
    }

    public void setRentStartDate(LocalDate rentStartDate) {
        this.rentStartDate = rentStartDate;
    }

    public LocalDate getRentEndDate() {
        return rentEndDate;
    }

    public void setRentEndDate(LocalDate rentEndDate) {
        this.rentEndDate = rentEndDate;
    }

    public LocalDate getLastRenewalDate() {
        return lastRenewalDate;
    }

    public void setLastRenewalDate(LocalDate lastRenewalDate) {
        this.lastRenewalDate = lastRenewalDate;
    }

    public Person getResponsibleTenant() {
        return responsibleTenant;
    }

    public void setResponsibleTenant(Person responsibleTenant) {
        this.responsibleTenant = responsibleTenant;
    }

    public boolean isRented() {
        return isRented;
    }

    public void setRented(boolean rented) {
        isRented = rented;
    }

    public double getCurrentUsedVolumeParkingPlace() {
        return currentUsedVolumeParkingPlace;
    }

    public List<Item> getStoredItems() {
        return storedItems;
    }

    public double getVolumeParkingPlace() {
        return volumeParkingPlace;
    }
}
