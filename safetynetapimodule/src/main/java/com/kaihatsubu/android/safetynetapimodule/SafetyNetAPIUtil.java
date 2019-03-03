package com.kaihatsubu.android.safetynetapimodule;

import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.safetynet.SafetyNetClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.SecureRandom;

public class SafetyNetAPIUtil {

    // You must obtain api key via GCP.
    static private final String API_KEY = "hogehoge";

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

    static byte[] getRequestNonce(String data) {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        byte[] bytes = new byte[24];
        SecureRandom random = new SecureRandom();
        random.nextBytes(bytes);
        try {
            byteStream.write(bytes);
            byteStream.write(data.getBytes());
        } catch (IOException e) {
            return null;
        }

        return byteStream.toByteArray();
    }

    static void sendSafetyNetRequest(Context context) {
        String nonceData = "Safety Net Sample: " + System.currentTimeMillis();
        byte[] nonce = getRequestNonce(nonceData);
        SafetyNetClient client = SafetyNet.getClient(context);

        OnSuccessListener<SafetyNetApi.AttestationResponse> successListener = new OnSuccessListener<SafetyNetApi.AttestationResponse>() {
            @Override
            public void onSuccess(SafetyNetApi.AttestationResponse attestationResponse) {
                // TODO send notification to host app.
                Log.d("tag_name", attestationResponse.getJwsResult().toString());
            }
        };

        OnFailureListener failureListener = new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // TODO send notification to host app.
                Log.d("tag_name", "failure");
            }
        };

        Task<SafetyNetApi.AttestationResponse> task = client.attest(nonce, SafetyNetAPIUtil.API_KEY);
        task.addOnSuccessListener(successListener);
        task.addOnFailureListener(failureListener);

    }

}
