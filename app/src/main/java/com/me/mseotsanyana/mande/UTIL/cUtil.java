package com.me.mseotsanyana.mande.UTIL;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import android.text.Layout;
import android.util.Log;
import android.util.TypedValue;


import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.me.mseotsanyana.mande.BLL.domain.session.cRoleDomain;
import com.me.mseotsanyana.mande.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by mseotsanyana on 2017/10/03.
 */

public class cUtil {

    static public List<String> removeDuplicate(List<String> list){
        Set<String> set = new HashSet<String>(list);
        List<String> set_list = new ArrayList<String>(set);
        return set_list;
    }

    /// <summary>
    /// Enable or disable shift mode on bottom navigation view
    /// </summary>
    /// <param name="bottomNavigationView"></param>
    /// <param name="enabled"></param>

    public static void setShiftMode(BottomNavigationView bottomNavigationView, boolean enableShiftMode, boolean enableItemShiftMode) {
        try {
            BottomNavigationView menuView = (BottomNavigationView) bottomNavigationView.getChildAt(0);
            if (menuView == null) {
                Log.e("Util: ","Unable to find BottomNavigationMenuView");
                return;
            }


            Field shiftMode = menuView.getClass().getDeclaredField("mShiftingMode");

            shiftMode.setAccessible(true);
            shiftMode.setBoolean(menuView, enableShiftMode);
            shiftMode.setAccessible(false);
            //shiftMode.Dispose();


            for(int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView)menuView.getChildAt(i);
                if (item == null)
                    continue;


                //--item.setShiftingMode(enableItemShiftMode);
                //--item.setChecked(item.getItemData().isChecked());

            }

            menuView.refreshDrawableState();// .updateMenuView();
        }
        catch (Exception ex) {
            Log.e("Util: ","Unable to set shift mode = "+ex.getMessage());

        }
    }

    public static void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                //noinspection RestrictedApi
                //--item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                //noinspection RestrictedApi
                //--item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("BNVHelper", "Unable to get shift mode field", e);
        } catch (IllegalAccessException e) {
            Log.e("BNVHelper", "Unable to change value of shift mode", e);
        }
    }

    public static void setIcon(Context context, BottomNavigationView view, int position){
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            for (int i = 0; i < menuView.getChildCount(); i++) {
                TextDrawable faIcon = new TextDrawable(context);

                faIcon.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                faIcon.setTextAlign(Layout.Alignment.ALIGN_CENTER);
                faIcon.setTypeface(cFontManager.getTypeface(context, cFontManager.FONTAWESOME));

                if (i == 0) {
                    faIcon.setText(context.getResources().getText(R.string.fa_login));
                    faIcon.setTextColor(Color.BLUE);
                }

                if (i == 1){
                    faIcon.setText(context.getResources().getText(R.string.fa_create));
                }

                if (i == 2){
                    faIcon.setText(context.getResources().getText(R.string.fa_join));
                }

                if (i == 3){
                    faIcon.setText(context.getResources().getText(R.string.fa_setting));
                }

                if (position == i){
                    faIcon.setTextColor(Color.BLUE);
                }else {
                    faIcon.setTextColor(Color.BLACK);
                }

                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                //--item.setIcon(faIcon);
                //noinspection RestrictedApi
                ///item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                //noinspection RestrictedApi
                //item.setChecked(item.getItemData().isChecked());
            }
        } catch (Exception e) {
            Log.e("BNVHelper", "Unable to modify icon", e);
        }
    }

    public static int sumRoleDomainIDs(ArrayList<cRoleDomain> roleDomains){
        int sum = 0;
        for (int i = 0; i < roleDomains.size(); i++){
            sum = sum + roleDomains.get(i).getRoleID();
        }

        return sum;
    }


    // check permission for reading external storage
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean checkPermission(final Context context)
    {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if(currentAPIVersion>=android.os.Build.VERSION_CODES.M)
        {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("External storage permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();

                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }
}
