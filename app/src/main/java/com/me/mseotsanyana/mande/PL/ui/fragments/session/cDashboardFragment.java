package com.me.mseotsanyana.mande.PL.ui.fragments.session;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.me.mseotsanyana.mande.R;

public class cDashboardFragment extends Fragment {
    //private static String TAG = cDashboardFragment.class.getSimpleName();

    // required empty public constructor
    public cDashboardFragment() {
    }

    public static cDashboardFragment newInstance() {
        return new cDashboardFragment();
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
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.session_dashboard_fragment, container, false);
    }

    /**
     * this event is triggered soon after on CreateView(). onViewCreated is called if the
     * view is returned from onCreateView() is non-null. Any view setup should occur here. e.g.
     * view lookups and attaching view listeners.
     */
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        /* create toolbar and progressbar */
        initViews(view);
    }

    private void initViews(View view) {
        ImageView imageViewOrganizations = view.findViewById(R.id.imageViewOrganizations);
        imageViewOrganizations.setOnClickListener(clickView -> {
            NavDirections action = cHomeFragmentDirections.
                    actionCHomeFragmentToCOrganizationDetailFragment();
            Navigation.findNavController(requireView()).navigate(action);
        });

        ImageView imageViewTeams = view.findViewById(R.id.imageViewTeams);
        imageViewTeams.setOnClickListener(clickView -> {
            NavDirections action = cHomeFragmentDirections.actionCHomeFragmentToCTeamFragment();
            Navigation.findNavController(requireView()).navigate(action);
        });

        ImageView LogFrames = view.findViewById(R.id.LogFrames);
        LogFrames.setOnClickListener(clickView ->{
            NavDirections action = cHomeFragmentDirections.actionCHomeFragmentToCLogFrameFragment();
            Navigation.findNavController(requireView()).navigate(action);
        });

        ImageView imageViewWorkplan = view.findViewById(R.id.imageViewWorkplan);
        imageViewWorkplan.setOnClickListener(clickView ->
                Toast.makeText(getActivity(), "Work Plan Clicked", Toast.LENGTH_SHORT).show());

        ImageView imageViewBudget = view.findViewById(R.id.imageViewBudget);
        imageViewBudget.setOnClickListener(clickView ->
                Toast.makeText(getActivity(), "Budget Clicked", Toast.LENGTH_SHORT).show());

        ImageView imageViewMonitoring = view.findViewById(R.id.imageViewMonitoring);
        imageViewMonitoring.setOnClickListener(clickView ->
                Toast.makeText(getActivity(), "Monitoring Clicked", Toast.LENGTH_SHORT).show());

        ImageView imageViewEvaluations = view.findViewById(R.id.imageViewEvaluations);
        imageViewEvaluations.setOnClickListener(clickView ->
                Toast.makeText(getActivity(), "Evaluation Clicked", Toast.LENGTH_SHORT).show());

        ImageView imageViewRAID = view.findViewById(R.id.imageViewRAID);
        imageViewRAID.setOnClickListener(clickView -> {
            /*FirebaseAuth.getInstance().signOut();
            NavDirections action = cHomeFragmentDirections.actionCHomeFragmentToCLoginFragment();
            Navigation.findNavController(requireView()).navigate(action);*/

            Toast.makeText(getActivity(), "RAID Clicked", Toast.LENGTH_SHORT).show();
        });
    }
}