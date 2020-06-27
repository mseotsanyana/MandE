package com.me.mseotsanyana.mande.PL.presenters.common;

import android.view.View;
import android.widget.TextView;
import com.me.mseotsanyana.placeholderview.annotationlibrary.Keep;
import com.me.mseotsanyana.placeholderviewlibrary.$.R;
import com.me.mseotsanyana.placeholderviewlibrary.ViewBinder;
import java.lang.Override;

@Keep
public class cHeadingView$ViewBinder extends ViewBinder<cHeadingView, View> {
  public cHeadingView$ViewBinder(cHeadingView resolver) {
    super(resolver, R.layout.cHeadingView, false);
  }

  @Override
  protected void resolveView(cHeadingView resolver) {
    resolver.onResolved();
  }

  @Override
  protected void recycleView() {
    cHeadingView resolver = getResolver();
  }

  @Override
  protected void unbind() {
    cHeadingView resolver = getResolver();
    boolean nullable = isNullable();
    if (resolver != null && nullable) {
      resolver.textViewHeading = null;
      resolver.toggleIcon = null;
      resolver.toggleView = null;
      setResolver(null);
      setAnimationResolver(null);
    }
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
}
