package com.lyr.qscommon.util;

import cn.hutool.core.io.FileUtil;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Component
public class IoUtil {

    public static void downLoad(byte[] bytes,String fileName, HttpServletResponse response){
        File file = FileUtil.writeBytes(bytes, fileName);
        // 设置 content-type 和 content-disposition 响应头
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        // 将文件写入响应流中
        try (InputStream inputStream = new FileInputStream(file);
             OutputStream outputStream = response.getOutputStream()) {
            byte[] buffer = new byte[4096];
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 删除临时文件
        FileUtil.del(file);
    }

}
