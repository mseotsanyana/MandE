package com.me.mseotsanyana.mande.PL.ui.adapters.logframe;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.me.mseotsanyana.mande.BLL.model.logframe.cOutcomeModel;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.UTIL.cConstant;
import com.me.mseotsanyana.mande.UTIL.cFontManager;

import java.text.SimpleDateFormat;

/**
 * Created by mseotsanyana on 2017/02/27.
 */

public class cOutcomeAuxAdapter extends RecyclerView.Adapter<cOutcomeAuxAdapter.cOutcomeViewHolder> {
    private static String TAG = cOutcomeAuxAdapter.class.getSimpleName();
    private static SimpleDateFormat sdf = cConstant.SHORT_FORMAT_DATE;

   // private final iOutcomePresenter.View outcomePresenterView;
   // private final iOutputPresenter.View outputPresenterView;

    private cOutcomeModel[] outcomeModels;
    private Context context;

    public cOutcomeAuxAdapter(Context context, cOutcomeModel[] outcomeModels) {
        this.context = context;
        this.outcomeModels = outcomeModels;

        //this.outcomePresenterView = outcomePresenterView;
        //this.outputPresenterView = outputPresenterView;
    }

    @NonNull
    @Override
    public cOutcomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.outcome_aux_cardview,
                parent, false);
        return new cOutcomeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull cOutcomeViewHolder OH, int position) {
        cOutcomeModel outcomeModel = outcomeModels[position];

        //final int parentBackgroundColor = (position % 2 == 0) ? R.color.list_even :
        //        R.color.list_odd;

        OH.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.list_odd));

        OH.textViewName.setText(outcomeModel.getName());
        OH.textViewDescription.setText(outcomeModel.getDescription());
        OH.textViewStartDate.setText(sdf.format(outcomeModel.getStartDate()));
        OH.textViewEndDate.setText(sdf.format(outcomeModel.getEndDate()));

        /* icon for syncing a record */
        OH.textViewSyncIcon.setTypeface(null, Typeface.NORMAL);
        OH.textViewSyncIcon.setTypeface(
                cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
        OH.textViewSyncIcon.setTextColor(context.getColor(R.color.colorAccent));
        OH.textViewSyncIcon.setText(context.getResources().getString(R.string.fa_sync));
        OH.textViewSyncIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //IPH.logFrameListener.onClickSyncLogFrame(position,
                //       parentLogFrameModel);
            }
        });

        /* icon for deleting a record */
        OH.textViewDeleteIcon.setTypeface(null, Typeface.NORMAL);
        OH.textViewDeleteIcon.setTypeface(
                cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
        OH.textViewDeleteIcon.setTextColor(context.getColor(R.color.colorAccent));
        OH.textViewDeleteIcon.setText(context.getResources().getString(R.string.fa_delete));
        OH.textViewDeleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //IPH.logFrameListener.onClickDeleteLogFrame(position,
                //       parentLogFrameModel.getLogFrameID());
            }
        });

        /* icon for saving updated record */
        OH.textViewUpdateIcon.setTypeface(null, Typeface.NORMAL);
        OH.textViewUpdateIcon.setTypeface(
                cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
        OH.textViewUpdateIcon.setTextColor(context.getColor(R.color.colorAccent));
        OH.textViewUpdateIcon.setText(context.getResources().getString(R.string.fa_update));
        OH.textViewUpdateIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //IPH.logFrameListener.onClickUpdateLogFrame(position,
                //        parentLogFrameModel);
            }
        });
    }

    @Override
    public int getItemCount() {
        return outcomeModels.length;
    }


    public static class cOutcomeViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;

        private AppCompatTextView textViewName;
        private AppCompatTextView textViewDescription;
        private AppCompatTextView textViewStartDate;
        private AppCompatTextView textViewEndDate;

        private AppCompatTextView textViewSyncIcon;
        private AppCompatTextView textViewDeleteIcon;
        private AppCompatTextView textViewUpdateIcon;

        private View viewHolder;

        private cOutcomeViewHolder(final View viewHolder) {
            super(viewHolder);
            this.viewHolder = viewHolder;

            this.cardView = viewHolder.findViewById(R.id.cardView);

            this.textViewName = viewHolder.findViewById(R.id.textViewName);
            this.textViewDescription = viewHolder.findViewById(R.id.textViewDescription);
            this.textViewStartDate = viewHolder.findViewById(R.id.textViewStartDate);
            this.textViewEndDate = viewHolder.findViewById(R.id.textViewEndDate);
            this.textViewSyncIcon = viewHolder.findViewById(R.id.textViewSyncIcon);
            this.textViewDeleteIcon = viewHolder.findViewById(R.id.textViewDeleteIcon);
            this.textViewUpdateIcon = viewHolder.findViewById(R.id.textViewUpdateIcon);
        }

        public void setPaddingLeft(int paddingLeft) {
            viewHolder.setPadding(paddingLeft, 0, 0, 0);
        }
    }
}
