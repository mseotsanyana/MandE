package com.me.mseotsanyana.mande.PL.presenters.common;

import android.view.View;
import android.widget.TextView;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.me.mseotsanyana.placeholderview.annotationlibrary.Keep;
import com.me.mseotsanyana.placeholderviewlibrary.$.R;
import com.me.mseotsanyana.placeholderviewlibrary.cExpandableViewBinder;
import java.lang.Deprecated;
import java.lang.Override;

@Keep
public class cRolesView$cExpandableViewBinder extends cExpandableViewBinder<cRolesView, View> {
  public cRolesView$cExpandableViewBinder(cRolesView resolver) {
    super(resolver, R.layout.cRolesView, false, false, false);
  }

  @Override
  protected void resolveView(cRolesView resolver) {
    resolver.onResolved();
  }

  @Override
  protected void recycleView() {
    cRolesView resolver = getResolver();
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
  protected void bindToggle(final cRolesView resolver, View itemView) {
    itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (isExpanded()) collapse();
        else expand();
      }
    });
  }

  @Override
  protected void bindExpand(cRolesView resolver) {
  }

  @Override
  protected void bindCollapse(cRolesView resolver) {
  }
}
