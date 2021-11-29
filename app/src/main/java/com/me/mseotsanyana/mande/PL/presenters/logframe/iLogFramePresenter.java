package com.me.mseotsanyana.mande.PL.presenters.logframe;

import android.content.Context;

import androidx.annotation.NonNull;

import com.me.mseotsanyana.mande.BLL.model.logframe.cLogFrameModel;
import com.me.mseotsanyana.mande.PL.presenters.base.iPresenter;
import com.me.mseotsanyana.mande.PL.ui.iBaseView;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public interface iLogFramePresenter extends iPresenter {
    interface View extends iBaseView {
        /* pass data from presenter to the view */
        void onClickBMBLogFrame(int menuIndex, cLogFrameModel logFrameModel);
        void onClickCreateLogFrame(cLogFrameModel logFrameModel);
        void onClickCreateSubLogFrame(String logFrameID, cLogFrameModel logFrameModel);
        void onClickUpdateLogFrame(int position, cLogFrameModel logFrameModel);
        void onClickDeleteLogFrame(int position, String logframeID);
        void onClickDeleteSubLogFrame(int position, String logframeID);
        void onClickUploadLogFrame(File excelFile);

        /* pass data from interactor to the view */
        void onRetrieveLogFramesCompleted(List<cTreeModel> treeModels);
        void onRetrieveLogFramesFailed(String msg);

        void onLogFrameCreated(cLogFrameModel logFrameModel, String msg);
        void onLogFrameCreateFailed(String msg);
        void onSubLogFrameCreated(cLogFrameModel logFrameModel, String msg);
        void onSubLogFrameCreateFailed(String msg);


        void onLogFrameUpdated(cLogFrameModel logFrameModel, int position, String msg);
        void onLogFrameUpdateFailed(String msg);

        void onLogFrameDeleted(int position, String msg);
        void onSubLogFrameDeleted(int position, String msg);
        void onLogFrameDeleteFailed(String msg);
        void onSubLogFrameDeleteFailed(String msg);
        //void onLogFrameSynced(cLogFrameModel logFrameModel);

        void onUploadLogFrameCompleted(String msg);
        void onUploadLogFrameFailed(String msg);

        //void onRequestPermissionsResult(Context context, int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);

        /* shared preferences */
        //void onRetrieveSharedOrgsCompleted(ArrayList<cOrganizationModel> organizationModels);
    }

    /* pass data from view to the interactor */
    void createLogFrameModel(cLogFrameModel logFrameModel);
    void createSubLogFrameModel(String logFrameID, cLogFrameModel logSubFrameModel);
    void readLogFrames();
    void updateLogFrame(cLogFrameModel logFrameModel, int position);
    void deleteLogFrameModel(String logFrameID, int position);
    void deleteSubLogFrameModel(String logSubFrameID, int position);
    //void syncLogFrameModel(cLogFrameModel logFrameModel);
    //void readSharedOrganizations();

    void uploadLogFrameFromExcel(String filePath);
}