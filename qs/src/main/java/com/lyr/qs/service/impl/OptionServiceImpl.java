package com.lyr.qs.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
    public void createOptions(JSONObject question, Integer questionId) {
        JSONArray options = question.getJSONArray(Constant.OPTIONS);
        int size = options.size();
        for (int index = 0; index < size; index++) {
            JSONObject option = options.getJSONObject(index);
            createOption(option, index, questionId);
        }
    }

    @Override
    public void createOption(JSONObject option, int index, Integer questionId) {
        Option entity = Option.builder()
                .BelongQuestionId(questionId)
                .orderId(index)
                .myOption(option.getString(Constant.MY_OPTION))
                .build();
        this.save(entity);
    }

    @Override
    public void updateOption(JSONObject option, Integer index, Integer questionId) throws CustomException {
        Integer optionId = Integer.parseInt(option.getString(Constant.OPTION_ID));
        String myOption = option.getString(Constant.MY_OPTION);
        Option entity = this.getById(optionId);
        if(EmptyUtils.isEmpty(entity)){
            throw new CustomException(Constant.FAIL,"根据选项id找不到选项实体!!! 当前的选项是"+myOption);
        }
        entity.setMyOption(myOption);
        entity.setOrderId(index);
        this.saveOrUpdate(entity);
    }

    /**
     * 该方法做了两件事：更新 or 新增，因此可以独立出两个方法
     */
    @Override
    public void updateOrCreateOption(JSONObject option, Integer index, Integer questionId) throws CustomException {
        String optionIdStr = option.getString(Constant.OPTION_ID);
        if(EmptyUtils.isEmpty(optionIdStr)){
            createOption(option,index,questionId);
        }else{
            updateOption(option,index,questionId);
        }
    }

    @Override
    public void removeOptionsByQuestionId(Integer questionId) {
        List<Option> list = this.lambdaQuery().eq(Option::getBelongQuestionId, questionId).list();
        List<Integer> optionIds = list.stream().map(Option::getId).collect(Collectors.toList());
        this.removeByIds(optionIds);
    }

    @Override
    public List<Option> getOptionsByQuestionId(Integer questionId) {
        return  this.lambdaQuery()
                .eq(Option::getBelongQuestionId, questionId)
                .orderByAsc(Option::getOrderId)
                .list();
    }
}
