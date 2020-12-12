package com.me.mseotsanyana.mande.BLL.model.wpb;

import android.os.Parcel;
import android.os.Parcelable;

import com.me.mseotsanyana.mande.BLL.model.logframe.cInputModel;
import com.me.mseotsanyana.mande.BLL.model.session.cUserModel;

import java.util.HashSet;
import java.util.Set;

public class cHumanModel extends cInputModel implements Parcelable {
    private int quantity;
    private Set<cUserModel> userModelSet;

    public cHumanModel(){
        userModelSet = new HashSet<>();
    }

    protected cHumanModel(Parcel in) {
        quantity = in.readInt();
    }

    public static final Creator<cHumanModel> CREATOR = new Creator<cHumanModel>() {
        @Override
        public cHumanModel createFromParcel(Parcel in) {
            return new cHumanModel(in);
        }

        @Override
        public cHumanModel[] newArray(int size) {
            return new cHumanModel[size];
        }
    };

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Set<cUserModel> getUserModelSet() {
        return userModelSet;
    }

    public void setUserModelSet(Set<cUserModel> userModelSet) {
        this.userModelSet = userModelSet;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(quantity);
    }
}
