package com.yapin.shanduo.okhttp;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by 90426 on 2017/7/29.
 */

public abstract class JavaOkCallback implements okhttp3.Callback{

    private static String SUCCESS_OK = "true";

    private static Handler handler = new Handler(Looper.getMainLooper());

    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void onFailure(Call call, IOException e) {
        Log.e("java_error_msg",e.getMessage());
        onFailure(e.getMessage());
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        try {
            final String res = response.body().string();
            Log.d("res",res);
            final JSONObject jsonObject = new JSONObject(res);
            final boolean success = jsonObject.getBoolean("success");

            if(success){
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        onResponse(res);
                    }
                });
            }else{
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String errorCode = jsonObject.getString("errorCode");
                            onFailure(errorCode);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }

        } catch (JSONException e) {
            e.printStackTrace();
            onFailure("");
        }

    }

    public abstract void onFailure(String msg);

    public abstract void onResponse(String response);
}
