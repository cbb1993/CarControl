package com.alliance.map;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.amap.api.services.route.DistanceResult;
import com.amap.api.services.route.DistanceSearch;
import com.base.baselib.base.BaseActivity;
import com.base.baselib.base.BaseFragment;
import com.base.event.FragmentBack;
import com.base.event.LocationSearchEvent;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;

public class RestRouteShowActivity extends BaseFragment implements OnClickListener ,DistanceSearch.OnDistanceSearchListener{

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



    @Override
    public int attachLayoutRes() {
        return R.layout.activity_rest_calculate;
    }

    @Override
    public void initView(@NotNull View view) {

    }

    @Override
    public void lazyLoad() {

    }

    private View search_poi_content,ll_detail,btn_line;

    private TextView tv_dest,tv_far,tv_location;

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {

        EventBus.getDefault().register(this);

        tv_search = findViewById(R.id.tv_search);

        search_poi_content = findViewById(R.id.search_poi_content);
        ll_detail = findViewById(R.id.ll_detail);

        tv_dest = findViewById(R.id.tv_dest);
        tv_far = findViewById(R.id.tv_far);
        tv_location = findViewById(R.id.tv_location);
        btn_line = findViewById(R.id.btn_line);

        btn_line.setOnClickListener(this);

        tv_search.setOnClickListener(this);
        mRouteMapView = (MapView) findViewById(R.id.navi_view);
        mRouteMapView.onCreate(savedInstanceState);
        mAmap = mRouteMapView.getMap();


        myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);


        mAmap.setMyLocationEnabled(true);
        mAmap.moveCamera(CameraUpdateFactory.zoomTo(16));


        mAmap.setMyLocationStyle(myLocationStyle);
        mAmap.getUiSettings().setMyLocationButtonEnabled(true);
        mAmap.getUiSettings().setZoomControlsEnabled(false);

        mAmap.setOnMyLocationChangeListener(new AMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                if (location != null && location.getExtras() != null) {
                    city = location.getExtras().getString("City");
                }
            }
        });



    }

    /**
     * 方法必须重写
     */
    @Override
    public void onResume() {
        super.onResume();
        mRouteMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mRouteMapView.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mRouteMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        mRouteMapView.onDestroy();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_search) {
//            Intent intent = new Intent(getActivity(), SearchPoiActivity.class);
//            intent.putExtra("city", city);
//            startActivityForResult(intent, REQUEST_POI_CODE);
            SearchPoiActivity frag = new SearchPoiActivity();
            Bundle bundle = new Bundle();
            bundle.putString("city", city);
            frag.setArguments(bundle);
            showFrag(frag);

        } else if (i == R.id.btn_line) {
            Intent intent = new Intent(getActivity(), MapCalculateRouteActivity.class);
            intent.putExtra(MapCalculateRouteActivity.START_NAVI, new RouteBean("我的位置",
                    mAmap.getMyLocation().getLatitude(), mAmap.getMyLocation().getLongitude()));

            if (endBean != null) {
                intent.putExtra(MapCalculateRouteActivity.END_NAVI, endBean);
            }
            intent.putExtra("city", city);
            startActivity(intent);
        }
    }

    private void showFrag(Fragment fragment) {
        ll_detail.setVisibility(View.VISIBLE);
        search_poi_content.setVisibility(View.VISIBLE);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.search_poi_content, fragment).commit();
    }

    private void dismissFrag() {
        search_poi_content.setVisibility(View.GONE);
    }


    // 保存上一次的marker
    private Marker marker;

    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
    public void receive(LocationSearchEvent event) {
        dismissFrag();
        if (event != null && event.getCode() == REQUEST_POI_CODE) {
            //搜索位置
            tv_search.setText(event.getName());
            LatLng latLng = new LatLng(event.getLatitude(), event.getLongitude());
            if (marker == null) {
                marker = mAmap.addMarker(new MarkerOptions().position(latLng).title(event.getName()).snippet(""));
            } else {
                marker.setPosition(latLng);
            }
            mAmap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(latLng, 16, 0, 0)), 500, null);

            LatLonPoint start = new LatLonPoint(mAmap.getMyLocation().getLatitude(), mAmap.getMyLocation().getLongitude());
            setData(start, event);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
    public void receive(FragmentBack event) {
        dismissFrag();
    }
    private RouteBean startBean, endBean;
    private DistanceSearch distanceSearch;
    public void setData(LatLonPoint start, LocationSearchEvent event) {
        distanceSearch = new DistanceSearch(getActivity());
        distanceSearch.setDistanceSearchListener(this);
        startBean = new RouteBean("我的位置",start.getLatitude(),start.getLongitude());
        endBean = new RouteBean(event.getName(),event.getLatitude(),event.getLongitude());
        tv_dest.setText(event.getName());
        tv_location.setText(event.getAddress());
        DistanceSearch.DistanceQuery query = new DistanceSearch.DistanceQuery();
        LatLonPoint end = new LatLonPoint(event.getLatitude(), event.getLongitude());
        query.addOrigins(start);
        query.setDestination(end);
        query.setType(DistanceSearch.TYPE_DISTANCE);
        distanceSearch.calculateRouteDistanceAsyn(query);
    }

    @Override
    public void onDistanceSearched(DistanceResult distanceResult, int i) {
        if (i == 1000 && distanceResult != null && distanceResult.getDistanceResults() != null && distanceResult.getDistanceResults().size() > 0) {
            double far = distanceResult.getDistanceResults().get(0).getDistance();
            far = far / 1000;
            DecimalFormat df = new DecimalFormat("#.0");
            tv_far.setText(String.format("%s公里", df.format(far)));
        }
    }
}
