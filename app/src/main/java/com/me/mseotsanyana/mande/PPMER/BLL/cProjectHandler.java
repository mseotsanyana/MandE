package com.me.mseotsanyana.mande.PPMER.BLL;

import android.content.Context;
import android.database.Cursor;

import com.me.mseotsanyana.mande.PPMER.DAL.cProjectModel;
import com.me.mseotsanyana.mande.PPMER.DAL.cProjectDBA;

import java.util.ArrayList;
import java.util.List;


public class cProjectHandler extends cMapper<cProjectModel, cProjectDomain>
{
    private cProjectDBA projectDBA;
    private Context context;

    public cProjectHandler(Context context) {
        projectDBA = new cProjectDBA(context);
        this.context = context;
    }

    //public cProjectHandler() {
        //projectDb = new cProjectDBA();
    //}

    // business rules for adding a project
    public boolean addProject(cProjectDomain domain) {
        // mapp the business domain to the model
        cProjectModel model = this.DomainToModel(domain);
        return projectDBA.addProject(model);
    }

    // business rules for adding a project
    public boolean addProjectFromExcel(cProjectDomain domain) {
        //Toast.makeText(context, "TEST "+sheet.getPhysicalNumberOfRows(),
        //        Toast.LENGTH_SHORT).show();
        cProjectModel model = this.DomainToModel(domain);
        return projectDBA.addProjectFromExcel(model);
    }


    // business rules for deleting a project
    public boolean deleteProject(cProjectDomain domain) {
        // mapping the business domain to the model
        cProjectModel model = this.DomainToModel(domain);
        return projectDBA.deleteProject(model);
    }

    // business rules for deleting organization
    public boolean deleteAllProjects() {
        return projectDBA.deleteAllProjects();
    }

    // business rules for deleting a project
    public boolean updateProject(cProjectDomain domain) {
        // mapping the business domain to the model
        cProjectModel model = this.DomainToModel(domain);
        return projectDBA.updateProject(model);
    }

    public Cursor getAllProjects() {
        return projectDBA.getAllProject();
    }

    public ArrayList<cProjectDomain> getProjectList() {
        List<cProjectModel> projectModel = projectDBA.getProjectList();

        ArrayList<cProjectDomain> projectDomain = new ArrayList<>();
        cProjectDomain domain;

        for(int i = 0; i < projectModel.size(); i++) {
            domain = this.ModelToDomain(projectModel.get(i));
            projectDomain.add(domain);
        }

        return projectDomain;
    }

    /*
    public ArrayList<cTreeModel> getProjectTree(){
        List<cTreeModel> projectModelTree = projectDBA.getProjectTree();
        ArrayList<cTreeModel> projectDomainTree = new ArrayList<>();
        cProjectDomain projectDomain;


        for (int i = 0; i < projectModelTree.size(); i++) {
            int child = projectModelTree.get(i).getId();
            int parent = projectModelTree.get(i).getpId();
            int type = projectModelTree.get(i).getType();

            cGoalModel goalModel = (cGoalModel) goalModelTree.get(i).getModelObject();

            goalDomain = ModelToDomain(goalModel);

            goalDomainTree.add(new cTreeModel(child, parent, type, goalDomain));
        }

        return null;
    }
    */

    @Override
    protected cProjectDomain ModelToDomain(cProjectModel model) {
        cProjectDomain domain = new cProjectDomain();

        domain.setProjectID(model.getProjectID());
        domain.setOverallAimID(model.getOverallAimID());
        domain.setSpecificAimID(model.getSpecificAimID());
        domain.setOwnerID(model.getOwnerID());
        domain.setProjectManagerID(model.getProjectManagerID());
        domain.setProjectName(model.getProjectName());
        domain.setProjectDescription(model.getProjectDescription());
        domain.setCountry(model.getCountry());
        domain.setRegion(model.getRegion());
        domain.setProjectStatus(model.getProjectStatus());
        domain.setCreateDate(model.getCreateDate());
        domain.setStartDate(model.getStartDate());
        domain.setCloseDate(model.getCloseDate());

        return domain;
    }

    @Override
    protected cProjectModel DomainToModel(cProjectDomain domain) {
        cProjectModel model = new cProjectModel();

        model.setProjectID(domain.getProjectID());
        model.setOverallAimID(domain.getOverallAimID());
        model.setSpecificAimID(domain.getSpecificAimID());
        model.setOwnerID(domain.getOwnerID());
        model.setProjectManagerID(domain.getProjectManagerID());
        model.setProjectName(domain.getProjectName());
        model.setProjectDescription(domain.getProjectDescription());
        model.setCountry(domain.getCountry());
        model.setRegion(domain.getRegion());
        model.setProjectStatus(domain.getProjectStatus());
        model.setCreateDate(domain.getCreateDate());
        model.setStartDate(domain.getStartDate());
        model.setCloseDate(domain.getCloseDate());

        return model;
    }

}
