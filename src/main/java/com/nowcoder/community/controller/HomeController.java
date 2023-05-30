package com.nowcoder.community.controller;

import com.nowcoder.community.entity.PageBean;
import com.nowcoder.community.entity.Result;
import com.nowcoder.community.service.DiscussPostService;
import com.nowcoder.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * ClassName: DiscussPostController
 * Package: com.nowcoder.community.controller
 * Description:
 *
 * @Author CJ
 * @Create 2023/5/30 10:06
 * @Version 1.0
 */
@RestController
@RequestMapping("/index")
public class HomeController {

    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private UserService userService;


    //分页查询评论
    @GetMapping
    public Result getIndexPage(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pagesize,
            Integer userId
    ){
        PageBean pagebean = discussPostService.page(page,pagesize,userId);
        return Result.success(pagebean);
    }


}
