package com.lime.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JredisController {

    @GetMapping("index")
    public String index(){
        return JSON.toJSONString("NIHAO");
    }
}
