package com.me.mseotsanyana.mande.PL.ui.adapters.session;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.treeadapterlibrary.cNode;
import com.me.mseotsanyana.treeadapterlibrary.cTreeAdapter;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;
import com.me.mseotsanyana.treeadapterlibrary.cTreeViewHolder;

import java.util.List;

/**
 * Created by mseotsanyana on 2017/02/27.
 */

public class cPermissionAdapter extends cTreeAdapter {
    //private static final String TAG = cPermissionAdapter.class.getSimpleName();

    public static final int ROLE = 0;
    public static final int PERMISSION = 1;

    public cPermissionAdapter(Context context, List<cTreeModel> treeModels) {

        super(context, treeModels);

    }

    public RecyclerView.ViewHolder OnCreateTreeViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        switch (viewType) {
            case ROLE:
                view = inflater.inflate(R.layout.privilege_cardview, parent, false);
                viewHolder = new cRoleTreeViewHolder(view);
                break;
            case PERMISSION:
                view = inflater.inflate(R.layout.privilege_entity_cardview, parent, false);
                viewHolder = new cPermissionTreeViewHolder(view);
                break;
            default:
                viewHolder = null;
                break;
        }

        return viewHolder;
    }

    public void OnBindTreeViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        final cNode node = visibleNodes.get(position);

        final cTreeModel treeModel = (cTreeModel) node.getObj();

        if (treeModel != null) {
            switch (treeModel.getType()) {
                case ROLE:
                    //final cPermissionModel privilegeModel = (cPermissionModel) treeModel.getModelObject();

                    break;

                case PERMISSION:
                    //final cPermissionTreeModel permTreeModel = (cPermissionTreeModel)
                    //        treeModel.getModelObject();

            }
        }
    }

    public class cRoleTreeViewHolder extends cTreeViewHolder {


        private View treeView;

        public cRoleTreeViewHolder(final View treeViewHolder) {
            super(treeViewHolder);
            treeView = treeViewHolder;

        }

        public void setPaddingLeft(int paddingLeft) {
            treeView.setPadding(paddingLeft, 0, 0, 0);
        }

    }


    public class cPermissionTreeViewHolder extends cTreeViewHolder {
        private View treeView;

        public cPermissionTreeViewHolder(View treeViewHolder) {
            super(treeViewHolder);
            treeView = treeViewHolder;
        }

        public void setPaddingLeft(int paddingLeft) {
            treeView.setPadding(paddingLeft, 0, 0, 0);
        }
    }
}

//    static public class cQAAdapter extends BaseAdapter {
//
//        final int[] ICONS = new int[]{
//                R.string.fa_plus,
//                R.string.fa_delete
//        };
//
//        LayoutInflater mLayoutInflater;
//        List<cCustomActionItemText> mItems;
//        cCustomActionItemText item;
//
//        Context context;
//
//        public cQAAdapter(Context context) {
//            this.context = context;
//            mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//            mItems = new ArrayList<cCustomActionItemText>();
//
//            item = new cCustomActionItemText(context, "Add", ICONS[0]);
//            mItems.add(item);
//
//            item = new cCustomActionItemText(context, "Del", ICONS[1]);
//            mItems.add(item);
//        }
//
//        @Override
//        public int getCount() {
//            return mItems.size();
//        }
//
//        @Override
//        public Object getItem(int arg) {
//            return mItems.get(arg);
//        }
//
//        @Override
//        public long getItemId(int arg) {
//            return arg;
//        }
//
//        @Override
//        public View getView(int position, View arg1, ViewGroup viewGroup) {
//            View view = mLayoutInflater.inflate(R.layout.action_item_flexible, viewGroup, false);
//
//            cCustomActionItemText item = (cCustomActionItemText) getItem(position);
//
//            TextView image = (TextView) view.findViewById(R.id.image);
//            TextView text = (TextView) view.findViewById(R.id.title);
//
//            //image.setImageDrawable(item.getIcon());
//            text.setText(item.getTitle());
//
//            image.setTypeface(null, Typeface.NORMAL);
//            image.setTypeface(cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
//            image.setText(context.getResources().getString(item.getImage()));
//
//            return view;
//        }
//    }


                    // quick actions on roles
//                    PVH.textViewQuickActionIcon.setTypeface(null, Typeface.NORMAL);
//                    PVH.textViewQuickActionIcon.setTypeface(cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
//                    PVH.textViewQuickActionIcon.setText(context.getResources().getString(R.string.fa_ellipsis_h));
//                    PVH.textViewQuickActionIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
//                    PVH.textViewQuickActionIcon.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            // create the quick action view, passing the view anchor
//                            cCustomQuickAction quickAction = cCustomQuickAction.Builder(view);
//
//                            // set the adapter
//                            quickAction.setAdapter(new cQAAdapter(context));
//
//                            // set the number of columns ( setting -1 for auto )
//                            quickAction.setNumColumns(-1);
//                            quickAction.setOnClickListener(new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int position) {
//                                    switch (position) {
//                                        case 0:
//                                            permissionInterface.onAddPermissionEntities(node);
//                                            break;
//                                        case 1:
//                                            permissionInterface.onRemovePermissionEntities(node);
//                                            break;
//                                        default:
//                                            permissionInterface.onResponseMessage(R.string.privilege_filter_error,
//                                                    R.string.privilege_filter_error_msg);
//                                            break;
//                                    }
//
//                                    dialog.dismiss();
//                                }
//                            });
//
//                            // finally show the view
//                            quickAction.show();
//                        }
//                    });