package com.nowcoder.community.service.imp;

import com.nowcoder.community.dao.LoginTicketMapper;
import com.nowcoder.community.dao.UserMapper;
import com.nowcoder.community.entity.LoginTicket;
import com.nowcoder.community.entity.Result;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.util.CommunityUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * ClassName: LoginServiceImp
 * Package: com.nowcoder.community.service.imp
 * Description:
 *
 * @Author CJ
 * @Create 2023/5/31 16:33
 * @Version 1.0
 */
@Service
public class LoginServiceImp {

    @Autowired
    private LoginTicketMapper loginTicketMapper;

    @Autowired
    private UserMapper userMapper;


    public Result login(String username, String password, Integer expireSeconds) {
        //空值处理
        if(StringUtils.isBlank(username)){
            return Result.error("账号为空错误");
        }
        if(StringUtils.isBlank(password)){
            return Result.error("密码为空错误");
        }

        //验证账号
        User user = userMapper.selectByName(username);
        if(user == null){
            return Result.error("账号不存在");
        }

        //验证密码
        String inputPassword = CommunityUtil.md5(password+user.getSalt());
        if(!user.getPassword().equals(inputPassword)){
            return Result.error("密码不正确");
        }

        //验证激活状态
        if(user.getStatus() == 0){
            return Result.error("账号未激活");
        }

        //到这里登陆成功
        //生成激活凭证
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(user.getId());
        loginTicket.setTicket(CommunityUtil.generateUUID());
        loginTicket.setStatus(0);
        loginTicket.setExpired(new Date(System.currentTimeMillis()+expireSeconds));

        //存到数据库
        loginTicketMapper.insertLoginTicket(loginTicket);

        //返回成功结果
        return Result.success(loginTicket.getTicket());


    }

    public Result logout(String ticket) {
        loginTicketMapper.updateStatus(ticket,1);
        Integer newStatus = loginTicketMapper.selectByTicket(ticket).getStatus();
        return Result.success("登出成功,现在状态为" + newStatus);
    }
}
