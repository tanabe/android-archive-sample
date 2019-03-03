package com.kaihatsubu.android.safetynetapimodule;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.safetynet.SafetyNetClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class SafetyNetAPIUtil {
    static public void showMessage(Context context, String title, String message) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .setNegativeButton("Cancel", null)
                .show();
    }

    static public boolean isGooglePlayServicesAvailable(Context context) {
        GoogleApiAvailability instance = GoogleApiAvailability.getInstance();
        int result = instance.isGooglePlayServicesAvailable(context);
        Log.d("tag_name","result is: " + result);
        if (result == ConnectionResult.SUCCESS) {
            return true;
        } else {
            return false;
        }
    }
}
