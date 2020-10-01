package com.me.mseotsanyana.mande.BLL.interactors.common.Impl;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.common.iUploadGlobalInteractor;
import com.me.mseotsanyana.mande.BLL.repository.common.iUploadGlobalRepository;

public class cUploadGlobalInteractorImpl extends cAbstractInteractor
        implements iUploadGlobalInteractor {
    private Callback callback;
    private iUploadGlobalRepository uploadGlobalRepository;

    public cUploadGlobalInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                       iUploadGlobalRepository uploadGlobalRepository,
                                       Callback callback) {
        super(threadExecutor, mainThread);



        if (uploadGlobalRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.uploadGlobalRepository = uploadGlobalRepository;
        this.callback = callback;
    }

    /* notify on the main thread */
    private void notifyError(String msg){
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onUploadGlobalCompleted(msg);
            }
        });
    }

    /* notify on the main thread */
    private void postMessage(String msg){
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onUploadGlobalCompleted(msg);
            }
        });
    }

    @Override
    public void run() {
        /* delete all global module records */

        uploadGlobalRepository.deleteFrequencies();
        uploadGlobalRepository.deletePeriods();
        uploadGlobalRepository.deleteFiscalYears();
        uploadGlobalRepository.deleteCharts();

        /* upload all global module records */

        if(uploadGlobalRepository.addFrequencyFromExcel()){
            postMessage("Frequency Entity Added Successfully!");
        }else {
            notifyError("Failed to Add Frequency Entity");
        }

        if(uploadGlobalRepository.addFiscalYearFromExcel()){
            postMessage("FiscalYear Entity Added Successfully!");
        }else {
            notifyError("Failed to Add FiscalYear Entity");
        }

        if(uploadGlobalRepository.addChartFromExcel()){
            postMessage("Chart Entity Added Successfully!");
        }else {
            notifyError("Failed to Add Chart Entity");
        }
    }
}
