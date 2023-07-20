package com.lyr.qs.controller;

import com.alibaba.fastjson.JSONObject;
import com.lyr.qscommon.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

/**
 * 简单示例
 */
@RestController
@RequestMapping("/qs")
@Slf4j
public class TestQsController {

    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    @GetMapping("/list")
    public String list(){
        log.info("获取到了所有的问卷");
        return "获取到了所有的问卷";
    }

    @GetMapping("/get")
    public String get(@RequestParam("id") Integer id){
        return "根据问卷id="+id+"获取到了问卷";
    }

    @PostMapping("/get/user")
    public String getUser(@RequestBody UserDTO userDTO){
        return userDTO.getName() + " : " + userDTO.getAge();
    }

    // 测试kafka
    // 填写问卷,kafka发送到qs_analysis模块进行统计
    @PostMapping("/fill")
    public void getUser(@RequestBody JSONObject jsonObject){
        kafkaTemplate.send("qs-analysis-topic",jsonObject.toJSONString());
    }


}
