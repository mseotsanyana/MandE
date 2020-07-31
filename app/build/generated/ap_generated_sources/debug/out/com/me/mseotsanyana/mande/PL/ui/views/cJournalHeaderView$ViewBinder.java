package com.me.mseotsanyana.mande.PL.ui.views;

import android.view.View;
import android.widget.TextView;
import com.me.mseotsanyana.placeholderview.annotationlibrary.Keep;
import com.me.mseotsanyana.placeholderviewlibrary.$.R;
import com.me.mseotsanyana.placeholderviewlibrary.ViewBinder;
import java.lang.Override;

@Keep
public class cJournalHeaderView$ViewBinder extends ViewBinder<cJournalHeaderView, View> {
  public cJournalHeaderView$ViewBinder(cJournalHeaderView resolver) {
    super(resolver, R.layout.cJournalHeaderView, false);
  }

  @Override
  protected void resolveView(cJournalHeaderView resolver) {
    resolver.onResolved();
  }

  @Override
  protected void recycleView() {
    cJournalHeaderView resolver = getResolver();
  }

  @Override
  protected void unbind() {
    cJournalHeaderView resolver = getResolver();
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
  protected void bindViewPosition(cJournalHeaderView resolver, int position) {
  }

  @Override
  protected void bindViews(cJournalHeaderView resolver, View itemView) {
    resolver.textViewHeading = (TextView)itemView.findViewById(R.id.textViewHeading);
    resolver.toggleIcon = (TextView)itemView.findViewById(R.id.toggleIcon);
  }

  @Override
  protected void bindClick(final cJournalHeaderView resolver, View itemView) {
  }

  @Override
  protected void bindLongClick(final cJournalHeaderView resolver, View itemView) {
  }
}
