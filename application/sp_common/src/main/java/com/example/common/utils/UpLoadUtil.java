package com.example.common.utils;

import com.example.common.exception.ServiceException;
import com.example.common.vo.UpLoadFileVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 文件上传助手类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class UpLoadUtil {
    @Autowired
    protected HttpServletRequest request;
    // 上传文件保存路径
    private String filePath = "static/uploads/" + new SimpleDateFormat("yyyy-MM").format(new Date()) + "/";

    /**
     * 文件上传
     *
     * @param file
     * @param fileExt 限制上传后缀
     * @return
     */
    public UpLoadFileVO upload(MultipartFile file, String fileExt) {
        // 对文件判空
        if (file.isEmpty()) {
            throw new ServiceException("文件不能为空", 106);
        }
        // 获取文件名称
        String originalFileName = file.getOriginalFilename();
        // 获取后缀
        String originalFileExt = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        // 验证文件后缀
        List<String> fileExtList = Arrays.asList(fileExt.split(","));
        if (!fileExtList.contains(originalFileExt)) {
            throw new ServiceException("不允许上传该类型的文件", 106);
        }
        // 当前时间戳对文件进行重命名，并保存文件名后缀
        String fileName = System.currentTimeMillis() + "." + originalFileExt;
        File upload = new File(getPath(filePath + fileName + "/"));
        try {
            // 上传文件
            file.transferTo(upload);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(e.getMessage(), 106);
        }
        UpLoadFileVO vo = new UpLoadFileVO();
        // 文件名称
        vo.setFileName(fileName);
        // 文件大小字节
        vo.setFileSize(file.getSize());
        // 文件保存路径
        vo.setFullPath("/" + filePath + fileName);
        // 文件网络路径
        vo.setFileUrl(DomainUtil.getCurrentDomain(request) + "/" + filePath + fileName);
        return vo;
    }

    /**
     * 获取目录工具类
     *
     * @param subdirectory
     * @return
     */
    public static String getPath(String subdirectory) {
        //获取跟目录---与jar包同级目录的upload目录下指定的子目录subdirectory
        File upload = null;
        try {
            //本地测试时获取到的是"工程目录/target/upload/subdirectory
            File path = new File(ResourceUtils.getURL("classpath:").getPath());
            if (!path.exists()) path = new File("");
            upload = new File(path.getAbsolutePath(), subdirectory);
            if (!upload.exists()) upload.mkdirs();//如果不存在则创建目录
            String realPath = String.valueOf(upload);
            return realPath;
        } catch (FileNotFoundException e) {
            throw new ServiceException(e.getMessage(), 106);
        }
    }
}
