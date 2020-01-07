package com.me.mseotsanyana.mande.PPMER.DAL;

import com.me.mseotsanyana.mande.PPMER.DAL.model.cActivityModel;
import com.me.mseotsanyana.mande.PPMER.DAL.model.cImpactModel;
import com.me.mseotsanyana.mande.PPMER.DAL.model.cLogFrameModel;
import com.me.mseotsanyana.mande.PPMER.DAL.model.cOutcomeModel;
import com.me.mseotsanyana.mande.PPMER.DAL.model.cOutputModel;

import java.util.Date;
import java.util.Map;
import java.util.Set;

public class cLogFrameTreeModel {
    private int parentID;
    private int childID;
    private int serverID;
    private int orgID;
    private int ownerID;
    private int groupBITS;
    private int permsBITS;
    private int statusBITS;
    private Date createdDate;
    private Date modifiedDate;
    private Date syncedDate;

    private cLogFrameModel parentLogFrameModel;
    private cLogFrameModel childLogFrameModel;

    private Map<cOutcomeModel, Set<cImpactModel>> impactModelSetMap;
    private Map<cOutputModel, Set<cOutcomeModel>> outcomeModelSetMap;
    private Map<cActivityModel, Set<cOutputModel>> outputModelSetMap;
    private Set<cActivityModel> activityModelSet;


}

