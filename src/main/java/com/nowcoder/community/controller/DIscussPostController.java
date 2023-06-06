package com.nowcoder.community.controller;

import com.alibaba.fastjson2.JSONObject;
import com.github.pagehelper.Page;
import com.nowcoder.community.annotation.LoginRequriment;
import com.nowcoder.community.dao.CommentMapper;
import com.nowcoder.community.dao.UserMapper;
import com.nowcoder.community.entity.*;
import com.nowcoder.community.service.imp.CommentServiceImp;
import com.nowcoder.community.service.imp.DisscussPostServiceImp;
import com.nowcoder.community.service.imp.UserServiceImp;
import com.nowcoder.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ClassName: DIscussPostController
 * Package: com.nowcoder.community.controller
 * Description:
 *
 * @Author CJ
 * @Create 2023/6/6 10:29
 * @Version 1.0
 */
@RestController
@RequestMapping("/discuss")
public class DIscussPostController {
    @Autowired
    private DisscussPostServiceImp disscussPostServiceImp;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private CommentServiceImp commentServiceImp;

    @Autowired
    private UserServiceImp userServiceImp;

    @LoginRequriment
    @PostMapping("/add")
    public Result addDiscussPost(String title,String content){
        //使用@LoginRequriment 进来代表已经登陆了
        User nowUser = hostHolder.getUser();

        DiscussPost discussPost = new DiscussPost();
        discussPost.setUserId(nowUser.getId());
        discussPost.setTitle(title);
        discussPost.setContent(content);
        discussPost.setCreateTime(new Date());

        return disscussPostServiceImp.addDiscussPost(discussPost);
    }

    //显示帖子详情
    @GetMapping("/detail/{discussPostId}")
    public Result getDiscussPost(@PathVariable  Integer discussPostId){
        JSONObject resJson = new JSONObject();

        //查询帖子
        DiscussPost discuss = disscussPostServiceImp.findDiscussPostById(discussPostId);
        resJson.put("帖子详情",discuss);

        //查询帖子作者
        User discussUser = userServiceImp.findUserById(discuss.getUserId());
        resJson.put("帖子作者",discussUser.getUsername());


        /**
         * 查询评论
         * 1.普通评论 enyityType = 1 (需要分页)
         * 2.回复（无目标）entityType  = 2  targetId = 0 （不分页）
         * 3.回复（有目标）entityType  = 2  targetId != 0 （不分页）
         */

        //当前帖子的id作为查询评论的入参
        int entityId = discuss.getId();

        //1
        PageBean<Comment> commentPage = commentServiceImp.findCommentByEntity(1, 5, 1,entityId);
        resJson.put("帖子的评论（第一页）",commentPage);

        //2\3
        List<Comment> reply2 = new ArrayList<>();
        List<Comment> reply3 = new ArrayList<>();

        PageBean<Comment> replyPage = commentServiceImp.findCommentByEntity(1, Integer.MAX_VALUE, 2, entityId);
        List<Comment> replyList = replyPage.getRows();
        for(Comment reply:replyList){
            if(reply.getTargetId()==0){
                reply2.add(reply);
            }else{
                //User target = userServiceImp.findUserById(reply.getTargetId());
                reply3.add(reply);
            }
        }
        resJson.put("回复1",reply2);
        resJson.put("回复2",reply3);
        return Result.success(resJson);
    }
}
