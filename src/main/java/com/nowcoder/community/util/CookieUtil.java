package com.nowcoder.community.util;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

/**
 * ClassName: CookieUtil
 * Package: com.nowcoder.community.interceptor
 * Description:
 * 从request中相应名称的cookie
 * @Author CJ
 * @Create 2023/6/1 15:31
 * @Version 1.0
 */

public class CookieUtil {
    public static String getValue(HttpServletRequest request, String name){
        if(request == null || name == null){
            throw new IllegalArgumentException("参数为空！");
        }
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals(name)){
                    return cookie.getValue();
                }
            }
        }
        return null;

    }
}
