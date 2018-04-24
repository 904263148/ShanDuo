package com.yapin.shanduo.okhttp;

/**
 * Created by 90426 on 2017/11/13.
 */

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.GlideModule;
import com.yapin.shanduo.utils.FileUtil;

import java.io.InputStream;

import okhttp3.OkHttpClient;

public class OkHttpGlideModule implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        if (!Glide.isSetup()) {
            GlideBuilder gb = new GlideBuilder(context);
            DiskCache cache = DiskLruCacheWrapper.get(FileUtil.getCacheFolder(), 1000 * 1024 * 1024);
            gb.setDiskCache(cache);
            Glide.setup(gb);
        }
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        OkHttpClient mHttpClient = new OkHttpClient().newBuilder()
                .sslSocketFactory(TrustAllCerts.createSSLSocketFactory())
                .hostnameVerifier(new TrustAllCerts.TrustAllHostnameVerifier())
                .build();
        glide.register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(mHttpClient));
    }
}

