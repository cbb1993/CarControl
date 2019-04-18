package com.alliance.map.naviui


import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.SparseArray
import android.view.View
import android.widget.Toast
import com.alliance.map.R
import com.alliance.map.RouteBean
import com.alliance.map.Utils
import com.alliance.map.search.SearchTwoPoiActivity
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.model.MyLocationStyle
import com.amap.api.navi.AMapNavi
import com.amap.api.navi.model.AMapNaviPath
import com.amap.api.navi.model.NaviLatLng
import com.amap.api.navi.view.RouteOverLay
import com.base.baselib.base.BaseActivity
import kotlinx.android.synthetic.main.activity_map_calculate_route.*
import java.util.HashMap

class MapCalculateRouteActivity : BaseActivity(), AMap.OnMapLoadedListener, MapNaviListener, View.OnClickListener {

    companion object {
        const val ROUTE_UNSELECTED_TRANSPARENCY = 0.3f
        const val ROUTE_SELECTED_TRANSPARENCY = 1f
        const val START_NAVI = "startNaviBean"
        const val END_NAVI = "endNaviBean"
        const val REQUEST_ROUTE_CODE = 102
    }

    /**
     * 导航对象（单利模式）
     */
    private var mAMapNavi: AMapNavi? = null
    private var mAMap: AMap? = null
    private var startNavi: RouteBean? = null
    private var endNavi: RouteBean? = null
    val startList = ArrayList<NaviLatLng>() //起始坐标集合
    val wayList = ArrayList<NaviLatLng>()  //经过坐标集合
    val endList = ArrayList<NaviLatLng>() //终点坐标集合
    val routeOverlays = SparseArray<RouteOverLay>() //终点坐标集合
    var strategyFlag = 0
    var routeID = -1
    var zindex = 1  //路线的权值，重合路线情况下，权值高的路线会覆盖权值低的路线
    var myLocationStyle: MyLocationStyle = MyLocationStyle()

    lateinit var city: String

    override fun getLayoutId(): Int {
        return R.layout.activity_map_calculate_route
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navi_view.onCreate(savedInstanceState)
        init()
        initNavi()
    }

