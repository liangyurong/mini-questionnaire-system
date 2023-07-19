package com.lyr.fdfs.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyr.fdfs.entity.FdfsUploadEntity;
import com.lyr.fdfs.mapper.FdfsUploadMapper;
import com.lyr.fdfs.service.FdfsUploadService;
import com.lyr.fdfs.util.FdfsUtil;
import com.lyr.qscommon.util.IoUtil;
import com.lyr.qscommon.constant.Constant;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;


@Service
public class FdfsUploadServiceImpl extends ServiceImpl<FdfsUploadMapper, FdfsUploadEntity> implements FdfsUploadService {

    @Autowired
    private FdfsUtil fdfsUtil;

    @Override
    public String uploadFile(MultipartFile file) throws IOException {
        if(file == null){
            return "no such file";
        }
        // 上传到fastdfs
        String path = fdfsUtil.upload(file);
        // 保存到数据库
        String originalFilename = file.getOriginalFilename();
        FdfsUploadEntity entity = FdfsUploadEntity.builder()
                .uploadTime(new Date())
                .path(path)
                .fileName(originalFilename)
                .build();
       this.save(entity);
       return path;
    }

    @Override
    public void deleteByPath(String path) {
        // 删除fastdfs的记录
        fdfsUtil.delete(path);
        // 删除数据库的记录
        LambdaQueryWrapper<FdfsUploadEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FdfsUploadEntity::getPath,path);
        FdfsUploadEntity en = this.getOne(wrapper);
        if(en!=null ){
            this.removeById(en);
        }
    }

    @Override
    public void download(String path, HttpServletResponse response) throws MalformedURLException {
        // 获取下载文件的字节流
        byte[] bytes = fdfsUtil.getFileBytesByPath(path);
        // 自定义文件名+后缀
        URL url = new URL(path);
        String extension = FilenameUtils.getExtension(url.getPath());
        String fileName = RandomUtil.randomString(15)+ Constant.COMMA +extension;
        // 下载文件
        IoUtil.downLoad(bytes,fileName,response);
    }
}
