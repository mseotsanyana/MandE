package com.me.mseotsanyana.mande.BLL.model.wpb;

import android.os.Parcel;
import android.os.Parcelable;

import com.me.mseotsanyana.mande.BLL.model.logframe.cInputModel;
import com.me.mseotsanyana.mande.BLL.model.session.cFunderModel;

import java.util.HashSet;
import java.util.Set;

public class cIncomeModel extends cInputModel implements Parcelable {
    /* information from table fund */
    private Set<cFunderModel> funderModelSet;

    public cIncomeModel(){
        funderModelSet = new HashSet<>();
    }

    protected cIncomeModel(Parcel in) {
    }

    public static final Creator<cIncomeModel> CREATOR = new Creator<cIncomeModel>() {
        @Override
        public cIncomeModel createFromParcel(Parcel in) {
            return new cIncomeModel(in);
        }

        @Override
        public cIncomeModel[] newArray(int size) {
            return new cIncomeModel[size];
        }
    };

    public Set<cFunderModel> getFunderModelSet() {
        return funderModelSet;
    }

    public void setFunderModelSet(Set<cFunderModel> funderModelSet) {
        this.funderModelSet = funderModelSet;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}
