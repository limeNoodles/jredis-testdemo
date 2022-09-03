package com.lime.service.impl;

import com.lime.entity.PhoneCode;
import com.lime.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements RedisService {
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean verifyCode(String phoneNum, String code) {
        String countKey = "VerifyCode"+phoneNum+":count";
        String codeKey = "VerifyCode"+phoneNum+":code";
        //设置每个手机每天发送三次
        Integer count = (Integer) redisTemplate.opsForValue().get(countKey);
        if(count == null){
            int time = PhoneCode.getRemainSecondsOneDay();
            redisTemplate.opsForValue().set(countKey,1,time,TimeUnit.SECONDS);
            //验证码发送到redis中
            redisTemplate.opsForValue().set(codeKey,code,120,TimeUnit.SECONDS);
            return true;
        }else if(count<=2){
            redisTemplate.opsForValue().increment(countKey,1);
            //验证码发送到redis中
            redisTemplate.opsForValue().set(codeKey,code,120,TimeUnit.SECONDS);
            return true;
        }else {
            //超过三次不再发送
            return false;
        }
    }

    @Override
    public boolean makeSureCode(String phoneNum,String code){
        String codeKey = "VerifyCode"+phoneNum+":code";
        String redisCode = (String) redisTemplate.opsForValue().get(codeKey);
        if(redisCode.equals(code)){
            return true;
        }else {
            return false;
        }
    }
}
