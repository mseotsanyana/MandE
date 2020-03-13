package com.me.mseotsanyana.mande;

import android.annotation.TargetApi;
import android.app.job.JobInfo;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.preference.SwitchPreference;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;

import com.me.mseotsanyana.mande.DAL.storage.managers.cSyncManager;

import java.util.List;

public class cSettingsActivity extends cAppCompatPreferenceActivity {

    private static final String TAG = cSettingsActivity.class.getSimpleName();

    //private cSessionManager session = new cSessionManager(getApplicationContext());

    private cSyncManager scheduleJobServices;

    private SharedPreferences.OnSharedPreferenceChangeListener
            onSharedPreferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
            // 1. enable auto sync
            Boolean enableSync = prefs.getBoolean("perform_sync", false);
            if (enableSync) {
                // 2. selected network type
                int selectedNetworkType = 1;
                String network_type = prefs.getString("network_type", "");
                int networkType = Integer.parseInt(network_type);
                if (networkType == 1) {
                    selectedNetworkType = JobInfo.NETWORK_TYPE_NONE;
                } else if (networkType == 2) {
                    selectedNetworkType = JobInfo.NETWORK_TYPE_ANY;
                } else if (networkType == 3) {
                    selectedNetworkType = JobInfo.NETWORK_TYPE_UNMETERED;
                } else if (networkType == 4) {
                    selectedNetworkType = JobInfo.NETWORK_TYPE_METERED; // cellular network?
                }
                // 3. device requires idling
                Boolean deviceIdling = prefs.getBoolean("device_idle", false);
                // 4. device requires charging
                Boolean deviceCharging = prefs.getBoolean("device_charging", false);
                // 5. selected interval type
                String syncperiod_interval = prefs.getString("syncperiod_interval", "");
                int selectedInterval = Integer.parseInt(syncperiod_interval);

                if (selectedInterval == 1){
                    //prefs.g("number_hours").setEnabled(false);
                }
                // 6. specified recurrence interval hours
                String number_hours = prefs.getString("number_hours", "");
                int intervalHours = Integer.parseInt(number_hours);
                // 7. requires reboot persistence
                Boolean persist = prefs.getBoolean("persist", false);

                /*
                Log.d(TAG, "Enable auto sync          : " + enableSync);
                Log.d(TAG, "Selected network          : " + selectedNetworkType);
                Log.d(TAG, "Device requires idling    : " + deviceIdling);
                Log.d(TAG, "Device requires charging  : " + deviceCharging);
                Log.d(TAG, "Interval type             : " + selectedInterval);
                Log.d(TAG, "Recurrence interval hours : " + intervalHours);
                Log.d(TAG, "Requires persistence      : " + persist);
                */

                PersistableBundle bundle = new PersistableBundle();
                bundle.putInt("selectedNetworkType",selectedNetworkType);
                bundle.putInt("selectedInterval",selectedInterval);
                bundle.putInt("intervalHours",intervalHours);
                bundle.putBoolean("deviceIdling",deviceIdling);
                bundle.putBoolean("deviceCharging",deviceCharging);
                bundle.putBoolean("persist",persist);

                // (re)start the job services
                scheduleJobServices.startServices(bundle);
            } else {
                // stop the job services
                scheduleJobServices.stopServices();
            }
        }
    };

    private static Preference.OnPreferenceChangeListener
            bindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();

            if (preference instanceof CheckBoxPreference) {
                if (preference.getKey().equals("perform_sync")) {
                    preference.setSummary(stringValue);
                    Log.d(TAG, "Enable Auto Sync Value: " + stringValue);
                }
            } else if (preference instanceof EditTextPreference) {
                if (preference.getKey().equals("number_hours")) {
                    preference.setSummary(stringValue);
                    Log.d(TAG, "Number of Minutes Value: " + stringValue);
                }
                /*
                if (preference.getKey().equals("email_address")) {
                    // update the changed gallery name to summary filed
                    preference.setSummary(stringValue);
                }
                */

            } else if (preference instanceof SwitchPreference) {
                if (preference.getKey().equals("device_idle")) {
                    preference.setSummary(stringValue);
                    Log.d(TAG, "Device Idling Value: " + stringValue);
                }
                if (preference.getKey().equals("device_charging")) {
                    preference.setSummary(stringValue);
                    Log.d(TAG, "Device Charging Value: " + stringValue);
                }
                if (preference.getKey().equals("persist")) {
                    preference.setSummary(stringValue);
                    Log.d(TAG, "Persistance Value: " + stringValue);

                }

            } else if (preference instanceof RingtonePreference) {
                // For ringtone preferences, look up the correct display value
                // using RingtoneManager.
                if (TextUtils.isEmpty(stringValue)) {
                    // Empty values correspond to 'silent' (no ringtone).
                    preference.setSummary(R.string.pref_ringtone_silent);

                } else {
                    Ringtone ringtone = RingtoneManager.getRingtone(
                            preference.getContext(), Uri.parse(stringValue));

                    if (ringtone == null) {
                        // Clear the summary if there was a lookup error.
                        preference.setSummary(R.string.summary_choose_ringtone);
                    } else {
                        // Set the summary to reflect the new ringtone display
                        // name.
                        String name = ringtone.getTitle(preference.getContext());
                        preference.setSummary(name);
                    }
                }
                Log.d(TAG, "Ringtone Value: " + stringValue);


            } else if (preference instanceof ListPreference) {
                if (preference.getKey().equals("network_type")) {
                    // For list preferences, look up the correct display value in
                    // the preference's 'entries' list.
                    ListPreference listPreference = (ListPreference) preference;
                    int index = listPreference.findIndexOfValue(stringValue);

                    // Set the summary to reflect the new value.
                    preference.setSummary(
                            index >= 0
                                    ? listPreference.getEntries()[index]
                                    : null);
                    Log.d(TAG, "Network Type Value: " + stringValue);
                } else if (preference.getKey().equals("syncperiod_interval")) {
                    ListPreference listPreference = (ListPreference) preference;
                    int index = listPreference.findIndexOfValue(stringValue);

                    // Set the summary to reflect the new value.
                    preference.setSummary(
                            index >= 0
                                    ? listPreference.getEntries()[index]
                                    : null);
                    Log.d(TAG, "Sync Period Interval Value: " + stringValue);
                } else {
                    preference.setSummary(stringValue);
                    Log.d(TAG, "Other Value: " + stringValue);
                }
            }
