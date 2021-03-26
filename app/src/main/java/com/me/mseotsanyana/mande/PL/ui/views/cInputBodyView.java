package com.me.mseotsanyana.mande.PL.ui.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.me.mseotsanyana.bmblibrary.BoomButtons.OnBMClickListener;
import com.me.mseotsanyana.bmblibrary.BoomButtons.cButtonPlaceEnum;
import com.me.mseotsanyana.bmblibrary.BoomButtons.cTextOutsideCircleButton;
import com.me.mseotsanyana.bmblibrary.Piece.cPiecePlaceEnum;
import com.me.mseotsanyana.bmblibrary.cBoomMenuButton;
import com.me.mseotsanyana.bmblibrary.cUtil;
import com.me.mseotsanyana.expandablelayoutlibrary.cExpandableLayout;
import com.me.mseotsanyana.mande.BLL.model.logframe.cInputModel;
import com.me.mseotsanyana.mande.PL.ui.listeners.logframe.iViewInputListener;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.UTIL.cConstant;
import com.me.mseotsanyana.mande.UTIL.cFontManager;
import com.me.mseotsanyana.placeholderview.annotationlibrary.Click;
import com.me.mseotsanyana.placeholderview.annotationlibrary.Layout;
import com.me.mseotsanyana.placeholderview.annotationlibrary.Resolve;
import com.me.mseotsanyana.placeholderview.annotationlibrary.View;
import com.me.mseotsanyana.placeholderview.annotationlibrary.expand.ChildPosition;
import com.me.mseotsanyana.placeholderview.annotationlibrary.expand.ParentPosition;
import com.me.mseotsanyana.treeadapterlibrary.cTreeAdapter;

import java.text.SimpleDateFormat;

@Layout(R.layout.input_resouces_cardview)
public class cInputBodyView extends cTreeAdapter {
    private static final String TAG = cInputBodyView.class.getSimpleName();
    private static SimpleDateFormat sdf = cConstant.SHORT_FORMAT_DATE;

    @ParentPosition
    public int parentPosition;

    @ChildPosition
    public int childPosition;

    @View(R.id.cardView)
    public CardView cardView;

    @View(R.id.textViewActivityCaption)
    public TextView textViewActivityCaption;

    @View(R.id.textViewActivity)
    public TextView textViewActivity;

    @View(R.id.textViewInputCaption)
    public TextView textViewInputCaption;

    @View(R.id.textViewInput)
    public TextView textViewInput;

    @View(R.id.textViewDescription)
    public TextView textViewDescription;

    @View(R.id.textViewStartDate)
    public TextView textViewStartDate;

    @View(R.id.textViewEndDate)
    public TextView textViewEndDate;

    @View(R.id.expandableLayout)
    public cExpandableLayout expandableLayout;

    @View(R.id.textViewDetailIcon)
    public TextView textViewDetailIcon;

    @View(R.id.textViewUpdateIcon)
    public TextView textViewUpdateIcon;

    @View(R.id.textViewDeleteIcon)
    public TextView textViewDeleteIcon;

    @View(R.id.textViewSyncIcon)
    public TextView textViewSyncIcon;

    @View(R.id.bmbMenu)
    public cBoomMenuButton bmbMenu;

    private final String[] bmb_caption = {
            "Sub-LogFrame Activities",
            "Child Inputs",
            "Questions"
    };

    private int[] bmb_imageid = {
            R.drawable.dashboard_activity,
            R.drawable.dashboard_input,
            R.drawable.dashboard_question
    };

    private Context context;
    private iViewInputListener listener;
    private cInputModel inputModel;

    private String activity;
    private String input;
    private String description;
    private String startDate;
    private String endDate;

    public cInputBodyView(Context context, iViewInputListener listener,
                          cInputModel inputModel) {
        super(context,null);
        this.context = context;
        this.listener = listener;
        this.inputModel = inputModel;
        this.activity = inputModel.getActivityModel().getName();
        this.input = inputModel.getResourceTypeModel().getName();
        this.description = inputModel.getResourceTypeModel().getDescription();
        this.startDate = sdf.format(inputModel.getStartDate());
        this.endDate = sdf.format(inputModel.getEndDate());
    }

    @Resolve
    public void onResolved() {
        textViewActivityCaption.setText(R.string.activity_caption);
        textViewActivity.setText(activity);
        textViewInputCaption.setText(R.string.input_caption);
        textViewInput.setText(input);
        textViewDescription.setText(description);
        textViewStartDate.setText(startDate);
        textViewEndDate.setText(endDate);

        /* collapse and expansion of the details */
        this.textViewDetailIcon.setTypeface(null, Typeface.NORMAL);
        this.textViewDetailIcon.setTypeface(cFontManager.getTypeface(context,
                cFontManager.FONTAWESOME));
        this.textViewDetailIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
        this.textViewDetailIcon.setText(context.getResources().getString(R.string.fa_angle_down));

        /* icon for saving updated record */
        this.textViewUpdateIcon.setTypeface(null, Typeface.NORMAL);
        this.textViewUpdateIcon.setTypeface(
                cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
        this.textViewUpdateIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
        this.textViewUpdateIcon.setText(context.getResources().getString(R.string.fa_update));

        /* icon for deleting a record */
        this.textViewDeleteIcon.setTypeface(null, Typeface.NORMAL);
        this.textViewDeleteIcon.setTypeface(
                cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
        this.textViewDeleteIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
        this.textViewDeleteIcon.setText(context.getResources().getString(R.string.fa_delete));

        /* icon for syncing a record */
        this.textViewSyncIcon.setTypeface(null, Typeface.NORMAL);
        this.textViewSyncIcon.setTypeface(
                cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
        this.textViewSyncIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
        this.textViewSyncIcon.setText(context.getResources().getString(R.string.fa_sync));

        /* icon for bmb menu */
        this.bmbMenu.clearBuilders();
        this.bmbMenu.setPiecePlaceEnum(cPiecePlaceEnum.DOT_3_1);
        this.bmbMenu.setButtonPlaceEnum(cButtonPlaceEnum.SC_3_1);
        for (int i = 0; i < this.bmbMenu.getPiecePlaceEnum().pieceNumber(); i++) {
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
                            listener.onClickBMBInput(index);
                        }
                    });
            this.bmbMenu.addBuilder(builder);
        }

        cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.list_odd));
    }

    @Click(R.id.textViewDetailIcon)
    void onDetailIconClick(){
        if (!(expandableLayout.isExpanded())) {
            this.textViewDetailIcon.setText(
                    context.getResources().getString(R.string.fa_angle_up));
        } else {
            this.textViewDetailIcon.setText(
                    context.getResources().getString(R.string.fa_angle_down));
        }

        this.expandableLayout.toggle();
    }

    @Click(R.id.textViewUpdateIcon)
    void onUpdateIconClick(){
        listener.onClickUpdateInput(childPosition, inputModel);
    }

    @Click(R.id.textViewDeleteIcon)
    void onDeleteIconClick(){
        listener.onClickDeleteInput(childPosition, inputModel.getComponentID());
    }

    @Click(R.id.textViewSyncIcon)
    void onSyncIconClick(){
        listener.onClickSyncInput(childPosition, inputModel);
    }

    public void setPlaceHolderViewInputListener(iViewInputListener listener) {
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder OnCreateTreeViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void OnBindTreeViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

    }
}