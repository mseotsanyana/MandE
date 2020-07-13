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
    Set<cLogFrameModel> getLogFrameModelSet(long userID, int primaryRoleBITS,
                                            int secondaryRoleBITS, int statusBITS);
    Set<cImpactModel> getImpactModelSetByID(long logFrameID, long userID, int primaryRole,
                                                int secondaryRoles, int statusBITS);
    Set<cOutcomeModel> getOutcomeModelSetByID(long logFrameID, long userID, int primaryRole,
                                                int secondaryRoles, int statusBITS);
    Set<cOutputModel> getOutputModelSetByID(long logFrameID, long userID, int primaryRole,
                                                int secondaryRoles, int statusBITS);
    Set<cActivityModel> getActivityModelSetByID(long logFrameID, long userID, int primaryRole,
                                                int secondaryRoles, int statusBITS);
    Set<cInputModel> getInputModelSetByID(long logFrameID, long userID, int primaryRole,
                                                int secondaryRoles, int statusBITS);

    ArrayList<cLogFrameModel> getLogFrameModels();
    ArrayList<cLogFrameModel> getChildLogFramesByID(long parentID);
    ArrayList<cImpactModel> getImpactsByID(long logFrameID);
    ArrayList<cOutcomeModel> getOutcomesByID(long logFrameID);
    ArrayList<cOutputModel> getOutputsByID(long logFrameID);
    ArrayList<cActivityModel> getActivitiesByID(long logFrameID);
    ArrayList<cInputModel> getInputsByID(long logFrameID);
    ArrayList<cQuestionModel> getQuestionsByID(long logFrameID);
    ArrayList<cIndicatorModel> getIndicatorsByID(long logFrameID);
    ArrayList<cRaidModel> getRaidsByID(long logFrameID);
    boolean deleteLogFrame(long logFrameID);
    boolean deleteSubLogFrame(long logFrameID);
    boolean deleteLogFrames();

    /* emit a set of logFrames */
    //Observable<Set<cLogFrameModel>> getLogFrameModelSet();
}
