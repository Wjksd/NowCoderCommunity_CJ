package com.nowcoder.community.controller;

import com.nowcoder.community.annotation.LoginRequriment;
import com.nowcoder.community.entity.Comment;
import com.nowcoder.community.entity.Result;
import com.nowcoder.community.service.imp.CommentServiceImp;
import com.nowcoder.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * ClassName: CommentController
 * Package: com.nowcoder.community.controller
 * Description:
 *
 * @Author CJ
 * @Create 2023/6/6 17:31
 * @Version 1.0
 */
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private CommentServiceImp commentServiceImp;

    @LoginRequriment
    @PostMapping("/add/{discussPostId}")
    public Result addComment(
            @PathVariable("discussPostId") int discussPostId, Comment comment){
        comment.setUserId(hostHolder.getUser().getId());
        comment.setStatus(0);
        comment.setCreateTime(new Date());

        return commentServiceImp.addComment(comment);

    }
}
