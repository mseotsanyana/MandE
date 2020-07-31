package com.me.mseotsanyana.mande.PL.ui.views;

import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import com.me.mseotsanyana.placeholderview.annotationlibrary.Keep;
import com.me.mseotsanyana.placeholderviewlibrary.$.R;
import com.me.mseotsanyana.placeholderviewlibrary.cExpandableViewBinder;
import java.lang.Deprecated;
import java.lang.Override;

@Keep
public class cQuestionBodyView$cExpandableViewBinder extends cExpandableViewBinder<cQuestionBodyView, View> {
  public cQuestionBodyView$cExpandableViewBinder(cQuestionBodyView resolver) {
    super(resolver, R.layout.cQuestionBodyView, false, false, false);
  }

  @Override
  protected void resolveView(cQuestionBodyView resolver) {
    resolver.onResolved();
  }

  @Override
  protected void recycleView() {
    cQuestionBodyView resolver = getResolver();
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
  protected void bindToggle(final cQuestionBodyView resolver, View itemView) {
    itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (isExpanded()) collapse();
        else expand();
      }
    });
  }

  @Override
  protected void bindExpand(cQuestionBodyView resolver) {
  }

  @Override
  protected void bindCollapse(cQuestionBodyView resolver) {
  }
}
