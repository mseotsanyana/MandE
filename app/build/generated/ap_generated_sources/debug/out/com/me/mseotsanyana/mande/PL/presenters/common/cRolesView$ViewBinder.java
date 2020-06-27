package com.me.mseotsanyana.mande.PL.presenters.common;

import android.view.View;
import android.widget.TextView;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.me.mseotsanyana.placeholderview.annotationlibrary.Keep;
import com.me.mseotsanyana.placeholderviewlibrary.$.R;
import com.me.mseotsanyana.placeholderviewlibrary.ViewBinder;
import java.lang.Override;

@Keep
public class cRolesView$ViewBinder extends ViewBinder<cRolesView, View> {
  public cRolesView$ViewBinder(cRolesView resolver) {
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
  protected void bindViews(cRolesView resolver, View itemView) {
    resolver.textViewName = (TextView)itemView.findViewById(R.id.textViewName);
    resolver.textViewDescription = (TextView)itemView.findViewById(R.id.textViewDescription);
    resolver.switchStatus = (SwitchMaterial)itemView.findViewById(R.id.switchStatus);
  }

  @Override
  protected void bindClick(final cRolesView resolver, View itemView) {
  }

  @Override
  protected void bindLongClick(final cRolesView resolver, View itemView) {
  }
}
