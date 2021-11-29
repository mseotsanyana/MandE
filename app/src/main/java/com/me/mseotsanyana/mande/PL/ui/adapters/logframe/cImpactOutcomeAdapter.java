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
import java.util.List;

/**
 * Created by mseotsanyana on 2017/02/27.
 */

public class cImpactOutcomeAdapter extends RecyclerView.Adapter<cImpactOutcomeAdapter.cOutcomeViewHolder> {
    //private static String TAG = cImpactOutcomeAdapter.class.getSimpleName();
    private static final SimpleDateFormat sdf = cConstant.SHORT_FORMAT_DATE;

    private final List<cOutcomeModel> outcomeModels;
    private final Context context;

    public cImpactOutcomeAdapter(Context context, List<cOutcomeModel> outcomeModels) {
        this.context = context;
        this.outcomeModels = outcomeModels;
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
        cOutcomeModel outcomeModel = outcomeModels.get(position);

        OH.cardView.setCardBackgroundColor(ContextCompat.getColor(context,
                R.color.list_odd));

        OH.textViewName.setText(outcomeModel.getName());
        OH.textViewDescription.setText(outcomeModel.getDescription());
        OH.textViewStartDate.setText(sdf.format(outcomeModel.getStartDate()));
        OH.textViewEndDate.setText(sdf.format(outcomeModel.getEndDate()));

        /* icon for deleting a record */
        OH.textViewDeleteIcon.setTypeface(null, Typeface.NORMAL);
        OH.textViewDeleteIcon.setTypeface(
                cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
        OH.textViewDeleteIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
        OH.textViewDeleteIcon.setText(context.getResources().getString(R.string.fa_delete));
        OH.textViewDeleteIcon.setOnClickListener(view -> {
            // IPH.logFrameListener.onClickDeleteLogFrame(position,
            //       parentLogFrameModel.getLogFrameID());
        });

        /* icon for saving updated record */
        OH.textViewUpdateIcon.setTypeface(null, Typeface.NORMAL);
        OH.textViewUpdateIcon.setTypeface(
                cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
        OH.textViewUpdateIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
        OH.textViewUpdateIcon.setText(context.getResources().getString(R.string.fa_update));
        OH.textViewUpdateIcon.setOnClickListener(view -> {
            /* IPH.logFrameListener.onClickUpdateLogFrame(position,
                    parentLogFrameModel);*/
        });
    }

    @Override
    public int getItemCount() {
        return outcomeModels.size();
    }


    public static class cOutcomeViewHolder extends RecyclerView.ViewHolder {
        private final CardView cardView;

        private final AppCompatTextView textViewName;
        private final AppCompatTextView textViewDescription;
        private final AppCompatTextView textViewStartDate;
        private final AppCompatTextView textViewEndDate;

        private final AppCompatTextView textViewDeleteIcon;
        private final AppCompatTextView textViewUpdateIcon;

        private final View viewHolder;

        private cOutcomeViewHolder(final View viewHolder) {
            super(viewHolder);
            this.viewHolder = viewHolder;

            this.cardView = viewHolder.findViewById(R.id.cardView);

            this.textViewName = viewHolder.findViewById(R.id.textViewName);
            this.textViewDescription = viewHolder.findViewById(R.id.textViewDescription);
            this.textViewStartDate = viewHolder.findViewById(R.id.textViewStartDate);
            this.textViewEndDate = viewHolder.findViewById(R.id.textViewEndDate);
            this.textViewDeleteIcon = viewHolder.findViewById(R.id.textViewDeleteIcon);
            this.textViewUpdateIcon = viewHolder.findViewById(R.id.textViewUpdateIcon);
        }

        public void setPaddingLeft(int paddingLeft) {
            viewHolder.setPadding(paddingLeft, 0, 0, 0);
        }
    }
}
