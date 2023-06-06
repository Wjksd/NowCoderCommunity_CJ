package com.nowcoder.community.interceptor;

import com.nowcoder.community.annotation.LoginRequriment;
import com.nowcoder.community.util.HostHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.Method;

/**
 * ClassName: LoginRequriedIntercepter
 * Package: com.nowcoder.community.interceptor
 * Description:
 *
 * @Author CJ
 * @Create 2023/6/5 20:06
 * @Version 1.0
 */
@Component
public class LoginRequriedIntercepter implements HandlerInterceptor {
    //
    @Autowired
    HostHolder hostHolder;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 只有不满足条件的时候才return false
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            // 调用HandlerMethod的getMethod()方法直接获取到拦截器拦截到的对象
            Method method = handlerMethod.getMethod();
            LoginRequriment loginRequired = method.getAnnotation(LoginRequriment.class);
            if (loginRequired != null && hostHolder.getUser() == null) {
                response.sendError(1,"用户未登录，无法操作");
                return false;
            }
        }
        return true;
    }

}
