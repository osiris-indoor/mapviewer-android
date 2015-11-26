package com.fhc25.percepcion.osiris.mapviewer.common.log;

import android.util.Log;

public class Lgr {

    public static void i(final String tag, final String text) {
        Log.i(tag, text);
    }

    public static void w(final String tag, final String text) {
        Log.w(tag, text);
    }

    public static void v(final String tag, final String text) {
        Log.v(tag, text);
    }

    public static void e(String tag, String text) {
        Log.e(tag, text);
    }

    public static void e(String tag, Exception e) {
        String message = "";
        String stackTrace = "";

        if (e.getMessage() != null) {
            message = e.getMessage();
        } else {
            message = "no message in exception";
        }

        if (e.getStackTrace() != null) {
            for (StackTraceElement element : e.getStackTrace()) {
                stackTrace += element.toString() + "\n";
            }
        }

        Log.e(tag, message + stackTrace);
    }

}
