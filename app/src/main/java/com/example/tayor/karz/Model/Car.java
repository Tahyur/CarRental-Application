package com.example.tayor.karz.Model;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import java.util.Objects;

public class Car implements Parcelable {

    private String documentId;
    private String name;
    private String model;
    private String imageUrl;
    private String mileage;
    private String color;
    private String status;

    public Car() {}

    public Car(String name, String model, String imageUrl, String mileage, String color) {
        this.name = name;
        this.model = model;
        this.imageUrl = imageUrl;
        this.mileage = mileage;
        this.color = color;
    }

    protected Car(Parcel in) {
        documentId = in.readString();
        name = in.readString();
        model = in.readString();
        imageUrl = in.readString();
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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
        return Objects.equals(getDocumentId(), car.getDocumentId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDocumentId());
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String id) {
        this.documentId = id;
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
        dest.writeString(documentId);
        dest.writeString(name);
        dest.writeString(model);
        dest.writeString(imageUrl);
        dest.writeString(mileage);
        dest.writeString(color);
        dest.writeString(status);
    }
}
