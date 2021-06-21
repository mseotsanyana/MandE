package com.me.mseotsanyana.mande.PL.ui.views;

import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatTextView;
import com.me.mseotsanyana.placeholderview.annotationlibrary.Keep;
import com.me.mseotsanyana.placeholderviewlibrary.$.R;
import com.me.mseotsanyana.placeholderviewlibrary.cExpandableViewBinder;
import java.lang.Deprecated;
import java.lang.Override;

@Keep
public class cTeamHeaderView$cExpandableViewBinder extends cExpandableViewBinder<cTeamHeaderView, View> {
  public cTeamHeaderView$cExpandableViewBinder(cTeamHeaderView resolver) {
    super(resolver, R.layout.cTeamHeaderView, false, true, true);
  }

  @Override
  protected void resolveView(cTeamHeaderView resolver) {
    resolver.onResolved();
  }

  @Override
  protected void recycleView() {
    cTeamHeaderView resolver = getResolver();
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
  protected void bindViewPosition(cTeamHeaderView resolver, int position) {
  }

  @Override
  protected void bindViews(cTeamHeaderView resolver, View itemView) {
    resolver.textViewName = (TextView)itemView.findViewById(R.id.textViewName);
    resolver.textViewDescription = (TextView)itemView.findViewById(R.id.textViewDescription);
    resolver.textViewDetailIcon = (AppCompatTextView)itemView.findViewById(R.id.textViewDetailIcon);
    resolver.textViewJoinIcon = (AppCompatTextView)itemView.findViewById(R.id.textViewJoinIcon);
    resolver.textViewAddIcon = (AppCompatTextView)itemView.findViewById(R.id.textViewAddIcon);
    resolver.textViewUpdateIcon = (AppCompatTextView)itemView.findViewById(R.id.textViewUpdateIcon);
    resolver.textViewDeleteIcon = (AppCompatTextView)itemView.findViewById(R.id.textViewDeleteIcon);
  }

  @Override
  protected void bindClick(final cTeamHeaderView resolver, View itemView) {
  }

  @Override
  protected void bindLongClick(final cTeamHeaderView resolver, View itemView) {
  }

  @Override
  protected void bindParentPosition(int position) {
    getResolver().parentPosition = position;
    setParentPosition(position);
  }

  @Override
  protected void bindChildPosition(int position) {
  }

  @Override
  protected void bindToggle(final cTeamHeaderView resolver, View itemView) {
    itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (isExpanded()) collapse();
        else expand();
      }
    });
  }

  @Override
  protected void bindExpand(cTeamHeaderView resolver) {
    resolver.onExpand();
  }

  @Override
  protected void bindCollapse(cTeamHeaderView resolver) {
    resolver.onCollapse();
  }
}
