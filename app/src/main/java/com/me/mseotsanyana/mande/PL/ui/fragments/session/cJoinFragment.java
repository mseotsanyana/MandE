package com.me.mseotsanyana.mande.PL.ui.fragments.session;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.me.mseotsanyana.mande.R;

public class cJoinFragment extends Fragment implements View.OnClickListener {

    //private cSessionManager session;
    private BottomNavigationView bottomNavigationView;

    public cJoinFragment(){}

    public static cJoinFragment newInstance(){
        cJoinFragment fragment = new cJoinFragment();

        return fragment;
    }

    private TextView tv_name,tv_email,tv_message;
    private SharedPreferences pref;
    private AppCompatButton btn_change_password,btn_logout;
    private EditText et_old_password,et_new_password;
    private AlertDialog dialog;
    private ProgressBar progress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home_join_fragment,container,false);
        initViews(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        pref = getActivity().getPreferences(0);
        //tv_name.setText("Welcome : "+pref.getString(cConstant.KEY_NAME,""));
        //tv_email.setText(pref.getString(cConstant.KEY_EMAIL,""));

        //bottomNavigationView = (BottomNavigationView) view.findViewById(R.id.bottom_navigation);
        //bottomNavigationView.getMenu().getItem(2).setChecked(true);

        //cUtil.setIcon(getContext(),bottomNavigationView, 2);
        //cUtil.disableShiftMode(bottomNavigationView);

        //setupBottomNavigation();
    }

    private void initViews(View view){

        tv_name = (TextView)view.findViewById(R.id.tv_name);
        tv_email = (TextView)view.findViewById(R.id.tv_email);
        btn_change_password = (AppCompatButton)view.findViewById(R.id.btn_chg_password);
        btn_logout = (AppCompatButton)view.findViewById(R.id.btn_logout);
        btn_change_password.setOnClickListener(this);
        btn_logout.setOnClickListener(this);

    }

    private void showDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_change_password, null);
        et_old_password = (EditText)view.findViewById(R.id.et_old_password);
        et_new_password = (EditText)view.findViewById(R.id.et_new_password);
        tv_message = (TextView)view.findViewById(R.id.tv_message);
        progress = (ProgressBar)view.findViewById(R.id.progress);
        builder.setView(view);
        builder.setTitle("Change Password");
        builder.setPositiveButton("Change Password", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String old_password = et_old_password.getText().toString();
                    String new_password = et_new_password.getText().toString();
                    if(!old_password.isEmpty() && !new_password.isEmpty()){

                        progress.setVisibility(View.VISIBLE);
                        //changePasswordProcess(pref.getString(cConstant.KEY_EMAIL,""),old_password,new_password);

                    }else {

                        tv_message.setVisibility(View.VISIBLE);
                        tv_message.setText("Fields are empty");
                    }
                }
            });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btn_chg_password:
                showDialog();
                break;
            case R.id.btn_logout:
                logout();
                break;
        }
    }

    private void logout() {
        SharedPreferences.Editor editor = pref.edit();
        //editor.putBoolean(cConstant.KEY_IS_LOGGEDIN,false);
        //editor.putString(cConstant.KEY_EMAIL,"");
        //editor.putString(cConstant.KEY_NAME,"");
        //editor.putString(cConstant.UNIQUE_ID,"");
        //editor.apply();
        //goToLogin();
    }
/*
    private void goToLogin(){

        Fragment login = new cLoginFragment_content_holder();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame,login);
        ft.commit();
    }
*/
//    private void changePasswordProcess(String email,String old_password,String new_password){
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(cConstant.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        iRequestInterface iRequestInterface = retrofit.create(iRequestInterface.class);
//
//        cUserModel user = new cUserModel();
//        user.setEmail(email);
//        user.setOldPassword(old_password);
//        user.setNewPassword(new_password);
//        cUserRequest request = new cUserRequest();
//        request.setOperation(cConstant.CHANGE_PASSWORD_OPERATION);
//        request.setUser(user);
//        Call<cUserResponse> response = iRequestInterface.operation(request);
//
//        response.enqueue(new Callback<cUserResponse>() {
//            @Override
//            public void onResponse(Call<cUserResponse> call, retrofit2.Response<cUserResponse> response) {
//
//                cUserResponse resp = response.body();
//                if(resp.getResult().equals(cConstant.SUCCESS)){
//                    progress.setVisibility(View.GONE);
//                    tv_message.setVisibility(View.GONE);
//                    dialog.dismiss();
//                    Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();
//
//                }else {
//                    progress.setVisibility(View.GONE);
//                    tv_message.setVisibility(View.VISIBLE);
//                    tv_message.setText(resp.getMessage());
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<cUserResponse> call, Throwable t) {
//
//                //Log.d(cConstant.KEY_TAG,"failed");
//                progress.setVisibility(View.GONE);
//                tv_message.setVisibility(View.VISIBLE);
//                tv_message.setText(t.getLocalizedMessage());
//
//
//            }
//        });
//    }
/*
    private void setupBottomNavigation() {

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.action_login:
                        pushFragment(new cLoginFragment());//;.newInstance(session));
                        return true;
                    case R.id.action_create:
                        pushFragment(cRegisterFragment.newInstance());
                        return true;
                    case R.id.action_settings:
                        pushFragment(cSettingsFragment.newInstance());
                        return true;
                }
                return false;
            }
        });
    }
*/
    /**
     * Method to push any fragment into given id.
     *
     * @param fragment An instance of Fragment to show into the given id.
     */
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
