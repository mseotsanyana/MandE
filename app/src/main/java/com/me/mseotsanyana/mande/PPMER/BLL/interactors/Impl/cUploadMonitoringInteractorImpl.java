package com.me.mseotsanyana.mande.PPMER.BLL.interactors.Impl;

import com.me.mseotsanyana.mande.PPMER.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.PPMER.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.PPMER.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.PPMER.BLL.interactors.iUploadMonitoringInteractor;
import com.me.mseotsanyana.mande.PPMER.BLL.repository.iUploadMonitoringRepository;

public class cUploadMonitoringInteractorImpl extends cAbstractInteractor
        implements iUploadMonitoringInteractor {
    private Callback callback;
    private iUploadMonitoringRepository uploadMonitoringRepository;

    public cUploadMonitoringInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                           iUploadMonitoringRepository uploadMonitoringRepository,
                                           Callback callback) {
        super(threadExecutor, mainThread);



        if (uploadMonitoringRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.uploadMonitoringRepository = uploadMonitoringRepository;
        this.callback = callback;
    }


    @Override
    public void run() {
        /* create a new repository objects and insert it in the database */

        /* delete functions */
        uploadMonitoringRepository.deleteMOVs();
        uploadMonitoringRepository.deleteMethods();
        uploadMonitoringRepository.deleteUnits();
        uploadMonitoringRepository.deleteIndicatorTypes();
        uploadMonitoringRepository.deleteQualitativeChoices();
        uploadMonitoringRepository.deleteDataCollectors();
        uploadMonitoringRepository.deleteIndicators();
        uploadMonitoringRepository.deleteMilestones();
        uploadMonitoringRepository.deleteIndicatorMilestones();
        uploadMonitoringRepository.deleteQuantitatives();
        uploadMonitoringRepository.deleteQualitatives();
        uploadMonitoringRepository.deleteQualitativeChoiceSets();

        /* add functions */
        uploadMonitoringRepository.addMOVFromExcel();
        uploadMonitoringRepository.addMethodFromExcel();
        uploadMonitoringRepository.addUnitFromExcel();
        uploadMonitoringRepository.addIndicatorTypeFromExcel();
        uploadMonitoringRepository.addQualitativeChoiceFromExcel();
        uploadMonitoringRepository.addDataCollectorFromExcel();
        uploadMonitoringRepository.addIndicatorFromExcel();
        uploadMonitoringRepository.addMResponseFromExcel();

        /* notify on the main thread that we have inserted this item */
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onUploadMonitoringCompleted("Monitoring Modules Added Successfully!");
            }
        });
    }
}
