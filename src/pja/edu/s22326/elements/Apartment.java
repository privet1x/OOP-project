package pja.edu.s22326.elements;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import pja.edu.s22326.interfaces.interfaceIdentify;


public class Apartment implements interfaceIdentify {
    private int id;
    private double volumeApartment;
    private Person responsibleTenant;
    private boolean isOccupied;
    private LocalDate rentStartDate;
    private LocalDate rentEndDate;
    private LocalDate lastRenewalDate;
    private List<Person> occupants;

    public Apartment(double volumeApartment) {
        this.volumeApartment = volumeApartment;
        this.id = IdGenerator.getNextId();
        occupants = new ArrayList<>();
    }
    public Apartment(double length, double width, double height) {
        this.volumeApartment = length * width * height;
        this.id = IdGenerator.getNextId();
        occupants = new ArrayList<>();
    }

    @Override
    public int getId() {
        return id;
    }

    public LocalDate getLastRenewalDate() {
        return lastRenewalDate;
    }

    public void setLastRenewalDate(LocalDate lastRenewalDate) {
        this.lastRenewalDate = lastRenewalDate;
    }

    public LocalDate getRentStartDate() {
        return rentStartDate;
    }

    public List<Person> getOccupants() {
        return occupants;
    }

    public void evictOccupants() {
        occupants.clear();
        this.setOccupied(false);
        this.setResponsibleTenant(null);
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

    public Person getResponsibleTenant() {
        return responsibleTenant;
    }

    public void setResponsibleTenant(Person responsibleTenant) {
        this.responsibleTenant = responsibleTenant;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    public double getVolumeApartment() {
        return volumeApartment;
    }

    @Override
    public String toString() {
        return "Apartment{" +
                "id=" + id +
                ", volume=" + volumeApartment +
                ", occupied=" + (isOccupied ? "Yes" : "No") +
                ", responsibleTenant=" + (responsibleTenant != null ? responsibleTenant.getName() : "None") +
                '}';
    }

}
