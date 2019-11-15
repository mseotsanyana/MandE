package com.me.mseotsanyana.mande.BRBAC.BLL;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

import com.me.mseotsanyana.mande.COM.cVolleyHandler;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import static com.me.mseotsanyana.mande.UTILITY.cConstant.URL_ADDRESS;

/**
 * Created by mseotsanyana on 2018/04/05.
 */

public class cAddressJobService extends JobService {
    private static final String TAG = cAddressJobService.class.getSimpleName();

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    boolean isWorking = false;
    boolean jobCancelled = false;
    private String base_url = URL_ADDRESS;
    private String url = "";
    private Map<String, String> params = new HashMap<>();
    private cSessionManager session;
    private cVolleyHandler volleyHandler;
    private cAddressHandler addressHandler;

    //private Map<String, String> params;
    //Type type;

    @Override
    public void onCreate() {
        super.onCreate();
        session = new cSessionManager(getApplicationContext());
        volleyHandler = new cVolleyHandler(getApplicationContext());
        addressHandler = new cAddressHandler(getApplicationContext());

        //type = new TypeToken<Map<String, String>>(){}.getType();
    }

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.d(TAG, "The Address JobService has started!");

        //String jsonString = jobParameters.getExtras().getString("jsonParam");

        //Gson gson = new Gson();
        //params = gson.fromJson(jsonString, type);

        isWorking = true;

        // We need 'jobParameters' so we can call 'jobFinished'
        syncAddressThread(jobParameters); // Services do NOT run on a separate thread

        return isWorking;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.d(TAG, "The Address JobService has completed.");

        jobCancelled = true;
        boolean needsReschedule = isWorking;
        jobFinished(jobParameters, needsReschedule);

