package com.lyr.qs.controller;

import com.lyr.qs.result.ResponseResult;
import com.lyr.qs.service.QuestionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 问题表
 * @author yurong333
 * @since 2022-12-30
 */
@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @ApiOperation("根据问题id删除问题")
    @GetMapping("/delete")
    public ResponseResult delete(@RequestParam("id")Integer id) {
        questionService.removeQuestionById(id);
        return new ResponseResult(HttpStatus.OK.value(), "删除问题成功");
    }

}

