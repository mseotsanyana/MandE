package com.me.mseotsanyana.mande.PL.ui.fragments.logframe;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.me.mseotsanyana.mande.BLL.interactors.logframe.Impl.cImpactInterator;
import com.me.mseotsanyana.mande.BLL.interactors.session.cOrganizationHandler;
import com.me.mseotsanyana.mande.PL.ui.adapters.logframe.cGoalAdapter;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;

/**
 * Created by mseotsanyana on 2016/12/04.
 */

public class cGoalFragment extends Fragment {
    private RecyclerView recyclerView;
    private cGoalAdapter goalAdapter;
    private ArrayList<cTreeModel> organizationTree;
    private ArrayList<cTreeModel> goalTree;
    private ArrayList<cTreeModel> goalTreeData;
    private cOrganizationHandler organizationHandler;
    private cImpactInterator goalHandler;

    int level = 0;

    public static cGoalFragment newInstance(ArrayList<cTreeModel> goalModel) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("GOALTREE", goalModel);
        cGoalFragment fragment = new cGoalFragment();
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
        //organizationHandler = new cOrganizationHandler(getActivity());
        //goalHandler = new cImpactInterator(getActivity());

        //organizationTree = organizationHandler.getOrganizationTree();
        //goalTree = goalHandler.getGoalTree();

        ArrayList<cTreeModel> goalTreeData = getArguments().getParcelableArrayList("GOALTREE");

        //goalTreeData = mergeOrganizationGoal(action_list, goalTree);

        goalAdapter = new cGoalAdapter(getActivity(), goalTreeData, level);
    }

    /**
     * the onCreateView method is called when fragment should create its View object
     * hierarchy either dynamically or via XML Layout inflation.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Register the event to subscribe.
        //-cGlobalBus.getBus().register(this);
        return inflater.inflate(R.layout.goal_list_fragment, parent, false);
    }

    /**
     * this event is triggered soon after on CreateView(). onViewCreated is called if the
     * view is returned from onCreateView() is non-null. Any view setup should occur here. e.g.
     * view lookups and attaching view listeners.
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // instantiate and initialize the action_list
        recyclerView = (RecyclerView)view.findViewById(R.id.goal_cardview_list);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        // populate the action_list with data from database
        recyclerView.setAdapter(goalAdapter);

        recyclerView.setLayoutManager(llm);

        // initialise the floating action button (FAB)
        initFab(view);
    }

    // initialise the floating action button
    private void initFab(View view) {
        view.findViewById(R.id.goal_fab).setOnClickListener(new View.OnClickListener() {
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


/*
    private void initDataSet(){
        cTreeModel treeData;
        treeData = new cTreeModel(0,-1,0, new cOrganizationDomain("Organization 1"));
        goalTreeData.add(treeData);
        treeData = new cTreeModel(1,-1,0, new cOrganizationDomain("Organization 1"));
        goalTreeData.add(treeData);
        treeData = new cTreeModel(2,0, 1, new cGoalDomain("Goal 1"));
        goalTreeData.add(treeData);
        treeData = new cTreeModel(3,1, 1, new cGoalDomain("Goal 3"));
        goalTreeData.add(treeData);
        treeData = new cTreeModel(4,0, 1, new cGoalDomain( "Goal 2"));
        goalTreeData.add(treeData);
    }

    List<cTreeModel> getTree(){
        initDataSet();
        return goalTreeData;
    }
*/
}
