package com.lime.controller;

import com.lime.entity.Message;
import com.lime.entity.PhoneCode;
import com.lime.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/redis")
public class RedisController {
    @Autowired
    private RedisService redisService;
    @GetMapping("phoneCode")
    public Message getPhoneCode(@RequestParam("phoneNum")String phoneNum){
        String code = PhoneCode.getCode();

        if(redisService.verifyCode(phoneNum,code)){
            return Message.success().add("phoneCode",code);
        }else {
            return Message.fail().add("errorMsg","今日已超三次！");
        }
    }

    @PostMapping("Code")
    public Message makeSureCode(@RequestParam("phoneNum")String phoneNum,@RequestParam("code")String code){
        if(redisService.makeSureCode(phoneNum,code)){
            return Message.success().add("message","验证成功");
        }else {
            return Message.fail().add("message","验证失败");
        }
    }



}
