package com.nowcoder.community.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * ClassName: LoginTicket
 * Package: com.nowcoder.community.entity
 * Description:
 *
 * @Author CJ
 * @Create 2023/5/31 15:29
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginTicket {
    private Integer id;
    private Integer userId;
    private String ticket;
    private Integer status;
    // 到期日期
    private Date expired;
}
