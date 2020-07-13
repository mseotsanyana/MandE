package com.me.mseotsanyana.mande.DAL.model.wpb;

import com.me.mseotsanyana.mande.DAL.model.logframe.cInputModel;
import com.me.mseotsanyana.mande.DAL.model.session.cFunderModel;

import java.util.HashSet;
import java.util.Set;

public class cIncomeModel extends cInputModel {
    /* information from table fund */
    private Set<cFunderModel> funderModelSet;

    public cIncomeModel(){
        funderModelSet = new HashSet<>();
    }

    public Set<cFunderModel> getFunderModelSet() {
        return funderModelSet;
    }

    public void setFunderModelSet(Set<cFunderModel> funderModelSet) {
        this.funderModelSet = funderModelSet;
    }
}
