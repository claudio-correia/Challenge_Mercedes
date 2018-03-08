package mb.DataStructures;

import java.util.Hashtable;

public class Vehicle {


    private String id;
    private String model;
    private String fuel;
    private String transmission;
    private Hashtable<String, String[]> availability;

    private String dealer;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public Hashtable<String, String[]> getAvailability() {
        return availability;
    }

    public void setAvailability(Hashtable<String, String[]> availability) {
        this.availability = availability;
    }

    public String getDealer() {
        return dealer;
    }

    public void setDealer(String dealer) {
        this.dealer = dealer;
    }
}
