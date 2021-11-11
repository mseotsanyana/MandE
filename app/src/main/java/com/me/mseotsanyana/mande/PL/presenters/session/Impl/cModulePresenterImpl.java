package com.me.mseotsanyana.mande.PL.presenters.session.Impl;

//public class cModulePresenterImpl extends cAbstractPresenter implements iModulePresenter,
//        iReadModuleInteractor.Callback, iUpdateRolePermissionInteractor.Callback{
//    //private static final String TAG = cModulePresenterImpl.class.getSimpleName();
//
//    private View view;
//    private final iSharedPreferenceRepository sessionManagerRepository;
//    private final iModuleRepository moduleRepository;
//
//    public cModulePresenterImpl(iExecutor executor, iMainThread mainThread,
//                                View view,
//                                iSharedPreferenceRepository sessionManagerRepository,
//                                iModuleRepository moduleRepository) {
//        super(executor, mainThread);
//
//        this.view = view;
//        this.sessionManagerRepository = sessionManagerRepository;
//        this.moduleRepository = moduleRepository;
//    }
//
//    @Override
//    public void onReadModuleFailed(String msg) {
//        if(this.view != null) {
//            this.view.onReadModuleFailed(msg);
//            this.view.hideProgress();
//        }
//    }
//
//    @Override
//    public void onReadModuleSucceeded(List<cTreeModel> treeModels) {
//        if(this.view != null) {
//            this.view.onReadModuleSucceeded(treeModels);
//            this.view.hideProgress();
//        }
//    }
//
//    @Override
//    public void readModules() {
//        iReadModuleInteractor readModuleInteractor = new cReadModuleInteractorImpl(
//                executor,
//                mainThread,
//                sessionManagerRepository,
//                moduleRepository,
//                this);
//
//        view.showProgress();
//        readModuleInteractor.execute();
//    }
//
//    @Override
//    public void onUpdatePermissionFailed(String msg) {
//        if(this.view != null) {
//            this.view.onUpdatePermissionFailed(msg);
//            this.view.hideProgress();
//        }
//    }
//
//    @Override
//    public void onUpdatePermissionSucceeded(String msg) {
//        if(this.view != null) {
//            this.view.onUpdatePermissionSucceeded(msg);
//            this.view.hideProgress();
//        }
//    }
//
//    @Override
//    public void updatePermissions(List<cNode> nodes) {
//        iUpdateRolePermissionInteractor updatePermissionInteractor = new cUpdatePermissionInteractorImpl(
//                executor,
//                mainThread,
//                sessionManagerRepository,
//                moduleRepository,
//                this,
//                nodes);
//
//        view.showProgress();
//        updatePermissionInteractor.execute();
//    }
//
//    /* ===================================== END PREFERENCE ===================================== */
//
//    /* corresponding view functions */
//    @Override
//    public void resume() {
//        readModules();
//    }
//
//    @Override
//    public void pause() {
//
//    }
//
//    @Override
//    public void stop() {
//        if(this.view != null){
//            this.view.hideProgress();
//        }
//    }
//
//    @Override
//    public void destroy() {
//        this.view = null;
//    }
//
//    @Override
//    public void onError(String message) {
//
//    }
//}
