package com.me.mseotsanyana.mande.PL.ui.views;

import android.widget.TableLayout;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import com.me.mseotsanyana.placeholderview.annotationlibrary.Keep;
import com.me.mseotsanyana.placeholderviewlibrary.$.R;
import com.me.mseotsanyana.placeholderviewlibrary.cSwipeDecor;
import com.me.mseotsanyana.placeholderviewlibrary.cSwipeDirectionalView;
import com.me.mseotsanyana.placeholderviewlibrary.cSwipeDirectionalViewBinder;
import com.me.mseotsanyana.placeholderviewlibrary.cSwipePlaceHolderView;
import com.me.mseotsanyana.placeholderviewlibrary.eSwipeDirection;
import java.lang.Override;

@Keep
public class cQuestionBodyView$DirectionalViewBinder extends cSwipeDirectionalViewBinder<cQuestionBodyView, cSwipePlaceHolderView.FrameView, cSwipeDirectionalView.SwipeDirectionalOption, cSwipeDecor> {
  public cQuestionBodyView$DirectionalViewBinder(cQuestionBodyView resolver) {
    super(resolver, R.layout.cQuestionBodyView, false);
  }

  @Override
  protected void resolveView(cQuestionBodyView resolver) {
    resolver.onResolved();
  }

  @Override
  protected void recycleView() {
    cQuestionBodyView resolver = getResolver();
  }

  @Override
  protected void unbind() {
    cQuestionBodyView resolver = getResolver();
    boolean nullable = isNullable();
    if (resolver != null && nullable) {
      resolver.cardViewQuestion = null;
      resolver.tableLayoutQuestion = null;
      resolver.textViewLabel = null;
      resolver.textViewQuestion = null;
      resolver.textViewQuestionType = null;
      resolver.textViewDescription = null;
      resolver.textViewGroup = null;
      resolver.textViewGroupDescription = null;
      resolver.textViewStartDate = null;
      resolver.textViewEndDate = null;
      setResolver(null);
      setAnimationResolver(null);
    }
  }

  @Override
  protected void bindViewPosition(cQuestionBodyView resolver, int position) {
  }

  @Override
  protected void bindViews(cQuestionBodyView resolver, cSwipePlaceHolderView.FrameView itemView) {
    resolver.cardViewQuestion = (CardView)itemView.findViewById(R.id.cardViewQuestion);
    resolver.tableLayoutQuestion = (TableLayout)itemView.findViewById(R.id.tableLayoutQuestion);
    resolver.textViewLabel = (TextView)itemView.findViewById(R.id.textViewLabel);
    resolver.textViewQuestion = (TextView)itemView.findViewById(R.id.textViewQuestion);
    resolver.textViewQuestionType = (TextView)itemView.findViewById(R.id.textViewQuestionType);
    resolver.textViewDescription = (TextView)itemView.findViewById(R.id.textViewDescription);
    resolver.textViewGroup = (TextView)itemView.findViewById(R.id.textViewGroup);
    resolver.textViewGroupDescription = (TextView)itemView.findViewById(R.id.textViewGroupDescription);
    resolver.textViewStartDate = (TextView)itemView.findViewById(R.id.textViewStartDate);
    resolver.textViewEndDate = (TextView)itemView.findViewById(R.id.textViewEndDate);
  }

  @Override
  protected void bindClick(final cQuestionBodyView resolver,
      cSwipePlaceHolderView.FrameView itemView) {
  }

  @Override
  protected void bindLongClick(final cQuestionBodyView resolver,
      cSwipePlaceHolderView.FrameView itemView) {
  }

  @Override
  protected void bindSwipeView(cSwipePlaceHolderView.FrameView itemView) {
  }

  @Override
  protected void bindSwipeIn(cQuestionBodyView resolver) {
  }

  @Override
  protected void bindSwipeOut(cQuestionBodyView resolver) {
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
  protected void bindSwipeHead(cQuestionBodyView resolver) {
  }

  @Override
  protected void bindSwipingDirection(eSwipeDirection direction) {
  }

  @Override
  protected void bindSwipeInDirectional(eSwipeDirection direction) {
  }

  @Override
  protected void bindSwipeOutDirectional(eSwipeDirection direction) {
  }

  @Override
  protected void bindSwipeTouch(float xStart, float yStart, float xCurrent, float yCurrent) {
  }
}
