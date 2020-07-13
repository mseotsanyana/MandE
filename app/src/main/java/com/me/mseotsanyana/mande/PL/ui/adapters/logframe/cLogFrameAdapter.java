package com.me.mseotsanyana.mande.PL.ui.adapters.logframe;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.me.mseotsanyana.bmblibrary.BoomButtons.OnBMClickListener;
import com.me.mseotsanyana.bmblibrary.BoomButtons.cTextOutsideCircleButton;
import com.me.mseotsanyana.bmblibrary.cBoomMenuButton;
import com.me.mseotsanyana.bmblibrary.cUtil;
import com.me.mseotsanyana.expandablelayoutlibrary.cExpandableLayout;
import com.me.mseotsanyana.mande.DAL.model.logframe.cLogFrameModel;
import com.me.mseotsanyana.mande.PL.presenters.logframe.iLogFramePresenter;
import com.me.mseotsanyana.mande.PL.ui.adapters.common.cCommonFragmentAdapter;
import com.me.mseotsanyana.mande.PL.ui.fragments.common.cCustomViewPager;
import com.me.mseotsanyana.mande.PL.ui.listeners.common.iViewPagerHeightListener;
import com.me.mseotsanyana.mande.PL.ui.listeners.logframe.iViewLogFrameListener;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.UTIL.cConstant;
import com.me.mseotsanyana.mande.UTIL.cFontManager;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;
import com.me.mseotsanyana.treeadapterlibrary.cNode;
import com.me.mseotsanyana.treeadapterlibrary.cTreeAdapter;
import com.me.mseotsanyana.treeadapterlibrary.cTreeViewHolder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mseotsanyana on 2017/02/27.
 */

