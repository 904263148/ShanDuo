package com.yapin.shanduo.ui.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Interpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.animation.Animation;
import com.amap.api.maps.model.animation.TranslateAnimation;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.presenter.ImageUrlPresenter;
import com.yapin.shanduo.presenter.UploadPresenter;
import com.yapin.shanduo.ui.adapter.SearchResultAdapter;
import com.yapin.shanduo.ui.contract.UploadContract;
import com.yapin.shanduo.utils.BitmapUtils;
import com.yapin.shanduo.utils.Constants;
import com.yapin.shanduo.utils.FileUtil;
import com.yapin.shanduo.utils.PrefUtil;
import com.yapin.shanduo.utils.ToastUtil;
import com.yapin.shanduo.widget.glide.SegmentedGroup;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MapGaodeActivity extends AppCompatActivity implements LocationSource,
        AMapLocationListener, GeocodeSearch.OnGeocodeSearchListener, PoiSearch.OnPoiSearchListener, UploadContract.View{

    @BindView(R.id.tv_send)
    TextView tvSend;

    private ListView listView;
    private SegmentedGroup mSegmentedGroup;
    private AutoCompleteTextView searchText;
    private AMap aMap;
    private MapView mapView;
    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;

    private String textlonlat;

//    private String[] items = {"KTV", "酒店", "风景名胜", "体育休闲"};
    private String items = "KTV|酒店|风景名胜|体育休闲";
    private Marker locationMarker;

    private ProgressDialog progDialog = null;
    private GeocodeSearch geocoderSearch;

    private int currentPage = 0;// 当前页面，从0开始计数
    private PoiSearch.Query query;// Poi查询条件类
    private PoiSearch poiSearch;
    private List<PoiItem> poiItems;// poi数据

//    private String searchType = items[0];
    private String searchType = items;
    private String searchKey = "";
    private LatLonPoint searchLatlonPoint;


    private List<PoiItem> resultData;

    private SearchResultAdapter searchResultAdapter;

    private boolean isItemClickAction;

    private List<Tip> autoTips;
    private boolean isfirstinput = true;
    private PoiItem firstItem;

    private boolean chat_map = false;

    private String s1="nullnull";

    private Context context;
    private Activity activity;

    private String chatLatLon;
    private String chatDesc;

    private boolean isClick = false;

    private ImageUrlPresenter uploadPresenter;
    private List<String> listShow = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.map_gaode);
        ButterKnife.bind(this);
        context = ShanDuoPartyApplication.getContext();
        activity = this;
        chat_map = getIntent().getBooleanExtra("chat_map" , false);

        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        init();
        textlonlat = PrefUtil.getcity(context);

        uploadPresenter = new ImageUrlPresenter();
        uploadPresenter.init(this);

        initView();

        resultData = new ArrayList<>();

    }

    public void initView() {
        if(chat_map)
            tvSend.setVisibility(View.VISIBLE);

        listView = (ListView) findViewById(R.id.listview);
        searchResultAdapter = new SearchResultAdapter(MapGaodeActivity.this);
        listView.setAdapter(searchResultAdapter);

        listView.setOnItemClickListener(onItemClickListener);

        mSegmentedGroup = (SegmentedGroup) findViewById(R.id.segmented_group);
        mSegmentedGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                searchType = items;
                switch (checkedId) {
                    case R.id.radio0 :
                        searchType = items;
                        break;
//                    case R.id.radio1 :
//                        searchType = items[1];
//                        break;
//                    case R.id.radio2 :
//                        searchType = items[2];
//                        break;
//                    case R.id.radio3 :
//                        searchType = items[3];
//                        break;
                }
                geoAddress();
            }
        });

        searchText = (AutoCompleteTextView) findViewById(R.id.keyWord);
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String newText = s.toString().trim();
                if (newText.length() > 0) {
                    InputtipsQuery inputquery = new InputtipsQuery(newText, textlonlat);
                    Inputtips inputTips = new Inputtips(MapGaodeActivity.this, inputquery);
                    inputquery.setCityLimit(true);
                    inputTips.setInputtipsListener(inputtipsListener);
                    inputTips.requestInputtipsAsyn();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        searchText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("MY", "setOnItemClickListener");
                if (autoTips != null && autoTips.size() > position) {
                    Tip tip = autoTips.get(position);
                    searchPoi(tip);
                }
            }
        });

        geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(this);
        progDialog = new ProgressDialog(this);

        hideSoftKey(searchText);
    }

    @OnClick(R.id.tv_send)
    public void onClick(View view){
        aMap.getMapScreenShot(new AMap.OnMapScreenShotListener() {
            @Override
            public void onMapScreenShot(Bitmap bitmap) {
                if(null == bitmap){
                    return;
                }

                try {
//                    FileOutputStream fos = new FileOutputStream(Environment.getExternalStorageDirectory() +Constants.PICTURE_PATH +"map_shot.png");

                    boolean b = BitmapUtils.saveMapImageToSD(getApplicationContext() , Environment.getExternalStorageDirectory() +Constants.PICTURE_PATH  ,"map.jpg", bitmap, 100);

//                    boolean b = bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
//                    try {
//                        fos.flush();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    try {
//                        fos.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                    StringBuffer buffer = new StringBuffer();
                    if (b) {
                        buffer.append("截屏成功 ");
                        listShow.clear();
                        listShow.add(Environment.getExternalStorageDirectory() +Constants.PICTURE_PATH + "map.jpg");
                        uploadPresenter.upload(listShow);

                    }else {
                        buffer.append("截屏失败 ");
                        ToastUtil.showShortToast(context , "位置获取失败");
                        onBackPressed();
                    }
//                    ToastUtil.show(getApplicationContext(), buffer.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onMapScreenShot(Bitmap bitmap, int i) {

            }
        });
    }

    /**
     * 初始化
     */
    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
        }

        aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {

            }

            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {
                if (!isItemClickAction && !isInputKeySearch) {
                    geoAddress();
                    startJumpAnimation();
                }
                searchLatlonPoint = new LatLonPoint(cameraPosition.target.latitude, cameraPosition.target.longitude);
                isInputKeySearch = false;
                isItemClickAction = false;
            }
        });

        aMap.setOnMapLoadedListener(new AMap.OnMapLoadedListener() {
            @Override
            public void onMapLoaded() {
                addMarkerInScreenCenter(null);
            }
        });
    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        aMap.getUiSettings().setZoomControlsEnabled(false);
        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
        deactivate();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        if(null != mlocationClient){
            mlocationClient.onDestroy();
        }
    }

    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
