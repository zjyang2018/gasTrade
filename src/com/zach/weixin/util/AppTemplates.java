package com.zach.weixin.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.common.utils.CalendarUtils;


public class AppTemplates {
 
	// 绑定了微信并且关注了服务号的用户 , 注册成功-推送注册短信
   public static JSONObject followTemplateMsg(String name){
            Map<String,TemplateData> param = new HashMap<>();
            String first = "感谢您关注" + name + "微信公众号！我们将为您提供最贴心的燃气服务~";
            param.put("first",new TemplateData(first,"#696969"));
           
            JSON.toJSONString(param);
            JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(param));
            
            return jsonObject;
    }
   
   //支付成功通知
   public static JSONObject payOkTemplateMsg(Date date ){
	   		String time = CalendarUtils.formatDateTime(date);
            Map<String,TemplateData> param = new HashMap<>();
            String first = "尊敬的用户，您已于" + time + "支付完成，我们将尽快为您派送商品，谢谢！";
            param.put("first",new TemplateData(first,"#696969"));
           
            JSON.toJSONString(param);
            JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(param));
            
            return jsonObject;
    }
   
 //订单派送通知
   public static JSONObject orderDeliveryTemplateMsg(String deliveryUser,String phoneNumber ){
            Map<String,TemplateData> param = new HashMap<>();
            String first = "尊敬的用户，您的商品正在派送中，请保持电话通畅！";
            param.put("first",new TemplateData(first,"#696969"));
            param.put("deliveryUser",new TemplateData(deliveryUser,"#696969"));
            param.put("phoneNumber",new TemplateData(phoneNumber,"#696969"));
           
            JSON.toJSONString(param);
            JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(param));
            
            return jsonObject;
    }
   
 //订单签收通知
   public static JSONObject orderSignTemplateMsg(String receiver,Date date ){
	   		String time = CalendarUtils.formatDateTime(date);
            Map<String,TemplateData> param = new HashMap<>();
            String first = "尊敬的用户，您的商品已被签收，祝您生活愉快！";
            param.put("first",new TemplateData(first,"#696969"));
            param.put("receiver",new TemplateData(receiver,"#696969"));
            param.put("time",new TemplateData(time,"#696969"));
           
            JSON.toJSONString(param);
            JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(param));
            
            return jsonObject;
    }
   
 //派单通知
   public static JSONObject dispatchTemplateMsg(String customerName,String custmoerAddress ){
            Map<String,TemplateData> param = new HashMap<>();
            String first = "你好，系统已自动为您接单，请您尽快接单，谢谢！";
            param.put("first",new TemplateData(first,"#696969"));
            param.put("customerName",new TemplateData(customerName,"#696969"));
            param.put("custmoerAddress",new TemplateData(custmoerAddress,"#696969"));
           
            JSON.toJSONString(param);
            JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(param));
            
            return jsonObject;
    }
   
 //接单通知
   public static JSONObject orderTakingTemplateMsg(String customerName,String custmoerAddress ){
            Map<String,TemplateData> param = new HashMap<>();
            String first = "你好，您已接单，请您尽快安排时间为客户送货，谢谢！";
            param.put("first",new TemplateData(first,"#696969"));
            param.put("customerName",new TemplateData(customerName,"#696969"));
            param.put("custmoerAddress",new TemplateData(custmoerAddress,"#696969"));
           
            JSON.toJSONString(param);
            JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(param));
            
            return jsonObject;
    }
   
 //派单完成通知
   public static JSONObject dispatchComplateTemplateMsg(String customerName,String custmoerAddress ){
            Map<String,TemplateData> param = new HashMap<>();
            String first = "你好，客户已签收商品，派送任务已完成，谢谢！";
            param.put("first",new TemplateData(first,"#696969"));
            param.put("customerName",new TemplateData(customerName,"#696969"));
            param.put("custmoerAddress",new TemplateData(custmoerAddress,"#696969"));
           
            JSON.toJSONString(param);
            JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(param));
            
            return jsonObject;
    }

}
