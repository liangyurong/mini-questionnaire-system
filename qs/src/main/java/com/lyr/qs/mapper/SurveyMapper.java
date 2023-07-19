package com.lyr.qs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyr.qs.entity.Survey;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 问卷表 Mapper 接口
 * </p>
 *
 * @author yurong333
 * @since 2022-12-30
 */
@Mapper
@Component
public interface SurveyMapper extends BaseMapper<Survey> {

}
