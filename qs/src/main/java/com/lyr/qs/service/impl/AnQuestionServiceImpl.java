package com.lyr.qs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyr.qs.entity.AnQuestion;
import com.lyr.qs.mapper.AnRadioMapper;
import com.lyr.qs.service.AnQuestionService;
import org.springframework.stereotype.Service;

/**
 * 单选题答题表
 *
 * @author yurong333
 * @since 2022-12-30
 */
@Service
public class AnQuestionServiceImpl extends ServiceImpl<AnRadioMapper, AnQuestion> implements AnQuestionService {

}
