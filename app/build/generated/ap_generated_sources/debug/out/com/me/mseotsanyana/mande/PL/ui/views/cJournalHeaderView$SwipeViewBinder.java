package com.me.mseotsanyana.mande.PL.ui.views;

import android.widget.TextView;
import com.me.mseotsanyana.placeholderview.annotationlibrary.Keep;
import com.me.mseotsanyana.placeholderviewlibrary.$.R;
import com.me.mseotsanyana.placeholderviewlibrary.cSwipeDecor;
import com.me.mseotsanyana.placeholderviewlibrary.cSwipePlaceHolderView;
import com.me.mseotsanyana.placeholderviewlibrary.cSwipeViewBinder;
import java.lang.Override;

@Keep
public class cJournalHeaderView$SwipeViewBinder extends cSwipeViewBinder<cJournalHeaderView, cSwipePlaceHolderView.FrameView, cSwipePlaceHolderView.SwipeOption, cSwipeDecor> {
  public cJournalHeaderView$SwipeViewBinder(cJournalHeaderView resolver) {
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
  protected void bindViews(cJournalHeaderView resolver, cSwipePlaceHolderView.FrameView itemView) {
    resolver.textViewHeading = (TextView)itemView.findViewById(R.id.textViewHeading);
    resolver.toggleIcon = (TextView)itemView.findViewById(R.id.toggleIcon);
  }

  @Override
  protected void bindClick(final cJournalHeaderView resolver,
      cSwipePlaceHolderView.FrameView itemView) {
  }

  @Override
  protected void bindLongClick(final cJournalHeaderView resolver,
      cSwipePlaceHolderView.FrameView itemView) {
  }

  @Override
  protected void bindSwipeView(cSwipePlaceHolderView.FrameView itemView) {
  }

  @Override
  protected void bindSwipeIn(cJournalHeaderView resolver) {
  }

  @Override
  protected void bindSwipeOut(cJournalHeaderView resolver) {
  }

  @Override
  protected void bindSwipeInState() {
  }

  @Override
  protected void bindSwipeOutState() {
  }

  @Override
  protected void bindSwipeCancelState() {
  }

  @Override
  protected void bindSwipeHead(cJournalHeaderView resolver) {
  }
}
