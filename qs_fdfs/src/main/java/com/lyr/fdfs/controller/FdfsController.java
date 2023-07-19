package com.lyr.fdfs.controller;

import com.lyr.fdfs.entity.FdfsUploadEntity;
import com.lyr.fdfs.service.FdfsUploadService;
import com.lyr.fdfs.util.FdfsUtil;
import com.lyr.qscommon.util.IoUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

@Api("文件上传与下载")
@RestController
@RequestMapping("/fdfs")
public class FdfsController {

    // 8899文件访问端口

    @Autowired
    private FdfsUploadService fdfsUploadService;

    // 获取文件列表
    @GetMapping("/list")
    @ApiOperation(value = "文件列表",httpMethod = "GET")
    public List<FdfsUploadEntity> list(){
        return fdfsUploadService.list();
    }

    // 操作：postman选择form-data，选择file，然后就可以选择文件进行上传
    @PostMapping("/upload")
    @ApiOperation(value = "上传文件",httpMethod = "POST")
    public String uploadFile(@RequestParam("file")MultipartFile file) throws IOException {
        return fdfsUploadService.uploadFile(file);
    }

    // 根据文件访问路径删除文件
    @DeleteMapping("/delete")
    @ApiOperation(value = "根据文件访问路径删除文件",httpMethod = "DELETE")
    public void deleteByPath(@RequestParam("path") String path){
         fdfsUploadService.deleteByPath(path);
    }

    // 下载文件
    @GetMapping("/download")
    @ApiOperation(value = "根据文件访问路径下载文件",httpMethod = "GET")
    public void downloadByPath(@RequestParam("path") String path, HttpServletResponse response) throws MalformedURLException {
        fdfsUploadService.download(path,response);
    }

}
