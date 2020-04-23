package com.me.mseotsanyana.mande.BLL.interactors.session.user.Impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.google.gson.Gson;
import com.me.mseotsanyana.mande.BLL.domain.session.cAddressDomain;
import com.me.mseotsanyana.mande.BLL.domain.session.cNotificationDomain;
import com.me.mseotsanyana.mande.BLL.domain.session.cRoleDomain;
import com.me.mseotsanyana.mande.BLL.domain.session.cSessionDomain;
import com.me.mseotsanyana.mande.BLL.domain.session.cUserDomain;
import com.me.mseotsanyana.mande.BLL.interactors.session.address.Impl.cAddressHandler;
import com.me.mseotsanyana.mande.BLL.interactors.session.session.Impl.cSessionHandler;
import com.me.mseotsanyana.mande.BLL.interactors.session.notification.Impl.cNotificationHandler;
import com.me.mseotsanyana.mande.BLL.interactors.session.organization.Impl.cOrganizationHandler;
import com.me.mseotsanyana.mande.BLL.interactors.session.role.Impl.cRoleHandler;
import com.me.mseotsanyana.mande.DAL.model.session.cAddressModel;
import com.me.mseotsanyana.mande.DAL.model.session.cNotificationModel;
import com.me.mseotsanyana.mande.DAL.model.session.cRoleModel;
import com.me.mseotsanyana.mande.DAL.model.session.cSessionModel;
import com.me.mseotsanyana.mande.DAL.storage.preference.cBitwise;
import com.me.mseotsanyana.mande.DAL.ìmpl.session.cUserRepositoryImpl;
import com.me.mseotsanyana.mande.DAL.model.session.cUserModel;
import com.me.mseotsanyana.mande.UTIL.INTERFACE.iMEEntityInterface;
import com.me.mseotsanyana.mande.UTIL.BLL.cMapper;
import com.me.mseotsanyana.mande.UTIL.cConstant;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by mseotsanyana on 2017/08/28.
 */

public class cUserHandler extends cMapper<cUserModel, cUserDomain> {
    private static SimpleDateFormat sdf = cConstant.FORMAT_DATE;
    private static String TAG = cUserHandler.class.getSimpleName();
    //final static private int _id = cSessionManager.USER;

    private Context context;
    //private cSessionManager session;
    private Gson gson;

    private cUserRepositoryImpl userDBA;

    private int entityBITS, operationBITS;

    private iMEEntityInterface userInterface;

    public cUserHandler(Context context,  iMEEntityInterface userInterface) {
        this.userDBA = new cUserRepositoryImpl(context);
        this.context = context;
        //this.session = session;
        this.gson = new Gson();
        this.userInterface = userInterface;


        /** 1. ENTITY SECTION **/

        // entity bits of all entities that are accessible
        //entityBITS = session.loadEntityBITS(session.loadUserID(),
        //        session.loadOrganizationID(), cSessionManager.types[0]);

        /** 2. OPERATION SECTION **/

        // operations associated to ENTITY entity
        //operationBITS = session.loadOperationBITS(cSessionManager.USER, cSessionManager.types[0]);

    }

    public cUserHandler(Context context) {
        this.userDBA = new cUserRepositoryImpl(context);
        this.context = context;
        //this.session = session;
        this.gson = new Gson();


        /** 1. ENTITY SECTION **/

        // entity bits of all entities that are accessible
        //entityBITS = session.loadEntityBITS(session.loadUserID(),
        //        session.loadOrganizationID(), cSessionManager.types[0]);

        /** 2. OPERATION SECTION **/

        // operations associated to ENTITY entity
        //operationBITS = session.loadOperationBITS(cSessionManager.USER,
        //        cSessionManager.types[0]);

    }


    public boolean addUserFromExcel(cUserDomain domain) {
        // map the business domain to the model
        cUserModel model = this.DomainToModel(domain);
        return true;//userDBA.addUserFromExcel(model);
    }

