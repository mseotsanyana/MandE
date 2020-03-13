package com.me.mseotsanyana.mande.BLL.interactors.awpb.Impl;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.awpb.iUploadAWPBInteractor;
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

    @Override
    public void run() {
        /* delete AWPB tables */
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

        /* create AWPB tables */
        uploadAWPBRepository.addTaskFromExcel();
        uploadAWPBRepository.addDocumentFromExcel();
        uploadAWPBRepository.addInvoiceFromExcel();
        uploadAWPBRepository.addTransactionFromExcel();
        uploadAWPBRepository.addJournalFromExcel();

        /* notify on the main thread that we have inserted this item */
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onUploadAWPBCompleted("AWPB Modules Added Successfully!");
            }
        });
    }
}
