package com.me.mseotsanyana.mande.PL.ui.fragments.session;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.me.mseotsanyana.mande.R;

public class cSignUpFragmentDirections {
  private cSignUpFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionCSignUpFragmentToCLoginFragment() {
    return new ActionOnlyNavDirections(R.id.action_cSignUpFragment_to_cLoginFragment);
  }
}
