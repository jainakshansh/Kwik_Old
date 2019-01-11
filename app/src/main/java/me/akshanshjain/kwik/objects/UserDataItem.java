package me.akshanshjain.kwik.objects;

import android.os.Parcel;
import android.os.Parcelable;

public class UserDataItem implements Parcelable {

    private String userID;
    private String userName;
    private String userEmail;
    private String userPhoneNumber;
    private String userPhotoUrl;

    private UserDataItem() {
    }

    public UserDataItem(String userID, String userName, String userEmail, String userPhoneNumber, String userPhotoUrl) {
        this.userID = userID;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPhoneNumber = userPhoneNumber;
        this.userPhotoUrl = userPhotoUrl;
    }

    protected UserDataItem(Parcel in) {
        userID = in.readString();
        userName = in.readString();
        userEmail = in.readString();
        userPhoneNumber = in.readString();
        userPhotoUrl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userID);
        dest.writeString(userName);
        dest.writeString(userEmail);
        dest.writeString(userPhoneNumber);
        dest.writeString(userPhotoUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserDataItem> CREATOR = new Creator<UserDataItem>() {
        @Override
        public UserDataItem createFromParcel(Parcel in) {
            return new UserDataItem(in);
        }

        @Override
        public UserDataItem[] newArray(int size) {
            return new UserDataItem[size];
        }
    };

    public String getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public String getUserPhotoUrl() {
        return userPhotoUrl;
    }

}
