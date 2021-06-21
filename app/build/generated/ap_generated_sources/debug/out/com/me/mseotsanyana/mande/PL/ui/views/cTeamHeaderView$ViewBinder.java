package com.me.mseotsanyana.mande.PL.ui.views;

import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatTextView;
import com.me.mseotsanyana.placeholderview.annotationlibrary.Keep;
import com.me.mseotsanyana.placeholderviewlibrary.$.R;
import com.me.mseotsanyana.placeholderviewlibrary.ViewBinder;
import java.lang.Override;

@Keep
public class cTeamHeaderView$ViewBinder extends ViewBinder<cTeamHeaderView, View> {
  public cTeamHeaderView$ViewBinder(cTeamHeaderView resolver) {
    super(resolver, R.layout.cTeamHeaderView, false);
  }

  @Override
  protected void resolveView(cTeamHeaderView resolver) {
    resolver.onResolved();
  }

  @Override
  protected void recycleView() {
    cTeamHeaderView resolver = getResolver();
  }

  @Override
  protected void unbind() {
    cTeamHeaderView resolver = getResolver();
    boolean nullable = isNullable();
    if (resolver != null && nullable) {
      resolver.textViewName = null;
      resolver.textViewDescription = null;
      resolver.textViewDetailIcon = null;
      resolver.textViewJoinIcon = null;
      resolver.textViewAddIcon = null;
      resolver.textViewUpdateIcon = null;
      resolver.textViewDeleteIcon = null;
      setResolver(null);
      setAnimationResolver(null);
    }
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
}
