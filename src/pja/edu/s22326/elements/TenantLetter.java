package pja.edu.s22326.elements;

public class TenantLetter {
    private Person tenant;
    private String message;
    private Apartment relatedApartment;
    private ParkingPlace relatedParkingPlace;

    public TenantLetter(Person tenant, Apartment relatedApartment, String message) {
        this.tenant = tenant;
        this.relatedApartment = relatedApartment;
        this.message = message;
    }

    public TenantLetter(Person tenant, ParkingPlace relatedParkingPlace, String message) {
        this.tenant = tenant;
        this.relatedParkingPlace = relatedParkingPlace;
        this.message = message;
    }

    public Person getTenant() {
        return tenant;
    }

    public boolean isRelatedToApartment(Apartment apartment) {
        return relatedApartment.equals(apartment);
    }

    public boolean isRelatedToParkingPlace(ParkingPlace parkingPlace) {
        return relatedParkingPlace.equals(parkingPlace);
    }

    public void setTenant(Person tenant) {
        this.tenant = tenant;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
