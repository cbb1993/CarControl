package com.alliance.map.search;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;
import com.alliance.map.R;
import com.alliance.map.RestRouteShowActivity;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Poi;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.base.baselib.base.BaseActivity;
import com.base.baselib.base.BaseFragment;
import com.base.baselib.utils.KbUtil;
import com.base.event.FragmentBack;
import com.base.event.LocationSearchEvent;
import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SearchPoiActivity extends BaseFragment implements TextWatcher,
        Inputtips.InputtipsListener, AdapterView.OnItemClickListener, View.OnTouchListener, View.OnClickListener, PoiSearch.OnPoiSearchListener {
    private AutoCompleteTextView mKeywordText;
    private ListView resultList;
    private List<Tip> mCurrentTipList;
    private SearchResultAdapter resultAdapter;
    private ProgressBar loadingBar;
    private TextView tvMsg,tv_search;
    private String city = "上海市";


    @Override
    public int attachLayoutRes() {
         return R.layout.activity_search_poi;
    }

    @Override
    public void initView(@NotNull View view) {

    }

    @Override
    public void lazyLoad() {
        city = getArguments().getString("city");
        findViews();
        resultList.setOnItemClickListener(this);
        resultList.setOnTouchListener(this);
        tvMsg.setVisibility(View.GONE);
        mKeywordText.addTextChangedListener(this);
        mKeywordText.requestFocus();
        KbUtil.showKeyboard(mKeywordText);
    }



    private void findViews() {
        mKeywordText = (AutoCompleteTextView) findViewById(R.id.ev_search);
        resultList = (ListView) findViewById(R.id.resultList);
        loadingBar = (ProgressBar) findViewById(R.id.search_loading);
        tvMsg = (TextView) findViewById(R.id.tv_msg);
        tv_search= findViewById(R.id.tv_search);
        View tv_back= findViewById(R.id.tv_back);

        setLoadingVisible(false);

        tv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mKeywordText.length()!=0){
                    setLoadingVisible(true);
                    InputtipsQuery inputquery = new InputtipsQuery(mKeywordText.getText().toString(), city);
                    Inputtips inputTips = new Inputtips(getActivity(), inputquery);
                    inputTips.setInputtipsListener(SearchPoiActivity.this);
                    inputTips.requestInputtipsAsyn();
                }
            }
        });


        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KbUtil.closeKeyboard(getActivity());
                EventBus.getDefault().post(new FragmentBack());
            }
        });
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        try {
            {
                if (tvMsg.getVisibility() == View.VISIBLE) {
                    tvMsg.setVisibility(View.GONE);
                }
                String newText = s.toString().trim();
                if (!TextUtils.isEmpty(newText)) {
                    setLoadingVisible(true);
                    InputtipsQuery inputquery = new InputtipsQuery(newText, city);
                    Inputtips inputTips = new Inputtips(getActivity(), inputquery);
                    inputTips.setInputtipsListener(this);
                    inputTips.requestInputtipsAsyn();
                } else {
                    resultList.setVisibility(View.GONE);
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void setLoadingVisible(boolean isVisible) {
        if (isVisible) {
            loadingBar.setVisibility(View.VISIBLE);
        } else {
            loadingBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //点击提示后再次进行搜索，获取POI出入口信息
        if (mCurrentTipList != null) {
            Tip tip = (Tip) parent.getItemAtPosition(position);
//            selectedPoi = new Poi(tip.getName(), new LatLng(tip.getPoint().getLatitude(), tip.getPoint().getLongitude()), tip.getPoiID());
//            if (!TextUtils.isEmpty(selectedPoi.getPoiId())) {
//                PoiSearch.Query query = new PoiSearch.Query(selectedPoi.getName(), "", city);
//                query.setDistanceSort(false);
//                query.requireSubPois(true);
//                PoiSearch poiSearch = new PoiSearch(getApplicationContext(), query);
//                poiSearch.setOnPoiSearchListener(this);
//                poiSearch.searchPOIIdAsyn(selectedPoi.getPoiId());
//            }

            LocationSearchEvent event = new LocationSearchEvent(
                    RestRouteShowActivity.REQUEST_POI_CODE,
                    tip.getName(),tip.getAddress(),tip.getPoint().getLatitude(),tip.getPoint().getLongitude());

            EventBus.getDefault().post(event);

            KbUtil.closeKeyboard(getActivity());

//            Intent intent = new Intent(getActivity(), RestRouteShowActivity.class);
//            intent.putExtra("tip", tip);
//            setResult(RestRouteShowActivity.REQUEST_POI_CODE, intent);
//            finish();
        }
    }

    @Override
    public void onGetInputtips(List<Tip> tipList, int rCode) {
        setLoadingVisible(false);
        try {
            if (rCode == 1000) {
                mCurrentTipList = new ArrayList<Tip>();
                for (Tip tip : tipList) {
                    if (null == tip.getPoint()) {
                        continue;
                    }
                    mCurrentTipList.add(tip);
                }

                if (null == mCurrentTipList || mCurrentTipList.isEmpty()) {
                    tvMsg.setText("抱歉，没有搜索到结果，请换个关键词试试");
                    tvMsg.setVisibility(View.VISIBLE);
                    resultList.setVisibility(View.GONE);
                } else {
                    resultList.setVisibility(View.VISIBLE);
                    resultAdapter = new SearchResultAdapter(getActivity(), mCurrentTipList);
                    resultList.setAdapter(resultAdapter);
                    resultAdapter.notifyDataSetChanged();
                }
            } else {
                tvMsg.setText("出错了，请稍后重试");
                tvMsg.setVisibility(View.VISIBLE);
            }
        } catch (Throwable e) {
            tvMsg.setText("出错了，请稍后重试");
            tvMsg.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {

    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int errorCode) {

    }


}
