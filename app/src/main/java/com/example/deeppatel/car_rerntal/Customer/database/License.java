package com.example.deeppatel.car_rerntal.Customer.database;

public class License {

    private String address;
    private String clazz;
    private String exp_date;
    private String name;
    private String license;
    private String zip;

    public License(String address, String clazz, String exp_date, String name, String license, String zip) {
        this.address = address;
        this.clazz = clazz;
        this.exp_date = exp_date;
        this.name = name;
        this.license = license;
        this.zip = zip;
    }

    public License() {

    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getExp_date() {
        return exp_date;
    }

    public void setExp_date(String exp_date) {
        this.exp_date = exp_date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}
