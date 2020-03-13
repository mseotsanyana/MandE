package com.me.mseotsanyana.mande.BLL.repository.raid;

public interface iUploadRAIDRepository {
    /* delete functions */
    boolean deleteRegisters();
    boolean deleteRiskLikelihoods();
    boolean deleteRiskLikelihoodSets();
    boolean deleteRiskImpacts();
    boolean deleteRiskImpactSets();
    boolean deleteRiskCriteria();
    boolean deleteRiskCriteriaSets();
    boolean deleteRisks();
    boolean deleteRiskConsequences();
    boolean deleteRiskRootCauses();
    boolean deleteRiskAnalysis();
    boolean deleteRiskActionTypes();
    boolean deleteRiskActions();
    boolean deleteRiskCurrentControls();
    boolean deleteRiskAdditionalControls();

    /* add functions */
    boolean addRegisterFromExcel();
    boolean addRiskLikelihoodFromExcel();
    boolean addRiskImpactFromExcel();
    boolean addRiskCriteriaFromExcel();
    boolean addRiskFromExcel();
    boolean addRiskAnalysisFromExcel();
    boolean addRiskActionTypeFromExcel();
    boolean addRiskActionFromExcel();

}
