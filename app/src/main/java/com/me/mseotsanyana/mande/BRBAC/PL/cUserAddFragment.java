package com.me.mseotsanyana.mande.BRBAC.PL;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.me.mseotsanyana.mande.BRBAC.BLL.cAddressDomain;
import com.me.mseotsanyana.mande.BRBAC.BLL.cAddressHandler;
import com.me.mseotsanyana.mande.BRBAC.BLL.cOrganizationDomain;
import com.me.mseotsanyana.mande.BRBAC.BLL.cOrganizationHandler;
import com.me.mseotsanyana.mande.BRBAC.BLL.cSessionManager;
import com.me.mseotsanyana.mande.BRBAC.BLL.cUserDomain;
import com.me.mseotsanyana.mande.BRBAC.BLL.cUserHandler;
import com.me.mseotsanyana.mande.INTERFACE.iMEEntityInterface;
import com.me.mseotsanyana.mande.PPMER.PL.cMainFragment;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.UTILITY.cUtil;
import com.me.mseotsanyana.multiselectspinnerlibrary.cKeyPairBoolData;
import com.me.mseotsanyana.multiselectspinnerlibrary.cSingleSpinnerSearch;
import com.me.mseotsanyana.multiselectspinnerlibrary.cSpinnerListener;
import com.me.mseotsanyana.validatorlibrary.cValidator;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

//import com.me.mseotsanyana.mande.PPMER.PL.cMainFragment;

public class cUserAddFragment extends Fragment {
    private static final String TAG = "cUserEditFragment";
    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

    private cSessionManager session;
    // toolbar views
    private Toolbar toolBar;
    private Menu toolBarMenu;
    private MenuItem toolBarMenuItem;

    private cValidator validator;
    private cUserDomain userDomain;
    private cAddressDomain addressDomain;

    private cOrganizationHandler organizationHandler;
    private cUserHandler userHandler;
    private cAddressHandler addressHandler;

    // user profile views
    private ImageView imageViewPhoto;
    private cSingleSpinnerSearch searchSingleSpinnerOrganization;

    private TextInputEditText textInputEditTextFirstName;
    private TextInputEditText textInputEditTextLastName;
    private RadioGroup radioGroupGender;
    private TextInputEditText textInputEditTextDescription;
    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextWebsite;
    private TextInputEditText textInputEditTextPhone;
    private TextInputEditText textInputEditTextPassword;
    private TextInputEditText textInputEditTextConfirmPassword;

    // user address views
    private TextInputEditText textInputEditTextStreet;
    private TextInputEditText textInputEditTextCity;
    private TextInputEditText textInputEditTextProvice;
    private TextInputEditText textInputEditTextPostalCode;
    private TextInputEditText textInputEditTextCountry;

    // button views
    private AppCompatButton appCompatButtonSave;
    private AppCompatButton appCompatButtonCancel;

    private int organizationID;
    private int organizationPosition;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;
    private Bitmap userBitmap = null;
    private String gender;

    private iMEEntityInterface userInterface;

    public cUserAddFragment() {
        // Required empty public constructor
    }

