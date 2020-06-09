package com.me.mseotsanyana.mande.PL.ui.fragments.common;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.fragment.app.Fragment;

import com.me.mseotsanyana.mande.DAL.storage.preference.cBitwise;
import com.me.mseotsanyana.mande.R;

public class cPermissionFragment extends Fragment {

    public static final String TITLE = "Permission";

    private AppCompatCheckBox checkBoxOwnerCreate;
    private AppCompatCheckBox checkBoxOwnerRead;
    private AppCompatCheckBox checkBoxOwnerUpdate;
    private AppCompatCheckBox checkBoxOwnerDelete;
    private AppCompatCheckBox checkBoxOwnerSync;

    private AppCompatCheckBox checkBoxGroupCreate;
    private AppCompatCheckBox checkBoxGroupRead;
    private AppCompatCheckBox checkBoxGroupUpdate;
    private AppCompatCheckBox checkBoxGroupDelete;
    private AppCompatCheckBox checkBoxGroupSync;

    private AppCompatCheckBox checkBoxOtherCreate;
    private AppCompatCheckBox checkBoxOtherRead;
    private AppCompatCheckBox checkBoxOtherUpdate;
    private AppCompatCheckBox checkBoxOtherDelete;
    private AppCompatCheckBox checkBoxOtherSync;

    private int permBITS;