//        Log.i("MY", "onLocationChanged");
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null
                    && amapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(amapLocation);

                LatLng curLatlng = new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude());

                searchLatlonPoint = new LatLonPoint(curLatlng.latitude, curLatlng.longitude);

                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curLatlng, 16f));

                isInputKeySearch = false;

                searchText.setText("");

            } else {
                String errText = "定位失败," + amapLocation.getErrorCode()+ ": " + amapLocation.getErrorInfo();
                Log.e("AmapErr",errText);
            }
        }
    }

    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setOnceLocation(true);
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }


    /**
     * 响应逆地理编码
     */
    public void geoAddress() {
//        Log.i("MY", "geoAddress"+ searchLatlonPoint.toString());
        showDialog();
        searchText.setText("");
        if (searchLatlonPoint != null){
            RegeocodeQuery query = new RegeocodeQuery(searchLatlonPoint, 2000, GeocodeSearch.AMAP);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
            geocoderSearch.getFromLocationAsyn(query);
        }
    }

    /**
     * 开始进行poi搜索
     */
    /**
     * 开始进行poi搜索
     */
    protected void doSearchQuery() {
//        Log.i("MY", "doSearchQuery");
        currentPage = 0;
        // 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query = new PoiSearch.Query(searchKey, searchType, textlonlat );
        query.setCityLimit(true);
        query.setPageSize(20);
        query.setPageNum(currentPage);

        if (searchLatlonPoint != null) {
            poiSearch = new PoiSearch(this, query);
            poiSearch.setOnPoiSearchListener(this);
            poiSearch.setBound(new PoiSearch.SearchBound(searchLatlonPoint, 2000, true));//
            poiSearch.searchPOIAsyn();


        }
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
        dismissDialog();
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getRegeocodeAddress() != null
                    && result.getRegeocodeAddress().getFormatAddress() != null) {
                String address = result.getRegeocodeAddress().getProvince() + result.getRegeocodeAddress().getCity() + result.getRegeocodeAddress().getDistrict() + result.getRegeocodeAddress().getTownship();
                firstItem = new PoiItem("regeo", searchLatlonPoint, address, address);
                doSearchQuery();
            }
        } else {
            Toast.makeText(MapGaodeActivity.this, "error code is " + rCode, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }

    /**
     * POI搜索结果回调
     * @param poiResult 搜索结果
     * @param resultCode 错误码
     */
    @Override
    public void onPoiSearched(PoiResult poiResult, int resultCode) {
        if (resultCode == AMapException.CODE_AMAP_SUCCESS) {
            if (poiResult != null && poiResult.getQuery() != null) {
                if (poiResult.getQuery().equals(query)) {
                    poiItems = poiResult.getPois();
                    if (poiItems != null && poiItems.size() > 0) {
                        updateListview(poiItems);
                    } else {
                        Toast.makeText(MapGaodeActivity.this, "无搜索结果", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                Toast.makeText(MapGaodeActivity.this, "无搜索结果", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 更新列表中的item
     * @param poiItems
     */
    private void updateListview(List<PoiItem> poiItems) {
        resultData.clear();
//        searchResultAdapter.setSelectedPosition(0);
        resultData.add(firstItem);
        resultData.addAll(poiItems);

        searchResultAdapter.setData(resultData);
        searchResultAdapter.notifyDataSetChanged();

        if(!isClick && chat_map && resultData.size() != 0){
            chatLatLon = resultData.get(0).getLatLonPoint().toString().trim();
            chatDesc = resultData.get(0).getTitle().toString().trim();
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            if (position != searchResultAdapter.getSelectedPosition()) {

            isClick = true;

                PoiItem poiItem = (PoiItem) searchResultAdapter.getItem(position);
                LatLng curLatlng = new LatLng(poiItem.getLatLonPoint().getLatitude(), poiItem.getLatLonPoint().getLongitude());

                isItemClickAction = true;

                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curLatlng, 16f));

                searchResultAdapter.setSelectedPosition(position);
                searchResultAdapter.notifyDataSetChanged();

                chatLatLon = resultData.get(position).getLatLonPoint().toString().trim();
                chatDesc = resultData.get(position).getTitle().toString().trim();

                if(chat_map)
                    return;

                String textTitle = resultData.get(position).getTitle().toString().trim();
                textlonlat = resultData.get(position).getLatLonPoint().toString().trim();
                String textSubTitle = resultData.get(position).getCityName()+poiItem.getAdName() + poiItem.getSnippet().toString().trim();
            /**
             * 删除textSubTitle里面包含string = s1 的值
             */
            if (textSubTitle.contains(s1)){
                    textSubTitle = textSubTitle.replace(textSubTitle.substring(textSubTitle.indexOf(s1), textSubTitle.length()), "");
                }
//                Toast.makeText(MapGaodeActivity.this, "名字是："+textTitle+"地址是："+textSubTitle+"经纬度是："+textlonlat, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                //在Intent对象当中添加一个键值对
                intent.putExtra("textTitle",textTitle+","+textSubTitle);
                intent.putExtra("textlonlat",textlonlat);
                intent.putExtra("textSubTitle",textSubTitle);
                intent.putExtra("Title",textTitle);
                setResult(RESULT_OK , intent);
                finish();

            }
//        }
    };

    public void showDialog() {
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setIndeterminate(false);
        progDialog.setCancelable(true);
        progDialog.setMessage("正在加载...");
        progDialog.show();
    }

    public void dismissDialog() {
        if (progDialog != null) {
            progDialog.dismiss();
        }
    }

    private void addMarkerInScreenCenter(LatLng locationLatLng) {
        LatLng latLng = aMap.getCameraPosition().target;
        Point screenPosition = aMap.getProjection().toScreenLocation(latLng);
        locationMarker = aMap.addMarker(new MarkerOptions()
                .anchor(0.5f,0.5f)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.purple_pin)));
        //设置Marker在屏幕上,不跟随地图移动
        locationMarker.setPositionByPixels(screenPosition.x,screenPosition.y);
        locationMarker.setZIndex(1);

    }

    /**
     * 屏幕中心marker 跳动
     */
    public void startJumpAnimation() {

        if (locationMarker != null ) {
            //根据屏幕距离计算需要移动的目标点
            final LatLng latLng = locationMarker.getPosition();
            Point point =  aMap.getProjection().toScreenLocation(latLng);
            point.y -= dip2px(this,125);
            LatLng target = aMap.getProjection()
                    .fromScreenLocation(point);
            //使用TranslateAnimation,填写一个需要移动的目标点
            Animation animation = new TranslateAnimation(target);
            animation.setInterpolator(new Interpolator() {
                @Override
                public float getInterpolation(float input) {
                    // 模拟重加速度的interpolator
                    if(input <= 0.5) {
                        return (float) (0.5f - 2 * (0.5 - input) * (0.5 - input));
                    } else {
                        return (float) (0.5f - Math.sqrt((input - 0.5f)*(1.5f - input)));
                    }
                }
            });
            //整个移动所需要的时间
            animation.setDuration(600);
            //设置动画
            locationMarker.setAnimation(animation);
            //开始动画
            locationMarker.startAnimation();

        } else {
            Log.e("ama","screenMarker is null");
        }
    }

    //dip和px转换
    private static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    Inputtips.InputtipsListener inputtipsListener = new Inputtips.InputtipsListener() {
        @Override
        public void onGetInputtips(List<Tip> list, int rCode) {
            if (rCode == AMapException.CODE_AMAP_SUCCESS) {// 正确返回
                autoTips = list;
                List<String> listString = new ArrayList<String>();
                for (int i = 0; i < list.size(); i++) {
                    listString.add(list.get(i).getName());
                }
                ArrayAdapter<String> aAdapter = new ArrayAdapter<String>(
                        getApplicationContext(),
                        R.layout.route_inputs, listString);
                searchText.setAdapter(aAdapter);
                aAdapter.notifyDataSetChanged();
                if (isfirstinput) {
                    isfirstinput = false;
                    searchText.showDropDown();
                }
            } else {
                Toast.makeText(MapGaodeActivity.this, "erroCode " + rCode , Toast.LENGTH_SHORT).show();
            }
        }
    };

    private boolean isInputKeySearch;
    private String inputSearchKey;
    private void searchPoi(Tip result) {
        isInputKeySearch = true;
        inputSearchKey = result.getName();//getAddress(); // + result.getRegeocodeAddress().getCity() + result.getRegeocodeAddress().getDistrict() + result.getRegeocodeAddress().getTownship();
        searchLatlonPoint = result.getPoint();
        firstItem = new PoiItem("tip", searchLatlonPoint, inputSearchKey, result.getAddress());
        firstItem.setCityName(result.getDistrict());
        firstItem.setAdName("");
        resultData.clear();

        searchResultAdapter.setSelectedPosition(0);

        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(searchLatlonPoint.getLatitude(), searchLatlonPoint.getLongitude()), 16f));

        hideSoftKey(searchText);
        doSearchQuery();
    }

    private void hideSoftKey(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view,InputMethodManager.SHOW_FORCED);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void uploadSuccess(String imgIds) {
        Intent intent = new Intent();
        intent.putExtra("chatLatLon" , chatLatLon);
        intent.putExtra("chatDesc" , chatDesc);
        intent.putExtra("imgId" , imgIds);
        setResult(RESULT_OK , intent);
        onBackPressed();
    }

    @Override
    public void loading() {

    }

    @Override
    public void networkError() {
        ToastUtil.showShortToast(context , "位置获取失败");
        onBackPressed();
    }

    @Override
    public void error(String msg) {
        ToastUtil.showShortToast(context , "位置获取失败");
        onBackPressed();
    }

    @Override
    public void showFailed(String msg) {
        ToastUtil.showShortToast(context , "位置获取失败");
        onBackPressed();
    }
}
