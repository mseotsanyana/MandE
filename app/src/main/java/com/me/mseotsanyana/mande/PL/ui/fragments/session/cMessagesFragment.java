package com.me.mseotsanyana.mande.PL.ui.fragments.session;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.me.mseotsanyana.mande.BLL.model.session.cOrganizationModel;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by mseotsanyana on 2016/12/04.
 */

public class cMessagesFragment extends Fragment {
    private Toolbar toolbar;
    private AppCompatActivity activity;
    private LinearLayout notificationProgressBar;
    //private cNotificationAdapter notificationAdapter;

    private TextView notificationName;

    public cMessagesFragment(){}

    public static cMessagesFragment newInstance() {
        return new cMessagesFragment();
    }

    public static cMessagesFragment newInstance(ArrayList<cOrganizationModel> domainList) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("ORGANIZATION", domainList);

        cMessagesFragment fragment = new cMessagesFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    /*
    * this event fires 1st, before creation of fragment or any views
    * the onAttach method is called when the Fragment instance is
    * associated with an Activity and this does not mean the activity
    * is fully initialized.
    */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    /**
     * this method is fired 2nd, before views are created for the fragment,
     * the onCreate method is called when the fragment instance is being created,
     * or re-created use onCreate for any standard setup that does not require
     * the activity to be fully created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * the onCreateView method is called when fragment should create its View object
     * hierarchy either dynamically or via XML Layout inflation.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.session_notifications_fragment, parent, false);
    }

    /**
     * this event is triggered soon after on CreateView(). onViewCreated is called if the
     * view is returned from onCreateView() is non-null. Any view setup should occur here. e.g.
     * view lookups and attaching view listeners.
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        /* initialise data structures */
        initDataStructures();

        /* initialize appBar Layout */
        initAppBarLayout(view);

        /* initialise recycler view */
        initRecyclerView(view);

        /* initialize progress bar */
        initProgressBarView(view);

        /* initialise draggable FAB */
        initDraggableFAB(view);

        /* show the back arrow button */
        activity.setSupportActionBar(toolbar);
        Objects.requireNonNull(activity.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(activity.getSupportActionBar()).setDisplayShowHomeEnabled(true);
    }

    private void initDataStructures() {
        /* contains a tree of outcomes */
        List<cTreeModel> notificationTreeModels = new ArrayList<>();

        /*notificationPresenter = new cNotificationPresenterImpl(
                cThreadExecutorImpl.getInstance(),
                cMainThreadImpl.getInstance(),
                this,
                new cSessionManagerImpl(getContext()),
                new cOutcomeRepositoryImpl(getContext()),
                logFrameID);*/

        // setup recycler view adapter
        /*outcomeAdapter = new cOutcomeAdapter(getActivity(), this,
                this, outcomeTreeModels, -1);*/

        activity = ((AppCompatActivity) getActivity());
    }

    private void initAppBarLayout(View view){
        /* initialize the toolbar */
        toolbar = view.findViewById(R.id.toolbar);
        TextView notificationCaption = view.findViewById(R.id.title);
        notificationName = view.findViewById(R.id.subtitle);
        notificationCaption.setText(R.string.notification_name_caption);
        CollapsingToolbarLayout collapsingToolbarLayout =
                view.findViewById(R.id.collapsingToolbarLayout);
        collapsingToolbarLayout.setContentScrimColor(Color.WHITE);
        collapsingToolbarLayout.setTitle("List of Notifications");
    }

    private void initRecyclerView(View view) {
        /*RecyclerView notificationRecyclerView = view.findViewById(R.id.notificationRecyclerView);
        notificationRecyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        notificationRecyclerView.setAdapter(notificationAdapter);
        notificationRecyclerView.setLayoutManager(llm);*/
    }

    private void initProgressBarView(View view) {
        notificationProgressBar = view.findViewById(R.id.notificationProgressBar);
    }

    // initialise the floating action button
    private void initDraggableFAB(View view) {
        view.findViewById(R.id.notificationDraggableFAB).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
