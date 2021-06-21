package com.me.mseotsanyana.mande.PL.ui.fragments.session;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.me.mseotsanyana.mande.R;

public class cLoginFragmentDirections {
  private cLoginFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionCLoginFragmentToCHomeFragment() {
    return new ActionOnlyNavDirections(R.id.action_cLoginFragment_to_cHomeFragment);
  }

  @NonNull
  public static NavDirections actionCLoginFragmentToCSignUpFragment() {
    return new ActionOnlyNavDirections(R.id.action_cLoginFragment_to_cSignUpFragment);
  }
}
