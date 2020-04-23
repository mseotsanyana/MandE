package com.me.mseotsanyana.mande.PL.ui.adapters.logframe;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Layout;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.me.mseotsanyana.bmblibrary.BoomButtons.OnBMClickListener;
import com.me.mseotsanyana.bmblibrary.BoomButtons.cTextOutsideCircleButton;
import com.me.mseotsanyana.bmblibrary.cBoomMenuButton;
import com.me.mseotsanyana.bmblibrary.cUtil;
import com.me.mseotsanyana.expandablelayoutlibrary.cExpandableLayout;
import com.me.mseotsanyana.mande.DAL.model.logframe.cLogFrameModel;
import com.me.mseotsanyana.mande.PL.presenters.base.cBuilderManager;
import com.me.mseotsanyana.mande.PL.presenters.logframe.iLogFramePresenter;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.UTIL.TextDrawable;
import com.me.mseotsanyana.mande.UTIL.cFontManager;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;
import com.me.mseotsanyana.treeadapterlibrary.cNode;
import com.me.mseotsanyana.treeadapterlibrary.cTreeAdapter;
import com.me.mseotsanyana.treeadapterlibrary.cTreeViewHolder;


import java.util.List;

/**
 * Created by mseotsanyana on 2017/02/27.
 */

public class cLogFrameAdapter extends cTreeAdapter {
    private static String TAG = cLogFrameAdapter.class.getSimpleName();

    public static final int PARENT_LOGFRAME = 0;
    public static final int CHILD_LOGFRAME = 1;

    public final iLogFramePresenter.View logframePresenterView;


    final String[] bmb_caption = {
            "Impacts",
            "Outcomes",
            "Outputs",
            "Activities",
            "Resources",
            "Questions",
            "Indicators",
            "Risks",
            "Evaluations",
            "Monitoring",
            "Work Plans",
            "Budgets"
    };

    int[] bmb_imageid = {
            R.drawable.dashboard_goal,
            R.drawable.dashboard_outcome,
            R.drawable.dashboard_output,
            R.drawable.dashboard_activity,
            R.drawable.dashboard_resource,
            R.drawable.dashboard_question,
            R.drawable.dashboard_indicator,
            R.drawable.dashboard_risk,
            R.drawable.dashboard_evaluation,
            R.drawable.dashboard_monitoring,
            R.drawable.dashboard_workplan,
            R.drawable.dashboard_budget
            //R.drawable.dashboard_movs,
            //R.drawable.dashboard_criterion,
            //R.drawable.dashboard_category,
            //R.drawable.dashboard_evaluating,
            //R.drawable.dashboard_report,
            //R.drawable.dashboard_notification
    };


    public cLogFrameAdapter(Context context, iLogFramePresenter.View logframePresenterView,
                            List<cTreeModel> data, int expLevel) {
        super(context, data, expLevel);

        this.logframePresenterView = logframePresenterView;
    }

    public RecyclerView.ViewHolder OnCreateTreeViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        switch (viewType) {
            case PARENT_LOGFRAME:
                view = inflater.inflate(R.layout.logframe_parent_cardview, parent, false);
                viewHolder = new cParentLogFrameViewHolder(view);
                break;
            case CHILD_LOGFRAME:
                view = inflater.inflate(R.layout.logframe_child_cardview, parent, false);
                viewHolder = new cChildLogFrameViewHolder(view);
                break;
            default:
                viewHolder = null;
                break;
        }

