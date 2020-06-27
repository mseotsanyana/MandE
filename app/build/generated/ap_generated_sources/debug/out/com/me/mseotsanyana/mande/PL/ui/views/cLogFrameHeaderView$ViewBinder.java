package com.me.mseotsanyana.mande.PL.ui.views;

import android.view.View;
import android.widget.TextView;
import com.me.mseotsanyana.placeholderview.annotationlibrary.Keep;
import com.me.mseotsanyana.placeholderviewlibrary.$.R;
import com.me.mseotsanyana.placeholderviewlibrary.ViewBinder;
import java.lang.Override;

@Keep
public class cLogFrameHeaderView$ViewBinder extends ViewBinder<cLogFrameHeaderView, View> {
  public cLogFrameHeaderView$ViewBinder(cLogFrameHeaderView resolver) {
    super(resolver, R.layout.cLogFrameHeaderView, false);
  }

  @Override
  protected void resolveView(cLogFrameHeaderView resolver) {
    resolver.onResolved();
  }

  @Override
  protected void recycleView() {
    cLogFrameHeaderView resolver = getResolver();
  }

  @Override
  protected void unbind() {
    cLogFrameHeaderView resolver = getResolver();
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
}
