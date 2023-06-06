package com.nowcoder.community.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * ClassName: comment
 * Package: com.nowcoder.community.entity
 * Description:
 *
 * @Author CJ
 * @Create 2023/6/6 15:36
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    private Integer id;
    private Integer userId;
    private Integer entityType;
    private Integer entityId;
    private Integer targetId;
    private String content;
    private Integer status;
    private Date createTime;
}
