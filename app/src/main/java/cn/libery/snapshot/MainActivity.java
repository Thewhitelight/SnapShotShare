package cn.libery.snapshot;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private SnapShotListenerManager mManager;
    public static final int PERMISSION_STORAGE = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkStoragePermission();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mManager != null) {
            mManager.stopListener();
        }
    }

    private void checkStoragePermission() {
        String read = Manifest.permission.READ_EXTERNAL_STORAGE;
        String write = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        if (ContextCompat.checkSelfPermission(this, read) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, write) == PackageManager.PERMISSION_GRANTED) {
            initSnapShotManager();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{read, write}, PERMISSION_STORAGE);
        }
    }

    private void initSnapShotManager() {
        mManager = new SnapShotListenerManager(this);
        mManager.stopListener();
        mManager.startListener();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_STORAGE) {
            if (grantResults.length > 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                initSnapShotManager();
            } else if (grantResults.length > 1 &&
                    (grantResults[0] == PackageManager.PERMISSION_DENIED &&
                            grantResults[1] == PackageManager.PERMISSION_DENIED)) {
                new AlertDialog.Builder(this)
                        .setMessage("请授予应用访问存储权限")
                        .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, final int which) {
                                Intent intent = new Intent();
                                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.setData(Uri.parse("package:" + BuildConfig.APPLICATION_ID));
                                startActivity(intent);
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        }
    }

}
