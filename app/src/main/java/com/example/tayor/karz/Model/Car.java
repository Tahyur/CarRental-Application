package com.example.tayor.karz.Model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.tayor.karz.R;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;
import io.realm.exceptions.RealmException;

public class Car extends RealmObject implements Parcelable {
    @PrimaryKey
    private int id;
    private String name;
    private String model;
    private int resource;
    private double mileage;
    private String color;
    private static List<Car> carList = new ArrayList<>();

    public Car() {

    }

    public Car(String name, String model, int resource, double mileage, String color) {
        this.name = name;
        this.model = model;
        this.resource = resource;
        this.mileage = mileage;
        this.color = color;
    }

    protected Car(Parcel in) {
        id = in.readInt();
        name = in.readString();
        model = in.readString();
        resource = in.readInt();
        mileage = in.readDouble();
        color = in.readString();
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

    public int getResource() {
        return resource;
    }

    public void setResource(int resource) {
        this.resource = resource;
    }

    private static void setDummyCarList() {
        carList.clear();
        carList.add(new Car("Mercedez Benz", "ML 919", R.drawable.benz, 15434.29, "Gray"));
        carList.add(new Car("Tesla", "V8", R.drawable.tesla, 12454.50, "Red"));
        carList.add(new Car("Ford", "Mustang", R.drawable.ford, 13453.57, "Black and Gold"));
        carList.add(new Car("Hexa", "H8", R.drawable.hexa, 9394.34, "Blue"));
        carList.add(new Car("Chevrolet", "Camaro", R.drawable.chevrolet, 9564.45, "White and Orange"));
    }

    @NonNull
    @Override
    public String toString() {
        return "Car{" +
                "name='" + name + '\'' +

                ", model='" + model + '\'' +
                '}';
    }

    public static void createDummyCarObject() {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<Car> query = realm.where(Car.class);
        RealmResults<Car> cars = query.findAll();
        if (query.findAll().size() < 1) {
            try {
                if (!realm.isInTransaction()) {
                    realm.beginTransaction();
                    int i = 0;
                    Log.d("carlist", cars.toString());
                    setDummyCarList();
                    for (Car c : carList) {
                        Car car = realm.createObject(Car.class, i + 1);
                        car.setName(c.getName());
                        car.setModel(c.getModel());
                        car.setResource(c.getResource());
                        car.setMileage(c.getMileage());
                        car.setColor(c.getColor());
                        i++;
                    }
                    realm.commitTransaction();
                }
            } catch (RealmException ex) {
                Log.e("ErrorHere", ex.getMessage());
            }
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMileage() {
        return mileage;
    }

    public void setMileage(double mileage) {
        this.mileage = mileage;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(model);
        dest.writeInt(resource);
        dest.writeDouble(mileage);
        dest.writeString(color);
    }
}
