package com.nowcoder.community.service.imp;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.nowcoder.community.dao.CommentMapper;
import com.nowcoder.community.entity.Comment;
import com.nowcoder.community.entity.PageBean;
import com.nowcoder.community.entity.Result;
import com.nowcoder.community.util.CommunityConstant;
import com.nowcoder.community.util.SensitiveFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * ClassName: CommentServiceImp
 * Package: com.nowcoder.community.service.imp
 * Description:
 *
 * @Author CJ
 * @Create 2023/6/6 16:00
 * @Version 1.0
 */
@Service
public class CommentServiceImp implements CommunityConstant {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private SensitiveFilter sensitiveFilter;
    @Autowired
    private DisscussPostServiceImp disscussPostServiceImp;

    //功能：分页查询评论
    public PageBean<Comment> findCommentByEntity(Integer page, Integer pagesize,Integer entityType, Integer entityId){
        //设置分页参数
        PageHelper.startPage(page,pagesize);
        //执行查询
        List<Comment> comments = commentMapper.selectCommentByEntity(entityType, entityId);
        Page<Comment> commentsPage = (Page<Comment>) comments;
        //封装对象
        PageBean<Comment> pageBean = new PageBean<Comment>(commentsPage.getTotal(),commentsPage.getResult());
        return pageBean;
    }

    //查询数量
    public int findCommentCount(int entityType,int entityId){
        return commentMapper.selectCountByEntity(entityType,entityId);
    }

    //1,添加评论；2，修改评论数据->事务管理
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Result addComment(Comment comment) {
        if (comment == null){
            return Result.error("参数为空");
        }

        // 添加评论
        comment.setContent(HtmlUtils.htmlEscape(comment.getContent()));     // 过滤标签
        comment.setContent(sensitiveFilter.filter(comment.getContent()));   // 过滤敏感词
        int rows = commentMapper.insertComment(comment);

        // 更新帖子的评论数量（只有评论给帖子的时候才会增加评论数量，回复给评论的时候不会）
        if (comment.getEntityType() == ENTITY_TYPE_POST){   // 判断为一个帖子
            int count = commentMapper.selectCountByEntity(comment.getEntityType(), comment.getEntityId());
            // 查到了数量之后要把这个count更新到帖子表中的comment_count字段中
            disscussPostServiceImp.updateCommentCount(comment.getEntityId(), count);
        }
        return Result.success("上传成功");
    }
}
