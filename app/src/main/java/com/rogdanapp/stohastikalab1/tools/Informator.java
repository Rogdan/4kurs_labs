package com.rogdanapp.stohastikalab1.tools;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Рома on 15.06.2017.
 */

public class Informator {
    private static final String TAG = "Rogdan";

    public static void log(String message){
        Log.i(TAG, message);
    }

    public static void toast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void error(Class cls, String error){
        Log.e(TAG, "ERROR in " + cls.getCanonicalName() + ": " + error);
    }

    public static void toast(Context context, int messageResId){
        Toast.makeText(context, messageResId, Toast.LENGTH_SHORT).show();
    }
}
