package io.citizenhealth.humanapi;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;
import android.os.Bundle;

import io.citizenhealth.humanapi.bridge.ConnectOptions;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;

public class RNReactNativeHumanApi extends ReactContextBaseJavaModule implements ActivityEventListener {

  private final ReactApplicationContext reactContext;
  private final static String TAG = RNReactNativeHumanApi.class.getName();

  private final static int HUMANAPI_AUTH = 1;
  private final static int RESULT_OK = -1;
  private final static int RESULT_CANCELED = 0;
  private final static int RESULT_FIRST_USER = 1;

  private Callback humanCallback;
  public RNReactNativeHumanApi(ReactApplicationContext reactContext) {
    super(reactContext);
    // Add the listener for `onActivityResult`
    reactContext.addActivityEventListener(this);

    this.reactContext = reactContext;
  }

  /**
   * This method is exported to JS
   */
  @ReactMethod
  public void onConnect(@Nullable ReadableMap options, Callback callback) {
    Log.d("hapi-home", "callback " + callback);
    humanCallback = callback;

    ConnectOptions connectOptions = new ConnectOptions(options);

    Activity activity = getCurrentActivity();

    if (activity == null) {
      // invokeAuthCallback(getReactApplicationContext().getString(R.string.com_auth0_android_react_native_lock_no_activity), null, null);
      return;
    }

    Intent intent = new Intent(activity, co.humanapi.connectsdk.ConnectActivity.class);

    intent.putExtras(connectOptions.getBundle());

    activity.startActivityForResult(intent, HUMANAPI_AUTH);
  }

  private boolean invokeCallback(int resultCode, Intent data) {
    Log.d("hapi-home", "humanCallback " + humanCallback);
    if (humanCallback == null) {
      Log.e(TAG, "Invalid/old callback called!");
      return false;
    }

    Log.d("hapi-home", "resultCode " + resultCode);
    WritableMap dataMap = null;
    if (resultCode == RESULT_FIRST_USER) {
      if (data != null) {
        Bundle b = data.getExtras();
        dataMap = Arguments.createMap();
        dataMap.putString("status", "auth");
        dataMap.putString(ConnectOptions.CLIENT_ID, b.getString(ConnectOptions.CLIENT_ID));
        dataMap.putString(ConnectOptions.HUMAN_ID, b.getString(ConnectOptions.HUMAN_ID));
        dataMap.putString(ConnectOptions.SESSION_TOKEN, b.getString(ConnectOptions.SESSION_TOKEN));
      }
      Log.d("hapi-home", "auth " + dataMap);
    } else if (resultCode == RESULT_OK) {
      if (data != null) {
        Bundle b = data.getExtras();
        dataMap = Arguments.createMap();
        dataMap.putString("status", "success");
        dataMap.putString(ConnectOptions.PUBLIC_TOKEN, b.getString(ConnectOptions.PUBLIC_TOKEN));
      }
      Log.d("hapi-home", "success " + dataMap);
    } else if (resultCode == RESULT_CANCELED) {
      dataMap = Arguments.createMap();
      dataMap.putString("status", "cancel");

      Log.d("hapi-home", "cancel " + dataMap);
    }
    Log.d("hapi-home", "invoke!");
    humanCallback.invoke(dataMap);
    humanCallback = null;

    return true;
  }

  @Override
  public String getName() {
    return "RNReactNativeHumanApi";
  }

  @Override
  public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
    Log.d("hapi-home", "requestCode " + requestCode);
    if (requestCode != HUMANAPI_AUTH) {
      return; // incorrect code
    }
    Log.d("hapi-home", "invokeCallback");
    invokeCallback(resultCode, data);
  }

  @Override
  public void onNewIntent(Intent intent) {

  }
}