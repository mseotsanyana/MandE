package com.me.mseotsanyana.mande.BLL.repository.session;

public interface iUploadSessionRepository {
    boolean deleteAddresses();
    boolean deleteValues();
    boolean deleteUsers();
    boolean deleteOrganizations();
    boolean deleteOrgAddresses();
    boolean deleteBeneficiaries();
    boolean deleteFunders();
    boolean deleteImplementingAgencies();
    boolean deleteMenuItems();
    boolean deleteEntities();
    boolean deleteOperations();
    boolean deleteStatuses();
    boolean deleteStatusSets();
    boolean deleteStatusSetStatus();
    boolean deletePermissions();
    boolean deleteRoles();
    boolean deleteUserRoles();
    boolean deleteUserAddresses();
    boolean deleteMenuRoles();
    boolean deletePrivileges();
    boolean deleteSettings();
    boolean deleteNotifications();
    boolean deletePublishers();
    boolean deleteSubscribers();
    boolean deleteNotifySettings();

    /* add (create) functions */
    boolean addAddressFromExcel();
    boolean addOrganizationFromExcel();
    boolean addUserFromExcel();
    boolean addMenuFromExcel();
    boolean addEntityFromExcel();
    boolean addOperationFromExcel();
    boolean addStatusFromExcel();
    boolean addStatusSetFromExcel();
    boolean addPermissionFromExcel();
    boolean addRoleFromExcel();
    boolean addSettingFromExcel();
    boolean addNotificationFromExcel();
}
