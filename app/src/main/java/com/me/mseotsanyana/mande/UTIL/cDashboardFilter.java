package com.me.mseotsanyana.mande.UTIL;

import com.me.mseotsanyana.mande.BLL.model.logframe.cActivityModel;
import com.me.mseotsanyana.mande.BLL.model.logframe.cOutcomeModel;
import com.me.mseotsanyana.mande.BLL.model.logframe.cOutputModel;
import com.me.mseotsanyana.mande.BLL.model.session.cOrganizationModel;
import com.me.mseotsanyana.multiselectspinnerlibrary.cKeyPairBoolData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mseotsanyana on 2017/10/27.
 */




public class cDashboardFilter {

    public static List<cKeyPairBoolData> getKeyPairBoolOrganizationTree(List<cOrganizationModel> organizationTree){
        List<cKeyPairBoolData> keyPairBoolOrganizationTree = new ArrayList<>();
        for (int i = 0; i < organizationTree.size(); i++) {
            cKeyPairBoolData idNameBool = new cKeyPairBoolData();
            //--idNameBool.setId((organizationTree.get(i)).getOrganizationID());
            idNameBool.setName((organizationTree.get(i)).getName());
            idNameBool.setObject(organizationTree.get(i));
            idNameBool.setSelected(false);
            keyPairBoolOrganizationTree.add(idNameBool);
        }
        return keyPairBoolOrganizationTree;
    }

//    public static List<cKeyPairBoolData> getKeyPairBoolGoalTree(List<cGoalDomain> goalTree){
//        List<cKeyPairBoolData> keyPairBoolGoalTree = new ArrayList<>();
//        for (int i = 0; i < goalTree.size(); i++) {
//            cKeyPairBoolData idNameBool = new cKeyPairBoolData();
//            idNameBool.setRefId((goalTree.get(i)).getOrganizationID());
//            idNameBool.setId((goalTree.get(i)).getGoalID());
//            idNameBool.setName((goalTree.get(i)).getGoalName());
//            idNameBool.setObject(goalTree.get(i));
//            idNameBool.setSelected(false);
//            keyPairBoolGoalTree.add(idNameBool);
//        }
//        return keyPairBoolGoalTree;
//    }

//    public static List<cKeyPairBoolData> getKeyPairBoolProjectTree(List<cProjectDomain> projectTree){
//        List<cKeyPairBoolData> keyPairBoolProjectTree = new ArrayList<>();
//        for (int i = 0; i < projectTree.size(); i++) {
//            cKeyPairBoolData idNameBool = new cKeyPairBoolData();
//            idNameBool.setRefId((projectTree.get(i)).getOverallAimID());
//            idNameBool.setId((projectTree.get(i)).getProjectID());
//            idNameBool.setName((projectTree.get(i)).getProjectName());
//            idNameBool.setObject(projectTree.get(i));
//            idNameBool.setSelected(false);
//            keyPairBoolProjectTree.add(idNameBool);
//        }
//        return keyPairBoolProjectTree;
//    }

    public static List<cKeyPairBoolData> getKeyPairBoolOutcomeTree(List<cOutcomeModel> outcomeTree){
        List<cKeyPairBoolData> keyPairBoolOutcomeTree = new ArrayList<>();
        for (int i = 0; i < outcomeTree.size(); i++) {
            cKeyPairBoolData idNameBool = new cKeyPairBoolData();
            idNameBool.setId((outcomeTree.get(i)).getComponentID());
            idNameBool.setName((outcomeTree.get(i)).getName());
            idNameBool.setObject(outcomeTree.get(i));
            idNameBool.setSelected(false);
            keyPairBoolOutcomeTree.add(idNameBool);
        }
        return keyPairBoolOutcomeTree;
    }

    public static List<cKeyPairBoolData> getKeyPairBoolOutputTree(List<cOutputModel> outputTree){
        List<cKeyPairBoolData> keyPairBoolOutputTree = new ArrayList<>();
        for (int i = 0; i < outputTree.size(); i++) {
            cKeyPairBoolData idNameBool = new cKeyPairBoolData();
            idNameBool.setId((outputTree.get(i)).getComponentID());
            idNameBool.setName((outputTree.get(i)).getName());
            idNameBool.setObject(outputTree.get(i));
            idNameBool.setSelected(false);
            keyPairBoolOutputTree.add(idNameBool);
        }
        return keyPairBoolOutputTree;
    }

    public static List<cKeyPairBoolData> getKeyPairBoolActivityTree(List<cActivityModel> activityTree){
        List<cKeyPairBoolData> keyPairBoolActivityTree = new ArrayList<>();
        for (int i = 0; i < activityTree.size(); i++) {
            cKeyPairBoolData idNameBool = new cKeyPairBoolData();
            //idNameBool.setId((activityTree.get(i)).getActivityID());
            idNameBool.setName((activityTree.get(i)).getName());
            idNameBool.setObject(activityTree.get(i));
            idNameBool.setSelected(false);
            keyPairBoolActivityTree.add(idNameBool);
        }
        return keyPairBoolActivityTree;
    }

}
