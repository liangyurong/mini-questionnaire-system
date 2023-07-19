package com.lyr.fdfs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyr.fdfs.entity.FdfsUploadEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface FdfsUploadMapper extends BaseMapper<FdfsUploadEntity> {

}
