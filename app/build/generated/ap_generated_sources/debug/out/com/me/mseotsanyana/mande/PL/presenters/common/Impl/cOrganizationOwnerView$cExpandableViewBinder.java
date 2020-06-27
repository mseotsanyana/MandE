package com.me.mseotsanyana.mande.PL.presenters.common.Impl;

import android.view.View;
import android.widget.TextView;
import com.me.mseotsanyana.multiselectspinnerlibrary.cSingleSpinnerSearch;
import com.me.mseotsanyana.placeholderview.annotationlibrary.Keep;
import com.me.mseotsanyana.placeholderviewlibrary.$.R;
import com.me.mseotsanyana.placeholderviewlibrary.cExpandableViewBinder;
import java.lang.Deprecated;
import java.lang.Override;

@Keep
public class cOrganizationOwnerView$cExpandableViewBinder extends cExpandableViewBinder<cOrganizationOwnerView, View> {
  public cOrganizationOwnerView$cExpandableViewBinder(cOrganizationOwnerView resolver) {
    super(resolver, R.layout.cOrganizationOwnerView, false, false, false);
  }

  @Override
  protected void resolveView(cOrganizationOwnerView resolver) {
    resolver.onResolved();
  }

  @Override
  protected void recycleView() {
    cOrganizationOwnerView resolver = getResolver();
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

  @Override
  protected void bindParentPosition(int position) {
    getResolver().mParentPosition = position;
    setParentPosition(position);
  }

  @Override
  protected void bindChildPosition(int position) {
    getResolver().mChildPosition = position;
    setChildPosition(position);
  }

  @Override
  protected void bindToggle(final cOrganizationOwnerView resolver, View itemView) {
    itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (isExpanded()) collapse();
        else expand();
      }
    });
  }

  @Override
  protected void bindExpand(cOrganizationOwnerView resolver) {
  }

  @Override
  protected void bindCollapse(cOrganizationOwnerView resolver) {
  }
}
