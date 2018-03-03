package io.citizenhealth.humanapi.bridge;

import android.support.annotation.Nullable;
import android.util.Log;
import android.os.Bundle;

import com.facebook.react.bridge.ReadableMap;

public class ConnectOptions {
    private static final String TAG = ConnectOptions.class.getName();

    public static final String CLIENT_ID = "client_id";
    public static final String AUTH_URL = "auth_url";
    public static final String CLIENT_USER_ID = "client_user_id";
    public static final String LANGUAGE = "language";
    public static final String PUBLIC_TOKEN = "public_token";
    public static final String HUMAN_ID = "human_id";
    public static final String SESSION_TOKEN = "session_token";

    private String clientID = "";
    private String authURL = "";
    private String clientUserID = "";
    private String language = "";
    private String publicToken = "";

    public ConnectOptions(@Nullable ReadableMap options) {
        if (options == null) {
            return;
        }

        if (options.hasKey(CLIENT_ID)) {
            clientID = options.getString(CLIENT_ID);
            Log.d(TAG, CLIENT_ID + clientID);
        }

        if (options.hasKey(AUTH_URL)) {
            authURL = options.getString(AUTH_URL);
            Log.d(TAG, AUTH_URL + authURL);
        }

        if (options.hasKey(CLIENT_USER_ID)) {
            clientUserID = options.getString(CLIENT_USER_ID);
            Log.d(TAG, CLIENT_USER_ID + clientUserID);
        }

        if (options.hasKey(LANGUAGE)) {
            language = options.getString(LANGUAGE);
            Log.d(TAG, LANGUAGE + language);
        }

        if (options.hasKey(PUBLIC_TOKEN)) {
            publicToken = options.getString(PUBLIC_TOKEN);
            Log.d(TAG, PUBLIC_TOKEN + publicToken);
        }
    }

    public Bundle getBundle() {
        Bundle b = new Bundle();

        b.putString(CLIENT_ID, clientID);
        b.putString(AUTH_URL, authURL);
        b.putString(CLIENT_USER_ID, clientUserID);

        if (language.trim().length() > 0)
            b.putString(LANGUAGE, language);

        if (publicToken.trim().length() > 0)
            b.putString(PUBLIC_TOKEN, publicToken);

        return b;
    }
}
