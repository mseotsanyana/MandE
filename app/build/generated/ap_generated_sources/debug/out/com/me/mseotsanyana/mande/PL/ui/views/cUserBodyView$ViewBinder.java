package com.me.mseotsanyana.mande.PL.ui.views;

import android.view.View;
import android.widget.TextView;
import com.me.mseotsanyana.placeholderview.annotationlibrary.Keep;
import com.me.mseotsanyana.placeholderviewlibrary.$.R;
import com.me.mseotsanyana.placeholderviewlibrary.ViewBinder;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.Override;

@Keep
public class cUserBodyView$ViewBinder extends ViewBinder<cUserBodyView, View> {
  public cUserBodyView$ViewBinder(cUserBodyView resolver) {
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
}
