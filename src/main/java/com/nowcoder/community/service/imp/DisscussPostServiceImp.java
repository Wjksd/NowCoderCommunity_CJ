package com.nowcoder.community.service.imp;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.nowcoder.community.dao.DiscussPostMapper;
import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.entity.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName: DisscussPostServiceImp
 * Package: com.nowcoder.community.service.imp
 * Description:
 *
 * @Author CJ
 * @Create 2023/5/29 21:41
 * @Version 1.0
 */
@Service
public class DisscussPostServiceImp {

    @Autowired
    private  DiscussPostMapper dpm;

    public PageBean page(Integer page, Integer pagesize, Integer userId) {
        //设置分页参数
        //PageHelper.startPage(page,pagesize);
        PageHelper.startPage(page,pagesize);
        //执行查询
        List<DiscussPost> postList = dpm.selectDiscussPostById(userId);
        //测试pagehelper
        //List<DiscussPost> postList = dpm.selectDiscussPostAll();
        Page<DiscussPost> postPage = (Page<DiscussPost>) postList;
        //封装对象
        PageBean pageBean = new PageBean(postPage.getTotal(),postPage.getResult());
        return pageBean;
    }


    public List<DiscussPost> findDiscussPosts(Integer userId, Integer offset, Integer limit) {
        return dpm.selectDiscussPost(userId,offset,limit);
    }


    public Integer findDiscussPostRows(Integer userId) {
        return dpm.selectDiscussPostRows(userId);
    }


}
