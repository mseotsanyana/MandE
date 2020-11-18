package com.me.mseotsanyana.mande.PL.ui.fragments.logframe;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.navigation.NavArgs;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class cActivityFragmentArgs implements NavArgs {
  private final HashMap arguments = new HashMap();

  private cActivityFragmentArgs() {
  }

  private cActivityFragmentArgs(HashMap argumentsMap) {
    this.arguments.putAll(argumentsMap);
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static cActivityFragmentArgs fromBundle(@NonNull Bundle bundle) {
    cActivityFragmentArgs __result = new cActivityFragmentArgs();
    bundle.setClassLoader(cActivityFragmentArgs.class.getClassLoader());
    if (bundle.containsKey("logFrameID")) {
      long logFrameID;
      logFrameID = bundle.getLong("logFrameID");
      __result.arguments.put("logFrameID", logFrameID);
    } else {
      throw new IllegalArgumentException("Required argument \"logFrameID\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  public long getLogFrameID() {
    return (long) arguments.get("logFrameID");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public Bundle toBundle() {
    Bundle __result = new Bundle();
    if (arguments.containsKey("logFrameID")) {
      long logFrameID = (long) arguments.get("logFrameID");
      __result.putLong("logFrameID", logFrameID);
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
    cActivityFragmentArgs that = (cActivityFragmentArgs) object;
    if (arguments.containsKey("logFrameID") != that.arguments.containsKey("logFrameID")) {
      return false;
    }
    if (getLogFrameID() != that.getLogFrameID()) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + (int)(getLogFrameID() ^ (getLogFrameID() >>> 32));
    return result;
  }

  @Override
  public String toString() {
    return "cActivityFragmentArgs{"
        + "logFrameID=" + getLogFrameID()
        + "}";
  }

  public static class Builder {
    private final HashMap arguments = new HashMap();

    public Builder(cActivityFragmentArgs original) {
      this.arguments.putAll(original.arguments);
    }

    public Builder(long logFrameID) {
      this.arguments.put("logFrameID", logFrameID);
    }

    @NonNull
    public cActivityFragmentArgs build() {
      cActivityFragmentArgs result = new cActivityFragmentArgs(arguments);
      return result;
    }

    @NonNull
    public Builder setLogFrameID(long logFrameID) {
      this.arguments.put("logFrameID", logFrameID);
      return this;
    }

    @SuppressWarnings("unchecked")
    public long getLogFrameID() {
      return (long) arguments.get("logFrameID");
    }
  }
}
