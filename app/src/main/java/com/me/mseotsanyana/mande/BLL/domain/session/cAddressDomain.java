package com.me.mseotsanyana.mande.BLL.domain.session;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by mseotsanyana on 2018/01/30.
 */

public class cAddressDomain implements Parcelable {
    //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    //private static String TAG = cAddressHandler.class.getSimpleName();

    private int addressID;
    private int serverID;
    private int ownerID;
    private int groupBITS;
    private int permsBITS;
    private int statusBITS;
    private String street;
    private String city;
    private String province;
    private String postalCode;
    private String country;
    private Date createdDate;
    private Date modifiedDate;
    private Date syncedDate;

    public cAddressDomain(){
        this.statusBITS = 0;
    }

    protected cAddressDomain(Parcel in) {
        this.setAddressID(in.readInt());
        this.setServerID(in.readInt());
        this.setOwnerID(in.readInt());
        this.setGroupBITS(in.readInt());
        this.setPermsBITS(in.readInt());
        this.setStatusBITS(in.readInt());
        this.setStreet(in.readString());
        this.setCity(in.readString());
        this.setProvince(in.readString());
        this.setPostalCode(in.readString());
        this.setCountry(in.readString());
        this.setCreatedDate(new Date(in.readLong()));
        this.setModifiedDate(new Date(in.readLong()));
        this.setSyncedDate(new Date(in.readLong()));
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.getAddressID());
        out.writeInt(this.getServerID());
        out.writeInt(this.getOwnerID());
        out.writeInt(this.getGroupBITS());
        out.writeInt(this.getPermsBITS());
        out.writeInt(this.getStatusBITS());
        out.writeString(this.getStreet());
        out.writeString(this.getCity());
        out.writeString(this.getProvince());
        out.writeString(this.getPostalCode());
        out.writeString(this.getCountry());
        out.writeLong(this.getCreatedDate().getTime());
        out.writeLong(this.getModifiedDate().getTime());
        out.writeLong(this.getSyncedDate().getTime());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<cAddressDomain> CREATOR = new Creator<cAddressDomain>() {
        @Override
        public cAddressDomain createFromParcel(Parcel in) {
            return new cAddressDomain(in);
        }

        @Override
        public cAddressDomain[] newArray(int size) {
            return new cAddressDomain[size];
        }
    };

    public int getAddressID() {
        return addressID;
    }

    public void setAddressID(int addressID) {
        this.addressID = addressID;
    }

    public int getServerID() {
        return serverID;
    }

    public void setServerID(int serverID) {
        this.serverID = serverID;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public int getGroupBITS() {
        return groupBITS;
    }

    public void setGroupBITS(int groupBITS) {
        this.groupBITS = groupBITS;
    }

    public int getPermsBITS() {
        return permsBITS;
    }

    public void setPermsBITS(int permsBITS) {
        this.permsBITS = permsBITS;
    }

    public int getStatusBITS() {
        return statusBITS;
    }

    public void setStatusBITS(int statusBITS) {
        this.statusBITS = statusBITS;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        //Log.d(TAG,"createdDate = "+createdDate);

        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Date getSyncedDate() {
        return syncedDate;
    }

    public void setSyncedDate(Date syncedDate) {
        this.syncedDate = syncedDate;
    }
}
