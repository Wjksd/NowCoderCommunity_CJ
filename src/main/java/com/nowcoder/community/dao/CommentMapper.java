package com.nowcoder.community.dao;

import com.nowcoder.community.entity.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * ClassName: CommentMapper
 * Package: com.nowcoder.community.dao
 * Description:
 *
 * @Author CJ
 * @Create 2023/6/6 15:39
 * @Version 1.0
 */
@Mapper
public interface CommentMapper {
    //根据实体查找评论
    @Select("select * from comment where status = 0 and " +
            "entity_type = #{entityType} and entity_id = #{entityId}")
    List<Comment> selectCommentByEntity(int entityType, int entityId);

    //获取评论总数
    @Select("select count(*) from comment where status = 0 and entity_type = " +
            "#{entityType} and entity_id = #{entityId} ")
    int selectCountByEntity(int entityType , int entityId);

    //插入评论
    @Insert("insert into comment values" +
            "(null,#{userId},#{entityType},#{entityId},#{targetId},#{content},#{status},#{createTime})")
    int insertComment(Comment comment);

    //
    @Select("select * from comment where id = #{id}")
    Comment selectCommentById(Integer id);

}
