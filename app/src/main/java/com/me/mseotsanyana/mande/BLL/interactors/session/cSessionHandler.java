package com.me.mseotsanyana.mande.BLL.interactors.session;

import android.content.Context;

import com.me.mseotsanyana.mande.BLL.domain.session.cSessionDomain;
import com.me.mseotsanyana.mande.DAL.ìmpl.session.cSessionImpl;
import com.me.mseotsanyana.mande.DAL.model.session.cSessionModel;
import com.me.mseotsanyana.mande.UTIL.BLL.cMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mseotsanyana on 2017/08/28.
 */

public class cSessionHandler extends cMapper<cSessionModel, cSessionDomain> {
    private cSessionImpl sessionDBA;
    private Context context;

    public cSessionHandler(Context context) {
        sessionDBA = new cSessionImpl(context);
        this.context = context;
    }

    public cSessionHandler() {

    }

    public boolean deleteAllSessions() {
        return sessionDBA.deleteSessions();
    }

    public boolean addSessionFromExcel(cSessionDomain domain) {
        // map the business domain to the model
        cSessionModel model = this.DomainToModel(domain);
        return sessionDBA.addSessionFromExcel(model);
    }

    public boolean addSession(cSessionDomain domain) {
        // map the business domain to the model
        cSessionModel model = this.DomainToModel(domain);
        return sessionDBA.addSession(model);
    }

    public cSessionDomain getSessionByID(int sessionID){
        cSessionModel sessionModel = sessionDBA.getSessionByID(sessionID);
        cSessionDomain sessionDomain = this.ModelToDomain(sessionModel);

        return sessionDomain;
    }

    public cSessionDomain getSessionByEmailPassword(String email, String password){
        cSessionDomain sessionDomain = null;
        cSessionModel sessionModel = sessionDBA.getSessionByEmailPassword(email, password);
        if (sessionModel != null)
            sessionDomain = this.ModelToDomain(sessionModel);
        return sessionDomain;
    }

    public ArrayList<cSessionDomain> getSessionList() {
        List<cSessionModel> sessionModel = null;//sessionDBA.getSessionList();

        ArrayList<cSessionDomain> sessionDomain = new ArrayList<>();
        cSessionDomain domain;

        for(int i = 0; i < sessionModel.size(); i++) {
            domain = this.ModelToDomain(sessionModel.get(i));
            sessionDomain.add(domain);
        }

        return sessionDomain;
    }

    public boolean checkSession(String email){
        return sessionDBA.checkSession(email);
    }

    public cSessionDomain checkSession(String email, String password){
        cSessionModel model = sessionDBA.checkSession(email, password);
        cSessionDomain domain = this.ModelToDomain(model);
        return domain;
    }


    @Override
    protected cSessionModel DomainToModel(cSessionDomain domain) {
        cSessionModel model = new cSessionModel();

        model.setSessionID(domain.getSessionID());
        model.setUserID(domain.getUserID());
        model.setOwnerID(domain.getOwnerID());
        model.setGroupBITS(domain.getGroupBITS());
        model.setPermsBITS(domain.getPermsBITS());
        model.setStatusBITS(domain.getStatusBITS());
        /*model.setName(domain.getName());
        model.setSurname(domain.getSurname());
        model.setDescription(domain.getDescription());
        model.setEmail(domain.getEmail());
        model.setUniqueID(domain.getUniqueID());
        model.setPassword(domain.getPassword());
        model.setSalt(domain.getSalt());
        model.setOldPassword(domain.getOldPassword());
        model.setNewPassword(domain.getNewPassword());
        model.setCreateDate(domain.getCreateDate());*/

        return model;
    }

    @Override
    protected cSessionDomain ModelToDomain(cSessionModel model) {
        cSessionDomain domain = new cSessionDomain();
        domain.setSessionID(model.getSessionID());
        domain.setUserID(model.getUserID());
        domain.setOwnerID(model.getOwnerID());
        domain.setGroupBITS(model.getGroupBITS());
        domain.setPermsBITS(model.getPermsBITS());
        domain.setStatusBITS(model.getStatusBITS());
        /*domain.setName(model.getName());
        domain.setSurname(model.getSurname());
        domain.setDescription(model.getDescription());
        domain.setEmail(model.getEmail());
        domain.setUniqueID(model.getUniqueID());
        domain.setPassword(model.getPassword());
        domain.setSalt(model.getSalt());
        domain.setOldPassword(model.getOldPassword());
        domain.setNewPassword(model.getNewPassword());
        domain.setCreateDate(model.getCreateDate());*/

        return domain;
    }
}
