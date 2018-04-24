package com.yapin.shanduo.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PictureUtil {

    public static String currentPhotoPath = null;

    public static File createImageFile() throws IOException {

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.CHINA);
        String timeStamp = format.format(new Date());
        String imageFileName = "shanduo_" + timeStamp + ".jpg";

        File image = new File(getAlbumDir(), imageFileName);
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public static String compressPicture(String currentPhotoPath) {
        String path = null;

        if (currentPhotoPath == null) {
            return null;
        }
        try {
            File f = new File(currentPhotoPath);
            path = getAlbumDir() + "/" + "cped_" + f.getName();
            if (FileUtil.exist(path)) {
                return path;
            }
            Bitmap bm = getSmallBitmap(currentPhotoPath);
            FileOutputStream fos = new FileOutputStream(new File(
                    getAlbumDir(), "cped_" + f.getName()));
            bm.compress(Bitmap.CompressFormat.JPEG, 40, fos);
            fos.close();
            bm.recycle();

        } catch (Exception e) {
            Log.e("", "error", e);
        }
        return path;
    }

    /**
     * 把bitmap转换成String
     *
     * @param filePath 文件路径
     * @return base64
     */
    public static String bitmapToString(String filePath) {

        Bitmap bm = getSmallBitmap(filePath);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 40, baos);
        byte[] b = baos.toByteArray();

        return Base64.encodeToString(b, Base64.DEFAULT);

    }

    /**
     * 计算图片的缩放值
     *
     * @param options   options
     * @param reqWidth  宽度
     * @param reqHeight 高度
     * @return 缩放值
     */
    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     * 根据路径获得图片并压缩返回bitmap用于显示
     *
     * @param filePath 图片路径
     * @return bitmap
     */
    private static Bitmap getSmallBitmap(String filePath) {

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        options.inSampleSize = calculateInSampleSize(options, 800, 800);
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 根据路径删除图片
     *
     * @param path 图片路径
     */
    public static void deleteTempFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 添加到图库
     *
     * @param context context
     * @param path    图片路径
     */
    public static void galleryAddPic(Context context, String path) {
        Intent intent = new Intent(
                Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(path);
        Uri contentUri = Uri.fromFile(f);
        intent.setData(contentUri);
        context.sendBroadcast(intent);
    }

    /**
     * 获取保存图片的目录
     *
     * @return file
     */
    private static File getAlbumDir() {
        File dir = null;
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            dir = new File(Environment.getExternalStorageDirectory(),
                    Constants.PICTURE_PATH);
            if (!dir.exists()) {
                dir.mkdirs();
            }
        }
        return dir;
    }
}