    public static cUserAddFragment newInstance(final iMEEntityInterface userInterface) {
        cUserAddFragment fragment = new cUserAddFragment();

        Bundle bundle = new Bundle();

        bundle.putSerializable("IUSER", userInterface);

        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        session = new cSessionManager(getContext());

        validator = new cValidator();

        userDomain = new cUserDomain();
        addressDomain = new cAddressDomain();
        userHandler = new cUserHandler(getContext(), session);
        addressHandler = new cAddressHandler(getContext());

        // initialise a handler and get organization data from the database
        organizationHandler = new cOrganizationHandler(getContext(), session);

        userInterface = (iMEEntityInterface) getArguments().getSerializable("IUSER");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.user_add_edit_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // get all organization from database
        final ArrayList<cOrganizationDomain> allOrganizations =
                organizationHandler.getOrganizationList();

        // create a action_list of organization ids and names
        final List<cKeyPairBoolData> keyPairBoolDataList = new ArrayList<>();

        for (int i = 0; i < allOrganizations.size(); i++) {
            cKeyPairBoolData idNameBool = new cKeyPairBoolData();
            idNameBool.setId(allOrganizations.get(i).getOrganizationID());
            idNameBool.setName(allOrganizations.get(i).getName());
            idNameBool.setSelected(false);
            keyPairBoolDataList.add(idNameBool);
        }

        /** 1. initialize the toolbar **/
        toolBar = (Toolbar) view.findViewById(R.id.me_toolbar);

        /** 2. initialize profile picture **/
        imageViewPhoto = (ImageView) view.findViewById(R.id.imageViewPhoto);
        imageViewPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        /** 3. initialize spinner views **/
        searchSingleSpinnerOrganization = (cSingleSpinnerSearch)
                view.findViewById(R.id.singleSpinnerSearchOrganization);

        /***
         * -1 is no by default selection
         *  0 to length will select corresponding values
         */

        // called when click spinner
        searchSingleSpinnerOrganization.setItems(keyPairBoolDataList, -1, new cSpinnerListener() {
            @Override
            public void onItemsSelected(List<cKeyPairBoolData> items) {
                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {
                        organizationID = (int) items.get(i).getId(); //FIXME
                        //organizationPosition = i;
                        //Log.d(TAG, i + " : " + items.get(i).getId() + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                    }
                }
            }
        });

        /** 4. initialize the first name view **/
        textInputEditTextFirstName = (TextInputEditText) view.findViewById(R.id.textInputEditTextFirstName);

        /** 5. initialize the last name view **/
        textInputEditTextLastName = (TextInputEditText) view.findViewById(R.id.textInputEditTextLastName);

        /** 6. initialize the gender view **/
        radioGroupGender = (RadioGroup) view.findViewById(R.id.radioGroupGender);

        /** 7. initialize the description view **/
        textInputEditTextDescription = (TextInputEditText) view.findViewById(R.id.textInputEditTextDescription);

        /** 8. initialize the email view **/
        textInputEditTextEmail = (TextInputEditText) view.findViewById(R.id.textInputEditTextEmail);

        /** 9. initialize the website view **/
        textInputEditTextWebsite = (TextInputEditText) view.findViewById(R.id.textInputEditTextWebsite);

        /** 10. initialize the phone view **/
        textInputEditTextPhone = (TextInputEditText) view.findViewById(R.id.textInputEditTextPhone);

        /** 11. initialize the password view **/
        textInputEditTextPassword = (TextInputEditText) view.findViewById(R.id.textInputEditTextPassword);

        /** 12. initialize the confirm password view **/
        textInputEditTextConfirmPassword = (TextInputEditText) view.findViewById(R.id.textInputEditTextConfirmPassword);

        // initialize user address views

        /** 13. initialize the street view **/
        textInputEditTextStreet = (TextInputEditText) view.findViewById(R.id.textInputEditTextStreet);

        /** 14. initialize the city view **/
        textInputEditTextCity = (TextInputEditText) view.findViewById(R.id.textInputEditTextCity);

        /** 15. initialize the province view **/
        textInputEditTextProvice = (TextInputEditText) view.findViewById(R.id.textInputEditTextProvice);

        /** 16. initialize the postal code view **/
        textInputEditTextPostalCode = (TextInputEditText) view.findViewById(R.id.textInputEditTextPostalCode);

        /** 17. initialize the country view **/
        textInputEditTextCountry = (TextInputEditText) view.findViewById(R.id.textInputEditTextCountry);

        /** 18. initialize the save button **/
        appCompatButtonSave = (AppCompatButton) view.findViewById(R.id.appCompatButtonSave);

        // initialize button views
        appCompatButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateForm()) {
                    if (addForm()){
                        userInterface.onResponseMessage("User Entity",
                                "User Entity Successfully Created.");
                    }else {
                        userInterface.onResponseMessage("User Entity",
                                " Error in Creating User Entity");
                    }

                }else{
                    userInterface.onResponseMessage("User Entity","Invalid Data");
                    //editForm();
                }


