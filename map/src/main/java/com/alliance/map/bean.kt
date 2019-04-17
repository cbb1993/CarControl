package com.alliance.map

import java.io.Serializable

/**
 * Created by dhy
 * Date: 2019/4/17
 * Time: 11:47
 * describe:
 */
/**
 * 坐标 经纬度和名称
 * @param routeName 坐标名称
 * @param latitude  坐标纬度
 * @param longitude  坐标精度
 */
data class RouteBean(
    val routeName: String,
    val longitude: Double,
    val latitude: Double
) : Serializable