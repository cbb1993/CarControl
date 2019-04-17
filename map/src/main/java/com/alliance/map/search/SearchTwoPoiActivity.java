package com.alliance.map.search;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;
import com.alliance.map.R;
import com.alliance.map.RestRouteShowActivity;
import com.alliance.map.RouteBean;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.base.baselib.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class SearchTwoPoiActivity extends BaseActivity implements
        Inputtips.InputtipsListener, AdapterView.OnItemClickListener, View.OnTouchListener, View.OnClickListener, PoiSearch.OnPoiSearchListener {
    private AutoCompleteTextView ev_search_start, ev_search_end;
    private ListView resultList;
    private List<Tip> mCurrentTipList;
    private SearchResultAdapter resultAdapter;
    private ProgressBar loadingBar;
    private TextView tvMsg, tv_sure;
    private String city = "上海市";
    private boolean isEnd = false;  //false 修改起点  true修改终点

    private RouteBean startBean, endBean;


    @Override
    public int getLayoutId() {
        return R.layout.activity_search_two_poi;
    }

    @Override
    public void initViews() {
        super.initViews();
        city = getIntent().getStringExtra("city");
        if (getIntent().getSerializableExtra("start") != null) {
            startBean = (RouteBean) getIntent().getSerializableExtra("start");
        }

        if (getIntent().getSerializableExtra("end") != null) {
            endBean = (RouteBean) getIntent().getSerializableExtra("end");
        }

        findViews();
    }


    private void findViews() {
        ev_search_start = findViewById(R.id.ev_search_start);
        ev_search_end = findViewById(R.id.ev_search_end);
        resultList = findViewById(R.id.resultList);
        loadingBar = findViewById(R.id.search_loading);
        tvMsg = findViewById(R.id.tv_msg);
        tv_sure = findViewById(R.id.tv_sure);


        ev_search_end.requestFocus();
        resultList.setOnItemClickListener(this);
        resultList.setOnTouchListener(this);
        tvMsg.setVisibility(View.GONE);

        setLoadingVisible(false);

        if (startBean != null) {
            ev_search_start.setText(startBean.getRouteName());
        }
        if (endBean != null) {
            ev_search_end.setText(endBean.getRouteName());
        }

        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (endBean != null&&startBean!=null) {
                    Intent intent = new Intent(SearchTwoPoiActivity.this, RestRouteShowActivity.class);
                    intent.putExtra("start", startBean);
                    intent.putExtra("end", endBean);
                    setResult(RestRouteShowActivity.REQUEST_ROUTE_CODE, intent);
                    finish();
                }else {
                    Toast.makeText(SearchTwoPoiActivity.this,"填写信息错误",Toast.LENGTH_SHORT);
                }
            }
        });


        ev_search_start.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                isEnd = false;
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
                            Inputtips inputTips = new Inputtips(getApplicationContext(), inputquery);
                            inputTips.setInputtipsListener(SearchTwoPoiActivity.this);
                            inputTips.requestInputtipsAsyn();
                        } else {
                            resultList.setVisibility(View.GONE);
                        }
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ev_search_end.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                isEnd = true;
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
                            Inputtips inputTips = new Inputtips(getApplicationContext(), inputquery);
                            inputTips.setInputtipsListener(SearchTwoPoiActivity.this);
                            inputTips.requestInputtipsAsyn();
                        } else {
                            resultList.setVisibility(View.GONE);
                        }
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void setLoadingVisible(boolean isVisible) {
        if (isVisible) {
            loadingBar.setVisibility(View.VISIBLE);
        } else {
            loadingBar.setVisibility(View.GONE);
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //点击提示后再次进行搜索，获取POI出入口信息
        if (mCurrentTipList != null) {
            Tip tip = (Tip) parent.getItemAtPosition(position);
            if (isEnd) {
                // 终点
                ev_search_end.setText( tip.getName());
                ev_search_end.setSelection(tip.getName().length());
                endBean = new RouteBean(tip.getName(),tip.getPoint().getLatitude(),tip.getPoint().getLongitude());
            } else {
                // 起点
                ev_search_start.setText( tip.getName());
                ev_search_start.setSelection(tip.getName().length());
                startBean = new RouteBean(tip.getName(),tip.getPoint().getLatitude(),tip.getPoint().getLongitude());
            }
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
                    resultAdapter = new SearchResultAdapter(getApplicationContext(), mCurrentTipList);
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
