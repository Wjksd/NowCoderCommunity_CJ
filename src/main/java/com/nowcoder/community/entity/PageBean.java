package com.nowcoder.community.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * ClassName: PageBean
 * Package: com.itheima.pojo
 * Description:
 *
 * @Author CJ
 * @Create 2023/5/8 20:31
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageBean<T> {
    private Long total;//总记录数
    private List<T> rows; //数据列表
}
