package cn.libery.snapshot;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.lang.ref.WeakReference;

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
        ImageView image = (ImageView) findViewById(R.id.snap_shot_image);
        String imageUri = getIntent().getStringExtra("snapshot_uri");
        image.setImageURI(Uri.parse(imageUri));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                finish();
            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Toast.makeText(SnapShotActivity.this, "分享", Toast.LENGTH_SHORT).show();
            }
        });
        mHandler = new CountDownHandler(this);
        mHandler.sendEmptyMessageDelayed(MSG_WHAT, 3000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeMessages(MSG_WHAT);
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
