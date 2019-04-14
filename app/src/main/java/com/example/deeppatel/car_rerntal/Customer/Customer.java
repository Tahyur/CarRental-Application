package com.example.deeppatel.car_rerntal.Customer;

import android.graphics.Color;

import com.example.deeppatel.car_rerntal.Helpers.RandomColorGenerator;

import java.io.Serializable;
import java.util.Random;

public class Customer implements Serializable {

    private String customerName;
    private String licenseNo;
    private int color;

    public Customer(String customerName, String licenseNo) {
        this.customerName = customerName;
        this.licenseNo = licenseNo;
        this.color = RandomColorGenerator.getRandColor();
    }

    public int getColor() {
        return color;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }


}
