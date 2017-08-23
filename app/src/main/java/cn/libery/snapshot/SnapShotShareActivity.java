package cn.libery.snapshot;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Libery on 2017/7/18.
 * Email:libery.szq@qq.com
 */

public class SnapShotShareActivity extends Activity {

    private ImageView shareImage;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snapshot_share);
        shareImage = (ImageView) findViewById(R.id.snapshot_share_image);
        findViewById(R.id.share_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                finish();
            }
        });
        setShareImageFilePath(getIntent().getStringExtra("snap_shot_share_path"));
    }

    public void setShareImageFilePath(String path) {
        if (TextUtils.isEmpty(path)) {
            finish();
        }
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        if (bitmap == null) {
            finish();
        }
        bitmap = ImageUtil.getShareBitmap(bitmap);
        ImageUtil.saveImageToGallery(bitmap, path);
        shareImage.setImageURI(Uri.parse(path));
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_start, R.anim.activity_finish);
    }

}
