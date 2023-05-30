package com.nowcoder.community.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * ClassName: DiscussPost
 * Package: com.nowcoder.community.entity
 * Description:
 *
 * @Author CJ
 * @Create 2023/5/29 20:50
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiscussPost {

    private Integer id;
    private Integer userId;
    private String title;
    private String content;
    private int type;
    private int status;
    private Date createTime;
    private Integer commentCount;
    private Double score;
}
