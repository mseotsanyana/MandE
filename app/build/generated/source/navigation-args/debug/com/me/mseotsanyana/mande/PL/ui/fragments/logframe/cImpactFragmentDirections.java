package com.me.mseotsanyana.mande.PL.ui.fragments.logframe;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import com.me.mseotsanyana.mande.BLL.model.logframe.cOutcomeModel;
import com.me.mseotsanyana.mande.BLL.model.logframe.cQuestionModel;
import com.me.mseotsanyana.mande.R;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class cImpactFragmentDirections {
  private cImpactFragmentDirections() {
  }

  @NonNull
  public static ActionCImpactFragmentToCImpactDetailFragment actionCImpactFragmentToCImpactDetailFragment(
      @NonNull cOutcomeModel[] outcomeModels, @NonNull cQuestionModel[] questionModels) {
    return new ActionCImpactFragmentToCImpactDetailFragment(outcomeModels, questionModels);
  }

  public static class ActionCImpactFragmentToCImpactDetailFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    private ActionCImpactFragmentToCImpactDetailFragment(@NonNull cOutcomeModel[] outcomeModels,
        @NonNull cQuestionModel[] questionModels) {
      if (outcomeModels == null) {
        throw new IllegalArgumentException("Argument \"outcomeModels\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("outcomeModels", outcomeModels);
      if (questionModels == null) {
        throw new IllegalArgumentException("Argument \"questionModels\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("questionModels", questionModels);
    }

    @NonNull
    public ActionCImpactFragmentToCImpactDetailFragment setOutcomeModels(
        @NonNull cOutcomeModel[] outcomeModels) {
      if (outcomeModels == null) {
        throw new IllegalArgumentException("Argument \"outcomeModels\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("outcomeModels", outcomeModels);
      return this;
    }

    @NonNull
    public ActionCImpactFragmentToCImpactDetailFragment setQuestionModels(
        @NonNull cQuestionModel[] questionModels) {
      if (questionModels == null) {
        throw new IllegalArgumentException("Argument \"questionModels\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("questionModels", questionModels);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
      Bundle __result = new Bundle();
      if (arguments.containsKey("outcomeModels")) {
        cOutcomeModel[] outcomeModels = (cOutcomeModel[]) arguments.get("outcomeModels");
        __result.putParcelableArray("outcomeModels", outcomeModels);
      }
      if (arguments.containsKey("questionModels")) {
        cQuestionModel[] questionModels = (cQuestionModel[]) arguments.get("questionModels");
        __result.putParcelableArray("questionModels", questionModels);
      }
      return __result;
    }

    @Override
    public int getActionId() {
      return R.id.action_cImpactFragment_to_cImpactDetailFragment;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public cOutcomeModel[] getOutcomeModels() {
      return (cOutcomeModel[]) arguments.get("outcomeModels");
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public cQuestionModel[] getQuestionModels() {
      return (cQuestionModel[]) arguments.get("questionModels");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionCImpactFragmentToCImpactDetailFragment that = (ActionCImpactFragmentToCImpactDetailFragment) object;
      if (arguments.containsKey("outcomeModels") != that.arguments.containsKey("outcomeModels")) {
        return false;
      }
      if (getOutcomeModels() != null ? !getOutcomeModels().equals(that.getOutcomeModels()) : that.getOutcomeModels() != null) {
        return false;
      }
      if (arguments.containsKey("questionModels") != that.arguments.containsKey("questionModels")) {
        return false;
      }
      if (getQuestionModels() != null ? !getQuestionModels().equals(that.getQuestionModels()) : that.getQuestionModels() != null) {
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
      result = 31 * result + java.util.Arrays.hashCode(getOutcomeModels());
      result = 31 * result + java.util.Arrays.hashCode(getQuestionModels());
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionCImpactFragmentToCImpactDetailFragment(actionId=" + getActionId() + "){"
          + "outcomeModels=" + getOutcomeModels()
          + ", questionModels=" + getQuestionModels()
          + "}";
    }
  }
}
