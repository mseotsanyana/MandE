package com.me.mseotsanyana.mande.BRBAC.PL;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.os.PersistableBundle;
import android.util.Log;
import android.util.Pair;

import com.me.mseotsanyana.mande.BRBAC.BLL.cSessionManager;
import com.me.mseotsanyana.mande.BRBAC.BLL.cSyncedJobService;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static com.me.mseotsanyana.mande.UTILITY.cConstant.URL_ADDRESS;
import static com.me.mseotsanyana.mande.UTILITY.cConstant.URL_USER;

/**
 * Created by mseotsanyana on 2018/04/10.
 */

public class cSyncManager {
    private static final String TAG = cSyncManager.class.getSimpleName();

    private final String PACKAGE = "com.me.mseotsanyana.mande.BRBAC.BLL.";
    private String address_url = URL_ADDRESS;
    private String user_url    = URL_USER;

    private cSessionManager session;

    private PersistableBundle extraParams;

    private ArrayList<cSyncData> syncDataList;

    JobScheduler jobScheduler;
    Context context;

    public cSyncManager(Context context) {
        this.context = context;
        this.session = new cSessionManager(context);
    }

    public void startServices(PersistableBundle bundle) {
        int selectedNetworkType = bundle.getInt("selectedNetworkType");
        int selectedInterval = bundle.getInt("selectedInterval");
        int intervalHours = bundle.getInt("intervalHours");
        Boolean deviceIdling = bundle.getBoolean("deviceIdling");
        Boolean deviceCharging = bundle.getBoolean("deviceCharging");
        Boolean persist = bundle.getBoolean("persist");

        // JobScheduler needs at least one constraint to be set.
        boolean constraintSet = selectedNetworkType != JobInfo.NETWORK_TYPE_NONE ||
                deviceIdling || deviceCharging || selectedInterval != 1 || persist;

        if (constraintSet) {
            jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);

            JobInfo.Builder builder = new JobInfo.Builder(0, new ComponentName(context,
                    cSyncedJobService.class));

            builder.setRequiredNetworkType(selectedNetworkType);
            builder.setRequiresDeviceIdle(deviceIdling);
            builder.setRequiresCharging(deviceCharging);
            builder.setPersisted(persist);

            if (selectedInterval != 1) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    builder.setMinimumLatency(TimeUnit.MINUTES.toMillis(intervalHours));
                } else {
                    builder.setPeriodic(TimeUnit.MINUTES.toMillis(intervalHours));
                }
            }

            // adding extra parameters - a list of entities to be synced.
            extraParams  = new PersistableBundle();
            syncDataList = new ArrayList<>();
