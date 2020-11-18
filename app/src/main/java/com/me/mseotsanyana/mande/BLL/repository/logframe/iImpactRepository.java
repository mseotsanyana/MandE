package com.me.mseotsanyana.mande.BLL.repository.logframe;

import com.me.mseotsanyana.mande.BLL.model.logframe.cImpactModel;

import java.util.Set;

public interface iImpactRepository {
    /* the create function of the Impact entity */
    boolean addImpact(cImpactModel impactModel);

    /* the read functions of the Impact entity with different sets */
    Set<cImpactModel> getImpactModelSet(long logFrameID, long userID, int primaryRole,
                                        int secondaryRoles, int statusBITS);
}
