package com.nowcoder.community.service.imp;


import com.alibaba.fastjson2.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.nowcoder.community.dao.DiscussPostMapper;
import com.nowcoder.community.dao.UserMapper;
import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.entity.PageBean;
import com.nowcoder.community.entity.Result;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.util.SensitiveFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

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

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SensitiveFilter sensitiveFilter;

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


    public Result addDiscussPost(DiscussPost discussPost) {
        if(discussPost == null){
            return Result.error("发布内容为空");
        }

        // 转义HTML标记
        discussPost.setTitle(HtmlUtils.htmlEscape(discussPost.getTitle()));
        discussPost.setContent(HtmlUtils.htmlEscape(discussPost.getContent()));

        // 过滤敏感词：把过滤后的再填装进去
        discussPost.setTitle(sensitiveFilter.filter(discussPost.getTitle()));
        discussPost.setContent(sensitiveFilter.filter(discussPost.getContent()));

        dpm.insertDiscussPost(discussPost);
        JSONObject json = new JSONObject();
        json.put("提示信息","发布成功");
        json.put("title",discussPost.getTitle());
        json.put("content",discussPost.getContent());
        return Result.success(json);
    }

    public DiscussPost findDiscussPostById(Integer discussPostId) {
        //根据帖子id查询帖子
        return dpm.selectDiscussPostByPostId(discussPostId);

    }

    public void updateCommentCount(Integer entityId, int count) {
        dpm.updateCommentCount(entityId,count);
    }
}
