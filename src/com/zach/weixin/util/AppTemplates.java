package com.zach.weixin.util;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


public class AppTemplates {
 
	// 绑定了微信并且关注了服务号的用户 , 注册成功-推送注册短信
   static JSONObject followTemplateMsg(String name){
            Map<String,TemplateData> param = new HashMap<>();
            String first = "感谢您关注" + name + "微信公众号！我们将为您提供最贴心的燃气服务~";
            param.put("first",new TemplateData(first,"#696969"));
           
            JSON.toJSONString(param);
            JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(param));
            
            return jsonObject;
    }

}
