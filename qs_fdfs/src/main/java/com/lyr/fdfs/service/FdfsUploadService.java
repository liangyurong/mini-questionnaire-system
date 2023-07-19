package com.lyr.fdfs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lyr.fdfs.entity.FdfsUploadEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;


public interface FdfsUploadService extends IService<FdfsUploadEntity> {

    String uploadFile(MultipartFile file) throws IOException;

    void deleteByPath(String path);

    void download(String path, HttpServletResponse response) throws MalformedURLException;
}
