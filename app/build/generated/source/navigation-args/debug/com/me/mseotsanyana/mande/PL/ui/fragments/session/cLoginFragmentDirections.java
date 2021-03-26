package com.me.mseotsanyana.mande.PL.ui.fragments.session;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.me.mseotsanyana.mande.R;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class cLoginFragmentDirections {
  private cLoginFragmentDirections() {
  }

  @NonNull
  public static ActionCLoginFragmentToCHomeFragment actionCLoginFragmentToCHomeFragment(
      @NonNull String userEmail) {
    return new ActionCLoginFragmentToCHomeFragment(userEmail);
  }

  @NonNull
  public static NavDirections actionCLoginFragmentToCSignUpFragment() {
    return new ActionOnlyNavDirections(R.id.action_cLoginFragment_to_cSignUpFragment);
  }

  public static class ActionCLoginFragmentToCHomeFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    private ActionCLoginFragmentToCHomeFragment(@NonNull String userEmail) {
      if (userEmail == null) {
        throw new IllegalArgumentException("Argument \"userEmail\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("userEmail", userEmail);
    }

    @NonNull
    public ActionCLoginFragmentToCHomeFragment setUserEmail(@NonNull String userEmail) {
      if (userEmail == null) {
        throw new IllegalArgumentException("Argument \"userEmail\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("userEmail", userEmail);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
      Bundle __result = new Bundle();
      if (arguments.containsKey("userEmail")) {
        String userEmail = (String) arguments.get("userEmail");
        __result.putString("userEmail", userEmail);
      }
      return __result;
    }

    @Override
    public int getActionId() {
      return R.id.action_cLoginFragment_to_cHomeFragment;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public String getUserEmail() {
      return (String) arguments.get("userEmail");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionCLoginFragmentToCHomeFragment that = (ActionCLoginFragmentToCHomeFragment) object;
      if (arguments.containsKey("userEmail") != that.arguments.containsKey("userEmail")) {
        return false;
      }
      if (getUserEmail() != null ? !getUserEmail().equals(that.getUserEmail()) : that.getUserEmail() != null) {
        return false;
      }
      if (getActionId() != that.getActionId()) {
        return false;
      }
      return true;
    }

    @Override
    public int hashCode() {
      int result = 1;
      result = 31 * result + (getUserEmail() != null ? getUserEmail().hashCode() : 0);
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionCLoginFragmentToCHomeFragment(actionId=" + getActionId() + "){"
          + "userEmail=" + getUserEmail()
          + "}";
    }
  }
}
