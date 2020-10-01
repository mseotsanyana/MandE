package com.me.mseotsanyana.mande.PL.ui.fragments.report;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.me.mseotsanyana.mande.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by mseotsanyana on 2016/12/10.
 */

public class cEditProjectFragment extends DialogFragment implements AdapterView.OnItemSelectedListener{

    private cEditProjectFragment.OnEditProjectListener callback;

    //cProjectDomain projectDomain;
    //cProjectHandler projectHandler;

    private EditText nameEdit;
    private String name;
    private EditText descriptionEdit;
    private String description;
    private EditText countryEdit;
    private String country;
    private EditText regionEdit;
    private String region;
    private EditText startDateEdit;
    private String startDate;
    private EditText closeDateEdit;
    private String closeDate;

    private Spinner statusSpinner;
    private int statusPos;
    private Spinner managerSpinner;
    private int managerPos;

    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

    Calendar newCalendar = Calendar.getInstance();

    DatePickerDialog startDatePickerDialog;
    DatePickerDialog closeDatePickerDialog;

    private Button saveButton;
    private Button cancelButton;

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        //selection.setText(items[position]);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public interface OnEditProjectListener{
        //void onEditProject(cProjectDomain projectDomain);
        //cProjectDomain getProjectDomain();
    }

    void cAddProjectFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //Your callback initialization here
        try {
            callback = (cEditProjectFragment.OnEditProjectListener) getTargetFragment();
        }catch (ClassCastException e) {
            throw new ClassCastException("Calling Fragment must implement OnAddProjectListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        getDialog().setTitle("Edit Project");
//        // get data from adapter
//        projectDomain = callback.getProjectDomain();
//
//        name          = projectDomain.getProjectName();
//        description   = projectDomain.getProjectDescription();
//        country       = projectDomain.getCountry();
//        region        = projectDomain.getRegion();
//        startDate     = formatter.format(projectDomain.getStartDate());
//        closeDate     = formatter.format(projectDomain.getCloseDate());
//        statusPos     = projectDomain.getProjectStatus();
//        managerPos    = projectDomain.getProjectManagerID();
//
        return inflater.inflate(R.layout.fragment_add_project, container);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        // get fields from view
        nameEdit = (EditText)view.findViewById(R.id.project_name_id);
        nameEdit.setText(name);

        descriptionEdit = (EditText)view.findViewById(R.id.project_description_id);
        descriptionEdit.setText(description);

        countryEdit = (EditText)view.findViewById(R.id.project_country_id);
        countryEdit.setText(country);

        regionEdit = (EditText)view.findViewById(R.id.project_region_id);
        regionEdit.setText(region);

        startDateEdit = (EditText)view.findViewById(R.id.project_startdate_id);
        startDateEdit.setText(startDate);

        closeDateEdit = (EditText)view.findViewById(R.id.project_closedate_id);
        closeDateEdit.setText(closeDate);

        statusSpinner = (Spinner)view.findViewById(R.id.project_status_id);
        statusSpinner.setOnItemSelectedListener(this);
        //statusSpinner.setSelection(statusPos);

        //String l = statusSpinner.getItemAtPosition(statusPos).toString();
        //statusSpinner.setPrompt(l);
        //Toast.makeText(getActivity(),
        //        l,
         //       Toast.LENGTH_SHORT).show();


        managerSpinner = (Spinner)view.findViewById(R.id.project_manager_id);
        managerSpinner.setOnItemSelectedListener(this);
        //managerSpinner.setSelection(managerPos);

        saveButton = (Button)view.findViewById(R.id.button_save_id);
        cancelButton = (Button)view.findViewById(R.id.button_cancel_id);

        // show soft keyboard automatically and request focus to field
        nameEdit.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);


        // start date listener
        startDateEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        startDateEdit.setText(formatter.format(newDate.getTime()));
                    }
                },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                startDatePickerDialog.show();
            }
        });

        // closing date listener
        closeDateEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        closeDateEdit.setText(formatter.format(newDate.getTime()));
                    }

                },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                closeDatePickerDialog.show();

            }
        });

        // event handling of the edit button
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                // update the database
//                projectDomain.setProjectName(nameEdit.getText().toString());
//                projectDomain.setProjectDescription(descriptionEdit.getText().toString());
//                projectDomain.setCountry(countryEdit.getText().toString());
//                projectDomain.setRegion(regionEdit.getText().toString());
//
//                int selectedStatus = statusSpinner.getSelectedItemPosition();
//                projectDomain.setProjectStatus(selectedStatus);
//
//                int selectedManager = managerSpinner.getSelectedItemPosition();
//                projectDomain.setProjectManagerID(selectedManager);
//
//                try {
//                    projectDomain.setStartDate(formatter.parse(startDateEdit.getText().toString()));
//                }catch (ParseException e){
//                    projectDomain.setStartDate(null);
//                }
//
//                try {
//                    projectDomain.setCloseDate(formatter.parse(closeDateEdit.getText().toString()));
//                }catch (ParseException e) {
//                    projectDomain.setCloseDate(null);
//                }
//
//                projectHandler = new cProjectHandler(getActivity());
                /*--boolean result = projectHandler.updateProject(projectDomain);

                if (result) {
                    callback.onEditProject(projectDomain);
                }else{
                    Toast.makeText(getActivity(),
                            "Unable to Edit Project",
                            Toast.LENGTH_SHORT).show();
                }*/
                dismiss();
            }
        });

        // event handling of the add button
        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // dismiss the dialog box
                dismiss();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();

        nameEdit.setText(name);
        descriptionEdit.setText(description);
        countryEdit.setText(country);
        regionEdit.setText(region);
        startDateEdit.setText(startDate);
        closeDateEdit.setText(closeDate);
        statusSpinner.setSelection(statusPos);
        managerSpinner.setSelection(managerPos);
    }
}
