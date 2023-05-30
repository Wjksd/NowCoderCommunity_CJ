package com.nowcoder.community.service;

import com.nowcoder.community.entity.User;

/**
 * ClassName: UserService
 * Package: com.nowcoder.community.service
 * Description:
 *
 * @Author CJ
 * @Create 2023/5/29 21:48
 * @Version 1.0
 */
public interface UserService {

    public User findUserById(Integer id);
}
