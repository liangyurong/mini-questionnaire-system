package com.lyr.qs.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyr.qs.constant.Constant;
import com.lyr.qs.entity.Option;
import com.lyr.qs.form.OptionUpdateForm;
import com.lyr.qs.mapper.OptionMapper;
import com.lyr.qs.service.OptionService;
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


    @Override
    public void deleteOptionsByQuestionId(Integer questionId) {
        // 根据问题id获取选项列表
        List<OptionVO> options = this.getOptionsByQuestionId(questionId);
        // 选项ids
        List<Integer> optionIds = options.stream().map(OptionVO::getId).collect(Collectors.toList());
        // 批量删除
        this.removeByIds(optionIds);
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
        // 已存在的选项id
        List<Integer> oldOptionIds = this.getOptionsByQuestionId(questionId).stream().map(OptionVO::getId).collect(Collectors.toList());

        // 需要新增的选项(没有主键id的就是新增选项)
        List<OptionUpdateForm> needAddOptions = options.stream().filter(option -> ObjectUtil.isNull(option.getId())).collect(Collectors.toList());
        // 新增选项
        for (OptionUpdateForm form : needAddOptions) {
            this.createOption(questionId, form);
        }

        // 需要更新的选项
        List<OptionUpdateForm> needUpdateOptions = options.stream()
                .filter(option -> oldOptionIds.contains(option.getId()))
                .collect(Collectors.toList());
        // 更新选项
        for (OptionUpdateForm form : needAddOptions) {
            this.updateOption(form);
        }

        // 需要更新的选项id
        List<Integer> needUpdateOptionIds = needUpdateOptions.stream().map(OptionUpdateForm::getId).collect(Collectors.toList());
        // 需要删除的选项
        List<Integer> needDeleteOptionIds = oldOptionIds.stream()
                .filter(id -> !needUpdateOptionIds.contains(id))
                .collect(Collectors.toList());
        // 删除选项
        this.removeByIds(needDeleteOptionIds);
    }

    private void updateOption(OptionUpdateForm form) {
        Option option = this.getById(form.getId());
        if (option != null) {
            option.setContent(form.getContent());
            option.setOrderNumber(form.getOrderNumber());
            this.updateById(option);
        }
    }

    private void createOption(Integer questionId, OptionUpdateForm form) {
        Option option = new Option();
        option.setQuestionId(questionId);
        option.setContent(form.getContent());
        option.setOrderNumber(form.getOrderNumber());
        this.save(option);
    }

    @Override
    public List<OptionVO> getOptionsByQuestionId(Integer questionId) {
        List<Option> list = this.lambdaQuery().eq(Option::getQuestionId, questionId).list();
        return list.stream().map(option -> BeanUtil.copyProperties(option,OptionVO.class)).collect(Collectors.toList());
    }


}
