package com.me.mseotsanyana.mande.PL.ui.fragments.logframe;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.me.mseotsanyana.mande.BLL.domain.logframe.cOutcomeDomain;
import com.me.mseotsanyana.mande.BLL.interactors.logframe.Impl.cOutcomeInterator;
import com.me.mseotsanyana.mande.PL.ui.adapters.logframe.cOutcomeAdapter;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;

/**
 * Created by mseotsanyana on 2016/12/04.
 */

public class cOutcomeFragment extends Fragment {

    private cOutcomeInterator outcomeHandler;
    private cOutcomeDomain outcomeDomain;

    private RecyclerView recyclerView;
    private cOutcomeAdapter outcomeAdapter;

    private int cardPosition = 0;
    private int level = 0;

    public static cOutcomeFragment newInstance(ArrayList<cTreeModel> outcomeModel) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("OUTCOMETREE", outcomeModel);
        cOutcomeFragment fragment = new cOutcomeFragment();
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
        // getting a action_list with all projects in a database
        outcomeHandler = new cOutcomeInterator(getActivity());

        ArrayList<cTreeModel> outcomeTreeData = getArguments().getParcelableArrayList("OUTCOMETREE");

        outcomeAdapter = new cOutcomeAdapter(getActivity(), outcomeTreeData, level);

    }

    /**
     * the onCreateView method is called when fragment should create its View object
     * hierarchy either dynamically or via XML Layout inflation.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Register the event to subscribe.
        //-cGlobalBus.getBus().register(this);
        return inflater.inflate(R.layout.outcome_fragment_recycleview, parent, false);
    }

    /**
     * this event is triggered soon after on CreateView(). onViewCreated is called if the
     * view is returned from onCreateView() is non-null. Any view setup should occur here. e.g.
     * view lookups and attaching view listeners.
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // instantiate and initialize the action_list
        recyclerView = (RecyclerView)view.findViewById(R.id.outcome_recycleview_id);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        // populate the action_list with data from database
        recyclerView.setAdapter(outcomeAdapter);

        recyclerView.setLayoutManager(llm);

        // initialise the floating action button (FAB)
        initFab(view);
    }

    // initialise the floating action button
    private void initFab(View view) {
        view.findViewById(R.id.outcome_fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                /*-addProjectFragment.setStyle(DialogFragment.STYLE_NORMAL,R.style.CustomDialog);
                addProjectFragment.setCancelable(false);
                //addProjectFragment.show(fragmentManager,"fragment_add_project");
                //Snackbar.make(getView(), "FAB Clicked", Snackbar.LENGTH_SHORT).show();

                addProjectFragment.setTargetFragment(cProjectFragment.this, 0);
                addProjectFragment.show(fragmentManager,"fragment_add_project");
                -*/
            }
        });
    }
}
