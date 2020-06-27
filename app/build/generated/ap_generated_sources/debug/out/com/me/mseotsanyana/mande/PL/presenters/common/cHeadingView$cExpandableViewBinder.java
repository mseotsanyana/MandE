package com.me.mseotsanyana.mande.PL.presenters.common;

import android.view.View;
import android.widget.TextView;
import com.me.mseotsanyana.placeholderview.annotationlibrary.Keep;
import com.me.mseotsanyana.placeholderviewlibrary.$.R;
import com.me.mseotsanyana.placeholderviewlibrary.cExpandableViewBinder;
import java.lang.Deprecated;
import java.lang.Override;

@Keep
public class cHeadingView$cExpandableViewBinder extends cExpandableViewBinder<cHeadingView, View> {
  public cHeadingView$cExpandableViewBinder(cHeadingView resolver) {
    super(resolver, R.layout.cHeadingView, false, true, true);
  }

  @Override
  protected void resolveView(cHeadingView resolver) {
    resolver.onResolved();
  }

  @Override
  protected void recycleView() {
    cHeadingView resolver = getResolver();
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
  protected void bindViewPosition(cHeadingView resolver, int position) {
  }

  @Override
  protected void bindViews(cHeadingView resolver, View itemView) {
    resolver.textViewHeading = (TextView)itemView.findViewById(R.id.textViewHeading);
    resolver.toggleIcon = (TextView)itemView.findViewById(R.id.toggleIcon);
  }

  @Override
  protected void bindClick(final cHeadingView resolver, View itemView) {
  }

  @Override
  protected void bindLongClick(final cHeadingView resolver, View itemView) {
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
  protected void bindToggle(final cHeadingView resolver, View itemView) {
    itemView.findViewById(R.id.toggleView).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (isExpanded()) collapse();
        else expand();
      }
    });
  }

  @Override
  protected void bindExpand(cHeadingView resolver) {
    resolver.onExpand();
  }

  @Override
  protected void bindCollapse(cHeadingView resolver) {
    resolver.onCollapse();
  }
}
