package com.me.mseotsanyana.mande.PL.ui.fragments.raid;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.me.mseotsanyana.mande.R;

public class cRaidLogFragmentDirections {
  private cRaidLogFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionCRaidLogFragmentToCRiskRegisterFragment() {
    return new ActionOnlyNavDirections(R.id.action_cRaidLogFragment_to_cRiskRegisterFragment);
  }

  @NonNull
  public static NavDirections actionCRaidLogFragmentToCAssumptionRegisterFragment() {
    return new ActionOnlyNavDirections(R.id.action_cRaidLogFragment_to_cAssumptionRegisterFragment);
  }

  @NonNull
  public static NavDirections actionCRaidLogFragmentToCIssueRegisterFragment() {
    return new ActionOnlyNavDirections(R.id.action_cRaidLogFragment_to_cIssueRegisterFragment);
  }

  @NonNull
  public static NavDirections actionCRaidLogFragmentToCDependencyRegisterFragment() {
    return new ActionOnlyNavDirections(R.id.action_cRaidLogFragment_to_cDependencyRegisterFragment);
  }
}
