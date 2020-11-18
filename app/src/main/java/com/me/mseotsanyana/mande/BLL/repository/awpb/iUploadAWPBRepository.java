package com.me.mseotsanyana.mande.BLL.repository.awpb;

public interface iUploadAWPBRepository {
    boolean deleteTasks();
    boolean deleteActivityTasks();
    boolean deletePrecedingTasks();
    boolean deleteTaskMilestones();
    boolean deleteTaskAssignments();
    boolean deleteTimesheets();
    boolean deleteUserComments();
    boolean deleteDocuments();
    boolean deleteInvoices();
    boolean deleteInvoiceTimesheet();
    boolean deleteInternals();
    boolean deleteExternals();
    boolean deleteTransactions();
    boolean deleteJournals();

    boolean addTaskFromExcel();
    boolean addDocumentFromExcel();
    boolean addInvoiceFromExcel();
    boolean addTransactionFromExcel();
    boolean addJournalFromExcel();
}
