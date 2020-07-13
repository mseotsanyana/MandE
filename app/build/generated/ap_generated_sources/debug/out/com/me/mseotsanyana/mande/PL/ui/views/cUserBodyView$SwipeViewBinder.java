package com.me.mseotsanyana.mande.PL.ui.views;

import android.view.View;
import android.widget.TextView;
import com.me.mseotsanyana.placeholderview.annotationlibrary.Keep;
import com.me.mseotsanyana.placeholderviewlibrary.$.R;
import com.me.mseotsanyana.placeholderviewlibrary.cSwipeDecor;
import com.me.mseotsanyana.placeholderviewlibrary.cSwipePlaceHolderView;
import com.me.mseotsanyana.placeholderviewlibrary.cSwipeViewBinder;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.Override;

@Keep
public class cUserBodyView$SwipeViewBinder extends cSwipeViewBinder<cUserBodyView, cSwipePlaceHolderView.FrameView, cSwipePlaceHolderView.SwipeOption, cSwipeDecor> {
  public cUserBodyView$SwipeViewBinder(cUserBodyView resolver) {
    super(resolver, R.layout.cUserBodyView, false);
  }

  @Override
  protected void resolveView(cUserBodyView resolver) {
    resolver.onResolved();
  }

  @Override
  protected void recycleView() {
    cUserBodyView resolver = getResolver();
  }

  @Override
  protected void unbind() {
    cUserBodyView resolver = getResolver();
    boolean nullable = isNullable();
    if (resolver != null && nullable) {
      resolver.circleImageViewUser = null;
      resolver.textViewName = null;
      resolver.textViewPhone = null;
      resolver.textViewEmail = null;
      resolver.textViewDeleteIcon = null;
      setResolver(null);
      setAnimationResolver(null);
    }
  }

  @Override
  protected void bindViewPosition(cUserBodyView resolver, int position) {
  }

  @Override
  protected void bindViews(cUserBodyView resolver, cSwipePlaceHolderView.FrameView itemView) {
    resolver.circleImageViewUser = (CircleImageView)itemView.findViewById(R.id.circleImageViewUser);
    resolver.textViewName = (TextView)itemView.findViewById(R.id.textViewName);
    resolver.textViewPhone = (TextView)itemView.findViewById(R.id.textViewPhone);
    resolver.textViewEmail = (TextView)itemView.findViewById(R.id.textViewEmail);
    resolver.textViewDeleteIcon = (TextView)itemView.findViewById(R.id.textViewDeleteIcon);
  }

  @Override
  protected void bindClick(final cUserBodyView resolver, cSwipePlaceHolderView.FrameView itemView) {
    itemView.findViewById(R.id.onDeleteIconClick).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        resolver.onDeleteIconClick();
      }
    });
  }

  @Override
  protected void bindLongClick(final cUserBodyView resolver,
      cSwipePlaceHolderView.FrameView itemView) {
  }

  @Override
  protected void bindSwipeView(cSwipePlaceHolderView.FrameView itemView) {
  }

  @Override
  protected void bindSwipeIn(cUserBodyView resolver) {
  }

  @Override
  protected void bindSwipeOut(cUserBodyView resolver) {
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
  protected void bindSwipeHead(cUserBodyView resolver) {
  }
}
