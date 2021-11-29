package com.me.mseotsanyana.mande.PL.ui.fragments.common;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.me.mseotsanyana.mande.BLL.model.logframe.cImpactModel;

import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.UTIL.cConstant;

import java.text.SimpleDateFormat;
import java.util.List;

public class cCommonAttributeFragment extends Fragment {
    //private static final String TAG = cCommonAttributeFragment.class.getSimpleName();
    private static final SimpleDateFormat sdf = cConstant.SHORT_FORMAT_DATE;

    private cImpactModel impactModel;

    private SwitchMaterial switchDeleted;
    private SwitchMaterial switchBlocked;
    private SwitchMaterial switchActivated;
    private SwitchMaterial switchCancelled;
    private SwitchMaterial switchPending;

    private AppCompatCheckBox checkBoxOwnerRead;
    private AppCompatCheckBox checkBoxOwnerUpdate;
    private AppCompatCheckBox checkBoxOwnerDelete;

    private AppCompatCheckBox checkBoxPrimaryRead;
    private AppCompatCheckBox checkBoxPrimaryUpdate;
    private AppCompatCheckBox checkBoxPrimaryDelete;

    private AppCompatCheckBox checkBoxSecondaryRead;
    private AppCompatCheckBox checkBoxSecondaryUpdate;
    private AppCompatCheckBox checkBoxSecondaryDelete;

    private AppCompatCheckBox checkBoxOrganizationRead;
    private AppCompatCheckBox checkBoxOrganizationUpdate;
    private AppCompatCheckBox checkBoxOrganizationDelete;


    private static final String DELETED = "1";
    private static final String BLOCKED = "2";
    private static final String ACTIVATED = "4";
    private static final String CANCELLED = "8";
    private static final String PENDING = "16";

    private static final String OWNER_READ = "2048";
    private static final String OWNER_UPDATE = "1024";
    private static final String OWNER_DELETE = "512";
    private static final String PRIMARY_READ = "256";
    private static final String PRIMARY_UPDATE = "128";
    private static final String PRIMARY_DELETE = "64";
    private static final String SECONDARY_READ = "32";
    private static final String SECONDARY_UPDATE = "16";
    private static final String SECONDARY_DELETE = "8";
    private static final String ORGANIZATION_READ = "4";
    private static final String ORGANIZATION_UPDATE = "2";
    private static final String ORGANIZATION_DELETE = "1";

    public cCommonAttributeFragment() {
    }

    public static cCommonAttributeFragment newInstance(cImpactModel impactModel) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("IMPACT_MODEL", impactModel);
        cCommonAttributeFragment fragment = new cCommonAttributeFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.me_common_attributes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        /* data sets */
        initDataStructures();

