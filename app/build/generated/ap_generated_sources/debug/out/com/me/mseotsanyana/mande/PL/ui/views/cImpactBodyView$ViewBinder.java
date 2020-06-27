package com.me.mseotsanyana.mande.PL.ui.views;

import android.view.View;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import com.me.mseotsanyana.bmblibrary.cBoomMenuButton;
import com.me.mseotsanyana.expandablelayoutlibrary.cExpandableLayout;
import com.me.mseotsanyana.placeholderview.annotationlibrary.Keep;
import com.me.mseotsanyana.placeholderviewlibrary.$.R;
import com.me.mseotsanyana.placeholderviewlibrary.ViewBinder;
import java.lang.Override;

@Keep
public class cImpactBodyView$ViewBinder extends ViewBinder<cImpactBodyView, View> {
  public cImpactBodyView$ViewBinder(cImpactBodyView resolver) {
    super(resolver, R.layout.cImpactBodyView, false);
  }

  @Override
  protected void resolveView(cImpactBodyView resolver) {
    resolver.onResolved();
  }

  @Override
  protected void recycleView() {
    cImpactBodyView resolver = getResolver();
  }

  @Override
  protected void unbind() {
    cImpactBodyView resolver = getResolver();
    boolean nullable = isNullable();
    if (resolver != null && nullable) {
      resolver.cardView = null;
      resolver.textViewNameCaption = null;
      resolver.textViewName = null;
      resolver.textViewDescription = null;
      resolver.textViewStartDate = null;
      resolver.textViewEndDate = null;
      resolver.expandableLayout = null;
      resolver.textViewDetailIcon = null;
      resolver.textViewUpdateIcon = null;
      resolver.textViewDeleteIcon = null;
      resolver.textViewSyncIcon = null;
      resolver.bmbMenu = null;
      setResolver(null);
      setAnimationResolver(null);
    }
  }

  @Override
  protected void bindViewPosition(cImpactBodyView resolver, int position) {
  }

  @Override
  protected void bindViews(cImpactBodyView resolver, View itemView) {
    resolver.cardView = (CardView)itemView.findViewById(R.id.cardView);
    resolver.textViewNameCaption = (TextView)itemView.findViewById(R.id.textViewNameCaption);
    resolver.textViewName = (TextView)itemView.findViewById(R.id.textViewName);
    resolver.textViewDescription = (TextView)itemView.findViewById(R.id.textViewDescription);
    resolver.textViewStartDate = (TextView)itemView.findViewById(R.id.textViewStartDate);
    resolver.textViewEndDate = (TextView)itemView.findViewById(R.id.textViewEndDate);
    resolver.expandableLayout = (cExpandableLayout)itemView.findViewById(R.id.expandableLayout);
    resolver.textViewDetailIcon = (TextView)itemView.findViewById(R.id.textViewDetailIcon);
    resolver.textViewUpdateIcon = (TextView)itemView.findViewById(R.id.textViewUpdateIcon);
    resolver.textViewDeleteIcon = (TextView)itemView.findViewById(R.id.textViewDeleteIcon);
    resolver.textViewSyncIcon = (TextView)itemView.findViewById(R.id.textViewSyncIcon);
    resolver.bmbMenu = (cBoomMenuButton)itemView.findViewById(R.id.bmbMenu);
  }

  @Override
  protected void bindClick(final cImpactBodyView resolver, View itemView) {
    itemView.findViewById(R.id.onDetailIconClick).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        resolver.onDetailIconClick();
      }
    });
    itemView.findViewById(R.id.onUpdateIconClick).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        resolver.onUpdateIconClick();
      }
    });
    itemView.findViewById(R.id.onDeleteIconClick).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        resolver.onDeleteIconClick();
      }
    });
    itemView.findViewById(R.id.onSyncIconClick).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        resolver.onSyncIconClick();
      }
    });
  }

  @Override
  protected void bindLongClick(final cImpactBodyView resolver, View itemView) {
  }
}
