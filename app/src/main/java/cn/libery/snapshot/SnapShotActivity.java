package cn.libery.snapshot;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Logger;

/**
 * Created by Libery on 2017/7/17.
 * Email:libery.szq@qq.com
 */

public class SnapShotActivity extends AppCompatActivity {

    private CountDownHandler mHandler;
    private static final int MSG_WHAT = 1;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(this).inflate(R.layout.activity_sanpshot, null);
        setContentView(view);
        final ImageView image = (ImageView) findViewById(R.id.snap_shot_image);
        final String snapshotPath = getIntent().getStringExtra("snapshot_path");
        new Handler().postDelayed(new Runnable() {//fix 魅蓝note resolveUri failed on bad bitmap uri
            @Override
            public void run() {
                Bitmap bitmap = BitmapFactory.decodeFile(snapshotPath);
                bitmap = ImageUtil.addBitmap(bitmap, BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round));
                image.setImageBitmap(bitmap);
                final Bitmap finalBitmap = bitmap;
                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        Intent i = new Intent(SnapShotActivity.this, SnapShotShareActivity.class);
                        i.putExtra("snap_shot_share_path", ImageUtil.saveImageToGallery(finalBitmap, snapshotPath));
                        startActivity(i);
                        overridePendingTransition(R.anim.activity_start, R.anim.activity_finish);
                        finish();
                    }
                });
            }
        }, 200);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                finish();
            }
        });
        mHandler = new CountDownHandler(this);
        mHandler.sendEmptyMessageDelayed(MSG_WHAT, 5000);
    }

    private static class CountDownHandler extends Handler {

        private WeakReference<Activity> mReference;

        CountDownHandler(final Activity reference) {
            mReference = new WeakReference<>(reference);
        }

        @Override
        public void handleMessage(final Message msg) {
            super.handleMessage(msg);
            Activity act = mReference.get();
            act.finish();
        }
    }

}