        return needsReschedule;
    }

    private void syncAddressThread(final JobParameters jobParameters) {
        new Thread(new Runnable() {
            public void run() {
                // start of synchronization algorithm

                /* == DOWNLOAD FROM THE SERVER TO UPDATE THE CLIENT == */

                /* 1. get unsynced addresses from local database */
                int sync_operations = 0;//session.getAddressOperationBITS();
                int own_sync        = sync_operations & session.permissions[12];
                int group_sync      = sync_operations & session.permissions[13];
                int other_sync      = sync_operations & session.permissions[14];
                sync_operations     = own_sync | group_sync | other_sync;




                isWorking = false;
                boolean needsReschedule = false;
                jobFinished(jobParameters, needsReschedule);
                // end of synchronization algorithm
            }
        }).start();
    }
}
/*

    private void syncAddressThread(final JobParameters jobParameters) {
        new Thread(new Runnable() {
            public void run() {
                // start of synchronization algorithm

                 == DOWNLOAD FROM THE SERVER TO UPDATE THE CLIENT ==

                 1. get unsynced addresses from local database
                int sync_operations = session.getAddressOperationBITS();
                int own_sync = sync_operations & session.permissions[12];
                int group_sync = sync_operations & session.permissions[13];
                int other_sync = sync_operations & session.permissions[14];
                sync_operations = own_sync | group_sync | other_sync;

                //Log.d(TAG, "Sync Operation -> " + session.getAddressOperationBITS() + " vs. " + sync_operations);
                ArrayList<cAddressDomain> addressDomains = addressHandler.getAddresses(
                        session.getUserID(),               loggedin user id
                        session.getGroupBIT(),             primary group bit
                        session.getOtherBITS(),           /* secondary group bits
                        sync_operations,                  /* user entity operation bits (sync only)
                        session.getAddressStatusBITS()    /* user entity statuses
                );
                Gson gson = new Gson();
                String domain_gson = gson.toJson(addressDomains);

                Log.d(TAG, "Response from client -> " + domain_gson);

                /* 1.2. get the maximum synced date to filter the latest server records
                Date timestamp = addressDomains.get(0).getSyncedDate();
                for (int i = 1; i < addressDomains.size(); i++) {
                    if (timestamp.before(addressDomains.get(i).getSyncedDate())) {
                        timestamp = addressDomains.get(i).getSyncedDate();
                    }
                }
                Log.d(TAG, "Max Timestamp -> " + timestamp);

                //FIXME: the timestamp of GetTime different from UNIX TIMESTAMP?
                long timestamp_string = timestamp.getTime() / 1000;
                Log.d(TAG, "Max Timestamp as number -> " + timestamp_string);

                /* 1.3. prepare parameters to filter records from remote server
                params = new HashMap<>();
                params.put("userID", Integer.toString(session.getUserID()));
                params.put("group", Integer.toString(session.getGroupBIT()));
                params.put("other", Integer.toString(session.getOtherBITS()));
                params.put("perms", Integer.toString(sync_operations));
                params.put("status", Integer.toString(session.getAllStatusBITS()));
                params.put("synced_date", Long.toString(timestamp_string));

                url = base_url;
                url = volleyHandler.generateUrl(url, params);

                Log.d(TAG, "userID    -> "+session.getUserID());
                Log.d(TAG, "group     -> "+session.getGroupBIT());
                Log.d(TAG, "other     -> "+session.getOtherBITS());
                Log.d(TAG, "perms     -> "+session.getUserOperationBITS());
                Log.d(TAG, "status    -> "+session.getAllStatusBITS());
                Log.d(TAG, "url       -> "+url);
                Log.d(TAG, "Timestamp -> "+timestamp.toString());


                /* 1.4. get the latest data from remote database
                cVolleyHandler.cVolleyResponse<JSONObject> response =
                        volleyHandler.loadFutureJsonObject(
                                url,
                                TAG,
                                Request.Method.GET,
                                null,
                                params);

                //Log.d(TAG, "Response from server -> " + response.getResponse());

                1.5 reading the JSONObject response from the server
                JSONObject jsonObject = response.getResponse();
                try {
                    JSONArray addresses = (JSONArray) jsonObject.get("addresses");
                    for (int i = 0; i < addresses.length(); i++) {
                        JSONObject address = (JSONObject) addresses.get(i);
                        int serverID = address.getInt("_id");

                        cAddressDomain serverAddress = new cAddressDomain();

                        serverAddress.setServerID(serverID);
                        serverAddress.setOwnerID(address.getInt("_id_owner"));
                        serverAddress.setGroupBITS(address.getInt("_bits_group"));
                        serverAddress.setPermsBITS(address.getInt("_bits_perm"));
                        serverAddress.setStatusBITS(address.getInt("_bits_status"));
                        serverAddress.setStreet(address.getString("street"));
                        serverAddress.setCity(address.getString("city"));
                        serverAddress.setProvince(address.getString("province"));
                        serverAddress.setPostalCode(address.getString("postal_code"));
                        serverAddress.setCountry(address.getString("country"));
                        serverAddress.setCreatedDate(sdf.parse(address.getString("created_date")));
                        serverAddress.setModifiedDate(sdf.parse(address.getString("modified_date")));
                        serverAddress.setSyncedDate(sdf.parse(address.getString("synced_date")));

                        // check if the server record already exists in the client's database
                        // and return corresponding client's record, otherwise null (i.e. 0)
                        cAddressDomain localAddress = addressHandler.getAddressByServerID(serverID);
                        int address_status = localAddress.getStatusBITS();

                        // is the server record already exists in the client's database?
                        if (localAddress.getServerID() != 0) {
                            // is the server record has been modified by the client?
                            if ((address_status & session.statuses[1]) != 0) {
                                // reset sync status
                                address_status &= ~session.statuses[1];

                                // conflict resolution:
                                // if the local record was modified after the server object was synced,
                                // update with the server record
                                if (localAddress.getSyncedDate().before(serverAddress.getSyncedDate())) {
                                    serverAddress.setStatusBITS(address_status);
                                    addressHandler.updateAddress(serverAddress);
                                    //Log.d(TAG, "Update with the Server Record -> " + gson.toJson(serverAddress));
                                } else {
                                    localAddress.setStatusBITS(address_status);
                                    addressHandler.updateAddress(localAddress);
                                    //Log.d(TAG, "Update with the Local Record1 -> " + gson.toJson(localAddress));
                                }
                            } else {
                                serverAddress.setStatusBITS(address_status);
                                addressHandler.updateAddress(serverAddress);
                                //Log.d(TAG, "Update with the Local Record2 -> " + gson.toJson(serverAddress));
                            }
                        } else {
                            serverAddress.setStatusBITS(address_status);
                            addressHandler.createAddress(serverAddress);
                            //Log.d(TAG, "Create with the Server Record -> " + gson.toJson(serverAddress));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                /* == EXTRACT FROM THE CLIENT TO UPLOAD TO THE SERVER ==
                /* 2. prepare local records to be created, updated and deleted
                 *     from the server
                ArrayList<cAddressDomain> records_create = new ArrayList<>();
                ArrayList<cAddressDomain> records_update = new ArrayList<>();
                ArrayList<cAddressDomain> records_delete = new ArrayList<>();

                for (int i = 0; i < addressDomains.size(); i++) {
                    //Log.d(TAG, "TEST NULL   -> " + addressDomains.get(i).getServerID());
                    if ((addressDomains.get(i).getStatusBITS() & session.statuses[1]) != 0) {
                        //Log.d(TAG, "TEST STATUS -> " + (addressDomains.get(i).getStatusBITS() & session.statuses[1]));

                        if (addressDomains.get(i).getServerID() == 0) {
                            // CREATE = POST
                            records_create.add(addressDomains.get(i));
                        } else {
                            // UPDATE = PUT
                            records_update.add(addressDomains.get(i));
                        }

                        if ((addressDomains.get(i).getStatusBITS() & session.statuses[2]) != 0) {
                            // DELETE = DELETE
                            records_delete.add(addressDomains.get(i));
                        }
                    }
                }

                /* 2.1 post (or create) the new local records in the server
                for (int i = 0; i < records_create.size(); i++) {
                    url = base_url;
                    HashMap<String, String> params = new HashMap<>();

                    params.put("ownerID", Integer.toString(records_create.get(i).getOwnerID()));
                    params.put("groupBITS", Integer.toString(records_create.get(i).getGroupBITS()));
                    params.put("permsBITS", Integer.toString(records_create.get(i).getPermsBITS()));
                    params.put("statusBITS", Integer.toString(records_create.get(i).getStatusBITS()));
                    params.put("street", records_create.get(i).getStreet());
                    params.put("city", records_create.get(i).getCity());
                    params.put("province", records_create.get(i).getProvince());
                    params.put("postalCode", records_create.get(i).getPostalCode());
                    params.put("country", records_create.get(i).getCountry());
                    params.put("createdDate", sdf.format(records_create.get(i).getCreatedDate()));
                    params.put("modifiedDate", sdf.format(records_create.get(i).getModifiedDate()));
                    params.put("syncedDate", sdf.format(records_create.get(i).getSyncedDate()));


                    //Log.d(TAG, "PARAMETERS -> " + params);
                    cVolleyHandler.cVolleyResponse<JSONObject> create_response =
                            volleyHandler.loadFutureJsonObject(
                                    url,
                                    TAG,
                                    Request.Method.POST,
                                    null,
                                    params);

                    JSONObject res_results = create_response.getResponse();
                    try {
                        if (res_results.getBoolean("result")){
                            // prepare for updating necessary fields
                            cAddressDomain domain = records_create.get(i);
                            // update server id, status and synced date
                            domain.setServerID(res_results.getInt("server_id"));
                            domain.setStatusBITS(~session.statuses[1]);
                            domain.setSyncedDate(sdf.parse(res_results.getString("synced_date")));
                            // update the record to complete the synced circle
                            addressHandler.updateAddress(domain);

                            Log.d(TAG, "Synced Record -> " + gson.toJson(domain));
                        }
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Log.d(TAG, "POST Response from the server -> " + create_response.getResponse());

                }
                Log.d(TAG, "POST data to the Server     -> " + gson.toJson(records_create));

                /* 2.2 put (or update) the modified local records in the server
                for (int i = 0; i < records_update.size(); i++) {

                }
                Log.d(TAG, "PUT data to the Server      -> " + gson.toJson(records_update));

                /* 2.3 delete the locally deleted records from the server
                for (int i = 0; i < records_delete.size(); i++) {

                }
                Log.d(TAG, "DELETE data from the Server -> " + gson.toJson(records_delete));

                isWorking = false;
                boolean needsReschedule = false;
                jobFinished(jobParameters, needsReschedule);
                // end of synchronization algorithm
            }
        }).start();
    }
    */