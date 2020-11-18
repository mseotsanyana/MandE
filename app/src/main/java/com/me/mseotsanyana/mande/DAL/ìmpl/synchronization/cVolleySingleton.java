package com.me.mseotsanyana.mande.DAL.ìmpl.synchronization;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Volley;

/**
 * Created by mseotsanyana on 2018/03/28.
 */

public class cVolleySingleton {
    public static final String TAG = "cVolleySingleton";

    private static cVolleySingleton singletonInstance;
    private RequestQueue requestQueue;
    private static Context context;

    private cVolleySingleton(Context context) {
        this.context = context;
        this.requestQueue = getRequestQueue();

    }

    public static synchronized cVolleySingleton getInstance(Context context) {
        if (singletonInstance == null) {
            singletonInstance = new cVolleySingleton(context);
        }
        return singletonInstance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);

        VolleyLog.d("Adding request to queue: %s", req.getUrl());

        getRequestQueue().add(req);
    }
}
