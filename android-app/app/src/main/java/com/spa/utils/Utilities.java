/**
 * This file contain all common functions which are use in application
 */
package com.spa.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class Utilities {

    private static PrintWriter exceptionLogWriter = null;

    /*
      * Method to get KeyHash
      * */
    public static void GetKeyHash(Activity activity) {
        try {
            PackageInfo info = activity.getPackageManager().getPackageInfo(
                    "com.spa.servicedealz",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

    public static void logExceptionToFile(String extraMessage,
                                          Throwable exceptionToLog) {
        try {
            if (null == exceptionLogWriter)
                initializeExceptionLogFile();

            if (null != exceptionLogWriter) {
                StringBuffer logBuffer = new StringBuffer();
                logBuffer.append("------------------ " + new Date()
                        + " ------------------\n");
                logBuffer.append("\n*******************************\n");
                if (null != extraMessage) {
                    logBuffer.append(extraMessage + "\n");
                }
                exceptionLogWriter.println(logBuffer.toString());
                if (null != exceptionToLog)
                    exceptionToLog.printStackTrace(exceptionLogWriter);
                exceptionLogWriter.println();
            }

            if (null == extraMessage)
                extraMessage = "extra message";
            if (null != exceptionToLog) {
                Log.e("VMDUtil", extraMessage, exceptionToLog);
            } else {
                Log.e("VMDUtil", extraMessage);
            }

        } catch (Throwable e) {
            Log.e("Utilities", "exception in writing to exception log file.", e);
        }
    }

    public static void logExceptionToFile(Throwable exceptionToLog) {
//        logExceptionToFile(null, exceptionToLog);
    }

    private static void initializeExceptionLogFile() {
        try {
            if (null != exceptionLogWriter)
                return;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd_MM_yy");
            String logFileName = "log.log";

            File directory = new File(Environment.getExternalStorageDirectory()
                    .toString() + "/ServiceDealExceptionDir");
            boolean b = directory.mkdirs();

            if (b) {

            } else {
                directory.mkdirs();
            }

            logFileName = "ServiceDealz_exception_"
                    + simpleDateFormat.format(new Date()) + ".txt";
            File logFile = new File(directory, logFileName);
            if (!logFile.exists()) {
                logFile.createNewFile();
            }
            exceptionLogWriter = new PrintWriter(new FileOutputStream(logFile,
                    true), true);

        } catch (Throwable e) {
            Log.e("Utilities ", "initializing exception log file", e);
        }
    }

    public static void logToFile(String FIleName, String extraMessage) {
       /* try {
            if (null == exceptionLogWriter)
                initializeLogFile(FIleName);

            if (null != exceptionLogWriter) {
                StringBuffer logBuffer = new StringBuffer();
                logBuffer.append("------------------ " + new Date()
                        + " ------------------\n");
                logBuffer.append("\n**************"+FIleName+"*****************\n");
                if (null != extraMessage) {
                    logBuffer.append(extraMessage + "\n");
                }
                exceptionLogWriter.println(logBuffer.toString());
               *//* if (null != exceptionToLog)
                    exceptionToLog.printStackTrace(exceptionLogWriter);
                exceptionLogWriter.println();*//*
            }

            if (null == extraMessage)
                extraMessage = "extra message";
            if (null != extraMessage) {
                Log.e("VMDUtil", extraMessage);
            } else {
                Log.e("VMDUtil", extraMessage);
            }

        } catch (Throwable e) {
            Log.e("Utilities", "exception in writing to exception log file.", e);
        }*/
    }


    private static void initializeLogFile(String filename) {
        try {
            if (null != exceptionLogWriter)
                return;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd_MM_yy");
            String logFileName = "log.log";

            File directory = new File(Environment.getExternalStorageDirectory()
                    .toString() + "/ServiceDealLogDir");
            boolean b = directory.mkdirs();

            if (b) {

            } else {
                directory.mkdirs();
            }

            logFileName = "ServiceDealzLog"
                    + simpleDateFormat.format(new Date()) + ".txt";
            File logFile = new File(directory, logFileName);
            if (!logFile.exists()) {
                logFile.createNewFile();
            }
            exceptionLogWriter = new PrintWriter(new FileOutputStream(logFile,
                    true), true);

        } catch (Throwable e) {
            Log.e("Utilities ", "initializing exception log file", e);
        }
    }

}
