package com.nowcoder.community.service.imp;

import com.nowcoder.community.dao.LoginTicketMapper;
import com.nowcoder.community.dao.UserMapper;
import com.nowcoder.community.entity.LoginTicket;
import com.nowcoder.community.entity.Result;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.util.CommunityUtil;
import com.nowcoder.community.util.MailClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Date;
import java.util.Random;

/**
 * ClassName: UserServiceImp
 * Package: com.nowcoder.community.service.imp
 * Description:
 *
 * @Author CJ
 * @Create 2023/5/29 21:49
 * @Version 1.0
 */
@Service
public class UserServiceImp {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MailClient mailClient;

    @Autowired
    private LoginTicketMapper loginTicketMapper;

//    @Autowired(required = false)
//    private TemplateEngine templateEngine;

    @Value("${community.path.domain}")
    private String domain;


    public User findUserById(Integer id) {
        return userMapper.selectById(id);
    }


    //注册功能
    //传入user 做一些判断，成功后返回激活链接，失败返回提示信息
    public Result register(User user) {

        //空值处理
        if (user == null) {
            throw new IllegalArgumentException("参数不能为空");
        }
        if (StringUtils.isBlank(user.getUsername())) {
            return Result.error("账号不能为空");
        }
        if (StringUtils.isBlank(user.getPassword())) {
            return Result.error("密码不能为空");
        }
        if (StringUtils.isBlank(user.getEmail())) {
            return Result.error("邮箱不能为空");
        }

        //验证账号，账户已存在
        User userSelectByName = userMapper.selectByName(user.getUsername());
        if (userSelectByName != null) {
            return Result.error("账户已存在");
        }

        //验证邮箱，邮箱已被注册
        userSelectByName = userMapper.selectByEmail(user.getEmail());
        if (userSelectByName != null) {
            return Result.error("邮箱已被注册");
        }

        //注册用户,生成所需属性
        user.setSalt(CommunityUtil.generateUUID().substring(0, 5));
        user.setPassword(CommunityUtil.md5(user.getPassword() + user.getSalt()));
        user.setType(0);
        user.setStatus(0);
        user.setActivationCode(CommunityUtil.generateUUID());
        user.setHeaderUrl(String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000)));
        user.setCreateTime(new Date());
        userMapper.insertUser(user);

        //注册成功，发送激活邮件链接
        Context context = new Context();
        context.setVariable("email", user.getEmail());
        // http://localhost:8080/activation/101/code
        String url = domain + "/activation/" + user.getId() + "/" + user.getActivationCode();
        context.setVariable("url", url);
        //String content = templateEngine.process("/mail/activation", context);
        //mailClient.sendMail(user.getEmail(), "激活账号", content);
        return Result.success("url:" + url);
    }

    //用户激活
    public Result activation(Integer userId, String code) {
        //已经激活，失败
        User nowUser = userMapper.selectById(userId);
        int nowStatus = nowUser.getStatus();
        if (nowStatus == 1) {
            return Result.error("已经激活，失败");
        } else {
            //无效激活码，失败
            if (!nowUser.getActivationCode().equals(code)) {
                return Result.error("无效激活码，失败");
            } else {
                //激活成功
                //nowUser.setStatus(1);
                userMapper.updateStatus(userId,1);
                return Result.success("激活成功");
            }



        }
    }

    public LoginTicket getLoginTicket(String ticket) {
        return loginTicketMapper.selectByTicket(ticket);
    }
}
