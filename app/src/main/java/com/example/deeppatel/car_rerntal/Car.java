package com.example.deeppatel.car_rerntal;

public class Car {

    private String car_name;
    private String car_model;
    private String booked_by;
    private String booked_on;
    private String available_on;

    public Car(String car_name, String car_model, String booked_by, String booked_on, String available_on) {
        this.car_name = car_name;
        this.car_model = car_model;
        this.booked_by = booked_by;
        this.booked_on = booked_on;
        this.available_on = available_on;
    }

    public Car() {

    }

    public String getCar_name() {
        return car_name;
    }

    public void setCar_name(String car_name) {
        this.car_name = car_name;
    }

    public String getCar_model() {
        return car_model;
    }

    public String getBooked_by() {
        return booked_by;
    }

    public void setBooked_by(String booked_by) {
        this.booked_by = booked_by;
    }

    public String getBooked_on() {
        return booked_on;
    }

    public void setBooked_on(String booked_on) {
        this.booked_on = booked_on;
    }

    public String getAvailable_on() {
        return available_on;
    }

    public void setAvailable_on(String available_on) {
        this.available_on = available_on;
    }

    public void setCar_model(String car_model) {
        this.car_model = car_model;
    }
}