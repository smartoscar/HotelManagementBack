package com.oscar.springbootstudy.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.oscar.springbootstudy.base.FastDfsClient;
import com.oscar.springbootstudy.commons.ApiData;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@RestController
@RequestMapping("/fileTrans")
public class FileTransController {

    @Autowired
    private FastDfsClient fastDfsClient;

    @PostMapping("/uploadFile")
    public ApiData uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String path = fastDfsClient.uploadFile(file);
            if(!StringUtils.isEmpty(path)) {
                return new ApiData(ApiData.STATUS_SUCCESS, "上传成功", path);
            } else {
                return new ApiData(ApiData.STATUS_FAIL, "上传失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiData(ApiData.STATUS_FAIL, "上传失败");
        }
    }

    @GetMapping("/deleteByPath")
    public ApiData deleteByPath(String filePathName) {
        fastDfsClient.deleteFile(filePathName);
        return new ApiData(ApiData.STATUS_SUCCESS, "删除文件成功");
    }

    @GetMapping("/download")
    public void download(String fileUrl, HttpServletResponse response, HttpServletRequest request) throws IOException {
        byte[] data = fastDfsClient.download(fileUrl);
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("下载文件名.jpg", "UTF-8"));
        // 写出
        ServletOutputStream outputStream = response.getOutputStream();
        IOUtils.write(data, outputStream);
    }
}
