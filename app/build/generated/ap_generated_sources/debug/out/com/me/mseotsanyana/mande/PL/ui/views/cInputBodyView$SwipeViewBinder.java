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
public class cInputBodyView$SwipeViewBinder extends cSwipeViewBinder<cInputBodyView, cSwipePlaceHolderView.FrameView, cSwipePlaceHolderView.SwipeOption, cSwipeDecor> {
  public cInputBodyView$SwipeViewBinder(cInputBodyView resolver) {
    super(resolver, R.layout.cInputBodyView, false);
  }

  @Override
  protected void resolveView(cInputBodyView resolver) {
    resolver.onResolved();
  }

  @Override
  protected void recycleView() {
    cInputBodyView resolver = getResolver();
  }

  @Override
  protected void unbind() {
    cInputBodyView resolver = getResolver();
    boolean nullable = isNullable();
    if (resolver != null && nullable) {
      resolver.cardView = null;
      resolver.textViewActivityCaption = null;
      resolver.textViewActivity = null;
      resolver.textViewInputCaption = null;
      resolver.textViewInput = null;
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
  protected void bindViewPosition(cInputBodyView resolver, int position) {
  }

  @Override
  protected void bindViews(cInputBodyView resolver, cSwipePlaceHolderView.FrameView itemView) {
    resolver.cardView = (CardView)itemView.findViewById(R.id.cardView);
    resolver.textViewActivityCaption = (TextView)itemView.findViewById(R.id.textViewActivityCaption);
    resolver.textViewActivity = (TextView)itemView.findViewById(R.id.textViewActivity);
    resolver.textViewInputCaption = (TextView)itemView.findViewById(R.id.textViewInputCaption);
    resolver.textViewInput = (TextView)itemView.findViewById(R.id.textViewInput);
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
  protected void bindClick(final cInputBodyView resolver,
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
  protected void bindLongClick(final cInputBodyView resolver,
      cSwipePlaceHolderView.FrameView itemView) {
  }

  @Override
  protected void bindSwipeView(cSwipePlaceHolderView.FrameView itemView) {
  }

  @Override
  protected void bindSwipeIn(cInputBodyView resolver) {
  }

  @Override
  protected void bindSwipeOut(cInputBodyView resolver) {
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
  protected void bindSwipeHead(cInputBodyView resolver) {
  }
}
