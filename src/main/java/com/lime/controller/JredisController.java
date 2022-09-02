package com.lime.controller;

import com.alibaba.fastjson.JSON;
import com.lime.entity.Message;
import com.lime.entity.PhoneCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JredisController {
    private PhoneCode phoneCode = new PhoneCode();
    @GetMapping("phoneCode")
    public Message getPhoneCode(@RequestParam(name = "phoneNum",required = false)String phoneNum){
        String code = phoneCode.getCode();
        if(phoneCode.verifyCode(phoneNum,code)){
            return Message.success().add("phoneCode",code);
        }else {
            return Message.fail().add("errorMsg","今日已超三次！");
        }
    }

    @PostMapping("Code")
    public Message makeSureCode(@RequestParam("code")String code){
        if(phoneCode.makeSureCode(code)){
            return Message.success().add("message","验证成功");
        }else {
            return Message.fail().add("message","验证失败");
        }
    }
}
