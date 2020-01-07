package com.me.mseotsanyana.mande.PPMER.BLL.repository;

import com.me.mseotsanyana.mande.PPMER.DAL.model.cQuestionModel;
import com.me.mseotsanyana.mande.PPMER.DAL.model.cRaidModel;
import com.me.mseotsanyana.mande.PPMER.DAL.model.cImpactModel;
import com.me.mseotsanyana.mande.PPMER.DAL.model.cOutcomeModel;

import java.util.Set;

public interface iOutcomeRepository {
    /* the create function of the Impact entity */
    boolean addOutcome(cOutcomeModel outcomeModel);

    /* the read functions of the Impact entity */
    Set<cOutcomeModel> getOutcomeModelSet(int userID, int orgID, int primaryRole,
                                          int secondaryRoles, int operationBITS, int statusBITS);
    Set<cImpactModel> getImpactModelSetByID(int impactID, int userID, int orgID, int primaryRole,
                                            int secondaryRoles, int operationBITS, int statusBITS);
    Set<cOutcomeModel> getOutcomeModelSetByID(int impactID, int userID, int orgID, int primaryRole,
                                              int secondaryRoles, int operationBITS, int statusBITS);
    Set<cQuestionModel> getQuestionModelSetByID(int impactID, int userID, int orgID, int primaryRole,
                                                int secondaryRoles, int operationBITS, int statusBITS);
    Set<cRaidModel> getRaidModelSetByID(int impactID, int userID, int orgID, int primaryRole,
                                        int secondaryRoles, int operationBITS, int statusBITS);
}
