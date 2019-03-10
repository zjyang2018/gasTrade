package com.zach.weixin.util;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


public class TestWX {
    public static void main(String[] args) {
        //新增用户成功 - 推送微信消息
        senMsg("oYpZz1LHPO5qggb5tfiWdDDh7lCQ");
    }
   static void senMsg(String openId){
        //用户是否订阅该公众号标识 (0代表此用户没有关注该公众号 1表示关注了该公众号)
        Integer  state= WX_UserUtil.subscribeState(openId);
        System.out.println("state:"+state);
        // 绑定了微信并且关注了服务号的用户 , 注册成功-推送注册短信
        if(state==1){
            Map<String,TemplateData> param = new HashMap<>();
            param.put("first",new TemplateData("恭喜其小林！","#696969"));
            param.put("keyword1",new TemplateData("12345345456","#696969"));
            param.put("keyword2",new TemplateData("2017年","#696969"));
            param.put("remark",new TemplateData("祝愉快！","#696969"));
            //注册的微信-模板Id
           // String regTempId =  WX_TemplateMsgUtil.getWXTemplateMsgId("ywBb70467vr18");

            JSON.toJSONString(param);
            JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(param));
            //调用发送微信消息给用户的接口
          WX_TemplateMsgUtil.sendWechatMsgToUser(openId,"veNLgN8mi-L3Gn67PfkO6l-yS42CV7m8T0c4Pf-PjFw", "", 
                  "#000000", jsonObject);


          //获取公众号的自动回复规则
          String urlinfo="https://api.weixin.qq.com/cgi-bin/get_current_autoreply_info?access_token="+WX_TokenUtil.getWXToken().getAccessToken();
          JSONObject joinfo = WX_HttpsUtil.httpsRequest(urlinfo, "GET", null);
          Object o=joinfo.get("is_add_friend_reply_open");
         // System.out.println("o:"+joinfo);
          String getTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={0}&secret={1}";
            JSONObject Token = WX_HttpsUtil.httpsRequest(getTokenUrl, "GET", null);
            System.out.println("Token:"+Token);


        }
    }

}
