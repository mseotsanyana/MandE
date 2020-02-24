package com.me.mseotsanyana.mande.PPMER.BLL.interactors;

import com.me.mseotsanyana.mande.PPMER.BLL.interactors.base.iInteractor;

public interface iUploadAWPBInteractor extends iInteractor {
    interface Callback{
        void onUploadAWPBCompleted();
    }
}
