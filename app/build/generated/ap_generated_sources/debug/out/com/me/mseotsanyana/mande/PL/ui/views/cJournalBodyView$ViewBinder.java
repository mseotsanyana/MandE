package com.me.mseotsanyana.mande.PL.ui.views;

import android.view.View;
import android.widget.TextView;
import com.me.mseotsanyana.placeholderview.annotationlibrary.Keep;
import com.me.mseotsanyana.placeholderviewlibrary.$.R;
import com.me.mseotsanyana.placeholderviewlibrary.ViewBinder;
import java.lang.Override;

@Keep
public class cJournalBodyView$ViewBinder extends ViewBinder<cJournalBodyView, View> {
  public cJournalBodyView$ViewBinder(cJournalBodyView resolver) {
    super(resolver, R.layout.cJournalBodyView, false);
  }

  @Override
  protected void resolveView(cJournalBodyView resolver) {
    resolver.onResolved();
  }

  @Override
  protected void recycleView() {
    cJournalBodyView resolver = getResolver();
  }

  @Override
  protected void unbind() {
    cJournalBodyView resolver = getResolver();
    boolean nullable = isNullable();
    if (resolver != null && nullable) {
      resolver.appCompatTextViewDateIcon = null;
      resolver.appCompatTextViewDate = null;
      resolver.appCompatTextViewDescription = null;
      resolver.appCompatTextViewAmount = null;
      resolver.appCompatTextViewActionIcon = null;
      setResolver(null);
      setAnimationResolver(null);
    }
  }

  @Override
  protected void bindViewPosition(cJournalBodyView resolver, int position) {
  }

  @Override
  protected void bindViews(cJournalBodyView resolver, View itemView) {
    resolver.appCompatTextViewDateIcon = (TextView)itemView.findViewById(R.id.appCompatTextViewDateIcon);
    resolver.appCompatTextViewDate = (TextView)itemView.findViewById(R.id.appCompatTextViewDate);
    resolver.appCompatTextViewDescription = (TextView)itemView.findViewById(R.id.appCompatTextViewDescription);
    resolver.appCompatTextViewAmount = (TextView)itemView.findViewById(R.id.appCompatTextViewAmount);
    resolver.appCompatTextViewActionIcon = (TextView)itemView.findViewById(R.id.appCompatTextViewActionIcon);
  }

  @Override
  protected void bindClick(final cJournalBodyView resolver, View itemView) {
  }

  @Override
  protected void bindLongClick(final cJournalBodyView resolver, View itemView) {
  }
}
