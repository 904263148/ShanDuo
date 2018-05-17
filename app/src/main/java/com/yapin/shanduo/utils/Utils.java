package com.yapin.shanduo.utils;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.yapin.shanduo.model.entity.PhotoFolder;

import java.io.File;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    /**
     * 验证权限并获取权限
     *
     */
    public static void checkPermission(Activity activity){
        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE , Manifest.permission.CAMERA ,Manifest.permission.ACCESS_COARSE_LOCATION , Manifest.permission.ACCESS_FINE_LOCATION};
            //验证是否许可权限
            for (String str : permissions) {
                if (activity.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    activity.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                    return;
                }
            }
        }
    }


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

    //tablayout下划线文字同宽
    public static void setIndicator (TabLayout tabs,int leftDip,int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }

        /**
         * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
         */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }


    //判断vip等级
    public static String vipLevel(int level){
        String vipLevel = "";
        if(level > 0 && level < 9){
            vipLevel = "VIP" +level;
        }
        if(level >= 11 ){
            vipLevel = "SVIP" + (level -10);
        }
        return vipLevel;
    }

    public static void setLocation(final Context context){

        final AMapLocationClient mlocationClient = new AMapLocationClient(context);
        //初始化定位参数
        AMapLocationClientOption mLocationOption = new AMapLocationClientOption();

        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        //设置定位间隔,单位毫秒,默认为2000ms
        //        mLocationOption.setInterval(2000);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
        mlocationClient.startLocation();
        mLocationOption.setOnceLocation(true);
        //设置定位监听
        mlocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation amapLocation) {
                if (amapLocation != null) {
                    if (amapLocation.getErrorCode() == 0) {
                        //定位成功回调信息，设置相关消息
                        amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                        amapLocation.getLatitude();//获取纬度
                        amapLocation.getLongitude();//获取经度
                        amapLocation.getAccuracy();//获取精度信息
                        amapLocation.getAoiName();//获取当前定位点的AOI信息
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date date = new Date(amapLocation.getTime());
                        df.format(date);//定位时间
                        PrefUtil.setLon(context , amapLocation.getLongitude()+"");
                        PrefUtil.setLat(context , amapLocation.getLatitude()+"");
//                        PrefUtil.setAoiName(context,amapLocation.getAoiName()+"");
//                        PrefUtil.setAccuracy(context,amapLocation.getAccuracy()+"");
                        Log.i("lon--------------lat" , "经度:"+amapLocation.getLongitude()+"-------纬度:" +amapLocation.getLatitude()+"精确经纬度-----"+amapLocation.getAccuracy());

                        mlocationClient.stopLocation();

                    } else {
                        //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                        Log.e("AmapError","location Error, ErrCode:"
                                + amapLocation.getErrorCode() + ", errInfo:"
                                + amapLocation.getErrorInfo());
                    }
                }
            }
        });
    }





}
