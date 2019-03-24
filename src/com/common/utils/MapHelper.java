package com.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.common.http.HttpUrlClient;

/**
 * 通过地图上的两个坐标计算距离 Created by Administrator on 2019/3/11.
 */
public class MapHelper {
	/**
	 * 地球半径
	 */
	private static double EarthRadius = 6378.137;

	/**
	 * 经纬度转化成弧度 Add by 成长的小猪（Jason.Song） on 2017/11/01
	 * http://blog.csdn.net/jasonsong2008
	 *
	 * @param d
	 *            经度/纬度
	 * @return
	 */
	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	/**
	 * 计算两个坐标点之间的距离 http://blog.csdn.net/jasonsong2008
	 *
	 * @param firstLatitude
	 *            第一个坐标的纬度
	 * @param firstLongitude
	 *            第一个坐标的经度
	 * @param secondLatitude
	 *            第二个坐标的纬度
	 * @param secondLongitude
	 *            第二个坐标的经度
	 * @return 返回两点之间的距离，单位：公里/千米
	 */
	public static double getDistance(double firstLatitude, double firstLongitude, double secondLatitude,
			double secondLongitude) {
		double firstRadLat = rad(firstLatitude);
		double firstRadLng = rad(firstLongitude);
		double secondRadLat = rad(secondLatitude);
		double secondRadLng = rad(secondLongitude);

		double a = firstRadLat - secondRadLat;
		double b = firstRadLng - secondRadLng;
		double cal = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(firstRadLat) * Math.cos(secondRadLat) * Math.pow(Math.sin(b / 2), 2))) * EarthRadius;
		double result = Math.round(cal * 10000d) / 10000d;
		return result;
	}

	/**
	 * 计算两个坐标点之间的距离 http://blog.csdn.net/jasonsong2008
	 *
	 * @param firstPoint
	 *            第一个坐标点的（纬度,经度） 例如："31.2553210000,121.4620020000"
	 * @param secondPoint
	 *            第二个坐标点的（纬度,经度） 例如："31.2005470000,121.3269970000"
	 * @return 返回两点之间的距离，单位：公里/千米
	 */
	public static double GetPointDistance(String firstPoint, String secondPoint) {
		String[] firstArray = firstPoint.split(",");
		String[] secondArray = secondPoint.split(",");
		double firstLatitude = Double.valueOf(firstArray[0].trim());
		double firstLongitude = Double.valueOf(firstArray[1].trim());
		double secondLatitude = Double.valueOf(secondArray[0].trim());
		double secondLongitude = Double.valueOf(secondArray[1].trim());
		return getDistance(firstLatitude, firstLongitude, secondLatitude, secondLongitude);
	}

	public static JSONObject addressToPoint(String address) {
		final String KEY_1 = "7d9fbeb43e975cd1e9477a7e5d5e192a";
		String url = "http://api.map.baidu.com/geocoder?address=" + address + "&output=json&key=" + KEY_1;
		JSONObject result = HttpUrlClient.doGetStr(url);
		if ("OK".equals(result.getString("status"))) {
			return result.getJSONObject("result");
		}
		// {"confidence":30,"level":"村庄","location":{"lat":22.564436,"lng":113.98797},"precise":0}
		return null;
	}

	public static JSONObject addressToLocation(String address) {
		JSONObject result = addressToPoint(address);
		if (result != null) {
			return result.getJSONObject("location");
		}
		// {"lat":22.564436,"lng":113.98797}
		return null;
	}

	public static void main(String[] args) {
		String address = "深圳市南山区桃源村北门";
		JSONObject result = addressToPoint(address);
		System.out.println(result.toJSONString());
		System.out.println(addressToLocation(address));
	}

}
