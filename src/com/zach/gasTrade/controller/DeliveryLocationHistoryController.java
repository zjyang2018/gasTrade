/*
 * Powered By moti technology
 * Web Site: http://moti technology
 * Since 2017 - 2019
 */

package com.zach.gasTrade.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.common.http.HttpsUtil;
import com.common.utils.MapHelper;
import com.common.utils.XmlUtilCommon;
import com.common.wx.WeiXinSignUtil;
import com.zach.gasTrade.common.Constants;
import com.zach.gasTrade.common.DataResult;
import com.zach.gasTrade.common.Result;
import com.zach.gasTrade.dto.DeliveryNewLocationDto;
import com.zach.gasTrade.service.DeliveryLocationHistoryService;
import com.zach.gasTrade.service.DeliveryUserService;
import com.zach.gasTrade.vo.DeliveryLocationHistoryVo;
import com.zach.gasTrade.vo.DeliveryUserVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "派送员历史位置相关api")
@Controller
public class DeliveryLocationHistoryController extends CommonController {
	private Logger logger = Logger.getLogger(getClass());

	@Autowired
	private DeliveryLocationHistoryService deliveryLocationHistoryService;

	@Autowired
	private DeliveryUserService deliveryUserService;

	/**
	 * 根据条件查询
	 * 
	 * @param country
	 * @param result
	 * @return
	 */
	@ApiOperation(value = "历史位置查询", notes = "请求参数说明{\n" + "  \"deliveryUserId\": \"6666666\"\n" + "}\\n返回参数字段说明:\\n")
	@RequestMapping(value = "/deliveryLocationHistory/query")
	@ResponseBody
	public Result query(HttpServletRequest request, @RequestBody DeliveryLocationHistoryVo filterMask)
			throws Exception {
		DataResult result = DataResult.initResult();

		try {
			// 查询
			DeliveryLocationHistoryVo deliveryLocationHistory = deliveryLocationHistoryService
					.getDeliveryLocationHistoryBySelective(filterMask);
			result.setData(deliveryLocationHistory);

		} catch (RuntimeException e) {
			result.setCode(Constants.FAILURE);
			result.setMsg(e.getMessage());
			logger.error("系统异常," + e.getMessage(), e);
		} catch (Exception e) {
			result.setCode(Constants.FAILURE);
			result.setMsg("系统异常,请稍后重试");
			logger.error("系统异常,请稍后重试", e);
		}
		return result;

	}

	/**
	 * 获取所有派送员最新位置
	 * 
	 * @param country
	 * @param result
	 * @return
	 */
	@ApiOperation(value = "获取所有派送员最新位置", notes = "请求参数说明:\\n返回参数字段说明:\\n")
	@RequestMapping(value = "/deliveryLocationHistory/allDeliveryLocationList", method = RequestMethod.GET)
	@ResponseBody
	public DataResult queryAllDeliveryLocationList(HttpServletRequest request, Map<String, Object> filterMask)
			throws Exception {
		DataResult result = DataResult.initResult();

		try {
			// 查询
			List<DeliveryNewLocationDto> list = deliveryLocationHistoryService.queryAllDeliveryLocationList(filterMask);
			int workingCount = 0;
			int freeCount = 0;
			for (DeliveryNewLocationDto bean : list) {
				if ("10".equals(bean.getWorkStatus())) {
					workingCount++;
				} else if ("20".equals(bean.getWorkStatus())) {
					freeCount++;
					bean.setCustomerAddress("暂无");
				}
			}
			Map<String, Object> resultData = new HashMap<String, Object>();
			resultData.put("list", list);
			resultData.put("workingCount", workingCount);
			resultData.put("freeCount", freeCount);

			result.setData(resultData);
		} catch (RuntimeException e) {
			result.setCode(Constants.FAILURE);
			result.setMsg(e.getMessage());
			logger.error("系统异常," + e.getMessage(), e);
		} catch (Exception e) {
			result.setCode(Constants.FAILURE);
			result.setMsg("系统异常,请稍后重试");
			logger.error("系统异常,请稍后重试", e);
		}
		return result;

	}

	/**
	 * 保存
	 * 
	 * @param country
	 * @param result
	 * @return
	 */
	@ApiOperation(value = "位置信息添加接口", notes = "")
	@RequestMapping(value = "/deliveryLocationHistory/save")
	@ResponseBody
	public Result save(HttpServletRequest request, @RequestBody DeliveryLocationHistoryVo filterMask) {
		Result result = Result.initResult();

		try {
			// 淇濆瓨
			deliveryLocationHistoryService.save(filterMask);

		} catch (RuntimeException e) {
			result.setCode(Constants.FAILURE);
			result.setMsg(e.getMessage());
			logger.error("系统异常," + e.getMessage(), e);
		} catch (Exception e) {
			result.setCode(Constants.FAILURE);
			result.setMsg("系统异常,请稍后重试");
			logger.error("系统异常,请稍后重试", e);
		}
		return result;

	}

	/**
	 * 修改
	 * 
	 * @param country
	 * @param result
	 * @return
	 */
	@ApiOperation(value = "位置更新接口", notes = "请求参数说明")
	@RequestMapping(value = "/deliveryLocationHistory/edit")
	@ResponseBody
	public Result edit(HttpServletRequest request, @RequestBody DeliveryLocationHistoryVo filterMask) {
		Result result = Result.initResult();

		try {
			// 位置更新
			deliveryLocationHistoryService.update(filterMask);

		} catch (RuntimeException e) {
			result.setCode(Constants.FAILURE);
			result.setMsg(e.getMessage());
			logger.error("系统异常," + e.getMessage(), e);
		} catch (Exception e) {
			result.setCode(Constants.FAILURE);
			result.setMsg("系统异常,请稍后重试");
			logger.error("系统异常,请稍后重试", e);
		}
		return result;
	}

