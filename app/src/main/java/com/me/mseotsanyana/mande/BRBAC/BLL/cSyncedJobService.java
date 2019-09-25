package com.me.mseotsanyana.mande.BRBAC.BLL;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.me.mseotsanyana.mande.BRBAC.PL.cSyncManager;
import com.me.mseotsanyana.mande.COM.cVolleyHandler;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mseotsanyana on 2018/04/05.
 */

public class cSyncedJobService extends JobService {
    private static final String TAG = cSyncedJobService.class.getSimpleName();
    private final String date_format = "yyyy-MM-dd HH:mm:ss";

    private SimpleDateFormat sdf = new SimpleDateFormat(date_format);
    private Class[] context = new Class[]{Context.class};

    private boolean isWorking = false;
    private boolean jobCancelled = false;

    private cVolleyHandler volleyHandler;
    private ArrayList<cSyncManager.cSyncData> entities;
    private Map<String, String> params;
    private Type type;
    private Gson gson;

    public static boolean isGetter(Method method) {
        if (!method.getName().startsWith("get"))
            return false;
        if (method.getParameterTypes().length != 0)
            return false;
        if (void.class.equals(method.getReturnType()))
            return false;
        return true;
    }

    public static boolean isSetter(Method method) {
        if (!method.getName().startsWith("set"))
            return false;
        if (method.getParameterTypes().length != 1)
            return false;
        return true;
    }

    public static boolean isMatch(Method method, Field field) {
        if (!method.getName().toUpperCase().endsWith(field.getName().toUpperCase()))
            return false;
        return true;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        volleyHandler = new cVolleyHandler(getApplicationContext());
        entities = new ArrayList<cSyncManager.cSyncData>();
        params = new HashMap<>();
        type = new TypeToken<ArrayList<cSyncManager.cSyncData>>() {
        }.getType();
        gson = new GsonBuilder().setDateFormat(date_format).create();
    }

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.d(TAG, "The JobService has started!");

        isWorking = true;

        // We need 'jobParameters' so we can call 'jobFinished'
        syncThread(jobParameters); // Services do NOT run on a separate thread

        return isWorking;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.d(TAG, "The JobService has completed.");

        jobCancelled = true;
        boolean needsReschedule = isWorking;
        jobFinished(jobParameters, needsReschedule);

