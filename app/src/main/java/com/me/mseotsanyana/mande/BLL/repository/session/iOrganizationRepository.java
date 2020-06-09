package com.me.mseotsanyana.mande.BLL.repository.session;

import com.me.mseotsanyana.mande.DAL.model.session.cOrganizationModel;

import java.util.Set;

public interface iOrganizationRepository {
    Set<cOrganizationModel> getOrganizationSet();
}
