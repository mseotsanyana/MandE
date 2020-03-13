package com.me.mseotsanyana.mande.BLL.interactors.evaluator;

import com.me.mseotsanyana.mande.BLL.interactors.base.iInteractor;

public interface iUploadEvaluationInteractor extends iInteractor {
    interface Callback{
        void onUploadEvaluationCompleted(String s);
    }
}
