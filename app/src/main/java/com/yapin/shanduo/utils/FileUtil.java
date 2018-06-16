package com.yapin.shanduo.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.yapin.shanduo.app.ShanDuoPartyApplication;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;

/**
 * 文件操作类
 *
 */
public class FileUtil {
    private static String pathDiv = "/";
    private static File cacheDir = !isExternalStorageWritable() ?
            ShanDuoPartyApplication.getContext().getFilesDir() :
            ShanDuoPartyApplication.getContext().getExternalCacheDir();

    public enum FileType{
        IMG,
        AUDIO,
        VIDEO,
        FILE,
    }


    /**
     * 创建临时文件
     *
     * @param type 文件类型
     */
    public static File getTempFile(FileType type){
        try{
            File file = File.createTempFile(type.toString(), null, cacheDir);
            file.deleteOnExit();
            return file;
        }catch (IOException e){
            return null;
        }
    }

    /**
     * 判断缓存文件是否存在
     */
    public static boolean isCacheFileExist(String fileName){
        File file = new File(getCacheFilePath(fileName));
        return file.exists();
    }

    /**
     * 判断外部存储是否可用
     */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /**
     * 获取缓存文件地址
     */
    public static String getCacheFilePath(String fileName) {
        return cacheDir.getAbsolutePath() + pathDiv + fileName;
    }

