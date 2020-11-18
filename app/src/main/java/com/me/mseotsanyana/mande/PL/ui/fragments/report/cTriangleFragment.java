package com.me.mseotsanyana.mande.PL.ui.fragments.report;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.me.mseotsanyana.mande.PL.ui.adapters.logframe.cLogFrameAdapter;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.quickactionlibrary.cCustomActionItemText;
import com.me.mseotsanyana.quickactionlibrary.cCustomQuickAction;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mseotsanyana on 2017/02/24.
 */

public class cTriangleFragment extends Fragment {
    //private cTriangleDomain triangleDomain;

    private RecyclerView recyclerView;
    private cLogFrameAdapter triangleAdapter;

    private int cardPosition = 0;
    private int level = 0;

    public static cTriangleFragment newInstance(ArrayList<cTreeModel> triangleModel) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("TRIANGLE", triangleModel);
        cTriangleFragment fragment = new cTriangleFragment();
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
        //triangleHandler = new cTriangleHandler();

        ArrayList<cTreeModel> triangleTreeData = getArguments().getParcelableArrayList("TRIANGLE");

//        triangleAdapter = new cLogFrameAdapter(getActivity(), null,triangleTreeData,null);

    }

    /**
     * the onCreateView method is called when fragment should create its View object
     * hierarchy either dynamically or via XML Layout inflation.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Register the event to subscribe.
        //-cGlobalBus.getBus().register(this);
        return inflater.inflate(R.layout.fragment_triangle_list, parent, false);
    }

    /**
     * this event is triggered soon after on CreateView(). onViewCreated is called if the
     * view is returned from onCreateView() is non-null. Any view setup should occur here. e.g.
     * view lookups and attaching view listeners.
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // instantiate and initialize the action_list
        recyclerView = (RecyclerView)view.findViewById(R.id.triangle_card_list);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        // populate the action_list with data from database
        recyclerView.setAdapter(triangleAdapter);

        recyclerView.setLayoutManager(llm);

        // initialise the floating action button (FAB)
        initFab(view);
    }

    // initialise the floating action button
    private void initFab(View view) {
        view.findViewById(R.id.triangle_fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();

                // create the quick action view, passing the view anchor
                cCustomQuickAction qa = cCustomQuickAction.Builder( v );

                // set the adapter
                qa.setAdapter(new cCustomAdapter(getActivity()) );

                // set the number of columns ( setting -1 for auto )
                qa.setNumColumns(2);
                qa.setOnClickListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick( DialogInterface dialog, int position) {
                        switch(position){
                            case 0:
                                Toast.makeText(getActivity(), "Selected item: " + position, Toast.LENGTH_SHORT ).show();
                                break;
                            case 1:
                                Toast.makeText(getActivity(), "Selected item: " + position, Toast.LENGTH_SHORT ).show();
                                break;
                            case 2:
                                Toast.makeText(getActivity(), "Selected item: " + position, Toast.LENGTH_SHORT ).show();
                                break;
                            case 3:
                                Toast.makeText(getActivity(), "Selected item: " + position, Toast.LENGTH_SHORT ).show();
                                break;
                            default:
                                Toast.makeText(getActivity(), "Error! ", Toast.LENGTH_SHORT ).show();
                                break;
                        }

                        dialog.dismiss();

                    }
                } );

                // finally show the view
                qa.show();
            }
        });
    }
/*

    private void initDataSet(){
        cTreeModel treeData;
        treeData = new cTreeModel(0,-1,0, new cOverallAim("To improve the lives of children who use the centre To improve the lives of children who use the centre To improve the lives of children who use the centre"));
        treeAdapter.add(treeData);
        treeData = new cTreeModel(1,0, 1, new cSpecificAim("To improve the parenting skills of the parents using the centre"));
        treeAdapter.add(treeData);
        treeData = new cTreeModel(2,0, 1, new cSpecificAim( "To increase children's self-esteem"));
        treeAdapter.add(treeData);
        treeData = new cTreeModel(3,0, 1, new cSpecificAim("To help parents give each other support"));
        treeAdapter.add(treeData);
        treeData = new cTreeModel(4,1, 2, new cObjective( "Provide workshops, information and advice on parenting skills"));
        treeAdapter.add(treeData);
        treeData = new cTreeModel(5,1, 2, new cObjective("Run a drop-in centre"));
        treeAdapter.add(treeData);
        treeData = new cTreeModel(6,1, 2, new cObjective("Organise outings for parents"));
        treeAdapter.add(treeData);
        treeData = new cTreeModel(7,2, 2, new cObjective("Provide opportunities for play, drama and dancing"));
        treeAdapter.add(treeData);
        treeData = new cTreeModel(8,2, 2, new cObjective("Run a drop-in centre with a play area"));
        treeAdapter.add(treeData);
        treeData = new cTreeModel(9,3, 2, new cObjective("Provide workshops, information and advice on parenting skills"));
        treeAdapter.add(treeData);
        treeData = new cTreeModel(10,3, 2, new cObjective("Organise outings for parents"));
        treeAdapter.add(treeData);
    }

    List<cTreeModel> getTree(){
        initDataSet();
        return treeAdapter;
    }
*/

    /**
     * Custom Adapter just for custom values
     * @author modified by motlatsi
     *
     */
    static class cCustomAdapter extends BaseAdapter {

        static final int[] ICONS = new int[]{
                R.drawable.ic_arrow_forward_black_24dp,
                R.drawable.ic_arrow_downward_black_24dp,
                R.drawable.ic_arrow_upward_black_24dp,
                R.drawable.ic_arrow_back_black_24dp
        };

        LayoutInflater mLayoutInflater;
        List<cCustomActionItemText> mItems;
        cCustomActionItemText item;


        public cCustomAdapter( Context context ) {
            mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            mItems = new ArrayList<cCustomActionItemText>();
/*
            item = new cCustomActionItemText(context, "goal", "ICONS[0]");
            mItems.add(item);

            item = new cCustomActionItemText(context, "need", "ICONS[1]");
            mItems.add(item);

            item = new cCustomActionItemText(context, "aim", "ICONS[2]");
            mItems.add(item);

            item = new cCustomActionItemText(context, "task", "ICONS[3]");
            mItems.add(item);
            */
            /*
            //int total = (int) (4 + (Math.random() * 30));
            int total = 4;

            for (int i = 0; i < total; i++) {
                //cCustomActionItemText item = new cCustomActionItemText(context, "Title " + i, ICONS[(int) (Math.random() * ICONS.length)]);
                cCustomActionItemText item = new cCustomActionItemText(context, "Title " + i, ICONS[i]);
                mItems.add(item);
            }*/
        }

        @Override
        public int getCount() {
            return mItems.size();
        }

        @Override
        public Object getItem( int arg0 ) {
            return mItems.get( arg0 );
        }

        @Override
        public long getItemId( int arg0 ) {
            return arg0;
        }

        @Override
        public View getView( int position, View arg1, ViewGroup viewGroup) {
            View view = mLayoutInflater.inflate(R.layout.action_item_flexible, viewGroup, false);

            cCustomActionItemText item = (cCustomActionItemText) getItem( position );

            ImageView image = (ImageView) view.findViewById( R.id.image );
            TextView text = (TextView) view.findViewById( R.id.title );

            image.setImageDrawable( item.getIcon() );
            text.setText( item.getTitle() );

            return view;
        }
    };
}
