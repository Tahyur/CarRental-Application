package com.example.tayor.karz.Model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.Objects;

import io.realm.RealmObject;

public class Car extends RealmObject implements Parcelable {

    private String id;
    private String name;
    private String model;
    private String image;
    private String mileage;
    private String color;
    private String status;

    public Car() {
    }

    public Car(String name, String model, String image, String mileage, String color) {
        this.name = name;
        this.model = model;
        this.image = image;
        this.mileage = mileage;
        this.color = color;
    }

    protected Car(Parcel in) {
        id = in.readString();
        name = in.readString();
        model = in.readString();
        image = in.readString();
        mileage = in.readString();
        color = in.readString();
        status = in.readString();
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @NonNull
    @Override
    public String toString() {
        return "Car{" +
                "name='" + name + '\'' +

                ", model='" + model + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Car)) return false;
        Car car = (Car) o;
        return Objects.equals(getId(), car.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(model);
        dest.writeString(image);
        dest.writeString(mileage);
        dest.writeString(color);
        dest.writeString(status);
    }
}
