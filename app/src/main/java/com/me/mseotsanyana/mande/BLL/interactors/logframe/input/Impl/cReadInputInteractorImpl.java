package com.me.mseotsanyana.mande.BLL.interactors.logframe.input.Impl;

import android.util.Log;

import com.google.gson.Gson;
import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.logframe.input.iReadInputInteractor;
import com.me.mseotsanyana.mande.BLL.repository.session.iSessionManagerRepository;
import com.me.mseotsanyana.mande.BLL.repository.wpb.iExpenseRepository;
import com.me.mseotsanyana.mande.BLL.repository.wpb.iHumanRepository;
import com.me.mseotsanyana.mande.BLL.repository.wpb.iIncomeRepository;
import com.me.mseotsanyana.mande.BLL.repository.wpb.iMaterialRepository;
import com.me.mseotsanyana.mande.DAL.model.logframe.cActivityModel;
import com.me.mseotsanyana.mande.DAL.model.logframe.cQuestionModel;
import com.me.mseotsanyana.mande.DAL.model.session.cUserModel;
import com.me.mseotsanyana.mande.DAL.model.wpb.cExpenseModel;
import com.me.mseotsanyana.mande.DAL.model.wpb.cHumanModel;
import com.me.mseotsanyana.mande.DAL.model.wpb.cIncomeModel;
import com.me.mseotsanyana.mande.DAL.model.wpb.cJournalModel;
import com.me.mseotsanyana.mande.DAL.model.wpb.cMaterialModel;
import com.me.mseotsanyana.mande.DAL.storage.preference.cBitwise;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class cReadInputInteractorImpl extends cAbstractInteractor
        implements iReadInputInteractor {
    private static String TAG = cReadInputInteractorImpl.class.getSimpleName();

    private Callback callback;
    private iHumanRepository humanRepository;
    private iMaterialRepository materialRepository;
    private iIncomeRepository incomeRepository;
    private iExpenseRepository expenseRepository;

    private long userID, logFrameID;
    private int primaryRoleBITS, secondaryRoleBITS, operationBITS, statusBITS;

    public cReadInputInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                    iSessionManagerRepository sessionManagerRepository,
                                    iHumanRepository humanRepository,
                                    iMaterialRepository materialRepository,
                                    iIncomeRepository incomeRepository,
                                    iExpenseRepository expenseRepository,
                                    Callback callback, long logFrameID) {
        super(threadExecutor, mainThread);

        if (sessionManagerRepository == null || humanRepository == null ||
                materialRepository == null || incomeRepository == null ||
                expenseRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.humanRepository = humanRepository;
        this.materialRepository = materialRepository;
        this.incomeRepository = incomeRepository;
        this.expenseRepository = expenseRepository;
        this.callback = callback;

        this.logFrameID = logFrameID;

        /* common attributes */
        this.userID = sessionManagerRepository.loadUserID();
        this.primaryRoleBITS = sessionManagerRepository.loadPrimaryRoleBITS();
        this.secondaryRoleBITS = sessionManagerRepository.loadSecondaryRoleBITS();

        /* attributes related to INPUT entity */
        this.operationBITS = sessionManagerRepository.loadOperationBITS(
                cBitwise.INPUT, cBitwise.LOGFRAME_MODULE);
        this.statusBITS = sessionManagerRepository.loadStatusBITS(
                cBitwise.INPUT, cBitwise.LOGFRAME_MODULE, cBitwise.READ);
    }

    /* */
    private void notifyError(String msg) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onInputModelsFailed(msg);
            }
        });
    }

    /* */
    private void postMessage(Map<Integer, ArrayList<cTreeModel>> inputTreeMap) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onInputModelsRetrieved(inputTreeMap);
            }
        });
    }

    /**
     * returns human resources
     * @param humanModelSet set of users
     * @return tree
     */
    private ArrayList<cTreeModel> getHumanModelTree(Set<cHumanModel> humanModelSet) {
        ArrayList<cHumanModel> humanModels = new ArrayList<>(humanModelSet);
        ArrayList<cTreeModel> humanTreeModels = new ArrayList<>();

        int parentIndex = 0, childIndex = 0;
        for (int i = 0; i < humanModels.size(); i++) {
            /* input model */
            cHumanModel humanModel = humanModels.get(i);

            ArrayList<cUserModel> users = new ArrayList<>(
                    humanModel.getUserModelSet());
            ArrayList<cActivityModel> activities = new ArrayList<>(
                    humanModel.getChildActivityModelSet());
            ArrayList<cQuestionModel> questions = new ArrayList<>(
                    humanModel.getQuestionModelSet());
            ArrayList<cJournalModel> journals = new ArrayList<>(
                    humanModel.getJournalModelSet());

            /* human resources */
            humanTreeModels.add(new cTreeModel(parentIndex, -1, 0, humanModel));
            /* set of users under the input */
            if (users.size() > 0) {
                childIndex = parentIndex + 1;
                humanTreeModels.add(new cTreeModel(childIndex, parentIndex, 1, users));
            }
            /* set of activity children under the sub-logframe of the input logframe */
            if (activities.size() > 0) {
                childIndex = parentIndex + 2;
                humanTreeModels.add(new cTreeModel(childIndex, parentIndex, 1, activities));
            }
            /* set of questions under the input */
            if (questions.size() > 0) {
                childIndex = parentIndex + 3;
                humanTreeModels.add(new cTreeModel(childIndex, parentIndex, 1, questions));
            }
            /* set of journals under the input */
            if (journals.size() > 0) {
                childIndex = parentIndex + 4;
                humanTreeModels.add(new cTreeModel(childIndex, parentIndex, 1, journals));
            }

            /* next parent index */
            parentIndex = childIndex + 1;
        }

        return humanTreeModels;
    }

    /**
     * returns material resources
     *
     * @param materialModelSet materials
     * @return tree
     */
    private ArrayList<cTreeModel> getMaterialModelTree(Set<cMaterialModel> materialModelSet) {
        ArrayList<cMaterialModel> materialModels = new ArrayList<>(materialModelSet);
        ArrayList<cTreeModel> materialTreeModels = new ArrayList<>();

        int parentIndex = 0, childIndex = 0;
        for (int i = 0; i < materialModels.size(); i++) {
            /* input model */
            cMaterialModel materialModel = materialModels.get(i);

            ArrayList<cActivityModel> activities = new ArrayList<>(
                    materialModel.getChildActivityModelSet());
            ArrayList<cQuestionModel> questions = new ArrayList<>(
                    materialModel.getQuestionModelSet());
            ArrayList<cJournalModel> journals = new ArrayList<>(
                    materialModel.getJournalModelSet());

            /* material resources */
            materialTreeModels.add(new cTreeModel(parentIndex, -1, 0, materialModel));

            /* set of activity children under the sub-logframe of the input logframe */
            if (activities.size() > 0) {
                childIndex = parentIndex + 1;
                materialTreeModels.add(new cTreeModel(childIndex, parentIndex, 1, activities));
            }
            /* set of questions under the input */
            if (questions.size() > 0) {
                childIndex = parentIndex + 2;
                materialTreeModels.add(new cTreeModel(childIndex, parentIndex, 1, questions));
            }
            /* set of journals under the input */
            if (journals.size() > 0) {
                childIndex = parentIndex + 3;
                materialTreeModels.add(new cTreeModel(childIndex, parentIndex, 1, journals));
            }

            /* next parent index */
            parentIndex = childIndex + 1;
        }

        return materialTreeModels;
    }

    /**
     * returns income resources
     *
     * @param incomeModelSet incomes/funds
     * @return tree
     */
    private ArrayList<cTreeModel> getIncomeModelTree(Set<cIncomeModel> incomeModelSet) {
        ArrayList<cIncomeModel> incomeModels = new ArrayList<>(incomeModelSet);
        ArrayList<cTreeModel> incomeTreeModels = new ArrayList<>();

        /* income resources */
        int parentIndex = 0, childIndex = 0;
        for (int i = 0; i < incomeModels.size(); i++) {
            /* income model */
            cIncomeModel incomeModel = incomeModels.get(i);

            ArrayList<cActivityModel> activities = new ArrayList<>(
                    incomeModel.getChildActivityModelSet());
            ArrayList<cQuestionModel> questions = new ArrayList<>(
                    incomeModel.getQuestionModelSet());
            ArrayList<cJournalModel> journals = new ArrayList<>(
                    incomeModel.getJournalModelSet());

            incomeTreeModels.add(new cTreeModel(parentIndex, -1, 0, incomeModel));

            /* set of activity children under the sub-logframe of the input logframe */
            if (activities.size() > 0) {
                childIndex = parentIndex + 1;
                incomeTreeModels.add(new cTreeModel(childIndex, parentIndex, 1, activities));
            }
            /* set of questions under the input */
            if (questions.size() > 0) {
                childIndex = parentIndex + 2;
                incomeTreeModels.add(new cTreeModel(childIndex, parentIndex, 1, questions));
            }
            /* set of journals under the input */
            if (journals.size() > 0) {
                childIndex = parentIndex + 3;
                incomeTreeModels.add(new cTreeModel(childIndex, parentIndex, 1, journals));
            }

            /* next parent index */
            parentIndex = childIndex + 1;
        }

        return incomeTreeModels;
    }

    /**
     * returns planned expenditure
     *
     * @param expenseModelSet expenses
     * @return tree
     */
    private ArrayList<cTreeModel> getExpenseModelTree(Set<cExpenseModel> expenseModelSet) {
        ArrayList<cExpenseModel> expenseModels = new ArrayList<>(expenseModelSet);
        ArrayList<cTreeModel> expenseTreeModels = new ArrayList<>();

        /* expense resources */
        int parentIndex = 0, childIndex = 0;
        for (int i = 0; i < expenseModels.size(); i++) {
            /* material model */
            cExpenseModel expenseModel = expenseModels.get(i);

            ArrayList<cActivityModel> activities = new ArrayList<>(
                    expenseModel.getChildActivityModelSet());
            ArrayList<cQuestionModel> questions = new ArrayList<>(
                    expenseModel.getQuestionModelSet());
            ArrayList<cJournalModel> journals = new ArrayList<>(
                    expenseModel.getJournalModelSet());

            expenseTreeModels.add(new cTreeModel(parentIndex, -1, 0, expenseModel));

            /* set of activity children under the sub-logframe of the input logframe */
            if (activities.size() > 0) {
                childIndex = parentIndex + 1;
                expenseTreeModels.add(new cTreeModel(childIndex, parentIndex, 1, activities));
            }
            /* set of questions under the input */
            if (questions.size() > 0) {
                childIndex = parentIndex + 2;
                expenseTreeModels.add(new cTreeModel(childIndex, parentIndex, 1, questions));
            }
            /* set of journals under the input */
            if (journals.size() > 0) {
                childIndex = parentIndex + 3;
                expenseTreeModels.add(new cTreeModel(childIndex, parentIndex, 1, journals));
            }

            /* next parent index */
            parentIndex = childIndex + 1;
        }

        return expenseTreeModels;
    }

    @Override
    public void run() {
        if ((operationBITS & cBitwise.READ) != 0) {

            /* retrieve a set logFrames from the database */
            Log.d(TAG, "LOGFRAME ID = " + logFrameID + "; USER ID = " + userID +
                    "; PRIMARY = " + primaryRoleBITS + "; SECONDARY = " + secondaryRoleBITS +
                    "; STATUS = " + statusBITS);

            Set<cHumanModel> humanModelSet = humanRepository.getHumanModelSet(
                    logFrameID, userID, primaryRoleBITS, secondaryRoleBITS, statusBITS);
            Set<cMaterialModel> materialModelSet = materialRepository.getMaterialModelSet(
                    logFrameID, userID, primaryRoleBITS, secondaryRoleBITS, statusBITS);
            Set<cIncomeModel> incomeModelSet = incomeRepository.getIncomeModelSet(
                    logFrameID, userID, primaryRoleBITS, secondaryRoleBITS, statusBITS);
            Set<cExpenseModel> expenseModelSet = expenseRepository.getExpenseModelSet(
                    logFrameID, userID, primaryRoleBITS, secondaryRoleBITS, statusBITS);

            if (humanModelSet != null && materialModelSet != null && incomeModelSet != null &&
                    expenseModelSet != null) {
                Gson gson = new Gson();
                Log.d(TAG, "HUMAN SIZE = " + gson.toJson(humanModelSet.size()));
                Log.d(TAG, "MATERIAL SIZE = " + gson.toJson(materialModelSet.size()));
                Log.d(TAG, "INCOME SIZE = " + gson.toJson(incomeModelSet.size()));
                Log.d(TAG, "EXPENSE SIZE = " + gson.toJson(expenseModelSet.size()));

                /* get the tree resources */
                ArrayList<cTreeModel> humanModelTree = getHumanModelTree(humanModelSet);
                ArrayList<cTreeModel> materialModelTree = getMaterialModelTree(materialModelSet);
                ArrayList<cTreeModel> incomeModelTree = getIncomeModelTree(incomeModelSet);
                ArrayList<cTreeModel> expenseModelTree = getExpenseModelTree(expenseModelSet);

                /* update the resources map */
                Map<Integer, ArrayList<cTreeModel>> inputTreeMap = new HashMap<>();
                inputTreeMap.put(1, humanModelTree);
                inputTreeMap.put(2, materialModelTree);
                inputTreeMap.put(3, incomeModelTree);
                inputTreeMap.put(4, expenseModelTree);

                Log.d(TAG, "INPUT TREE SIZE = " + gson.toJson(inputTreeMap.size()));

                postMessage(inputTreeMap);
            } else {
                notifyError("Failed to read inputs !!");
            }
        } else {
            notifyError("Failed due to reading access rights !!");
        }
    }
}
