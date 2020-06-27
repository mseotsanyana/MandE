package com.me.mseotsanyana.mande.PL.presenters.common;

import android.view.View;
import android.widget.TextView;
import com.me.mseotsanyana.multiselectspinnerlibrary.cSingleSpinnerSearch;
import com.me.mseotsanyana.placeholderview.annotationlibrary.Keep;
import com.me.mseotsanyana.placeholderviewlibrary.$.R;
import com.me.mseotsanyana.placeholderviewlibrary.cExpandableViewBinder;
import java.lang.Deprecated;
import java.lang.Override;

@Keep
public class cIndividualOwnerView$cExpandableViewBinder extends cExpandableViewBinder<cIndividualOwnerView, View> {
  public cIndividualOwnerView$cExpandableViewBinder(cIndividualOwnerView resolver) {
    super(resolver, R.layout.cIndividualOwnerView, false, false, false);
  }

  @Override
  protected void resolveView(cIndividualOwnerView resolver) {
    resolver.onResolved();
  }

  @Override
  protected void recycleView() {
    cIndividualOwnerView resolver = getResolver();
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
  protected void bindViewPosition(cIndividualOwnerView resolver, int position) {
  }

  @Override
  protected void bindViews(cIndividualOwnerView resolver, View itemView) {
    resolver.textViewCaption = (TextView)itemView.findViewById(R.id.textViewCaption);
    resolver.textViewName = (TextView)itemView.findViewById(R.id.textViewName);
    resolver.textViewDescription = (TextView)itemView.findViewById(R.id.textViewDescription);
    resolver.singleSpinnerSearchOwners = (cSingleSpinnerSearch)itemView.findViewById(R.id.singleSpinnerSearchOwners);
  }

  @Override
  protected void bindClick(final cIndividualOwnerView resolver, View itemView) {
  }

  @Override
  protected void bindLongClick(final cIndividualOwnerView resolver, View itemView) {
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
  protected void bindToggle(final cIndividualOwnerView resolver, View itemView) {
    itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (isExpanded()) collapse();
        else expand();
      }
    });
  }

  @Override
  protected void bindExpand(cIndividualOwnerView resolver) {
  }

  @Override
  protected void bindCollapse(cIndividualOwnerView resolver) {
  }
}
