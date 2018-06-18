package com.iot.letthingsspeak.configdevice.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.support.annotation.NonNull;
import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Listens for 3 broadcasted custom wifi actions:
 * <p>
 * - {@link #ACTION_WIFI_ON} - {@link #ACTION_WIFI_OFF} - {@link #ACTION_CONNECT_TO_WIFI}
 * <p>
 * These actions are custom and can be replaced with any other string. To test these custom actions
 * you can do the following
 * <p>
 * <code>
 * <p>
 * adb shell am broadcast -a android.intent.action.WIFI_ON
 * <p>
 * adb shell am broadcast -a android.intent.action.WIFI_OFF
 * <p>
 * adb shell am broadcast -a android.intent.action.CONNECT_TO_WIFI -e ssid {ssid} -e password {pwd}
 * <p>
 * </code>
 */
public class WifiConnectionReceiver extends BroadcastReceiver {

    /**
     * Notifies the receiver to turn wifi on
     */
    private static final String ACTION_WIFI_ON = "android.intent.action.WIFI_ON";

    /**
     * Notifies the receiver to turn wifi off
     */
    private static final String ACTION_WIFI_OFF = "android.intent.action.WIFI_OFF";

    /**
     * Notifies the receiver to connect to a specified wifi
     */
    private static final String ACTION_CONNECT_TO_WIFI = "android.intent.action.CONNECT_TO_WIFI";

    private WifiManager wifiManager;

    public WifiConnectionReceiver() {
    }

    @NonNull
    public static IntentFilter getIntentFilterForWifiConnectionReceiver() {
        final IntentFilter randomIntentFilter = new IntentFilter(ACTION_WIFI_ON);
        randomIntentFilter.addAction(ACTION_WIFI_OFF);
        randomIntentFilter.addAction(ACTION_CONNECT_TO_WIFI);
        return randomIntentFilter;
    }

    public void onReceive(Context c, Intent intent) {
        Log.d(TAG, "onReceive() called with: intent = [" + intent + "]");

        wifiManager = (WifiManager) c.getSystemService(Context.WIFI_SERVICE);

        final String action = intent.getAction();

        if (!isTextNullOrEmpty(action)) {
            switch (action) {
                case ACTION_WIFI_ON:
                    // Turns wifi on
                    wifiManager.setWifiEnabled(true);
                    break;
                case ACTION_WIFI_OFF:
                    // Turns wifi off
                    wifiManager.setWifiEnabled(false);
                    break;
                case ACTION_CONNECT_TO_WIFI:
                    // Connects to a specific wifi network
                    final String networkSSID = intent.getStringExtra("ssid");
                    final String networkPassword = intent.getStringExtra("password");

                    if (!isTextNullOrEmpty(networkSSID) && !isTextNullOrEmpty(networkPassword)) {
                        connectToWifi(networkSSID, networkPassword);
                    } else {
                        Log.e(TAG, "onReceive: cannot use " + ACTION_CONNECT_TO_WIFI +
                                "without passing in a proper wifi SSID and password.");
                    }
                    break;
            }
        }
    }

    private boolean isTextNullOrEmpty(final String text) {
        return text != null && !text.isEmpty();
    }

    /**
     * Connect to the specified wifi network.
     *
     * @param networkSSID     - The wifi network SSID
     * @param networkPassword - the wifi password
     */
    private void connectToWifi(final String networkSSID, final String networkPassword) {
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }

        WifiConfiguration conf = new WifiConfiguration();
        conf.SSID = String.format("\"%s\"", networkSSID);
        conf.preSharedKey = String.format("\"%s\"", networkPassword);

        int netId = wifiManager.addNetwork(conf);
        wifiManager.disconnect();
        wifiManager.enableNetwork(netId, true);
        wifiManager.reconnect();
    }

}