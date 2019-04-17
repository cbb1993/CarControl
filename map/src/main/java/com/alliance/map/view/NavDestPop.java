package com.alliance.map.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.alliance.map.R;
import com.amap.api.maps.model.Poi;
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
    public NavDestPop(Context context) {
        this( LayoutInflater.from(context).inflate(
                R.layout.pop_nav_dest, null), ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,true);

        initView(context);
    }

    private TextView tv_dest,tv_far,tv_location;
    private DistanceSearch distanceSearch;

    private void initView(Context context) {
      setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
      setOutsideTouchable(false);

        tv_dest = getContentView().findViewById(R.id.tv_dest);
        tv_far = getContentView().findViewById(R.id.tv_far);
        tv_location = getContentView().findViewById(R.id.tv_location);
        distanceSearch = new DistanceSearch(context);
        distanceSearch.setDistanceSearchListener(this);
    }


    public void setData(LatLonPoint start, Tip tip){
        tv_dest.setText(tip.getName());
        tv_location.setText(tip.getAddress());

        DistanceSearch.DistanceQuery query = new DistanceSearch.DistanceQuery();
        LatLonPoint end = new LatLonPoint (tip.getPoint().getLatitude(), tip.getPoint().getLongitude());
        query.addOrigins(start);
        query.setDestination(end);
        query.setType(DistanceSearch.TYPE_DISTANCE);
        distanceSearch.calculateRouteDistanceAsyn(query);

    }

    @Override
    public void onDistanceSearched(DistanceResult distanceResult, int i) {
        if(distanceResult!=null&&distanceResult.getDistanceResults()!=null&&distanceResult.getDistanceResults().size()>0){

            double far = distanceResult.getDistanceResults().get(0).getDistance();

            far = far / 1000;
            DecimalFormat df = new DecimalFormat("#.0");
            tv_far.setText(String.format("%s公里", df.format(far)));
        }
    }
}
