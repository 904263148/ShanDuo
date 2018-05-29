package com.yapin.shanduo.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.utils.PrefUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlaceActivity extends BaseActivity {

    @BindView(R.id.map_view)
    MapView mapView;

    private AMap aMap;
    private Context context;
    private Activity activity;

    private UiSettings mUiSettings;
    private CameraUpdate mUpdata;

    private Double lat , lon;
    private String place;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);
        ButterKnife.bind(this);

        context = ShanDuoPartyApplication.getContext();
        activity = this;

        lat = getIntent().getDoubleExtra("lat" , Double.parseDouble(PrefUtil.getLat(context)));
        lon = getIntent().getDoubleExtra("lon" , Double.parseDouble(PrefUtil.getLon(context)));
        place = getIntent().getStringExtra("place");

        initUi();
        mapView.onCreate(savedInstanceState);

        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    protected void initUi() {
        if (aMap == null) {
            aMap = mapView.getMap();
            mUiSettings = aMap.getUiSettings();
            mUiSettings.setZoomControlsEnabled(false);
            mUiSettings.setCompassEnabled(true);
            mUpdata = CameraUpdateFactory.newCameraPosition(
            //15是缩放比例，0是倾斜度，30显示比例
                    new CameraPosition(new LatLng(lat,lon), 15, 0, 30));//这是地理位置，就是经纬度。
            aMap.moveCamera(mUpdata);//定位的方法
            drawMarkers();
        }
    }

    public void drawMarkers() {

        Marker marker = aMap.addMarker(new MarkerOptions()
                .position(new LatLng(lat,lon))
                .title(place)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .draggable(true));
        marker.showInfoWindow();// 设置默认显示一个infowinfow
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

}