    public long createUser(cUserDomain domain) {
        // map the business domain to the model
        cUserModel model = this.DomainToModel(domain);
        return 1;//userDBA.createUser(model);
    }

    public boolean updateUser(cUserDomain domain) {
        // map the business domain to the model
        cUserModel model = this.DomainToModel(domain);
        return userDBA.updateUser(model);
    }

    public boolean deleteUser(int userID) {
        return userDBA.deleteUser(userID);
    }

    public boolean deleteUsers() {
        return userDBA.deleteUsers();
    }

    public cUserDomain getUserByID(int userID) {
        cUserModel userModel = userDBA.getUserByID(userID);
        cUserDomain userDomain = this.ModelToDomain(userModel);

        return userDomain;
    }

    public cUserDomain getUserByEmailPassword(String email, String password) {
        cUserDomain userDomain = null;
        cUserModel userModel = userDBA.getUserByEmailPassword(email, password);
        //Log.d(TAG + " USER HANDLESR ", gson.toJson(userModel));
        if (userModel != null)
            userDomain = this.ModelToDomain(userModel);

        return userDomain;
    }

    public ArrayList<cUserDomain> getUserList(int userID, int orgID,
                                              int primaryRole, int secondaryRoles) {
        // statuses of operations associated with USER entity
        int statusBITS = 0;//session.loadStatusBITS(cSessionManager.USER, cSessionManager.types[0]);

        ArrayList<cUserDomain> userDomains = new ArrayList<>();
/*
        Log.d(TAG, "ENTITY ID = " + cSessionManager.USER +
                ", ENTITY BITS = " + entityBITS + ", USER ID = " + userID +
                ", ORG. ID = " + orgID + ", PRIMARY ROLE = " + primaryRole +
                ", SECONDARY ROLES = " + secondaryRoles + ", OPERATIONS = " + operationBITS +
                ", STATUSES = " + statusBITS);
*/
        if(!cBitwise.isEntity(/*cSessionManager.USER*/0, entityBITS)){
            userInterface.onResponseMessage("User Entity", "Insufficient privileges to access entity. " +
                    "Please contact your system administrator");
        }
        else if (cBitwise.isRead(operationBITS, statusBITS)) {
            List<cUserModel> userModel = userDBA.getUserList(
                    userID, primaryRole, secondaryRoles, operationBITS, statusBITS);

            cUserDomain domain;

            for (int i = 0; i < userModel.size(); i++) {
                domain = this.ModelToDomain(userModel.get(i));
                userDomains.add(domain);
            }
        } else {
            userInterface.onResponseMessage("User Entity", "Insufficient privileges. " +
                    "Please contact your system administrator");
        }

        return userDomains;
    }

    public boolean checkUser(String email) {
        return userDBA.checkUser(email);
    }

    public cUserDomain checkUser(String email, String password) {
        cUserModel model = userDBA.checkUser(email, password);
        cUserDomain domain = this.ModelToDomain(model);
        return domain;
    }

    /* auxiliary methods */


    /**
     * Updates the current photo for the report.
     * @param userID the identifier of the report for which to save the photo
     * @param photo  the photo to save to the internal storage and save path in
     *               the database.
     */
    public String updatePhotoFromStorage(int userID, Bitmap photo) {
        // removes photo from the database if one already exists
        deletePhotoFromStorage(userID);

        // adds the new photo to the internal storage
        File internalStorage = context.getDir("UserPhotos", Context.MODE_PRIVATE);
        File userFilePath = new File(internalStorage, userID + ".png");
        String userPath = userFilePath.toString();

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(userFilePath);
            photo.compress(Bitmap.CompressFormat.PNG, 100 /*quality*/, fos);
            fos.close();
        } catch (Exception e) {
            Log.d(TAG, "Problem updating profile photo ", e);
            userPath = "";
        }

