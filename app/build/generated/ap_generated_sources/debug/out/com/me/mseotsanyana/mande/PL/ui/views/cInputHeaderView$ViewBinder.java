package com.me.mseotsanyana.mande.PL.ui.views;

import android.view.View;
import android.widget.TextView;
import com.me.mseotsanyana.placeholderview.annotationlibrary.Keep;
import com.me.mseotsanyana.placeholderviewlibrary.$.R;
import com.me.mseotsanyana.placeholderviewlibrary.ViewBinder;
import java.lang.Override;

@Keep
public class cInputHeaderView$ViewBinder extends ViewBinder<cInputHeaderView, View> {
  public cInputHeaderView$ViewBinder(cInputHeaderView resolver) {
    super(resolver, R.layout.cInputHeaderView, false);
  }

  @Override
  protected void resolveView(cInputHeaderView resolver) {
    resolver.onResolved();
  }

  @Override
  protected void recycleView() {
    cInputHeaderView resolver = getResolver();
  }

  @Override
  protected void unbind() {
    cInputHeaderView resolver = getResolver();
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
  protected void bindViewPosition(cInputHeaderView resolver, int position) {
  }

  @Override
  protected void bindViews(cInputHeaderView resolver, View itemView) {
    resolver.textViewHeading = (TextView)itemView.findViewById(R.id.textViewHeading);
    resolver.toggleIcon = (TextView)itemView.findViewById(R.id.toggleIcon);
  }

  @Override
  protected void bindClick(final cInputHeaderView resolver, View itemView) {
  }

  @Override
  protected void bindLongClick(final cInputHeaderView resolver, View itemView) {
  }
}
