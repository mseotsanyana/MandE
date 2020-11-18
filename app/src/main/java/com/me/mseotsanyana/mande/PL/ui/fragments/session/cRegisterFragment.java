package com.me.mseotsanyana.mande.PL.ui.fragments.session;

//import android.app.Fragment;
//import android.app.FragmentTransaction;
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
import com.google.android.material.snackbar.Snackbar;
import com.me.mseotsanyana.mande.R;

public class cRegisterFragment extends Fragment implements View.OnClickListener{

    private BottomNavigationView bottomNavigationView;

    public cRegisterFragment(){}

    public static cRegisterFragment newInstance(){
        cRegisterFragment fragment = new cRegisterFragment();

        return fragment;
    }

    private AppCompatButton btn_register;
    private EditText et_email,et_password,et_name;
    private TextView tv_login;
    private ProgressBar progress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home_register_fragment,container,false);
        initViews(view);
        return view;
    }

    private void initViews(View view){

        //btn_register = (AppCompatButton)view.findViewById(R.id.btn_register);
        //tv_login = (TextView)view.findViewById(R.id.tv_login);
        //et_name = (EditText)view.findViewById(R.id.et_name);
        //et_email = (EditText)view.findViewById(R.id.et_email);
        //et_password = (EditText)view.findViewById(R.id.et_password);

        //progress = (ProgressBar)view.findViewById(R.id.progress);

        //btn_register.setOnClickListener(this);
        //tv_login.setOnClickListener(this);
        //bottomNavigationView = (BottomNavigationView) view.findViewById(R.id.bottom_navigation);
        //bottomNavigationView.getMenu().getItem(1).setChecked(true);

        //cUtil.setIcon(getContext(),bottomNavigationView, 1);
        //cUtil.disableShiftMode(bottomNavigationView);

        //setupBottomNavigation();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.tv_login:
                //goToLogin();
                break;

            case R.id.btn_register:

                String name = et_name.getText().toString();
                String email = et_email.getText().toString();
                String password = et_password.getText().toString();

                if(!name.isEmpty() && !email.isEmpty() && !password.isEmpty()) {

                    progress.setVisibility(View.VISIBLE);
                    //registerProcess(name,email,password);

                } else {

                    Snackbar.make(getView(), "Fields are empty !", Snackbar.LENGTH_LONG).show();
                }
                break;

        }

    }

//    private void registerProcess(String name, String email,String password){
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(cConstant.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        iRequestInterface iRequestInterface = retrofit.create(iRequestInterface.class);
//
//        cUserModel user = new cUserModel();
//        user.setName(name);
//        user.setEmail(email);
//        user.setPassword(password);
//        cUserRequest request = new cUserRequest();
//        request.setOperation(cConstant.REGISTER_OPERATION);
//        request.setUser(user);
//        Call<cUserResponse> response = iRequestInterface.operation(request);
//
//        response.enqueue(new Callback<cUserResponse>() {
//            @Override
//            public void onResponse(Call<cUserResponse> call, retrofit2.Response<cUserResponse> response) {
//
//                cUserResponse resp = response.body();
//                Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();
//                progress.setVisibility(View.INVISIBLE);
//            }
//
//            @Override
//            public void onFailure(Call<cUserResponse> call, Throwable t) {
//
//                progress.setVisibility(View.INVISIBLE);
//                //Log.d(cConstant.KEY_TAG,"failed");
//                Snackbar.make(getView(), t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();
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
                        pushFragment(new cLoginFragment());//.newInstance(null));
                        return true;
                    case R.id.action_join:
                        pushFragment(cJoinFragment.newInstance());
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
