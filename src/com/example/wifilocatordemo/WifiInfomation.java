package com.example.wifilocatordemo;

import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class WifiInfomation extends Activity implements OnClickListener {
	private static final String TAG = "WifiInfo";
	WifiManager wifi;
	BroadcastReceiver receiver;

	TextView textStatus;
	Button buttonScan;
	StringBuilder sb = new StringBuilder();


	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wifiinfo);

		textStatus = (TextView) findViewById(R.id.textStatus);
		buttonScan = (Button) findViewById(R.id.buttonScan);
		buttonScan.setOnClickListener(this);

		wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);

		if (receiver == null)
			receiver = new WiFiScanReceiver();

		registerReceiver(receiver, new IntentFilter(
				WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
		Log.d(TAG, "onCreate()");
	}

	@Override
	public void onStop() {
		unregisterReceiver(receiver);
	}

	public void onClick(View view) {
		Toast.makeText(this, "Scanning!!!",
				Toast.LENGTH_LONG).show();

		if (view.getId() == R.id.buttonScan) {
			Log.d(TAG, "onClick() wifi.startScan()");
			wifi.startScan();
		}
	}


	class WiFiScanReceiver extends BroadcastReceiver {
		
		@Override
		public void onReceive(Context c, Intent intent) {
		  	 sb = new StringBuilder();
		  	 List<ScanResult> results = wifi.getScanResults();
		     for(int i = 0; i < results.size(); i++){
		  		 sb.append(new Integer(i+1).toString() + ".");
		  		 sb.append((results.get(i)).toString());
		  		 sb.append("\n");
		  	 }
		     textStatus.setText(sb);
			
		}

	}
}