                    //addForm();
                    //pushFragment(cUserFragment.newInstance());
                //}
            }
        });

        // 19. initialize the save button
        appCompatButtonCancel = (AppCompatButton) view.findViewById(R.id.appCompatButtonCancel);
        appCompatButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pushFragment(cUserFragment.newInstance());
            }
        });
    }

    private boolean addForm() {
        // create address
        addressDomain.setStreet(textInputEditTextStreet.getText().toString());
        addressDomain.setCity(textInputEditTextCity.getText().toString());
        addressDomain.setProvince(textInputEditTextProvice.getText().toString());
        addressDomain.setPostalCode(textInputEditTextPostalCode.getText().toString());
        addressDomain.setCountry(textInputEditTextCountry.getText().toString());

        // create address
        long userID = -1;
        long addressID = addressHandler.createAddress(addressDomain);
        //if (addressID < 0) {
        //    userInterface.onResponseMessage("User Entity","Error in creating address.");
            //Toast.makeText(getContext(), " Error in creating address", Toast.LENGTH_SHORT).show();
        //}

        if (addressID > 0) {
            // user details
            cUserDomain domain = new cUserDomain();
            domain.setOrganizationID(organizationID);
            //domain.setAddressID((int) addressID);
            domain.setName(textInputEditTextFirstName.getText().toString());
            domain.setSurname(textInputEditTextLastName.getText().toString());
            int selectedId = radioGroupGender.getCheckedRadioButtonId();
            if (selectedId == R.id.radioButtonFemale)
                gender = "Female";
            else
                gender = "Male";
            domain.setGender(gender);
            domain.setDescription(textInputEditTextDescription.getText().toString());
            domain.setEmail(textInputEditTextEmail.getText().toString());
            domain.setWebsite(textInputEditTextWebsite.getText().toString());
            domain.setPhone(textInputEditTextPhone.getText().toString());
            domain.setPassword(textInputEditTextPassword.getText().toString());

            userID = userHandler.createUser(domain);
            //if (userID < 0) {
            //    userInterface.onResponseMessage("User Entity", "Error in creating user.");
                //Toast.makeText(getContext(), " Error in creating user", Toast.LENGTH_SHORT).show();
            //}

            // add photo to internal storage and update photo path in database
            if (userBitmap != null) {
                imageViewPhoto.setImageBitmap(userBitmap);
                String photoPath = userHandler.updatePhotoFromStorage((int) userID, userBitmap);
                domain.setPhoto(photoPath);
            } else {
                imageViewPhoto.setImageResource(R.drawable.ic_launcher);
            }
        //} else {
          //  userInterface.onResponseMessage("User Entity", "Error in creating address.");
            //Toast.makeText(getContext(), " Error in creating address", Toast.LENGTH_SHORT).show();
        }

        return ((addressID > 0) && (userID > 0));
    }

    private boolean validateForm() {
        // validate first name
        boolean isFirstNameValid;
        if (validator.isAlpha(textInputEditTextFirstName.getText().toString())) {
            isFirstNameValid = true;
        } else {
            isFirstNameValid = false;
            textInputEditTextFirstName.setError("Invalid first name!");
        }

        // validate last name
        boolean isLastNameValid;
        if (validator.isAlpha(textInputEditTextLastName.getText().toString())) {
            isLastNameValid = true;
        } else {
            isLastNameValid = false;
            textInputEditTextLastName.setError("Invalid last name!");
        }

        // validate job title
        boolean isJobTitleValid;
        if (validator.isAlpha(textInputEditTextDescription.getText().toString())) {
            isJobTitleValid = true;
        } else {
            isJobTitleValid = false;
            textInputEditTextDescription.setError("Invalid job title!");
        }

        // validate email
        boolean isEmailValid;
        if (validator.isEmail(textInputEditTextEmail.getText().toString())) {
            isEmailValid = true;
        } else {
            isEmailValid = false;
            textInputEditTextEmail.setError("Invalid email address!");
        }

        // validate website:
        boolean isWebsiteValid;
        if (validator.isWebsiteURL(textInputEditTextWebsite.getText().toString())) {
            isWebsiteValid = true;
        } else {
            isWebsiteValid = false;
            textInputEditTextWebsite.setError("Invalid website address!");
        }

        // validate phone number:
        boolean isPhoneValid;
        if (validator.isPhoneNumber(textInputEditTextPhone.getText().toString())) {
            isPhoneValid = true;
        } else {
            isPhoneValid = false;
            textInputEditTextPhone.setError("Invalid phone number!");
        }

        // validate password: FIXME
        boolean isPasswordValid;
        if (validator.isAlpha(textInputEditTextPassword.getText().toString())) {
            isPasswordValid = true;
        } else {
            isPasswordValid = false;
            textInputEditTextPassword.setError("Invalid password!");
        }

        // validate confirm password: FIXME
        boolean isConfirmPasswordValid;
        if (validator.isAlpha(textInputEditTextConfirmPassword.getText().toString())) {
            isConfirmPasswordValid = true;
        } else {
            isConfirmPasswordValid = false;
            textInputEditTextConfirmPassword.setError("Invalid confirm password!");
        }

        boolean isStreetValid;
        // validate street
        if (validator.isAlpha(textInputEditTextStreet.getText().toString())) {
            isStreetValid = true;
        } else {
            isStreetValid = false;
            textInputEditTextStreet.setError("Invalid street name!");
        }

        // validate city
        boolean isCityValid;
        if (validator.isAlpha(textInputEditTextCity.getText().toString())) {
            isCityValid = true;
        } else {
            isCityValid = false;
            textInputEditTextCity.setError("Invalid city name!");
        }

        // validate province
        boolean isProvinceValid;
        if (validator.isAlpha(textInputEditTextProvice.getText().toString())) {
            isProvinceValid = true;
        } else {
            isProvinceValid = false;
            textInputEditTextProvice.setError("Invalid province name!");
        }

        // validate postal code
        boolean isPostalCodeValid;
        if (validator.isAlphanumeric(textInputEditTextPostalCode.getText().toString())) {
            isPostalCodeValid = true;
        } else {
            isPostalCodeValid = false;
            textInputEditTextPostalCode.setError("Invalid postal code!");
        }

        // validate country
        boolean isCountryValid;
        if (validator.isAlpha(textInputEditTextCountry.getText().toString())) {
            isCountryValid = true;
        } else {
            isCountryValid = false;
            textInputEditTextCountry.setError("Invalid country name!");
        }

        return (isStreetValid && isCityValid && isProvinceValid &&
                isPostalCodeValid && isCountryValid);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case cUtil.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if (userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    //code for deny
                    Log.d(TAG, "Permission Denied ");

                }
                break;
        }
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = cUtil.checkPermission(getContext());
                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result) {
                        cameraIntent();
                    }

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    if (result) {
                        galleryIntent();
                    }

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        userBitmap = (Bitmap) data.getExtras().get("data");
        imageViewPhoto.setImageBitmap(userBitmap);
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        if (data != null) {
            try {
                userBitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),
                        data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        imageViewPhoto.setImageBitmap(userBitmap);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        toolBar.inflateMenu(R.menu.menu_main);
        toolBarMenu = toolBar.getMenu();

        toolBarMenuItem = toolBarMenu.findItem(R.id.homeItem);

        toolBar.setOnMenuItemClickListener(
                new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        return onOptionsItemSelected(item);
                    }
                });

        SearchView searchView = (SearchView) toolBarMenu.findItem(R.id.searchItem).getActionView();

        search(searchView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.homeItem:
                pushFragment(cMainFragment.newInstance(session));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                //roleUserTreeAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }

    protected void pushFragment(Fragment fragment) {
        if (fragment == null)
            return;

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (ft != null) {
            ft.replace(R.id.fragment_frame, fragment);
            ft.commit();
        }
    }
}
