package com.me.mseotsanyana.mande.BLL.repository.global;

public interface iUploadGlobalRepository {
    boolean deleteFrequencies();
    boolean deletePeriods();
    boolean deleteFiscalYears();

    boolean addFrequencyFromExcel();
    boolean addFiscalYearFromExcel();
}
