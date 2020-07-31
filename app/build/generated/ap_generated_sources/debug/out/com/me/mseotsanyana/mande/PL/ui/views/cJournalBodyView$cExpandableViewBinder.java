package com.me.mseotsanyana.mande.PL.ui.views;

import android.view.View;
import android.widget.TextView;
import com.me.mseotsanyana.placeholderview.annotationlibrary.Keep;
import com.me.mseotsanyana.placeholderviewlibrary.$.R;
import com.me.mseotsanyana.placeholderviewlibrary.cExpandableViewBinder;
import java.lang.Deprecated;
import java.lang.Override;

@Keep
public class cJournalBodyView$cExpandableViewBinder extends cExpandableViewBinder<cJournalBodyView, View> {
  public cJournalBodyView$cExpandableViewBinder(cJournalBodyView resolver) {
    super(resolver, R.layout.cJournalBodyView, false, false, false);
  }

  @Override
  protected void resolveView(cJournalBodyView resolver) {
    resolver.onResolved();
  }

  @Override
  protected void recycleView() {
    cJournalBodyView resolver = getResolver();
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

  @Override
  protected void bindParentPosition(int position) {
    getResolver().parentPosition = position;
    setParentPosition(position);
  }

  @Override
  protected void bindChildPosition(int position) {
    getResolver().childPosition = position;
    setChildPosition(position);
  }

  @Override
  protected void bindToggle(final cJournalBodyView resolver, View itemView) {
    itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (isExpanded()) collapse();
        else expand();
      }
    });
  }

  @Override
  protected void bindExpand(cJournalBodyView resolver) {
  }

  @Override
  protected void bindCollapse(cJournalBodyView resolver) {
  }
}
