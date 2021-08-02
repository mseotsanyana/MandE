package com.me.mseotsanyana.mande.BLL.model.session;


/**
 * Created by mseotsanyana on 2017/08/24.
 */

public class cUnixOperationModel {
    private String operationServerID;

    private String name;
    private String description;
    private boolean checked;

    public cUnixOperationModel(){}

    public cUnixOperationModel(cUnixOperationModel operationModel){
        this.setOperationServerID(operationModel.getOperationServerID());
        this.setName(operationModel.getName());
        this.setDescription(operationModel.getDescription());
        this.setChecked(operationModel.isChecked());
    }

    public String getOperationServerID() {
        return operationServerID;
    }

    public void setOperationServerID(String operationServerID) {
        this.operationServerID = operationServerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
