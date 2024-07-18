package pja.edu.s22326.elements;

import pja.edu.s22326.interfaces.interfaceIdentify;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Person implements interfaceIdentify {
    private String name;
    private String surname;
    private LocalDate dateOfBirth;
    private List<TenantLetter> tenantLetters;
    private int id;
    private String correspondenceAddress;

    public Person(String name, String surname, String correspondenceAddress,LocalDate dateOfBirth) {
        this.name = name;
        this.surname = surname;
        this.correspondenceAddress = correspondenceAddress;
        this.dateOfBirth = dateOfBirth;
        this.id = IdGenerator.getNextId();
        tenantLetters = new ArrayList<>();
    }

    @Override
    public int getId() {
        return id;
    }

    public void removeTenantLetterForApartment(Apartment apartment) {
        tenantLetters.removeIf(letter -> letter.isRelatedToApartment(apartment));
    }

    public void removeTenantLetterForParkingPlace(ParkingPlace parkingPlace) {
        tenantLetters.removeIf(letter -> letter.isRelatedToParkingPlace(parkingPlace));
    }

    public List<TenantLetter> getTenantLetters() {
        return tenantLetters;
    }

    public boolean hasLetterForApartment(Apartment apartment) {
        return tenantLetters.stream().anyMatch(letter -> letter.isRelatedToApartment(apartment));
    }

    public boolean hasLetterForParkingPlace(ParkingPlace parkingPlace) {
        return tenantLetters.stream()
                .anyMatch(letter -> letter.isRelatedToParkingPlace(parkingPlace));
    }

    public void addTenantLetter(TenantLetter letter) {
            this.tenantLetters.add(letter);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getCorrespondenceAddress() {
        return correspondenceAddress;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }
}
