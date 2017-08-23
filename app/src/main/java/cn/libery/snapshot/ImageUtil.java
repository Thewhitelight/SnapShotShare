package cn.libery.snapshot;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by shizhiqiang on 2017/8/23.
 */

public class ImageUtil {

    /**
     * 保存图片
     */
    public static String saveImageToGallery(Bitmap bmp, String path) {
        File file = new File(path);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            Log.e("snapshot:", "AbsPath:" + file.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file.getAbsolutePath();
    }

    /**
     * 放大缩小图片
     */
    private static Bitmap zoomBitmap(Bitmap bitmap, int w, int h) {
        Bitmap newBp = null;
        if (bitmap != null) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            Matrix matrix = new Matrix();
            float scaleW = (float) w / width;
            float scaleH = (float) h / height;
            matrix.postScale(scaleW, scaleH);
            newBp = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        }
        return newBp;
    }

    /**
     * 垂直方向合成一张图片
     */
    public static Bitmap addBitmap(Bitmap first, Bitmap second) {
        if (first == null || second == null) return null;
        int firstWidth = (int) (first.getWidth() * 1.0);
        int firstHeight = (int) (first.getHeight() * 0.93);
        int totalHeight = firstHeight + second.getHeight();
        second = zoomBitmap(second, firstWidth, second.getHeight());
        Bitmap result = Bitmap.createBitmap(firstWidth, totalHeight, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(result);
        canvas.drawColor(Color.WHITE);
        first = zoomBitmap(first, firstWidth, firstHeight);
        canvas.drawBitmap(first, 0, 0, null);
        canvas.drawBitmap(second, 0, firstHeight, null);
        return result;
    }


    /**
     * 生成一张分享图片
     */
    public static Bitmap getShareBitmap(Bitmap snapshot) {
        if (snapshot == null) return null;
        int originWidth = snapshot.getWidth();
        int originHeight = snapshot.getHeight();
        Bitmap result = Bitmap.createBitmap(originWidth, originHeight, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(result);
        canvas.drawColor(Color.BLACK);
        int width = (int) (originWidth * 0.84);
        int height = (int) (originHeight * 0.93);
        snapshot = zoomBitmap(snapshot, width, height);
        canvas.drawBitmap(snapshot, (originWidth - width) / 2, (originHeight - height) / 2, null);
        return result;
    }
}
