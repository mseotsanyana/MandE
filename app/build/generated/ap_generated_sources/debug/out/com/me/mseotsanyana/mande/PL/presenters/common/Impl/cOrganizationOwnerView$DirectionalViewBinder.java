package com.me.mseotsanyana.mande.PL.presenters.common.Impl;

import android.widget.TextView;
import com.me.mseotsanyana.multiselectspinnerlibrary.cSingleSpinnerSearch;
import com.me.mseotsanyana.placeholderview.annotationlibrary.Keep;
import com.me.mseotsanyana.placeholderviewlibrary.$.R;
import com.me.mseotsanyana.placeholderviewlibrary.cSwipeDecor;
import com.me.mseotsanyana.placeholderviewlibrary.cSwipeDirectionalView;
import com.me.mseotsanyana.placeholderviewlibrary.cSwipeDirectionalViewBinder;
import com.me.mseotsanyana.placeholderviewlibrary.cSwipePlaceHolderView;
import com.me.mseotsanyana.placeholderviewlibrary.eSwipeDirection;
import java.lang.Override;

@Keep
public class cOrganizationOwnerView$DirectionalViewBinder extends cSwipeDirectionalViewBinder<cOrganizationOwnerView, cSwipePlaceHolderView.FrameView, cSwipeDirectionalView.SwipeDirectionalOption, cSwipeDecor> {
  public cOrganizationOwnerView$DirectionalViewBinder(cOrganizationOwnerView resolver) {
    super(resolver, R.layout.cOrganizationOwnerView, false);
  }

  @Override
  protected void resolveView(cOrganizationOwnerView resolver) {
    resolver.onResolved();
  }

  @Override
  protected void recycleView() {
    cOrganizationOwnerView resolver = getResolver();
  }

  @Override
  protected void unbind() {
    cOrganizationOwnerView resolver = getResolver();
    boolean nullable = isNullable();
    if (resolver != null && nullable) {
      resolver.textViewCaption = null;
      resolver.textViewName = null;
      resolver.textViewDescription = null;
      resolver.singleSpinnerSearchOwners = null;
      setResolver(null);
      setAnimationResolver(null);
    }
  }

  @Override
  protected void bindViewPosition(cOrganizationOwnerView resolver, int position) {
  }

  @Override
  protected void bindViews(cOrganizationOwnerView resolver,
      cSwipePlaceHolderView.FrameView itemView) {
    resolver.textViewCaption = (TextView)itemView.findViewById(R.id.textViewCaption);
    resolver.textViewName = (TextView)itemView.findViewById(R.id.textViewName);
    resolver.textViewDescription = (TextView)itemView.findViewById(R.id.textViewDescription);
    resolver.singleSpinnerSearchOwners = (cSingleSpinnerSearch)itemView.findViewById(R.id.singleSpinnerSearchOwners);
  }

  @Override
  protected void bindClick(final cOrganizationOwnerView resolver,
      cSwipePlaceHolderView.FrameView itemView) {
  }

  @Override
  protected void bindLongClick(final cOrganizationOwnerView resolver,
      cSwipePlaceHolderView.FrameView itemView) {
  }

  @Override
  protected void bindSwipeView(cSwipePlaceHolderView.FrameView itemView) {
  }

  @Override
  protected void bindSwipeIn(cOrganizationOwnerView resolver) {
  }

  @Override
  protected void bindSwipeOut(cOrganizationOwnerView resolver) {
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
  protected void bindSwipeHead(cOrganizationOwnerView resolver) {
  }

  @Override
  protected void bindSwipingDirection(eSwipeDirection direction) {
  }

  @Override
  protected void bindSwipeInDirectional(eSwipeDirection direction) {
  }

  @Override
  protected void bindSwipeOutDirectional(eSwipeDirection direction) {
  }

  @Override
  protected void bindSwipeTouch(float xStart, float yStart, float xCurrent, float yCurrent) {
  }
}
