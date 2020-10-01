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
 * Created by mseotsanyana on 2016/08/12.
 */
public class cAddProjectFragment extends DialogFragment {

    private OnAddProjectListener callback;


    private EditText nameEdit;
    private EditText descriptionEdit;
    private EditText countryEdit;
    private EditText regionEdit;
    private EditText startDateEdit;
    private EditText closeDateEdit;

    private Spinner statusSpinner;
    private Spinner managerSpinner;

    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

    Calendar newCalendar = Calendar.getInstance();

    DatePickerDialog startDatePickerDialog;
    DatePickerDialog closeDatePickerDialog;

    private Button saveButton;
    private Button cancelButton;

    public interface OnAddProjectListener {
        //void onAddProject(cProjectDomain projectDomain);
    }

    void cAddProjectFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //Your callback initialization here
        try {
            callback = (OnAddProjectListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException("Calling Fragment must implement OnAddProjectListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setTitle("Add Project");
        return inflater.inflate(R.layout.fragment_add_project, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // get fields from view
        nameEdit = (EditText) view.findViewById(R.id.project_name_id);
        descriptionEdit = (EditText) view.findViewById(R.id.project_description_id);
        countryEdit = (EditText) view.findViewById(R.id.project_country_id);
        regionEdit = (EditText) view.findViewById(R.id.project_region_id);
        startDateEdit = (EditText) view.findViewById(R.id.project_startdate_id);
        closeDateEdit = (EditText) view.findViewById(R.id.project_closedate_id);

        statusSpinner = (Spinner) view.findViewById(R.id.project_status_id);
        managerSpinner = (Spinner) view.findViewById(R.id.project_manager_id);

        saveButton = (Button) view.findViewById(R.id.button_save_id);
        cancelButton = (Button) view.findViewById(R.id.button_cancel_id);

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
                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
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

                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                closeDatePickerDialog.show();

            }
        });
    }
}
//
//        // event handling of the add button
//        saveButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//
//                projectDomain = new cProjectDomain();
//
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
//                /*--boolean result = projectHandler.addProject(projectDomain);
//
//                if (result) {
//                    callback.onAddProject(projectDomain);
//                }else{
//                    Toast.makeText(getActivity(),
//                            "Unable to Add Project",
//                            Toast.LENGTH_SHORT).show();
//                }*/
//                dismiss();
//            }
//        });
//
//        // event handling of the add button
//        cancelButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                // dismiss the dialog box
//                dismiss();
//            }
//        });
//    }

    /*
    public void addProjectFromExcel(Row cRow){
        projectDomain = new cProjectDomain();

        projectDomain.setProjectID((int)cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        projectDomain.setOverallAimID((int)cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        projectDomain.setSpecificAimID((int)cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        projectDomain.setOwnerID((int)cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        projectDomain.setProjectManagerID((int)cRow.getCell(4, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        projectDomain.setProjectName(cRow.getCell(5, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        projectDomain.setProjectDescription(cRow.getCell(6, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        projectDomain.setCountry(cRow.getCell(7, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        projectDomain.setRegion(cRow.getCell(8, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        projectDomain.setProjectStatus((int)cRow.getCell(9, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        projectDomain.setCreateDate(cRow.getCell(10, Row.CREATE_NULL_AS_BLANK).getDateCellValue());
        projectDomain.setStartDate(cRow.getCell(11, Row.CREATE_NULL_AS_BLANK).getDateCellValue());
        projectDomain.setCloseDate(cRow.getCell(12, Row.CREATE_NULL_AS_BLANK).getDateCellValue());


        projectDomain.setProjectID((int)cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        projectDomain.setOverallAimID((int)cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        projectDomain.setSpecificAimID((int)cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        projectDomain.setOwnerID((int)cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        projectDomain.setProjectManagerID((int)cRow.getCell(4, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        projectDomain.setProjectName(cRow.getCell(5, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        projectDomain.setProjectDescription(cRow.getCell(6, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        projectDomain.setCountry(cRow.getCell(7, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        projectDomain.setRegion(cRow.getCell(8, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        projectDomain.setProjectStatus((int)cRow.getCell(9, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        projectDomain.setCreateDate(cRow.getCell(10, Row.CREATE_NULL_AS_BLANK).getDateCellValue());
        projectDomain.setStartDate(cRow.getCell(11, Row.CREATE_NULL_AS_BLANK).getDateCellValue());
        projectDomain.setCloseDate(cRow.getCell(12, Row.CREATE_NULL_AS_BLANK).getDateCellValue());

        // add the row into the database
        boolean result = projectHandler.addProjectFromExcel(projectDomain);

        if (!result) {
            Toast.makeText(getActivity(),
                    "Unable to add project from excel file",
                    Toast.LENGTH_SHORT).show();
        }
    }

    }
*/