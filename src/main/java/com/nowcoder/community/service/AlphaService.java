package com.nowcoder.community.service;

import com.nowcoder.community.dao.AlphaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * ClassName: AlphaService
 * Package: com.nowcoder.community.service
 * Description:
 *
 * @Author CJ
 * @Create 2023/5/29 10:21
 * @Version 1.0
 */

@Service
public class AlphaService {
    @Autowired
    private AlphaDao alphaDao;

    public String alphaService(){
        return "alpha";
    }

    public String find(){
        return alphaDao.select();
    }
}
