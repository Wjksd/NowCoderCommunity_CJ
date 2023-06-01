package com.nowcoder.community.interceptor;

import com.nowcoder.community.entity.LoginTicket;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.imp.UserServiceImp;
import com.nowcoder.community.util.CookieUtil;
import com.nowcoder.community.util.HostHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

/**
 * ClassName: LoginInterceptor
 * Package: com.nowcoder.community.interceptor
 * Description:
 *
 * @Author CJ
 * @Create 2023/6/1 15:18
 * @Version 1.0
 */
@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private UserServiceImp userServiceImp;

    @Autowired
    private HostHolder hostHolder;

    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        //获取cookie中的ticket
        String ticket = CookieUtil.getValue(request, "ticket");
        //根据ticket获取相应的loginTicket凭证
        LoginTicket loginTicket = userServiceImp.getLoginTicket(ticket);
        //从loginTicket凭证中获取userId，进一步得到用户对象user
        if(loginTicket != null && loginTicket.getStatus() == 0
                && loginTicket.getExpired().after(new Date())) {
            int userId = loginTicket.getUserId();
            User user = userServiceImp.findUserById(userId);
            //存储该用户
            log.info("本线程存储该用户信息：#{}",user);
            hostHolder.setUser(user);
        }
        return true;
    }

    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           @Nullable ModelAndView modelAndView) throws Exception {
        User getUser = hostHolder.getUser();
        if(getUser !=null ){
        log.info("成功拿到该线程对象：#{}",getUser);
        }

    }

}