        //int updatedUserID = userDBA.updateUserPhotoPath(userID, userPath);

        return userPath;
    }

    /**
     * Deletes the photo for the report from the internal storage, if any.
     * @param userID the report to remove.
     */
    private void deletePhotoFromStorage(int userID) {
        String photoPath = userDBA.getUserPhotoPath(userID);
        if (photoPath != null && photoPath.length() != 0) {
            File userFilePath = new File(photoPath);
            userFilePath.delete();
        }
    }

/*
    public Bitmap getPhotoFromStorage(int userID) {
        String photoPath = userDBA.getUserPhotoPath(userID);
        Log.d(TAG, "Photo path: "+photoPath+", User ID: "+userID);

        Bitmap bitmap = null;
        if (photoPath != null && photoPath.length() != 0) {
            try {
                FileInputStream fileInputStream = context.openFileInput(photoPath);
                bitmap = BitmapFactory.decodeStream(fileInputStream);
                fileInputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }
*/

    /**
     * Gets the picture for the specified report in the database.
     * @param userID the identifier of the user for which to get the photo.
     * @return the photo for the user, or null if no photo was found.
     */
    public Bitmap getUserPhoto(int userID) {
        String photoPath = getUserPhotoPath(userID);
        if (photoPath == null || photoPath.length() == 0)
            return (null);

        Bitmap userPhoto = BitmapFactory.decodeFile(photoPath);

        return userPhoto;
    }

    /**
     * Gets the path of the photo for the specified user in the database.
     * @param userID the identifier of the user for which to get the photo.
     * @return the photo for the user, or null if no photo was found.
     */
    private String getUserPhotoPath(int userID) {
        String photoPath = userDBA.getUserPhotoPath(userID);
        return photoPath;
    }

    /* synchronization methods */

    public boolean createEntity(cUserDomain domain) {
        // map the business domain to the model
        /*
        cAddressModel model = this.DomainToModel(domain);
        model.getSyncedDate();
        return addressDBA.createAddress(model);
        */
        return false;
    }

    public boolean updateEntity(cUserDomain domain) {
        // map the business domain to the model
        /*
        cAddressModel model = this.DomainToModel(domain);
        return addressDBA.updateAddress(model);
        */
        return false;
    }

    public ArrayList<cUserDomain> getEntities(int user_id,
                                              int groupBIT,
                                              int otherBITS,
                                              int operations,
                                              int statuses) {

        ArrayList<cUserDomain> userDomains = new ArrayList<>();

        Log.d(TAG, "USER ID = " + user_id + ", GROUP = " + groupBIT + ", OTHER = " + otherBITS +
                ", OPERATION = " + operations + ", STATUS = " + statuses);
/*
        List<cUserModel> userModels = userDBA.getUsers(user_id, group, other, operations, statuses);

        cUserDomain domain;
        for (int i = 0; i < userModels.size(); i++) {
            domain = this.ModelToDomain(userModels.get(i));
            userDomains.add(domain);
        }
*/
        return userDomains;
    }

    Set<cAddressModel> toAddressModelSet(Set<cAddressDomain> addressDomainSet){
        Set<cAddressModel> addressModelSet = new HashSet<>();
        cAddressHandler addressHandler = new cAddressHandler();

        for (cAddressDomain addressDomain : addressDomainSet) {
            //cAddressModel addressModel = addressHandler.DomainToModel(addressDomain);
            //addressModelSet.add(addressModel);
        }

        return addressModelSet;
    }

    Set<cAddressDomain> toAddressDomainSet(Set<cAddressModel> addressModelSet){
        Set<cAddressDomain> addressDomainSet = new HashSet<>();
        cAddressHandler addressHandler = new cAddressHandler();

        for (cAddressModel addressModel : addressModelSet) {
            //cAddressDomain addressDomain = addressHandler.ModelToDomain(addressModel);
            //addressDomainSet.add(addressDomain);
        }

        return addressDomainSet;
    }

    Set<cSessionModel> toSessionModelSet(Set<cSessionDomain> sessionDomainSet){
        Set<cSessionModel> sessionModelSet = new HashSet<>();
        cSessionHandler sessionHandler = new cSessionHandler();

        for (cSessionDomain sessionDomain : sessionDomainSet) {
            //cSessionModel sessionModel = sessionHandler.DomainToModel(sessionDomain);
            //sessionModelSet.add(sessionModel);
        }

        return sessionModelSet;
    }

    Set<cSessionDomain> toSessionDomainSet(Set<cSessionModel> roleModelSet){
        Set<cSessionDomain> sessionDomainSet = new HashSet<>();
        cSessionHandler sessionHandler = new cSessionHandler();

        for (cSessionModel sessionModel : roleModelSet) {
            //cSessionDomain roleDomain = sessionHandler.ModelToDomain(sessionModel);
            //sessionDomainSet.add(roleDomain);
        }

        return sessionDomainSet;
    }

    Set<cRoleModel> toRoleModelSet(Set<cRoleDomain> roleDomainSet){
        Set<cRoleModel> roleModelSet = new HashSet<>();
        cRoleHandler roleHandler = new cRoleHandler();

        for (cRoleDomain roleDomain : roleDomainSet) {
            //cRoleModel roleModel = roleHandler.DomainToModel(roleDomain);
            //roleModelSet.add(roleModel);
        }

        return roleModelSet;
    }

    Set<cRoleDomain> toRoleDomainSet(Set<cRoleModel> roleModelSet){
        Set<cRoleDomain> domainSet = new HashSet<>();
        cRoleHandler roleHandler = new cRoleHandler();

        for (cRoleModel roleModel : roleModelSet) {
            //cRoleDomain roleDomain = roleHandler.ModelToDomain(roleModel);
            //domainSet.add(roleDomain);
        }

        return domainSet;
    }

    Set<cNotificationModel> toNotificationModelSet(Set<cNotificationDomain> notificationDomainSet){
        Set<cNotificationModel> notificationModelSet = new HashSet<>();
        cNotificationHandler notificationHandler = new cNotificationHandler();

        for (cNotificationDomain notificationDomain : notificationDomainSet) {
            //cNotificationModel notificationModel =
            //       notificationHandler.DomainToModel(notificationDomain);
            //notificationModelSet.add(notificationModel);
        }

        return notificationModelSet;
    }

    Set<cNotificationDomain> toNotificationDomainSet(Set<cNotificationModel> notificationModelSet){
        Set<cNotificationDomain> notificationDomainSet = new HashSet<>();
        cNotificationHandler notificationHandler = new cNotificationHandler();

        for (cNotificationModel notificationModel : notificationModelSet) {
            //cNotificationDomain notificationDomain =
            //        notificationHandler.ModelToDomain(notificationModel);
            //notificationDomainSet.add(notificationDomain);
        }

        return notificationDomainSet;
    }

    @Override
    protected cUserModel DomainToModel(cUserDomain domain) {
        cUserModel model = new cUserModel();
        cOrganizationHandler organizationHandler = new cOrganizationHandler();

        model.setUserID(domain.getUserID());
        model.setOrganizationID(domain.getOrganizationID());
        model.setServerID(domain.getServerID());
        model.setOwnerID(domain.getOwnerID());
        model.setOrgID(domain.getOrgID());
        model.setUniqueID(domain.getUniqueID());
        model.setGroupBITS(domain.getGroupBITS());
        model.setPermsBITS(domain.getPermsBITS());
        model.setStatusBITS(domain.getStatusBITS());
        model.setPhoto(domain.getPhoto());
        model.setName(domain.getName());
        model.setSurname(domain.getSurname());
        model.setGender(domain.getGender());
        model.setDescription(domain.getDescription());
        model.setEmail(domain.getEmail());
        model.setWebsite(domain.getWebsite());
        model.setPhone(domain.getPhone());
        model.setPassword(domain.getPassword());
        model.setSalt(domain.getSalt());
        model.setOldPassword(domain.getOldPassword());
        model.setNewPassword(domain.getNewPassword());
        model.setCreatedDate(domain.getCreatedDate());
        model.setModifiedDate(domain.getModifiedDate());
        model.setSyncedDate(domain.getSyncedDate());

        if (domain.getOrganizationDomain() != null) {
            //model.setOrganizationModel(
            //        organizationHandler.DomainToModel(domain.getOrganizationDomain()));
        }

        if(!domain.getAddressDomainSet().isEmpty()) {
            model.setAddressModelSet(toAddressModelSet(domain.getAddressDomainSet()));
        }

        if(!domain.getSessionDomainSet().isEmpty()) {
            model.setSessionModelSet(toSessionModelSet(domain.getSessionDomainSet()));
        }

        if(!domain.getRoleDomainSet().isEmpty()) {
            model.setRoleModelSet(toRoleModelSet(domain.getRoleDomainSet()));
        }

        if(!domain.getPublisherDomainSet().isEmpty()) {
            model.setPublisherModelSet(toNotificationModelSet(domain.getPublisherDomainSet()));
        }

        if(!domain.getSubscriberDomainSet().isEmpty()) {
            model.setSubscriberModelSet(toNotificationModelSet(domain.getSubscriberDomainSet()));
        }

        return model;
    }

    @Override
    protected cUserDomain ModelToDomain(cUserModel model) {
        cUserDomain domain = new cUserDomain();
        cOrganizationHandler organizationHandler = new cOrganizationHandler();

        domain.setUserID(model.getUserID());
        domain.setOrganizationID(model.getOrganizationID());
        domain.setServerID(model.getServerID());
        domain.setOwnerID(model.getOwnerID());
        domain.setOrgID(model.getOrgID());
        domain.setUniqueID(model.getUniqueID());
        domain.setGroupBITS(model.getGroupBITS());
        domain.setPermsBITS(model.getPermsBITS());
        domain.setStatusBITS(model.getStatusBITS());
        domain.setPhoto(model.getPhoto());
        domain.setName(model.getName());
        domain.setSurname(model.getSurname());
        domain.setGender(model.getGender());
        domain.setDescription(model.getDescription());
        domain.setEmail(model.getEmail());
        domain.setWebsite(model.getWebsite());
        domain.setPhone(model.getPhone());
        domain.setPassword(model.getPassword());
        domain.setSalt(model.getSalt());
        domain.setOldPassword(model.getOldPassword());
        domain.setNewPassword(model.getNewPassword());
        domain.setCreatedDate(model.getCreatedDate());
        domain.setModifiedDate(model.getModifiedDate());
        domain.setSyncedDate(model.getSyncedDate());

        if(model.getOrganizationModel() != null) {
            //domain.setOrganizationDomain(
            //        organizationHandler.ModelToDomain(model.getOrganizationModel()));
        }

        if(!model.getAddressModelSet().isEmpty()) {
            domain.setAddressDomainSet(toAddressDomainSet(model.getAddressModelSet()));
        }

        if(!model.getRoleModelSet().isEmpty()) {
            domain.setRoleDomainSet(toRoleDomainSet(model.getRoleModelSet()));
        }

        if(!model.getSessionModelSet().isEmpty()) {
            domain.setSessionDomainSet(toSessionDomainSet(model.getSessionModelSet()));
        }

        if(!model.getPublisherModelSet().isEmpty()) {
            domain.setPublisherDomainSet(toNotificationDomainSet(model.getPublisherModelSet()));
        }

        if(!model.getSubscriberModelSet().isEmpty()) {
            domain.setSubscriberDomainSet(toNotificationDomainSet(model.getSubscriberModelSet()));
        }

        return domain;
    }
}
