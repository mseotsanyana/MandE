package com.me.mseotsanyana.mande.PL.ui.views;

import android.widget.TextView;
import com.me.mseotsanyana.placeholderview.annotationlibrary.Keep;
import com.me.mseotsanyana.placeholderviewlibrary.$.R;
import com.me.mseotsanyana.placeholderviewlibrary.cSwipeDecor;
import com.me.mseotsanyana.placeholderviewlibrary.cSwipePlaceHolderView;
import com.me.mseotsanyana.placeholderviewlibrary.cSwipeViewBinder;
import java.lang.Override;

@Keep
public class cLogFrameHeaderView$SwipeViewBinder extends cSwipeViewBinder<cLogFrameHeaderView, cSwipePlaceHolderView.FrameView, cSwipePlaceHolderView.SwipeOption, cSwipeDecor> {
  public cLogFrameHeaderView$SwipeViewBinder(cLogFrameHeaderView resolver) {
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
  protected void bindViews(cLogFrameHeaderView resolver, cSwipePlaceHolderView.FrameView itemView) {
    resolver.textViewHeading = (TextView)itemView.findViewById(R.id.textViewHeading);
    resolver.toggleIcon = (TextView)itemView.findViewById(R.id.toggleIcon);
  }

  @Override
  protected void bindClick(final cLogFrameHeaderView resolver,
      cSwipePlaceHolderView.FrameView itemView) {
  }

  @Override
  protected void bindLongClick(final cLogFrameHeaderView resolver,
      cSwipePlaceHolderView.FrameView itemView) {
  }

  @Override
  protected void bindSwipeView(cSwipePlaceHolderView.FrameView itemView) {
  }

  @Override
  protected void bindSwipeIn(cLogFrameHeaderView resolver) {
  }

  @Override
  protected void bindSwipeOut(cLogFrameHeaderView resolver) {
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
  protected void bindSwipeHead(cLogFrameHeaderView resolver) {
  }
}
