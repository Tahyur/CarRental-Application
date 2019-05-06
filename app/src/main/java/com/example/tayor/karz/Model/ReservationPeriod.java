package com.example.tayor.karz.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class ReservationPeriod implements Parcelable {
    private String documentId;
    private String reservationId;
    private String StartDate;
    private String StartTime;
    private String EndDate;
    private String EndTime;
    private String Status;

    protected ReservationPeriod(Parcel in) {
        documentId = in.readString();
        reservationId = in.readString();
        StartDate = in.readString();
        StartTime = in.readString();
        EndDate = in.readString();
        EndTime = in.readString();
        Status = in.readString();
    }

    public static final Creator<ReservationPeriod> CREATOR = new Creator<ReservationPeriod>() {
        @Override
        public ReservationPeriod createFromParcel(Parcel in) {
            return new ReservationPeriod(in);
        }

        @Override
        public ReservationPeriod[] newArray(int size) {
            return new ReservationPeriod[size];
        }
    };

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getReservationId() {
        return reservationId;
    }

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(documentId);
        dest.writeString(reservationId);
        dest.writeString(StartDate);
        dest.writeString(StartTime);
        dest.writeString(EndDate);
        dest.writeString(EndTime);
        dest.writeString(Status);
    }
}
