package com.me.mseotsanyana.mande.COM;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.me.mseotsanyana.mande.BRBAC.BLL.cAddressHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by mseotsanyana on 2018/04/17.
 */

public class cVolleyHandler {
    public static final String TAG = cVolleyHandler.class.getSimpleName();

    public static final String BOUNDARY = "ME";

    private Context context;

    public cVolleyHandler(Context context) {
        this.context = context;
    }

    public cVolleyResponse<JSONObject> syncAPI(String url,                  /* request URL */
                                                        String keyTrack,             /* Service name making request */
                                                        int action,                  /* method: GET,POST,PUT,DELETE */
                                                        final JSONObject jsonRequest /* JSONObject data: {fields=params}*/
    ) {

        RequestFuture<JSONObject> future = RequestFuture.newFuture();

        JsonObjectRequest request = new JsonObjectRequest(action, url, jsonRequest, future, future){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            public byte[] getBody() {
                String requestBody = jsonRequest.toString();
                try {
                    Log.d(TAG, "PARAMS IN THE BODY = " + requestBody);
                    return requestBody == null ? null : requestBody.getBytes("utf-8");

                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                            requestBody, "utf-8");
                    return null;
                }
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        cVolleySingleton.getInstance(context).addToRequestQueue(request,
                keyTrack + Calendar.getInstance().getTimeInMillis());

        try {
            JSONObject response = future.get(2, TimeUnit.MINUTES);
            return new cVolleyResponse<>(true, response);
        } catch (InterruptedException e) {
            // exception handling
            return buildErrorMessage(e.getMessage());
        } catch (ExecutionException e) {
            // exception handling
            return buildErrorMessage(e.getMessage());
        } catch (TimeoutException e) {
            // exception handling
            return buildErrorMessage(e.getMessage());
        }
    }

    public cVolleyResponse buildErrorMessage(String errorMessage) {
        cVolleyResponse volleyResponse = new cVolleyResponse(false, null);
        volleyResponse.setErrorMessage(errorMessage);
        return volleyResponse;
    }

    public String buildURL(String baseUrl, Map<String, String> params) {
        int index = 0;
        if (params.size() > 0) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (index == 0) {
                    baseUrl += "?" + entry.getKey() + "=" + entry.getValue();
                } else {
                    baseUrl += "&" + entry.getKey() + "=" + entry.getValue();
                }
                index = index + 1;
            }
        }
        return baseUrl;
    }

    public class cVolleyResponse<T> {
        private boolean isSuccess = false;
        private T response;
        private String errorMessage;

        public cVolleyResponse(boolean isSuccess, T response) {
            this.isSuccess = isSuccess;
            this.response = response;
        }

        public cVolleyHandler.cVolleyResponse setSuccess(boolean success) {
            this.isSuccess = success;
            return this;
        }

        public cVolleyHandler.cVolleyResponse setResponse(T response) {
            this.response = response;
            return this;
        }

        public cVolleyHandler.cVolleyResponse setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
            return this;
        }

        public boolean isSuccess() {
            return this.isSuccess;
        }

        public T getResponse() {
            return this.response;
        }

        public String getErrorMessage() {
            return this.errorMessage;
        }
    }
}

