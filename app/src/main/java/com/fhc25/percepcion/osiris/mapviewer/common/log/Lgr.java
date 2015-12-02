/**
Copyright 2015 Osiris Project Team

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/   

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
