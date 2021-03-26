package com.me.mseotsanyana.mande.DAL.ìmpl;

import android.content.res.AssetManager;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

public final class cDatabaseUtils {

    // Private constructor to prevent instantiation
    private cDatabaseUtils() {
        throw new UnsupportedOperationException();
    }

    //public static methods here
    public static String loadJSONFromAsset(String jsonMenu, AssetManager assetManager) {
        String json;
        try {
            //AssetManager assetManager = context.getAssets();
            InputStream is = assetManager.open(jsonMenu);

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        //Log.e(TAG, "Json response " + json);
        return json;
    }

}
