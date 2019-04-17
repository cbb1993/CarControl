package com.alliance.map;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.alliance.map.naviui.MapCalculateRouteActivity;
import com.alliance.map.search.SearchPoiActivity;
import com.alliance.map.search.SearchTwoPoiActivity;
import com.alliance.map.view.NavDestPop;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.*;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.help.Tip;
import com.base.baselib.base.BaseActivity;
import org.jetbrains.annotations.Nullable;

public class RestRouteShowActivity extends BaseActivity implements OnClickListener {

    private AMap mAmap;
    /**
     * 地图对象
     */
    private MapView mRouteMapView;

    MyLocationStyle myLocationStyle;

    private TextView tv_search;

    // 当前定位城市
    private String city = "上海市";


    public static final int REQUEST_POI_CODE = 101;
    public static final int REQUEST_ROUTE_CODE = 102;

    private NavDestPop navDestPop ;


    @Override
    public int getLayoutId() {
        return R.layout.activity_rest_calculate;
    }

    @Override
    public void initViews(@Nullable Bundle savedInstanceState) {
        super.initViews(savedInstanceState);

        tv_search = findViewById(R.id.tv_search);

        navDestPop =new NavDestPop(this);

        Button gpsnavi = (Button) findViewById(R.id.gpsnavi);
        tv_search.setOnClickListener(this);
        gpsnavi.setOnClickListener(this);
        mRouteMapView = (MapView) findViewById(R.id.navi_view);
        mRouteMapView.onCreate(savedInstanceState);
        mAmap = mRouteMapView.getMap();


        myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);


        mAmap.setMyLocationEnabled(true);
        mAmap.moveCamera(CameraUpdateFactory.zoomTo(18));


        mAmap.setMyLocationStyle(myLocationStyle);
        mAmap.getUiSettings().setMyLocationButtonEnabled(true);

        mAmap.setOnMyLocationChangeListener(new AMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                if (location != null && location.getExtras() != null) {
                    city = location.getExtras().getString("City");
                    navDestPop.setCity(city);
                }
            }
        });


    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mRouteMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        mRouteMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mRouteMapView.onSaveInstanceState(outState);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRouteMapView.onDestroy();
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_search) {
            Intent intent = new Intent(RestRouteShowActivity.this, SearchPoiActivity.class);
            intent.putExtra("city", city);
            startActivityForResult(intent, REQUEST_POI_CODE);
        }else if (i == R.id.gpsnavi) {
            Intent intent = new Intent(RestRouteShowActivity.this, MapCalculateRouteActivity.class);
            intent.putExtra(MapCalculateRouteActivity.START_NAVI,  new RouteBean("我的位置",
                    mAmap.getMyLocation().getLatitude(),mAmap.getMyLocation().getLongitude()));
            intent.putExtra("city",city);
            startActivity(intent);
        }
    }

    // 保存上一次的marker
    private Marker marker;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null ) {
            if (requestCode == REQUEST_POI_CODE&& data.getParcelableExtra("tip") != null) {
                //搜索位置
                Tip tip = data.getParcelableExtra("tip");
                tv_search.setText(tip.getName());
                LatLng latLng = new LatLng(tip.getPoint().getLatitude(), tip.getPoint().getLongitude());
                if (marker == null) {
                    marker = mAmap.addMarker(new MarkerOptions().position(latLng).title(tip.getName()).snippet(""));
                }else {
                    marker.setPosition(latLng);
                }
                mAmap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(latLng, 18, 0, 0)), 500, null);

                navDestPop.showAtLocation(tv_search, Gravity.BOTTOM,0,0);
                LatLonPoint start = new LatLonPoint (mAmap.getMyLocation().getLatitude(), mAmap.getMyLocation().getLongitude());
                navDestPop.setData(start,tip);
            }
        }
    }


}
