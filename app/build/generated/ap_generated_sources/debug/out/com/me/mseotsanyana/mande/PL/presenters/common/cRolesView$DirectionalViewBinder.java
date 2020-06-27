package com.me.mseotsanyana.mande.PL.presenters.common;

import android.widget.TextView;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.me.mseotsanyana.placeholderview.annotationlibrary.Keep;
import com.me.mseotsanyana.placeholderviewlibrary.$.R;
import com.me.mseotsanyana.placeholderviewlibrary.cSwipeDecor;
import com.me.mseotsanyana.placeholderviewlibrary.cSwipeDirectionalView;
import com.me.mseotsanyana.placeholderviewlibrary.cSwipeDirectionalViewBinder;
import com.me.mseotsanyana.placeholderviewlibrary.cSwipePlaceHolderView;
import com.me.mseotsanyana.placeholderviewlibrary.eSwipeDirection;
import java.lang.Override;

@Keep
public class cRolesView$DirectionalViewBinder extends cSwipeDirectionalViewBinder<cRolesView, cSwipePlaceHolderView.FrameView, cSwipeDirectionalView.SwipeDirectionalOption, cSwipeDecor> {
  public cRolesView$DirectionalViewBinder(cRolesView resolver) {
    super(resolver, R.layout.cRolesView, false);
  }

  @Override
  protected void resolveView(cRolesView resolver) {
    resolver.onResolved();
  }

  @Override
  protected void recycleView() {
    cRolesView resolver = getResolver();
  }

  @Override
  protected void unbind() {
    cRolesView resolver = getResolver();
    boolean nullable = isNullable();
    if (resolver != null && nullable) {
      resolver.textViewName = null;
      resolver.textViewDescription = null;
      resolver.switchStatus = null;
      setResolver(null);
      setAnimationResolver(null);
    }
  }

  @Override
  protected void bindViewPosition(cRolesView resolver, int position) {
  }

  @Override
  protected void bindViews(cRolesView resolver, cSwipePlaceHolderView.FrameView itemView) {
    resolver.textViewName = (TextView)itemView.findViewById(R.id.textViewName);
    resolver.textViewDescription = (TextView)itemView.findViewById(R.id.textViewDescription);
    resolver.switchStatus = (SwitchMaterial)itemView.findViewById(R.id.switchStatus);
  }

  @Override
  protected void bindClick(final cRolesView resolver, cSwipePlaceHolderView.FrameView itemView) {
  }

  @Override
  protected void bindLongClick(final cRolesView resolver,
      cSwipePlaceHolderView.FrameView itemView) {
  }

  @Override
  protected void bindSwipeView(cSwipePlaceHolderView.FrameView itemView) {
  }

  @Override
  protected void bindSwipeIn(cRolesView resolver) {
  }

  @Override
  protected void bindSwipeOut(cRolesView resolver) {
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
  protected void bindSwipeHead(cRolesView resolver) {
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
