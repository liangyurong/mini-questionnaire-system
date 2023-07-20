package com.lyr.qs.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyr.qs.dto.SurveyDto;
import com.lyr.qs.entity.Survey;
import com.lyr.qs.exception.CustomException;
import com.lyr.qs.result.ResponseResult;
import com.lyr.qs.service.SurveyService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/survey")
@CrossOrigin("*")
public class SurveyController {

    @Autowired
    private SurveyService surveyService;

    @ApiOperation("获取问卷列表，分页展示")
    @GetMapping("/list")
    public ResponseResult list(@RequestBody(required = false) SurveyDto dto) {
        Page<Survey> surveyPage = surveyService.page(dto);
        return new ResponseResult(HttpStatus.OK.value(), "获取问卷列表成功", surveyPage);
    }

    @ApiOperation("根据问卷id获取一份问卷数据")
    @GetMapping("/getSurveyById")
    public ResponseResult getSurveyById(@RequestParam("id") Integer id) {
        Survey survey = surveyService.getSurveyDataById(id);
        return new ResponseResult(HttpStatus.OK.value(), "获取问卷列表成功", survey);
    }

    @ApiOperation("创建一张问卷")
    @PostMapping("/createQuestionnaire")
    public ResponseResult createQuestionnaire(@RequestBody JSONObject json) throws Exception {
        surveyService.createQuestionnaire(json);
        return new ResponseResult(HttpStatus.OK.value(), "创建问卷成功");
    }

    @ApiOperation("更新问卷")
    @PostMapping("/updateQuestionnaire")
    public ResponseResult updateQuestionnaire(@RequestBody JSONObject json) throws Exception {
        surveyService.updateQuestionnaire(json);
        return new ResponseResult(HttpStatus.OK.value(), "更新问卷成功");
    }

    @ApiOperation("根据问卷id删除问卷")
    @GetMapping("/deleteById")
    public ResponseResult deleteSurveyBySurveyId(@RequestParam("id") Integer id) {
        surveyService.deleteById(id);
        return new ResponseResult(HttpStatus.OK.value(), "删除问卷成功");
    }


    @ApiOperation("改变问卷的状态：设计中->发布，发布->结束，结束->发布")
    @GetMapping("/updateSurveyState")
    public ResponseResult updateSurveyState(@RequestParam("id") Integer id, @RequestParam("surveyState") Integer surveyState) {
        surveyService.updateSurveyState(id, surveyState);
        return new ResponseResult(HttpStatus.OK.value(), "更新问卷状态成功");
    }

    @ApiOperation("填写问卷，并将填写的答卷数据存储到数据库")
    @PostMapping("/fillQuestionnaire")
    public ResponseResult fillQuestionnaire(@RequestBody JSONObject json) throws CustomException {
        surveyService.fillQuestionnaire(json);
        return new ResponseResult(HttpStatus.OK.value(), "填写问卷成功");
    }

//    @ApiOperation("根据问卷id和手机号，获取问卷数据")
//    @GetMapping("/getSurveyData/{surveyId}/{phone}")
//    public ResponseResult getSurveyData(@PathVariable("surveyId") String surveyId,@PathVariable("phone") String phone ) {
//        return surveyService.getSurveyData(surveyId,phone);
//    }
//
//    @ApiOperation("根据问卷id和手机号，获取某份问卷答案。之后可以将答案组装到问卷中")
//    @GetMapping("/getSurveyAnswer/{surveyId}/{phone}")
//    public ResponseResult getSurveyAnswer(@PathVariable("surveyId") String surveyId,@PathVariable("phone") String phone ) {
//        return surveyService.getSurveyAnswer(surveyId,phone);
//    }
//

//
//    @ApiOperation("根据问卷id和日期获取问卷数据和全部答案的数据")
//    @GetMapping("/getAnswerStatisticsBySurveyIdAndDate")
//    public ResponseResult getAnswerStatisticsBySurveyIdAndDate(@RequestParam("surveyId") String surveyId,@RequestParam("startDate") String startDate,@RequestParam("endDate") String endDate) {
//        return surveyService.getAnswerStatisticsBySurveyIdAndDate(surveyId,startDate,endDate);
//    }
//
//    // 之所以写两个路径，是因为@PathVariable不允许空值
//    @ApiOperation("根据页码和条数和问卷状态码获取问卷。最新创建的问卷是第一条.补充：如果surveyState是-1，说明是查找任何状态码的答卷")
//    @RequestMapping(method = RequestMethod.GET,value = {"/getSurveyByPageAndSizeAndSurveyStateAndKeyword/{page}/{size}/{surveyState}","/getSurveyByPageAndSizeAndSurveyStateAndKeyword/{page}/{size}/{surveyState}/{keyword}"})
//    public ResponseResult getSurveyByPageAndSizeAndSurveyStateAndKeyword(@PathVariable("page") Integer page,
//                                                                 @PathVariable("size") Integer size,
//                                                                 @PathVariable("surveyState") String surveyState,
//                                                                 @PathVariable(value = "keyword",required = false) String keyword) {
//        return surveyService.getSurveyByPageAndSizeAndSurveyStateAndKeyword(page,size,surveyState,keyword);
//    }
//
//    @ApiOperation("根据页码和条数和问卷id获取所有的答卷")
//    @GetMapping("/getAnswerSurveyByPageAndSize/{page}/{size}/{id}")
//    public ResponseResult getAnswerSurveyByPageAndSize(@PathVariable(value = "page") Integer page, @PathVariable("size") Integer size, @PathVariable("id") String id) {
//        return surveyService.getAnswerSurveyByPageAndSize(page,size,id);
//    }
//
//    @ApiOperation("根据问卷id和手机号删除答卷")
//    @GetMapping("/deleteAnswerSurveyBySurveyIdAndPhone/{id}/{phone}")
//    public ResponseResult deleteAnswerSurveyBySurveyIdAndPhone(@PathVariable(value = "id") String id,@PathVariable(value = "phone") String phone) {
//        return surveyService.deleteAnswerSurveyBySurveyIdAndPhone(id,phone);
//    }
}

