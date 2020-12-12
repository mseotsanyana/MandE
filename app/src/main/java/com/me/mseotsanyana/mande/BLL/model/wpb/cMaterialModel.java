package com.me.mseotsanyana.mande.BLL.model.wpb;

import android.os.Parcel;
import android.os.Parcelable;

import com.me.mseotsanyana.mande.BLL.model.logframe.cInputModel;

public class cMaterialModel extends cInputModel implements Parcelable {
    private int quantity;

    public cMaterialModel(){}

    protected cMaterialModel(Parcel in) {
        quantity = in.readInt();
    }

    public static final Creator<cMaterialModel> CREATOR = new Creator<cMaterialModel>() {
        @Override
        public cMaterialModel createFromParcel(Parcel in) {
            return new cMaterialModel(in);
        }

        @Override
        public cMaterialModel[] newArray(int size) {
            return new cMaterialModel[size];
        }
    };

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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
