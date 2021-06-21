package com.me.mseotsanyana.mande.DAL.ìmpl.realtime.session;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.me.mseotsanyana.mande.BLL.model.session.cTeamModel;
import com.me.mseotsanyana.mande.BLL.model.session.cUserAccountModel;
import com.me.mseotsanyana.mande.BLL.model.session.cUserProfileModel;
import com.me.mseotsanyana.mande.BLL.repository.session.iTeamRepository;
import com.me.mseotsanyana.mande.DAL.storage.database.cRealtimeHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mseotsanyana on 2017/08/24.
 */

public class cTeamFirebaseRepositoryImpl implements iTeamRepository {
    private static String TAG = cTeamFirebaseRepositoryImpl.class.getSimpleName();

    private final Context context;
    private final FirebaseDatabase database;

    public cTeamFirebaseRepositoryImpl(Context context) {
        this.context = context;
        this.database = FirebaseDatabase.getInstance();
    }

    /* ############################################# READ ACTIONS ############################################# */

    @Override
    public void readTeamsWithMembers(long userServerID, int primaryTeamBIT, int secondaryTeamBITS,
                                     int operationsBITS, int statusBITS, String organizationServerID,
                                     iReadTeamModelCallback callback) {

        DatabaseReference dbTeamsRef;
        dbTeamsRef = database.getReference(cRealtimeHelper.KEY_TEAMS);
        Query teamServerQuery = dbTeamsRef.orderByChild("organizationServerID").equalTo(organizationServerID);

        teamServerQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<cTeamModel, List<cUserProfileModel>> teamMembersMap = new HashMap<>();
                long[] numTeams = {snapshot.getChildrenCount()};

                for(DataSnapshot team: snapshot.getChildren()){
                    cTeamModel teamModel = team.getValue(cTeamModel.class);
                    assert teamModel != null;

                    // TEAM MEMBERS
                    readTeamMembers(teamMembersMap, numTeams, teamModel, callback);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onReadTeamFailed("Failed to read teams: " +
                        error.toException());
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    private void readTeamMembers(Map<cTeamModel, List<cUserProfileModel>> teamMembersMap, long[] numTeams,
                                 cTeamModel teamModel, iReadTeamModelCallback callback){
        DatabaseReference dbTeamMembersRef;
        dbTeamMembersRef = database.getReference(cRealtimeHelper.KEY_TEAM_MEMBERS);
        //assert teamModel != null;

        //Gson gson = new Gson();
        //Log.d(TAG, "<<<<<<<<<<<<=>>>>>>>>>>> "+gson.toJson(teamModel.getCompositeServerID()));
        dbTeamMembersRef.child(teamModel.getCompositeServerID()).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        List<cUserProfileModel> userProfileModels = new ArrayList<>();
                        long[] numUserProfiles = {snapshot.getChildrenCount()};
                        for (DataSnapshot teamMemberID: snapshot.getChildren()){

                            String userAccountServerID = teamMemberID.getKey();

                            // USER ACCOUNT
                            readUserAccounts(teamMembersMap, numTeams, teamModel, userProfileModels,
                                    numUserProfiles, userAccountServerID, callback);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        callback.onReadTeamFailed("Failed to read team members: " +
                                error.toException());
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });
    }

    private void readUserAccounts(Map<cTeamModel, List<cUserProfileModel>> teamMembersMap, long[] numTeams,
                                  cTeamModel teamModel, List<cUserProfileModel> userProfileModels,
                                  long[] numUserProfiles, String userAccountServerID,
                                  iReadTeamModelCallback callback){
        DatabaseReference dbUserAccountsRef;
        dbUserAccountsRef = database.getReference(cRealtimeHelper.KEY_USERACCOUNTS);
        assert userAccountServerID != null;
        dbUserAccountsRef.child(userAccountServerID).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        cUserAccountModel userAccountModel = snapshot.getValue(cUserAccountModel.class);
                        assert userAccountModel != null;
                        // USER PROFILE
                        readUserProfile(teamMembersMap, numTeams, teamModel, userAccountModel,
                                userProfileModels, numUserProfiles, callback);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        callback.onReadTeamFailed("Failed to read user account: " +
                                error.toException());
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });
    }

    private void readUserProfile(Map<cTeamModel, List<cUserProfileModel>> teamMembersMap, long[] numTeams,
                                 cTeamModel teamModel, cUserAccountModel userAccountModel,
                                 List<cUserProfileModel> userProfileModels, long[] numUserProfiles,
                                 iReadTeamModelCallback callback){
        DatabaseReference dbUserProfilesRef;
        dbUserProfilesRef = database.getReference(cRealtimeHelper.KEY_USERPROFILES);
        assert userAccountModel != null;
        dbUserProfilesRef.child(userAccountModel.getUserServerID()).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        cUserProfileModel userProfileModel = snapshot.getValue(cUserProfileModel.class);
                        userProfileModels.add(userProfileModel);

                        /* add all the team and its user profiles to build a map */
                        if (numUserProfiles[0] - 1 == 0) {
                            teamMembersMap.put(teamModel, userProfileModels);
                        } else {
                            numUserProfiles[0] = numUserProfiles[0] - 1;
                        }

                        /* add all the teams with their members in the map before a call back */
                        if (numTeams[0] - 1 == 0) {
                            callback.onReadTeamsWithMembersSucceeded(teamMembersMap);
                        } else {
                            numTeams[0] = numTeams[0] - 1;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        callback.onReadTeamFailed("Failed to read user profile: " +
                                error.toException());
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });
    }
}
