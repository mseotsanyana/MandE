package com.me.mseotsanyana.mande.PL.ui.views;

import android.view.View;
import android.widget.TextView;
import com.me.mseotsanyana.placeholderview.annotationlibrary.Keep;
import com.me.mseotsanyana.placeholderviewlibrary.$.R;
import com.me.mseotsanyana.placeholderviewlibrary.cExpandableViewBinder;
import java.lang.Deprecated;
import java.lang.Override;

@Keep
public class cJournalHeaderView$cExpandableViewBinder extends cExpandableViewBinder<cJournalHeaderView, View> {
  public cJournalHeaderView$cExpandableViewBinder(cJournalHeaderView resolver) {
    super(resolver, R.layout.cJournalHeaderView, false, true, true);
  }

  @Override
  protected void resolveView(cJournalHeaderView resolver) {
    resolver.onResolved();
  }

  @Override
  protected void recycleView() {
    cJournalHeaderView resolver = getResolver();
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

  @Override
  protected void bindParentPosition(int position) {
    getResolver().mParentPosition = position;
    setParentPosition(position);
  }

  @Override
  protected void bindChildPosition(int position) {
  }

  @Override
  protected void bindToggle(final cJournalHeaderView resolver, View itemView) {
    itemView.findViewById(R.id.toggleView).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (isExpanded()) collapse();
        else expand();
      }
    });
  }

  @Override
  protected void bindExpand(cJournalHeaderView resolver) {
    resolver.onExpand();
  }

  @Override
  protected void bindCollapse(cJournalHeaderView resolver) {
    resolver.onCollapse();
  }
}
