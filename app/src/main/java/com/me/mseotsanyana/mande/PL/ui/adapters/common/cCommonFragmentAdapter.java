package com.me.mseotsanyana.mande.PL.ui.adapters.common;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.me.mseotsanyana.mande.BLL.model.session.cOrganizationModel;
import com.me.mseotsanyana.mande.BLL.model.session.cRoleModel;
import com.me.mseotsanyana.mande.BLL.model.session.cStatusModel;
import com.me.mseotsanyana.mande.BLL.model.session.cUserModel;
import com.me.mseotsanyana.mande.DAL.ìmpl.session.cSessionManagerImpl;
import com.me.mseotsanyana.mande.PL.ui.fragments.common.cCommonDatesFragment;
import com.me.mseotsanyana.mande.PL.ui.fragments.common.cCustomViewPager;
import com.me.mseotsanyana.mande.PL.ui.fragments.common.cOwnershipFragment;
import com.me.mseotsanyana.mande.PL.ui.fragments.common.cPermissionFragment;
import com.me.mseotsanyana.mande.PL.ui.fragments.common.cStatusFragment;

import java.util.ArrayList;

public class cCommonFragmentAdapter extends FragmentStatePagerAdapter {

    private static int TAB_COUNT = 4;
    private int currentPosition = -1;

    private long ownerID    = -1;
    private long ownOrgID   = -1;
    private int groupBITS  = -1;
    private int permBITS   = -1;
    private int statusBITS = -1;
    private String createdDate;
    private String modifiedDate;
    private String syncedDate;

    private cSessionManagerImpl sessionManager;

    public cCommonFragmentAdapter(@NonNull FragmentManager fragmentManager,
                                  cSessionManagerImpl sessionManager) {
        super(fragmentManager);

        this.sessionManager = sessionManager;
    }

    public void readCommonAttributes(long ownerID, long ownOrgID, int groupBITS, int permBITS,
                                     int statusBITS, String createdDate, String modifiedDate,
                                     String syncedDate){
        this.ownerID      = ownerID;
        this.ownOrgID     = ownOrgID;
        this.groupBITS    = groupBITS;
        this.permBITS     = permBITS;
        this.statusBITS   = statusBITS;
        this.createdDate  = createdDate;
        this.modifiedDate = modifiedDate;
        this.syncedDate   = syncedDate;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return cPermissionFragment.newInstance(this.permBITS);

            case 1:
                return cStatusFragment.newInstance(this.statusBITS,
                        new ArrayList<cStatusModel>(sessionManager.loadStatusSet()));

            case 2:
                return cOwnershipFragment.newInstance(this.ownerID, this.ownOrgID, this.groupBITS,
                        new ArrayList<cUserModel>(sessionManager.loadIndividualOwners()),
                        new ArrayList<cOrganizationModel>(sessionManager.loadOrganizationOwners()),
                        sessionManager.loadPrimaryRoleBITS(),
                        sessionManager.loadSecondaryRoleBITS(),
                        new ArrayList<cRoleModel>(sessionManager.loadRoleSet()));

            case 3:
                return cCommonDatesFragment.newInstance(this.createdDate, this.modifiedDate,
                        this.syncedDate);

        }
        return null;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return cPermissionFragment.TITLE;

            case 1:
                return cStatusFragment.TITLE;

            case 2:
                return cOwnershipFragment.TITLE;

            case 3:
                return cCommonDatesFragment.TITLE;

        }
        return super.getPageTitle(position);
    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        if (position != currentPosition) {
            Fragment fragment = (Fragment) object;
            cCustomViewPager pager = (cCustomViewPager) container;
            if (fragment != null && fragment.getView() != null) {
                currentPosition = position;
                pager.measureCurrentView(fragment.getView());
            }
        }
    }
}
