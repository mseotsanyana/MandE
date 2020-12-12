package com.me.mseotsanyana.mande.BLL.model.wpb;

import android.os.Parcel;
import android.os.Parcelable;

import com.me.mseotsanyana.mande.BLL.model.logframe.cInputModel;

public class cExpenseModel extends cInputModel implements Parcelable {
    private double expense;

    public cExpenseModel(){}

    protected cExpenseModel(Parcel in) {
        expense = in.readDouble();
    }

    public static final Creator<cExpenseModel> CREATOR = new Creator<cExpenseModel>() {
        @Override
        public cExpenseModel createFromParcel(Parcel in) {
            return new cExpenseModel(in);
        }

        @Override
        public cExpenseModel[] newArray(int size) {
            return new cExpenseModel[size];
        }
    };

    public double getExpense() {
        return expense;
    }

    public void setExpense(double expense) {
        this.expense = expense;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(expense);
    }
}
