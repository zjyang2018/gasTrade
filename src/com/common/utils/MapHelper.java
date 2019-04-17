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
		try {
			JSONObject result = HttpUrlClient.doGetStr(url);
			if ("OK".equals(result.getString("status"))) {
				return result.getJSONObject("result");
			}
		} catch (Exception e) {
			throw new RuntimeException("地址填写错误,请检查");
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

	/**
	 * 坐标转换，腾讯地图转换成百度地图坐标
	 * 
	 * @param lat
	 *            腾讯纬度
	 * @param lon
	 *            腾讯经度
	 * @return 返回结果：经度,纬度
	 */
	public static double[] map_tx2bd(double lat, double lon) {
		double bd_lat;
		double bd_lon;
		double x_pi = 3.14159265358979324;
		double x = lon, y = lat;
		double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
		double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);
		bd_lon = z * Math.cos(theta) + 0.0065;
		bd_lat = z * Math.sin(theta) + 0.006;

		System.out.println("bd_lat:" + bd_lat);
		System.out.println("bd_lon:" + bd_lon);

		return new double[] { bd_lat, bd_lon };
	}

	/**
	 * 坐标转换，百度地图坐标转换成腾讯地图坐标
	 * 
	 * @param lat
	 *            百度坐标纬度
	 * @param lon
	 *            百度坐标经度
	 * @return 返回结果：纬度,经度
	 */
	public static double[] map_bd2tx(double lat, double lon) {
		double tx_lat;
		double tx_lon;
		double x_pi = 3.14159265358979324;
		double x = lon - 0.0065, y = lat - 0.006;
		double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
		double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
		tx_lon = z * Math.cos(theta);
		tx_lat = z * Math.sin(theta);
		return new double[] { tx_lat, tx_lon };
	}

	public static void main(String[] args) {
		String address = "深圳市南山区深大地铁站A出口";
		JSONObject result = addressToPoint(address);
		System.out.println(result.toJSONString());
		System.out.println(addressToLocation(address));
		// 22.565914,113.986665
		// 22.54584,113.942845
	}

}
