package com.me.mseotsanyana.mande.PL.ui.fragments.logframe;

import android.os.Bundle;
import androidx.annotation.NonNull;
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
  public static ActionCLogFrameFragmentToCImpactFragment actionCLogFrameFragmentToCImpactFragment(
      long logFrameID) {
    return new ActionCLogFrameFragmentToCImpactFragment(logFrameID);
  }

  @NonNull
  public static ActionCLogFrameFragmentToCOutcomeFragment actionCLogFrameFragmentToCOutcomeFragment(
      long logFrameID) {
    return new ActionCLogFrameFragmentToCOutcomeFragment(logFrameID);
  }

  @NonNull
  public static ActionCLogFrameFragmentToCOutputFragment actionCLogFrameFragmentToCOutputFragment(
      long logFrameID) {
    return new ActionCLogFrameFragmentToCOutputFragment(logFrameID);
  }

  @NonNull
  public static ActionCLogFrameFragmentToCInputFragment actionCLogFrameFragmentToCInputFragment(
      long logFrameID) {
    return new ActionCLogFrameFragmentToCInputFragment(logFrameID);
  }

  @NonNull
  public static ActionCLogFrameFragmentToCEvaluationFragment actionCLogFrameFragmentToCEvaluationFragment(
      long logFrameID) {
    return new ActionCLogFrameFragmentToCEvaluationFragment(logFrameID);
  }

  @NonNull
  public static ActionCLogFrameFragmentToCActivityFragment actionCLogFrameFragmentToCActivityFragment(
      long logFrameID) {
    return new ActionCLogFrameFragmentToCActivityFragment(logFrameID);
  }

  @NonNull
  public static ActionCLogFrameFragmentToCQuestionFragment actionCLogFrameFragmentToCQuestionFragment(
      long logFrameID) {
    return new ActionCLogFrameFragmentToCQuestionFragment(logFrameID);
  }

  @NonNull
  public static ActionCLogFrameFragmentToCIndicatorFragment actionCLogFrameFragmentToCIndicatorFragment(
      long logFrameID) {
    return new ActionCLogFrameFragmentToCIndicatorFragment(logFrameID);
  }

  @NonNull
  public static ActionCLogFrameFragmentToCRaidLogFragment actionCLogFrameFragmentToCRaidLogFragment(
      long logFrameID) {
    return new ActionCLogFrameFragmentToCRaidLogFragment(logFrameID);
  }

  @NonNull
  public static ActionCLogFrameFragmentToCAWPBFragment actionCLogFrameFragmentToCAWPBFragment(
      long logFrameID) {
    return new ActionCLogFrameFragmentToCAWPBFragment(logFrameID);
  }

  @NonNull
  public static ActionCLogFrameFragmentToCBookKeepingFragment actionCLogFrameFragmentToCBookKeepingFragment(
      long logFrameID) {
    return new ActionCLogFrameFragmentToCBookKeepingFragment(logFrameID);
  }

  @NonNull
  public static ActionCLogFrameFragmentToCMonitoringFragment actionCLogFrameFragmentToCMonitoringFragment(
      long logFrameID) {
    return new ActionCLogFrameFragmentToCMonitoringFragment(logFrameID);
  }

  public static class ActionCLogFrameFragmentToCImpactFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private ActionCLogFrameFragmentToCImpactFragment(long logFrameID) {
      this.arguments.put("logFrameID", logFrameID);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionCLogFrameFragmentToCImpactFragment setLogFrameID(long logFrameID) {
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
      return R.id.action_cLogFrameFragment_to_cImpactFragment;
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
      ActionCLogFrameFragmentToCImpactFragment that = (ActionCLogFrameFragmentToCImpactFragment) object;
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
      return "ActionCLogFrameFragmentToCImpactFragment(actionId=" + getActionId() + "){"
          + "logFrameID=" + getLogFrameID()
          + "}";
    }
  }

  public static class ActionCLogFrameFragmentToCOutcomeFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private ActionCLogFrameFragmentToCOutcomeFragment(long logFrameID) {
      this.arguments.put("logFrameID", logFrameID);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionCLogFrameFragmentToCOutcomeFragment setLogFrameID(long logFrameID) {
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
      return R.id.action_cLogFrameFragment_to_cOutcomeFragment;
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
      ActionCLogFrameFragmentToCOutcomeFragment that = (ActionCLogFrameFragmentToCOutcomeFragment) object;
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
      return "ActionCLogFrameFragmentToCOutcomeFragment(actionId=" + getActionId() + "){"
          + "logFrameID=" + getLogFrameID()
          + "}";
    }
  }

  public static class ActionCLogFrameFragmentToCOutputFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private ActionCLogFrameFragmentToCOutputFragment(long logFrameID) {
      this.arguments.put("logFrameID", logFrameID);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionCLogFrameFragmentToCOutputFragment setLogFrameID(long logFrameID) {
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
      return R.id.action_cLogFrameFragment_to_cOutputFragment;
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
      ActionCLogFrameFragmentToCOutputFragment that = (ActionCLogFrameFragmentToCOutputFragment) object;
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
      return "ActionCLogFrameFragmentToCOutputFragment(actionId=" + getActionId() + "){"
          + "logFrameID=" + getLogFrameID()
          + "}";
    }
  }

  public static class ActionCLogFrameFragmentToCInputFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private ActionCLogFrameFragmentToCInputFragment(long logFrameID) {
      this.arguments.put("logFrameID", logFrameID);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionCLogFrameFragmentToCInputFragment setLogFrameID(long logFrameID) {
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
      return R.id.action_cLogFrameFragment_to_cInputFragment;
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
      ActionCLogFrameFragmentToCInputFragment that = (ActionCLogFrameFragmentToCInputFragment) object;
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
      return "ActionCLogFrameFragmentToCInputFragment(actionId=" + getActionId() + "){"
          + "logFrameID=" + getLogFrameID()
          + "}";
    }
  }

  public static class ActionCLogFrameFragmentToCEvaluationFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private ActionCLogFrameFragmentToCEvaluationFragment(long logFrameID) {
      this.arguments.put("logFrameID", logFrameID);
    }

    @NonNull
    @SuppressWarnings("unchecked")
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

  public static class ActionCLogFrameFragmentToCActivityFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private ActionCLogFrameFragmentToCActivityFragment(long logFrameID) {
      this.arguments.put("logFrameID", logFrameID);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionCLogFrameFragmentToCActivityFragment setLogFrameID(long logFrameID) {
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
      return R.id.action_cLogFrameFragment_to_cActivityFragment;
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
      ActionCLogFrameFragmentToCActivityFragment that = (ActionCLogFrameFragmentToCActivityFragment) object;
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
      return "ActionCLogFrameFragmentToCActivityFragment(actionId=" + getActionId() + "){"
          + "logFrameID=" + getLogFrameID()
          + "}";
    }
  }

  public static class ActionCLogFrameFragmentToCQuestionFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private ActionCLogFrameFragmentToCQuestionFragment(long logFrameID) {
      this.arguments.put("logFrameID", logFrameID);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionCLogFrameFragmentToCQuestionFragment setLogFrameID(long logFrameID) {
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
      return R.id.action_cLogFrameFragment_to_cQuestionFragment;
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
      ActionCLogFrameFragmentToCQuestionFragment that = (ActionCLogFrameFragmentToCQuestionFragment) object;
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
      return "ActionCLogFrameFragmentToCQuestionFragment(actionId=" + getActionId() + "){"
          + "logFrameID=" + getLogFrameID()
          + "}";
    }
  }

  public static class ActionCLogFrameFragmentToCIndicatorFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private ActionCLogFrameFragmentToCIndicatorFragment(long logFrameID) {
      this.arguments.put("logFrameID", logFrameID);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionCLogFrameFragmentToCIndicatorFragment setLogFrameID(long logFrameID) {
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
      return R.id.action_cLogFrameFragment_to_cIndicatorFragment;
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
      ActionCLogFrameFragmentToCIndicatorFragment that = (ActionCLogFrameFragmentToCIndicatorFragment) object;
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
      return "ActionCLogFrameFragmentToCIndicatorFragment(actionId=" + getActionId() + "){"
          + "logFrameID=" + getLogFrameID()
          + "}";
    }
  }

  public static class ActionCLogFrameFragmentToCRaidLogFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private ActionCLogFrameFragmentToCRaidLogFragment(long logFrameID) {
      this.arguments.put("logFrameID", logFrameID);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionCLogFrameFragmentToCRaidLogFragment setLogFrameID(long logFrameID) {
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
      return R.id.action_cLogFrameFragment_to_cRaidLogFragment;
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
      ActionCLogFrameFragmentToCRaidLogFragment that = (ActionCLogFrameFragmentToCRaidLogFragment) object;
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
      return "ActionCLogFrameFragmentToCRaidLogFragment(actionId=" + getActionId() + "){"
          + "logFrameID=" + getLogFrameID()
          + "}";
    }
  }

  public static class ActionCLogFrameFragmentToCAWPBFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private ActionCLogFrameFragmentToCAWPBFragment(long logFrameID) {
      this.arguments.put("logFrameID", logFrameID);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionCLogFrameFragmentToCAWPBFragment setLogFrameID(long logFrameID) {
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
      return R.id.action_cLogFrameFragment_to_cAWPBFragment;
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
      ActionCLogFrameFragmentToCAWPBFragment that = (ActionCLogFrameFragmentToCAWPBFragment) object;
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
      return "ActionCLogFrameFragmentToCAWPBFragment(actionId=" + getActionId() + "){"
          + "logFrameID=" + getLogFrameID()
          + "}";
    }
  }

  public static class ActionCLogFrameFragmentToCBookKeepingFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private ActionCLogFrameFragmentToCBookKeepingFragment(long logFrameID) {
      this.arguments.put("logFrameID", logFrameID);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionCLogFrameFragmentToCBookKeepingFragment setLogFrameID(long logFrameID) {
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
      return R.id.action_cLogFrameFragment_to_cBookKeepingFragment;
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
      ActionCLogFrameFragmentToCBookKeepingFragment that = (ActionCLogFrameFragmentToCBookKeepingFragment) object;
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
      return "ActionCLogFrameFragmentToCBookKeepingFragment(actionId=" + getActionId() + "){"
          + "logFrameID=" + getLogFrameID()
          + "}";
    }
  }

  public static class ActionCLogFrameFragmentToCMonitoringFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private ActionCLogFrameFragmentToCMonitoringFragment(long logFrameID) {
      this.arguments.put("logFrameID", logFrameID);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionCLogFrameFragmentToCMonitoringFragment setLogFrameID(long logFrameID) {
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
      return R.id.action_cLogFrameFragment_to_cMonitoringFragment;
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
      ActionCLogFrameFragmentToCMonitoringFragment that = (ActionCLogFrameFragmentToCMonitoringFragment) object;
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
      return "ActionCLogFrameFragmentToCMonitoringFragment(actionId=" + getActionId() + "){"
          + "logFrameID=" + getLogFrameID()
          + "}";
    }
  }
}
