package com.me.mseotsanyana.mande.PPMER.PL;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.me.mseotsanyana.mande.COM.cEvent;
import com.me.mseotsanyana.mande.COM.cGlobalBus;
import com.me.mseotsanyana.mande.PPMER.BLL.cProjectDomain;
import com.me.mseotsanyana.mande.PPMER.BLL.cProjectHandler;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

/**
 * Created by mseotsanyana on 2016/07/22.
 */
public class cProjectFragment extends Fragment implements cAddProjectFragment.OnAddProjectListener,
        cEditProjectFragment.OnEditProjectListener
{
    cProjectHandler projectHandler;
    cProjectDomain projectDomain;
    cAddProjectFragment addProjectFragment;
    cEditProjectFragment editProjectFragment;

    RecyclerView recyclerView;
    cProjectAdapter projectAdapter;

    private int cardPosition = 0;

    int level = 0;

    public static cProjectFragment newInstance(ArrayList<cTreeModel> projectModel) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("PROJECTTREE", projectModel);
        cProjectFragment fragment = new cProjectFragment();
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
        /*
        projectHandler = new cProjectHandler(getActivity());

        projectAdapter = new cProjectAdapter_old(getActivity(), projectHandler.getProjectList(),
                cProjectFragment.this);

        addProjectFragment = new cAddProjectFragment();
        editProjectFragment = new cEditProjectFragment();
*/
        ArrayList<cTreeModel> projectTreeData = getArguments().getParcelableArrayList("PROJECTTREE");

        projectAdapter = new cProjectAdapter(getActivity(), projectTreeData, level);

    }

    /**
     * the onCreateView method is called when fragment should create its View object
     * hierarchy either dynamically or via XML Layout inflation.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Register the event to subscribe.
        cGlobalBus.getBus().register(this);
        return inflater.inflate(R.layout.fragment_project_list, parent, false);
    }

    /**
     * this event is triggered soon after on CreateView(). onViewCreated is called if the
     * view is returned from onCreateView() is non-null. Any view setup should occur here. e.g.
     * view lookups and attaching view listeners.
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // instantiate and initialize the action_list
        recyclerView = (RecyclerView)view.findViewById(R.id.card_list);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        // populate the action_list with data from database
        recyclerView.setAdapter(projectAdapter);

        recyclerView.setLayoutManager(llm);

        // initialise the floating action button (FAB)
        initFab(view);
    }

    /**
     * This method is called after the parent Activity's on Create() method has completed.
     * Accessing the view hierarchy of the parent activity must be done in the onActivityCreated
     * at this point, it is safe to search for activity View objects by their ID, for example.
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        cGlobalBus.getBus().unregister(this);
    }

    // initialise the floating action button
    private void initFab(View view) {
        view.findViewById(R.id.project_fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                addProjectFragment.setStyle(DialogFragment.STYLE_NORMAL,R.style.CustomDialog);
                addProjectFragment.setCancelable(false);
                //addProjectFragment.show(fragmentManager,"fragment_add_project");
                //Snackbar.make(getView(), "FAB Clicked", Snackbar.LENGTH_SHORT).show();

                addProjectFragment.setTargetFragment(cProjectFragment.this, 0);
                addProjectFragment.show(fragmentManager,"fragment_add_project");
            }
        });
    }

    @Subscribe
    public void getMessage(cEvent.cActivityFragmentMessage activityFragmentMessage) {
        //Write code to perform action after event is received.
        quickActionMenu(activityFragmentMessage.getMessage());
    }

    // method called when the imageview is clicked on the cardview
    public void onclickImageView(View view, int position){
        // the position of the cardview on the recycleview
        cardPosition = position;
        cEvent.cProjectActivityMessage projectActivityMessageEvent =
                new cEvent.cProjectActivityMessage(view);

        cGlobalBus.getBus().post(projectActivityMessageEvent);

        //Snackbar.make(getView(), "ImageView Clicked", Snackbar.LENGTH_SHORT).show();
    }

    // menu for quick actions
    public void quickActionMenu(final int position){
        switch (position){
            case 0: // delete
                // get the project details at a card position of recycleview
                projectDomain = projectAdapter.getItem(cardPosition);

                if (projectDomain != null)
                {
                    AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
                    ad.setTitle("Delete Project");
                    ad.setMessage("Do you want to delete the project: '"+projectDomain.getProjectName()+"' ?");

                    ad.setPositiveButton(
                            "Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    // delete from database
                                    boolean result = projectHandler.deleteProject(projectDomain);
                                    if (result)
                                    {
                                        // remove from a action_list in the adapter class
                                        //-projectAdapter.removeItem(cardPosition);
                                        // animate the deletion from the recycleview
                                        projectAdapter.notifyItemRemoved(cardPosition);
                                    }else
                                        Toast.makeText(getActivity(),
                                                "Unable to Delete Project",
                                                Toast.LENGTH_SHORT).show();
                                }
                            }
                    );

                    ad.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    ad.show();
                }
                break;
            case 1: // edit
                // get the project details at a card position of recycleview
                projectDomain = projectAdapter.getItem(cardPosition);

                if (projectDomain != null) {

                    FragmentManager fragmentManager = getFragmentManager();
                    editProjectFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
                    editProjectFragment.setCancelable(false);
                    editProjectFragment.setTargetFragment(cProjectFragment.this, 0);
                    editProjectFragment.show(fragmentManager, "fragment_add_project");
                }

                break;
            case 2:
                Toast.makeText(getActivity(), "SYNC selected",
                        Toast.LENGTH_SHORT).show();
                break;
            case 3:
                Toast.makeText(getActivity(), "DETAILS selected",
                        Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onAddProject(cProjectDomain projectDomain){
        //- projectAdapter.addItem(projectDomain);
        projectAdapter.notifyDataSetChanged();
        //Toast.makeText(getActivity(), "DETAILS selected",
        //        Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEditProject(cProjectDomain projectDomain){
        //- projectAdapter.updateItem(projectDomain, cardPosition);
        projectAdapter.notifyDataSetChanged();
        //Toast.makeText(getActivity(), "DETAILS selected",
        //        Toast.LENGTH_SHORT).show();
    }

    public cProjectDomain getProjectDomain() {
        return projectDomain;
    }
}


//===========================================================================================
/*


                projectHandler.deleteProject(projectDomain);

                //String project = projectDomain.getProjectName();

                //RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(0);
                //int selectedItemPosition = recyclerView.getChildLayoutPosition(recycleviewImage);
                //TextView name = (TextView) viewHolder.itemView.findViewById(R.id.valName);
                //String selectedName = (String) name.getText();
                //Toast.makeText(getActivity(),
                //        "DELETE item selected "+project+" "+id, Toast.LENGTH_SHORT)
                //        .show();


    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenuInfo menuInfo)
    {
        if(view.getId()==R.id.project_list_view_id)
        {
            // inflating the context menu
            MenuInflater inflater = getActivity().getMenuInflater();
            inflater.inflate(R.menu.context_menu, menu);

            // copy cursor into array
            ArrayList<String> projects = new ArrayList<String>();
            projectListCursor.moveToFirst();
            int index = 0;
            do
            {
                projects.add(index, projectListCursor.getString(projectListCursor.getColumnIndex("projectName")));
                index = index + 1;
            }while (projectListCursor.moveToNext());

            // populate the title of the context menu with project name
            String[] project_list = projects.toArray(new String[projects.size()]);
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
            menu.setHeaderTitle(project_list[info.position]);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.Add:
                Toast.makeText(getActivity().getApplicationContext(),"Adding",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.Edit:
                Toast.makeText(getActivity().getApplicationContext(),"Editing",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.Delete:
                Toast.makeText(getActivity().getApplicationContext(),"Deleting",Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
    */



