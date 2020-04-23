package com.me.mseotsanyana.mande.BLL.interactors.wpb;

import com.me.mseotsanyana.mande.BLL.interactors.base.iInteractor;

public interface iUploadAWPBInteractor extends iInteractor {
    interface Callback{
        void onUploadAWPBCompleted(String msg);
    }
}
