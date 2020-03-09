package com.me.mseotsanyana.mande.PPMER.BLL.repository;

public interface iUploadGlobalRepository {
    boolean deleteFrequencies();
    boolean deletePeriods();
    boolean deleteFiscalYears();

    boolean addFrequencyFromExcel();
    boolean addFiscalYearFromExcel();
}
