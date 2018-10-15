package com.android.camera;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import com.toro.camera.R;
import android.preference.PreferenceManager;
import android.content.SharedPreferences;

import android.app.CsAlertDialog; // frankie, add

/**
 * Activity that shows permissions request dialogs and handles lack of critical permissions.
 */
public class PermissionsActivity extends Activity {
    private static final String TAG = "PermissionsActivity";

    private static int PERMISSION_REQUEST_CODE = 1;

    private int mIndexPermissionRequestCamera;
    private boolean mShouldRequestCameraPermission;
    private boolean mFlagHasCameraPermission;
    
    private int mIndexPermissionRequestMicrophone;
    private boolean mShouldRequestMicrophonePermission;
    private boolean mFlagHasMicrophonePermission;
    
    private int mIndexPermissionRequestStorageWrite;
    private int mIndexPermissionRequestStorageRead;
    private boolean mShouldRequestStoragePermission;
    private boolean mFlagHasStoragePermission;
    
    private int mIndexPermissionRequestLocation;
    private boolean mShouldRequestLocationPermission;
    
    private int mNumPermissionsToRequest;
    private boolean mCriticalPermissionDenied;
    private Intent mIntent;
    private boolean mIsReturnResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIntent = getIntent();
        mIsReturnResult = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!mCriticalPermissionDenied && !mIsReturnResult) {
            mNumPermissionsToRequest = 0;

            // fraknie, add , reset all flags first
            mShouldRequestCameraPermission = false;
            mFlagHasCameraPermission = false;
            mIndexPermissionRequestCamera = 0;
            
            mShouldRequestMicrophonePermission = false;
            mFlagHasMicrophonePermission = false;
            mIndexPermissionRequestMicrophone = 0;

            mShouldRequestStoragePermission = false;
            mFlagHasStoragePermission = false;
            mIndexPermissionRequestStorageWrite = 0;
            mIndexPermissionRequestStorageRead = 0;

            mShouldRequestLocationPermission = false;
            mIndexPermissionRequestLocation = 0;
            
            checkPermissions();
        } else {
            mCriticalPermissionDenied = false;
        }
    }
    @Override
    public void onPause() {
        super.onPause();

        if(mFailureDialog != null) {
            mFailureDialog.dismiss();
        }
    }

    private void checkPermissions() {
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            mNumPermissionsToRequest++;
            mShouldRequestCameraPermission = true;
        } else {
            mFlagHasCameraPermission = true;
        }

        if (checkSelfPermission(Manifest.permission.RECORD_AUDIO)  != PackageManager.PERMISSION_GRANTED) {
            mNumPermissionsToRequest++;
            mShouldRequestMicrophonePermission = true;
        } else {
            mFlagHasMicrophonePermission = true;
        }

        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED 
            || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            mNumPermissionsToRequest = mNumPermissionsToRequest + 2;
            mShouldRequestStoragePermission = true;
        } else {
            mFlagHasStoragePermission = true;
        }

        if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            mNumPermissionsToRequest++;
            mShouldRequestLocationPermission = true;
        }

        if (mNumPermissionsToRequest != 0) {
            buildPermissionsRequest();
        } else {
            handlePermissionsSuccess();
        }
    }

    private int addRequestedPermissions(String[] permissions, int index, String perm) { // frankie, add
        if(index < permissions.length) {
            permissions[index] = perm;
            return index + 1; // next index
        }
        return index;
    }
    private void buildPermissionsRequest() {
        Log.v(TAG, "buildPermissionsRequest, " + mNumPermissionsToRequest);
        
        String[] permissionsToRequest = new String[mNumPermissionsToRequest];
        int permissionsRequestIndex = 0;
        Log.v(TAG, "mShouldRequestCameraPermission:" + mShouldRequestCameraPermission + " /" + permissionsRequestIndex); // frankie, 
        if (mShouldRequestCameraPermission) {
            //permissionsToRequest[permissionsRequestIndex] = Manifest.permission.CAMERA;
            //permissionsRequestIndex++;
            permissionsRequestIndex = addRequestedPermissions(permissionsToRequest, permissionsRequestIndex, Manifest.permission.CAMERA);
            mIndexPermissionRequestCamera = permissionsRequestIndex;
        }
        Log.v(TAG, "mShouldRequestMicrophonePermission:" + mShouldRequestMicrophonePermission + " /" + permissionsRequestIndex); // fankie,
        if (mShouldRequestMicrophonePermission) {
            //permissionsToRequest[permissionsRequestIndex] = Manifest.permission.RECORD_AUDIO;
            //permissionsRequestIndex++;
            permissionsRequestIndex = addRequestedPermissions(permissionsToRequest, permissionsRequestIndex, Manifest.permission.RECORD_AUDIO);
            mIndexPermissionRequestMicrophone = permissionsRequestIndex;
        }
        Log.v(TAG, "mShouldRequestStoragePermission:" + mShouldRequestStoragePermission + " /" + permissionsRequestIndex); // fankie,
        if (mShouldRequestStoragePermission) {
            //permissionsToRequest[permissionsRequestIndex] =Manifest.permission.WRITE_EXTERNAL_STORAGE;
            //permissionsRequestIndex++;
            permissionsRequestIndex = addRequestedPermissions(permissionsToRequest, permissionsRequestIndex, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            mIndexPermissionRequestStorageWrite = permissionsRequestIndex;
            //permissionsToRequest[permissionsRequestIndex] = Manifest.permission.READ_EXTERNAL_STORAGE;
            //permissionsRequestIndex++;
            permissionsRequestIndex = addRequestedPermissions(permissionsToRequest, permissionsRequestIndex, Manifest.permission.READ_EXTERNAL_STORAGE);
            mIndexPermissionRequestStorageRead = permissionsRequestIndex;
        }
        Log.v(TAG, "mShouldRequestLocationPermission:" + mShouldRequestLocationPermission + " /" + permissionsRequestIndex); // fankie,
        if (mShouldRequestLocationPermission) {
            //permissionsToRequest[permissionsRequestIndex] = Manifest.permission.ACCESS_COARSE_LOCATION;
            //permissionsRequestIndex++;
            permissionsRequestIndex = addRequestedPermissions(permissionsToRequest, permissionsRequestIndex, Manifest.permission.ACCESS_COARSE_LOCATION);
            
            mIndexPermissionRequestLocation = permissionsRequestIndex;
        }
        requestPermissions(permissionsToRequest, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        if (mShouldRequestCameraPermission) {
            if ((grantResults.length >= (mIndexPermissionRequestCamera + 1)) 
                && (grantResults[mIndexPermissionRequestCamera] == PackageManager.PERMISSION_GRANTED)) {
                mFlagHasCameraPermission = true;
            } else {
                mCriticalPermissionDenied = true;
            }
        }
        if (mShouldRequestMicrophonePermission) {
            if ((grantResults.length >= (mIndexPermissionRequestMicrophone + 1)) 
                && (grantResults[mIndexPermissionRequestMicrophone] == PackageManager.PERMISSION_GRANTED)) {
                mFlagHasMicrophonePermission = true;
            } else {
                mCriticalPermissionDenied = true;
            }
        }
        if (mShouldRequestStoragePermission) {
            if ((grantResults.length >= (mIndexPermissionRequestStorageRead + 1)) 
                &&  (grantResults[mIndexPermissionRequestStorageWrite] == PackageManager.PERMISSION_GRANTED) 
                && (grantResults[mIndexPermissionRequestStorageRead] == PackageManager.PERMISSION_GRANTED)) {
                mFlagHasStoragePermission = true;
            } else {
                mCriticalPermissionDenied = true;
            }
        }

        if (mShouldRequestLocationPermission) {
            if ((grantResults.length >= (mIndexPermissionRequestLocation + 1)) 
                && (grantResults[mIndexPermissionRequestLocation] == PackageManager.PERMISSION_GRANTED)) {
                // Do nothing
            } else {
                // Do nothing
            }
        }

        if (mFlagHasCameraPermission 
            && mFlagHasMicrophonePermission 
            && mFlagHasStoragePermission) {
            
            handlePermissionsSuccess();
        } else if (mCriticalPermissionDenied) {
            handlePermissionsFailure();
        }
    }

    private void handlePermissionsSuccess() {
        if (mIntent != null) {
            setRequestPermissionShow();
            mIsReturnResult = true;
            mIntent.setClass(this, CameraActivity.class);
            mIntent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
            startActivity(mIntent);
            finish();
        } else {
            mIsReturnResult = false;
            Intent intent = new Intent(this, CameraActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private Dialog mFailureDialog = null;
    private void handlePermissionsFailure() {
        if(mFailureDialog != null) {
            mFailureDialog.dismiss();
        }
        //new AlertDialog.Builder(this)
        mFailureDialog = new CsAlertDialog.Builder(this)
                .setCancelable(false) // frankie,
//                .setOnDismissListener(new DialogInterface.OnDismissListener() {
//                    @Override
//                    public void onDismiss(DialogInterface dialog) {
//                        finish();
//                    }
//                })
                .setTitle(getResources().getString(R.string.camera_error_title))
                .setMessage(getResources().getString(R.string.error_permissions))
                .setOnKeyListener(new Dialog.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_BACK) {
                            finish();
                        }
                        return true;
                    }
                })
                .setPositiveButton(getResources().getString(R.string.dialog_dismiss),
                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .create();
        if(mFailureDialog != null) {
            mFailureDialog.show();
        }
    }

    private void setRequestPermissionShow() {
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isRequestShown = prefs.getBoolean(CameraSettings.KEY_REQUEST_PERMISSION, false);
        if (!isRequestShown) {
            SharedPreferences.Editor editor = prefs.edit();
			editor.putBoolean(CameraActivity.KEY_REQUEST_PERMISSION_SECURE, true);	// frankie, add
            editor.putBoolean(CameraSettings.KEY_REQUEST_PERMISSION, true);
            editor.apply();
        }
    }
}
