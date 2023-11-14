package com.lyr.qs.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyr.qs.constant.Constant;
import com.lyr.qs.entity.Option;
import com.lyr.qs.exception.CustomException;
import com.lyr.qs.form.OptionUpdateForm;
import com.lyr.qs.mapper.OptionMapper;
import com.lyr.qs.service.OptionService;
import com.lyr.qs.util.EmptyUtils;
import com.lyr.qs.vo.OptionVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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


    @Resource
    private OptionMapper optionMapper;

    @Override
    public void deleteOptionsByQuestionId(Integer questionId) {
//        // 根据问题id获取选项列表
//        List<Option> optionList = this.getOptionsByQuestionId(questionId);
//        // 选项ids
//        List<Integer> optionIds = optionList.stream().map(Option::getId).collect(Collectors.toList());
//        // 批量删除
//        this.removeByIds(optionIds);
    }

    @Override
    public void deleteOptionsByQuestionIds(List<Integer> questionIds) {
        for(Integer questionId: questionIds){
            this.deleteOptionsByQuestionId(questionId);
        }
    }

    // TODO lyr 逻辑需要修改，需要找出已有的选项，和新选项做对比，再做新增，删除或更新
    @Override
    public void updateOptions(Integer questionId, List<OptionUpdateForm> options) {
        // 删除现有的选项
        QueryWrapper<Option> optionQueryWrapper = new QueryWrapper<>();
        optionQueryWrapper.eq("question_id", questionId);
        optionMapper.delete(optionQueryWrapper);
        // 插入新的选项
        for (OptionUpdateForm optionForm : options) {
            Option option = new Option();
            option.setQuestionId(questionId);
            option.setContent(optionForm.getContent());
            option.setOrderNumber(optionForm.getOrderNumber());
            optionMapper.insert(option);
        }
    }


    @Override
    public List<OptionVO> getOptionsByQuestionId(Integer questionId) {
        List<Option> list = this.lambdaQuery().eq(Option::getQuestionId, questionId).list();
        return list.stream().map(option -> BeanUtil.copyProperties(option,OptionVO.class)).collect(Collectors.toList());
    }


}