        return viewHolder;
    }

    public void OnBindTreeViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        cNode node = visibleNodes.get(position);
        cTreeModel obj = (cTreeModel) node.getObj();

        if (obj != null) {
            switch (obj.getType()) {
                case PARENT_LOGFRAME:
                    cLogFrameModel parentLogFrameModel = (cLogFrameModel) obj.getModelObject();
                    cParentLogFrameViewHolder PVH = ((cParentLogFrameViewHolder) viewHolder);

                    PVH.textViewName.setText(parentLogFrameModel.getName());
                    PVH.textViewDescription.setText(parentLogFrameModel.getDescription());
                    //PVH.textViewCount.setText("USERS: " + node.numberOfChildren());

                    PVH.bmb.clearBuilders();
                    for (int i = 0; i < PVH.bmb.getPiecePlaceEnum().pieceNumber(); i++) {
                        cTextOutsideCircleButton.Builder builder = new cTextOutsideCircleButton.Builder()
                                .isRound(false)
                                .shadowCornerRadius(cUtil.dp2px(20))
                                .buttonCornerRadius(cUtil.dp2px(20))
                                .normalColor(Color.LTGRAY)
                                .pieceColor(Color.parseColor("#228B22"))//FIXME
                                .normalImageRes(bmb_imageid[i])
                                .normalText(bmb_caption[i])
                                .listener(new OnBMClickListener() {
                                    @Override
                                    public void onBoomButtonClick(int index) {
                                        /* when the boom-button is clicked. */
                                        logframePresenterView.onClickBoomMenu(index);
                                    }
                                });
                        PVH.bmb.addBuilder(builder);
                    }

                    PVH.bmb.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            PVH.bmb.boom();
                        }
                    });

                    PVH.setPaddingLeft(40 * node.getLevel());

                    // the collapse and expansion of the parent logframe
                    if (node.isLeaf()) {
                        PVH.textViewExpandLogFrameIcon.setVisibility(View.INVISIBLE);
                    } else {
                        PVH.textViewExpandLogFrameIcon.setVisibility(View.VISIBLE);
                        if (node.isExpand()) {
                            PVH.textViewExpandLogFrameIcon.setTypeface(null, Typeface.NORMAL);
                            PVH.textViewExpandLogFrameIcon.setTypeface(
                                    cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                            PVH.textViewExpandLogFrameIcon.setText(
                                    context.getResources().getString(R.string.fa_minus));
                        } else {
                            PVH.textViewExpandLogFrameIcon.setTypeface(null, Typeface.NORMAL);
                            PVH.textViewExpandLogFrameIcon.setTypeface(
                                    cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                            PVH.textViewExpandLogFrameIcon.setText(
                                    context.getResources().getString(R.string.fa_plus));
                        }
                    }

                    PVH.textViewExpandLogFrameIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            expandOrCollapse(position);
                        }
                    });

                    // collapse and expansion of the details of the role
                    PVH.textViewDetailIcon.setTypeface(null, Typeface.NORMAL);
                    PVH.textViewDetailIcon.setTypeface(cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    PVH.textViewDetailIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
                    PVH.textViewDetailIcon.setText(context.getResources().getString(R.string.fa_angle_down));
                    PVH.textViewDetailIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (!(PVH.expandableLayout.isExpanded())) {
                                PVH.textViewDetailIcon.setText(context.getResources().getString(R.string.fa_angle_up));
                            } else {
                                PVH.textViewDetailIcon.setText(context.getResources().getString(R.string.fa_angle_down));
                            }

                            PVH.expandableLayout.toggle();
                        }
                    });

                    /** icon for saving updated record **/
                    PVH.textViewUpdateIcon.setTypeface(null, Typeface.NORMAL);
                    PVH.textViewUpdateIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    PVH.textViewUpdateIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
                    PVH.textViewUpdateIcon.setText(context.getResources().getString(R.string.fa_update));
                    PVH.textViewUpdateIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            logframePresenterView.onClickUpdateLogframe(parentLogFrameModel, position);

