package com.me.mseotsanyana.mande.PL.ui.adapters.logframe;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.me.mseotsanyana.mande.UTIL.BLL.cGoalDomain;
import com.me.mseotsanyana.mande.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mseotsanyana on 2016/12/27.
 */

public class cGoalAdapter_old extends RecyclerView.Adapter<cGoalAdapter_old.cGoalViewHolder> {

    private List<cGoalDomain> goalList = new ArrayList<>();

    @Override
    public int getItemCount() {
        return goalList.size();
    }

    @Override
    public cGoalAdapter_old.cGoalViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.fragment_goal_card, viewGroup, false);

        return new cGoalAdapter_old.cGoalViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(cGoalAdapter_old.cGoalViewHolder goalViewHolder, int position) {

    }

    public class cGoalViewHolder extends RecyclerView.ViewHolder {
        public cGoalViewHolder(final View view) {
            super(view);


        }

    }
}
