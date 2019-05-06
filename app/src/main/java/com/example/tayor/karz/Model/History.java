package com.example.tayor.karz.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class History implements Parcelable {
    private Reservation reservation;
    private String documentId;
    private String mileageReturned;
    private String finalCharge;

    protected History(Parcel in) {
        reservation = in.readParcelable(Reservation.class.getClassLoader());
        documentId = in.readString();
        mileageReturned = in.readString();
        finalCharge = in.readString();
    }

    public static final Creator<History> CREATOR = new Creator<History>() {
        @Override
        public History createFromParcel(Parcel in) {
            return new History(in);
        }

        @Override
        public History[] newArray(int size) {
            return new History[size];
        }
    };

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getMileageReturned() {
        return mileageReturned;
    }

    public void setMileageReturned(String mileageReturned) {
        this.mileageReturned = mileageReturned;
    }

    public String getFinalCharge() {
        return finalCharge;
    }

    public void setFinalCharge(String finalCharge) {
        this.finalCharge = finalCharge;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(reservation, flags);
        dest.writeString(documentId);
        dest.writeString(mileageReturned);
        dest.writeString(finalCharge);
    }
}
