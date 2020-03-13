package com.me.mseotsanyana.mande.BLL.interactors.logframe;

import com.me.mseotsanyana.mande.BLL.domain.logframe.cLogFrameDomain;
import com.me.mseotsanyana.mande.BLL.interactors.base.iInteractor;

import java.util.Set;

/**
 *  This interactor is responsible for retrieving a set LogFrames
 *  from the database.
 */
public interface iGetLogFramesInteractor extends iInteractor {
    interface Callback {
        void onLogFrameRetrieved(Set<cLogFrameDomain> logFrameDomainSet);
    }
}
