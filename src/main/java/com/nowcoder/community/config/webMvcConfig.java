package com.nowcoder.community.config;

import com.nowcoder.community.interceptor.AlphaInterceptor;
import com.nowcoder.community.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * ClassName: webMvcConfig
 * Package: com.nowcoder.community.config
 * Description:
 *
 * @Author CJ
 * @Create 2023/6/1 15:02
 * @Version 1.0
 */
@Configuration
public class webMvcConfig implements WebMvcConfigurer {

    @Autowired
    private AlphaInterceptor alphaInterceptor;

    @Autowired
    private LoginInterceptor loginInterceptor;



    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(alphaInterceptor)
                .addPathPatterns("/alpha/**");

        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**").excludePathPatterns("/alpha/**","/logout");

    }


}
