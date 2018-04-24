package com.yapin.shanduo.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.yapin.shanduo.model.entity.PhotoFolder;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    /**
     * 打开拍照
     *
     * @param activity    activity
     * @param requestCode code
     */
    public static void takePhoto(Activity activity, int requestCode) {
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File file = PictureUtil.createImageFile();
            Uri uri = Uri.fromFile(file);
            intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            activity.startActivityForResult(intent, requestCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取本地所有图片和图片文件夹
     *
     * @param context context
     * @return 图片和图片文件夹
     */
    public static Map<String, PhotoFolder> getPhotos(Context context) {

        Map<String, PhotoFolder> map = new HashMap<>();
        PhotoFolder folder = new PhotoFolder();
        folder.setName(Constants.ALL_PHOTO);
        folder.setDirPath(Constants.ALL_PHOTO);
        folder.setList(new ArrayList<String>());
        map.put(Constants.ALL_PHOTO, folder);

        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        ContentResolver contentResolver = context.getContentResolver();

        Cursor cursor = null;
        try {
            cursor = contentResolver.query(uri
                    , null
                    , MediaStore.Images.Media.MIME_TYPE + " in(?, ?)"
                    , new String[]{"image/jpeg", "image/png"}
                    , MediaStore.Images.Media.DATE_MODIFIED + " DESC");

            if (cursor == null) {
                return map;
            }
            int columnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
            while (cursor.moveToNext()) {
                String path = cursor.getString(columnIndex);
                File parentPath = new File(path).getParentFile();
                if (parentPath == null) {
                    continue;
                }
                String dirPath = parentPath.getAbsolutePath();
                if (map.containsKey(dirPath)) {
                    map.get(dirPath).getList().add(path);
                    map.get(Constants.ALL_PHOTO).getList().add(path);
                    continue;
                }
                PhotoFolder photoFolder = new PhotoFolder();
                List<String> list = new ArrayList<>();
                list.add(path);
                photoFolder.setList(list);
                photoFolder.setDirPath(dirPath);
                photoFolder.setName(dirPath.substring(dirPath.lastIndexOf(File.separator) + 1, dirPath.length()));
                map.put(dirPath, photoFolder);
                map.get(Constants.ALL_PHOTO).getList().add(path);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return map;
    }

    /**
     * 验证手机号码是否有效
     *
     * @param tel tel
     * @return 是否有效
     */
    public static boolean isTelValid(String tel) {
        return tel.length() == 11;
    }

    /**
     * 验证手机号码
     *
     * 移动号码段:139、138、137、136、135、134、150、151、152、157、158、159、182、183、187、188、147
     * 134, 135 , 136, 137, 138, 139, 147, 150, 151, 152, 157, 158, 159, 178, 182, 183, 184, 187, 188
     * 联通号码段:130、131、132、136、185、186、145
     * 电信号码段:133、153、180、189
     *
     * @param cellphone
     * @return
     */
    public static boolean checkCellphone(String cellphone) {
//        String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0-9])|(17[0-9])|(19[9]))\\d{8}$";
        String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(166)|(18[0-9])|(17[0-9])|(19[8|9]))\\d{8}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(cellphone);
        return m.matches();
    }


    /**
     * 验证邮箱是否有效
     *
     * @param email email
     * @return 是否有效
     */
    public static boolean isEmailValid(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);

        return m.matches();
    }

    /**
     * OLD_DOG提供的验证邮箱是否有效
     *
     * @param email email
     * @return 是否有效
     */
    public static boolean isEmailSuccess(String email) {
        String str = "^\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);

        return m.matches();
    }


    /**
     * 判断密码的长度
     *
     * @param password 密码
     * @return 长度是否符合规范
     */
    public static boolean isPasswordValid(String password) {
        return password.length() > 5;
    }

    public static String getLocalImagePath(String path) {
        return "file://" + path;
    }

    /**
     * 去除字符串第一个和最后一个字符
     *
     * @param str    原始的字符串
     * @param prefix 例如,
     * @param suffix 例如,
     *
     * @return 字符串
     */
    public static String removeStartEndChar(String str, String prefix, String suffix) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        if (str.startsWith(prefix)) {
            str = str.substring(1, str.length());
        }
        if (str.endsWith(suffix)) {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    /**
     * 去除字符串最后一个字符
     *
     * @param str    原始的字符串
     * @param suffix 例如,
     * @return 字符串
     */
    public static String removeEndChar(String str, String suffix) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        if (str.endsWith(suffix)) {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    /**
     * 数字保留两位小数四舍五入
     */
    public static String getDouble(double num){
        if(num == 0){
            return "0";
        }else{
            return new DecimalFormat("#.00").format(num);
        }
    }

}
