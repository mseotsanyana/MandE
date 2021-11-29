package com.me.mseotsanyana.mande.PL.ui;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetManager;
import android.net.Uri;
import android.webkit.MimeTypeMap;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Created by Aki on 1/7/2017.
 */


public class cViewUtils {

    public static String loadImageFromAsset(String assetPath, AssetManager assetManager) {
        String imageFile;
        try {
            InputStream is = assetManager.open(assetPath);

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            imageFile = new String(buffer, StandardCharsets.UTF_8);

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        //Log.e(TAG, "Json response " + json);
        return imageFile;
    }


    private static String getFileExtension(Uri uri, Context context) {
        ContentResolver cR = context.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
}