    /**
     * 将数据存储为文件
     *
     * @param data 数据
     * @param fileName 文件名
     * @param type 文件类型
     */
    public static boolean createFile(byte[] data, String fileName, String type){
        if (isExternalStorageWritable()){
            File dir = ShanDuoPartyApplication.getContext().getExternalFilesDir(type);
            if (dir != null){
                File f = new File(dir, fileName);
                try{
                    if (f.createNewFile()){
                        FileOutputStream fos = new FileOutputStream(f);
                        fos.write(data);
                        fos.flush();
                        fos.close();
                        return true;
                    }
                }catch (IOException e){
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * 将图片存储为文件
     *
     * @param bitmap 图片
     */
    public static String createFile(Bitmap bitmap, String filename){
        File f = new File(cacheDir, filename);
        try{
            if (f.createNewFile()){
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
                byte[] bitmapdata = bos.toByteArray();
                FileOutputStream fos = new FileOutputStream(f);
                fos.write(bitmapdata);
                fos.flush();
                fos.close();
            }
        }catch (IOException e){
        }
        if (f.exists()){
            return f.getAbsolutePath();
        }
        return null;
    }

    /**
     * 将数据存储为文件
     *
     * @param data 数据
     */
    public static void createFile(byte[] data,String filename){
        File f = new File(cacheDir, filename);
        try{
            if (f.createNewFile()){
                FileOutputStream fos = new FileOutputStream(f);
                fos.write(data);
                fos.flush();
                fos.close();
            }
        }catch (IOException e){
        }
    }


    /**
     * 判断文件或文件夹是否存在
     *
     * @param absolutePath 文件的绝对路径
     * @return false or true
     */
    public static boolean exist(String absolutePath) {
        File file = new File(absolutePath);
        return exist(file);
    }

    /**
     * 判断文件或文件夹是否存在
     *
     * @param file file
     * @return false or true
     */
    public static boolean exist(File file) {
        return file.exists();
    }

    /**
     * 如果指定目录不存在，则创建此目录
     *
     * @param absolutePath 文件的绝对路径
     */
    public static void mkDir(String absolutePath) {
        File file = new File(absolutePath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * 删除文件夹或文件
     *
     * @param absolutePath 绝对路径
     */
    public static void delete(String absolutePath) {
        new File(absolutePath).delete();
    }

    /**
     * 删除指定目录下面的指定文件
     *
     * @param absolutePath 绝对路径
     * @param str          文件包含的字符串信息
     */
    public static void deleteFile(String absolutePath, String str) {
        File file = new File(absolutePath);
        if (!file.exists()) return;
        if (!file.isDirectory()) file.delete();
        File[] files = file.listFiles();
        for (File f : files) {
            if (f.isFile()) {
                if (f.getName().contains(str)) {
                    f.delete();
                }
            }
        }
    }

    /**
     * 删除指定目录下面的所有文件
     *
     * @param absolutePath 绝对路径
     */
    public static void deleteFile(String absolutePath) {
        File file = new File(absolutePath);
        if (!file.exists()) return;
        if (!file.isDirectory()) file.delete();
        File[] files = file.listFiles();
        for (File f : files) {
            if (f.isFile())
                f.delete();
        }
    }

    /**
     * 读取文件夹下文件的内容
     *
     * @param filePath 文件夹路径
     * @return 文件内容
     */
    public static String readFile(String filePath) {
        String path = getSDCardBasePath() + filePath;
        File file = new File(path);
        if (!file.exists()) {
            return "";
        }
        if (!file.isDirectory()) {
            return readOnlyFile(file);
        }
        File[] files = file.listFiles();
        if (files.length == 0) {
            return "";
        }
        String fullPath = path + files[files.length - 1].getName();

        return readOnlyFile(new File(fullPath));
    }

    /**
     * 读取文件的内容
     *
     * @param file file
     * @return 文件内容
     */
    public static String readOnlyFile(File file) {
        String content = "";
        if (!file.exists()) {
            return content;
        }
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;

        try {
            inputStream = new FileInputStream(file);
            inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            // 分行读取
            while ((line = bufferedReader.readLine()) != null) {
                content += line + "\n";
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content;
    }

    /**
     * 判断是否有SD卡
     *
     * @return true or false
     */
    public static boolean mountedSDCard() {
        return Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取SD卡的路径
     *
     * @return sd卡路径
     */
    public static String getSDCardBasePath() {
        return Environment.getExternalStorageDirectory().getPath();
    }

    /**
     * 获取56kon的路径
     *
     * @return 图片缓存路径
     */
    public static String getCacheFolder(String filePath) {
        return FileUtil.getSDCardBasePath() + filePath;
    }

    /**
     * 获取图片缓存的路径
     *
     * @return 图片缓存file
     */
    public static File getCacheFolder() {
        String path = FileUtil.getSDCardBasePath()
                + Constants.CACHE_FILE_PATH;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    /**
     * 获取文件file
     *
     * @param filePath 文件夹路径
     * @return file
     */
    public static File getFile(String filePath) {
        String path = FileUtil.getSDCardBasePath()
                + filePath;
        return new File(path);
    }

    /**
     * 获取文件夹的大小
     *
     * @param file file
     * @return 文件夹大小, long型
     * @throws Exception
     */
    public static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (File subFile : fileList) {
                if (subFile.isDirectory()) {
                    size = size + getFolderSize(subFile);
                } else {
                    size = size + getFileSize(subFile);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 获取文件的大小
     *
     * @param file file
     * @return 文件大小, long型
     */
    public static long getFileSize(File file) {
        return file.length();
    }

    /**
     * 格式化单位
     *
     * @param size 文件夹大小
     * @return 格式化后的字符串
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "Byte";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "TB";
    }

    /**
     * 格式化单位
     *
     * @param size 文件夹大小
     * @return 格式化后的字符串
     */
    public static String getMbFormatSize(double size) {
        double kiloByte = size / 1024 / 1024;

        BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
        return result1.setScale(1, BigDecimal.ROUND_HALF_UP)
                .toPlainString() + "M";
    }

    /**
     * 获取文件夹大小字符串
     *
     * @param file file
     * @return 文件夹大小
     */
    public static String getCacheSize(File file) {
        if (!exist(file))
            return "0M";
        try {
            return getMbFormatSize(getFolderSize(file));
        } catch (Exception e) {
            e.printStackTrace();
            return "0M";
        }
    }

    /**
     * 删除指定目录下文件及目录
     *
     * @param deleteThisPath 是否删除
     * @param filePath       目录路径
     */
    public static void deleteFolderFile(String filePath, boolean deleteThisPath) {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                File file = new File(filePath);
                if (file.isDirectory()) {
                    File files[] = file.listFiles();
                    for (File file1 : files) {
                        deleteFolderFile(file1.getAbsolutePath(), true);
                    }
                }
                if (deleteThisPath) {
                    if (!file.isDirectory()) {
                        file.delete();
                    } else {
                        if (file.listFiles().length == 0) {
                            file.delete();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 根据Uri返回文件绝对路径
     * 兼容了file:///开头的 和 content://开头的情况
     */
    public static String getRealFilePathFromUri(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null) {
            data = uri.getPath();
        }
        else if (ContentResolver.SCHEME_FILE.equalsIgnoreCase(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equalsIgnoreCase(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    /**
     * 检查文件是否存在
     */
    public static String checkDirPath(String dirPath) {
        if (TextUtils.isEmpty(dirPath)) {
            return "";
        }
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dirPath;
    }
}
