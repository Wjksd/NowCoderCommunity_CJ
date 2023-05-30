package com.nowcoder.community.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;

/**
 * ClassName: AlphaConfig
 * Package: com.nowcoder.community.config
 * Description:
 *
 * @Author CJ
 * @Create 2023/5/29 10:23
 * @Version 1.0
 */

@Configuration
public class AlphaConfig {
    //管理第三方的bean
    @Bean("format")
    public SimpleDateFormat simpleDateFormat(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
}
