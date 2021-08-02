package com.me.mseotsanyana.mande.BLL.interactors;

import java.util.List;

public final class cInteractorUtils {

    public static boolean isSettingsNonNull(String organizationServerID, String userServerID,
                                         int entityBITS, int entitypermBITS, int primaryTeamBIT,
                                         List<Integer> secondaryTeamBITS, List<Integer> statusBITS) {

        return (organizationServerID != null && userServerID != null && entityBITS != -1 &&
                entitypermBITS != -1 && primaryTeamBIT != -1 && secondaryTeamBITS != null &&
                statusBITS != null);
    }
}
