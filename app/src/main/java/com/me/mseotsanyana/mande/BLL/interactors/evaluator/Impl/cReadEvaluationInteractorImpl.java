package com.me.mseotsanyana.mande.BLL.interactors.evaluator.Impl;

import android.util.Log;

import com.google.gson.Gson;
import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.evaluator.iReadEvaluationInteractor;
import com.me.mseotsanyana.mande.BLL.repository.evaluator.iEvaluationRepository;
import com.me.mseotsanyana.mande.BLL.repository.session.iSessionManagerRepository;
import com.me.mseotsanyana.mande.BLL.model.evaluator.cEvaluationModel;
import com.me.mseotsanyana.mande.BLL.model.logframe.cQuestionModel;
import com.me.mseotsanyana.mande.BLL.model.session.cUserModel;
import com.me.mseotsanyana.mande.DAL.storage.preference.cBitwise;
import com.me.mseotsanyana.questionnairelibrary.forms.db.cDBQuestion;
import com.me.mseotsanyana.questionnairelibrary.forms.db.cDBQuestionnaire;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.stream.Collectors;

public class cReadEvaluationInteractorImpl extends cAbstractInteractor
        implements iReadEvaluationInteractor {
    private static String TAG = cReadEvaluationInteractorImpl.class.getSimpleName();

    private Callback callback;
    private iEvaluationRepository evaluationRepository;
    private long userID, logFrameID;
    private int primaryRoleBITS, secondaryRoleBITS, operationBITS, statusBITS;

    private String logFrameName;

    public cReadEvaluationInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                         iSessionManagerRepository sessionManagerRepository,
                                         iEvaluationRepository evaluationRepository,
                                         Callback callback, long logFrameID) {
        super(threadExecutor, mainThread);

        if (sessionManagerRepository == null || evaluationRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.evaluationRepository = evaluationRepository;
        this.callback = callback;

        this.logFrameID = logFrameID;

        /* common attributes */
        this.userID = sessionManagerRepository.loadUserID();
        this.primaryRoleBITS = sessionManagerRepository.loadPrimaryRoleBITS();
        this.secondaryRoleBITS = sessionManagerRepository.loadSecondaryRoleBITS();

        /* attributes related to OUTCOME entity:Fixme */
        this.operationBITS = sessionManagerRepository.loadOperationBITS(
                cBitwise.EVALUATION, cBitwise.EVALUATION_MODULE);
        this.statusBITS = sessionManagerRepository.loadStatusBITS(
                cBitwise.EVALUATION, cBitwise.EVALUATION_MODULE, cBitwise.READ);
    }

    /* */
    private void notifyError(String msg) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onEvaluationModelsFailed(msg);
            }
        });
    }

    /* */
    private void postMessage(String logFrameName, ArrayList<cTreeModel> evaluationTreeModels) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onEvaluationModelsRetrieved(logFrameName, evaluationTreeModels);
            }
        });
    }

    cDBQuestion convertTDBQuestion(cQuestionModel questionModel){
        cDBQuestion dbQuestion = new cDBQuestion();

        dbQuestion.setQuestionID(questionModel.getQuestionID());
        dbQuestion.setQuestionTypeID(questionModel.getQuestionTypeModel().getQuestionTypeID());
        dbQuestion.setQuestionGroupingID(questionModel.getQuestionGroupingModel().getQuestionGroupingID());
        dbQuestion.setLabel(questionModel.getLabel());
        dbQuestion.setQuestion(questionModel.getQuestion());

        /* array choices
        Set<Pair<Long, String>> arrayChoiceSet = new HashSet<>();
        for (cArrayChoiceModel choice: questionModel.getArraySetModelSet()) {
            arrayChoiceSet.add(new Pair<>(choice.getArrayChoiceID(), choice.getName()));
        }
        dbQuestion.setArrayChoiceSet(arrayChoiceSet);*/

        /* matrix choices
        Set<Pair<Long, String>> rowChoiceSet = new HashSet<>();
        Set<Pair<Long, String>> colChoiceSet = new HashSet<>();
        for (Pair<cRowChoiceModel, cColChoiceModel> matrix: questionModel.getMatrixChoiceModelSet()){
            rowChoiceSet.add(new Pair<>((long)matrix.first.getRowOptionID(),
                    matrix.first.getName()));
            colChoiceSet.add(new Pair<>((long)matrix.second.getColOptionID(),
                    matrix.second.getName()));
        }
        dbQuestion.setRowChoiceSet(rowChoiceSet);
        dbQuestion.setColChoiceSet(colChoiceSet);*/

        return dbQuestion;
    }

    private ArrayList<cTreeModel> getEvaluationTree(Set<cEvaluationModel> evaluationModelSet) {
        ArrayList<cTreeModel> evaluationTreeModels = new ArrayList<>();
        int parentIndex = 0, childIndex = 0;

        ArrayList<cEvaluationModel> evaluationModels = new ArrayList<>(evaluationModelSet);

        if (evaluationModels.size() > 0) {
            logFrameName = evaluationModels.get(0).getLogFrameModel().getName();
        }

        for (int i = 0; i < evaluationModels.size(); i++) {
            /* evaluation */
            cEvaluationModel evaluationModel = evaluationModels.get(i);
            evaluationTreeModels.add(new cTreeModel(parentIndex, -1, 0,
                    evaluationModel));

            /* set of users under the evaluation */
            ArrayList<cUserModel> users = new ArrayList<>(evaluationModel.getUserModelSet());
            if (users.size() > 0) {
                childIndex = parentIndex + 1;
                evaluationTreeModels.add(new cTreeModel(childIndex, parentIndex, 1, users));
            }

            /* set of questions under the evaluation */
            ArrayList<cQuestionModel> questions = new ArrayList<>(
                    evaluationModel.getQuestionModelSet());

            if (questions.size() > 0) {
                childIndex = parentIndex + 2;
                evaluationTreeModels.add(new cTreeModel(childIndex, parentIndex, 2,
                        questions));

                /* FIXME group questions by their sections */
                Map<Long, List<cQuestionModel>> questionModelMap = questions.stream()
                        .collect(Collectors.groupingBy(cQuestionModel::getQuestionID));

                /* create a cDBQuestionnaire object and transform cQuestionModel to it */
                Map<Long, Vector<cDBQuestion>> dbQuestionGroups = new HashMap<>();
                for (Map.Entry<Long, List<cQuestionModel>> entry : questionModelMap.entrySet()) {
                    List<cQuestionModel> questionModels = entry.getValue();
                    Vector<cDBQuestion> dbQuestionVector = new Vector<>();
                    for(int j = 0; j < questionModels.size(); j++){
                        dbQuestionVector.add(convertTDBQuestion(questionModels.get(j)));
                    }
                    dbQuestionGroups.put(entry.getKey(), dbQuestionVector);
                }

                cDBQuestionnaire dbQuestionnaire = new cDBQuestionnaire();
                dbQuestionnaire.setFormName(evaluationModel.getName());
                dbQuestionnaire.setQuestionGroups(dbQuestionGroups);
                evaluationModel.setQuestionnaireObj(dbQuestionnaire);
            }

            /* next parent index */
            parentIndex = childIndex + 1;
        }

        return evaluationTreeModels;
    }

    @Override
    public void run() {
        //fixme
        if ((operationBITS & cBitwise.READ) == 0) {

            /* retrieve a set logFrames from the database */
            Log.d(TAG, "LOGFRAME ID = " + logFrameID + "; USER ID = " + userID +
                    "; PRIMARY = " + primaryRoleBITS + "; SECONDARY = " + secondaryRoleBITS +
                    "; STATUS = " + statusBITS);

            Set<cEvaluationModel> evaluationModelSet = evaluationRepository.getEvaluationModelSet(
                    logFrameID, userID, primaryRoleBITS, secondaryRoleBITS, statusBITS);

            if (evaluationModelSet != null) {
                Gson gson = new Gson();
                Log.d(TAG, "EVALUATION = " + gson.toJson(evaluationModelSet.size()));

                ArrayList<cTreeModel> evaluationTreeModels = getEvaluationTree(evaluationModelSet);

                postMessage(logFrameName, evaluationTreeModels);
            } else {
                notifyError("Failed to read evaluations !!");
            }
        } else {
            notifyError("Failed due to reading access rights !!");
        }
    }
}
