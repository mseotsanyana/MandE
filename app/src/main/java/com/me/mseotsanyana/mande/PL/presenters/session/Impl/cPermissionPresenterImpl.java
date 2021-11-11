package com.me.mseotsanyana.mande.PL.presenters.session.Impl;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.session.permission.Impl.
        cReadRolePermissionsInteractorImpl;
import com.me.mseotsanyana.mande.BLL.interactors.session.permission.Impl.
        cUpdatePermissionInteractorImpl;
import com.me.mseotsanyana.mande.BLL.interactors.session.permission.iReadRolePermissionsInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.session.permission.iUpdateRolePermissionInteractor;
import com.me.mseotsanyana.mande.BLL.repository.session.iPermissionRepository;
import com.me.mseotsanyana.mande.BLL.repository.session.iSharedPreferenceRepository;
import com.me.mseotsanyana.mande.PL.presenters.base.cAbstractPresenter;
import com.me.mseotsanyana.mande.PL.presenters.session.iPermissionPresenter;
import com.me.mseotsanyana.treeadapterlibrary.cNode;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.List;

public class cPermissionPresenterImpl extends cAbstractPresenter implements iPermissionPresenter,
        iReadRolePermissionsInteractor.Callback, iUpdateRolePermissionInteractor.Callback {
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
    public void readRolePermissions() {
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
        if (this.view != null) {
            this.view.onReadRolePermissionFailed(msg);
            this.view.hideProgress();
        }
    }

    @Override
    public void onReadRolePermissionsSucceeded(List<cTreeModel> treeModels) {
        if (this.view != null) {
            this.view.onReadRolePermissionSucceeded(treeModels);
            this.view.hideProgress();
        }
    }

    @Override
    public void updateRolePermissions(List<cNode> nodes) {
        iUpdateRolePermissionInteractor updateRolePermissionInteractor =
                new cUpdatePermissionInteractorImpl(
                executor,
                mainThread,
                sharedPreferenceRepository,
                permissionRepository,
                this,
                nodes);

        view.showProgress();
        updateRolePermissionInteractor.execute();
    }

    @Override
    public void onUpdateRolePermissionSucceeded(String msg) {
        if (this.view != null) {
            this.view.onUpdateRolePermissionSucceeded(msg);
            this.view.hideProgress();
        }
    }

    @Override
    public void onUpdateRolePermissionFailed(String msg) {
        if (this.view != null) {
            this.view.onUpdateRolePermissionFailed(msg);
            this.view.hideProgress();
        }
    }

    /* ===================================== END PREFERENCE ===================================== */

    /* corresponding view functions */
    @Override
    public void resume() {
        readRolePermissions();
    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {
        if (this.view != null) {
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
