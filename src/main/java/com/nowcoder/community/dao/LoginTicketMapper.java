package com.nowcoder.community.dao;

import com.nowcoder.community.entity.LoginTicket;
import org.apache.ibatis.annotations.*;

/**
 * ClassName: LoginTicketMapper
 * Package: com.nowcoder.community.dao
 * Description:
 *
 * @Author CJ
 * @Create 2023/5/31 15:30
 * @Version 1.0
 */
@Mapper
public interface LoginTicketMapper {

    //插入一条数据
    @Insert("insert into login_ticket(user_id, ticket, status, expired) values (#{userId}, #{ticket}, #{status}, #{expired})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertLoginTicket(LoginTicket loginTicket);

    @Select("select id, user_id, ticket, status, expired from login_ticket where ticket = #{ticket}")
    LoginTicket selectByTicket(String ticket);


    @Update("update login_ticket set status = #{status} where ticket = #{ticket}")
    int updateStatus(String ticket , int status);

}
