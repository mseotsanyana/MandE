package com.me.mseotsanyana.mande.PPMER.BLL.interactors;

import com.me.mseotsanyana.mande.PPMER.BLL.domain.cLogFrameDomain;
import com.me.mseotsanyana.mande.PPMER.BLL.interactors.base.iInteractor;

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
