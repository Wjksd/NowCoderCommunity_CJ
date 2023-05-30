package com.nowcoder.community.service.imp;

import com.nowcoder.community.dao.UserMapper;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ClassName: UserServiceImp
 * Package: com.nowcoder.community.service.imp
 * Description:
 *
 * @Author CJ
 * @Create 2023/5/29 21:49
 * @Version 1.0
 */
@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public User findUserById(Integer id) {
        return userMapper.selectById(id);
    }
}
