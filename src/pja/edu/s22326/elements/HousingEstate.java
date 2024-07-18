package pja.edu.s22326.elements;

import pja.edu.s22326.exceptions.ProblematicTenantException;
import pja.edu.s22326.exceptions.TooManyThingsException;
import pja.edu.s22326.itemsAndVehicles.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HousingEstate {

    private List<Apartment> apartments;
    private List<ParkingPlace> parkingPlaces;
    private List<Person> people;

    public HousingEstate() {
        this.apartments = new ArrayList<>();
        this.parkingPlaces = new ArrayList<>();
        this.people = new ArrayList<>();
    }

    public void clearAllData() {
        for (ParkingPlace parkingPlace : parkingPlaces) {
            parkingPlace.clearStoredItems();
        }

        for (Person person: people) {
            person.getTenantLetters().clear();
        }

        for (Apartment apartment : apartments) {
            apartment.evictOccupants();
        }

    }

    public void addApartment(Apartment apartment) {
        apartments.add(apartment);
    }

    public void addPerson(Person person) {
        people.add(person);
    }

    public void addParkingPlace(ParkingPlace parkingPlace) {
        parkingPlaces.add(parkingPlace);
    }

    private void sortApartments(List<Apartment> apartments) {
        Collections.sort(apartments, Comparator.comparingDouble(Apartment::getVolumeApartment));
    }

    private void sortParkingPlaces(List<ParkingPlace> parkingPlaces) {
        Collections.sort(parkingPlaces, Comparator.comparingDouble(ParkingPlace::getVolumeParkingPlace));
    }

    private List<ParkingPlace> getOccupantParkingPlaces(List<ParkingPlace> allParkingPlaces, Person occupant) {
        List<ParkingPlace> occupantParkingPlaces = new ArrayList<>();

        for (ParkingPlace parkingPlace : allParkingPlaces) {
            if (parkingPlace.isRented() && parkingPlace.getResponsibleTenant().equals(occupant)) {
                occupantParkingPlaces.add(parkingPlace);
            }
        }

        return occupantParkingPlaces;
    }

    private void sortParkingPlaceItems(ParkingPlace parkingPlace) {
        Collections.sort(parkingPlace.getStoredItems(), new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                int volumeCompare = Double.compare(o2.getVolume(), o1.getVolume());
                if (volumeCompare != 0) return volumeCompare;
                return o1.getName().compareTo(o2.getName());
            }
        });
    }

    public void saveEstateStatus(String filename) {
        // Sort the apartments first
        sortApartments(apartments);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, false))) {

            for (Apartment apartment : apartments) {
                if (apartment.getResponsibleTenant() != null) {
                    Person responsibleTenant = apartment.getResponsibleTenant();

                    writer.write("Apartment with ID: " + apartment.getId() + " and Volume: " + apartment.getVolumeApartment() + " is rented by:\n");
                    writer.write("\tResponsible Tenant: ID " + responsibleTenant.getId() + ", Name: " + responsibleTenant.getName() + ", Surname: "  + responsibleTenant.getSurname() +
                            ", Correspondence Address: " + responsibleTenant.getCorrespondenceAddress() + ", Date of birth: " + responsibleTenant.getDateOfBirth() + "\n");

                    for (TenantLetter letter : apartment.getResponsibleTenant().getTenantLetters()) {
                        writer.write("\t\tTenant Letters: " + letter.getMessage() + "\n");
                    }


                    // List all occupants living in this apartment
                    for (Person occupant : apartment.getOccupants()) {
                        writer.write("\tOccupant: ID " + occupant.getId() + ", Name: " + occupant.getName() + ", Surname: "  + occupant.getSurname() +
                                ", Correspondence Address: " + occupant.getCorrespondenceAddress() + ", Date of birth: " + occupant.getDateOfBirth() + "\n");

                        // Iterate through all parking places to find those rented by this occupant
                        for (ParkingPlace parkingPlace : parkingPlaces) {
                            sortParkingPlaces(getOccupantParkingPlaces(parkingPlaces, occupant));
                            if (parkingPlace.isRented() && parkingPlace.getResponsibleTenant().equals(occupant)) {

                                writer.write("\t\tOccupant rents Parking Place ID: " + parkingPlace.getId() + ", Volume: " + parkingPlace.getVolumeParkingPlace() + " with stored items:\n");

                                sortParkingPlaceItems(parkingPlace);
                                // List items stored in this parking place
                                List<Item> storedItems = parkingPlace.getStoredItems();
                                for (Item item : storedItems) {
                                    if(!(item instanceof Vehicle))
                                        writer.write("\t\t\tItem: " + item.getName() + ", Volume: " + item.getVolume() + "\n");

                                    if (item instanceof Vehicle) {
                                        Vehicle vehicle = (Vehicle) item;
                                        writer.write("\t\t\tVehicle: " + vehicle.getName() + ", Volume: " + vehicle.getVolume() +
                                                ", Engine Type: " + vehicle.getEngineType() + ", Vehicle Type: " + vehicle.getVehicleType());

                                        if (vehicle instanceof OffroadCar) {
                                            OffroadCar offRoadCar = (OffroadCar) vehicle;
                                            writer.write(", Ground Clearance: " + offRoadCar.getGroundClearance());
                                        } else if (vehicle instanceof CityCar) {
                                            CityCar cityCar = (CityCar) vehicle;
                                            writer.write(", Is Cabriolet: " + cityCar.isCabriolet());
                                        } else if (vehicle instanceof Boat) {
                                            Boat boat = (Boat) vehicle;
                                            writer.write(", Water Displacement: " + boat.getWaterDisplacement());
                                        } else if (vehicle instanceof Motorcycle) {
                                            Motorcycle motorcycle = (Motorcycle) vehicle;
                                            writer.write(", Has Trolley: " + motorcycle.hasTrolley());
                                        } else if (vehicle instanceof Amphibious) {
                                            Amphibious amphibious = (Amphibious) vehicle;
                                            writer.write(", Number of Wheels: " + amphibious.getNumberOfWheels());
                                        }
                                    }

                                }
                            }
                        }
                    }
                }
            }
            writer.flush();
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public void renewRentApartment(Apartment apartment, LocalDate newRentEndDate) {
        if (apartment.isOccupied()) {
            Person responsibleTenant = apartment.getResponsibleTenant();
            apartment.setRentEndDate(newRentEndDate); // Update the rent end date
            responsibleTenant.removeTenantLetterForApartment(apartment);

            System.out.println("You successfully renewed rent for apartment.");
            apartment.setLastRenewalDate(TimeSimulator.getCurrentDate());
        } else {
            System.out.println("Apartment is not currently rented.");
        }
    }

    public void renewRentParkingPlace(ParkingPlace parkingPlace, LocalDate newRentEndDate) {
        if (parkingPlace.isRented()) {
            Person responsibleTenant = parkingPlace.getResponsibleTenant();
            parkingPlace.setRentEndDate(newRentEndDate); // Update the rent end date
            responsibleTenant.removeTenantLetterForParkingPlace(parkingPlace);

            System.out.println("You successfully renewed rent for apartment.");
            parkingPlace.setLastRenewalDate(TimeSimulator.getCurrentDate());
        } else {
            System.out.println("Parking place is not currently rented.");
        }
    }

    public void cancelRentApartment(Apartment apartment) {
        if (apartment.isOccupied()) {
            Person responsibleTenant = apartment.getResponsibleTenant();
            apartment.evictOccupants(); // Evict tenants and set apartment to unoccupied
            apartment.setRentEndDate(null); // Clear the rent end date
            responsibleTenant.removeTenantLetterForApartment(apartment);

            System.out.println("You successfully canceled rent for apartment.");
        } else {
            System.out.println("Apartment is not currently rented.");
        }
    }

    public void cancelRentParkingPlace(ParkingPlace parkingPlace) {
        if (parkingPlace.isRented()) {
            Person responsibleTenant = parkingPlace.getResponsibleTenant();
            parkingPlace.clearStoredItems(); // Clear any stored items and set parking place to unrented
            parkingPlace.setRentEndDate(null); // Clear the rent end date
            responsibleTenant.removeTenantLetterForParkingPlace(parkingPlace);

            System.out.println("You successfully canceled rent for parking place.");
        } else {
            System.out.println("Parking place is not currently rented.");
        }
    }

    private synchronized void checkIfAllSpacesAreRented() {
        boolean allRented = true;

        for (Apartment apartment : apartments) {
            if (!apartment.isOccupied()) {
                allRented = false;
                break;
            }
        }


        if (allRented) {
            for (ParkingPlace parkingPlace : parkingPlaces) {
                if (!parkingPlace.isRented()) {
                    allRented = false;
                    break;
                }
            }
        }

        if (allRented) {
            System.out.println("All spaces are rented right now.");
        } else {
            System.out.println("There are some unrented spaces right now.");
        }
    }

    public synchronized void handleOverdueRentals(LocalDate currentDate) {

        for (Apartment apartment : apartments) {
            if (apartment.getRentEndDate() != null && currentDate.isAfter(apartment.getRentEndDate())) {
                Person responsibleTenant = apartment.getResponsibleTenant();
                if (responsibleTenant != null && !responsibleTenant.hasLetterForApartment(apartment)) {
                    TenantLetter letter = new TenantLetter(responsibleTenant, apartment, "Your rent for apartment ID: " + apartment.getId() + " is overdue. Please contact the housing estate.");
                    responsibleTenant.addTenantLetter(letter);
                    System.out.println("Letter was added: " + letter.getMessage());
                }

                LocalDate thirtyDaysAgo = currentDate.minusDays(30);
                if ((apartment.getLastRenewalDate() == null || apartment.getLastRenewalDate().isBefore(thirtyDaysAgo)) && apartment.isOccupied()) {
                        apartment.evictOccupants();
                        System.out.println("All occupants were evicted.");
                }
            }
        }

        for (ParkingPlace parkingPlace : parkingPlaces) {
            if (parkingPlace.getRentEndDate() != null && currentDate.isAfter(parkingPlace.getRentEndDate())) {
                Person responsibleTenant = parkingPlace.getResponsibleTenant();
                if (responsibleTenant != null && !responsibleTenant.hasLetterForParkingPlace(parkingPlace)) {
                    TenantLetter letter = new TenantLetter(responsibleTenant, parkingPlace, "Your rent for parking place ID: " + parkingPlace.getId() + " is overdue. Please contact the housing estate.");
                    responsibleTenant.addTenantLetter(letter);
                    System.out.println("Letter was added: " + letter.getMessage());
                }

                LocalDate thirtyDaysAgo = currentDate.minusDays(30);
                if ((parkingPlace.getLastRenewalDate() == null || parkingPlace.getLastRenewalDate().isBefore(thirtyDaysAgo)) && parkingPlace.isRented()) {
                    parkingPlace.clearStoredItems();
                    System.out.println("All items were utilized.");
                }
            }
        }

        checkIfAllSpacesAreRented();
    }

    public void checkInPersonToApartment(Person person, Apartment apartment) {
        if(apartment.isOccupied()) {
            if (!apartment.getOccupants().contains(person)) {
                apartment.getOccupants().add(person);
                System.out.println(person.getName() + " is checked into Apartment " + apartment.getId());
            } else {
                System.out.println(person.getName() + " is already in Apartment " + apartment.getId());
            }
        } else
            System.out.println("Apartment " + apartment.getId() + " is not rented.");
    }

    public void checkOutPersonFromApartment(Person person, Apartment apartment) {
        if(apartment.isOccupied()) {
            if (apartment.getOccupants().contains(person)) {
                apartment.getOccupants().remove(person);
                System.out.println(person.getName() + " was checked out of Apartment " + apartment.getId());
            } else {
                System.out.println(person.getName() + " is not in Apartment " + apartment.getId());
            }
        } else
            System.out.println("Apartment " + apartment.getId() + " is not rented.");
    }

    public void rentApartment(Person person, Apartment apartment, LocalDate startDate, LocalDate endDate) throws ProblematicTenantException {
        if (apartment.isOccupied()) {
            System.out.println("Apartment " + apartment.getId() + " is already occupied.");
            return;
        }

        if (person.getTenantLetters().size() > 3) {
            throw new ProblematicTenantException("Person " + person.getName() + " had already renting rooms: " + showAllSpacesRentedByPerson(person));
        }

        if (countTotalRentedSpaces(person) >= 5) {
            System.out.println("Person " + person.getName() + " can't rent more than 5 spaces.");
            return;
        }

        apartment.setRentStartDate(startDate);
        apartment.setRentEndDate(endDate);
        apartment.setOccupied(true);
        apartment.setResponsibleTenant(person);
        apartment.getOccupants().add(person); // responsible tenant is also an occupant of apartment
        System.out.println("Apartment " + apartment.getId() + " has been rented to " + person.getName() + ".");
    }

    public void rentParkingPlace(Person person, ParkingPlace parkingPlace, LocalDate startDate, LocalDate endDate) throws ProblematicTenantException {

        if (person.getTenantLetters().size() > 3) {
            throw new ProblematicTenantException("Person " + person.getName() + " had already renting rooms: " + showAllSpacesRentedByPerson(person));
        }

        if (parkingPlace.isRented()) {
            System.out.println("Parking place " + parkingPlace.getId() + " is already rented.");
            return;
        }

        if (countTotalRentedSpaces(person) >= 5) {
            System.out.println("Person " + person.getName() + " cannot rent more than 5 spaces.");
            return;
        }

        parkingPlace.setRentStartDate(startDate);
        parkingPlace.setRentEndDate(endDate);
        parkingPlace.setRented(true);
        parkingPlace.setResponsibleTenant(person);
        System.out.println("Parking place " + parkingPlace.getId() + " has been rented to " + person.getName() + ".");
    }

    private int countTotalRentedSpaces(Person person) {
        int count = 0;
        for (Apartment apartment : apartments) {
            if (apartment.getResponsibleTenant() != null && apartment.getResponsibleTenant().equals(person)) {
                count++;
            }
        }

        for (ParkingPlace parkingPlace : parkingPlaces) {
            if (parkingPlace.getResponsibleTenant() != null && parkingPlace.getResponsibleTenant().equals(person)) {
                count++;
            }
        }
        return count;
    }

    public String showAllSpacesRentedByPerson(Person person) {
        StringBuilder rentedSpaces = new StringBuilder();

        // List all apartments rented by the person
        for (Apartment apartment : apartments) {
            if (apartment.getResponsibleTenant() != null && apartment.getResponsibleTenant().equals(person)) {
                rentedSpaces.append("Apartment ID: ")
                        .append(apartment.getId())
                        .append(", ");
            }
        }

        // List all parking places rented by the person
        for (ParkingPlace parkingPlace : parkingPlaces) {
            if (parkingPlace.getResponsibleTenant() != null && parkingPlace.getResponsibleTenant().equals(person)) {
                rentedSpaces.append("Parking Place ID: ")
                        .append(parkingPlace.getId())
                        .append(", ");
            }
        }

        // Remove the last comma and space if the string is not empty
        if (rentedSpaces.length() > 0) {
            rentedSpaces.setLength(rentedSpaces.length() - 2);
        }


        return rentedSpaces.toString();
    }

    public void checkInItemToParkingPlace(Person person, Item item, int parkingPlaceId) throws TooManyThingsException {
        ParkingPlace parkingPlace = findParkingPlaceById(parkingPlaceId);
        if (parkingPlace == null) {
            System.out.println("Parking place not found.");
            return;
        }

        if (!isEligibleForParkingPlaceAction(person, parkingPlace)) {
            System.out.println("Person is not eligible to perform this action.");
            return;
        }

        parkingPlace.checkInItem(item);
        System.out.println("Item:" + item.getName() + " with volume:" + item.getVolume() + " was checked into the parking place ");
    }

    public void checkOutItemFromParkingPlace(Person person, Item item, int parkingPlaceId) {
        ParkingPlace parkingPlace = findParkingPlaceById(parkingPlaceId);
        if (parkingPlace == null) {
            System.out.println("Parking place not found.");
            return;
        }

        if (!isEligibleForParkingPlaceAction(person, parkingPlace)) {
            System.out.println("Person is not eligible to perform this action.");
            return;
        }

        parkingPlace.checkOutItem(item);
        System.out.println("Item:" + item.getName() + " with volume:" + item.getVolume() + " was checked out of the parking place ");
    }

    private ParkingPlace findParkingPlaceById(int parkingPlaceId) {
        return parkingPlaces.stream().filter(p -> p.getId() == parkingPlaceId).findFirst().orElse(null);
    }
    // filter creates new Stream sequence. If lambda returned true then this object is included in the stream if false then excluded out of this new Stream
    private boolean isEligibleForParkingPlaceAction(Person person, ParkingPlace parkingPlace) {
        return isRentingParkingPlace(person, parkingPlace);
    }

    public boolean isRentingParkingPlace(Person person, ParkingPlace parkingPlace) {
        return parkingPlaces.stream().anyMatch(p -> p.getId() == parkingPlace.getId() && p.isRented() && p.getResponsibleTenant().equals(person));
    }

}