    private fun initNavi() {
        city = intent.getStringExtra("city")
        startNavi = intent.getSerializableExtra(START_NAVI) as RouteBean?
        endNavi = intent.getSerializableExtra(END_NAVI) as RouteBean?
        tv_start.text = startNavi?.routeName
        tv_end.text = endNavi?.routeName
        //将起始和终点位置放入列表
        if (startNavi != null) startList.add(NaviLatLng(startNavi?.latitude!!, startNavi?.longitude!!))
        if (endNavi != null) endList.add(NaviLatLng(endNavi?.latitude!!, endNavi?.longitude!!))
        //当没有给定起始和终点位置时首先定位到当前位置
//        if (startNavi == null || endNavi == null) {
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE)
        mAMap?.myLocationStyle = myLocationStyle
        mAMap?.isMyLocationEnabled = true
        mAMap?.moveCamera(CameraUpdateFactory.zoomTo(18f))
//        }
        mAMapNavi = AMapNavi.getInstance(applicationContext)
        mAMapNavi?.addAMapNaviListener(this)
    }

    private fun init() {
        calculate_route_start_navi.setOnClickListener(this)
        map_traffic.setOnClickListener(this)
        strategy_choose.setOnClickListener(this)
        route_line_one.setOnClickListener(this)
        route_line_two.setOnClickListener(this)
        route_line_three.setOnClickListener(this)
        tv_start.setOnClickListener(this)
        tv_end.setOnClickListener(this)


        if (mAMap == null) {
            mAMap = navi_view.map
            mAMap?.setTrafficEnabled(false)
            mAMap?.setOnMapLoadedListener(this)
            map_traffic.setImageResource(R.mipmap.map_traffic_white)
            val uiSettings = mAMap?.getUiSettings()
            uiSettings?.isZoomControlsEnabled = false
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.calculate_route_start_navi -> startNavi()
            R.id.map_traffic -> setTraffic()
            R.id.strategy_choose -> strategyChoose()
            R.id.route_line_one -> focuseRouteLine(true, false, false)
            R.id.route_line_two -> focuseRouteLine(false, true, false)
            R.id.route_line_three -> focuseRouteLine(false, false, true)
            R.id.tv_start -> skip()
            R.id.tv_end -> skip()
        }
    }

    private fun skip() {
        val intent = Intent(this@MapCalculateRouteActivity, SearchTwoPoiActivity::class.java)
        intent.putExtra("city", city)
        intent.putExtra("start", startNavi)
        intent.putExtra("end", endNavi)
        startActivityForResult(intent, REQUEST_ROUTE_CODE)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_ROUTE_CODE && data != null
            && data.getSerializableExtra("start") != null && data.getSerializableExtra("end") != null
        ) {
            startNavi = data.getSerializableExtra("start") as RouteBean?
            endNavi = data.getSerializableExtra("end") as RouteBean?
            tv_start.text = startNavi?.routeName
            tv_end.text = endNavi?.routeName
            startList.clear()
            endList.clear()
            if (startNavi != null) startList.add(NaviLatLng(startNavi?.latitude!!, startNavi?.longitude!!))
            if (endNavi != null) endList.add(NaviLatLng(endNavi?.latitude!!, endNavi?.longitude!!))
            calculateDriveRoute()
        }

    }

    /**
     *路线tag选中设置
     */
    private fun focuseRouteLine(lineOne: Boolean, lineTwo: Boolean, lineThree: Boolean) {
        setLinelayoutOne(lineOne)
        setLinelayoutTwo(lineTwo)
        setLinelayoutThree(lineThree)
    }

    /**
     * 第三条路线是否加粗
     */
    private fun setLinelayoutThree(lineThree: Boolean) {
        if (route_line_three.visibility != View.VISIBLE)
            return
        val overlay: RouteOverLay = routeOverlays.get(route_line_three.getTag() as Int)
        if (lineThree) {
            routeID = route_line_three.getTag() as Int
            calculate_route_navi_overview.text = Utils.getRouteOverView(overlay.aMapNaviPath)
            mAMapNavi?.selectRouteId(routeID)
            overlay.setTransparency(ROUTE_SELECTED_TRANSPARENCY)
            overlay.setZindex(zindex++)
            route_line_three_view.visibility = View.VISIBLE
            route_line_three_strategy.setTextColor(resources.getColor(R.color.colorBlue))
            route_line_three_time.setTextColor(resources.getColor(R.color.colorBlue))
            route_line_three_distance.setTextColor(resources.getColor(R.color.colorBlue))
        } else {
            overlay.setTransparency(ROUTE_UNSELECTED_TRANSPARENCY)
            route_line_three_view.visibility = View.INVISIBLE
            route_line_three_strategy.setTextColor(resources.getColor(R.color.colorDark))
            route_line_three_time.setTextColor(resources.getColor(R.color.colorDark))
            route_line_three_distance.setTextColor(resources.getColor(R.color.colorDark))
        }
    }

    /**
     * 第二条路线是否加粗
     */
    private fun setLinelayoutTwo(lineTwo: Boolean) {
        if (route_line_two.visibility != View.VISIBLE)
            return
        val overlay: RouteOverLay = routeOverlays.get(route_line_two.getTag() as Int)
        if (lineTwo) {
            routeID = route_line_two.getTag() as Int
            calculate_route_navi_overview.text = Utils.getRouteOverView(overlay.aMapNaviPath)
            mAMapNavi?.selectRouteId(routeID)
            overlay.setTransparency(ROUTE_SELECTED_TRANSPARENCY)
            overlay.setZindex(zindex++)
            route_line_two_view.visibility = View.VISIBLE
            route_line_two_strategy.setTextColor(resources.getColor(R.color.colorBlue))
            route_line_two_time.setTextColor(resources.getColor(R.color.colorBlue))
            route_line_two_distance.setTextColor(resources.getColor(R.color.colorBlue))
        } else {
            overlay.setTransparency(ROUTE_UNSELECTED_TRANSPARENCY)
            route_line_two_view.visibility = View.INVISIBLE
            route_line_two_strategy.setTextColor(resources.getColor(R.color.colorDark))
            route_line_two_time.setTextColor(resources.getColor(R.color.colorDark))
            route_line_two_distance.setTextColor(resources.getColor(R.color.colorDark))
        }
    }

    /**
     * 第一条路线是否加粗
     */
    private fun setLinelayoutOne(lineOne: Boolean) {
        if (route_line_one.visibility != View.VISIBLE)
            return
        val overlay: RouteOverLay = routeOverlays.get(route_line_one.getTag() as Int)
        if (lineOne) {
            routeID = route_line_one.getTag() as Int
            calculate_route_navi_overview.text = Utils.getRouteOverView(overlay.aMapNaviPath)
            mAMapNavi?.selectRouteId(routeID)
            overlay.setTransparency(ROUTE_SELECTED_TRANSPARENCY)
            overlay.setZindex(zindex++)
            route_line_one_view.visibility = View.VISIBLE
            route_line_one_strategy.setTextColor(resources.getColor(R.color.colorBlue))
            route_line_one_time.setTextColor(resources.getColor(R.color.colorBlue))
            route_line_one_distance.setTextColor(resources.getColor(R.color.colorBlue))
        } else {
            overlay.setTransparency(ROUTE_UNSELECTED_TRANSPARENCY)
            route_line_one_view.visibility = View.INVISIBLE
            route_line_one_strategy.setTextColor(resources.getColor(R.color.colorDark))
            route_line_one_time.setTextColor(resources.getColor(R.color.colorDark))
            route_line_one_distance.setTextColor(resources.getColor(R.color.colorDark))
        }
    }

    /**
     * 设置偏好
     */
    private fun strategyChoose() {


    }

    /**
     *是否显示路况
     */
    private fun setTraffic() {
        if (mAMap!!.isTrafficEnabled()) {
            map_traffic.setImageResource(R.mipmap.map_traffic_white)
            mAMap?.setTrafficEnabled(false)
        } else {
            map_traffic.setImageResource(R.mipmap.map_traffic_hl_white)
            mAMap?.setTrafficEnabled(true)
        }
    }

    /**
     * 开始导航
     */
    private fun startNavi() {
        if (routeID != -1) {
            mAMapNavi?.selectRouteId(routeID)
            val gpsintent = Intent(applicationContext, MapRouteNaviActivity::class.java)
            gpsintent.putExtra("gps", false) // gps 为true为真实导航，为false为模拟导航
            startActivity(gpsintent)
        }
    }

    override fun onMapLoaded() {
        calculateDriveRoute()
    }

    /**
     * 计算路径规划计算
     */
    fun calculateDriveRoute() {
        try {
            strategyFlag = mAMapNavi!!.strategyConvert(
                false,
                false,
                false,
                false,
                true
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
        mAMapNavi?.calculateDriveRoute(startList, endList, wayList, strategyFlag)
    }

    override fun onCalculateRouteSuccess(p0: IntArray?) {
        cleanRouteOverlay()
        var paths = mAMapNavi?.naviPaths
        for (i in 0 until paths?.size!!) {
            val path = paths.get(p0!![i])
            path?.run {
                drawRoutes(p0!![i], path)
            }
        }
        setRouteLineTag(paths, p0)
        mAMap?.setMapType(AMap.MAP_TYPE_NAVI)

    }

    /**
     * 多路线回调 根据查询到的线路来控制 三个方案的显示与否
     */
    private fun setRouteLineTag(paths: HashMap<Int, AMapNaviPath>, p0: IntArray?) {
        if (p0?.size!! < 1) {
            visiableRouteLine(false, false, false)
            return
        }
        var indexOne = 0
        val stragegyTagOne = paths.get(p0[indexOne])?.labels
        setLinelayoutOneContent(p0[indexOne], stragegyTagOne)
        if (p0.size == 1) {
            visiableRouteLine(true, false, false)
            focuseRouteLine(true, false, false)
            return
        }

        val indexTwo = 1
        val stragegyTagTwo = paths[p0[indexTwo]]!!.labels
        setLinelayoutTwoContent(p0[indexTwo], stragegyTagTwo)
        if (p0.size == 2) {
            visiableRouteLine(true, true, false)
            focuseRouteLine(true, false, false)
            return
        }

        val indexThree = 2
        val stragegyTagThree = paths[p0[indexThree]]!!.labels
        setLinelayoutThreeContent(p0[indexThree], stragegyTagThree)
        if (p0.size >= 3) {
            visiableRouteLine(true, true, true)
            focuseRouteLine(true, false, false)
        }
    }

    /**
     * 设置第一条线路Tab 内容
     */
    private fun setLinelayoutOneContent(i: Int, stragegyTagOne: String?) {
        route_line_one.setTag(i)
        val overLay = routeOverlays.get(i)
        overLay.zoomToSpan()
        val path = overLay.aMapNaviPath
        route_line_one_strategy.text = stragegyTagOne
        val timeDes = Utils.getFriendlyTime(path.allTime)
        route_line_one_time.setText(timeDes)
        val disDes = Utils.getFriendlyDistance(path.allLength)
        route_line_one_distance.setText(disDes)
    }

    /**
     * 设置第二条线路Tab 内容
     */
    private fun setLinelayoutTwoContent(i: Int, stragegyTagTwo: String?) {
        route_line_two.setTag(i)
        val overLay = routeOverlays.get(i)
        overLay.zoomToSpan()
        val path = overLay.aMapNaviPath
        route_line_two_strategy.text = stragegyTagTwo
        val timeDes = Utils.getFriendlyTime(path.allTime)
        route_line_two_time.setText(timeDes)
        val disDes = Utils.getFriendlyDistance(path.allLength)
        route_line_two_distance.setText(disDes)
    }

    /**
     * 设置第三条线路Tab 内容
     */
    private fun setLinelayoutThreeContent(i: Int, stragegyTagThree: String?) {
        route_line_three.setTag(i)
        val overLay = routeOverlays.get(i)
        overLay.zoomToSpan()
        val path = overLay.aMapNaviPath
        route_line_three_strategy.text = stragegyTagThree
        val timeDes = Utils.getFriendlyTime(path.allTime)
        route_line_three_time.setText(timeDes)
        val disDes = Utils.getFriendlyDistance(path.allLength)
        route_line_three_distance.setText(disDes)
    }

    /**
     * 控制方案tab是否显示
     */
    private fun visiableRouteLine(lineOne: Boolean, lineTwo: Boolean, lineThree: Boolean) {
        setLinelayoutOneVisiable(lineOne)
        setLinelayoutTwoVisiable(lineTwo)
        setLinelayoutThreeVisiable(lineThree)
    }

    /**
     * 控制方案一tab是否显示
     */
    private fun setLinelayoutOneVisiable(lineOne: Boolean) {
        route_line_one.visibility = if (lineOne) View.VISIBLE else View.GONE
    }

    /**
     * 控制方案二tab是否显示
     */
    private fun setLinelayoutTwoVisiable(lineTwo: Boolean) {
        route_line_two.visibility = if (lineTwo) View.VISIBLE else View.GONE
    }

    /**
     * 控制方案三tab是否显示
     */
    private fun setLinelayoutThreeVisiable(lineThree: Boolean) {
        route_line_three.visibility = if (lineThree) View.VISIBLE else View.GONE
    }

    /**
     * 绘制路线
     */
    private fun drawRoutes(i: Int, path: AMapNaviPath) {
        mAMap?.moveCamera(CameraUpdateFactory.changeTilt(0F))
        val routeOverLay = RouteOverLay(mAMap, path, this@MapCalculateRouteActivity)
        routeOverLay.width = 40f
        routeOverLay.isTrafficLine = true
        routeOverLay.setLightsVisible(false)
        routeOverLay.addToMap()
        routeOverlays.put(i, routeOverLay)
    }

    /**
     * 清空路线
     */
    private fun cleanRouteOverlay() {
        for (i in 0 until routeOverlays.size()) {
            val key = routeOverlays.keyAt(i)
            val overlay = routeOverlays.get(key)
            overlay.removeFromMap()
            overlay.destroy()
        }
        routeOverlays.clear()
    }

    override fun onResume() {
        super.onResume()
        navi_view.onResume()
    }

    override fun onPause() {
        super.onPause()
        navi_view.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        navi_view.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        startList.clear()
        wayList.clear()
        endList.clear()
        routeOverlays.clear()
        navi_view.onDestroy()
        mAMapNavi?.removeAMapNaviListener(this)
        mAMapNavi?.destroy()
    }

}
