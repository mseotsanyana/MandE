package com.me.mseotsanyana.mande.PL.ui.fragments.common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.me.mseotsanyana.mande.PL.ui.listeners.common.iViewPagerHeightListener;

public class cCustomViewPager extends ViewPager {
    private static final String TAG = cCustomViewPager.class.getSimpleName();
    private View currentView;

    private iViewPagerHeightListener onViewPagerHeightListener;

    public cCustomViewPager(Context context) {
        super(context);
    }

    public cCustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setOnCustomViewListener(iViewPagerHeightListener onViewPagerHeightListener){
        this.onViewPagerHeightListener = onViewPagerHeightListener;
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (currentView == null) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        int height = 0;
        currentView.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        height = currentView.getMeasuredHeight();
        //if (h > height) height = h;
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);

        int h = (int) dpToPixel(getContext(), height);

        //Log.d(TAG, "HEIGHT = "+h);
        onViewPagerHeightListener.onViewPagerHeightUpdate(height);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    public static float dpToPixel(Context c, float dp) {
        float density = c.getResources().getDisplayMetrics().density;
        float pixel = dp * density;
        return pixel;
    }

    public void measureCurrentView(View view) {
        this.currentView = view;
        requestLayout();
    }

    public int measureFragment(View view) {
        if (view == null)
            return 0;

        view.measure(0, 0);
        return view.getMeasuredHeight();
    }
}
