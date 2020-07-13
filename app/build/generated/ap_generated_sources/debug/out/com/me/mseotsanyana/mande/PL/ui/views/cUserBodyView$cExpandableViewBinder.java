package com.me.mseotsanyana.mande.PL.ui.views;

import android.view.View;
import android.widget.TextView;
import com.me.mseotsanyana.placeholderview.annotationlibrary.Keep;
import com.me.mseotsanyana.placeholderviewlibrary.$.R;
import com.me.mseotsanyana.placeholderviewlibrary.cExpandableViewBinder;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.Deprecated;
import java.lang.Override;

@Keep
public class cUserBodyView$cExpandableViewBinder extends cExpandableViewBinder<cUserBodyView, View> {
  public cUserBodyView$cExpandableViewBinder(cUserBodyView resolver) {
    super(resolver, R.layout.cUserBodyView, false, false, false);
  }

  @Override
  protected void resolveView(cUserBodyView resolver) {
    resolver.onResolved();
  }

  @Override
  protected void recycleView() {
    cUserBodyView resolver = getResolver();
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
  protected void bindViewPosition(cUserBodyView resolver, int position) {
  }

  @Override
  protected void bindViews(cUserBodyView resolver, View itemView) {
    resolver.circleImageViewUser = (CircleImageView)itemView.findViewById(R.id.circleImageViewUser);
    resolver.textViewName = (TextView)itemView.findViewById(R.id.textViewName);
    resolver.textViewPhone = (TextView)itemView.findViewById(R.id.textViewPhone);
    resolver.textViewEmail = (TextView)itemView.findViewById(R.id.textViewEmail);
    resolver.textViewDeleteIcon = (TextView)itemView.findViewById(R.id.textViewDeleteIcon);
  }

  @Override
  protected void bindClick(final cUserBodyView resolver, View itemView) {
    itemView.findViewById(R.id.onDeleteIconClick).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        resolver.onDeleteIconClick();
      }
    });
  }

  @Override
  protected void bindLongClick(final cUserBodyView resolver, View itemView) {
  }

  @Override
  protected void bindParentPosition(int position) {
    getResolver().parentPosition = position;
    setParentPosition(position);
  }

  @Override
  protected void bindChildPosition(int position) {
    getResolver().childPosition = position;
    setChildPosition(position);
  }

  @Override
  protected void bindToggle(final cUserBodyView resolver, View itemView) {
    itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (isExpanded()) collapse();
        else expand();
      }
    });
  }

  @Override
  protected void bindExpand(cUserBodyView resolver) {
  }

  @Override
  protected void bindCollapse(cUserBodyView resolver) {
  }
}
