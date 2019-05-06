package com.example.tayor.karz.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Reservation implements Parcelable {
    private String documentId;
    private String userId; // user document Id
    private ReservationPeriod reservationPeriod;
    private String estimatedCharges;
    private String deposit;
    private String hours;
    private Car car;

    public Reservation() {
    }

    protected Reservation(Parcel in) {
        documentId = in.readString();
        userId = in.readString();
        deposit = in.readString();
        hours = in.readString();
        car = in.readParcelable(Car.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(documentId);
        dest.writeString(userId);
        dest.writeString(deposit);
        dest.writeString(hours);
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

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public ReservationPeriod getReservationPeriod() {
        return reservationPeriod;
    }

    public void setReservationPeriod(ReservationPeriod reservationPeriod) {
        this.reservationPeriod = reservationPeriod;
    }

    public String getEstimatedCharges() {
        return estimatedCharges;
    }

    public void setEstimatedCharges(String estimatedCharges) {
        this.estimatedCharges = estimatedCharges;
    }
}
