package com.me.mseotsanyana.mande.PL.ui.fragments.logframe;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.me.mseotsanyana.mande.R;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class cLogFrameFragmentDirections {
  private cLogFrameFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionCLogFrameFragmentToCImpactFragment() {
    return new ActionOnlyNavDirections(R.id.action_cLogFrameFragment_to_cImpactFragment);
  }

  @NonNull
  public static NavDirections actionCLogFrameFragmentToCOutcomeFragment() {
    return new ActionOnlyNavDirections(R.id.action_cLogFrameFragment_to_cOutcomeFragment);
  }

  @NonNull
  public static NavDirections actionCLogFrameFragmentToCOutputFragment() {
    return new ActionOnlyNavDirections(R.id.action_cLogFrameFragment_to_cOutputFragment);
  }

  @NonNull
  public static NavDirections actionCLogFrameFragmentToCInputFragment() {
    return new ActionOnlyNavDirections(R.id.action_cLogFrameFragment_to_cInputFragment);
  }

  @NonNull
  public static ActionCLogFrameFragmentToCEvaluationFragment actionCLogFrameFragmentToCEvaluationFragment(
      long logFrameID) {
    return new ActionCLogFrameFragmentToCEvaluationFragment(logFrameID);
  }

  public static class ActionCLogFrameFragmentToCEvaluationFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    private ActionCLogFrameFragmentToCEvaluationFragment(long logFrameID) {
      this.arguments.put("logFrameID", logFrameID);
    }

    @NonNull
    public ActionCLogFrameFragmentToCEvaluationFragment setLogFrameID(long logFrameID) {
      this.arguments.put("logFrameID", logFrameID);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
      Bundle __result = new Bundle();
      if (arguments.containsKey("logFrameID")) {
        long logFrameID = (long) arguments.get("logFrameID");
        __result.putLong("logFrameID", logFrameID);
      }
      return __result;
    }

    @Override
    public int getActionId() {
      return R.id.action_cLogFrameFragment_to_cEvaluationFragment;
    }

    @SuppressWarnings("unchecked")
    public long getLogFrameID() {
      return (long) arguments.get("logFrameID");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionCLogFrameFragmentToCEvaluationFragment that = (ActionCLogFrameFragmentToCEvaluationFragment) object;
      if (arguments.containsKey("logFrameID") != that.arguments.containsKey("logFrameID")) {
        return false;
      }
      if (getLogFrameID() != that.getLogFrameID()) {
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
      result = 31 * result + (int)(getLogFrameID() ^ (getLogFrameID() >>> 32));
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionCLogFrameFragmentToCEvaluationFragment(actionId=" + getActionId() + "){"
          + "logFrameID=" + getLogFrameID()
          + "}";
    }
  }
}
