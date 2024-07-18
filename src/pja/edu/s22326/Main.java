package pja.edu.s22326;

import pja.edu.s22326.elements.*;
import pja.edu.s22326.exceptions.ProblematicTenantException;
import pja.edu.s22326.exceptions.TooManyThingsException;
import pja.edu.s22326.itemsAndVehicles.*;
import java.util.Scanner;

import java.sql.Time;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Apartment apartment1 = new Apartment(100.0);
        Apartment apartment2 = new Apartment(110.0);
        Apartment apartment3 = new Apartment(120.0);
        Apartment apartment4 = new Apartment(130.0);
        Apartment apartment5 = new Apartment(50.0);


        ParkingPlace parkingPlace1 = new ParkingPlace(100.0);
        ParkingPlace parkingPlace2 = new ParkingPlace(200.0);
        ParkingPlace parkingPlace3 = new ParkingPlace(250.0);
        ParkingPlace parkingPlace4 = new ParkingPlace(300.0);
        ParkingPlace parkingPlace5 = new ParkingPlace(60.0);

        Person person1 = new Person("Anton", "Pazniak", "rzeczypospolitej 33", LocalDate.of(2002,11,25));
        Person person2 = new Person("John", "Carson", "koszykowa 86",LocalDate.of(2001,11,25));
        Person person3 = new Person("Dmytro", "Filko", "Bonifacego 22",LocalDate.of(2000,11,25));
        Person person4 = new Person("Franklin", "Alabaman", "Belgradzka 4",LocalDate.of(2000,11,25));
        Person person5 = new Person("Mahmud", "Gita", "Plosa 3",LocalDate.of(2000,11,25));

        Item item1 = new Item("Cupboard", 10);
        Item item2 = new Item("Bed", 30);
        Item item3 = new Item("superBig", 1000);

        Vehicle vehicle1 = new Amphibious("myAmphibious", 20, "Petrol", 2);
        Vehicle vehicle2 = new Boat("Lamborghini", 30, "Petrol", 99);
        Vehicle vehicle3 = new CityCar("BMW", 40, "Diesel", false);
        Vehicle vehicle4 = new Motorcycle("Harley Davidson", 10, "Petrol", false);
        Vehicle vehicle5 = new OffroadCar("Jeep", 50, "Hydrogen", 9);

        HousingEstate estate = new HousingEstate();

        estate.addApartment(apartment1);
        estate.addApartment(apartment2);
        estate.addApartment(apartment3);
        estate.addApartment(apartment4);
        estate.addApartment(apartment5);

        estate.addParkingPlace(parkingPlace1);
        estate.addParkingPlace(parkingPlace2);
        estate.addParkingPlace(parkingPlace3);
        estate.addParkingPlace(parkingPlace4);
        estate.addParkingPlace(parkingPlace5);

        estate.addPerson(person1);
        estate.addPerson(person2);
        estate.addPerson(person3);
        estate.addPerson(person4);
        estate.addPerson(person5);

        TimeSimulator timeSimulator = new TimeSimulator(estate);
        timeSimulator.start();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            displayMenu();

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline left-over

            try {
                switch (choice) {
                    case 11:
                        // Normal scenario: Rent an available apartment to a person
                        estate.clearAllData();

                        estate.rentApartment(person1, apartment3, TimeSimulator.getCurrentDate(), TimeSimulator.getCurrentDate().plusDays(30));
                        break;
                    case 12:
                        // Scenario: Rent an already occupied apartment
                        estate.clearAllData();
                        estate.rentApartment(person1, apartment1, TimeSimulator.getCurrentDate(), TimeSimulator.getCurrentDate().plusDays(30));
                        estate.rentApartment(person2, apartment2, TimeSimulator.getCurrentDate(), TimeSimulator.getCurrentDate().plusDays(30));
                        break;
                    case 13:
                        // Scenario: Rent with a problematic tenant history
                        // Manually add tenant letters to person3 to simulate history
                        estate.clearAllData();

                        estate.rentApartment(person3, apartment2, TimeSimulator.getCurrentDate(), TimeSimulator.getCurrentDate().plusDays(30));
                        estate.rentParkingPlace(person3, parkingPlace1, TimeSimulator.getCurrentDate(), TimeSimulator.getCurrentDate().plusDays(30));

                        person3.addTenantLetter(new TenantLetter(person3, apartment2,"Overdue Rent 1"));
                        person3.addTenantLetter(new TenantLetter(person3, apartment2,"Overdue Rent 2"));
                        person3.addTenantLetter(new TenantLetter(person3, apartment2,"Overdue Rent 3"));
                        person3.addTenantLetter(new TenantLetter(person3, apartment2,"Overdue Rent 4"));

                        estate.rentApartment(person3, apartment3, TimeSimulator.getCurrentDate(), TimeSimulator.getCurrentDate().plusDays(30));
                        break;
                    case 14:
                        // Scenario: Rent exceeding maximum allowed spaces
                        // Assuming person4 has already rented 5 spaces
                        estate.clearAllData();

                        estate.rentApartment(person4, apartment1, TimeSimulator.getCurrentDate(), TimeSimulator.getCurrentDate().plusDays(30));
                        estate.rentApartment(person4, apartment2, TimeSimulator.getCurrentDate(), TimeSimulator.getCurrentDate().plusDays(30));
                        estate.rentApartment(person4, apartment3, TimeSimulator.getCurrentDate(), TimeSimulator.getCurrentDate().plusDays(30));
                        estate.rentApartment(person4, apartment4, TimeSimulator.getCurrentDate(), TimeSimulator.getCurrentDate().plusDays(30));
                        estate.rentParkingPlace(person4, parkingPlace1, TimeSimulator.getCurrentDate(), TimeSimulator.getCurrentDate().plusDays(30));

                        estate.rentApartment(person4, apartment5, TimeSimulator.getCurrentDate(), TimeSimulator.getCurrentDate().plusDays(30));
                        break;
                    case 21:
                        // Normal scenario: Rent an available parking place to a person
                        estate.clearAllData();

                        estate.rentParkingPlace(person1, parkingPlace1, TimeSimulator.getCurrentDate(), TimeSimulator.getCurrentDate().plusDays(30));
                        break;
                    case 22:
                        // Scenario: Try renting an already rented parking place
                        estate.clearAllData();

                        estate.rentParkingPlace(person1, parkingPlace1, TimeSimulator.getCurrentDate(), TimeSimulator.getCurrentDate().plusDays(30));
                        estate.rentParkingPlace(person2, parkingPlace1, TimeSimulator.getCurrentDate(), TimeSimulator.getCurrentDate().plusDays(30)); // This will fail
                        break;
                    case 23:
                        // Scenario: Problematic Tenant Exception
                        estate.clearAllData();

                        estate.rentParkingPlace(person3, parkingPlace1, TimeSimulator.getCurrentDate(), TimeSimulator.getCurrentDate().plusDays(30));

                        person3.addTenantLetter(new TenantLetter(person3, parkingPlace1,"Parking Issue 1"));
                        person3.addTenantLetter(new TenantLetter(person3, parkingPlace1,"Parking Issue 2"));
                        person3.addTenantLetter(new TenantLetter(person3, parkingPlace1,"Parking Issue 3"));
                        person3.addTenantLetter(new TenantLetter(person3, parkingPlace1,"Parking Issue 4"));

                        estate.rentParkingPlace(person3, parkingPlace2, TimeSimulator.getCurrentDate(), TimeSimulator.getCurrentDate().plusDays(30));
                        break;
                    case 24:
                        // Scenario: Rent exceeding maximum allowed spaces
                        // Assuming person4 has already rented 5 spaces
                        estate.clearAllData();

                        estate.rentParkingPlace(person4, parkingPlace2, TimeSimulator.getCurrentDate(), TimeSimulator.getCurrentDate().plusDays(30));
                        estate.rentParkingPlace(person4, parkingPlace3, TimeSimulator.getCurrentDate(), TimeSimulator.getCurrentDate().plusDays(30));
                        estate.rentParkingPlace(person4, parkingPlace4, TimeSimulator.getCurrentDate(), TimeSimulator.getCurrentDate().plusDays(30));
                        estate.rentParkingPlace(person4, parkingPlace5, TimeSimulator.getCurrentDate(), TimeSimulator.getCurrentDate().plusDays(30));
                        estate.rentApartment(person4, apartment1, TimeSimulator.getCurrentDate(), TimeSimulator.getCurrentDate().plusDays(30));

                        estate.rentParkingPlace(person4, parkingPlace1, TimeSimulator.getCurrentDate(), TimeSimulator.getCurrentDate().plusDays(30)); // This should fail
                        break;
                    case 31:
                        // Normal scenario: Check in a person to an apartment
                        estate.clearAllData();

                        estate.rentApartment(person4, apartment1, TimeSimulator.getCurrentDate(), TimeSimulator.getCurrentDate().plusDays(30));
                        estate.checkInPersonToApartment(person1, apartment1);
                        break;
                    case 32:
                        // Scenario: Person is already checked into an apartment
                        estate.clearAllData();
                        estate.rentApartment(person4, apartment1, TimeSimulator.getCurrentDate(), TimeSimulator.getCurrentDate().plusDays(30));

                        estate.checkInPersonToApartment(person1, apartment1);
                        estate.checkInPersonToApartment(person1, apartment1);
                        break;
                    case 33:
                        // Scenario: Apartment is not rented
                        estate.clearAllData();

                        estate.checkInPersonToApartment(person1, apartment1);
                        break;
                    case 34:
                        // Scenario: Check out  person from the apartment
                        estate.clearAllData();
                        estate.rentApartment(person4, apartment1, TimeSimulator.getCurrentDate(), TimeSimulator.getCurrentDate().plusDays(30));

                        estate.checkInPersonToApartment(person1, apartment1);
                        estate.checkOutPersonFromApartment(person1, apartment1);
                        break;
                    case 35:
                        // Scenario: Person is not in Apartment
                        estate.clearAllData();
                        estate.rentApartment(person4, apartment1, TimeSimulator.getCurrentDate(), TimeSimulator.getCurrentDate().plusDays(30));

                        estate.checkOutPersonFromApartment(person1, apartment1);
                        break;
                    case 41:
                        // Normal scenario: Check in an item to a parking place
                        estate.clearAllData();
                        estate.rentParkingPlace(person1, parkingPlace1, TimeSimulator.getCurrentDate(), TimeSimulator.getCurrentDate().plusDays(30));

                        estate.checkInItemToParkingPlace(person1, item1, parkingPlace1.getId());
                        estate.checkInItemToParkingPlace(person1, vehicle1, parkingPlace1.getId());
                        estate.checkInItemToParkingPlace(person1, vehicle2, parkingPlace1.getId());
                        estate.checkInItemToParkingPlace(person1, vehicle3, parkingPlace1.getId());

                        break;
                    case 42:
                        // Scenario: Parking place not found
                        estate.clearAllData();

                        estate.checkInItemToParkingPlace(person1, item1, -1); // Invalid parking place ID
                        break;
                    case 43:
                        // Scenario: Person is not eligible to perform this action
                        estate.clearAllData();

                        estate.rentParkingPlace(person2, parkingPlace1, TimeSimulator.getCurrentDate(), TimeSimulator.getCurrentDate().plusDays(30));
                        estate.checkInItemToParkingPlace(person1, item1, parkingPlace1.getId()); // Person1 is not the renter of parkingPlace1
                        break;
                    case 44:
                        // Scenario: Too Many Things Exception
                        estate.clearAllData();
                        estate.rentParkingPlace(person1, parkingPlace1, TimeSimulator.getCurrentDate(), TimeSimulator.getCurrentDate().plusDays(30));

                        estate.checkInItemToParkingPlace(person1, item3, parkingPlace1.getId());
                        break;
                    case 51:
                        // Normal scenario: Check out an item from a parking place
                        estate.clearAllData();
                        estate.rentParkingPlace(person1, parkingPlace1, TimeSimulator.getCurrentDate(), TimeSimulator.getCurrentDate().plusDays(30));
                        estate.checkInItemToParkingPlace(person1, item1, parkingPlace1.getId());

                        estate.checkOutItemFromParkingPlace(person1, item1, parkingPlace1.getId());
                        break;
                    case 52:
                        // Scenario: Parking place not found
                        estate.clearAllData();

                        estate.checkOutItemFromParkingPlace(person1, item1, -1); // Invalid parking place ID
                        break;
                    case 53:
                        // Scenario: Person is not eligible to perform this action
                        estate.clearAllData();
                        estate.rentParkingPlace(person2, parkingPlace1, TimeSimulator.getCurrentDate(), TimeSimulator.getCurrentDate().plusDays(30));
                        estate.checkInItemToParkingPlace(person2, item1, parkingPlace1.getId());

                        estate.checkOutItemFromParkingPlace(person1, item1, parkingPlace1.getId()); // Person1 is not the renter of parkingPlace1
                        break;
                    case 61:
                        // Normal scenario: Successfully renew rent for an apartment
                        estate.clearAllData();
                        estate.rentApartment(person1, apartment1, TimeSimulator.getCurrentDate(), TimeSimulator.getCurrentDate().plusDays(30));
                        estate.renewRentApartment(apartment1, TimeSimulator.getCurrentDate().plusDays(30));
                        break;
                    case 62:
                        // Scenario: Attempt to renew rent for an apartment that is not rented
                        estate.clearAllData();
                        estate.renewRentApartment(apartment1, TimeSimulator.getCurrentDate().plusDays(30));
                        break;
                    case 63:
                        // Normal scenario: Successfully renew rent for a parking place
                        estate.clearAllData();
                        estate.rentParkingPlace(person1, parkingPlace1, TimeSimulator.getCurrentDate(),TimeSimulator.getCurrentDate().plusDays(30));
                        estate.renewRentParkingPlace(parkingPlace1, TimeSimulator.getCurrentDate().plusDays(30));
                        break;
                    case 64:
                        // Scenario: Attempt to renew rent for a parking place that is not rented
                        estate.clearAllData();
                        estate.renewRentParkingPlace(parkingPlace1, TimeSimulator.getCurrentDate().plusDays(30));
                        break;
                    case 71:
                        // Normal scenario: Successfully cancel rent for an apartment
                        estate.clearAllData();
                        estate.rentApartment(person1, apartment1, TimeSimulator.getCurrentDate(), TimeSimulator.getCurrentDate().plusDays(30));
                        estate.cancelRentApartment(apartment1);
                        break;
                    case 72:
                        // Scenario: Attempt to cancel rent for an apartment that is not rented
                        estate.clearAllData();
                        estate.cancelRentApartment(apartment1);
                        break;
                    case 73:
                        // Normal scenario: Successfully cancel rent for a parking place
                        estate.clearAllData();
                        estate.rentParkingPlace(person1, parkingPlace1, TimeSimulator.getCurrentDate(), TimeSimulator.getCurrentDate().plusDays(30));
                        estate.cancelRentParkingPlace(parkingPlace1);
                        break;
                    case 74:
                        // Scenario: Attempt to cancel rent for a parking place that is not rented
                        estate.clearAllData();
                        estate.cancelRentParkingPlace(parkingPlace1);
                        break;
                    case 81:
                        // Scenario: all occupants were evicted
                        estate.rentApartment(person1, apartment1, LocalDate.now(), TimeSimulator.getCurrentDate().plusDays(30));
                        break;
                    case 82:
                        // Just renew rent
                        estate.renewRentApartment(apartment3, TimeSimulator.getCurrentDate().plusDays(30));
                        break;
                    case 97:
                        // Scenario: All spaces are rented
                        //estate.clearAllData();
                        estate.rentApartment(person4, apartment1, TimeSimulator.getCurrentDate(), TimeSimulator.getCurrentDate().plusDays(30));
                        estate.rentApartment(person4, apartment2, TimeSimulator.getCurrentDate(), TimeSimulator.getCurrentDate().plusDays(30));
                        estate.rentApartment(person4, apartment3, TimeSimulator.getCurrentDate(), TimeSimulator.getCurrentDate().plusDays(30));
                        estate.rentApartment(person4, apartment4, TimeSimulator.getCurrentDate(), TimeSimulator.getCurrentDate().plusDays(30));
                        estate.rentApartment(person4, apartment5, TimeSimulator.getCurrentDate(), TimeSimulator.getCurrentDate().plusDays(30));

                        estate.rentParkingPlace(person3, parkingPlace1, TimeSimulator.getCurrentDate(), TimeSimulator.getCurrentDate().plusDays(30));
                        estate.rentParkingPlace(person3, parkingPlace2, TimeSimulator.getCurrentDate(), TimeSimulator.getCurrentDate().plusDays(30));
                        estate.rentParkingPlace(person3, parkingPlace3, TimeSimulator.getCurrentDate(), TimeSimulator.getCurrentDate().plusDays(30));
                        estate.rentParkingPlace(person3, parkingPlace4, TimeSimulator.getCurrentDate(), TimeSimulator.getCurrentDate().plusDays(30));
                        estate.rentParkingPlace(person3, parkingPlace5, TimeSimulator.getCurrentDate(), TimeSimulator.getCurrentDate().plusDays(30));
                        break;
                    case 98:
                        // Scenario: save statuses
                        estate.rentApartment(person4, apartment1, TimeSimulator.getCurrentDate(), TimeSimulator.getCurrentDate().plusDays(30));
                        estate.rentApartment(person4, apartment2, TimeSimulator.getCurrentDate(), TimeSimulator.getCurrentDate().plusDays(30));
                        estate.rentApartment(person4, apartment3, TimeSimulator.getCurrentDate(), TimeSimulator.getCurrentDate().plusDays(30));
                        estate.rentApartment(person3, apartment4, TimeSimulator.getCurrentDate(), TimeSimulator.getCurrentDate().plusDays(30));
                        estate.rentApartment(person4, apartment5, TimeSimulator.getCurrentDate(), TimeSimulator.getCurrentDate().plusDays(30));

                        estate.rentParkingPlace(person4, parkingPlace1, TimeSimulator.getCurrentDate(), TimeSimulator.getCurrentDate().plusDays(30));
                        estate.rentParkingPlace(person3, parkingPlace5, TimeSimulator.getCurrentDate(), TimeSimulator.getCurrentDate().plusDays(30));
                        estate.rentParkingPlace(person2, parkingPlace2, TimeSimulator.getCurrentDate(), TimeSimulator.getCurrentDate().plusDays(30));
                        estate.rentParkingPlace(person1, parkingPlace4, TimeSimulator.getCurrentDate(), TimeSimulator.getCurrentDate().plusDays(30));
                        estate.rentParkingPlace(person5, parkingPlace3, TimeSimulator.getCurrentDate(), TimeSimulator.getCurrentDate().plusDays(30));

                        estate.checkInItemToParkingPlace(person4, item1, parkingPlace1.getId());
                        estate.checkInItemToParkingPlace(person4, vehicle1, parkingPlace1.getId());
                        estate.checkInItemToParkingPlace(person4, vehicle2, parkingPlace1.getId());
                        estate.checkInItemToParkingPlace(person4, vehicle3, parkingPlace1.getId());

                        estate.saveEstateStatus("resources/savedStatuses.txt");
                        System.out.println("Saved.");
                        break;
                    case 99:
                        System.out.println("Exiting program.");
                        scanner.close();
                        timeSimulator.interrupt();
                        timeSimulator.stopRunning();
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (ProblematicTenantException e) {
                System.out.println(e.getMessage());
            } catch (TooManyThingsException e) {
                System.out.println(e.getMessage());
            }
        }

    }
    public static void displayMenu() {
        System.out.println("\n--- Housing Estate Management System ---");
        System.out.println("11 - Normal scenario: Rent an available apartment to a person");
        System.out.println("12 - Scenario: Rent an already occupied apartment");
        System.out.println("13 - Scenario: Rent with a problematic tenant history");
        System.out.println("14 - Scenario: Rent exceeding maximum allowed spaces");
        System.out.println("21 - Normal scenario: Rent an available parking place to a person");
        System.out.println("22 - Scenario: Try renting an already rented parking place");
        System.out.println("23 - Scenario: Problematic Tenant Exception for parking place");
        System.out.println("24 - Scenario: Rent exceeding maximum allowed spaces for parking place");
        System.out.println("31 - Normal scenario: Check in a person to an apartment");
        System.out.println("32 - Scenario: Person is already checked into an apartment");
        System.out.println("33 - Scenario: Apartment is not rented");
        System.out.println("34 - Scenario: Check out a person from the apartment");
        System.out.println("35 - Scenario: Person is not in Apartment");
        System.out.println("41 - Normal scenario: Check in an item to a parking place");
        System.out.println("42 - Scenario: Parking place not found");
        System.out.println("43 - Scenario: Person is not eligible to perform this action for item check-in");
        System.out.println("44 - Scenario: Too Many Things Exception for parking place");
        System.out.println("51 - Normal scenario: Check out an item from a parking place");
        System.out.println("52 - Scenario: Parking place not found for item check-out");
        System.out.println("53 - Scenario: Person is not eligible to perform this action for item check-out");
        System.out.println("61 - Normal scenario: Successfully renew rent for an apartment");
        System.out.println("62 - Scenario: Attempt to renew rent for an apartment that is not rented");
        System.out.println("63 - Normal scenario: Successfully renew rent for a parking place");
        System.out.println("64 - Scenario: Attempt to renew rent for a parking place that is not rented");
        System.out.println("71 - Normal scenario: Successfully cancel rent for an apartment");
        System.out.println("72 - Scenario: Attempt to cancel rent for an apartment that is not rented");
        System.out.println("73 - Normal scenario: Successfully cancel rent for a parking place");
        System.out.println("74 - Scenario: Attempt to cancel rent for a parking place that is not rented");
        System.out.println("81 - Scenario: all occupants were evicted");
        System.out.println("82 - Just renew rent");
        System.out.println("97 - Scenario: All spaces are rented");
        System.out.println("98 - Scenario: save statuses");
        System.out.println("99 - Exit program");
        System.out.print("Enter your choice: ");
    }
}
