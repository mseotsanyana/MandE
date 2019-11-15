package com.me.mseotsanyana.mande.INTERFACE;

import java.io.Serializable;

public interface iMEEntityInterface extends Serializable {
    void onUpdateEntity(int position);
    void onResponseMessage(String title, String message);
}
