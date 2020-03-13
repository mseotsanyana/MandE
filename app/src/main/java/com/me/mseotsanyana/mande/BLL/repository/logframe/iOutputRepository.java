package com.me.mseotsanyana.mande.BLL.repository.logframe;

import com.me.mseotsanyana.mande.DAL.model.logframe.cQuestionModel;
import com.me.mseotsanyana.mande.DAL.model.logframe.cRaidModel;
import com.me.mseotsanyana.mande.DAL.model.logframe.cImpactModel;
import com.me.mseotsanyana.mande.DAL.model.logframe.cOutcomeModel;

import java.util.Set;

public interface iOutputRepository {
    /* the create function of the Impact entity */
    boolean addImpact(cImpactModel impactModel);

    /* the read functions of the Impact entity */
    Set<cImpactModel> getImpactModelSet(int userID, int orgID, int primaryRole,
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
