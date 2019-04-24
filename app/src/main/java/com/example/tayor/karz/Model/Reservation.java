package com.example.tayor.karz.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Reservation implements Parcelable {
    private String id;
    private String carId;
    private String userId;
    private String StartDateTime;
    private String EndDateTime;
    private String deposit;
    private String billingOverview;
    private String hours;
    private String mileageReturned;
    private Car car;

    public Reservation() {

    }

    protected Reservation(Parcel in) {
        id = in.readString();
        carId = in.readString();
        userId = in.readString();
        StartDateTime = in.readString();
        EndDateTime = in.readString();
        deposit = in.readString();
        billingOverview = in.readString();
        hours = in.readString();
        mileageReturned = in.readString();
        car = in.readParcelable(Car.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(carId);
        dest.writeString(userId);
        dest.writeString(StartDateTime);
        dest.writeString(EndDateTime);
        dest.writeString(deposit);
        dest.writeString(billingOverview);
        dest.writeString(hours);
        dest.writeString(mileageReturned);
        dest.writeParcelable(car, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Reservation> CREATOR = new Creator<Reservation>() {
        @Override
        public Reservation createFromParcel(Parcel in) {
            return new Reservation(in);
        }

        @Override
        public Reservation[] newArray(int size) {
            return new Reservation[size];
        }
    };

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStartDateTime() {
        return StartDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        StartDateTime = startDateTime;
    }

    public String getEndDateTime() {
        return EndDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        EndDateTime = endDateTime;
    }

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getBillingOverview() {
        return billingOverview;
    }

    public void setBillingOverview(String billingOverview) {
        this.billingOverview = billingOverview;
    }

    public String getMileageReturned() {
        return mileageReturned;
    }

    public void setMileageReturned(String mileageReturned) {
        this.mileageReturned = mileageReturned;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "StartDateTime='" + StartDateTime + '\'' +
                ", EndDateTime='" + EndDateTime + '\'' +
                ", deposit='" + deposit + '\'' +
                ", hours='" + hours + '\'' +
                ", car=" + car +
                '}';
    }
}
