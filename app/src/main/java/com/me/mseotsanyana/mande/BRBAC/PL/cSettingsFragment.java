package com.me.mseotsanyana.mande.BRBAC.PL;

//import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.me.mseotsanyana.mande.BRBAC.BLL.cSessionManager;
import com.me.mseotsanyana.mande.UTILITY.cUploadSessionData;
import com.me.mseotsanyana.mande.UTILITY.cUtil;
import com.me.mseotsanyana.mande.R;

public class cSettingsFragment extends Fragment {
    private cSessionManager session;

    private AppCompatButton appCompatButton;
    private BottomNavigationView bottomNavigationView;

    public cSettingsFragment(){}

    public static cSettingsFragment newInstance() {

        return new cSettingsFragment();
    }
    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        session = new cSessionManager(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_settings_fragment, container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        appCompatButton = (AppCompatButton) view.findViewById(R.id.appCompatButtonUploadRBAC);
        appCompatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new cUploadSessionData(getContext(), session).execute();            }
        });

        bottomNavigationView = (BottomNavigationView) view.findViewById(R.id.bottom_navigation);
        bottomNavigationView.getMenu().getItem(3).setChecked(true);

        cUtil.setIcon(getContext(),bottomNavigationView, 3);
        cUtil.disableShiftMode(bottomNavigationView);
        setupBottomNavigation();
    }

    private void setupBottomNavigation() {

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.action_login:
                        pushFragment(cLoginFragment.newInstance(null));
                        return true;
                    case R.id.action_create:
                        pushFragment(cRegisterFragment.newInstance());
                        return true;
                    case R.id.action_join:
                        pushFragment(cJoinFragment.newInstance());
                        return true;
                }
                return false;
            }
        });
    }


    /**Preferences
     * Method to push any fragment into given id.
     *
     * @param fragment An instance of FPreferencesragment to show into the given id.
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