        return needsReschedule;
    }


    private long getMaxSyncedDate(JSONArray jsonDomains) {
        Date date = null;
        try {
            JSONObject jsonDomainSD = (JSONObject) jsonDomains.get(0);

            date = sdf.parse(jsonDomainSD.getString("syncedDate"));

            for (int j = 1; j < jsonDomains.length(); j++) {
                jsonDomainSD = (JSONObject) jsonDomains.get(j);
                Date nexDate = sdf.parse(jsonDomainSD.getString("syncedDate"));
                if (date.before(nexDate)) {
                    date = nexDate;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "Max Synced Date -> " + date);
        Log.d(TAG, "Max Synced Date toString  -> " + date.toString());

        long timestamp = date.getTime() / 1000;
        Log.d(TAG, "Max Synced Timestamp as a Number -> " + timestamp);

        return timestamp;
    }

    private void syncThread(final JobParameters jobParameters) {
        new Thread(new Runnable() {
            public void run() {
                // start of synchronization algorithm
                /* == DOWNLOAD FROM THE SERVER TO UPDATE THE CLIENT == */

                /*** 1. get unsynced entity data from the local database ***/

                String jsonEntities = jobParameters.getExtras().getString("jsonEntities");
                Log.d(TAG, "SYNC DATA: " + jsonEntities);

                /*** 1.1 list of entities to sync ***/
                entities = gson.fromJson(jsonEntities, type);

                // iterate through the list of entities to sync
                for (int i = 0; i < entities.size(); i++) {
                    try {
                        // instantiating the handler object
                        Class handler = Class.forName(entities.get(i).getEntity().first);
                        Constructor handlerCtor = handler.getConstructor(context);
                        Object handlerObj = handlerCtor.newInstance(getApplicationContext());

                        // instantiating the domain object
                        Class domain = Class.forName(entities.get(i).getEntity().second);
                        Constructor domainCtor = domain.getConstructor();
                        //Object domainObj       = domainCtor.newInstance();

                        // get the entity handler method to retrieve data to sync
                        Method handlerEntity = handler.getDeclaredMethod("getEntities",
                                int.class, int.class, int.class, int.class, int[].class);

                        //int[] allPermsMasks = entities.get(i).getPermsMask();
                        //int[] allStatusMasks = entities.get(i).getStatusMask();

                        // list of domains to sync
                        ArrayList<Object> domainObjects = (ArrayList<Object>) handlerEntity.invoke(handlerObj,
                                entities.get(i).getUserID(),
                                entities.get(i).getGroupBIT(),
                                entities.get(i).getOtherBITS(),
                                entities.get(i).getSyncPermsBITS(),
                                entities.get(i).getPermStatuses());

                        JSONArray jsonClientDomains = new JSONArray(gson.toJson(domainObjects));


                        Log.d(TAG, "JSON Domain Objects to Sync -> " + jsonClientDomains);

                        if (jsonClientDomains.length() > 0) {

                            /*** 1.2. get the maximum synced date to filter the latest server records ***/
                            long timestamp = getMaxSyncedDate(jsonClientDomains);

                            /*** 1.3. prepare parameters to filter records to sync from the remote server ***/
                            params = new HashMap<>();
                            int[] permStatuses = entities.get(i).getPermStatuses();
                            int ownerPermStatuses = permStatuses[0];
                            int groupPermStatuses = permStatuses[1];
                            int otherPermStatuses = permStatuses[2];

                            params.put("userID", Integer.toString(entities.get(i).getUserID()));
                            params.put("groupBIT", Integer.toString(entities.get(i).getGroupBIT()));
                            params.put("otherBITS", Integer.toString(entities.get(i).getOtherBITS()));
                            params.put("syncPermsBITS", Integer.toString(entities.get(i).getSyncPermsBITS()));
                            params.put("ownerStatusBITS", Integer.toString(ownerPermStatuses));
                            params.put("groupStatusBITS", Integer.toString(groupPermStatuses));
                            params.put("otherStatusBITS", Integer.toString(otherPermStatuses));
                            params.put("syncedDate", Long.toString(timestamp));

                            // construct the corresponding URL
                            String url = volleyHandler.buildURL(entities.get(i).getUrl(), params);

                            Log.d(TAG, "userID              -> " + entities.get(i).getUserID());
                            Log.d(TAG, "groupBIT            -> " + entities.get(i).getGroupBIT());
                            Log.d(TAG, "otherBITS           -> " + entities.get(i).getOtherBITS());
                            Log.d(TAG, "permsBITS           -> " + entities.get(i).getSyncPermsBITS());
                            Log.d(TAG, "ownerPermStatusBITS -> " + ownerPermStatuses);
                            Log.d(TAG, "groupPermStatusBITS -> " + groupPermStatuses);
                            Log.d(TAG, "otherPermStatusBITS -> " + otherPermStatuses);
                            Log.d(TAG, "url                 -> " + url);
                            Log.d(TAG, "Timestamp           -> " + timestamp);

                            /*** 1.4. get the latest data from remote database ***/

                            cVolleyHandler.cVolleyResponse<JSONObject> response =
                                    volleyHandler.syncAPI(
                                            url,
                                            TAG,
                                            Request.Method.GET,
                                            null);

                            Log.d(TAG, "Response from the Remote Server -> " + response.getResponse());

                            /*** 1.5 reading the JSONObject response from the server ***/

                            JSONObject jsonServerResponse = response.getResponse();

                            if (jsonServerResponse.getBoolean("response")) {

                                JSONArray jsonServerDomains = (JSONArray) jsonServerResponse.get("entities");
                                JSONObject clientDomain = null;
                                JSONObject serverDomain = null;

                                int localServerID = 0, remoteServerID = 0;

                                for (int j = 0; j < jsonServerDomains.length(); j++) {

                                    JSONObject jsonServerDomain = (JSONObject) jsonServerDomains.get(j);
                                    remoteServerID = jsonServerDomain.getInt("serverID");

                                    for (int k = 0; k < jsonClientDomains.length(); k++) {

                                        JSONObject jsonClientDomain = (JSONObject) jsonClientDomains.get(k);
                                        localServerID = jsonClientDomain.getInt("serverID");

                                        if (remoteServerID == localServerID) {
                                            clientDomain = jsonClientDomain;
                                            serverDomain = jsonServerDomain;
                                        }
                                    }

                                    Log.d(TAG, "JSON Client Domain Objects to Sync -> " + clientDomain);
                                    Log.d(TAG, "JSON Server Domain Objects to Sync -> " + serverDomain);

                                    // does the server record already exist in the client database?
                                    if ((clientDomain != null) && (serverDomain != null) &&
                                            (clientDomain.getInt("serverID") != 0)) {

                                        // fields of the client that need to be updated
                                        Date clientSyncedDate = null;
                                        Date serverSyncedDate = null;
                                        try {
                                            serverSyncedDate = sdf.parse(serverDomain.getString("syncedDate"));
                                            clientSyncedDate = sdf.parse(clientDomain.getString("syncedDate"));
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }

                                        int syncStatusMask = entities.get(i).getSyncStatusMask();
                                        // is the server record modified by the client?
                                        if ((clientDomain.getInt("statusBITS") & syncStatusMask) != 0) {
                                            // conflict resolution:
                                            // if the local record was modified after the server object was synced,
                                            // update with the server record
                                            if (clientSyncedDate.before(serverSyncedDate)) {

                                                Method updateEntity = handler.getDeclaredMethod("updateEntity",
                                                        new Class[]{JSONObject.class});
                                                boolean isUpdated = (boolean) updateEntity.invoke(handlerObj,
                                                        new Object[]{serverDomain});

                                                if (isUpdated) {
                                                    Log.d(TAG, "Conflict: Updated with the Server Record");
                                                } else {
                                                    Log.d(TAG, "Conflict: Failed to Update with the Server Record");
                                                }

                                            } else {

                                                Method updateEntity = handler.getDeclaredMethod("updateEntity",
                                                        new Class[]{JSONObject.class});

                                                boolean isUpdated = (boolean) updateEntity.invoke(handlerObj,
                                                        new Object[]{clientDomain});

                                                if (isUpdated) {
                                                    Log.d(TAG, "Conflict: Updated with the Client Record");
                                                } else {
                                                    Log.d(TAG, "Conflict: Failed to Update with the Client Record");
                                                }
                                            }
                                        }
                                        // reset
                                        clientDomain = null;
                                        serverDomain = null;
                                    // the server record doesn't exist in the client's database
                                    } else {
                                        Method createEntity = handler.getDeclaredMethod("createEntity",
                                                new Class[]{JSONObject.class});
                                        long isCreated = (long) createEntity.invoke(handlerObj,
                                                new Object[]{jsonServerDomain});

                                        if (isCreated > 0) {
                                            Log.d(TAG, "Created with the Server Record");
                                        } else {
                                            Log.d(TAG, "Failed to Create with the Server Record");
                                        }
                                    }
                                }
                            } else {
                                Log.d(TAG, "There are no records from the server to sync");
                            }

            /* == EXTRACT FROM THE CLIENT TO UPLOAD TO THE SERVER ==
            /* 2. prepare local records to be created, updated and deleted from the server */

                            ArrayList<JSONObject> records_create = new ArrayList<>();
                            ArrayList<JSONObject> records_update = new ArrayList<>();
                            ArrayList<JSONObject> records_delete = new ArrayList<>();
                            ArrayList<JSONObject> records_purge = new ArrayList<>();

                            // bits variables which holds the statuses of sync and delete permissions

                            // get status mask from "BitwisePermission" class
                            //int[] statusMask = entities.get(i).getStatusMask();
                            //Log.d(TAG, "LoggedIn User StatusMasks -> " + statusMask);

                            for (int l = 0; l < jsonClientDomains.length(); l++) {
                                //Log.d(TAG, "JSONObject   -> " + (gson.toJson(syncEntityDomains.get(i))));

                                JSONObject jsonClientDomain = (JSONObject) jsonClientDomains.get(l);

                                //Log.d(TAG, "JSONObject Modified   -> " + jsonDomain);
                                int tmp_client_status = jsonClientDomain.getInt("statusBITS");
                                // the record has been modified
                                if ((tmp_client_status & entities.get(i).getSyncStatusMask()) != 0) {

                                    if (jsonClientDomain.getInt("serverID") == 0) {
                                        // CREATE = POST
                                        records_create.add(jsonClientDomain);
                                    } else {
                                        // UPDATE = PUT
                                        records_update.add(jsonClientDomain);
                                    }

                                    if ((tmp_client_status & entities.get(i).getDelStatusMask()) != 0) {
                                        // DELETE = DELETE
                                        records_delete.add(jsonClientDomain);
                                    }
                                }
                            }

                            Log.d(TAG, "Created Records -> " + records_create);
                            Log.d(TAG, "Updated Records -> " + records_update);
                            Log.d(TAG, "Deleted Records -> " + records_delete);

                            /*** 2.1 post (or create) the new local records in the server ***/
                            for (int li = 0; li < records_create.size(); li++) {
                                // reset the sync status
                                int tmp_sync = (records_create.get(li).getInt("statusBITS") &
                                        ~entities.get(i).getSyncStatusMask());
                                records_create.get(li).put("statusBITS", tmp_sync);

                                // build a CREATE json request
                                JSONObject request = new JSONObject();
                                request.put("operation", "CREATE");
                                request.put("record", records_create.get(li));

                                // create the record in the server
                                cVolleyHandler.cVolleyResponse<JSONObject> create_response =
                                        volleyHandler.syncAPI(
                                                url,
                                                TAG,
                                                Request.Method.POST,
                                                request);

                                JSONObject res_results = create_response.getResponse();
                                Log.d(TAG, "POST Response from the server after creating the record -> " + res_results);

                                if (res_results.getBoolean("response")) {
                                    // the serverID and Synced date are updated by the server
                                    records_create.get(li).put("serverID", res_results.getInt("serverID"));
                                    records_create.get(li).put("syncedDate", res_results.getString("syncedDate"));

                                    // update the client record to complete the synced circle
                                    Method createEntity = handler.getDeclaredMethod("createEntity",
                                            new Class[]{JSONObject.class});

                                    long isCreated = (long) createEntity.invoke(handlerObj,
                                            new Object[]{records_create.get(li)});

                                    if (isCreated > 0) {
                                        Log.d(TAG, "Updated the client's record to complete the sync circle");
                                    } else {
                                        Log.d(TAG, "Failed to update the client's record to complete sync circle");
                                    }
                                }else{
                                    Log.d(TAG, "Received a fail message from the server when trying to CREATE a record");
                                }
                            }


                            /*** 2.2 put (or update) the modified local records in the server ***/
                            for (int lj = 0; lj < records_update.size(); lj++) {
                                // build a UPDATE json request
                                JSONObject request = new JSONObject();
                                JSONObject privileges = new JSONObject();

                                int[] status = entities.get(i).getPermStatuses();
                                privileges.put("userID", entities.get(i).getUserID());
                                privileges.put("groupBIT", entities.get(i).getGroupBIT());
                                privileges.put("otherBITS", entities.get(i).getOtherBITS());
                                privileges.put("syncPermsBITS", entities.get(i).getSyncPermsBITS());
                                privileges.put("ownerStatusBITS", status[0]);
                                privileges.put("groupStatusBITS", status[1]);
                                privileges.put("otherStatusBITS", status[2]);

                                request.put("operation", "UPDATE");
                                request.put("privileges", privileges);
                                request.put("record", records_update.get(lj));

                                // update the record in the server
                                cVolleyHandler.cVolleyResponse<JSONObject> update_response =
                                        volleyHandler.syncAPI(
                                                url,
                                                TAG,
                                                Request.Method.POST,
                                                request);

                                JSONObject res_results = update_response.getResponse();
                                Log.d(TAG, "POST Response from the server after updating the record -> " + res_results);

                                if (res_results.getBoolean("response")) {
                                    // the serverID and Synced date are updated by the server
                                    records_update.get(lj).put("serverID", res_results.getInt("serverID"));
                                    records_update.get(lj).put("syncedDate", res_results.getString("syncedDate"));

                                    // update the client record to complete the synced circle
                                    Method updateEntity = handler.getDeclaredMethod("updateEntity",
                                            new Class[]{JSONObject.class});

                                    boolean isUpdated = (boolean) updateEntity.invoke(handlerObj,
                                            new Object[]{records_update.get(lj)});

                                    if (isUpdated) {
                                        Log.d(TAG, "Updated the client's record to complete the sync circle");
                                    } else {
                                        Log.d(TAG, "Failed to update the client's record to complete sync circle");
                                    }
                                }else{
                                    Log.d(TAG, "Received a fail message from the server when trying to UPDATE a record");
                                }
                            }
                            //Log.d(TAG, "PUT data to the Server      -> " + gson.toJson(records_update));

                            /*** 2.3 delete the locally deleted records from the server ***/
                            for (int lk = 0; lk < records_delete.size(); lk++) {
                                // align the client del status with the server del status
                                int tmp_del = (records_delete.get(lk).getInt("statusBITS") &
                                        entities.get(i).getDelStatusMask());
                                records_delete.get(lk).put("statusBITS", tmp_del);

                                // build a DELETE json request
                                JSONObject request = new JSONObject();
                                JSONObject privileges = new JSONObject();
                                JSONObject record = new JSONObject();

                                int[] status = entities.get(i).getPermStatuses();
                                privileges.put("userID", entities.get(i).getUserID());
                                privileges.put("groupBIT", entities.get(i).getGroupBIT());
                                privileges.put("otherBITS", entities.get(i).getOtherBITS());
                                privileges.put("syncPermsBITS", entities.get(i).getSyncPermsBITS());
                                privileges.put("ownerStatusBITS", status[0]);
                                privileges.put("groupStatusBITS", status[1]);
                                privileges.put("otherStatusBITS", status[2]);

                                record.put("serverID", records_delete.get(lk).getInt("serverID"));
                                record.put("statusBITS", (records_delete.get(lk).getInt("statusBITS") |
                                        entities.get(i).getDelStatusMask()));

                                request.put("operation", "DELETE");
                                request.put("privileges", privileges);
                                request.put("record", record);

                                // create the record in the server
                                cVolleyHandler.cVolleyResponse<JSONObject> delete_response =
                                        volleyHandler.syncAPI(
                                                url,
                                                TAG,
                                                Request.Method.POST,
                                                request);

                                JSONObject res_results = delete_response.getResponse();
                                Log.d(TAG, "POST Response from the server after creating the record?? -> " + res_results);

                                if (res_results.getBoolean("response")) {
                                    // the serverID, statusBITS and Synced date are deleted by the server
                                    JSONObject jsonRecord = (JSONObject) res_results.get("record");
                                    records_delete.get(lk).put("serverID", jsonRecord.getString("serverID"));
                                    records_delete.get(lk).put("statusBITS", jsonRecord.getString("statusBITS"));
                                    records_delete.get(lk).put("syncedDate", jsonRecord.getString("syncedDate"));

                                    // delete the client record to complete the synced circle
                                    Method deleteEntity = handler.getDeclaredMethod("deleteEntity",
                                            new Class[]{JSONObject.class});

                                    boolean isDeleted = (boolean) deleteEntity.invoke(handlerObj,
                                            new Object[]{records_delete.get(lk)});

                                    if (isDeleted) {
                                        Log.d(TAG, "Updated (for delete) the client's record to complete the sync circle");
                                    } else {
                                        Log.d(TAG, "Failed to update (for delete) the client's record to complete sync circle");
                                    }
                                }else{
                                    Log.d(TAG, "Received a fail message from the server when trying to DELETE a record");
                                }
                            }
                            //Log.d(TAG, "DELETE data from the Server -> " + gson.toJson(records_delete));
                        }
                        //Log.d(TAG, "Server Entity Object -> " + gson.toJson(serverDomain));

                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            // end of synchronization algorithm
        }).start();
    }
}