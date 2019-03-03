package com.example.deeppatel.car_rerntal.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerEngine {

    private List<Customer> customerListList = new ArrayList<>();

    public CustomerEngine(List<Customer> customerListList) {
        this.customerListList = customerListList;
    }

    public CustomerEngine() {
    }

    public int getCount(){

        return this.customerListList.size();

    }

    public Customer getcustomer(int position){

        if(customerListList.size() > 0){

            return customerListList.get(position);

        }

        return null;

    }

    public void addCustomers(int count){

        for(int i = 0; i < count; i++){

            customerListList.add(new Customer("Customer " + i, "License No:" + i));

        }

    }

}
