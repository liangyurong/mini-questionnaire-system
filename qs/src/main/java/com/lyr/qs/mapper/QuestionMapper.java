package com.lyr.qs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyr.qs.entity.Question;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 问题表 Mapper 接口
 * </p>
 *
 * @author yurong333
 * @since 2022-12-30
 */
@Mapper
public interface QuestionMapper extends BaseMapper<Question> {

}
