package com.me.mseotsanyana.mande.BRBAC.BLL;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class cPermissionTreeDomain extends ArrayList<Parcelable> implements Parcelable {
    private int organizationID;
    private int privilegeID;
    private cEntityDomain entityDomain;

    private HashMap<cOperationDomain,
            HashMap<cStatusDomain, cPermissionDomain>> permDomainDetails;

    public cPermissionTreeDomain(){}


    protected cPermissionTreeDomain(Parcel in) {
        organizationID = in.readInt();
        privilegeID = in.readInt();
        entityDomain = in.readParcelable(cEntityDomain.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(organizationID);
        dest.writeInt(privilegeID);
        dest.writeParcelable(entityDomain, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<cPermissionTreeDomain> CREATOR =
            new Creator<cPermissionTreeDomain>() {
        @Override
        public cPermissionTreeDomain createFromParcel(Parcel in) {
            return new cPermissionTreeDomain(in);
        }

        @Override
        public cPermissionTreeDomain[] newArray(int size) {
            return new cPermissionTreeDomain[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        cPermissionTreeDomain that = (cPermissionTreeDomain) o;
        return organizationID == that.organizationID && privilegeID == that.privilegeID &&
                Objects.equals(entityDomain, that.entityDomain) &&
                Objects.equals(permDomainDetails, that.permDomainDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(organizationID, privilegeID, entityDomain, permDomainDetails);
    }

    public int getOrganizationID() {
        return organizationID;
    }

    public void setOrganizationID(int organizationID) {
        this.organizationID = organizationID;
    }

    public int getPrivilegeID() {
        return privilegeID;
    }

    public void setPrivilegeID(int privilegeID) {
        this.privilegeID = privilegeID;
    }

    public cEntityDomain getEntityDomain() {
        return entityDomain;
    }

    public void setEntityDomain(cEntityDomain entityDomain) {
        this.entityDomain = entityDomain;
    }

    public HashMap<cOperationDomain,
            HashMap<cStatusDomain, cPermissionDomain>> getPermDomainDetails() {
        return permDomainDetails;
    }

    public void setPermDomainDetails(HashMap<cOperationDomain,
            HashMap<cStatusDomain, cPermissionDomain>> permDomainDetails) {
        this.permDomainDetails = permDomainDetails;
    }
}
