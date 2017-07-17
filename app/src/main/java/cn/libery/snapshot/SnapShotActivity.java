package cn.libery.snapshot;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
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
        final ImageView image = (ImageView) findViewById(R.id.snap_shot_image);
        final String imageUri = getIntent().getStringExtra("snapshot_uri");
        new Handler().postDelayed(new Runnable() {//fix 魅蓝note resolveUri failed on bad bitmap uri
            @Override
            public void run() {
                image.setImageURI(Uri.parse(imageUri));
//                Bitmap bitmap=drawableToBitmap(image.getDrawable());
//                bitmap=addBitmap(bitmap,drawableToBitmap(getResources().getDrawable(R.mipmap.ic_launcher)));
//                image.setImageBitmap(bitmap);
            }
        }, 200);
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
        mHandler.sendEmptyMessageDelayed(MSG_WHAT, 5000);
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                        : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeMessages(MSG_WHAT);
    }

    private Bitmap addBitmap(Bitmap first, Bitmap second) {
        int width = Math.max(first.getWidth(), second.getWidth());
        int height = first.getHeight() + second.getHeight();
        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(first, 0, 0, null);
        canvas.drawBitmap(second, first.getHeight(), 0, null);
        return result;
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
