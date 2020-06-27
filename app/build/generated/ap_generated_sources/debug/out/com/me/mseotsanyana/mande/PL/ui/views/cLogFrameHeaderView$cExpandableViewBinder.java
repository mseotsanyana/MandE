package com.me.mseotsanyana.mande.PL.ui.views;

import android.view.View;
import android.widget.TextView;
import com.me.mseotsanyana.placeholderview.annotationlibrary.Keep;
import com.me.mseotsanyana.placeholderviewlibrary.$.R;
import com.me.mseotsanyana.placeholderviewlibrary.cExpandableViewBinder;
import java.lang.Deprecated;
import java.lang.Override;

@Keep
public class cLogFrameHeaderView$cExpandableViewBinder extends cExpandableViewBinder<cLogFrameHeaderView, View> {
  public cLogFrameHeaderView$cExpandableViewBinder(cLogFrameHeaderView resolver) {
    super(resolver, R.layout.cLogFrameHeaderView, false, true, true);
  }

  @Override
  protected void resolveView(cLogFrameHeaderView resolver) {
    resolver.onResolved();
  }

  @Override
  protected void recycleView() {
    cLogFrameHeaderView resolver = getResolver();
  }

  @Deprecated
  @Override
  protected void unbind() {
  }

  @Deprecated
  @Override
  protected void bindAnimation(int deviceWidth, int deviceHeight, View view) {
  }

  @Override
  protected void bindViewPosition(cLogFrameHeaderView resolver, int position) {
  }

  @Override
  protected void bindViews(cLogFrameHeaderView resolver, View itemView) {
    resolver.textViewHeading = (TextView)itemView.findViewById(R.id.textViewHeading);
    resolver.toggleIcon = (TextView)itemView.findViewById(R.id.toggleIcon);
  }

  @Override
  protected void bindClick(final cLogFrameHeaderView resolver, View itemView) {
  }

  @Override
  protected void bindLongClick(final cLogFrameHeaderView resolver, View itemView) {
  }

  @Override
  protected void bindParentPosition(int position) {
    getResolver().mParentPosition = position;
    setParentPosition(position);
  }

  @Override
  protected void bindChildPosition(int position) {
  }

  @Override
  protected void bindToggle(final cLogFrameHeaderView resolver, View itemView) {
    itemView.findViewById(R.id.toggleView).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (isExpanded()) collapse();
        else expand();
      }
    });
  }

  @Override
  protected void bindExpand(cLogFrameHeaderView resolver) {
    resolver.onExpand();
  }

  @Override
  protected void bindCollapse(cLogFrameHeaderView resolver) {
    resolver.onCollapse();
  }
}