        /* common attributes views */
        initCommonAttributeViews(view);
    }

    private void initDataStructures() {
        assert getArguments() != null;
        impactModel = getArguments().getParcelable("IMPACT_MODEL");
    }

    private void initCommonAttributeViews(View view) {

        AppCompatTextView textViewCreatedDate = view.findViewById(R.id.textViewCreatedDate);
        AppCompatTextView textViewModifiedDate = view.findViewById(R.id.textViewModifiedDate);

        switchDeleted = view.findViewById(R.id.switchDeleted);
        switchBlocked = view.findViewById(R.id.switchBlocked);
        switchActivated = view.findViewById(R.id.switchActivated);
        switchCancelled = view.findViewById(R.id.switchCancelled);
        switchPending = view.findViewById(R.id.switchPending);

        checkBoxOwnerRead = view.findViewById(R.id.checkBoxOwnerRead);
        checkBoxOwnerUpdate = view.findViewById(R.id.checkBoxOwnerUpdate);
        checkBoxOwnerDelete = view.findViewById(R.id.checkBoxOwnerDelete);

        checkBoxPrimaryRead = view.findViewById(R.id.checkBoxPrimaryRead);
        checkBoxPrimaryUpdate = view.findViewById(R.id.checkBoxPrimaryUpdate);
        checkBoxPrimaryDelete = view.findViewById(R.id.checkBoxPrimaryDelete);

        checkBoxSecondaryRead = view.findViewById(R.id.checkBoxSecondaryRead);
        checkBoxSecondaryUpdate = view.findViewById(R.id.checkBoxSecondaryUpdate);
        checkBoxSecondaryDelete = view.findViewById(R.id.checkBoxSecondaryDelete);

        checkBoxOrganizationRead = view.findViewById(R.id.checkBoxOrganizationRead);
        checkBoxOrganizationUpdate = view.findViewById(R.id.checkBoxOrganizationUpdate);
        checkBoxOrganizationDelete = view.findViewById(R.id.checkBoxOrganizationDelete);

        Button buttonReset = view.findViewById(R.id.buttonReset);

        textViewCreatedDate.setText(sdf.format(impactModel.getCreatedDate()));
        textViewModifiedDate.setText(sdf.format(impactModel.getModifiedDate()));

        //int status_bit = impactModel.getStatusBIT();

        setupStatusBITS(impactModel.getStatusBIT());

        switchDeleted.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            SwitchMaterial sm = (SwitchMaterial) compoundButton;
            if (isChecked) {
                sm.setChecked(true);
                switchBlocked.setChecked(false);
                switchActivated.setChecked(false);
                switchCancelled.setChecked(false);
                switchPending.setChecked(false);
            } else if (!switchBlocked.isChecked() && !switchActivated.isChecked() &&
                    !switchCancelled.isChecked() && !switchPending.isChecked()) {
                sm.setChecked(true);
            }
        });
        switchBlocked.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            SwitchMaterial sm = (SwitchMaterial) compoundButton;
            if (isChecked) {
                sm.setChecked(true);
                switchDeleted.setChecked(false);
                switchActivated.setChecked(false);
                switchCancelled.setChecked(false);
                switchPending.setChecked(false);
            } else if (!switchDeleted.isChecked() && !switchActivated.isChecked() &&
                    !switchCancelled.isChecked() && !switchPending.isChecked()) {
                sm.setChecked(true);
            }
        });
        switchActivated.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            SwitchMaterial sm = (SwitchMaterial) compoundButton;
            if (isChecked) {
                sm.setChecked(true);
                switchDeleted.setChecked(false);
                switchBlocked.setChecked(false);
                switchCancelled.setChecked(false);
                switchPending.setChecked(false);
            } else if (!switchDeleted.isChecked() && !switchBlocked.isChecked() &&
                    !switchCancelled.isChecked() && !switchPending.isChecked()) {
                sm.setChecked(true);
            }
        });
        switchCancelled.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            SwitchMaterial sm = (SwitchMaterial) compoundButton;
            if (isChecked) {
                sm.setChecked(true);
                switchDeleted.setChecked(false);
                switchBlocked.setChecked(false);
                switchActivated.setChecked(false);
                switchPending.setChecked(false);
            } else if (!switchDeleted.isChecked() && !switchBlocked.isChecked() &&
                    !switchActivated.isChecked() && !switchPending.isChecked()) {
                sm.setChecked(true);
            }
        });
        switchPending.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            SwitchMaterial sm = (SwitchMaterial) compoundButton;
            if (isChecked) {
                sm.setChecked(true);
                switchDeleted.setChecked(false);
                switchBlocked.setChecked(false);
                switchActivated.setChecked(false);
                switchCancelled.setChecked(false);
            } else if (!switchDeleted.isChecked() && !switchBlocked.isChecked() &&
                    !switchActivated.isChecked() && !switchCancelled.isChecked()) {
                sm.setChecked(true);
            }
        });

        List<Integer> unixpermBITS = impactModel.getUnixpermBITS();
        setupUnixPerms(unixpermBITS);

        /* owner permissions */
        checkBoxOwnerRead.setOnClickListener(ownerReadView -> {
            boolean checked = ((CheckBox) ownerReadView).isChecked();
            checkBoxOwnerRead.setChecked(checked);
        });
        checkBoxOwnerUpdate.setOnClickListener(ownerUpdateView -> {
            boolean checked = ((CheckBox) ownerUpdateView).isChecked();
            checkBoxOwnerUpdate.setChecked(checked);
        });
        checkBoxOwnerDelete.setOnClickListener(ownerDeleteView -> {
            boolean checked = ((CheckBox) ownerDeleteView).isChecked();
            checkBoxOwnerDelete.setChecked(checked);
        });

        /* primary permissions */
        checkBoxPrimaryRead.setOnClickListener(primaryReadView -> {
            boolean checked = ((CheckBox) primaryReadView).isChecked();
            checkBoxPrimaryRead.setChecked(checked);
        });
        checkBoxPrimaryUpdate.setOnClickListener(primaryUpdateView -> {
            boolean checked = ((CheckBox) primaryUpdateView).isChecked();
            checkBoxPrimaryUpdate.setChecked(checked);
        });
        checkBoxPrimaryDelete.setOnClickListener(primaryDeleteView -> {
            boolean checked = ((CheckBox) primaryDeleteView).isChecked();
            checkBoxPrimaryDelete.setChecked(checked);
        });

        /* secondary permissions */
        checkBoxSecondaryRead.setOnClickListener(secondaryReadView -> {
            boolean checked = ((CheckBox) secondaryReadView).isChecked();
            checkBoxSecondaryRead.setChecked(checked);
        });
        checkBoxSecondaryUpdate.setOnClickListener(secondaryUpdateView -> {
            boolean checked = ((CheckBox) secondaryUpdateView).isChecked();
            checkBoxSecondaryUpdate.setChecked(checked);
        });
        checkBoxSecondaryDelete.setOnClickListener(secondaryDeleteView -> {
            boolean checked = ((CheckBox) secondaryDeleteView).isChecked();
            checkBoxSecondaryDelete.setChecked(checked);
        });

        /* organization permissions */
        checkBoxOrganizationRead.setOnClickListener(organizationReadView -> {
            boolean checked = ((CheckBox) organizationReadView).isChecked();
            checkBoxOrganizationRead.setChecked(checked);
        });
        checkBoxOrganizationUpdate.setOnClickListener(organizationUpdateView -> {
            boolean checked = ((CheckBox) organizationUpdateView).isChecked();
            checkBoxOrganizationUpdate.setChecked(checked);
        });
        checkBoxOrganizationDelete.setOnClickListener(organizationDeleteView -> {
            boolean checked = ((CheckBox) organizationDeleteView).isChecked();
            checkBoxOrganizationDelete.setChecked(checked);
        });

        buttonReset.setOnClickListener(ownerReadView -> {

            setupStatusBITS(impactModel.getStatusBIT());

            //resetUnixPerms();

            checkBoxOwnerRead.setChecked(OWNER_READ.equals(checkBoxOwnerRead.getTag()));
            checkBoxOwnerUpdate.setChecked(OWNER_UPDATE.equals(checkBoxOwnerUpdate.getTag()));
            checkBoxOwnerDelete.setChecked(OWNER_DELETE.equals(checkBoxOwnerDelete.getTag()));

            checkBoxPrimaryRead.setChecked(
                    PRIMARY_READ.equals(checkBoxPrimaryRead.getTag()));
            checkBoxPrimaryUpdate.setChecked(
                    PRIMARY_UPDATE.equals(checkBoxPrimaryUpdate.getTag()));
            checkBoxPrimaryDelete.setChecked(
                    PRIMARY_DELETE.equals(checkBoxPrimaryDelete.getTag()));

            checkBoxSecondaryRead.setChecked(
                    SECONDARY_READ.equals(checkBoxSecondaryRead.getTag()));
            checkBoxSecondaryUpdate.setChecked(
                    SECONDARY_UPDATE.equals(checkBoxSecondaryUpdate.getTag()));
            checkBoxSecondaryDelete.setChecked(
                    SECONDARY_DELETE.equals(checkBoxSecondaryDelete.getTag()));

            checkBoxOrganizationRead.setChecked(
                    ORGANIZATION_READ.equals(checkBoxOrganizationRead.getTag()));
            checkBoxOrganizationUpdate.setChecked(
                    ORGANIZATION_UPDATE.equals(checkBoxOrganizationUpdate.getTag()));
            checkBoxOrganizationDelete.setChecked(
                    ORGANIZATION_DELETE.equals(checkBoxOrganizationDelete.getTag()));
        });
    }

    private void setupStatusBITS(int status_bit) {
        boolean isStatusChecked;
        switch (Integer.toString(status_bit)) {
            case DELETED:
                isStatusChecked = (status_bit & Integer.parseInt(DELETED)) != 0;
                switchDeleted.setChecked(isStatusChecked);
                switchDeleted.setTag(isStatusChecked);
                break;
            case BLOCKED:
                isStatusChecked = (status_bit & Integer.parseInt(BLOCKED)) != 0;
                switchBlocked.setChecked(isStatusChecked);
                switchDeleted.setTag(isStatusChecked);
                break;
            case ACTIVATED:
                isStatusChecked = (status_bit & Integer.parseInt(ACTIVATED)) != 0;
                switchActivated.setChecked(isStatusChecked);
                switchDeleted.setTag(isStatusChecked);
                break;
            case CANCELLED:
                isStatusChecked = (status_bit & Integer.parseInt(CANCELLED)) != 0;
                switchCancelled.setChecked(isStatusChecked);
                switchDeleted.setTag(isStatusChecked);
                break;
            case PENDING:
                isStatusChecked = (status_bit & Integer.parseInt(PENDING)) != 0;
                switchPending.setChecked(isStatusChecked);
                switchDeleted.setTag(isStatusChecked);
                break;
        }
    }

    private void setupUnixPerms(List<Integer> unixpermBITS) {
        for (Integer perm_bit : unixpermBITS) {
            switch (perm_bit.toString()) {
                case OWNER_READ:
                    checkBoxOwnerRead.setChecked(true);
                    checkBoxOwnerRead.setTag(perm_bit.toString());
                    break;
                case OWNER_UPDATE:
                    checkBoxOwnerUpdate.setChecked(true);
                    checkBoxOwnerUpdate.setTag(perm_bit.toString());
                    break;
                case OWNER_DELETE:
                    checkBoxOwnerDelete.setChecked(true);
                    checkBoxOwnerDelete.setTag(perm_bit.toString());
                    break;
                case PRIMARY_READ:
                    checkBoxPrimaryRead.setChecked(true);
                    checkBoxPrimaryRead.setTag(perm_bit.toString());
                    break;
                case PRIMARY_UPDATE:
                    checkBoxPrimaryUpdate.setChecked(true);
                    checkBoxPrimaryUpdate.setTag(perm_bit.toString());
                    break;
                case PRIMARY_DELETE:
                    checkBoxPrimaryDelete.setChecked(true);
                    checkBoxPrimaryDelete.setTag(perm_bit.toString());
                    break;
                case SECONDARY_READ:
                    checkBoxSecondaryRead.setChecked(true);
                    checkBoxSecondaryRead.setTag(perm_bit.toString());
                    break;
                case SECONDARY_UPDATE:
                    checkBoxSecondaryUpdate.setChecked(true);
                    checkBoxSecondaryUpdate.setTag(perm_bit.toString());
                    break;
                case SECONDARY_DELETE:
                    checkBoxSecondaryDelete.setChecked(true);
                    checkBoxSecondaryDelete.setTag(perm_bit.toString());
                    break;
                case ORGANIZATION_READ:
                    checkBoxOrganizationRead.setChecked(true);
                    checkBoxOrganizationRead.setTag(perm_bit.toString());
                    break;
                case ORGANIZATION_UPDATE:
                    checkBoxOrganizationUpdate.setChecked(true);
                    checkBoxOrganizationUpdate.setTag(perm_bit.toString());
                    break;
                case ORGANIZATION_DELETE:
                    checkBoxOrganizationDelete.setChecked(true);
                    checkBoxOrganizationDelete.setTag(perm_bit.toString());
                    break;
            }
        }
    }
}