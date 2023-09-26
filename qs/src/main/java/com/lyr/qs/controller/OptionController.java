package com.lyr.qs.controller;

import com.lyr.qs.result.R;
import com.lyr.qs.service.OptionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 选项表
 *
 * @author yurong333
 * @since 2022-12-30
 */
@RestController
@RequestMapping("/option")
public class OptionController {

    @Autowired
    private OptionService optionService;

    @ApiOperation("根据选项id删除选项")
    @GetMapping("/delete")
    public R delete(@RequestParam("id")Integer id) {
        optionService.removeById(id);
        return new R(HttpStatus.OK.value(), "删除选项成功");
    }
}

