package com.me.mseotsanyana.mande.UTILITY.COM;

import android.view.View;

/**
 * Created by mseotsanyana on 2016/11/26.
 */

public class cEvent {
    // Event used to send message from activity to fragment.
    public static class cActivityFragmentMessage {
        private int message;
        public cActivityFragmentMessage(int message) {
            this.message = message;
        }
        public int getMessage() {
            return message;
        }
    }

    // cEvent used to send message from project fragment to main activity.
    public static class cProjectActivityMessage {
        private View message;

        public cProjectActivityMessage(View message) {
            this.message = message;
        }
        public View getMessage() {
            return message;
        }
    }

    // Event used to send message from activity to adapter.
    public static class cActivityAdapterMessage {
        private String message;
        public cActivityAdapterMessage(String message) {
            this.message = message;
        }
        public String getMessage() {
            return message;
        }
    }

    // Event used to send message from activity to activity.
    public static class cActivityActivityMessage {
        private String message;
        public cActivityActivityMessage(String message) {
            this.message = message;
        }
        public String getMessage() {
            return message;
        }
    }

    // Event used to send message from handler to fragment.
    public static class cHandlerFragmentMessage {
        private String message;
        public cHandlerFragmentMessage(String message) {
            this.message = message;
        }
        public String getMessage() {
            return message;
        }
    }

    // Event used to send message from handler to fragment.
    public static class cPermissionFragmentMessage {
        private int message;
        public cPermissionFragmentMessage(int message) {
            this.message = message;
        }
        public int getMessage() {
            return message;
        }
    }


}