/*
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                            // setting icon to dialog
                            TextDrawable faIcon = new TextDrawable(context);
                            faIcon.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                            faIcon.setTextAlign(Layout.Alignment.ALIGN_CENTER);
                            faIcon.setTypeface(cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                            faIcon.setText(context.getResources().getText(R.string.fa_save));
                            faIcon.setTextColor(context.getColor(R.color.colorAccent));
                            alertDialogBuilder.setIcon(faIcon);

                            // set title
                            alertDialogBuilder.setTitle("Save Role.");
                            // set dialog message
                            alertDialogBuilder
                                    .setMessage("Do you want to SAVE role: " +
                                            PVH.textViewName.getText() + " ?")
                                    .setCancelable(false)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // update the permissions in the database

                                            //EVH.createPermissions();
                                            dialog.dismiss();
                                            //notifyItemChanged(position);
                                        }
                                    })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // if this button is clicked, just close
                                            dialog.cancel();
                                        }
                                    });

                            // create alert dialog
                            AlertDialog alertDialog = alertDialogBuilder.create();

                            // show it
                            alertDialog.show();*/
                        }
                    });

                    /** icon for deleting a record **/
                    PVH.textViewDeleteIcon.setTypeface(null, Typeface.NORMAL);
                    PVH.textViewDeleteIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    PVH.textViewDeleteIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
                    PVH.textViewDeleteIcon.setText(context.getResources().getString(R.string.fa_delete));
                    PVH.textViewDeleteIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            logframePresenterView.onClickDeleteLogframe(
                                    parentLogFrameModel.getLogFrameID());
                        }
                    });

                    /** icon for syncing a record **/
                    PVH.textViewSyncIcon.setTypeface(null, Typeface.NORMAL);
                    PVH.textViewSyncIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    PVH.textViewSyncIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
                    PVH.textViewSyncIcon.setText(context.getResources().getString(R.string.fa_sync));
                    PVH.textViewSyncIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                            // setting icon to dialog
                            TextDrawable faIcon = new TextDrawable(context);
                            faIcon.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                            faIcon.setTextAlign(Layout.Alignment.ALIGN_CENTER);
                            faIcon.setTypeface(cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                            faIcon.setText(context.getResources().getText(R.string.fa_sync));
                            faIcon.setTextColor(context.getColor(R.color.colorAccent));
                            alertDialogBuilder.setIcon(faIcon);

                            // set title
                            alertDialogBuilder.setTitle("Sync Role.");
                            // set dialog message
                            alertDialogBuilder
                                    .setMessage("Do you want to SYNCHRONISE role: " +
                                            PVH.textViewName.getText() + " ?")
                                    .setCancelable(false)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // update the permissions in the database
                                            //EVH.createPermissions();
                                            dialog.dismiss();
                                            //notifyItemChanged(position);
                                        }
                                    })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // if this button is clicked, just close
                                            dialog.cancel();
                                        }
                                    });

                            // create alert dialog
                            AlertDialog alertDialog = alertDialogBuilder.create();

                            // show it
                            alertDialog.show();
                        }
                    });

                    /** icon for creating a record **/
                    PVH.textViewCreateIcon.setTypeface(null, Typeface.NORMAL);
                    PVH.textViewCreateIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    PVH.textViewCreateIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
                    PVH.textViewCreateIcon.setText(context.getResources().getString(R.string.fa_create));
                    PVH.textViewCreateIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                            // setting icon to dialog
                            TextDrawable faIcon = new TextDrawable(context);
                            faIcon.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                            faIcon.setTextAlign(Layout.Alignment.ALIGN_CENTER);
                            faIcon.setTypeface(cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                            faIcon.setText(context.getResources().getText(R.string.fa_create));
                            faIcon.setTextColor(context.getColor(R.color.colorAccent));
                            alertDialogBuilder.setIcon(faIcon);

                            // set title
                            alertDialogBuilder.setTitle("Create LogFrame.");
                            // set dialog message
                            alertDialogBuilder
                                    .setMessage("Do you want to CREATE logframe: " +
                                            PVH.textViewName.getText() + " ?")
                                    .setCancelable(false)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // update the permissions in the database
                                            //EVH.createPermissions();
                                            dialog.dismiss();
                                            //notifyItemChanged(position);
                                        }
                                    })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // if this button is clicked, just close
                                            dialog.cancel();
                                        }
                                    });

                            // create alert dialog
                            AlertDialog alertDialog = alertDialogBuilder.create();

                            // show it
                            alertDialog.show();
                        }
                    });

                    break;

                case CHILD_LOGFRAME:
                    cLogFrameModel childLogFrameModel = (cLogFrameModel) obj.getModelObject();
                    cChildLogFrameViewHolder CVH = ((cChildLogFrameViewHolder) viewHolder);
                    CVH.setPaddingLeft(20 * node.getLevel());

                    CVH.textViewName.setText(childLogFrameModel.getName());
                    CVH.textViewDescription.setText(childLogFrameModel.getDescription());


                    CVH.bmb.clearBuilders();
                    for (int i = 0; i < CVH.bmb.getPiecePlaceEnum().pieceNumber(); i++)
                        CVH.bmb.addBuilder(cBuilderManager.getSimpleCircleButtonBuilder());

                    CVH.bmb.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //cBoomMenuButton bmb = (cBoomMenuButton)view.findViewById(R.id.bmb);
                            CVH.bmb.boom();
                        }
                    });

                    // collapse and expansion of the details of the role
                    CVH.textViewDetailIcon.setTypeface(null, Typeface.NORMAL);
                    CVH.textViewDetailIcon.setTypeface(cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    CVH.textViewDetailIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
                    CVH.textViewDetailIcon.setText(context.getResources().getString(R.string.fa_angle_down));
                    CVH.textViewDetailIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (!(CVH.expandableLayout.isExpanded())) {
                                CVH.textViewDetailIcon.setText(context.getResources().getString(R.string.fa_angle_up));
                            } else {
                                CVH.textViewDetailIcon.setText(context.getResources().getString(R.string.fa_angle_down));
                            }

                            CVH.expandableLayout.toggle();
                        }
                    });

                    /** icon for saving updated record **/
                    CVH.textViewUpdateIcon.setTypeface(null, Typeface.NORMAL);
                    CVH.textViewUpdateIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    CVH.textViewUpdateIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
                    CVH.textViewUpdateIcon.setText(context.getResources().getString(R.string.fa_update));
                    CVH.textViewUpdateIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                            // setting icon to dialog
                            TextDrawable faIcon = new TextDrawable(context);
                            faIcon.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                            faIcon.setTextAlign(Layout.Alignment.ALIGN_CENTER);
                            faIcon.setTypeface(cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                            faIcon.setText(context.getResources().getText(R.string.fa_save));
                            faIcon.setTextColor(context.getColor(R.color.colorAccent));
                            alertDialogBuilder.setIcon(faIcon);

                            // set title
                            alertDialogBuilder.setTitle("Save Role.");
                            // set dialog message
                            alertDialogBuilder
                                    .setMessage("Do you want to SAVE role: " +
                                            CVH.textViewName.getText() + " ?")
                                    .setCancelable(false)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // update the permissions in the database

                                            //EVH.createPermissions();
                                            dialog.dismiss();
                                            //notifyItemChanged(position);
                                        }
                                    })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // if this button is clicked, just close
                                            dialog.cancel();
                                        }
                                    });

                            // create alert dialog
                            AlertDialog alertDialog = alertDialogBuilder.create();

                            // show it
                            alertDialog.show();
                        }
                    });

                    /** icon for deleting a record **/
                    CVH.textViewDeleteIcon.setTypeface(null, Typeface.NORMAL);
                    CVH.textViewDeleteIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    CVH.textViewDeleteIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
                    CVH.textViewDeleteIcon.setText(context.getResources().getString(R.string.fa_delete));
                    CVH.textViewDeleteIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                            // setting icon to dialog
                            TextDrawable faIcon = new TextDrawable(context);
                            faIcon.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                            faIcon.setTextAlign(Layout.Alignment.ALIGN_CENTER);
                            faIcon.setTypeface(cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                            faIcon.setText(context.getResources().getText(R.string.fa_delete));
                            faIcon.setTextColor(context.getColor(R.color.colorAccent));
                            alertDialogBuilder.setIcon(faIcon);

                            // set title
                            alertDialogBuilder.setTitle("Remove Role.");
                            // set dialog message
                            alertDialogBuilder
                                    .setMessage("Do you want to REMOVE role: " +
                                            CVH.textViewName.getText() + " ?")
                                    .setCancelable(false)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // update the permissions in the database
                                            //EVH.createPermissions();
                                            //permissionHandler.deletePermission()
                                            dialog.dismiss();
                                            //notifyItemChanged(position);
                                        }
                                    })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // if this button is clicked, just close
                                            dialog.cancel();
                                        }
                                    });

                            // create alert dialog
                            AlertDialog alertDialog = alertDialogBuilder.create();

                            // show it
                            alertDialog.show();
                        }
                    });

                    /** icon for syncing a record **/
                    CVH.textViewSyncIcon.setTypeface(null, Typeface.NORMAL);
                    CVH.textViewSyncIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    CVH.textViewSyncIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
                    CVH.textViewSyncIcon.setText(context.getResources().getString(R.string.fa_sync));
                    CVH.textViewSyncIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                            // setting icon to dialog
                            TextDrawable faIcon = new TextDrawable(context);
                            faIcon.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                            faIcon.setTextAlign(Layout.Alignment.ALIGN_CENTER);
                            faIcon.setTypeface(cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                            faIcon.setText(context.getResources().getText(R.string.fa_sync));
                            faIcon.setTextColor(context.getColor(R.color.colorAccent));
                            alertDialogBuilder.setIcon(faIcon);

                            // set title
                            alertDialogBuilder.setTitle("Sync Role.");
                            // set dialog message
                            alertDialogBuilder
                                    .setMessage("Do you want to SYNCHRONISE role: " +
                                            CVH.textViewName.getText() + " ?")
                                    .setCancelable(false)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // update the permissions in the database
                                            //EVH.createPermissions();
                                            dialog.dismiss();
                                            //notifyItemChanged(position);
                                        }
                                    })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // if this button is clicked, just close
                                            dialog.cancel();
                                        }
                                    });

                            // create alert dialog
                            AlertDialog alertDialog = alertDialogBuilder.create();

                            // show it
                            alertDialog.show();
                        }
                    });

                    /** icon for creating a record **/
                    CVH.textViewCreateIcon.setTypeface(null, Typeface.NORMAL);
                    CVH.textViewCreateIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    CVH.textViewCreateIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
                    CVH.textViewCreateIcon.setText(context.getResources().getString(R.string.fa_create));
                    CVH.textViewCreateIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                            // setting icon to dialog
                            TextDrawable faIcon = new TextDrawable(context);
                            faIcon.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                            faIcon.setTextAlign(Layout.Alignment.ALIGN_CENTER);
                            faIcon.setTypeface(cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                            faIcon.setText(context.getResources().getText(R.string.fa_create));
                            faIcon.setTextColor(context.getColor(R.color.colorAccent));
                            alertDialogBuilder.setIcon(faIcon);

                            // set title
                            alertDialogBuilder.setTitle("Create LogFrame.");
                            // set dialog message
                            alertDialogBuilder
                                    .setMessage("Do you want to CREATE logframe: " +
                                            CVH.textViewName.getText() + " ?")
                                    .setCancelable(false)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // update the permissions in the database
                                            //EVH.createPermissions();
                                            dialog.dismiss();
                                            //notifyItemChanged(position);
                                        }
                                    })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // if this button is clicked, just close
                                            dialog.cancel();
                                        }
                                    });

                            // create alert dialog
                            AlertDialog alertDialog = alertDialogBuilder.create();

                            // show it
                            alertDialog.show();
                        }
                    });

                    break;
            }
        }
    }

    public static class cParentLogFrameViewHolder extends cTreeViewHolder {
        private TextView textViewExpandLogFrameIcon;
        private cExpandableLayout expandableLayout;

        //private TextView textViewCount;
        private cBoomMenuButton bmb;

        private TextView textViewName;
        private TextView textViewDescription;

        private TextView textViewDetailIcon;
        private TextView textViewSyncIcon;
        private TextView textViewDeleteIcon;
        private TextView textViewUpdateIcon;
        private TextView textViewCreateIcon;

        private View treeView;

        public cParentLogFrameViewHolder(final View treeViewHolder) {
            super(treeViewHolder);
            treeView = treeViewHolder;

            this.textViewExpandLogFrameIcon = (TextView)
                    treeViewHolder.findViewById(R.id.textViewExpandLogFrameIcon);
            this.expandableLayout = (cExpandableLayout)
                    treeViewHolder.findViewById(R.id.expandableLayout);

            //this.textViewCount = (TextView) treeViewHolder.findViewById(R.id.textViewCount);
            this.bmb = (cBoomMenuButton) treeViewHolder.findViewById(R.id.bmb);

            this.textViewName = (TextView) treeViewHolder.findViewById(R.id.textViewName);
            this.textViewDescription =
                    (TextView) treeViewHolder.findViewById(R.id.textViewDescription);
            this.textViewDetailIcon = (TextView) treeViewHolder.findViewById(R.id.textViewDetailIcon);
            this.textViewSyncIcon = (TextView) treeViewHolder.findViewById(R.id.textViewSyncIcon);
            this.textViewDeleteIcon = (TextView) treeViewHolder.findViewById(R.id.textViewDeleteIcon);
            this.textViewUpdateIcon = (TextView) treeViewHolder.findViewById(R.id.textViewUpdateIcon);
            this.textViewCreateIcon = (TextView) treeViewHolder.findViewById(R.id.textViewCreateIcon);
        }

        public void setPaddingLeft(int paddingLeft) {
            treeView.setPadding(paddingLeft, 0, 0, 0);
        }
    }

    public static class cChildLogFrameViewHolder extends cTreeViewHolder {
        private cExpandableLayout expandableLayout;

        private cBoomMenuButton bmb;

        private TextView textViewName;
        private TextView textViewDescription;

        private TextView textViewDetailIcon;
        private TextView textViewSyncIcon;
        private TextView textViewDeleteIcon;
        private TextView textViewUpdateIcon;
        private TextView textViewCreateIcon;

        private View treeView;

        public cChildLogFrameViewHolder(View treeViewHolder) {
            super(treeViewHolder);
            treeView = treeViewHolder;

            this.expandableLayout = (cExpandableLayout)
                    treeViewHolder.findViewById(R.id.expandableLayout);
            this.bmb = (cBoomMenuButton) treeViewHolder.findViewById(R.id.bmb);
            this.textViewName = (TextView) treeViewHolder.findViewById(R.id.textViewName);
            this.textViewDescription =
                    (TextView) treeViewHolder.findViewById(R.id.textViewDescription);
            this.textViewDetailIcon = (TextView) treeViewHolder.findViewById(R.id.textViewDetailIcon);
            this.textViewSyncIcon = (TextView) treeViewHolder.findViewById(R.id.textViewSyncIcon);
            this.textViewDeleteIcon = (TextView) treeViewHolder.findViewById(R.id.textViewDeleteIcon);
            this.textViewUpdateIcon = (TextView) treeViewHolder.findViewById(R.id.textViewUpdateIcon);
            this.textViewCreateIcon = (TextView) treeViewHolder.findViewById(R.id.textViewCreateIcon);
        }

        public void setPaddingLeft(int paddingLeft) {
            treeView.setPadding(paddingLeft, 0, 0, 0);
        }
    }
}