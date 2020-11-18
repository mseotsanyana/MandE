package com.me.mseotsanyana.mande.PL.ui.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.me.mseotsanyana.bmblibrary.BoomButtons.OnBMClickListener;
import com.me.mseotsanyana.bmblibrary.BoomButtons.cButtonPlaceEnum;
import com.me.mseotsanyana.bmblibrary.BoomButtons.cTextOutsideCircleButton;
import com.me.mseotsanyana.bmblibrary.Piece.cPiecePlaceEnum;
import com.me.mseotsanyana.bmblibrary.cBoomMenuButton;
import com.me.mseotsanyana.bmblibrary.cUtil;
import com.me.mseotsanyana.expandablelayoutlibrary.cExpandableLayout;
import com.me.mseotsanyana.mande.BLL.model.logframe.cImpactModel;
import com.me.mseotsanyana.mande.PL.ui.listeners.logframe.iViewImpactListener;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.UTIL.cConstant;
import com.me.mseotsanyana.mande.UTIL.cFontManager;
import com.me.mseotsanyana.placeholderview.annotationlibrary.Click;
import com.me.mseotsanyana.placeholderview.annotationlibrary.Layout;
import com.me.mseotsanyana.placeholderview.annotationlibrary.Resolve;
import com.me.mseotsanyana.placeholderview.annotationlibrary.View;
import com.me.mseotsanyana.placeholderview.annotationlibrary.expand.ChildPosition;
import com.me.mseotsanyana.placeholderview.annotationlibrary.expand.ParentPosition;

import java.text.SimpleDateFormat;

@Layout(R.layout.impact_outcome_cardview)
public class cImpactBodyView {
    private static final String TAG = cImpactBodyView.class.getSimpleName();
    private static SimpleDateFormat sdf = cConstant.SHORT_FORMAT_DATE;

    @ParentPosition
    public int parentPosition;

    @ChildPosition
    public int childPosition;

    @View(R.id.cardView)
    public CardView cardView;

    @View(R.id.textViewNameCaption)
    public TextView textViewNameCaption;

    @View(R.id.textViewName)
    public TextView textViewName;

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
            "Child Impacts",
            "Outcomes",
            "Questions",
            "Assumptions/Risks"
    };

    private int[] bmb_imageid = {
            R.drawable.dashboard_impact,
            R.drawable.dashboard_outcome,
            R.drawable.dashboard_question,
            R.drawable.dashboard_risk
    };

    private Context context;
    private iViewImpactListener listener;
    private cImpactModel impactModel;

    private String impact;
    private String description;
    private String startDate;
    private String endDate;

    public cImpactBodyView(Context context, iViewImpactListener listener,
                           cImpactModel impactModel) {
        this.context = context;
        this.listener = listener;
        this.impactModel = impactModel;
        this.impact = impactModel.getName();
        this.description = impactModel.getDescription();
        this.startDate = sdf.format(impactModel.getStartDate());
        this.endDate = sdf.format(impactModel.getEndDate());
    }

    @Resolve
    public void onResolved() {
        textViewNameCaption.setText(R.string.impact_caption);
        textViewName.setText(impact);
        textViewDescription.setText(description);
        textViewStartDate.setText(startDate);
        textViewEndDate.setText(endDate);

        /* collapse and expansion of the details */
        this.textViewDetailIcon.setTypeface(null, Typeface.NORMAL);
        this.textViewDetailIcon.setTypeface(cFontManager.getTypeface(context,
                cFontManager.FONTAWESOME));
        this.textViewDetailIcon.setTextColor(context.getColor(R.color.colorAccent));
        this.textViewDetailIcon.setText(context.getResources().getString(R.string.fa_angle_down));

        /* icon for saving updated record */
        this.textViewUpdateIcon.setTypeface(null, Typeface.NORMAL);
        this.textViewUpdateIcon.setTypeface(
                cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
        this.textViewUpdateIcon.setTextColor(context.getColor(R.color.colorAccent));
        this.textViewUpdateIcon.setText(context.getResources().getString(R.string.fa_update));

        /* icon for deleting a record */
        this.textViewDeleteIcon.setTypeface(null, Typeface.NORMAL);
        this.textViewDeleteIcon.setTypeface(
                cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
        this.textViewDeleteIcon.setTextColor(context.getColor(R.color.colorAccent));
        this.textViewDeleteIcon.setText(context.getResources().getString(R.string.fa_delete));

        /* icon for syncing a record */
        this.textViewSyncIcon.setTypeface(null, Typeface.NORMAL);
        this.textViewSyncIcon.setTypeface(
                cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
        this.textViewSyncIcon.setTextColor(context.getColor(R.color.colorAccent));
        this.textViewSyncIcon.setText(context.getResources().getString(R.string.fa_sync));

        /* icon for bmb menu */
        this.bmbMenu.clearBuilders();
        this.bmbMenu.setPiecePlaceEnum(cPiecePlaceEnum.DOT_4_1);
        this.bmbMenu.setButtonPlaceEnum(cButtonPlaceEnum.SC_4_1);
        for (int i = 0; i < this.bmbMenu.getPiecePlaceEnum().pieceNumber(); i++) {
            cTextOutsideCircleButton.Builder builder = new cTextOutsideCircleButton
                    .Builder()
                    .isRound(false)
                    .shadowCornerRadius(cUtil.dp2px(20))
                    .buttonCornerRadius(cUtil.dp2px(20))
                    .normalColor(Color.LTGRAY)
                    .pieceColor(context.getColor(R.color.colorAccent))
                    .normalImageRes(bmb_imageid[i])
                    .normalText(bmb_caption[i])
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
                            /* when the boom-button is clicked. */
                            listener.onClickBMBImpact(index,  impactModel.getImpactID());
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
        listener.onClickUpdateImpact(childPosition, impactModel);
    }

    @Click(R.id.textViewDeleteIcon)
    void onDeleteIconClick(){
        listener.onClickDeleteImpact(childPosition, impactModel.getImpactID());
    }

    @Click(R.id.textViewSyncIcon)
    void onSyncIconClick(){
        listener.onClickSyncImpact(childPosition, impactModel);
    }

    public void setPlaceHolderViewImpactListener(iViewImpactListener listener) {
        this.listener = listener;
    }
}