//addProjectFragment.setArguments(bundle);

//listView.setSelection(0);
//listView.setItemChecked(0,true);
// set a listener on the clicks on the action_list items
        /*listView.setOnItemClickListener(
                new OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
                    {
                        Cursor cursor = (Cursor) adapterView.getItemAtPosition(i);
                        if (cursor != null)
                        {
                            int id = cursor.getInt(cursor.getColumnIndex("_id"));
                            String name = cursor.getString(cursor.getColumnIndex("projectName"));
                            String description = cursor.getString(cursor.getColumnIndex("projectDescription"));

                            projectDomain = new cProjectDomain(id, name, description);
                            bundle.putParcelable("selectedProject", projectDomain);

                            //Toast.makeText(getActivity(),projectDomain.getProjectID()+"",Toast.LENGTH_SHORT).show();

                        }
                    }
                });


        addProjectFragment.setArguments(bundle);

        // make the action_list beware of the menu
        //registerForContextMenu(listView);

        // instantiate and initialize the button
        addButton = (Button) view.findViewById(R.id.button_add_id);
        // add button listener
        addButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                bundle.putString("title", "Add Project");
                bundle.putInt("op", 0);

                FragmentManager fragmentManager = getFragmentManager();
                addProjectFragment.setStyle(DialogFragment.STYLE_NORMAL,R.style.CustomDialog);
                addProjectFragment.setCancelable(false);
                addProjectFragment.show(fragmentManager,"fragment_add_project");
            }
        });

        editButton = (Button) view.findViewById(R.id.button_edit_id);
        editButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                bundle.putString("title", "Update Project");
                bundle.putInt("op", 1);

                FragmentManager fragmentManager = getFragmentManager();
                addProjectFragment.setStyle(DialogFragment.STYLE_NORMAL,R.style.CustomDialog);
                addProjectFragment.setCancelable(false);
                addProjectFragment.show(fragmentManager,"edit_project_fragment");
            }
        });

        deleteButton = (Button) view.findViewById(R.id.button_delete_id);
        deleteButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                bundle.putString("title", "Delete Project");
                bundle.putInt("op", 2);

                if (projectDomain != null)
                {
                    AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
                    ad.setTitle("Delete Project");
                    ad.setMessage("Are you sure you want to the project: '"+projectDomain.getProjectName()+"' ?");

                    ad.setPositiveButton(
                            "Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    projectHandler = new cProjectHandler(getActivity().getApplicationContext());
                                    boolean result = projectHandler.deleteProject(projectDomain);
                                    if (result)
                                    {
                                        updateListView();
                                    }else
                                        Toast.makeText(getActivity(),
                                                "Unable to Delete Project",
                                                Toast.LENGTH_SHORT).show();
                                }
                            }
                    );

                    ad.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });

                    ad.show();

                }
            }
        });*/