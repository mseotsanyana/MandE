package com.me.mseotsanyana.mande.PL.presenters.session.Impl;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.session.permission.Impl.
        cReadRolePermissionsInteractorImpl;
import com.me.mseotsanyana.mande.BLL.interactors.session.permission.iReadRolePermissionsInteractor;
import com.me.mseotsanyana.mande.BLL.model.session.cPermissionModel;
import com.me.mseotsanyana.mande.BLL.model.session.cRoleModel;
import com.me.mseotsanyana.mande.BLL.repository.session.iPermissionRepository;
import com.me.mseotsanyana.mande.BLL.repository.session.iSharedPreferenceRepository;
import com.me.mseotsanyana.mande.PL.presenters.base.cAbstractPresenter;
import com.me.mseotsanyana.mande.PL.presenters.session.iPermissionPresenter;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.List;
import java.util.Map;

public class cPermissionPresenterImpl extends cAbstractPresenter implements iPermissionPresenter,
        iReadRolePermissionsInteractor.Callback{
    //private static String TAG = cTeamPresenterImpl.class.getSimpleName();

    private View view;
    private final iSharedPreferenceRepository sharedPreferenceRepository;
    private final iPermissionRepository permissionRepository;

    public cPermissionPresenterImpl(iExecutor executor, iMainThread mainThread,
                                    View view,
                                    iSharedPreferenceRepository sharedPreferenceRepository,
                                    iPermissionRepository permissionRepository) {
        super(executor, mainThread);

        this.view = view;
        this.sharedPreferenceRepository = sharedPreferenceRepository;
        this.permissionRepository = permissionRepository;
    }

    // READ PERMISSIONS

    @Override
    public void readPermissions() {
        iReadRolePermissionsInteractor readRolePermissionsInteractor =
                new cReadRolePermissionsInteractorImpl(
                executor,
                mainThread,
                sharedPreferenceRepository,
                permissionRepository,
                this);

        view.showProgress();
        readRolePermissionsInteractor.execute();
    }

    @Override
    public void onReadRolePermissionsFailed(String msg) {
        if(this.view != null) {
            this.view.onReadPermissionFailed(msg);
            this.view.hideProgress();
        }
    }

    @Override
    public void onReadRolePermissionsRetrieved(List<cTreeModel> treeModels) {
        if(this.view != null) {
            this.view.onReadPermissionSucceeded(treeModels);
            this.view.hideProgress();
        }
    }

    /* ===================================== END PREFERENCE ===================================== */

    /* corresponding view functions */
    @Override
    public void resume() {
        readPermissions();
    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {
        if(this.view != null){
            this.view.hideProgress();
        }
    }

    @Override
    public void destroy() {
        this.view = null;
    }

    @Override
    public void onError(String message) {

    }
}