/*
            Log.d(TAG, "Enable Auto Sync: " + preference.getKey().equals("perform_sync"));
            Log.d(TAG, "Number of Hours: " + preference.getKey().equals("number_hours"));
            Log.d(TAG, "Device Idling: " + preference.getKey().equals("device_idle"));
            Log.d(TAG, "Device Charging: " + preference.getKey().equals("device_charging"));
            Log.d(TAG, "Persist: " + preference.getKey().equals("persist"));
            Log.d(TAG, "Network Type: " + preference.getKey().equals("network_type"));
            Log.d(TAG, "Sync Periodic Interval: " + preference.getKey().equals("syncperiod_interval"));
*/
            return true;
        }
    };

    /**
     * Helper method to determine if the device has an extra-large screen. For
     * example, 10" tablets are extra-large.
     */
    private static boolean isLargeTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    private static void bindPreferenceSummaryToValue(Preference preference) {
        preference.setOnPreferenceChangeListener(bindPreferenceSummaryToValueListener);

        if ((preference instanceof CheckBoxPreference) || (preference instanceof SwitchPreference)) {
            bindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                    PreferenceManager
                            .getDefaultSharedPreferences(preference.getContext())
                            .getBoolean(preference.getKey(), false));
        } else if (preference instanceof ListPreference) {
            bindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                    PreferenceManager
                            .getDefaultSharedPreferences(preference.getContext())
                            .getString(preference.getKey(), ""));
        } else if (preference instanceof EditTextPreference) {
            bindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                    PreferenceManager
                            .getDefaultSharedPreferences(preference.getContext())
                            .getString(preference.getKey(), ""));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActionBar();

        PreferenceManager.setDefaultValues(this, R.xml.pref_data_sync, false);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);

       scheduleJobServices = new cSyncManager(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.unregisterOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onIsMultiPane() {
        return isLargeTablet(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.pref_headers, target);
    }

    /**
     * This method stops fragment injection in malicious applications.
     * Make sure to deny any unknown fragments here.
     */
    protected boolean isValidFragment(String fragmentName) {
        return PreferenceFragment.class.getName().equals(fragmentName)
                || GeneralPreferenceFragment.class.getName().equals(fragmentName);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class GeneralPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            //int preferenceFile_toLoad = -1;
            String settings = getArguments().getString("settings");

            if ("prefs_general".equalsIgnoreCase(settings)) {
                // load general preferences from an XML resource
                addPreferencesFromResource(R.xml.pref_general);
                // bind general summary values to saved preference values
                bindPreferenceSummaryToValue(findPreference(getString(R.string.full_name)));
                bindPreferenceSummaryToValue(findPreference(getString(R.string.email_address)));
            } else if ("prefs_notification".equalsIgnoreCase(settings)) {
                // load the notification preferences from an XML resource
                addPreferencesFromResource(R.xml.pref_notification);
                // bind notivication summary value to saved preference values
                bindPreferenceSummaryToValue(findPreference(getString(R.string.notifications_new_message)));
                bindPreferenceSummaryToValue(findPreference(getString(R.string.notification_ringtone)));
            } else if ("prefs_sync".equals(settings)) {
                // load synchronization preferences from an XML resource
                addPreferencesFromResource(R.xml.pref_data_sync);
                // bind synchronization summary value to saved preference values
                bindPreferenceSummaryToValue(findPreference(getString(R.string.perform_sync)));
                bindPreferenceSummaryToValue(findPreference(getString(R.string.network_type)));
                bindPreferenceSummaryToValue(findPreference(getString(R.string.device_idle)));
                bindPreferenceSummaryToValue(findPreference(getString(R.string.device_charging)));
                bindPreferenceSummaryToValue(findPreference(getString(R.string.syncperiod_interval)));
                bindPreferenceSummaryToValue(findPreference(getString(R.string.number_hours)));
                bindPreferenceSummaryToValue(findPreference(getString(R.string.persist)));
            }
        }
    }
}