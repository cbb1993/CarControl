package com.alliance.map;

import android.Manifest;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import com.alliance.map.naviui.MapCalculateRouteActivity;
import com.base.baselib.base.BaseActivity;

/**
 * Created by 坎坎.
 * Date: 2019/4/15
 * Time: 16:47
 * describe:
 */
public class MapTestActivity extends BaseActivity {


    @Override
    public void initViews() {
        super.initViews();

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE
                } ,
                1);

        findViewById(R.id.nav).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MapTestActivity.this, RestRouteShowActivity.class));
            }
        });

        findViewById(R.id.btn_naviPage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapTestActivity.this, MapCalculateRouteActivity.class);
                intent.putExtra(MapCalculateRouteActivity.START_NAVI,new RouteBean("我的位置",39.90759, 116.392582));
                intent.putExtra(MapCalculateRouteActivity.END_NAVI,new RouteBean("北京朝阳沟（5号线）",39.993537, 116.472875));
                startActivity(intent);

            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_map_test;
    }
}
