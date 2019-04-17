package com.example.tayor.karz.Model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;
import io.realm.exceptions.RealmException;

public class Car extends RealmObject implements Parcelable {

    private String id;
    private String name;
    private String model;
    private String image;
    private double mileage;
    private String color;
    private boolean status;

    private static List<Car> carList = new ArrayList<>();

    public Car() {

    }

    public Car(String name, String model, String image, double mileage, String color) {
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
        mileage = in.readDouble();
        color = in.readString();
        status = in.readByte() != 0;
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

//    private static void setDummyCarList() {
//        carList.clear();
//        carList.add(new Car("Mercedez Benz", "ML 919", "https://firebasestorage.googleapis.com/v0/b/karz-25686.appspot.com/o/images%2Fbenz.jpeg?alt=media&token=c52eb31e-361b-4b7c-9252-1004748cbbf4",1234.67, "White"));
//        carList.add(new Car("BMW", "super", "https://firebasestorage.googleapis.com/v0/b/karz-25686.appspot.com/o/images%2Fbmw.jpg?alt=media&token=6cb112ec-471d-4b7d-82cb-6d580866fa0b", 12454.50, "Silver"));
//        carList.add(new Car("Bugatti", "Veron","https://firebasestorage.googleapis.com/v0/b/karz-25686.appspot.com/o/images%2Fbugatti.jpg?alt=media&token=849393f0-fbea-4081-90c7-b31d06ec7421", 13453.57, "Black and Red"));
//        carList.add(new Car("Nissan", "H8", "https://firebasestorage.googleapis.com/v0/b/karz-25686.appspot.com/o/images%2Fnissan.jpg?alt=media&token=06b7851b-40fc-4f69-bc3f-2c457777663a", 9394.34, "Silver"));
//        carList.add(new Car("Ford", "Mustang", "https://firebasestorage.googleapis.com/v0/b/karz-25686.appspot.com/o/images%2Fmustang.jpg?alt=media&token=8fc79df1-9bea-408d-8281-118879e29b10", 9564.45, "Black"));
//    }

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

    //    public static void createDummyCarObject() {
//        Realm realm = Realm.getDefaultInstance();
//        RealmQuery<Car> query = realm.where(Car.class);
//        RealmResults<Car> cars = query.findAll();
//        if (query.findAll().size() < 1) {
//            try {
//                if (!realm.isInTransaction()) {
//                    realm.beginTransaction();
//                    int i = 0;
//                    Log.d("carlist", cars.toString());
//                    setDummyCarList();
//                    for (Car c : carList) {
//                        Car car = realm.createObject(Car.class, i + 1);
//                        car.setName(c.getName());
//                        car.setModel(c.getModel());
//                        car.setImage(c.getImage());
//                        car.setMileage(c.getMileage());
//                        car.setColor(c.getColor());
//                        i++;
//                    }
//                    realm.commitTransaction();
//                }
//            } catch (RealmException ex) {
//                Log.e("ErrorHere", ex.getMessage());
//            }
//        }
//    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
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
        dest.writeDouble(mileage);
        dest.writeString(color);
        dest.writeByte((byte) (status ? 1 : 0));
    }


}
