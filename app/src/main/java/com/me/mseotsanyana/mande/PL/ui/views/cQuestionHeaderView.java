package com.me.mseotsanyana.mande.PL.ui.views;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.UTIL.cFontManager;
import com.me.mseotsanyana.placeholderview.annotationlibrary.Layout;
import com.me.mseotsanyana.placeholderview.annotationlibrary.Resolve;
import com.me.mseotsanyana.placeholderview.annotationlibrary.View;
import com.me.mseotsanyana.placeholderview.annotationlibrary.expand.Collapse;
import com.me.mseotsanyana.placeholderview.annotationlibrary.expand.Expand;
import com.me.mseotsanyana.placeholderview.annotationlibrary.expand.Parent;
import com.me.mseotsanyana.placeholderview.annotationlibrary.expand.ParentPosition;
import com.me.mseotsanyana.placeholderview.annotationlibrary.expand.SingleTop;
import com.me.mseotsanyana.placeholderview.annotationlibrary.expand.Toggle;

@Parent
@SingleTop
@Layout(R.layout.question_placeholderview_heading)
public class cQuestionHeaderView {

    @View(R.id.textViewHeading)
    public TextView textViewHeading;

    @View(R.id.toggleIcon)
    public TextView toggleIcon;

    @Toggle(R.id.toggleView)
    public LinearLayout toggleView;

    @ParentPosition
    public int mParentPosition;

    private Context context;
    private String heading;

    public cQuestionHeaderView(Context context, String heading) {
        this.context = context;
        this.heading = heading;
    }

    @Resolve
    public void onResolved() {
        toggleIcon.setTypeface(null, Typeface.NORMAL);
        toggleIcon.setTypeface(cFontManager.getTypeface(this.context, cFontManager.FONTAWESOME));
        //toggleIcon.setTextColor(context.getColor(R.color.white));
        toggleIcon.setText(context.getResources().getString(R.string.fa_angle_up));

        textViewHeading.setText(this.heading);
    }

    @Expand
    public void onExpand() {
        toggleIcon.setTypeface(null, Typeface.NORMAL);
        toggleIcon.setTypeface(cFontManager.getTypeface(this.context, cFontManager.FONTAWESOME));
        //toggleIcon.setTextColor(context.getColor(R.color.white));
        toggleIcon.setText(context.getResources().getString(R.string.fa_angle_down));
    }

    @Collapse
    public void onCollapse() {
        toggleIcon.setTypeface(null, Typeface.NORMAL);
        toggleIcon.setTypeface(cFontManager.getTypeface(this.context, cFontManager.FONTAWESOME));
        //toggleIcon.setTextColor(context.getColor(R.color.white));
        toggleIcon.setText(context.getResources().getString(R.string.fa_angle_up));
    }
}