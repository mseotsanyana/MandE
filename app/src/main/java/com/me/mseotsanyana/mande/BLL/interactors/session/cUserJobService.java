package com.me.mseotsanyana.mande.BLL.interactors.session;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

/**
 * Created by mseotsanyana on 2018/04/05.
 */

public class cUserJobService extends JobService {
    private static final String TAG = cUserJobService.class.getSimpleName();

    boolean isWorking    = false;
    boolean jobCancelled = false;

    // Called by the Android system when it's time to run the job
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.d(TAG, "The User JobService has started!");
        isWorking = true;

        // We need 'jobParameters' so we can call 'jobFinished'
        syncUserThread(jobParameters); // Services do NOT run on a separate thread

        return isWorking;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.d(TAG, "The User JobService has completed.");

        jobCancelled = true;
        boolean needsReschedule = isWorking;
        jobFinished(jobParameters, needsReschedule);

        return needsReschedule;
    }

    private void syncUserThread(final JobParameters jobParameters) {
        new Thread(new Runnable() {
            public void run() {

                // start of synchronization algorithm

                // end of synchronization algorithm

                isWorking = false;
                boolean needsReschedule = false;
                jobFinished(jobParameters, needsReschedule);
            }
        }).start();
    }
}
