package com.gdj.myview.utils;

import android.util.Log;

public class LogUtils {
    private static final String tag = "MVP";
    public static int logLevel = Log.DEBUG;

    private static String getFunctionName() {
        StackTraceElement[] sts = Thread.currentThread().getStackTrace();

        if (sts == null) {
            return null;
        }

        for (StackTraceElement st : sts) {
            if (st.isNativeMethod()) {
                continue;
            }

            if (st.getClassName().equals(Thread.class.getName())) {
                continue;
            }

            if (st.getClassName().equals(LogUtils.class.getName())) {
                continue;
            }

            return "[" + Thread.currentThread().getId() + ": " + st.getFileName() + ":" + st.getLineNumber() + "]";
        }

        return null;
    }

    public static void info(String message) {
        if (logLevel <= Log.INFO) {
            String name = getFunctionName();
            String ls = (name == null ? message : (name + " - " + message));
            Log.i(tag, ls);
        }
    }

    public static void i(String message) {
        info(message);
    }

    public static void verbose(String message) {
        if (logLevel <= Log.VERBOSE) {
            String name = getFunctionName();
            String ls = (name == null ? message : (name + " - " + message));
            Log.v(tag, ls);
        }
    }

    public static void v(String message) {
        verbose(message);
    }

    public static void warn(String message) {
        if (logLevel <= Log.WARN) {
            String name = getFunctionName();
            String ls = (name == null ? message : (name + " - " + message));
            Log.w(tag, ls);
        }
    }

    public static void w(String message) {
        warn(message);
    }

    public static void error(String message) {
        if (logLevel <= Log.ERROR) {
            String name = getFunctionName();
            String ls = (name == null ? message : (name + " - " + message));
            Log.e(tag, ls);
        }
    }

    public static void error(Throwable ex) {
        if (logLevel <= Log.ERROR) {
            StringBuffer sb = new StringBuffer();
            String name = getFunctionName();
            StackTraceElement[] sts = ex.getStackTrace();

            if (name != null) {
                sb.append(name + " - " + ex + "\r\n");
            } else {
                sb.append(ex + "\r\n");
            }

            if (sts != null && sts.length > 0) {
                for (StackTraceElement st : sts) {
                    if (st != null) {
                        sb.append("[ " + st.getFileName() + ":" + st.getLineNumber() + " ]\r\n");
                    }
                }
            }

            Log.e(tag, sb.toString());
        }
    }

    public static void e(String message) {
        error(message);
    }

    public static void e(Exception ex) {
        error(ex);
    }

    public static void debug(String message) {
        if (logLevel <= Log.DEBUG) {
            String name = getFunctionName();
            String ls = (name == null ? message : (name + " - " + message));
            Log.d(tag, ls);
        }
    }

    public static void d(String message) {
        debug(message);
    }
}