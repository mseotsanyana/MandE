package com.me.mseotsanyana.mande.PL.ui.views;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

import com.me.mseotsanyana.mande.DAL.model.session.cUserModel;
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

import java.text.SimpleDateFormat;

import static com.me.mseotsanyana.mande.R.color.gray;

@Layout(R.layout.user_short_list)
public class cUserBodyView {
    private static final String TAG = cUserBodyView.class.getSimpleName();
    private static SimpleDateFormat sdf = cConstant.SHORT_FORMAT_DATE;

    @ParentPosition
    public int parentPosition;

    @ChildPosition
    public int childPosition;

    @View(R.id.circleImageViewUser)
    public de.hdodenhof.circleimageview.CircleImageView circleImageViewUser;

    @View(R.id.textViewName)
    public TextView textViewName;

    @View(R.id.textViewPhone)
    public TextView textViewPhone;

    @View(R.id.textViewEmail)
    public TextView textViewEmail;

    @View(R.id.textViewDeleteIcon)
    public TextView textViewDeleteIcon;

    private Context context;
    private iViewInputListener listener;
    private cUserModel userModel;

    private String name;
    private String surname;
    private String phone;
    private String email;


    public cUserBodyView(Context context, iViewInputListener listener,
                         cUserModel userModel) {
        this.context = context;
        this.listener = listener;
        this.userModel = userModel;
        this.name = userModel.getName();
        this.surname = userModel.getSurname();
        this.phone = userModel.getPhone();
        this.email = userModel.getEmail();
    }

    @Resolve
    public void onResolved() {
        circleImageViewUser.setImageResource(gray);
        textViewName.setText(name +" "+surname);
        textViewPhone.setText(phone);
        textViewEmail.setText(email);

        /* icon for deleting a record */
        this.textViewDeleteIcon.setTypeface(null, Typeface.NORMAL);
        this.textViewDeleteIcon.setTypeface(
                cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
        this.textViewDeleteIcon.setTextColor(context.getColor(R.color.colorAccent));
        this.textViewDeleteIcon.setText(context.getResources().getString(R.string.fa_delete));

    }

    @Click(R.id.textViewDeleteIcon)
    void onDeleteIconClick(){
        //listener.onClickDeleteInput(childPosition, userModel.getInputID());
    }

    public void setPlaceHolderViewInputListener(iViewInputListener listener) {
        this.listener = listener;
    }
}