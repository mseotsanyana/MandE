package com.me.mseotsanyana.mande.BLL.interactors.base;

public interface iInteractor {
    /**
     * This is the main method that starts an interactor. It will make sure that the
     * interactor operation is done on a background thread.
     */
    void execute();
}
