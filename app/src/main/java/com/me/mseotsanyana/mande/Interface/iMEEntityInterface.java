package com.me.mseotsanyana.mande.Interface;

import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.io.Serializable;
import java.util.ArrayList;

public interface iMEEntityInterface extends Serializable {
    void onUpdateEntity(int position);
    void onResponseMessage(String title, String message);
}
