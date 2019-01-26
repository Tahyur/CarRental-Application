package com.example.tayor.karz.Model;

import java.util.ArrayList;
import java.util.List;

public class Car {
    private String name;
    private String model;
    private static List<Car> carList = new ArrayList<>();
    public Car(){

    }

    public Car(String name, String model){
        this.name = name;
        this.model = model;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }


    @Override
    public String toString() {
        return "Car{" +
                "name='" + name + '\'' +
                ", model='" + model + '\'' +
                '}';
    }

    public static List<Car> getDummyCarList(){
        carList.clear();
        setDummyCar();
        return  carList;
    }

    private static void setDummyCar(){
        carList.add(new Car("Toyota","Corolla"));
        carList.add(new Car("Toyota","Camry"));
        carList.add(new Car("Honda","Accord"));
        carList.add(new Car("Hyundai","Elentra"));
        carList.add(new Car("Dodge","Charger"));
    }
}
