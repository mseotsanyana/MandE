package com.me.mseotsanyana.mande.PL.presenters.common.Impl;

import android.view.View;
import android.widget.TextView;
import com.me.mseotsanyana.multiselectspinnerlibrary.cSingleSpinnerSearch;
import com.me.mseotsanyana.placeholderview.annotationlibrary.Keep;
import com.me.mseotsanyana.placeholderviewlibrary.$.R;
import com.me.mseotsanyana.placeholderviewlibrary.ViewBinder;
import java.lang.Override;

@Keep
public class cOrganizationOwnerView$ViewBinder extends ViewBinder<cOrganizationOwnerView, View> {
  public cOrganizationOwnerView$ViewBinder(cOrganizationOwnerView resolver) {
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
  protected void bindViews(cOrganizationOwnerView resolver, View itemView) {
    resolver.textViewCaption = (TextView)itemView.findViewById(R.id.textViewCaption);
    resolver.textViewName = (TextView)itemView.findViewById(R.id.textViewName);
    resolver.textViewDescription = (TextView)itemView.findViewById(R.id.textViewDescription);
    resolver.singleSpinnerSearchOwners = (cSingleSpinnerSearch)itemView.findViewById(R.id.singleSpinnerSearchOwners);
  }

  @Override
  protected void bindClick(final cOrganizationOwnerView resolver, View itemView) {
  }

  @Override
  protected void bindLongClick(final cOrganizationOwnerView resolver, View itemView) {
  }
}
