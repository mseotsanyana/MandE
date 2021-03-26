package com.me.mseotsanyana.mande.PL.ui.fragments.session;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.navigation.NavArgs;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class cHomeFragmentArgs implements NavArgs {
  private final HashMap arguments = new HashMap();

  private cHomeFragmentArgs() {
  }

  private cHomeFragmentArgs(HashMap argumentsMap) {
    this.arguments.putAll(argumentsMap);
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static cHomeFragmentArgs fromBundle(@NonNull Bundle bundle) {
    cHomeFragmentArgs __result = new cHomeFragmentArgs();
    bundle.setClassLoader(cHomeFragmentArgs.class.getClassLoader());
    if (bundle.containsKey("userEmail")) {
      String userEmail;
      userEmail = bundle.getString("userEmail");
      if (userEmail == null) {
        throw new IllegalArgumentException("Argument \"userEmail\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("userEmail", userEmail);
    } else {
      throw new IllegalArgumentException("Required argument \"userEmail\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public String getUserEmail() {
    return (String) arguments.get("userEmail");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public Bundle toBundle() {
    Bundle __result = new Bundle();
    if (arguments.containsKey("userEmail")) {
      String userEmail = (String) arguments.get("userEmail");
      __result.putString("userEmail", userEmail);
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
    cHomeFragmentArgs that = (cHomeFragmentArgs) object;
    if (arguments.containsKey("userEmail") != that.arguments.containsKey("userEmail")) {
      return false;
    }
    if (getUserEmail() != null ? !getUserEmail().equals(that.getUserEmail()) : that.getUserEmail() != null) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + (getUserEmail() != null ? getUserEmail().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "cHomeFragmentArgs{"
        + "userEmail=" + getUserEmail()
        + "}";
  }

  public static class Builder {
    private final HashMap arguments = new HashMap();

    public Builder(cHomeFragmentArgs original) {
      this.arguments.putAll(original.arguments);
    }

    public Builder(@NonNull String userEmail) {
      if (userEmail == null) {
        throw new IllegalArgumentException("Argument \"userEmail\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("userEmail", userEmail);
    }

    @NonNull
    public cHomeFragmentArgs build() {
      cHomeFragmentArgs result = new cHomeFragmentArgs(arguments);
      return result;
    }

    @NonNull
    public Builder setUserEmail(@NonNull String userEmail) {
      if (userEmail == null) {
        throw new IllegalArgumentException("Argument \"userEmail\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("userEmail", userEmail);
      return this;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public String getUserEmail() {
      return (String) arguments.get("userEmail");
    }
  }
}
