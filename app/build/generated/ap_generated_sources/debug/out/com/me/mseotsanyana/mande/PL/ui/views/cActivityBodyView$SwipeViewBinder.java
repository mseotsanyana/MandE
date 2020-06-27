package com.me.mseotsanyana.mande.PL.ui.views;

import android.view.View;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import com.me.mseotsanyana.bmblibrary.cBoomMenuButton;
import com.me.mseotsanyana.expandablelayoutlibrary.cExpandableLayout;
import com.me.mseotsanyana.placeholderview.annotationlibrary.Keep;
import com.me.mseotsanyana.placeholderviewlibrary.$.R;
import com.me.mseotsanyana.placeholderviewlibrary.cSwipeDecor;
import com.me.mseotsanyana.placeholderviewlibrary.cSwipePlaceHolderView;
import com.me.mseotsanyana.placeholderviewlibrary.cSwipeViewBinder;
import java.lang.Override;

@Keep
public class cActivityBodyView$SwipeViewBinder extends cSwipeViewBinder<cActivityBodyView, cSwipePlaceHolderView.FrameView, cSwipePlaceHolderView.SwipeOption, cSwipeDecor> {
  public cActivityBodyView$SwipeViewBinder(cActivityBodyView resolver) {
    super(resolver, R.layout.cActivityBodyView, false);
  }

  @Override
  protected void resolveView(cActivityBodyView resolver) {
    resolver.onResolved();
  }

  @Override
  protected void recycleView() {
    cActivityBodyView resolver = getResolver();
  }

  @Override
  protected void unbind() {
    cActivityBodyView resolver = getResolver();
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
  protected void bindViewPosition(cActivityBodyView resolver, int position) {
  }

  @Override
  protected void bindViews(cActivityBodyView resolver, cSwipePlaceHolderView.FrameView itemView) {
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
  protected void bindClick(final cActivityBodyView resolver,
      cSwipePlaceHolderView.FrameView itemView) {
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
  protected void bindLongClick(final cActivityBodyView resolver,
      cSwipePlaceHolderView.FrameView itemView) {
  }

  @Override
  protected void bindSwipeView(cSwipePlaceHolderView.FrameView itemView) {
  }

  @Override
  protected void bindSwipeIn(cActivityBodyView resolver) {
  }

  @Override
  protected void bindSwipeOut(cActivityBodyView resolver) {
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
  protected void bindSwipeHead(cActivityBodyView resolver) {
  }
}
