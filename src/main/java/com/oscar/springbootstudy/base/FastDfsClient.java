package com.oscar.springbootstudy.base;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FastDfsClient {

    @Autowired
    private FastFileStorageClient storageClient;

    public String uploadFile(MultipartFile file) throws Exception {
        return upload(file);
    }

    private String upload(MultipartFile multipartFile) throws Exception {
        String originalFilename = multipartFile
                .getOriginalFilename()
                .substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1);
        StorePath storePath = this.storageClient
                .uploadImageAndCrtThumbImage(multipartFile.getInputStream(),multipartFile.getSize(), originalFilename, null);
        return storePath.getFullPath();
    }

    public void deleteFile(String fileUrl) {
        if (StringUtils.isEmpty(fileUrl)) {
//            LOGGER.info("fileUrl == >>文件路径为空...");
            return;
        }
        try {
            //对根据传入的url删除文件
            StorePath storePath = StorePath.parseFromUrl(fileUrl);
            storageClient.deleteFile(storePath.getGroup(), storePath.getPath());
            //删除上传所产生的缩略图
            val split = fileUrl.split("\\.");
            split[0] += "_150x150.";
            String fileUrl2 = split[0] + split[1];
            StorePath storePath2 = StorePath.parseFromUrl(fileUrl2);
            storageClient.deleteFile(storePath2.getGroup(), storePath2.getPath());
        } catch (Exception e) {
//            LOGGER.info(e.getMessage());
        }
    }

    public byte[] download(String fileUrl) {
        String group = fileUrl.substring(0, fileUrl.indexOf("/"));
        String path = fileUrl.substring(fileUrl.indexOf("/") + 1);
        byte[] bytes = storageClient.downloadFile(group, path, new DownloadByteArray());
        return bytes;
    }

}
