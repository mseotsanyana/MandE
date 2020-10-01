package com.me.mseotsanyana.mande.PL.ui.fragments.evaluator;

import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.navigation.NavArgs;
import com.me.mseotsanyana.questionnairelibrary.forms.db.cDBQuestionnaire;
import java.io.Serializable;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class cEvaluationFormFragmentArgs implements NavArgs {
  private final HashMap arguments = new HashMap();

  private cEvaluationFormFragmentArgs() {
  }

  private cEvaluationFormFragmentArgs(HashMap argumentsMap) {
    this.arguments.putAll(argumentsMap);
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static cEvaluationFormFragmentArgs fromBundle(@NonNull Bundle bundle) {
    cEvaluationFormFragmentArgs __result = new cEvaluationFormFragmentArgs();
    bundle.setClassLoader(cEvaluationFormFragmentArgs.class.getClassLoader());
    if (bundle.containsKey("dbQuestionnaire")) {
      cDBQuestionnaire dbQuestionnaire;
      if (Parcelable.class.isAssignableFrom(cDBQuestionnaire.class) || Serializable.class.isAssignableFrom(cDBQuestionnaire.class)) {
        dbQuestionnaire = (cDBQuestionnaire) bundle.get("dbQuestionnaire");
      } else {
        throw new UnsupportedOperationException(cDBQuestionnaire.class.getName() + " must implement Parcelable or Serializable or must be an Enum.");
      }
      if (dbQuestionnaire == null) {
        throw new IllegalArgumentException("Argument \"dbQuestionnaire\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("dbQuestionnaire", dbQuestionnaire);
    } else {
      throw new IllegalArgumentException("Required argument \"dbQuestionnaire\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public cDBQuestionnaire getDbQuestionnaire() {
    return (cDBQuestionnaire) arguments.get("dbQuestionnaire");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public Bundle toBundle() {
    Bundle __result = new Bundle();
    if (arguments.containsKey("dbQuestionnaire")) {
      cDBQuestionnaire dbQuestionnaire = (cDBQuestionnaire) arguments.get("dbQuestionnaire");
      if (Parcelable.class.isAssignableFrom(cDBQuestionnaire.class) || dbQuestionnaire == null) {
        __result.putParcelable("dbQuestionnaire", Parcelable.class.cast(dbQuestionnaire));
      } else if (Serializable.class.isAssignableFrom(cDBQuestionnaire.class)) {
        __result.putSerializable("dbQuestionnaire", Serializable.class.cast(dbQuestionnaire));
      } else {
        throw new UnsupportedOperationException(cDBQuestionnaire.class.getName() + " must implement Parcelable or Serializable or must be an Enum.");
      }
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
    cEvaluationFormFragmentArgs that = (cEvaluationFormFragmentArgs) object;
    if (arguments.containsKey("dbQuestionnaire") != that.arguments.containsKey("dbQuestionnaire")) {
      return false;
    }
    if (getDbQuestionnaire() != null ? !getDbQuestionnaire().equals(that.getDbQuestionnaire()) : that.getDbQuestionnaire() != null) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + (getDbQuestionnaire() != null ? getDbQuestionnaire().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "cEvaluationFormFragmentArgs{"
        + "dbQuestionnaire=" + getDbQuestionnaire()
        + "}";
  }

  public static class Builder {
    private final HashMap arguments = new HashMap();

    public Builder(cEvaluationFormFragmentArgs original) {
      this.arguments.putAll(original.arguments);
    }

    public Builder(@NonNull cDBQuestionnaire dbQuestionnaire) {
      if (dbQuestionnaire == null) {
        throw new IllegalArgumentException("Argument \"dbQuestionnaire\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("dbQuestionnaire", dbQuestionnaire);
    }

    @NonNull
    public cEvaluationFormFragmentArgs build() {
      cEvaluationFormFragmentArgs result = new cEvaluationFormFragmentArgs(arguments);
      return result;
    }

    @NonNull
    public Builder setDbQuestionnaire(@NonNull cDBQuestionnaire dbQuestionnaire) {
      if (dbQuestionnaire == null) {
        throw new IllegalArgumentException("Argument \"dbQuestionnaire\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("dbQuestionnaire", dbQuestionnaire);
      return this;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public cDBQuestionnaire getDbQuestionnaire() {
      return (cDBQuestionnaire) arguments.get("dbQuestionnaire");
    }
  }
}
