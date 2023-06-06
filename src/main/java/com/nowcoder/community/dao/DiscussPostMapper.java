package com.nowcoder.community.dao;

import com.nowcoder.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * ClassName: DiscussPostMapper
 * Package: com.nowcoder.community.dao
 * Description:
 *
 * @Author CJ
 * @Create 2023/5/29 20:57
 * @Version 1.0
 */
@Mapper
public interface DiscussPostMapper {

    List<DiscussPost> selectDiscussPost(Integer userId, Integer offset,Integer limit);

    List<DiscussPost> selectDiscussPostById(Integer userId);

//    @Select("select * from discuss_post")
//    List<DiscussPost> selectDiscussPostAll();


    Integer selectDiscussPostRows(@Param("userId") Integer userId);

    Integer insertDiscussPost(DiscussPost discussPost);

    @Select("select * from discuss_post where id = #{discussPostId}")
    DiscussPost selectDiscussPostByPostId(Integer discussPostId);

    void updateCommentCount(Integer entityId, int count);
}
