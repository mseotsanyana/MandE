package com.me.mseotsanyana.mande.UTILITY;

import android.view.View;

/**
 * Created by mseotsanyana on 2016/12/04.
 */

public class cRecycleViewMessage {
    private View cardView;
    private int position;

    public cRecycleViewMessage(View cardView, int position)
    {
        this.cardView = cardView;
        this.position = position;
    }

    public View getCardView() {
        return cardView;
    }

    public void setCardView(View cardView) {
        this.cardView = cardView;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
