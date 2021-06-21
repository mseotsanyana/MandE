package com.me.mseotsanyana.mande.PL.ui.fragments.session;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.me.mseotsanyana.mande.R;

public class cHomeFragmentDirections {
  private cHomeFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionCHomeFragmentToCLoginFragment() {
    return new ActionOnlyNavDirections(R.id.action_cHomeFragment_to_cLoginFragment);
  }

  @NonNull
  public static NavDirections actionCHomeFragmentToCUserProfileFragment() {
    return new ActionOnlyNavDirections(R.id.action_cHomeFragment_to_cUserProfileFragment);
  }

  @NonNull
  public static NavDirections actionCHomeFragmentToCOrganizationDetailFragment() {
    return new ActionOnlyNavDirections(R.id.action_cHomeFragment_to_cOrganizationDetailFragment);
  }

  @NonNull
  public static NavDirections actionCHomeFragmentToCTeamFragment() {
    return new ActionOnlyNavDirections(R.id.action_cHomeFragment_to_cTeamFragment);
  }
}
