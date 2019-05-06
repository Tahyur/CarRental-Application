package com.example.tayor.karz.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class License implements Parcelable {
    private String documentId;
    private String licenseNo;
    private String address;
    private String clazz;
    private String expDate;
    private String province;
    private String zip;

    protected License(Parcel in) {
        documentId = in.readString();
        licenseNo = in.readString();
        address = in.readString();
        clazz = in.readString();
        expDate = in.readString();
        province = in.readString();
        zip = in.readString();
    }

    public static final Creator<License> CREATOR = new Creator<License>() {
        @Override
        public License createFromParcel(Parcel in) {
            return new License(in);
        }

        @Override
        public License[] newArray(int size) {
            return new License[size];
        }
    };

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public License() {}

    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(documentId);
        dest.writeString(licenseNo);
        dest.writeString(address);
        dest.writeString(clazz);
        dest.writeString(expDate);
        dest.writeString(province);
        dest.writeString(zip);
    }
}
