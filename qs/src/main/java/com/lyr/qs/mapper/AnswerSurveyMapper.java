package com.lyr.qs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyr.qs.entity.AnSurvey;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 答卷表的基本信息 Mapper 接口
 * </p>
 *
 * @author yurong333
 * @since 2022-12-30
 */
@Mapper
public interface AnswerSurveyMapper extends BaseMapper<AnSurvey> {

}
