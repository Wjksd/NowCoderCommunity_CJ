package com.nowcoder.community.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * ClassName: LoginRequriment
 * Package: com.nowcoder.community.annotation
 * Description:
 *
 * @Author CJ
 * @Create 2023/6/5 20:08
 * @Version 1.0
 */
@Target({ElementType.METHOD})       // 用于描述方法
@Retention(RetentionPolicy.RUNTIME) // 程序运行才有效
@Documented
@Component
public @interface LoginRequriment {
    //标记作用
}
