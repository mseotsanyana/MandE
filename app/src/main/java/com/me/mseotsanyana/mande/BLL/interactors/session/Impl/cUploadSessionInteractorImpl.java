package com.me.mseotsanyana.mande.BLL.interactors.session.Impl;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.session.iUploadSessionInteractor;
import com.me.mseotsanyana.mande.BLL.repository.session.iUploadSessionRepository;

public class cUploadSessionInteractorImpl extends cAbstractInteractor
        implements iUploadSessionInteractor {
    private Callback callback;/* this is actually PresenterImpl */
    private iUploadSessionRepository uploadSessionRepository; /* this is actually RepositoryImpl */

    public cUploadSessionInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                        iUploadSessionRepository uploadSessionRepository,
                                        Callback callback) {
        super(threadExecutor, mainThread);



        if (uploadSessionRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.uploadSessionRepository = uploadSessionRepository;
        this.callback = callback;
    }

    private void notifyError(String msg){
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onUploadingSessionFailed("Failed to Upload "+ msg +" Session Module !!");
            }
        });
    }

    private void postMessage(String msg){
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onUploadingSessionSucceeded(msg);
            }
        });
    }

    @Override
    public void run() {
        /* delete all session module records */

        uploadSessionRepository.deleteAddresses();
        uploadSessionRepository.deleteValues();
        uploadSessionRepository.deleteUsers();
        uploadSessionRepository.deleteOrganizations();
        uploadSessionRepository.deleteOrgAddresses();
        uploadSessionRepository.deleteBeneficiaries();
        uploadSessionRepository.deleteFunders();
        uploadSessionRepository.deleteImplementingAgencies();
        uploadSessionRepository.deleteMenuItems();
        uploadSessionRepository.deleteEntities();
        uploadSessionRepository.deleteOperations();
        uploadSessionRepository.deleteStatuses();
        uploadSessionRepository.deleteStatusSets();
        uploadSessionRepository.deletePermissions();
        uploadSessionRepository.deleteStatusSetStatus();
        uploadSessionRepository.deletePrivileges();
        uploadSessionRepository.deleteRoles();
        uploadSessionRepository.deleteUserRoles();
        uploadSessionRepository.deleteUserAddresses();
        uploadSessionRepository.deleteMenuRoles();
        uploadSessionRepository.deleteSettings();
        uploadSessionRepository.deleteNotifications();
        uploadSessionRepository.deletePublishers();
        uploadSessionRepository.deleteSubscribers();
        uploadSessionRepository.deleteNotifySettings();

        /* upload all session module records */

        if(uploadSessionRepository.addAddressFromExcel()){
            postMessage("Address Entity (in Session Module) Added Successfully!");
        }else {
            notifyError("ADDRESS");
        }

        if(uploadSessionRepository.addOrganizationFromExcel()){
            postMessage("Organization Entity (in Session Module) Added Successfully!");
        }else {
            notifyError("ORGANIZATION");
        }

        if(uploadSessionRepository.addMenuFromExcel()){
            postMessage("User Entity (in Session Module) Added Successfully!");
        }else {
            notifyError("USER");
        }

        if(uploadSessionRepository.addUserFromExcel()){
            postMessage("User Entity (in Session Module) Added Successfully!");
        }else {
            notifyError("USER");
        }

        if(uploadSessionRepository.addEntityFromExcel()){
            postMessage("Entity Entity (in Session Module) Added Successfully!");
        }else {
            notifyError("ENTITY");
        }

        if(uploadSessionRepository.addOperationFromExcel()){
            postMessage("Operation Entity (in Session Module) Added Successfully!");
        }else {
            notifyError("OPERATION");
        }

        if(uploadSessionRepository.addStatusFromExcel()){
            postMessage("Status Entity (in Session Module) Added Successfully!");
        }else {
            notifyError("STATUS");
        }

        if(uploadSessionRepository.addStatusSetFromExcel()){
            postMessage("StatusSet Entity (in Session Module) Added Successfully!");
        }else {
            notifyError("STATUSSET");
        }

        if(uploadSessionRepository.addPermissionFromExcel()){
            postMessage("Permission Entity (in Session Module) Added Successfully!");
        }else {
            notifyError("PERMISSION");
        }

        if(uploadSessionRepository.addRoleFromExcel()){
            postMessage("Role Entity (in Session Module) Added Successfully!");
        }else {
            notifyError("ROLE");
        }

        if(uploadSessionRepository.addSettingFromExcel()){
            postMessage("Setting Entity (in Session Module) Added Successfully!");
        }else {
            notifyError("SETTING");
        }

        if(uploadSessionRepository.addNotificationFromExcel()){
            postMessage("Notification Entity (in Session Module) Added Successfully!");
        }else {
            notifyError("NOTIFICATION");
        }
    }
}
