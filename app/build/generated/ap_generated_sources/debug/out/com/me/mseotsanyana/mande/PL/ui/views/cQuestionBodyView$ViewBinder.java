package com.me.mseotsanyana.mande.PL.ui.views;

import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import com.me.mseotsanyana.placeholderview.annotationlibrary.Keep;
import com.me.mseotsanyana.placeholderviewlibrary.$.R;
import com.me.mseotsanyana.placeholderviewlibrary.ViewBinder;
import java.lang.Override;

@Keep
public class cQuestionBodyView$ViewBinder extends ViewBinder<cQuestionBodyView, View> {
  public cQuestionBodyView$ViewBinder(cQuestionBodyView resolver) {
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
  protected void bindViews(cQuestionBodyView resolver, View itemView) {
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
  protected void bindClick(final cQuestionBodyView resolver, View itemView) {
  }

  @Override
  protected void bindLongClick(final cQuestionBodyView resolver, View itemView) {
  }
}
