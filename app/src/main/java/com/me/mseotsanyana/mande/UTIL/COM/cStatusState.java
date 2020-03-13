package com.me.mseotsanyana.mande.UTIL.COM;

public class cStatusState{
    private int id;
    private boolean state;
    private boolean dirty;

    public cStatusState(int id, boolean state, boolean dirty){
        this.id = id;
        this.state = state;
        this.dirty = dirty;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public boolean isDirty() {
        return dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }
}