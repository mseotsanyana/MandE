package com.me.mseotsanyana.mande.UTILITY.BLL;

import java.util.Date;

/**
 * Created by mseotsanyana on 2016/12/27.
 */

public class cResourceDomain {
    private int resourceID;
    private String resourceName;
    private String resourceDescription;
    private Date resourceDateCreated;

    public int getResourceID() {
        return resourceID;
    }

    public void setResourceID(int resourceID) {
        this.resourceID = resourceID;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getResourceDescription() {
        return resourceDescription;
    }

    public void setResourceDescription(String resourceDescription) {
        this.resourceDescription = resourceDescription;
    }

    public Date getResourceDateCreated() {
        return resourceDateCreated;
    }

    public void setResourceDateCreated(Date resourceDateCreated) {
        this.resourceDateCreated = resourceDateCreated;
    }
}
