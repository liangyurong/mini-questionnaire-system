package com.lyr.analysis.controller;

import cn.hutool.json.JSONObject;
import com.lyr.qscommon.dto.UserDTO;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import com.lyr.qscommon.api.qs.QsInterface;

@RestController
@RequestMapping("/analysis")
public class AnalysisController {

    @Resource
    private QsInterface qsInterface;

    // 网关请求：http://localhost:8808/api/analysis/list
    @GetMapping("/list")
    public String list(){
        return qsInterface.list();
    }

    // 网关请求：http://localhost:8808/api/analysis/get?id=12
    @GetMapping("/get")
    public String get(@RequestParam("id") Integer id){
        return qsInterface.get(id);
    }

    @PostMapping("/get/user")
    public String getUser(@RequestBody UserDTO dto){
        return qsInterface.getUser(dto);
    }

}
