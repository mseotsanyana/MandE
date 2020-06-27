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
import com.me.mseotsanyana.bmblibrary.cUtil;
import com.me.mseotsanyana.expandablelayoutlibrary.cExpandableLayout;
import com.me.mseotsanyana.mande.DAL.model.logframe.cQuestionModel;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.UTIL.cFontManager;
import com.me.mseotsanyana.placeholderview.annotationlibrary.Layout;
import com.me.mseotsanyana.placeholderview.annotationlibrary.Resolve;
import com.me.mseotsanyana.placeholderview.annotationlibrary.View;
import com.me.mseotsanyana.placeholderview.annotationlibrary.expand.ChildPosition;
import com.me.mseotsanyana.placeholderview.annotationlibrary.expand.ParentPosition;

@Layout(R.layout.impact_outcome_cardview)
public class cQuestionBodyView {
    private static final String TAG = cQuestionBodyView.class.getSimpleName();

    @ParentPosition
    public int mParentPosition;

    @ChildPosition
    public int mChildPosition;

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
    public com.me.mseotsanyana.bmblibrary.cBoomMenuButton bmbMenu;

    private final String[] bmb_caption = {
            "Indicators",
            "Responses",
            "Questionnaires"
    };

    private int[] bmb_imageid = {
            R.drawable.dashboard_indicator,
            R.drawable.dashboard_response,
            R.drawable.dashboard_question,
    };

    private Context context;
    private String name;
    private String description;

    public cQuestionBodyView(Context context, cQuestionModel questionModel) {
        this.context = context;
        this.name = questionModel.getName();
        this.description = questionModel.getDescription();
    }

    @Resolve
    public void onResolved() {
        textViewNameCaption.setText(R.string.question_caption);
        textViewName.setText(name);
        textViewDescription.setText(description);

        /* collapse and expansion of the details */
        this.textViewDetailIcon.setTypeface(null, Typeface.NORMAL);
        this.textViewDetailIcon.setTypeface(cFontManager.getTypeface(context,
                cFontManager.FONTAWESOME));
        this.textViewDetailIcon.setTextColor(context.getColor(R.color.colorAccent));
        this.textViewDetailIcon.setText(context.getResources().getString(R.string.fa_angle_down));
        this.textViewDetailIcon.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                /*if (!(this.expandableLayout.isExpanded())) {
                    this.textViewDetailIcon.setText(context.getResources().getString(R.string.fa_angle_up));
                } else {
                    this.textViewDetailIcon.setText(context.getResources().getString(R.string.fa_angle_down));
                }

                this.expandableLayout.toggle();*/
            }
        });

        /* icon for saving updated record */
        this.textViewUpdateIcon.setTypeface(null, Typeface.NORMAL);
        this.textViewUpdateIcon.setTypeface(
                cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
        this.textViewUpdateIcon.setTextColor(context.getColor(R.color.colorAccent));
        this.textViewUpdateIcon.setText(context.getResources().getString(R.string.fa_update));
        this.textViewUpdateIcon.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                //IPH.logFrameListener.onClickUpdateLogFrame(position,
                //        parentLogFrameModel);
            }
        });

        /* icon for deleting a record */
        this.textViewDeleteIcon.setTypeface(null, Typeface.NORMAL);
        this.textViewDeleteIcon.setTypeface(
                cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
        this.textViewDeleteIcon.setTextColor(context.getColor(R.color.colorAccent));
        this.textViewDeleteIcon.setText(context.getResources().getString(R.string.fa_delete));
        this.textViewDeleteIcon.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                //IPH.logFrameListener.onClickDeleteLogFrame(position,
                //       parentLogFrameModel.getLogFrameID());
            }
        });

        /* icon for syncing a record */
        this.textViewSyncIcon.setTypeface(null, Typeface.NORMAL);
        this.textViewSyncIcon.setTypeface(
                cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
        this.textViewSyncIcon.setTextColor(context.getColor(R.color.colorAccent));
        this.textViewSyncIcon.setText(context.getResources().getString(R.string.fa_sync));
        this.textViewSyncIcon.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                //IPH.logFrameListener.onClickSyncLogFrame(position,
                //       parentLogFrameModel);
            }
        });

        this.bmbMenu.clearBuilders();
        this.bmbMenu.setPiecePlaceEnum(cPiecePlaceEnum.DOT_3_3);
        this.bmbMenu.setButtonPlaceEnum(cButtonPlaceEnum.SC_3_3);
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
                            //IPH.logFrameListener.onClickBoomMenu(index);
                        }
                    });
            this.bmbMenu.addBuilder(builder);
        }
        cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.list_odd));
    }
}