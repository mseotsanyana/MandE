package com.me.mseotsanyana.mande.DAL.model.evaluator;

import java.util.Date;

public class cTextResponseModel extends cEvaluationResponseModel{
    private String textResponse;

    public cTextResponseModel(){
        super();
    }

    public String getTextResponse() {
        return textResponse;
    }

    public void setTextResponse(String textResponse) {
        this.textResponse = textResponse;
    }
}
