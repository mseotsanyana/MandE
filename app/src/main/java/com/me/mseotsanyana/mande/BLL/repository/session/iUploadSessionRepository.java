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

    /* add (create) functions */
    boolean addAddressFromExcel();
    boolean addOrganizationFromExcel();
}
