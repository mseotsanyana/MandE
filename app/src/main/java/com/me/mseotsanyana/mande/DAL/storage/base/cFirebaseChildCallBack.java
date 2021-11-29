package com.me.mseotsanyana.mande.DAL.storage.base;

public abstract class cFirebaseChildCallBack {

    public abstract void onChildAdded(Object object);

    public abstract void onChildChanged(Object object);

    public abstract void onChildRemoved(Object object);

    public abstract void onCancelled(Object object);
}
