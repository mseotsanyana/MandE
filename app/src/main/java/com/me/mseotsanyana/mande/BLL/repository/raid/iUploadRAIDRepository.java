package com.me.mseotsanyana.mande.BLL.repository.raid;

public interface iUploadRAIDRepository {
    /* delete functions */
    boolean deletePIMs();
    boolean deleteRAIDLIKELIHOODs();
    boolean deleteRAIDIMPACTs();
    boolean deleteROBOTs();
    boolean deletePIMSETs();

    boolean deleteRiskRegisters();
    boolean deleteAssumptionRegisters();
    boolean deleteIssueRegisters();
    boolean deleteDependencyRegisters();
    boolean deleteRAIDRegisters();
    boolean deleteRAIDLOGs();
    boolean deleteRiskConsequences();
    boolean deleteRiskRootCauses();
    boolean deleteRisks();
    boolean deleteAssumptions();
    boolean deleteIssues();
    boolean deleteDependencies();

    boolean deleteRiskReviews();
    boolean deleteAssumptionReviews();
    boolean deleteIssueReviews();
    boolean deleteDependencyReviews();
    boolean deleteMilestoneReviews();

    boolean deleteRiskActions();
    boolean deleteAssumptionActions();
    boolean deleteIssueActions();
    boolean deleteDependencyActions();
    boolean deleteActions();

    boolean deleteRiskCurrentControls();
    boolean deleteRiskAdditionalControls();
    boolean deleteRiskActionTypes();

    /* add functions */
    boolean addPIMFromExcel();
    boolean addRAIDLikelihoodFromExcel();
    boolean addRAIDImpactFromExcel();
    boolean addRobotFromExcel();
    boolean addPIMSETFromExcel();
    boolean addRAIDRegisterFromExcel();
    boolean addRAIDLOGFromExcel();
    boolean addRiskFromExcel();
    boolean addAssumptionFromExcel();
    boolean addIssueFromExcel();
    boolean addIssueCommentFromExcel();
    boolean addDependencyFromExcel();

    boolean addMilestoneReviewFromExcel();

    boolean addRiskActionTypeFromExcel();
    boolean addActionFromExcel();
}
