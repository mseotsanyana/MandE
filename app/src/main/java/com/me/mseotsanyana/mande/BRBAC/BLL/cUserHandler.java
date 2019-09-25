package com.me.mseotsanyana.mande.BRBAC.BLL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.me.mseotsanyana.mande.BRBAC.DAL.cUserDBA;
import com.me.mseotsanyana.mande.BRBAC.DAL.cUserModel;
import com.me.mseotsanyana.mande.Interface.iMEEntityInterface;
import com.me.mseotsanyana.mande.PPMER.BLL.cMapper;
import com.me.mseotsanyana.mande.Util.cBitwisePermission;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mseotsanyana on 2017/08/28.
 */

public class cUserHandler extends cMapper<cUserModel, cUserDomain> {
    //final static private int _id = cSessionManager.USER;
    private static String TAG = cUserHandler.class.getSimpleName();

    private Context context;
    private cSessionManager session;

    private cUserDBA userDBA;

    private int entityBITS, operationBITS;

    private iMEEntityInterface userInterface;

    public cUserHandler(Context context, cSessionManager session, iMEEntityInterface userInterface) {
        this.userDBA = new cUserDBA(context);
        this.context = context;
        this.session = session;
        this.userInterface = userInterface;


        /** 1. ENTITY SECTION **/

        // entity bits of all entities that are accessible
        entityBITS = session.loadEntityBITS(session.loadUserID(),
                session.loadOrganizationID(), cSessionManager.types[0]);

        /** 2. OPERATION SECTION **/

        // operations associated to ENTITY entity
        operationBITS = session.loadOperationBITS(session.loadUserID(),
                session.loadOrganizationID(), cSessionManager.USER,
                cSessionManager.types[0]);

    }

    public cUserHandler(Context context, cSessionManager session) {
        this.userDBA = new cUserDBA(context);
        this.context = context;
        this.session = session;

        /** 1. ENTITY SECTION **/

        // entity bits of all entities that are accessible
        entityBITS = session.loadEntityBITS(session.loadUserID(),
                session.loadOrganizationID(), cSessionManager.types[0]);

        /** 2. OPERATION SECTION **/

        // operations associated to ENTITY entity
        operationBITS = session.loadOperationBITS(session.loadUserID(),
                session.loadOrganizationID(), cSessionManager.USER,
                cSessionManager.types[0]);

    }


    public boolean addUserFromExcel(cUserDomain domain) {
        // map the business domain to the model
        cUserModel model = this.DomainToModel(domain);
        return userDBA.addUserFromExcel(model);
    }

    public long createUser(cUserDomain domain) {
        // map the business domain to the model
        cUserModel model = this.DomainToModel(domain);
        return userDBA.createUser(model);
    }

    public boolean updateUser(cUserDomain domain) {
        // map the business domain to the model
        cUserModel model = this.DomainToModel(domain);
        return userDBA.updateUser(model);
    }

    public boolean deleteUser(int userID) {
        return userDBA.deleteUser(userID);
    }

    public boolean deleteAllUsers() {
        return userDBA.deleteAllUsers();
    }

    public cUserDomain getUserByID(int userID) {
        cUserModel userModel = userDBA.getUserByID(userID);
        cUserDomain userDomain = this.ModelToDomain(userModel);

        return userDomain;
    }

    public cUserDomain getUserByEmailPassword(String email, String password) {
        cUserDomain userDomain = null;
        cUserModel userModel = userDBA.getUserByEmailPassword(email, password);
        if (userModel != null)
            userDomain = this.ModelToDomain(userModel);
        return userDomain;
    }

    public ArrayList<cUserDomain> getUserList(int userID, int orgID,
                                              int primaryRole, int secondaryRoles) {
        // statuses of operations associated with USER entity
        int statusBITS = session.loadStatusBITS(userID, orgID,
                cSessionManager.USER, cSessionManager.types[0], cSessionManager.READ);

        ArrayList<cUserDomain> userDomains = new ArrayList<>();

        Log.d(TAG, "ENTITY ID = " + cSessionManager.USER +
                ", ENTITY BITS = " + entityBITS + ", USER ID = " + userID +
                ", ORG. ID = " + orgID + ", PRIMARY ROLE = " + primaryRole +
                ", SECONDARY ROLES = " + secondaryRoles + ", OPERATIONS = " + operationBITS +
                ", STATUSES = " + statusBITS);

        if(!cBitwisePermission.isEntity(cSessionManager.USER, entityBITS)){
            userInterface.onResponseMessage("User Entity", "Insufficient privileges to access entity. " +
                    "Please contact your system administrator");
        }
        else if (cBitwisePermission.isRead(operationBITS, statusBITS)) {
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

    @Override
    protected cUserModel DomainToModel(cUserDomain domain) {
        cUserModel model = new cUserModel();

        model.setUserID(domain.getUserID());
        model.setOrganizationID(domain.getOrganizationID());
        model.setAddressID(domain.getAddressID());
        model.setOwnerID(domain.getOwnerID());
        model.setGroupBITS(domain.getGroupBITS());
        model.setPermsBITS(domain.getPermsBITS());
        model.setStatusBITS(domain.getStatusBITS());
        model.setPhotoPath(domain.getPhotoPath());
        model.setName(domain.getName());
        model.setSurname(domain.getSurname());
        model.setGender(domain.getGender());
        model.setDescription(domain.getDescription());
        model.setEmail(domain.getEmail());
        model.setWebsite(domain.getWebsite());
        model.setPhone(domain.getPhone());
        model.setUniqueID(domain.getUniqueID());
        model.setPassword(domain.getPassword());
        model.setSalt(domain.getSalt());
        //model.setOldPassword(domain.getOldPassword());
        //model.setNewPassword(domain.getNewPassword());
        model.setCreatedDate(domain.getCreatedDate());
        model.setModifiedDate(domain.getModifiedDate());
        model.setSyncedDate(domain.getSyncedDate());

        return model;
    }

    @Override
    protected cUserDomain ModelToDomain(cUserModel model) {
        cUserDomain domain = new cUserDomain();

        domain.setUserID(model.getUserID());
        domain.setOrganizationID(model.getOrganizationID());
        domain.setAddressID(model.getAddressID());
        domain.setOwnerID(model.getOwnerID());
        domain.setGroupBITS(model.getGroupBITS());
        domain.setPermsBITS(model.getPermsBITS());
        domain.setStatusBITS(model.getStatusBITS());
        domain.setPhotoPath(model.getPhotoPath());
        domain.setName(model.getName());
        domain.setSurname(model.getSurname());
        domain.setGender(model.getGender());
        domain.setDescription(model.getDescription());
        domain.setEmail(model.getEmail());
        domain.setWebsite(model.getWebsite());
        domain.setPhone(model.getPhone());
        domain.setUniqueID(model.getUniqueID());
        domain.setPassword(model.getPassword());
        domain.setSalt(model.getSalt());
        //domain.setOldPassword(model.getOldPassword());
        //domain.setNewPassword(model.getNewPassword());
        domain.setCreatedDate(model.getCreatedDate());
        domain.setModifiedDate(model.getModifiedDate());
        domain.setSyncedDate(model.getSyncedDate());

        return domain;
    }
}
