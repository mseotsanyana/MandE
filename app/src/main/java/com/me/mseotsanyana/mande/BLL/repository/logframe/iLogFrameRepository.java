package com.me.mseotsanyana.mande.BLL.repository.logframe;

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
import java.util.Date;
import java.util.Set;

public interface iLogFrameRepository {
    /* the create function of the LogFrame entity */
    boolean addLogFrameFromExcel();
    boolean createLogFrameModel(cLogFrameModel logFrameModel);
    boolean createSubLogFrameModel(long logFrameID, cLogFrameModel logFrameModel);
    boolean updateLogFrameModel(cLogFrameModel logFrameModel);

    /* the read functions of the LogFrame entity */
    Set<cLogFrameModel> getLogFrameModelSet(int userID, int primaryRoleBITS,
                                            int secondaryRoleBITS, int statusBITS);
    Set<cImpactModel> getImpactModelSetByID(int logFrameID, int userID, int primaryRole,
                                                int secondaryRoles, int statusBITS);
    Set<cOutcomeModel> getOutcomeModelSetByID(int logFrameID, int userID, int primaryRole,
                                                int secondaryRoles, int statusBITS);
    Set<cOutputModel> getOutputModelSetByID(int logFrameID, int userID, int primaryRole,
                                                int secondaryRoles, int statusBITS);
    Set<cActivityModel> getActivityModelSetByID(int logFrameID, int userID, int primaryRole,
                                                int secondaryRoles, int statusBITS);
    Set<cInputModel> getInputModelSetByID(int logFrameID, int userID, int primaryRole,
                                                int secondaryRoles, int statusBITS);

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
    boolean deleteLogFrame(long logFrameID);
    boolean deleteSubLogFrame(long logFrameID);
    boolean deleteLogFrames();

    /* emit a set of logFrames */
    //Observable<Set<cLogFrameModel>> getLogFrameModelSet();
}
