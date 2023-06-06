package com.nowcoder.community.controller;

import com.nowcoder.community.annotation.LoginRequriment;
import com.nowcoder.community.entity.Result;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.imp.UserServiceImp;
import com.nowcoder.community.util.CommunityUtil;
import com.nowcoder.community.util.HostHolder;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * ClassName: UserController
 * Package: com.nowcoder.community.controller
 * Description:
 *
 * @Author CJ
 * @Create 2023/6/4 18:38
 * @Version 1.0
 */
@RestController
public class UserController {

    // 上传路径
    @Value("${community.path.upload}")
    private String uploadPath;

    // 域名
    @Value("${community.path.domain}")
    private String domain;


    @Autowired
    HostHolder hostHolder;

    @Autowired
    UserServiceImp userServiceImp;

    @GetMapping("/user")
    public String getSettingPage(){
        return "/site/setting";
    }

    @LoginRequriment
    @PostMapping("/upload")
    public Result uploadHeader(MultipartFile headImage){

        if(headImage == null){
            return Result.error("尚未选择图片");
        }
        Class aClass = headImage.getClass();

        String originalFilename = headImage.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        if(StringUtils.isBlank(suffix)){
            return Result.error("文件格式不正确");
        }

        // 生成随机文件名
        String fileName = CommunityUtil.generateUUID() + suffix;
        // 确定文件存放路径
        File dest = new File(uploadPath + "/" + fileName);
        try {
            headImage.transferTo(dest);
        } catch (IOException e) {
            throw new RuntimeException("上传文件失败，服务器发生异常", e);
        }

        // 上传成功则更新当前用户的头像访问路径（web访问路径）
        // eg: http://localhost:8080/community/user/header/xxx.png
        User user = hostHolder.getUser();
        String headerUrl = domain + "/header/" + fileName;
        userServiceImp.updateHeader(user.getId(), headerUrl);

        return Result.success(headerUrl);

    }

    @LoginRequriment
    @GetMapping("/header/{fileName}")
    public void getHeader(@PathVariable String fileName, HttpServletResponse response){
        // 服务器存放路径
        fileName = uploadPath + "/" + fileName;
        // 文件的后缀
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        // 相应图片
        response.setContentType("image/" + suffix);
        try(
                OutputStream os = response.getOutputStream();
                FileInputStream fis = new FileInputStream(fileName);
        ) {
            byte[] buffer = new byte[1024];
            int b = 0;
            while ((b = fis.read(buffer)) != -1){
                os.write(buffer, 0, b);
            }
        } catch (IOException e) {
            //log.error("读取图像失败" + e.getMessage());
            throw new RuntimeException("读取图像失败", e);
        }

    }

    @LoginRequriment
    @PostMapping("setPassword")
    public Result setPassword(String password){
        return userServiceImp.setPassword(password);
    }


}

