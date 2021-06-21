package com.me.mseotsanyana.mande.PL.ui.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;

import com.me.mseotsanyana.mande.BLL.model.session.cTeamModel;
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
@Layout(R.layout.session_team_cardview)
public class cTeamHeaderView {

    @View(R.id.textViewName)
    public TextView textViewName;

    @View(R.id.textViewDescription)
    public TextView textViewDescription;

    @View(R.id.textViewDetailIcon)
    public AppCompatTextView textViewDetailIcon;

    @View(R.id.textViewJoinIcon)
    public AppCompatTextView textViewJoinIcon;

    @View(R.id.textViewAddIcon)
    public AppCompatTextView textViewAddIcon;

    @View(R.id.textViewUpdateIcon)
    public AppCompatTextView textViewUpdateIcon;

    @View(R.id.textViewDeleteIcon)
    public AppCompatTextView textViewDeleteIcon;

    @ParentPosition
    public int parentPosition;

    private final Context context;
    private final String name;
    private final String description;

    public cTeamHeaderView(Context context, cTeamModel teamModel) {
        this.context = context;
        this.name = teamModel.getName();
        this.description = teamModel.getDescription();
    }

    @Resolve
    public void onResolved() {
        textViewName.setText(this.name);
        textViewDescription.setText(this.description);

        textViewDetailIcon.setTypeface(null, Typeface.NORMAL);
        textViewDetailIcon.setTypeface(cFontManager.getTypeface(this.context, cFontManager.FONTAWESOME));
        textViewDetailIcon.setText(context.getResources().getString(R.string.fa_angle_up));

        textViewJoinIcon.setTypeface(null, Typeface.NORMAL);
        textViewJoinIcon.setTypeface(cFontManager.getTypeface(this.context, cFontManager.FONTAWESOME));
        textViewJoinIcon.setText(context.getResources().getString(R.string.fa_join));

        textViewAddIcon.setTypeface(null, Typeface.NORMAL);
        textViewAddIcon.setTypeface(cFontManager.getTypeface(this.context, cFontManager.FONTAWESOME));
        textViewAddIcon.setText(context.getResources().getString(R.string.fa_create));

        textViewUpdateIcon.setTypeface(null, Typeface.NORMAL);
        textViewUpdateIcon.setTypeface(cFontManager.getTypeface(this.context, cFontManager.FONTAWESOME));
        textViewUpdateIcon.setText(context.getResources().getString(R.string.fa_update));

        textViewDeleteIcon.setTypeface(null, Typeface.NORMAL);
        textViewDeleteIcon.setTypeface(cFontManager.getTypeface(this.context, cFontManager.FONTAWESOME));
        textViewDeleteIcon.setText(context.getResources().getString(R.string.fa_delete));
    }

    @Expand
    public void onExpand() {
        textViewDetailIcon.setTypeface(null, Typeface.NORMAL);
        textViewDetailIcon.setTypeface(cFontManager.getTypeface(this.context, cFontManager.FONTAWESOME));
        textViewDetailIcon.setText(context.getResources().getString(R.string.fa_angle_down));
    }

    @Collapse
    public void onCollapse() {
        textViewDetailIcon.setTypeface(null, Typeface.NORMAL);
        textViewDetailIcon.setTypeface(cFontManager.getTypeface(this.context, cFontManager.FONTAWESOME));
        textViewDetailIcon.setText(context.getResources().getString(R.string.fa_angle_up));
    }
}