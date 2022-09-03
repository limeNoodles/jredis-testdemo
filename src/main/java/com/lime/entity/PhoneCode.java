package com.lime.entity;

import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Random;

public class PhoneCode {
    private Jedis jedis;
    public PhoneCode(){
        jedis = new Jedis("192.168.80.129",6379);
        jedis.auth("lime");
    }


    public static String getCode(){
        Random random = new Random();
        String code = "";
        for (int i=0;i<6;i++){
            int rand = random.nextInt(10);
            code += rand;
        }
        return code;
    }

    @Test
    public void test(){
        int time = getRemainSecondsOneDay();
        System.out.println(time);
    }

    public boolean verifyCode(String phoneNum,String code){
        String countKey = "VerifyCode"+phoneNum+":count";
        String codeKey = "VerifyCode"+code+":code";
        //设置每个手机每天发送三次
        String count = jedis.get(countKey);
        if(count == null){
           int time = getRemainSecondsOneDay();
           jedis.setex(countKey,time,"1");
            //验证码发送到redis中
            jedis.setex(codeKey,120,code);
            return true;
        }else if(Integer.parseInt(count)<=2){
            jedis.incr(countKey);
            //验证码发送到redis中
            jedis.setex(codeKey,120,code);
            return true;
        }else {
            //超过三次不再发送
            return false;
        }
    }

    public boolean makeSureCode(String code){
        String codeKey = "VerifyCode"+code+":code";
        String redisCode = jedis.get(codeKey);
        if(redisCode.equals(code)){
           return true;
        }else {
            return false;
        }
    }

    public static Integer getRemainSecondsOneDay() {
        Date currentDate = new Date();
        LocalDateTime midnight = LocalDateTime.ofInstant(currentDate.toInstant(),
        ZoneId.systemDefault()).plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime currentDateTime = LocalDateTime.ofInstant(currentDate.toInstant(),
        ZoneId.systemDefault());
        long seconds = ChronoUnit.SECONDS.between(currentDateTime, midnight);
        return (int) seconds;}
}
