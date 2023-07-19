package com.lyr.fdfs.util;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;

/**
 * FastDFS客户端包装类
 */
@Slf4j
@Component
public class FdfsUtil {

    @Autowired
    private FastFileStorageClient storageClient;

    @Value("${visit.host}")
    private String VISIT_HOST;

    // 可以上传图片文件：JPG、PNG、GIF 等格式的图片。
    //  文档文件：DOC、DOCX、PDF、TXT 等格式的文档。
    //视频文件：MP4、MOV、AVI、WMV 等格式的视频。
    //音频文件：MP3、WAV、FLAC 等格式的音频。
    //压缩文件：ZIP、RAR、7Z 等格式的压缩文件。
    //其他文件：各种格式的二进制文件，例如 EXE、APK、IPA 等

    /**
     * 可以上传的文件类型如下：
     * 图片文件：JPG、PNG、GIF 等格式的图片。
     * 文档文件：DOC、DOCX、PDF、TXT 等格式的文档。
     * 视频文件：MP4、MOV、AVI、WMV 等格式的视频。
     * 音频文件：MP3、WAV、FLAC 等格式的音频。
     * 压缩文件：ZIP、RAR、7Z 等格式的压缩文件。
     * 其他文件：各种格式的二进制文件，例如 EXE、APK、IPA 等
     *
     * 文件大小限制为1000MB
     *
     * 在 FastDFS 中，同一组（Group）下的文件名是唯一的。如果上传相同的文件，FastDFS 会将新的文件覆盖已存在的同名文件。如果要避免覆盖，可以通过在文件名中添加时间戳或随机字符串等方式来使得文件名不同。
     *
     */
    public String upload(MultipartFile file) throws IOException {
        String filePath = null;
        if (file != null) {
            // 文件名称：如 aa.png
            String originalFilename = file.getOriginalFilename();
            StorePath storePath = storageClient.uploadFile(file.getInputStream(),
                    file.getSize(), FilenameUtils.getExtension(originalFilename), null);
            filePath = VISIT_HOST + storePath.getFullPath();
        }
        return filePath;
    }

    /**
     * 后台上传文件：比如有时候处理了什么文件需要上传
     *
     * @param bytes     文件字节
     * @param extension 文件扩展名
     * @return 返回文件路径（卷名和文件名）
     */
    public String upload(byte[] bytes, String extension) {
        ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
        // 元数据
        StorePath storePath = storageClient.uploadFile(stream, bytes.length, extension, null);
        return storePath.getFullPath();
    }

    /**
     * 获取下载文件的字节流（此时下载字节流并不能得到文件）
     */
    public byte[] getFileBytesByPath(String filePath) {
        byte[] bytes = null;
        if (StringUtils.isNotBlank(filePath)) {
            URI uri = URI.create(filePath);
            String group = uri.getPath().split("/")[1];
            String path = uri.getPath().substring(group.length() + 2);
            DownloadByteArray byteArray = new DownloadByteArray();
            bytes = storageClient.downloadFile(group, path, byteArray);
        }
        return bytes;
    }

    /**
     * 删除文件
     *
     * @param filePath 文件路径
     */
    public void delete(String filePath) {
        if (StringUtils.isNotBlank(filePath)) {
            storageClient.deleteFile(filePath);
        }
    }
}
