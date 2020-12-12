package com.me.mseotsanyana.mande.PL.ui.views;

import android.view.View;
import android.widget.TextView;
import com.me.mseotsanyana.placeholderview.annotationlibrary.Keep;
import com.me.mseotsanyana.placeholderviewlibrary.$.R;
import com.me.mseotsanyana.placeholderviewlibrary.ViewBinder;
import java.lang.Override;

@Keep
public class cQuestionHeaderView$ViewBinder extends ViewBinder<cQuestionHeaderView, View> {
  public cQuestionHeaderView$ViewBinder(cQuestionHeaderView resolver) {
    super(resolver, R.layout.cQuestionHeaderView, false);
  }

  @Override
  protected void resolveView(cQuestionHeaderView resolver) {
    resolver.onResolved();
  }

  @Override
  protected void recycleView() {
    cQuestionHeaderView resolver = getResolver();
  }

  @Override
  protected void unbind() {
    cQuestionHeaderView resolver = getResolver();
    boolean nullable = isNullable();
    if (resolver != null && nullable) {
      resolver.textViewHeading = null;
      resolver.toggleIcon = null;
      resolver.toggleView = null;
      setResolver(null);
      setAnimationResolver(null);
    }
  }

  @Override
  protected void bindViewPosition(cQuestionHeaderView resolver, int position) {
  }

  @Override
  protected void bindViews(cQuestionHeaderView resolver, View itemView) {
    resolver.textViewHeading = (TextView)itemView.findViewById(R.id.textViewHeading);
    resolver.toggleIcon = (TextView)itemView.findViewById(R.id.toggleIcon);
  }

  @Override
  protected void bindClick(final cQuestionHeaderView resolver, View itemView) {
  }

  @Override
  protected void bindLongClick(final cQuestionHeaderView resolver, View itemView) {
  }
}
