package com.me.mseotsanyana.mande.PL.presenters.common;

import android.widget.TextView;
import com.me.mseotsanyana.placeholderview.annotationlibrary.Keep;
import com.me.mseotsanyana.placeholderviewlibrary.$.R;
import com.me.mseotsanyana.placeholderviewlibrary.cSwipeDecor;
import com.me.mseotsanyana.placeholderviewlibrary.cSwipePlaceHolderView;
import com.me.mseotsanyana.placeholderviewlibrary.cSwipeViewBinder;
import java.lang.Override;

@Keep
public class cHeadingView$SwipeViewBinder extends cSwipeViewBinder<cHeadingView, cSwipePlaceHolderView.FrameView, cSwipePlaceHolderView.SwipeOption, cSwipeDecor> {
  public cHeadingView$SwipeViewBinder(cHeadingView resolver) {
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
  protected void bindViews(cHeadingView resolver, cSwipePlaceHolderView.FrameView itemView) {
    resolver.textViewHeading = (TextView)itemView.findViewById(R.id.textViewHeading);
    resolver.toggleIcon = (TextView)itemView.findViewById(R.id.toggleIcon);
  }

  @Override
  protected void bindClick(final cHeadingView resolver, cSwipePlaceHolderView.FrameView itemView) {
  }

  @Override
  protected void bindLongClick(final cHeadingView resolver,
      cSwipePlaceHolderView.FrameView itemView) {
  }

  @Override
  protected void bindSwipeView(cSwipePlaceHolderView.FrameView itemView) {
  }

  @Override
  protected void bindSwipeIn(cHeadingView resolver) {
  }

  @Override
  protected void bindSwipeOut(cHeadingView resolver) {
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
  protected void bindSwipeHead(cHeadingView resolver) {
  }
}
