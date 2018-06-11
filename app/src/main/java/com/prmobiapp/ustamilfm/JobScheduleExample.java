package com.prmobiapp.ustamilfm;

import android.annotation.TargetApi;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.bumptech.glide.util.Util;

/**
 * Created by yasar on 22/2/18.
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class JobScheduleExample extends JobService {
    private static final String TAG = "SyncService";





    @Override
    public boolean onStartJob(JobParameters params) {


        Log.e(TAG, "onStartJob: callled" );





//        Intent service = new Intent(getApplicationContext(), MyService.class);
//        getApplicationContext().startService(service);
        scheduleJob(getApplicationContext()); // reschedule the job
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public static void scheduleJob(Context context) {
        ComponentName serviceComponent = new ComponentName(context, JobScheduleExample.class);
        JobInfo.Builder builder = new JobInfo.Builder(0, serviceComponent);
        builder.setMinimumLatency(1 * 1000); // wait at least
        builder.setOverrideDeadline(1 * 1000); // maximum delay
        //builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED); // require unmetered network
        //builder.setRequiresDeviceIdle(true); // device should be idle
        //builder.setRequiresCharging(false); // we don't care if the device is charging or not
        JobScheduler jobScheduler = context.getSystemService(JobScheduler.class);
        jobScheduler.schedule(builder.build());
    }
}
