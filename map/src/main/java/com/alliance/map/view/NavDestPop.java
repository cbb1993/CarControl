package com.alliance.map.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.alliance.map.MapTestActivity;
import com.alliance.map.R;
import com.alliance.map.RouteBean;
import com.alliance.map.naviui.MapCalculateRouteActivity;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.help.Tip;
import com.amap.api.services.route.DistanceResult;
import com.amap.api.services.route.DistanceSearch;

import java.text.DecimalFormat;

/**
 * Created by 坎坎.
 * Date: 2019/4/17
 * Time: 10:15
 * describe:
 */
public class NavDestPop extends PopupWindow implements DistanceSearch.OnDistanceSearchListener {
    public NavDestPop(View contentView, int width, int height, boolean focusable) {
        super(contentView, width, height, focusable);
    }

    private RouteBean startBean, endBean;

    public NavDestPop(Context context) {
        this(LayoutInflater.from(context).inflate(
                R.layout.pop_nav_dest, null), ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        initView(context);
    }

    private TextView tv_dest, tv_far, tv_location;
    private View btn_line;
    private DistanceSearch distanceSearch;
    private String city;

    private void initView(final Context context) {
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setOutsideTouchable(false);

        tv_dest = getContentView().findViewById(R.id.tv_dest);
        tv_far = getContentView().findViewById(R.id.tv_far);
        tv_location = getContentView().findViewById(R.id.tv_location);

        btn_line = getContentView().findViewById(R.id.btn_line);

        distanceSearch = new DistanceSearch(context);
        distanceSearch.setDistanceSearchListener(this);


        btn_line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(startBean!=null&&endBean!=null&&city!=null){
                    Intent intent = new Intent(context, MapCalculateRouteActivity.class);
                    intent.putExtra(MapCalculateRouteActivity.START_NAVI, startBean);
                    intent.putExtra(MapCalculateRouteActivity.END_NAVI,endBean);
                    intent.putExtra("city",city);
                    context.startActivity(intent);
                }
            }
        });
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setData(LatLonPoint start, Tip tip) {

        startBean = new RouteBean("我的位置",start.getLatitude(),start.getLongitude());
        endBean = new RouteBean(tip.getName(),tip.getPoint().getLatitude(),tip.getPoint().getLongitude());

        tv_dest.setText(tip.getName());
        tv_location.setText(tip.getAddress());

        DistanceSearch.DistanceQuery query = new DistanceSearch.DistanceQuery();
        LatLonPoint end = new LatLonPoint(tip.getPoint().getLatitude(), tip.getPoint().getLongitude());
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
