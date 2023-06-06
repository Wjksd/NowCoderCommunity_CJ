package com.nowcoder.community.controller;

import com.google.code.kaptcha.Producer;
import com.nowcoder.community.annotation.LoginRequriment;
import com.nowcoder.community.entity.Result;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.imp.LoginServiceImp;
import com.nowcoder.community.service.imp.UserServiceImp;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

/**
 * ClassName: LoginController
 * Package: com.nowcoder.community.controller
 * Description:
 *
 * @Author CJ
 * @Create 2023/5/30 20:32
 * @VersiGet1.0
 */
@RestController
@RequestMapping
public class LoginController {

    @Autowired
    private UserServiceImp userServiceImp;

    @Autowired
    private Producer kaptchaProducer;

    @Autowired
    private LoginServiceImp loginServiceImp;


    @GetMapping("/register")
    public String getRegisterPage(){
        return "/site/register";
    }

    //用户注册
    @PostMapping("/register")
    public Result register(User user){
        return userServiceImp.register(user);
    }

    //用户激活功能，响应激活链接，修改用户激活状态
    @GetMapping("/activation/{userId}/{code}")
    public Result activation(@PathVariable Integer userId,@PathVariable String code){
        return userServiceImp.activation(userId,code);
    }

    //生成验证码
    @GetMapping("/kaptcha")
    public void getKaptcha(HttpServletResponse response, HttpSession session){
        //生成验证码
        String text = kaptchaProducer.createText();
        BufferedImage image = kaptchaProducer.createImage(text);

        //存入session
        session.setAttribute("kaptcha",text);

        //输出图片到浏览器
        response.setContentType("image/png");

        try {
            OutputStream os = response.getOutputStream();
            ImageIO.write(image, "png", os);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    //用户登录
    @PostMapping("/login")
    public Result login(String username, String password, String code, boolean rememberMe,
                        HttpSession session, HttpServletResponse response
                        /*,@CookieValue(name = "kaptchaOwner",required = false) String kaptchaOwner*/){
        //验证码
        //当前会话存的验证码
        String kaptcha = (String) session.getAttribute("kaptcha");
        if(StringUtils.isBlank(kaptcha) || StringUtils.isBlank(code)
                || !kaptcha.equalsIgnoreCase(code)){
            return Result.error("验证码不正确");
        }

        //检查账号密码
        Integer expireSeconds = rememberMe ? 3600 * 12 * 30:3600 * 24;

        Result result = loginServiceImp.login(username, password, expireSeconds);

        //失败结果直接返回
        if(result.getCode() == 0){
            return result;
        }else{
            //成功则需要返回cookie
            Cookie cookie = new Cookie("ticket",result.getData().toString());
            cookie.setMaxAge(60);
            //cookie.setPath("/*");
            response.addCookie(cookie);

            return result;
        }

    }

    //用户登出
    @LoginRequriment
    @GetMapping("/logout")
    public Result logout(@CookieValue("ticket") String ticket){
        return loginServiceImp.logout(ticket);
    }


}
