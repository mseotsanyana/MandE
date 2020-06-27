package com.me.mseotsanyana.mande.BLL.interactors.monitor.Impl;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.monitor.iUploadMonitoringInteractor;
import com.me.mseotsanyana.mande.BLL.repository.monitor.iUploadMonitoringRepository;

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

    /* notify on the main thread */
    private void notifyError(String msg){
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onUploadMonitoringCompleted(msg);
            }
        });
    }

    /* notify on the main thread */
    private void postMessage(String msg){
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onUploadMonitoringCompleted(msg);
            }
        });
    }

    @Override
    public void run() {
        /* delete all monitoring module records */

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

        /* upload all monitoring module records */

        if(uploadMonitoringRepository.addMOVFromExcel()){
            postMessage("Frequency Entity Added Successfully!");
        }else {
            notifyError("Failed to Add Frequency Entity");
        }

        if(uploadMonitoringRepository.addMethodFromExcel()){
            postMessage("Method Entity Added Successfully!");
        }else {
            notifyError("Failed to Add Method Entity");
        }

        if(uploadMonitoringRepository.addUnitFromExcel()){
            postMessage("Unit Entity Added Successfully!");
        }else {
            notifyError("Failed to Add Unit Entity");
        }

        if(uploadMonitoringRepository.addIndicatorTypeFromExcel()){
            postMessage("IndicatorType Entity Added Successfully!");
        }else {
            notifyError("Failed to Add IndicatorType Entity");
        }

        if(uploadMonitoringRepository.addQualitativeChoiceFromExcel()){
            postMessage("QualitativeChoice Entity Added Successfully!");
        }else {
            notifyError("Failed to Add QualitativeChoice Entity");
        }

        if(uploadMonitoringRepository.addDataCollectorFromExcel()){
            postMessage("DataCollector Entity Added Successfully!");
        }else {
            notifyError("Failed to Add DataCollector Entity");
        }

        if(uploadMonitoringRepository.addIndicatorFromExcel()){
            postMessage("Indicator Entity Added Successfully!");
        }else {
            notifyError("Failed to Add Indicator Entity");
        }

        if(uploadMonitoringRepository.addMResponseFromExcel()){
            postMessage("MResponse Entity Added Successfully!");
        }else {
            notifyError("Failed to Add MResponse Entity");
        }
    }
}
