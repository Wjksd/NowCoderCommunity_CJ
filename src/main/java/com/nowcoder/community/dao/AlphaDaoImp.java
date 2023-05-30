package com.nowcoder.community.dao;

import org.springframework.stereotype.Repository;

/**
 * ClassName: AlphaDaoImp
 * Package: com.nowcoder.community.dao
 * Description:
 *
 * @Author CJ
 * @Create 2023/5/29 10:46
 * @Version 1.0
 */
@Repository
public class AlphaDaoImp implements AlphaDao{
    @Override
    public String select() {
        return "select success";
    }
}