    public static cPermissionFragment newInstance(int permBITS) {

        Bundle bundle = new Bundle();

        bundle.putInt("PERM_BITS", permBITS);

        cPermissionFragment fragment = new cPermissionFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.permBITS = getArguments().getInt("PERM_BITS", 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.me_unix_permission, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /* initialize and set listeners on owner permissions */
        checkBoxOwnerCreate = (AppCompatCheckBox) view.findViewById(R.id.checkBoxOwnerCreate);
        checkBoxOwnerCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((CheckBox) view).isChecked();
                // Check which checkbox was clicked
                if (checked){
                    permBITS |= cBitwise.OWNER_CREATE;
                }
                else{
                    permBITS &= ~cBitwise.OWNER_CREATE;
                }
            }
        });

        checkBoxOwnerRead = (AppCompatCheckBox) view.findViewById(R.id.checkBoxOwnerRead);
        checkBoxOwnerRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((CheckBox) view).isChecked();
                if (checked){
                    permBITS |= cBitwise.OWNER_READ;
                }
                else{
                    permBITS &= ~cBitwise.OWNER_READ;
                }
            }
        });

        checkBoxOwnerUpdate = (AppCompatCheckBox) view.findViewById(R.id.checkBoxOwnerUpdate);
        checkBoxOwnerUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((CheckBox) view).isChecked();
                if (checked){
                    permBITS |= cBitwise.OWNER_UPDATE;
                }
                else{
                    permBITS &= ~cBitwise.OWNER_UPDATE;
                }
            }
        });

        checkBoxOwnerDelete = (AppCompatCheckBox) view.findViewById(R.id.checkBoxOwnerDelete);
        checkBoxOwnerDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((CheckBox) view).isChecked();
                if (checked){
                    permBITS |= cBitwise.OWNER_DELETE;
                }
                else{
                    permBITS &= ~cBitwise.OWNER_DELETE;
                }
            }
        });
        checkBoxOwnerSync = (AppCompatCheckBox) view.findViewById(R.id.checkBoxOwnerSync);
        checkBoxOwnerSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((CheckBox) view).isChecked();
                if (checked){
                    permBITS |= cBitwise.OWNER_SYNC;
                }
                else{
                    permBITS &= ~cBitwise.OWNER_SYNC;
                }
            }
        });

        /* initialize and set listeners on group permissions */
        checkBoxGroupCreate = (AppCompatCheckBox) view.findViewById(R.id.checkBoxGroupCreate);
        checkBoxGroupCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((CheckBox) view).isChecked();
                if (checked){
                    permBITS |= cBitwise.GROUP_CREATE;
                }
                else{
                    permBITS &= ~cBitwise.GROUP_CREATE;
                }
            }
        });

        checkBoxGroupRead = (AppCompatCheckBox) view.findViewById(R.id.checkBoxGroupRead);
        checkBoxGroupRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((CheckBox) view).isChecked();
                if (checked){
                    permBITS |= cBitwise.GROUP_READ;
                }
                else{
                    permBITS &= ~cBitwise.GROUP_READ;
                }
            }
        });

        checkBoxGroupUpdate = (AppCompatCheckBox) view.findViewById(R.id.checkBoxGroupUpdate);
        checkBoxGroupUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((CheckBox) view).isChecked();
                if (checked){
                    permBITS |= cBitwise.GROUP_UPDATE;
                }
                else{
                    permBITS &= ~cBitwise.GROUP_UPDATE;
                }
            }
        });

        checkBoxGroupDelete = (AppCompatCheckBox) view.findViewById(R.id.checkBoxGroupDelete);
        checkBoxGroupDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((CheckBox) view).isChecked();
                if (checked){
                    permBITS |= cBitwise.GROUP_DELETE;
                }
                else{
                    permBITS &= ~cBitwise.GROUP_DELETE;
                }
            }
        });

        checkBoxGroupSync = (AppCompatCheckBox) view.findViewById(R.id.checkBoxGroupSync);
        checkBoxGroupSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((CheckBox) view).isChecked();
                if (checked){
                    permBITS |= cBitwise.GROUP_SYNC;
                }
                else{
                    permBITS &= ~cBitwise.GROUP_SYNC;
                }
            }
        });

        /* initialize and set listeners on other permissions */
        checkBoxOtherCreate = (AppCompatCheckBox) view.findViewById(R.id.checkBoxOtherCreate);
        checkBoxOtherCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((CheckBox) view).isChecked();
                if (checked){
                    permBITS |= cBitwise.OTHER_CREATE;
                }
                else{
                    permBITS &= ~cBitwise.OTHER_CREATE;
                }
            }
        });

        checkBoxOtherRead = (AppCompatCheckBox) view.findViewById(R.id.checkBoxOtherRead);
        checkBoxOtherRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((CheckBox) view).isChecked();
                if (checked){
                    permBITS |= cBitwise.OTHER_READ;
                }
                else{
                    permBITS &= ~cBitwise.OTHER_READ;
                }
            }
        });

        checkBoxOtherUpdate = (AppCompatCheckBox) view.findViewById(R.id.checkBoxOtherUpdate);
        checkBoxOtherUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((CheckBox) view).isChecked();
                if (checked){
                    permBITS |= cBitwise.OTHER_UPDATE;
                }
                else{
                    permBITS &= ~cBitwise.OTHER_UPDATE;
                }
            }
        });

        checkBoxOtherDelete = (AppCompatCheckBox) view.findViewById(R.id.checkBoxOtherDelete);
        checkBoxOtherDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((CheckBox) view).isChecked();
                if (checked){
                    permBITS |= cBitwise.OTHER_DELETE;
                }
                else{
                    permBITS &= ~cBitwise.OTHER_DELETE;
                }
            }
        });

        checkBoxOtherSync = (AppCompatCheckBox) view.findViewById(R.id.checkBoxOtherSync);
        checkBoxOtherSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((CheckBox) view).isChecked();
                if (checked){
                    permBITS |= cBitwise.OTHER_SYNC;
                }
                else{
                    permBITS &= ~cBitwise.OTHER_SYNC;
                }
            }
        });

        /* initialize permissions */
        checkBoxOwnerCreate.setChecked((permBITS & cBitwise.OWNER_CREATE) == cBitwise.OWNER_CREATE);
        checkBoxOwnerRead.setChecked((permBITS & cBitwise.OWNER_READ) == cBitwise.OWNER_READ);
        checkBoxOwnerUpdate.setChecked((permBITS & cBitwise.OWNER_UPDATE) == cBitwise.OWNER_UPDATE);
        checkBoxOwnerDelete.setChecked((permBITS & cBitwise.OWNER_DELETE) == cBitwise.OWNER_DELETE);
        checkBoxOwnerSync.setChecked((permBITS & cBitwise.OWNER_SYNC) == cBitwise.OWNER_SYNC);

        checkBoxGroupCreate.setChecked((permBITS & cBitwise.GROUP_CREATE) == cBitwise.GROUP_CREATE);
        checkBoxGroupRead.setChecked((permBITS & cBitwise.GROUP_READ) == cBitwise.GROUP_READ);
        checkBoxGroupUpdate.setChecked((permBITS & cBitwise.GROUP_UPDATE) == cBitwise.GROUP_UPDATE);
        checkBoxGroupDelete.setChecked((permBITS & cBitwise.GROUP_DELETE) == cBitwise.GROUP_DELETE);
        checkBoxGroupSync.setChecked((permBITS & cBitwise.GROUP_SYNC) == cBitwise.GROUP_SYNC);

        checkBoxOtherCreate.setChecked((permBITS & cBitwise.OTHER_CREATE) == cBitwise.OTHER_CREATE);
        checkBoxOtherRead.setChecked((permBITS & cBitwise.OTHER_READ) == cBitwise.OTHER_READ);
        checkBoxOtherUpdate.setChecked((permBITS & cBitwise.OTHER_UPDATE) == cBitwise.OTHER_UPDATE);
        checkBoxOtherDelete.setChecked((permBITS & cBitwise.OTHER_DELETE) == cBitwise.OTHER_DELETE);
        checkBoxOtherSync.setChecked((permBITS & cBitwise.OTHER_SYNC) == cBitwise.OTHER_SYNC);
    }

    public int getPermBITS() {
        return permBITS;
    }
}
