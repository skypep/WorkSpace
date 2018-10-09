/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.camera;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

// Use a different activity for secure camera only. So it can have a different
// task affinity from others. This makes sure non-secure camera activity is not
// started in secure lock screen.
public class SecureCameraActivity extends CameraActivity {
	private static final String TAG = "SCAM_Activity";

	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (Intent.ACTION_SCREEN_OFF.equals(intent.getAction())) {
//				mH.postDelayed(mFinishRunnable, 800);
				Log.w(TAG,"ACTION_SCREEN_OFF");
				//finishAndRemoveTask();
			}
			if (Intent.ACTION_SCREEN_ON.equals(intent.getAction())) {
				Log.w(TAG,"ACTION_SCREEN_ON");
				//finishAndRemoveTask();
			}
		}
	};
	private boolean mRegistered = false;
	private void register_receiver() {
		if(mRegistered == false) {
			mRegistered = true;
			final IntentFilter intentFilter = new IntentFilter();
			intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
			intentFilter.addAction(Intent.ACTION_SCREEN_ON);
			registerReceiver(mBroadcastReceiver, intentFilter);
		}
	}
	private void unregister_receiver() {
		if(mRegistered) {
			mRegistered = false;
			unregisterReceiver(mBroadcastReceiver);
		}
	}

	private Runnable mFinishRunnable = new Runnable() {
		public void run() {
			Log.v(TAG,"+++finishAndRemoveTask");
			finishAndRemoveTask();
		}
	};
	private Handler mH = new Handler();

	@Override
	public void onCreate(Bundle state) {
		setSecureActivity__(true);
		super.onCreate(state);
		Log.w(TAG,"onCreate");
		register_receiver();
	}
	@Override
	public void onStart() {
//		mH.removeCallbacks(mFinishRunnable);
		super.onStart();
		Log.w(TAG, "onStart");
	}

	@Override
	public void onResume() {
		mJumpGalley = false;
		mH.removeCallbacks(mFinishRunnable);
		super.onResume();
		Log.w(TAG, "onResume");

		//register_receiver();
	}
	@Override
	public void onPause() {
		super.onPause();
		Log.w(TAG, "onPause");
		//unregister_receiver();
		if(!mJumpGalley){
			mH.postDelayed(mFinishRunnable, 800);
		}
	}
	@Override
	public void onStop() {
		super.onStop();
		Log.w(TAG, "onStop");
//		mH.postDelayed(mFinishRunnable, 250);
	}

	@Override
	public void onDestroy() {
		unregister_receiver();
		super.onDestroy();
		Log.w(TAG,"onDestroy");

	}
}
