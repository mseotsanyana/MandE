package com.me.mseotsanyana.mande.DAL.model.wpb;

import com.me.mseotsanyana.mande.DAL.model.logframe.cInputModel;
import com.me.mseotsanyana.mande.DAL.model.session.cUserModel;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class cHumanModel extends cInputModel {
    private int quantity;
    private Set<cUserModel> userModelSet;

    public cHumanModel(){
        userModelSet = new HashSet<>();
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Set<cUserModel> getUserModelSet() {
        return userModelSet;
    }

    public void setUserModelSet(Set<cUserModel> userModelSet) {
        this.userModelSet = userModelSet;
    }
}
