package com.me.mseotsanyana.mande.BRBAC.BLL;

import android.content.Context;

import com.me.mseotsanyana.mande.BRBAC.DAL.cRoleModel;
import com.me.mseotsanyana.mande.BRBAC.DAL.cSessionModel;
import com.me.mseotsanyana.mande.PPMER.BLL.cMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mseotsanyana on 2017/08/29.
 */

public class cSessionRoleHandler extends cMapper<cSessionRoleModel, cSessionRoleDomain> {
    private Context context;
    private cSessionManager session;

    private cSessionRoleDBA sessionRoleDBA;
    private cSessionHandler sessionHandler;
    private cRoleHandler roleHandler;


    public cSessionRoleHandler(Context context, cSessionManager session) {
        sessionRoleDBA = new cSessionRoleDBA(context);
        sessionHandler = new cSessionHandler(context);
        roleHandler = new cRoleHandler(context, session);
        this.context = context;
        this.session = session;
    }

    // business rules for adding a session group
    public boolean addSessionRoleFromExcel(cSessionRoleDomain domain) {
        // map the business domain to the model.
        cSessionRoleModel model = this.DomainToModel(domain);
        return sessionRoleDBA.addSessionRoleFromExcel(model);
    }

    // business rules for adding a session group
    public boolean addSessionRole(cSessionRoleDomain domain) {
        // map the business domain to the model
        cSessionRoleModel model = this.DomainToModel(domain);
        return sessionRoleDBA.addSessionRole(model);
    }

    public ArrayList<cSessionRoleDomain> getSessionRoleList() {
        List<cSessionRoleModel> sessionRoleModel = sessionRoleDBA.getSessionRoleList();

        ArrayList<cSessionRoleDomain> sessionRoleDomains = new ArrayList<>();
        cSessionRoleDomain domain;

        for(int i = 0; i < sessionRoleModel.size(); i++) {
            domain = this.ModelToDomain(sessionRoleModel.get(i));
            sessionRoleDomains.add(domain);
        }

        return sessionRoleDomains;
    }

    public ArrayList<cSessionDomain> getSessionsByRoleID(int roleID){
        List<cSessionModel> sessionModels = sessionRoleDBA.getSessionsByRoleID(roleID);

        ArrayList<cSessionDomain> sessionDomains = new ArrayList<>();
        cSessionDomain domain;

        for(int i = 0; i < sessionModels.size(); i++) {
            domain = sessionHandler.ModelToDomain(sessionModels.get(i));
            sessionDomains.add(domain);
        }

        return sessionDomains;

    }

    public ArrayList<cRoleDomain> getRolesBySessionID(int sessionID){
        List<cRoleModel> roleModels = sessionRoleDBA.getRolesBySessionID(sessionID);

        ArrayList<cRoleDomain> roleDomains = new ArrayList<>();
        cRoleDomain domain;

        for(int i = 0; i < roleModels.size(); i++) {
            domain = roleHandler.ModelToDomain(roleModels.get(i));
            roleDomains.add(domain);
        }

        return roleDomains;

    }

    public ArrayList<Integer> getRoleIDsBySessionID(int sessionID){
        return sessionRoleDBA.getRoleIDsBySessionID(sessionID);
    }

    @Override
    protected cSessionRoleModel DomainToModel(cSessionRoleDomain domain) {
        cSessionRoleModel model = new cSessionRoleModel();

        model.setSessionID(domain.getSessionID());
        model.setRoleID(domain.getRoleID());
        model.setOwnerID(domain.getOwnerID());
        model.setGroupBITS(domain.getGroupBITS());
        model.setPermsBITS(domain.getPermsBITS());
        model.setStatusBITS(domain.getStatusBITS());
        model.setCreateDate(domain.getCreateDate());

        return model;
    }

    @Override
    protected cSessionRoleDomain ModelToDomain(cSessionRoleModel model) {
        cSessionRoleDomain domain = new cSessionRoleDomain();

        domain.setSessionID(model.getSessionID());
        domain.setRoleID(model.getRoleID());
        domain.setOwnerID(model.getOwnerID());
        domain.setGroupBITS(model.getGroupBITS());
        domain.setPermsBITS(model.getPermsBITS());
        domain.setStatusBITS(model.getStatusBITS());
        domain.setCreateDate(model.getCreateDate());

        return domain;
    }
}
