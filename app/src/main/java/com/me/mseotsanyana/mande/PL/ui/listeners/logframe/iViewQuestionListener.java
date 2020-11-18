package com.me.mseotsanyana.mande.PL.ui.listeners.logframe;

import com.me.mseotsanyana.mande.BLL.model.logframe.cQuestionModel;

public interface iViewQuestionListener {
    void onClickUpdateQuestion(int position, cQuestionModel questionModel);
    void onClickDeleteQuestion(int position, int questionID);
    void onClickSyncQuestion(int position, cQuestionModel questionModel);
    void onClickBMBQuestion(int index);
}
