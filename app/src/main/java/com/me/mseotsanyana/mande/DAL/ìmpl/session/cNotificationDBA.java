package com.me.mseotsanyana.mande.DAL.ìmpl.session;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.me.mseotsanyana.mande.DAL.model.session.cNotificationModel;
import com.me.mseotsanyana.mande.DAL.model.session.cSettingModel;
import com.me.mseotsanyana.mande.DAL.model.session.cUserModel;
import com.me.mseotsanyana.mande.DAL.storage.database.cSQLDBHelper;
import com.me.mseotsanyana.mande.UTIL.cConstant;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class cNotificationDBA {
    private static SimpleDateFormat sdf = cConstant.FORMAT_DATE;
    private static String TAG = cNotificationDBA.class.getSimpleName();

    // an object of the database helper
    private cSQLDBHelper dbHelper;
    private cEntityImpl entityDBA;

    public cNotificationDBA(Context context) {
        dbHelper  = new cSQLDBHelper(context);
        entityDBA = new cEntityImpl(context);
    }

    /* ##################################### CREATE ACTIONS ##################################### */

    /**
     * Add notifications from an excel file
     *
     * @param notificationModel
     * @param publishers
     * @param subscribers
     * @param settings
     * @return
     */
    public boolean addNotificationFromExcel(cNotificationModel notificationModel,
                                            ArrayList<Integer> publishers,
                                            ArrayList<Integer> subscribers,
                                            ArrayList<Integer> settings ) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, notificationModel.getNotificationID());
        cv.put(cSQLDBHelper.KEY_ENTITY_FK_ID, notificationModel.getEntityID());
        cv.put(cSQLDBHelper.KEY_ENTITY_TYPE_FK_ID, notificationModel.getEntityTypeID());
        cv.put(cSQLDBHelper.KEY_OPERATION_FK_ID, notificationModel.getOperationID());
        cv.put(cSQLDBHelper.KEY_NAME, notificationModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, notificationModel.getDescription());

        // insert a record
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblNOTIFICATION, null, cv) < 0) {
                return false;
            }

            // add notification publishers
            for(int publisher: publishers) {
                if (addPublisher(publisher, notificationModel.getNotificationID()))
                    continue;
                else
                    return false;
            }

            // add notification subscribers
            for(int subscriber: subscribers) {
                if (addSubscriber(subscriber, notificationModel.getNotificationID()))
                    continue;
                else
                    return false;
            }

            // add notification settings
            for(int setting: settings) {
                if (addNotificationSetting(notificationModel.getNotificationID(), setting))
                    continue;
                else
                    return false;
            }

        } catch (Exception e) {
            Log.d(TAG, "Exception in importing: "+e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addPublisher(int publisherID, int notificationID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(cSQLDBHelper.KEY_PUBLISHER_FK_ID, publisherID);
        cv.put(cSQLDBHelper.KEY_NOTIFICATION_FK_ID, notificationID);

        if (db.insert(cSQLDBHelper.TABLE_tblPUBLISHER, null, cv) < 0) {
            return false;
        }

        return true;
    }

    public boolean addSubscriber(int subscriberID, int notificationID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(cSQLDBHelper.KEY_SUBSCRIBER_FK_ID, subscriberID);
        cv.put(cSQLDBHelper.KEY_NOTIFICATION_FK_ID, notificationID);

        if (db.insert(cSQLDBHelper.TABLE_tblSUBSCRIBER, null, cv) < 0) {
            return false;
        }

        return true;
    }

    public boolean addNotificationSetting(int notificationID, int settingID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(cSQLDBHelper.KEY_NOTIFICATION_FK_ID, notificationID);
        cv.put(cSQLDBHelper.KEY_SETTING_FK_ID, settingID);


        if (db.insert(cSQLDBHelper.TABLE_tblNOTIFY_SETTING, null, cv) < 0) {
            return false;
        }

        return true;
    }

    /* ############################################# READ ACTIONS ############################################# */

    /**
     * Read and filter notifications/messages
     * @param userID
     * @param primaryRole
     * @param secondaryRoles
     * @param operationBITS
     * @param statusBITS
     * @return List
     */
    public List<cNotificationModel> getNotifications(
            int userID, int primaryRole, int secondaryRoles, int operationBITS, int statusBITS) {

        List<cNotificationModel> notificationModels = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT * "+
                " FROM "+ cSQLDBHelper.TABLE_tblNOTIFICATION +
                " WHERE ((("+cSQLDBHelper.KEY_OWNER_ID+" = ?) " +
                " AND (("+cSQLDBHelper.KEY_PERMS_BITS+" & ?) != 0)) " +
                " OR ((("+cSQLDBHelper.KEY_GROUP_BITS +" & ?) != 0) " +
                " AND (("+cSQLDBHelper.KEY_PERMS_BITS+" & ?) != 0)) "+
                " OR ((("+cSQLDBHelper.KEY_GROUP_BITS +" & ?) != 0) " +
                " AND (("+cSQLDBHelper.KEY_PERMS_BITS+" & ?) != 0)))";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{
                String.valueOf(userID),String.valueOf(operationBITS),
                String.valueOf(primaryRole),String.valueOf(operationBITS),
                String.valueOf(secondaryRoles),String.valueOf(operationBITS)});

        try {
            if (cursor.moveToFirst()) {
                do {
                    cNotificationModel notificationModel = new cNotificationModel();

                    notificationModel.setNotificationID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    notificationModel.setEntityID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ENTITY_FK_ID)));
                    notificationModel.setEntityTypeID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ENTITY_TYPE_FK_ID)));
                    notificationModel.setServerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    notificationModel.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    notificationModel.setOrgID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                    notificationModel.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    notificationModel.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    notificationModel.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    notificationModel.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    notificationModel.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    notificationModel.setCreatedDate(sdf.parse(cursor.getString(cursor.getColumnIndex(
                            cSQLDBHelper.KEY_CREATED_DATE))));
                    notificationModel.setModifiedDate(sdf.parse(cursor.getString(cursor.getColumnIndex(
                            cSQLDBHelper.KEY_MODIFIED_DATE))));
                    notificationModel.setSyncedDate(sdf.parse(cursor.getString(cursor.getColumnIndex(
                            cSQLDBHelper.KEY_SYNCED_DATE))));

                    // construct an entity model
                    notificationModel.setEntityModel(entityDBA.getEntityByID(
                            notificationModel.getEntityID(),
                            notificationModel.getEntityTypeID()));

                    // populate subscribers
                    notificationModel.setSubscriberModelSet(
                            getSubscribersByNotificationID(notificationModel.getNotificationID()));

                    // populate publishers
                    notificationModel.setPublisherModelSet(
                            getPublishersByNotificationID(notificationModel.getNotificationID()));

                    // populate settings
                    notificationModel.setSettingModelSet(
                            getSettingsByNotificationID(notificationModel.getNotificationID()));


                    notificationModels.add(notificationModel);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, " Error while reading: "+e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return notificationModels;
    }

    /**
     * Read users who publishes the specified notification/message
     * @param notificationID
     * @return Set
     */
    public Set<cUserModel> getPublishersByNotificationID(int notificationID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Set<cUserModel> userModelSet = new HashSet();

        // construct a selection query
        String selectQuery = "SELECT " +
                "user." + cSQLDBHelper.KEY_ID + ", user." + cSQLDBHelper.KEY_SERVER_ID + ", " +
                "user." + cSQLDBHelper.KEY_OWNER_ID + ", user." + cSQLDBHelper.KEY_ORG_ID + ", " +
                "user." + cSQLDBHelper.KEY_GROUP_BITS +", user." + cSQLDBHelper.KEY_PERMS_BITS + ", " +
                "user." + cSQLDBHelper.KEY_STATUS_BITS +", user." + cSQLDBHelper.KEY_NAME + ", " +
                "user." + cSQLDBHelper.KEY_SURNAME +", user." + cSQLDBHelper.KEY_CREATED_DATE + ", " +
                "user." + cSQLDBHelper.KEY_MODIFIED_DATE +", user." + cSQLDBHelper.KEY_SYNCED_DATE +
                " FROM " +
                cSQLDBHelper.TABLE_tblUSER + " user, " +
                cSQLDBHelper.TABLE_tblNOTIFICATION + " notification, " +
                cSQLDBHelper.TABLE_tblPUBLISHER + " publisher " +
                " WHERE user."+cSQLDBHelper.KEY_ID + " = publisher."+cSQLDBHelper.KEY_USER_FK_ID +
                " AND notification."+cSQLDBHelper.KEY_ID + " = publisher."+cSQLDBHelper.KEY_NOTIFICATION_FK_ID +
                " AND notification."+cSQLDBHelper.KEY_ID +" = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(notificationID)});

        try {
            if (cursor.moveToFirst()) {
                do {

                    cUserModel userModel = new cUserModel();

                    userModel.setUserID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    userModel.setServerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    userModel.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    userModel.setOrgID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                    userModel.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    userModel.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    userModel.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    userModel.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    userModel.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    userModel.setCreatedDate(Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    userModel.setModifiedDate(Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    userModel.setSyncedDate(Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));

                    userModelSet.add(userModel);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while reading "+e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return userModelSet;
    }

    /**
     * Read users who subscribe to the speficied notification/message
     * @param notificationID
     * @return Set
     */
    public Set<cUserModel> getSubscribersByNotificationID(int notificationID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Set<cUserModel> userModelSet = new HashSet();

        // construct a selection query
        String selectQuery = "SELECT " +
                "user." + cSQLDBHelper.KEY_ID + ", user." + cSQLDBHelper.KEY_SERVER_ID + ", " +
                "user." + cSQLDBHelper.KEY_OWNER_ID + ", user." + cSQLDBHelper.KEY_ORG_ID + ", " +
                "user." + cSQLDBHelper.KEY_GROUP_BITS +", user." + cSQLDBHelper.KEY_PERMS_BITS + ", " +
                "user." + cSQLDBHelper.KEY_STATUS_BITS +", user." + cSQLDBHelper.KEY_NAME + ", " +
                "user." + cSQLDBHelper.KEY_SURNAME +", user." + cSQLDBHelper.KEY_CREATED_DATE + ", " +
                "user." + cSQLDBHelper.KEY_MODIFIED_DATE +", user." + cSQLDBHelper.KEY_SYNCED_DATE +
                " FROM " +
                cSQLDBHelper.TABLE_tblUSER + " user, " +
                cSQLDBHelper.TABLE_tblNOTIFICATION + " notification, " +
                cSQLDBHelper.TABLE_tblSUBSCRIBER + " subscriber " +
                " WHERE user."+cSQLDBHelper.KEY_ID + " = subscriber."+cSQLDBHelper.KEY_USER_FK_ID +
                " AND notification."+cSQLDBHelper.KEY_ID + " = subscriber."+cSQLDBHelper.KEY_NOTIFICATION_FK_ID +
                " AND notification."+cSQLDBHelper.KEY_ID +" = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(notificationID)});

        try {
            if (cursor.moveToFirst()) {
                do {

                    cUserModel userModel = new cUserModel();

                    userModel.setUserID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    userModel.setServerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    userModel.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    userModel.setOrgID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                    userModel.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    userModel.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    userModel.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    userModel.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    userModel.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    userModel.setCreatedDate(Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    userModel.setModifiedDate(Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    userModel.setSyncedDate(Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));

                    userModelSet.add(userModel);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while reading "+e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return userModelSet;
    }

    /**
     * Read settings of the specified notification/message
     * @param notificationID
     * @return Set
     */
    public Set<cSettingModel> getSettingsByNotificationID(int notificationID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Set<cSettingModel> settingModelSet = new HashSet();

        // construct a selection query
        String selectQuery = "SELECT " +
                "setting." + cSQLDBHelper.KEY_ID + ", setting." + cSQLDBHelper.KEY_SERVER_ID + ", " +
                "setting." + cSQLDBHelper.KEY_OWNER_ID + ", setting." + cSQLDBHelper.KEY_ORG_ID + ", " +
                "setting." + cSQLDBHelper.KEY_GROUP_BITS +", setting." + cSQLDBHelper.KEY_PERMS_BITS + ", " +
                "setting." + cSQLDBHelper.KEY_STATUS_BITS +", setting." + cSQLDBHelper.KEY_NAME + ", " +
                "setting." + cSQLDBHelper.KEY_SURNAME +", setting." + cSQLDBHelper.KEY_CREATED_DATE + ", " +
                "setting." + cSQLDBHelper.KEY_MODIFIED_DATE +", setting." + cSQLDBHelper.KEY_SYNCED_DATE +
                " FROM " +
                cSQLDBHelper.TABLE_tblNOTIFICATION + " notification, " +
                cSQLDBHelper.TABLE_tblSETTING + " setting, " +
                cSQLDBHelper.TABLE_tblNOTIFY_SETTING + " setting_notification " +
                " WHERE notification."+cSQLDBHelper.KEY_ID + " = setting_notification."+cSQLDBHelper.KEY_NOTIFICATION_FK_ID +
                " AND setting."+cSQLDBHelper.KEY_ID + " = setting_notification."+cSQLDBHelper.KEY_SETTING_FK_ID +
                " AND notification."+cSQLDBHelper.KEY_ID +" = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(notificationID)});

        try {
            if (cursor.moveToFirst()) {
                do {

                    cSettingModel settingModel = new cSettingModel();

                    settingModel.setSettingID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    settingModel.setServerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    settingModel.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    settingModel.setOrgID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                    settingModel.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    settingModel.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    settingModel.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    settingModel.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    settingModel.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    settingModel.setCreatedDate(Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    settingModel.setModifiedDate(Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    settingModel.setSyncedDate(Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));

                    settingModelSet.add(settingModel);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while reading "+e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return settingModelSet;
    }

    /* ############################################# UPDATE ACTIONS ############################################# */



    /* ############################################# DELETE ACTIONS ############################################# */

    /**
     * Delete all notifications/messages
     * @return Boolean
     */
    public boolean deleteNotifications() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblNOTIFICATION, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    /* ############################################# SYNC ACTIONS ############################################# */


}
