package com.me.mseotsanyana.mande.PL.ui.views;

import android.content.Context;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.me.mseotsanyana.mande.DAL.model.logframe.cQuestionModel;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.UTIL.cConstant;
import com.me.mseotsanyana.placeholderview.annotationlibrary.Layout;
import com.me.mseotsanyana.placeholderview.annotationlibrary.Resolve;
import com.me.mseotsanyana.placeholderview.annotationlibrary.View;
import com.me.mseotsanyana.placeholderview.annotationlibrary.expand.ChildPosition;
import com.me.mseotsanyana.placeholderview.annotationlibrary.expand.ParentPosition;

import java.text.SimpleDateFormat;

@Layout(R.layout.question_less_detail_list)
public class cQuestionBodyView {
    private static final String TAG = cQuestionBodyView.class.getSimpleName();
    private static SimpleDateFormat sdf = cConstant.SHORT_FORMAT_DATE;

    @ParentPosition
    public int mParentPosition;

    @ChildPosition
    public int mChildPosition;

    @View(R.id.cardViewQuestion)
    public CardView cardViewQuestion;

    @View(R.id.tableLayoutQuestion)
    public TableLayout tableLayoutQuestion;

    @View(R.id.textViewLabel)
    public TextView textViewLabel;

    @View(R.id.textViewQuestion)
    public TextView textViewQuestion;

    @View(R.id.textViewQuestionType)
    public TextView textViewQuestionType;

    @View(R.id.textViewDescription)
    public TextView textViewDescription;

    @View(R.id.textViewGroup)
    public TextView textViewGroup;

    @View(R.id.textViewGroupDescription)
    public TextView textViewGroupDescription;

    @View(R.id.textViewStartDate)
    public TextView textViewStartDate;

    @View(R.id.textViewEndDate)
    public TextView textViewEndDate;

    private Context context;

    private int label;
    private String question;
    private String questionType;
    private String description;
    private String group;
    private String groupDescription;
    private String startDate;
    private String endDate;

    public cQuestionBodyView(Context context, cQuestionModel questionModel) {
        this.context = context;
        this.label = questionModel.getLabel();
        this.question = questionModel.getQuestion();
        this.questionType = questionModel.getQuestionTypeModel().getName();
        this.description = questionModel.getQuestionTypeModel().getDescription();
        if(questionModel.getQuestionGroupID() != 0) {
            this.group = questionModel.getQuestionGroupingModel().getName();
            this.groupDescription = questionModel.getQuestionGroupingModel().getDescription();
        }else {
            this.group = "None";
            this.groupDescription="None";
        }
        this.startDate = " "+sdf.format(questionModel.getStartDate())+" ";
        this.endDate = sdf.format(questionModel.getEndDate());
    }

    @Resolve
    public void onResolved() {
        textViewLabel.setText(label);
        textViewQuestion.setText(question);
        textViewQuestionType.setText(questionType);
        textViewDescription.setText(description);
        textViewGroup.setText(group);
        textViewGroupDescription.setText(groupDescription);
        textViewStartDate.setText(startDate);
        textViewEndDate.setText(endDate);
    }
}