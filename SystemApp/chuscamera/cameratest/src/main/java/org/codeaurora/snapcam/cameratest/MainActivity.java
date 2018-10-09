package org.codeaurora.snapcam.cameratest;

import android.app.Activity;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
    final static String TAG = "MainActivity";

    final static int REQUEST_CODE = 100;
    MainActivity mMainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainActivity = this;
        setContentView(R.layout.activity_main);

        Button button = (Button)this.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                mMainActivity.startActivityForResult(i, REQUEST_CODE);
            }
        });
        Button button02 = (Button)this.findViewById(R.id.button02);
        button02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                mMainActivity.startActivityForResult(i, REQUEST_CODE);
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.v(TAG, "requestCode:" + requestCode + " resultCode:" + resultCode);

    }



}
