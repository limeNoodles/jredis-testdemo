package com.lime.service;

public interface RedisService {
    public boolean verifyCode(String phoneNum,String code);
    public boolean makeSureCode(String phoneNum,String code);
}
