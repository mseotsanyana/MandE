package com.me.mseotsanyana.mande.DAL.ìmpl.realtime.session;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.me.mseotsanyana.mande.BLL.model.session.cRoleModel;
import com.me.mseotsanyana.mande.BLL.model.session.cUserAccountModel;
import com.me.mseotsanyana.mande.BLL.model.session.cUserProfileModel;
import com.me.mseotsanyana.mande.BLL.repository.session.iRoleRepository;
import com.me.mseotsanyana.mande.DAL.storage.database.cRealtimeHelper;
import com.me.mseotsanyana.mande.UTIL.cConstant;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mseotsanyana on 2017/08/24.
 */

public class cRoleFirebaseRepositoryImpl implements iRoleRepository {
    private static SimpleDateFormat sdf = cConstant.FORMAT_DATE;
    private static String TAG = cRoleFirebaseRepositoryImpl.class.getSimpleName();

    private Context context;
    private FirebaseDatabase database;

    public cRoleFirebaseRepositoryImpl(Context context) {
        this.context = context;
        database = FirebaseDatabase.getInstance();
    }

    /* ############################################# READ ACTIONS ############################################# */

    @Override
    public void readRoleUsers(List<cRoleModel> roleModels, int primaryTeamBIT, int secondaryTeamBITS,
                              int statusBITS, iRoleRepository.iReadRoleModelSetCallback callback) {

        for (cRoleModel roleModel : roleModels) {
            DatabaseReference dbRoleUserAccountRef;
            dbRoleUserAccountRef = database.getReference(cRealtimeHelper.KEY_ROLE_TEAMS);//FIXME

            dbRoleUserAccountRef.child(roleModel.getRoleServerID()).addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            List<String> userAccountServerIDs = new ArrayList<>();
                            for (DataSnapshot userAccount : snapshot.getChildren()) {
                                String userAccountServerID = userAccount.getValue(String.class);
                                userAccountServerIDs.add(userAccountServerID);
                            }

                            readUserAccounts(roleModel, userAccountServerIDs);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
        }
    }

    private void readUserAccounts(cRoleModel roleModel, List<String> userAccountServerIDs) {
        DatabaseReference dbUserAccountRef;
        dbUserAccountRef = database.getReference(cRealtimeHelper.KEY_USERACCOUNTS);

        List<cUserProfileModel> userProfileModels = new ArrayList<>();
        for (String userAccountServerID : userAccountServerIDs) {
            dbUserAccountRef.child(userAccountServerID).addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            cUserAccountModel userAccountModel = snapshot.getValue(cUserAccountModel.class);

                            assert userAccountModel != null;
                            readUserProfile(userProfileModels, userAccountModel);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
        }

        //FIXME: call back roleUsers(roleModel, userProfileModels);

    }

    private void readUserProfile(List<cUserProfileModel> userProfileModels, cUserAccountModel userAccountModel) {
        DatabaseReference dbUserProfileRef;
        dbUserProfileRef = database.getReference(cRealtimeHelper.KEY_USERPROFILES);

        dbUserProfileRef.child(userAccountModel.getUserServerID()).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        cUserProfileModel userProfileModel = snapshot.getValue(cUserProfileModel.class);
                        userProfileModels.add(userProfileModel);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}



