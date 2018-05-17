package com.yapin.shanduo.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 作者：L on 2018/5/7 0007 16:48
 */
public class BitmapUtils {
    public static Bitmap decodeUriAsBitmap(Context context, Uri uri) {
        Bitmap bitmap;
        try {
            bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }

    /**
     * 写图片文件到SD卡
     *
     * @param context  context
     * @param filePath 文件路径
     * @param fileName 文件名字
     * @param bitmap   图片流
     * @param quality  图片质量
     * @throws IOException
     */
    public static void saveImageToSD(Context context, String filePath, String fileName, Bitmap bitmap, int quality) throws IOException {
        if (bitmap == null || filePath == null || context == null || fileName == null)
            return;
        FileUtil.mkDir(filePath);
        File newFile = new File(filePath, fileName);
        if (!newFile.exists()) {
            newFile.createNewFile();
        }
        BufferedOutputStream bos = new BufferedOutputStream(
                new FileOutputStream(newFile));
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, bos);
        bos.flush();
        bos.close();
        scanPhoto(context, newFile);
        ToastUtil.showShortToast(context, "图片已保存在" + filePath + "文件夹");
    }

    /**
     * 写图片文件到SD卡
     *
     * @param context  context
     * @param filePath 文件路径
     * @param fileName 文件名字
     * @param bitmap   图片流
     * @param quality  图片质量
     * @throws IOException
     */
    public static boolean saveMapImageToSD(Context context, String filePath, String fileName, Bitmap bitmap, int quality) throws IOException {
        if (bitmap == null || filePath == null || context == null || fileName == null)
            return false;
        FileUtil.mkDir(filePath);
        File newFile = new File(filePath, fileName);
        if (!newFile.exists()) {
            newFile.createNewFile();
        }
        BufferedOutputStream bos = new BufferedOutputStream(
                new FileOutputStream(newFile));
        boolean b = bitmap.compress(Bitmap.CompressFormat.JPEG, quality, bos);
        bos.flush();
        bos.close();
        ToastUtil.showShortToast(context, "图片已保存在" + filePath + "文件夹");
        return b;
    }

    /**
     * 通知系统更新图片，让Gallery上能马上看到该图片
     *
     * @param context context
     * @param file    文件
     */
    private static void scanPhoto(Context context, File file) {
        Intent intent = new Intent(
                Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        context.sendBroadcast(intent);
    }

}
