package com.lyr.qs.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyr.qs.constant.Constant;
import com.lyr.qs.entity.Option;
import com.lyr.qs.exception.CustomException;
import com.lyr.qs.mapper.OptionMapper;
import com.lyr.qs.service.OptionService;
import com.lyr.qs.util.EmptyUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 选项表
 *
 * @author yurong333
 * @since 2022-12-30
 */
@Service
public class OptionServiceImpl extends ServiceImpl<OptionMapper, Option> implements OptionService {


    @Override
    public void deleteOptionsByQuestionId(Integer questionId) {
        // 根据问题id获取选项列表
        List<Option> optionList = this.getOptionsByQuestionId(questionId);
        // 选项ids
        List<Integer> optionIds = optionList.stream().map(Option::getId).collect(Collectors.toList());
        // 批量删除
        this.removeByIds(optionIds);
    }

    @Override
    public void deleteOptionsByQuestionIds(List<Integer> questionIds) {
        for(Integer questionId: questionIds){
            this.deleteOptionsByQuestionId(questionId);
        }
    }


    @Override
    public List<Option> getOptionsByQuestionId(Integer questionId) {
        LambdaQueryWrapper<Option> optionWrapper = new LambdaQueryWrapper<>();
        optionWrapper.eq(Option::getQuestionId, questionId);
        List<Option> optionList = this.list(optionWrapper);
        return optionList;
    }


}
