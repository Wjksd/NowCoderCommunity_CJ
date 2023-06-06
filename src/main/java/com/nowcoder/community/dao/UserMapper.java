package com.nowcoder.community.dao;

import com.nowcoder.community.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;


/**
 * ClassName: UserMapper
 * Package: com.nowcoder.community.dao
 * Description:
 *
 * @Author CJ
 * @Create 2023/5/29 17:17
 * @Version 1.0
 */
@Mapper
public interface UserMapper {
    User selectById(int id);

    User selectByName(String username);

    User selectByEmail(String email);

    //@Insert("insert into user(username) values('cj')")
    int insertUser(User user);

    int updateStatus(int id,int status);

    int updateHeaderUrl(int id,String headerUrl);

    int updatePassword(int id,String password);


    void updateSalt(Integer id, String newSalt);

}
