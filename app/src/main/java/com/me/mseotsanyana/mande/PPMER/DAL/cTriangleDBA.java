package com.me.mseotsanyana.mande.PPMER.DAL;

import android.content.Context;

import com.me.mseotsanyana.mande.PPMER.DAL.impl.cImpactImpl;
import com.me.mseotsanyana.mande.STORAGE.database.cSQLDBHelper;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by mseotsanyana on 2017/07/03.
 */

public class cTriangleDBA {
    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

    // an object of the database helper
    private cSQLDBHelper dbHelper;
    private static final String TAG = "dbHelper";

    private cImpactImpl goalDBA;
    private cSpecificAimDBA specificAimDBA;
    private cObjectiveDBA objectiveDBA;

    public cTriangleDBA(Context context) {
        dbHelper = new cSQLDBHelper(context);

        goalDBA = new cImpactImpl(context);
        specificAimDBA = new cSpecificAimDBA(context);
        objectiveDBA = new cObjectiveDBA(context);
    }
/*
    public List<cTriangleModel> getTriangleList() {

        List<cTriangleModel> triangleModelList = new ArrayList<>();
        triangleModelList.add((cTriangleModel) goalDBA.getGoalList());
        //triangleModelList.add((cTriangleModel) specificAimDBA.g());


        return triangleModelList;
    }

 */
}
