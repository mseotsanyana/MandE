package com.me.mseotsanyana.mande.BLL.interactors.wpb.Impl;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.wpb.iUploadAWPBInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.BLL.repository.awpb.iUploadAWPBRepository;

public class cUploadAWPBInteractorImpl extends cAbstractInteractor
        implements iUploadAWPBInteractor {
    private Callback callback;
    private iUploadAWPBRepository uploadAWPBRepository;

    public cUploadAWPBInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                     iUploadAWPBRepository uploadAWPBRepository,
                                     Callback callback) {
        super(threadExecutor, mainThread);



        if (uploadAWPBRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.uploadAWPBRepository = uploadAWPBRepository;
        this.callback = callback;
    }

    /* notify on the main thread */
    private void notifyError(String msg){
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onUploadAWPBCompleted(msg);
            }
        });
    }

    /* notify on the main thread */
    private void postMessage(String msg){
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onUploadAWPBCompleted(msg);
            }
        });
    }


    @Override
    public void run() {

        /* upload all AWPB module records */

        uploadAWPBRepository.deleteTasks();
        uploadAWPBRepository.deleteActivityTasks();
        uploadAWPBRepository.deletePrecedingTasks();
        uploadAWPBRepository.deleteTaskMilestones();
        uploadAWPBRepository.deleteTaskAssignments();
        uploadAWPBRepository.deleteTimesheets();
        uploadAWPBRepository.deleteUserComments();
        uploadAWPBRepository.deleteUserComments();
        uploadAWPBRepository.deleteDocuments();
        uploadAWPBRepository.deleteInvoices();
        uploadAWPBRepository.deleteInvoiceTimesheet();
        uploadAWPBRepository.deleteInternals();
        uploadAWPBRepository.deleteExternals();
        uploadAWPBRepository.deleteTransactions();
        uploadAWPBRepository.deleteJournals();

        /* upload all AWPB module records */

        if(uploadAWPBRepository.addTaskFromExcel()){
            postMessage("Task Entity Added Successfully!");
        }else {
            notifyError("Failed to Add Task Entity");
        }

        if(uploadAWPBRepository.addDocumentFromExcel()){
            postMessage("Document Entity Added Successfully!");
        }else {
            notifyError("Failed to Add Document Entity");
        }

        if(uploadAWPBRepository.addInvoiceFromExcel()){
            postMessage("Invoice Entity Added Successfully!");
        }else {
            notifyError("Failed to Add Invoice Entity");
        }

        if(uploadAWPBRepository.addTransactionFromExcel()){
            postMessage("Transaction Entity Added Successfully!");
        }else {
            notifyError("Failed to Add Transaction Entity");
        }


        if(uploadAWPBRepository.addJournalFromExcel()){
            postMessage("Journal Entity Added Successfully!");
        }else {
            notifyError("Failed to Add Journal Entity");
        }
    }
}
