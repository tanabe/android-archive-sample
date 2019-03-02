package com.kaihatsubu.android.safetynetapimodule;

import android.app.AlertDialog;
import android.content.Context;

public class SafetyNetAPIUtil {
    static public void showMessage(Context context, String title, String message) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .setNegativeButton("Cancel", null)
                .show();
    }
}
