package com.me.mseotsanyana.mande.PL.ui.fragments.logframe;

import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.navigation.NavArgs;
import com.me.mseotsanyana.mande.BLL.model.logframe.cOutcomeModel;
import com.me.mseotsanyana.mande.BLL.model.logframe.cQuestionModel;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.lang.System;
import java.util.HashMap;

public class cImpactDetailFragmentArgs implements NavArgs {
  private final HashMap arguments = new HashMap();

  private cImpactDetailFragmentArgs() {
  }

  private cImpactDetailFragmentArgs(HashMap argumentsMap) {
    this.arguments.putAll(argumentsMap);
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static cImpactDetailFragmentArgs fromBundle(@NonNull Bundle bundle) {
    cImpactDetailFragmentArgs __result = new cImpactDetailFragmentArgs();
    bundle.setClassLoader(cImpactDetailFragmentArgs.class.getClassLoader());
    if (bundle.containsKey("outcomeModels")) {
      cOutcomeModel[] outcomeModels;
      Parcelable[] __array = bundle.getParcelableArray("outcomeModels");
      if (__array != null) {
        outcomeModels = new cOutcomeModel[__array.length];
        System.arraycopy(__array, 0, outcomeModels, 0, __array.length);
      } else {
        outcomeModels = null;
      }
      if (outcomeModels == null) {
        throw new IllegalArgumentException("Argument \"outcomeModels\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("outcomeModels", outcomeModels);
    } else {
      throw new IllegalArgumentException("Required argument \"outcomeModels\" is missing and does not have an android:defaultValue");
    }
    if (bundle.containsKey("questionModels")) {
      cQuestionModel[] questionModels;
      Parcelable[] __array = bundle.getParcelableArray("questionModels");
      if (__array != null) {
        questionModels = new cQuestionModel[__array.length];
        System.arraycopy(__array, 0, questionModels, 0, __array.length);
      } else {
        questionModels = null;
      }
      if (questionModels == null) {
        throw new IllegalArgumentException("Argument \"questionModels\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("questionModels", questionModels);
    } else {
      throw new IllegalArgumentException("Required argument \"questionModels\" is missing and does not have an android:defaultValue");
    }
    return __result;
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

  @SuppressWarnings("unchecked")
  @NonNull
  public Bundle toBundle() {
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
  public boolean equals(Object object) {
    if (this == object) {
        return true;
    }
    if (object == null || getClass() != object.getClass()) {
        return false;
    }
    cImpactDetailFragmentArgs that = (cImpactDetailFragmentArgs) object;
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
    return true;
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + java.util.Arrays.hashCode(getOutcomeModels());
    result = 31 * result + java.util.Arrays.hashCode(getQuestionModels());
    return result;
  }

  @Override
  public String toString() {
    return "cImpactDetailFragmentArgs{"
        + "outcomeModels=" + getOutcomeModels()
        + ", questionModels=" + getQuestionModels()
        + "}";
  }

  public static class Builder {
    private final HashMap arguments = new HashMap();

    public Builder(cImpactDetailFragmentArgs original) {
      this.arguments.putAll(original.arguments);
    }

    public Builder(@NonNull cOutcomeModel[] outcomeModels,
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
    public cImpactDetailFragmentArgs build() {
      cImpactDetailFragmentArgs result = new cImpactDetailFragmentArgs(arguments);
      return result;
    }

    @NonNull
    public Builder setOutcomeModels(@NonNull cOutcomeModel[] outcomeModels) {
      if (outcomeModels == null) {
        throw new IllegalArgumentException("Argument \"outcomeModels\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("outcomeModels", outcomeModels);
      return this;
    }

    @NonNull
    public Builder setQuestionModels(@NonNull cQuestionModel[] questionModels) {
      if (questionModels == null) {
        throw new IllegalArgumentException("Argument \"questionModels\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("questionModels", questionModels);
      return this;
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
  }
}
