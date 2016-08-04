package com.hymanme.iscan.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hymanme.iscan.R;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askForPermissions(new String[]{Manifest.permission.CAMERA});
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == 1) {
                String result = data.getStringExtra("result");
                textView.setText(result);
            }
        }
    }

    public void askForPermissions(String[] permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            boolean allGrated = true;
            for (String permission : permissions) {
                // Here, thisActivity is the current activity
                if (ContextCompat.checkSelfPermission(this,
                        permission)
                        != PackageManager.PERMISSION_GRANTED) {

                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            permission)) {
                        allGrated = false;
                    }
                }
            }
            if (!allGrated) {
                ActivityCompat.requestPermissions(this, permissions, 1);
            }
        } else {
            startActivityForResult(new Intent(MainActivity.this, CaptureActivity.class), 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //// TODO: 2016/8/4 permission
        if (requestCode == 1) {
            startActivityForResult(new Intent(MainActivity.this, CaptureActivity.class), 1);
        }
    }
}
