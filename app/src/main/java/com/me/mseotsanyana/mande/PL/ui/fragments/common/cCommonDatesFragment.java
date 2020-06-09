package com.me.mseotsanyana.mande.PL.ui.fragments.common;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.me.mseotsanyana.mande.R;

import java.util.Date;

public class cCommonDatesFragment extends Fragment {

    public static final String TITLE = "Dates";

    private String createdDate;
    private String modifiedDate;
    private String syncedDate;

    private AppCompatTextView textViewCreatedDate;
    private AppCompatTextView textViewModifiedDate;
    private AppCompatTextView textViewSyncedDate;

    public static cCommonDatesFragment newInstance(String createdDate, String modifiedDate,
                                                   String syncedDate) {
        Bundle bundle = new Bundle();

        bundle.putString("DATE_CREATED", createdDate);
        bundle.putString("DATE_MODIFIED", modifiedDate);
        bundle.putString("DATE_SYNCED", syncedDate);

        cCommonDatesFragment fragment = new cCommonDatesFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.createdDate = getArguments().getString("DATE_CREATED", "");
        this.modifiedDate = getArguments().getString("DATE_CREATED", "");
        this.syncedDate = getArguments().getString("DATE_CREATED", "");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.me_common_dates, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /* initialize and set dates */
        this.textViewCreatedDate = (AppCompatTextView) view.findViewById(R.id.textViewCreatedDate);
        this.textViewModifiedDate = (AppCompatTextView) view.findViewById(R.id.textViewModifiedDate);
        this.textViewSyncedDate = (AppCompatTextView) view.findViewById(R.id.textViewSyncedDate);

        this.textViewCreatedDate.setText(this.createdDate);
        this.textViewModifiedDate.setText(this.modifiedDate);
        this.textViewSyncedDate.setText(this.syncedDate);
    }
}
