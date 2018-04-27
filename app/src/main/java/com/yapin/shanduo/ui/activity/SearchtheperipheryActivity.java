package com.yapin.shanduo.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.amap.api.maps.AMap;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.autonavi.amap.mapcore.AMapNativeRenderer;
import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dell on 2018/4/27.
 */

public class SearchtheperipheryActivity extends BaseActivity implements PoiSearch.OnPoiSearchListener{

    private Context context;
    private Activity activity;


//    @BindView(R.id.search_edit_text)
//    EditText search_edit_text;
//    @BindView(R.id.search_edit_delete)
//    ImageView search_edit_delete;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchtheperiphery);
        context = ShanDuoPartyApplication.getContext();
        activity = this;
        ButterKnife.bind(this);


    }


    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        PoiSearch.Query query=new PoiSearch.Query("","");
        query.setPageSize(10);
//        query.getPageNum();

        PoiSearch poiSearch = new PoiSearch(this, query);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn();


    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