	/**
	 * 删除
	 * 
	 * @return
	 */
	@ApiOperation(value = "位置信息删除接口", notes = "请求参数说明{\n" + "  \"deliveryUserId\": \"6666666\"\n" + "}\\n返回参数字段说明:\\n")
	@RequestMapping(value = "/deliveryLocationHistory/delete")
	@ResponseBody
	public Result delete(HttpServletRequest request, DeliveryLocationHistoryVo filterMask) {
		Result result = Result.initResult();

		try {
			// 删除
			deliveryLocationHistoryService.delete(filterMask);

		} catch (RuntimeException e) {
			result.setCode(Constants.FAILURE);
			result.setMsg(e.getMessage());
			logger.error("系统异常," + e.getMessage(), e);
		} catch (Exception e) {
			result.setCode(Constants.FAILURE);
			result.setMsg("系统异常,请稍后重试");
			logger.error("系统异常,请稍后重试", e);
		}
		return result;
	}

	/**
	 * 接收微信公众号上报地理位置
	 * 
	 * @return
	 */
	@ApiOperation(value = "接收微信公众号上报地理位置接口", notes = "请求参数说明")
	@RequestMapping(value = "/weixin/accept")
	@ResponseBody
	public String acceptWeiXinMsg(HttpServletRequest request, HttpServletResponse response) {
		String signature = request.getParameter("signature");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");
		// 随机字符串
		String echostr = request.getParameter("echostr");

		try {
			if ("POST".equals(request.getMethod().toUpperCase())) {
				ServletInputStream in = request.getInputStream();
				String xmlStr = XmlUtilCommon.getXmlString(in);

				// 推送微信数据到微擎
				try {
					String url = "https://we7.yourtk.com/api.php?id=8&signature=" + signature + "&timestamp="
							+ timestamp + "&nonce=" + nonce + "&echostr=" + echostr;
					HttpsUtil.httpsRequest(url, "POST", xmlStr);
				} catch (Exception e) {
					logger.info("推送微信数据到微擎==>" + xmlStr, e);
				}

				logger.info("接收微信参数==>" + xmlStr);
				Map<String, String> xmlMap = XmlUtilCommon.toMap(xmlStr.getBytes(), "utf-8");
				logger.info("获取微信推送参数==>" + JSON.toJSONString(xmlMap));
				if ("location".equalsIgnoreCase(xmlMap.get("msgtype"))
						|| ("event".equalsIgnoreCase(xmlMap.get("msgtype"))
								&& "location".equalsIgnoreCase(xmlMap.get("event")))) {
					logger.info("接收微信公众号地理位置消息==>" + xmlStr);
					String fromWXOpenId = xmlMap.get("fromusername");
					// 查询派送员信息
					DeliveryUserVo deliveryUserVo = new DeliveryUserVo();
					deliveryUserVo.setWxOpenId(fromWXOpenId);
					DeliveryUserVo deliveryUser = deliveryUserService.getDeliveryUserBySelective(deliveryUserVo);
					if (deliveryUser == null) {
						return "success";
					}
					Date now = new Date();
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(now);
					int second = calendar.get(Calendar.SECOND);
					// 一分钟内处理一次
					if (second > 10) {
						return "success";
					}
					// 地理位置经度
					String longitude = xmlMap.get("longitude");
					// 地理位置纬度
					String latitude = xmlMap.get("latitude");
					// 腾讯坐标转换百度坐标
					double[] map_bd = MapHelper.map_tx2bd(Double.valueOf(latitude), Double.valueOf(longitude));
					longitude = String.valueOf(map_bd[1]);
					latitude = String.valueOf(map_bd[0]);

					DeliveryLocationHistoryVo filterMask = new DeliveryLocationHistoryVo();
					filterMask.setDeliveryUserId(deliveryUser.getId());
					filterMask.setLongitude(longitude);
					filterMask.setLatitude(latitude);
					filterMask.setLocation(latitude + "," + longitude);
					deliveryLocationHistoryService.save(filterMask);
				} else if ("subscribe".equalsIgnoreCase(xmlMap.get("event"))) {
					String toUserName = xmlMap.get("tousername");
					String fromWXOpenId = xmlMap.get("fromusername");
					String content = "感谢您关注我们,我们将为您提供最贴心的服务!";

					Map<String, String> param = new HashMap<String, String>();
					param.put("FromUserName", toUserName);
					param.put("ToUserName", fromWXOpenId);
					param.put("MsgType", "text");
					param.put("CreateTime", String.valueOf(new Date().getTime()));
					param.put("Content", content);
					String responseStr = XmlUtilCommon.toXml(param);

					logger.info("微信关注返回消息:" + responseStr);

					return responseStr;
				}
			} else if ("GET".equals(request.getMethod().toUpperCase())) {
				// 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp，nonce参数
				boolean checkSign = WeiXinSignUtil.checkSignature(signature, timestamp, nonce);
				if (checkSign) {
					return echostr;
				}
				return "failure";
			}
		} catch (RuntimeException e) {
			logger.error("系统异常," + e.getMessage(), e);
		} catch (Exception e) {
			logger.error("系统异常,请稍后重试", e);
		}
		return "success";
	}

}
