package com.example.tayor.karz.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Car implements Parcelable {
    private String name;
    private String model;
    private static List<Car> carList = new ArrayList<>();
    public Car(){

    }

    public Car(String name, String model){
        this.name = name;
        this.model = model;
    }

    protected Car(Parcel in) {
        name = in.readString();
        model = in.readString();
    }

    public static final Creator<Car> CREATOR = new Creator<Car>() {
        @Override
        public Car createFromParcel(Parcel in) {
            return new Car(in);
        }

        @Override
        public Car[] newArray(int size) {
            return new Car[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(model);
    }
}
