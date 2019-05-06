package com.example.tayor.karz.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private String documentId;
    private String userId;
    private String firstName;
    private String lastName;
    private License license;
    private String imageUrl;

    public User() {}

    protected User(Parcel in) {
        documentId = in.readString();
        userId = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        license = in.readParcelable(License.class.getClassLoader());
        imageUrl = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public License getLicense() {
        return license;
    }

    public void setLicense(License license) {
        this.license = license;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(documentId);
        dest.writeString(userId);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeParcelable(license, flags);
        dest.writeString(imageUrl);
    }
}
