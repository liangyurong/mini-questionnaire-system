package com.lyr.qscommon.api.qs;

import com.lyr.qscommon.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 问卷系统统一调用接口
 */
@FeignClient(name = "qs-server",path = "/qs")
public interface QsInterface {

    @GetMapping("/list")
    String list();

    // 必须用@RequestParam注解，否则提供者接收不到
    @GetMapping("/get")
    String get(@RequestParam("id") Integer id);

    // 对象
    @PostMapping("/get/user")
    String getUser(@RequestBody UserDTO dto);

    // json


}
