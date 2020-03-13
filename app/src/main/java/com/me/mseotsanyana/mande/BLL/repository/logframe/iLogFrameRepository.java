package com.me.mseotsanyana.mande.BLL.repository.logframe;

import com.me.mseotsanyana.mande.BLL.domain.logframe.cLogFrameDomain;
import com.me.mseotsanyana.mande.DAL.model.logframe.cActivityModel;
import com.me.mseotsanyana.mande.DAL.model.logframe.cImpactModel;
import com.me.mseotsanyana.mande.DAL.model.monitor.cIndicatorModel;
import com.me.mseotsanyana.mande.DAL.model.logframe.cInputModel;
import com.me.mseotsanyana.mande.DAL.model.logframe.cLogFrameModel;
import com.me.mseotsanyana.mande.DAL.model.logframe.cOutcomeModel;
import com.me.mseotsanyana.mande.DAL.model.logframe.cOutputModel;
import com.me.mseotsanyana.mande.DAL.model.logframe.cQuestionModel;
import com.me.mseotsanyana.mande.DAL.model.logframe.cRaidModel;

import java.util.ArrayList;
import java.util.Set;

public interface iLogFrameRepository {
    /* the create function of the LogFrame entity */
    boolean addLogFrameFromExcel();
    boolean addLogFrame(cLogFrameDomain logFrameDomain);

    /* the read functions of the LogFrame entity */
    Set<cLogFrameModel> getLogFrameModelSet(int logFrameID, int userID, int orgID, int primaryRole,
                                            int secondaryRoles, int operationBITS, int statusBITS);
    Set<cImpactModel> getImpactModelSetByID(int logFrameID, int userID, int orgID, int primaryRole,
                                                int secondaryRoles, int operationBITS, int statusBITS);
    Set<cOutcomeModel> getOutcomeModelSetByID(int logFrameID, int userID, int orgID, int primaryRole,
                                                int secondaryRoles, int operationBITS, int statusBITS);
    Set<cOutputModel> getOutputModelSetByID(int logFrameID, int userID, int orgID, int primaryRole,
                                                int secondaryRoles, int operationBITS, int statusBITS);
    Set<cActivityModel> getActivityModelSetByID(int logFrameID, int userID, int orgID, int primaryRole,
                                                int secondaryRoles, int operationBITS, int statusBITS);
    Set<cInputModel> getInputModelSetByID(int logFrameID, int userID, int orgID, int primaryRole,
                                                int secondaryRoles, int operationBITS, int statusBITS);

    ArrayList<cLogFrameModel> getLogFrameModels();
    ArrayList<cLogFrameModel> getChildLogFramesByID(int parentID);
    ArrayList<cImpactModel> getImpactsByID(int logFrameID);
    ArrayList<cOutcomeModel> getOutcomesByID(int logFrameID);
    ArrayList<cOutputModel> getOutputsByID(int logFrameID);
    ArrayList<cActivityModel> getActivitiesByID(int logFrameID);
    ArrayList<cInputModel> getInputsByID(int logFrameID);
    ArrayList<cQuestionModel> getQuestionsByID(int logFrameID);
    ArrayList<cIndicatorModel> getIndicatorsByID(int logFrameID);
    ArrayList<cRaidModel> getRaidsByID(int logFrameID);
    boolean deleteLogFrame(cLogFrameModel logFrameModel);
    boolean deleteLogFrames();


    /* emit a set of logFrames */
    //Observable<Set<cLogFrameModel>> getLogFrameModelSet();
}