package com.nowcoder.community.service;

import com.nowcoder.community.dao.DiscussPostMapper;
import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.entity.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName: DiscussPostService
 * Package: com.nowcoder.community.service
 * Description:
 *
 * @Author CJ
 * @Create 2023/5/29 21:41
 * @Version 1.0
 */
public interface DiscussPostService {


    public List<DiscussPost> findDiscussPosts(Integer userId, Integer offset, Integer limit);


    public Integer findDiscussPostRows(Integer userId);

    PageBean page(Integer page, Integer pagesize, Integer userId);
}
