package com.me.mseotsanyana.mande.DAL.ìmpl.realtime.session;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.me.mseotsanyana.mande.BLL.model.session.cMenuModel;
import com.me.mseotsanyana.mande.BLL.repository.session.iMenuRepository;
import com.me.mseotsanyana.mande.DAL.storage.database.cRealtimeHelper;
import com.me.mseotsanyana.mande.DAL.ìmpl.cDatabaseUtils;
import com.me.mseotsanyana.mande.UTIL.cConstant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;


/**
 * Created by mseotsanyana on 2017/08/24.
 */

public class cMenuFirebaseRepositoryImpl implements iMenuRepository {
    private static SimpleDateFormat sdf = cConstant.FORMAT_DATE;
    private static String TAG = cMenuFirebaseRepositoryImpl.class.getSimpleName();

    private Context context;
    private FirebaseDatabase database;

    public cMenuFirebaseRepositoryImpl(Context context) {
        this.context = context;
        database = FirebaseDatabase.getInstance();
    }

    /* ############################################# READ ACTIONS ############################################# */

//    @Override
//    public void getMenuModels(long userID, int primaryRole, int secondaryRoles, int statusBITS,
//                              String organizationID, String currentUserID,
//                              iReadMenuModelSetCallback callback) {
//
//        if(organizationID != null && currentUserID != null) {
//            DatabaseReference dbUserRolesRef, dbRoleMenuItemsRef;
//            dbUserRolesRef = database.getReference(cRealtimeHelper.KEY_TEAM_ROLES);//FIXME
//            dbRoleMenuItemsRef = database.getReference(cRealtimeHelper.KEY_ROLE_PERMISSIONS);
//
//            Log.d(TAG, " <= OUTSIDE 1 => " + organizationID + "_" + currentUserID);
//
//            dbUserRolesRef.child(organizationID + "_" + currentUserID).addValueEventListener(
//                    new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
//
//                                String roleID = Objects.requireNonNull(ds.getKey());
//
//                                DatabaseReference dbRoleMenuItemRef = dbRoleMenuItemsRef.child(roleID);
//                                dbRoleMenuItemRef.addValueEventListener(new ValueEventListener() {
//                                    @Override
//                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                        Gson gson = new Gson();
//                                        List<cMenuModel> menuModels = new ArrayList<>();
//                                        for (DataSnapshot ds : snapshot.getChildren()) {
//                                            cMenuModel menuModel = ds.getValue(cMenuModel.class);
//                                            menuModels.add(menuModel);
//                                            callback.onReadMenuSucceeded(menuModels);
//                                            //Log.d(TAG, ds.getKey()+" <====> "+gson.toJson(menuModel));
//                                        }
//                                    }
//
//                                    @Override
//                                    public void onCancelled(@NonNull DatabaseError error) {
//
//                                    }
//                                });
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });
//        }else {
//            Gson gson = new Gson();
//            Log.d(TAG, "DEFALT MENU <====> "+gson.toJson(getDefaultMenuModelSet()));
//            callback.onReadMenuSucceeded(getDefaultMenuModelSet());
//        }
//
//    }
//
//    @Override
//    public Set<cMenuModel> getMenuModelSet(long userID, int primaryRole, int secondaryRoles, int statusBITS) {
//        return null;
//    }

    private void getUserRoles(String key){
        DatabaseReference dbReference = database.getReference(cRealtimeHelper.KEY_USERPROFILES);
        //dbReference.child(key).on()
    }
    

    public List<cMenuModel> getDefaultMenuModelSet() {
        List<cMenuModel> menuModels = new ArrayList<>();

        String menu = "jsons/sys_default_menu_items.json";
        String menuJSONString = cDatabaseUtils.loadJSONFromAsset(menu, context.getAssets());

        try {
            assert menuJSONString != null;
            JSONObject jsonObjectMenu = new JSONObject(menuJSONString);
            JSONArray jsonArrayMenu = jsonObjectMenu.getJSONArray("menu");
            for (int i = 0; i < jsonArrayMenu.length(); i++) {
                ArrayList<cMenuModel> subMenuModels = new ArrayList<>();
                JSONObject jsonObject = jsonArrayMenu.getJSONObject(i);
                JSONArray jsonArraySubMenu = jsonObject.getJSONArray("sub_menu");

                for (int j = 0; j < jsonArraySubMenu.length(); j++) {
                    cMenuModel subMenuModel = new cMenuModel();
                    JSONObject jsonObjectSubItem = jsonArraySubMenu.getJSONObject(j);
                    subMenuModel.setMenuServerID(jsonObjectSubItem.getInt("sub_id"));
                    subMenuModel.setName(jsonObjectSubItem.getString("sub_item"));
                    //subMenuModel.setDescription(jsonObjectSubItem.getString("sub_description"));
                    subMenuModels.add(subMenuModel);
                }

                cMenuModel menuModel = new cMenuModel();
                // set the main menu item
                menuModel.setMenuServerID(jsonObject.getInt("id"));
                menuModel.setName(jsonObject.getString("item"));
                //menuModel.setDescription(jsonObject.getString("description"));

                // sub menu items to the main menu item
                menuModel.setSubmenu(subMenuModels);

                menuModels.add(menuModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return menuModels;
    }

    @Override
    public void readMenuPermissions(String organizationServerID, String userServerID,
                                    int primaryTeamBIT, List<Integer> secondaryTeamBITS,
                                    List<Integer> statusBITS,
                                    iReadMenuPermissionsCallback callback) {

    }
}
