package com.me.mseotsanyana.mande.PL.presenters.common;

import android.view.View;
import android.widget.TextView;
import com.me.mseotsanyana.multiselectspinnerlibrary.cSingleSpinnerSearch;
import com.me.mseotsanyana.placeholderview.annotationlibrary.Keep;
import com.me.mseotsanyana.placeholderviewlibrary.$.R;
import com.me.mseotsanyana.placeholderviewlibrary.ViewBinder;
import java.lang.Override;

@Keep
public class cIndividualOwnerView$ViewBinder extends ViewBinder<cIndividualOwnerView, View> {
  public cIndividualOwnerView$ViewBinder(cIndividualOwnerView resolver) {
    super(resolver, R.layout.cIndividualOwnerView, false);
  }

  @Override
  protected void resolveView(cIndividualOwnerView resolver) {
    resolver.onResolved();
  }

  @Override
  protected void recycleView() {
    cIndividualOwnerView resolver = getResolver();
  }

  @Override
  protected void unbind() {
    cIndividualOwnerView resolver = getResolver();
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
}