public class cLogFrameAdapter extends cTreeAdapter implements iViewLogFrameListener,
        Filterable {
    private static String TAG = cLogFrameAdapter.class.getSimpleName();
    private static SimpleDateFormat sdf = cConstant.SHORT_FORMAT_DATE;

    private static final int PARENT_LOGFRAME = 0;
    private static final int CHILD_LOGFRAME  = 1;

    private final iLogFramePresenter.View logframePresenterView;

    public FragmentManager fragmentManager;

    private List<cTreeModel> filteredTreeModels;

    private final String[] bmb_caption = {
            "Impacts",
            "Outcomes",
            "Outputs",
            "Activities",
            "Inputs",
            "Questions",
            "Monitoring",
            "Evaluation",
            "Risk Register"
    };

    private int[] bmb_imageid = {
            R.drawable.dashboard_impact,
            R.drawable.dashboard_outcome,
            R.drawable.dashboard_output,
            R.drawable.dashboard_activity,
            R.drawable.dashboard_input,
            R.drawable.dashboard_question,
            R.drawable.dashboard_monitoring,
            R.drawable.dashboard_evaluating,
            R.drawable.dashboard_risk
    };

    public cLogFrameAdapter(Context context, iLogFramePresenter.View logframePresenterView,
                            List<cTreeModel> treeModels, FragmentManager fragmentManager) {
        super(context, treeModels);

        this.logframePresenterView = logframePresenterView;
        this.fragmentManager = fragmentManager;

        this.filteredTreeModels = treeModels;

    }

    public RecyclerView.ViewHolder OnCreateTreeViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;cCommonFragmentAdapter commonFragmentAdapter;
        switch (viewType) {
            case PARENT_LOGFRAME:
                view = inflater.inflate(R.layout.logframe_parent_cardview, parent, false);
                commonFragmentAdapter = logframePresenterView.onGetCommonFragmentAdapter();
                viewHolder = new cParentLogFrameViewHolder(view, commonFragmentAdapter,
                        this);
                break;
            case CHILD_LOGFRAME:
                view = inflater.inflate(R.layout.logframe_parent_cardview, parent, false);
                commonFragmentAdapter = logframePresenterView.onGetCommonFragmentAdapter();
                viewHolder = new cChildLogFrameViewHolder(view, commonFragmentAdapter,
                        this);
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
                    cLogFrameModel parentLogFrame = (cLogFrameModel) obj.getModelObject();
                    cParentLogFrameViewHolder PVH = ((cParentLogFrameViewHolder) viewHolder);

                    PVH.setPaddingLeft(20 * node.getLevel());

                    final int parentBackgroundColor = (position%2 == 0) ? R.color.list_even :
                            R.color.list_odd;
                    PVH.cardView.setCardBackgroundColor(ContextCompat.getColor(context,
                            parentBackgroundColor));

                    PVH.textViewOrganization.setText(parentLogFrame.getOrganizationModel().
                            getName());

                    PVH.textViewName.setText(parentLogFrame.getName());
                    PVH.textViewDescription.setText(parentLogFrame.getDescription());
                    PVH.textViewStartDate.setText(sdf.format(parentLogFrame.getStartDate()));
                    PVH.textViewEndDate.setText(sdf.format(parentLogFrame.getEndDate()));

                    PVH.bmbMenu.clearBuilders();
                    for (int i = 0; i < PVH.bmbMenu.getPiecePlaceEnum().pieceNumber(); i++) {
                        cTextOutsideCircleButton.Builder builder = new cTextOutsideCircleButton
                                .Builder()
                                .isRound(false)
                                .shadowCornerRadius(cUtil.dp2px(20))
                                .buttonCornerRadius(cUtil.dp2px(20))
                                .normalColor(Color.LTGRAY)
                                .pieceColor(context.getColor(R.color.colorPrimaryDark))
                                .normalImageRes(bmb_imageid[i])
                                .normalText(bmb_caption[i])
                                .listener(new OnBMClickListener() {
                                    @Override
                                    public void onBoomButtonClick(int index) {
                                        /* when the boom-button is clicked. */
                                        PVH.logFrameListener.onClickBMBLogFrame(
                                                index, parentLogFrame.getLogFrameID());
                                    }
                                });
                        PVH.bmbMenu.addBuilder(builder);
                    }

                    PVH.bmbMenu.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            PVH.bmbMenu.boom();
                        }
                    });

                    /* the collapse and expansion of the parent logframe */
                    if (node.isLeaf()) {
                        PVH.textViewExpandIcon.setVisibility(View.GONE);
                    } else {

                        PVH.textViewExpandIcon.setVisibility(View.VISIBLE);
                        if (node.isExpand()) {
                            PVH.textViewExpandIcon.setTypeface(null, Typeface.NORMAL);
                            PVH.textViewExpandIcon.setTypeface(
                                    cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                            PVH.textViewExpandIcon.setText(
                                    context.getResources().getString(R.string.fa_minus));
                        } else {
                            PVH.textViewExpandIcon.setTypeface(null, Typeface.NORMAL);
                            PVH.textViewExpandIcon.setTypeface(
                                    cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                            PVH.textViewExpandIcon.setText(
                                    context.getResources().getString(R.string.fa_plus));
                        }
                    }

                    PVH.textViewExpandIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            expandOrCollapse(position);
                        }
                    });

                    /* collapse and expansion of the details */
                    PVH.textViewDetailIcon.setTypeface(null, Typeface.NORMAL);
                    PVH.textViewDetailIcon.setTypeface(cFontManager.getTypeface(context,
                            cFontManager.FONTAWESOME));
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

                    /* icon for saving updated record */
                    PVH.textViewUpdateIcon.setTypeface(null, Typeface.NORMAL);
                    PVH.textViewUpdateIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    PVH.textViewUpdateIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
                    PVH.textViewUpdateIcon.setText(context.getResources().getString(R.string.fa_update));
                    PVH.textViewUpdateIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            PVH.logFrameListener.onClickUpdateLogFrame(position, parentLogFrame);
                        }
                    });

                    /* icon for deleting a record */
                    PVH.textViewDeleteIcon.setTypeface(null, Typeface.NORMAL);
                    PVH.textViewDeleteIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    PVH.textViewDeleteIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
                    PVH.textViewDeleteIcon.setText(context.getResources().getString(R.string.fa_delete));
                    PVH.textViewDeleteIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            PVH.logFrameListener.onClickDeleteLogFrame(position,
                                    parentLogFrame.getLogFrameID());
                        }
                    });

                    /* icon for syncing a record */
                    PVH.textViewSyncIcon.setTypeface(null, Typeface.NORMAL);
                    PVH.textViewSyncIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    PVH.textViewSyncIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
                    PVH.textViewSyncIcon.setText(context.getResources().getString(R.string.fa_sync));
                    PVH.textViewSyncIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            PVH.logFrameListener.onClickSyncLogFrame(position, parentLogFrame);
                        }
                    });

                    /* icon for creating a record */
                    PVH.textViewCreateIcon.setTypeface(null, Typeface.NORMAL);
                    PVH.textViewCreateIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    PVH.textViewCreateIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
                    PVH.textViewCreateIcon.setText(context.getResources().getString(R.string.fa_create));
                    PVH.textViewCreateIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            PVH.logFrameListener.onClickCreateSubLogFrame(
                                    parentLogFrame.getLogFrameID(), new cLogFrameModel());
                        }
                    });

                    /* setup the common details */
                    //PVH.setViewPager(parentLogFrame);

                    break;

                case CHILD_LOGFRAME:
                    cLogFrameModel childLogFrameModel = (cLogFrameModel) obj.getModelObject();
                    cChildLogFrameViewHolder CVH = ((cChildLogFrameViewHolder) viewHolder);

                    /* remove these views */
                    CVH.textViewExpandIcon.setVisibility(View.GONE);
                    CVH.textViewOrgCaption.setVisibility(View.GONE);
                    CVH.textViewOrganization.setVisibility(View.GONE);

                    CVH.setPaddingLeft(20 * node.getLevel());
                    final int childBackgroundColor = (position%2 == 0) ? R.color.list_even :
                            R.color.list_odd;
                    CVH.cardView.setCardBackgroundColor(ContextCompat.getColor(context,
                            childBackgroundColor));

                    CVH.textViewName.setText(childLogFrameModel.getName());
                    CVH.textViewDescription.setText(childLogFrameModel.getDescription());
                    CVH.textViewStartDate.setText(sdf.format(childLogFrameModel.getStartDate()));
                    CVH.textViewEndDate.setText(sdf.format(childLogFrameModel.getEndDate()));

                    // collapse and expansion of the details of the role
                    CVH.textViewDetailIcon.setTypeface(null, Typeface.NORMAL);
                    CVH.textViewDetailIcon.setTypeface(cFontManager.getTypeface(context,
                            cFontManager.FONTAWESOME));
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

                    /* icon for saving updated record */
                    CVH.textViewUpdateIcon.setTypeface(null, Typeface.NORMAL);
                    CVH.textViewUpdateIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    CVH.textViewUpdateIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
                    CVH.textViewUpdateIcon.setText(context.getResources().getString(R.string.fa_update));
                    CVH.textViewUpdateIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    });

                    /* icon for deleting a record */
                    CVH.textViewDeleteIcon.setTypeface(null, Typeface.NORMAL);
                    CVH.textViewDeleteIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    CVH.textViewDeleteIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
                    CVH.textViewDeleteIcon.setText(context.getResources().getString(R.string.fa_delete));
                    CVH.textViewDeleteIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    });

                    /* icon for syncing a record */
                    CVH.textViewSyncIcon.setTypeface(null, Typeface.NORMAL);
                    CVH.textViewSyncIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    CVH.textViewSyncIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
                    CVH.textViewSyncIcon.setText(context.getResources().getString(R.string.fa_sync));
                    CVH.textViewSyncIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    });

                    /* icon for creating a record */
                    CVH.textViewCreateIcon.setTypeface(null, Typeface.NORMAL);
                    CVH.textViewCreateIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    CVH.textViewCreateIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
                    CVH.textViewCreateIcon.setText(context.getResources().getString(R.string.fa_create));
                    CVH.textViewCreateIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    });

                    /* icon for creating a record */
                    CVH.bmbMenu.clearBuilders();
                    for (int i = 0; i < CVH.bmbMenu.getPiecePlaceEnum().pieceNumber(); i++) {
                        cTextOutsideCircleButton.Builder builder = new cTextOutsideCircleButton
                                .Builder()
                                .isRound(false)
                                .shadowCornerRadius(cUtil.dp2px(20))
                                .buttonCornerRadius(cUtil.dp2px(20))
                                .normalColor(Color.LTGRAY)
                                .pieceColor(context.getColor(R.color.colorPrimaryDark))
                                .normalImageRes(bmb_imageid[i])
                                .normalText(bmb_caption[i])
                                .listener(new OnBMClickListener() {
                                    @Override
                                    public void onBoomButtonClick(int index) {
                                        /* when the boom-button is clicked. */
                                        //CVH.logFrameListener.onClickBoomMenu(index);
                                    }
                                });
                        CVH.bmbMenu.addBuilder(builder);
                    }

                    CVH.bmbMenu.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CVH.bmbMenu.boom();
                        }
                    });

                    /* setup the common details */
                    //CVH.setViewPager(childLogFrameModel);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + obj.getType());
            }
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();
                Gson gson = new Gson();

                if (charString.isEmpty()) {
                    filteredTreeModels = treeModels;
                } else {

                    ArrayList<cTreeModel> filteredList = new ArrayList<>();
                    for (cTreeModel treeModel : treeModels) {
                        if (((cLogFrameModel)treeModel.getModelObject()).getName().toLowerCase().
                                contains(charString.toLowerCase())) {
                            filteredList.add(treeModel);
                            Log.d(TAG, "treeModel = "+gson.toJson(((cLogFrameModel)treeModel.
                                    getModelObject()).getName()));
                        }
                    }

                    filteredTreeModels = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.count  = filteredTreeModels.size();
                filterResults.values = filteredTreeModels;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredTreeModels = (ArrayList<cTreeModel>) filterResults.values;

                Gson gson = new Gson();
                Log.d(TAG, "treeModel result = "+gson.toJson(filteredTreeModels));
                try {
                    notifyTreeModelChanged(filteredTreeModels);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    @Override
    public void onClickBMBLogFrame(int index, long logFrameID) {
        logframePresenterView.onClickBMBLogFrame(index, logFrameID);
    }

    @Override
    public void onClickCreateSubLogFrame(long logFrameID, cLogFrameModel logFrameModel) {
        logframePresenterView.onClickCreateSubLogFrame(logFrameID, logFrameModel);
    }

    @Override
    public void onClickUpdateLogFrame(int position, cLogFrameModel logFrameModel) {
        logframePresenterView.onClickUpdateLogFrame(position, logFrameModel);
    }

    @Override
    public void onClickDeleteLogFrame(int position, long logframeID) {
        logframePresenterView.onClickDeleteLogFrame(position, logframeID);
    }

    @Override
    public void onClickSyncLogFrame(int position, cLogFrameModel logFrameModel) {
        logframePresenterView.onClickSyncLogFrame(logFrameModel);
    }

    public static class cParentLogFrameViewHolder extends cTreeViewHolder implements
            iViewPagerHeightListener {
        private CardView cardView;
        private cExpandableLayout expandableLayout;

        private AppCompatTextView textViewExpandIcon;
        private AppCompatTextView textViewOrganization;
        private AppCompatTextView textViewName;
        private AppCompatTextView textViewDescription;
        private AppCompatTextView textViewStartDate;
        private AppCompatTextView textViewEndDate;

        private cBoomMenuButton bmbMenu;
        private AppCompatTextView textViewSyncIcon;
        private AppCompatTextView textViewDeleteIcon;
        private AppCompatTextView textViewUpdateIcon;
        private AppCompatTextView textViewCreateIcon;
        private AppCompatTextView textViewDetailIcon;

        private cCustomViewPager viewPager;
        private TabLayout tabLayout;

        private View treeView;
        private cCommonFragmentAdapter commonFragmentAdapter;
        private iViewLogFrameListener logFrameListener;

        private cParentLogFrameViewHolder(final View treeViewHolder,
                                         cCommonFragmentAdapter commonFragmentAdapter,
                                         iViewLogFrameListener listener) {
            super(treeViewHolder);

            this.treeView = treeViewHolder;
            this.commonFragmentAdapter = commonFragmentAdapter;
            this.logFrameListener = listener;

            this.cardView = treeViewHolder.findViewById(R.id.cardView);
            this.expandableLayout = treeViewHolder.findViewById(R.id.expandableLayout);
            this.textViewExpandIcon = treeViewHolder.findViewById(R.id.textViewExpandIcon);
            this.bmbMenu = treeViewHolder.findViewById(R.id.bmbMenu);
            this.textViewOrganization = treeViewHolder.findViewById(R.id.textViewOrganization);
            this.textViewName = treeViewHolder.findViewById(R.id.textViewName);
            this.textViewDescription = treeViewHolder.findViewById(R.id.textViewDescription);
            this.textViewStartDate = treeViewHolder.findViewById(R.id.textViewStartDate);
            this.textViewEndDate = treeViewHolder.findViewById(R.id.textViewEndDate);
            this.textViewDetailIcon = treeViewHolder.findViewById(R.id.textViewDetailIcon);
            this.textViewSyncIcon = treeViewHolder.findViewById(R.id.textViewSyncIcon);
            this.textViewDeleteIcon = treeViewHolder.findViewById(R.id.textViewDeleteIcon);
            this.textViewUpdateIcon = treeViewHolder.findViewById(R.id.textViewUpdateIcon);
            this.textViewCreateIcon = treeViewHolder.findViewById(R.id.textViewCreateIcon);
            this.viewPager = treeViewHolder.findViewById(R.id.viewPager);

            this.tabLayout = treeViewHolder.findViewById(R.id.tabLayout);

            /*set the listener to the cCustomViewPager */
            this.viewPager.setOnCustomViewListener(this);

            /* forces the viewpager to create four fragments when loading */
            this.viewPager.setOffscreenPageLimit(4);

            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
            tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));

            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    //onSelectedFragment(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

            this.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    //viewPager.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });

        }

        public void setPaddingLeft(int paddingLeft) {
            treeView.setPadding(paddingLeft, 0, 0, 0);
        }

        private void setViewPager(cLogFrameModel logFrameModel) {
            /* pass data to the adapter */
            Gson gson = new Gson();
            this.commonFragmentAdapter.readCommonAttributes(logFrameModel.getOwnerID(),
                    logFrameModel.getOrgID(), logFrameModel.getGroupBITS(),
                    logFrameModel.getPermsBITS(), logFrameModel.getStatusBITS(),
                    gson.toJson(logFrameModel.getCreatedDate()).replace("\"", ""),
                    gson.toJson(logFrameModel.getModifiedDate()).replace("\"", ""),
                    gson.toJson(logFrameModel.getSyncedDate()).replace("\"", ""));

            /* bind the adapter to the viewPager */
            this.viewPager.setAdapter(this.commonFragmentAdapter);

            /* attached the pager to the tabLayer */
            this.tabLayout.setupWithViewPager(this.viewPager);
        }

        public void onViewPagerHeightUpdate(int height) {
            this.expandableLayout.onUpdateHeight(height);
        }
    }

    public static class cChildLogFrameViewHolder extends cTreeViewHolder implements
            iViewPagerHeightListener{
        private CardView cardView;
        private cExpandableLayout expandableLayout;

        private AppCompatTextView textViewExpandIcon;
        private AppCompatTextView textViewOrgCaption;
        private AppCompatTextView textViewOrganization;
        private AppCompatTextView textViewName;
        private AppCompatTextView textViewDescription;
        private AppCompatTextView textViewStartDate;
        private AppCompatTextView textViewEndDate;

        private AppCompatTextView textViewDetailIcon;
        private cBoomMenuButton bmbMenu;
        private AppCompatTextView textViewSyncIcon;
        private AppCompatTextView textViewDeleteIcon;
        private AppCompatTextView textViewUpdateIcon;
        private AppCompatTextView textViewCreateIcon;

        private cCustomViewPager viewPager;
        private TabLayout tabLayout;

        private View treeView;
        private cCommonFragmentAdapter commonFragmentAdapter;
        private iViewLogFrameListener logFrameListener;

        private cChildLogFrameViewHolder(View treeViewHolder,
                                         cCommonFragmentAdapter commonFragmentAdapter,
                                         iViewLogFrameListener recyclerViewLogFrame) {
            super(treeViewHolder);
            treeView = treeViewHolder;
            this.commonFragmentAdapter = commonFragmentAdapter;
            this.logFrameListener = recyclerViewLogFrame;

            this.cardView = treeViewHolder.findViewById(R.id.cardView);
            this.expandableLayout = treeViewHolder.findViewById(R.id.expandableLayout);
            this.textViewExpandIcon = treeViewHolder.findViewById(R.id.textViewExpandIcon);
            this.bmbMenu = treeViewHolder.findViewById(R.id.bmbMenu);
            this.textViewOrgCaption = treeViewHolder.findViewById(R.id.textViewOrgCaption);
            this.textViewOrganization = treeViewHolder.findViewById(R.id.textViewOrganization);
            this.textViewName = treeViewHolder.findViewById(R.id.textViewName);
            this.textViewDescription = treeViewHolder.findViewById(R.id.textViewDescription);
            this.textViewStartDate = treeViewHolder.findViewById(R.id.textViewStartDate);
            this.textViewEndDate = treeViewHolder.findViewById(R.id.textViewEndDate);
            this.textViewDetailIcon = treeViewHolder.findViewById(R.id.textViewDetailIcon);
            this.textViewSyncIcon = treeViewHolder.findViewById(R.id.textViewSyncIcon);
            this.textViewDeleteIcon = treeViewHolder.findViewById(R.id.textViewDeleteIcon);
            this.textViewUpdateIcon = treeViewHolder.findViewById(R.id.textViewUpdateIcon);
            this.textViewCreateIcon = treeViewHolder.findViewById(R.id.textViewCreateIcon);

            /* view pager for common attributes */
            this.viewPager = treeViewHolder.findViewById(R.id.viewPager);

            /* tab layout for common attributes */
            this.tabLayout = treeViewHolder.findViewById(R.id.tabLayout);

            /*set the listener to the cCustomViewPager */
            this.viewPager.setOnCustomViewListener(this);

            /* forces the viewpager to create four fragments when loading */
            this.viewPager.setOffscreenPageLimit(4);

            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
            tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        }

        private void setViewPager(cLogFrameModel logFrameModel) {
            /* pass data to the adapter */
            Gson gson = new Gson();
            this.commonFragmentAdapter.readCommonAttributes(logFrameModel.getOwnerID(),
                    logFrameModel.getOrgID(), logFrameModel.getGroupBITS(),
                    logFrameModel.getPermsBITS(), logFrameModel.getStatusBITS(),
                    gson.toJson(logFrameModel.getCreatedDate()).replace("\"", ""),
                    gson.toJson(logFrameModel.getModifiedDate()).replace("\"", ""),
                    gson.toJson(logFrameModel.getSyncedDate()).replace("\"", ""));

            /* bind the adapter to the viewPager */
            this.viewPager.setAdapter(this.commonFragmentAdapter);

            /* attached the pager to the tabLayer */
            this.tabLayout.setupWithViewPager(this.viewPager);
        }

        public void setPaddingLeft(int paddingLeft) {
            treeView.setPadding(paddingLeft, 0, 0, 0);
        }

        @Override
        public void onViewPagerHeightUpdate(int height) {
            this.expandableLayout.onUpdateHeight(height);
        }
    }
}