/*
            int[] addr_sync_status = session.getAddressPermStatusBITS();
            int[] user_sync_status = session.getUserPermStatusBITS();

            if ((addr_sync_status[0] | addr_sync_status[1] | addr_sync_status[2]) != 0) {
                // Adding sync address entity data to the list
                cSyncData addressSyncData = new cSyncData();
                // 1. set user ID
                addressSyncData.setUserID(session.getUserID());
                // 2. set user first group (their institutions)
                addressSyncData.setGroupBIT(session.getGroupBIT());
                // 3. set user second groups (other institutions of interest)
                addressSyncData.setOtherBITS(session.getOtherBITS());
                // 4. set entity ID
                addressSyncData.setEntityID(session.getAddressEntityID());
                // 5. set sync operations for own, group and other categories
                addressSyncData.setSyncPermsBITS(
                        session.getAddressOperationBITS() &
                                (session.permissions[12] |
                                session.permissions[13]  |
                                session.permissions[14]));
                // 6. set array of 3 sync statuses for owner, group and other sync operations
                addressSyncData.setPermStatuses(session.getSyncStatuses(addr_sync_status));
                // 7. set a sync (dirty) status mask
                addressSyncData.setSyncStatusMask(session.statuses[1]);
                // 8. set s delete status mask
                addressSyncData.setDelStatusMask(session.statuses[2]);
                // 8. handler and domain respectively
                Pair<String, String> addressEntityPair = Pair.create(
                        PACKAGE + "cAddressHandler",
                        PACKAGE + "cAddressDomain");
                addressSyncData.setEntity(addressEntityPair);
                // 9. URL for API
                addressSyncData.setUrl(address_url);

                //addressSyncData.setOperations(session.getAddressOperationBITS());
                //addressSyncData.setStatuses(session.getAddressStatusBITS());

                //addressSyncData.setPermsMask(session.permissions);
                //addressSyncData.setStatusMask(session.statuses);

                //addressSyncData.setSyncStatus(session.statuses[1]);
                //addressSyncData.setDelStatus(session.statuses[2]);

                syncDataList.add(addressSyncData);
            }

            if ((user_sync_status[0] | user_sync_status[1] | user_sync_status[2]) != 0) {

                // Adding sync user entity data to the list
                cSyncData userSyncData = new cSyncData();

                userSyncData.setUserID(session.getUserID());
                userSyncData.setGroupBIT(session.getGroupBIT());
                userSyncData.setOtherBITS(session.getOtherBITS());
                userSyncData.setEntityID(session.getUserEntityID());
                // 6. array of statuses for all 15 operations
                userSyncData.setPermStatuses(session.getSyncStatuses(user_sync_status));

                //userSyncData.setOperations(session.getUserOperationBITS());
                //userSyncData.setStatuses(session.getUserStatusBITS());
                Pair<String, String> userEntityPair = Pair.create(
                        PACKAGE + "cUserHandler",
                        PACKAGE + "cUserDomain");
                userSyncData.setEntity(userEntityPair);
                userSyncData.setUrl(user_url);

                //userSyncData.setPermsMask(session.permissions);
                //userSyncData.setStatusMask(session.statuses);

                //userSyncData.setSyncStatus(session.statuses[1]);
                //userSyncData.setDelStatus(session.statuses[2]);

                syncDataList.add(userSyncData);
            }

            Gson gson = new Gson();
            String jsonSyncDataList = gson.toJson(syncDataList);

            extraParams.putString("jsonEntities", jsonSyncDataList);
            builder.setExtras(extraParams);

            int resultCode = jobScheduler.schedule(builder.build());

            if (resultCode == JobScheduler.RESULT_SUCCESS) {
                Log.d(TAG, "The JobService " + cSyncedJobService.class.getSimpleName() +
                        " has been scheduled!");
            } else {
                Log.d(TAG, "The JobService " + cSyncedJobService.class.getSimpleName() +
                        " has not been scheduled!");
            }*/
        }
    }

    public void stopServices() {
        if (jobScheduler != null) {
            jobScheduler.cancelAll();
            jobScheduler = null;
            Log.d(TAG, "All scheduled jobs have been cancelled!");
        }
    }

    public class cSyncData {
        private int userID;          /* ID of loggedIn user                      */
        private int groupBIT;        /* primary group of the user                */
        private int otherBITS;       /* secondary groups of the user             */

        private int entityID;        /* ID of the entity                         */
/*
        private int createPermsBITS; /* owner, group and other create operations
        private int readPermsBITS;   /* owner, group and other read operations
        private int updatePermsBITS; /* owner, group and other update operations
        private int deletePermsBITS; /* owner, group and other delete operations
*/
        private int syncPermsBITS;   /* owner, group and other sync operations   */

        private int[] permStatuses;  /* array of owner, group, & other statuses  */

        private int brbacEntries;    /* list of accessible brbac entities        */
        private int ppmerEntries;    /* list of accessible ppmer entities        */

        //private int operations;      /* bits of permitted operations             */
        //private int statuses;        /* bits of permitted statuses               */

        private Pair<String, String> entity; /* name of handler                  */
                                             /* and domain respectively          */
        private String url ="";

        // permisions (or operation) masks
        //private int[] permsMask;
/*
        private int createMask;
        private int readMask;
        private int updateMask;
        private int deleteMask;
        private int syncMask;
*/
        // status masks
        //private int[] statusMask;

        private int syncStatusMask;
        private int delStatusMask;

        public int getUserID() {
            return userID;
        }

        public void setUserID(int userID) {
            this.userID = userID;
        }

        public int getGroupBIT() {
            return groupBIT;
        }

        public void setGroupBIT(int groupBIT) {
            this.groupBIT = groupBIT;
        }

        public int getOtherBITS() {
            return otherBITS;
        }

        public void setOtherBITS(int otherBITS) {
            this.otherBITS = otherBITS;
        }

        public int getEntityID() {
            return entityID;
        }

        public void setEntityID(int entityID) {
            this.entityID = entityID;
        }

        public int getSyncPermsBITS() {
            return syncPermsBITS;
        }

        public void setSyncPermsBITS(int syncPermsBITS) {
            this.syncPermsBITS = syncPermsBITS;
        }

        public int[] getPermStatuses() {
            return permStatuses;
        }

        public void setPermStatuses(int[] permStatuses) {
            this.permStatuses = permStatuses;
        }

        public int getSyncStatusMask() {
            return syncStatusMask;
        }

        public void setSyncStatusMask(int syncStatusMask) {
            this.syncStatusMask = syncStatusMask;
        }

        public int getDelStatusMask() {
            return delStatusMask;
        }

        public void setDelStatusMask(int delStatusMask) {
            this.delStatusMask = delStatusMask;
        }

        public Pair<String, String> getEntity() {
            return entity;
        }

        public void setEntity(Pair<String, String> entity) {
            this.entity = entity;
        }

        public int getBrbacEntries() {
            return brbacEntries;
        }

        public void setBrbacEntries(int brbacEntries) {
            this.brbacEntries = brbacEntries;
        }

        public int getPpmerEntries() {
            return ppmerEntries;
        }

        public void setPpmerEntries(int ppmerEntries) {
            this.ppmerEntries = ppmerEntries;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}

/*

    public void startAddressJobService(int jobID, PersistableBundle bundle) {
        ComponentName addressJobService = new ComponentName(context, cAddressJobService.class);
        JobInfo.Builder builder = new JobInfo.Builder(jobID, addressJobService);
        builder.setPeriodic(30000);
        builder.setExtras(bundle);

        int resultCode = jobScheduler.schedule(builder.build());

        if (resultCode == JobScheduler.RESULT_SUCCESS) {
            Log.d(TAG, "Address Job scheduled!");
        } else {
            Log.d(TAG, "Address Job not scheduled!");
        }
    }




    ComponentName getUserJobService() {
        ComponentName userJobService = new ComponentName(context, cUserJobService.class);

        return userJobService;
    }

    ComponentName getAddressJobService() {
        ComponentName addressJobService = new ComponentName(context, cAddressJobService.class);
        //PersistableBundle bundle = new PersistableBundle();
        params = new HashMap<String, String>();
        params.put("userID","7");
        params.put("group","0");
        params.put("other","4");
        params.put("perms","4");
        params.put("status","0");

        url = volleyHandler.generateUrl(url, params);
        bundle.putString();

        return addressJobService;
    }
    */

/*
    public void startServices(PersistableBundle bundle) {

        int selectedNetworkType = bundle.getInt("selectedNetworkType");
        int selectedInterval = bundle.getInt("selectedInterval");
        int intervalHours = bundle.getInt("intervalHours");
        Boolean deviceIdling = bundle.getBoolean("deviceIdling");
        Boolean deviceCharging = bundle.getBoolean("deviceCharging");
        Boolean persist = bundle.getBoolean("persist");

        // JobScheduler needs at least one constraint to be set.
        boolean constraintSet = selectedNetworkType != JobInfo.NETWORK_TYPE_NONE ||
                deviceIdling || deviceCharging || selectedInterval != 1 || persist;

        if (constraintSet) {

            services.add(getUserJobService());
            services.add(getAddressJobService());

            jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);

            for (int JOB_ID = 0; JOB_ID < services.size(); JOB_ID++) {
                JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, services.get(JOB_ID));
                builder.setRequiredNetworkType(selectedNetworkType);
                builder.setRequiresDeviceIdle(deviceIdling);
                builder.setRequiresCharging(deviceCharging);
                builder.setPersisted(persist);

                if (selectedInterval != 1) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        builder.setMinimumLatency(TimeUnit.MINUTES.toMillis(intervalHours));
                    } else {
                        builder.setPeriodic(TimeUnit.MINUTES.toMillis(intervalHours));
                    }
                }

                int resultCode = jobScheduler.schedule(builder.build());

                if (resultCode == JobScheduler.RESULT_SUCCESS) {
                    Log.d(TAG, "The JobService "+ services.get(JOB_ID).getClassName() +" has been scheduled!");
                } else {
                    Log.d(TAG, "The JobService "+ services.get(JOB_ID).getClassName() +" has not been scheduled!");
                }
            }

            services.clear();
        }else{
            Toast.makeText(context,"Constraints Error",Toast.LENGTH_SHORT).show();
        }
    }

    public void stopServices() {
        if (jobScheduler != null) {
            jobScheduler.cancelAll();
            jobScheduler = null;
            Log.d(TAG, "All scheduled jobs have been cancelled!");
        }
    }
 */