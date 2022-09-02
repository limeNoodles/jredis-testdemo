package com.lime.controller;

import com.alibaba.fastjson.JSON;
import com.lime.entity.Message;
import com.lime.entity.PhoneCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JredisController {
    private PhoneCode phoneCode = new PhoneCode();
    @GetMapping("phoneCode")
    public Message getPhoneCode(@RequestParam(name = "phoneNum",required = false)String phoneNum){
        String code = phoneCode.getCode();
        return Message.success().add("phoneCode",code);
    }
}
