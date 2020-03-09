package com.me.mseotsanyana.mande.PPMER.BLL.interactors;

import com.me.mseotsanyana.mande.PPMER.BLL.interactors.base.iInteractor;

public interface iUploadRAIDInteractor extends iInteractor {
    interface Callback{
        void onUploadRAIDCompleted(String s);
    }
}
