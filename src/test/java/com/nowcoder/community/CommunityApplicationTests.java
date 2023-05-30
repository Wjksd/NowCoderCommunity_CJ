package com.nowcoder.community;

import com.nowcoder.community.dao.DiscussPostMapper;
import com.nowcoder.community.dao.UserMapper;
import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.DiscussPostService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
class CommunityApplicationTests{
//    private ApplicationContext applicationContext;
//
//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        this.applicationContext = applicationContext;
//    }
//
//    @Test
//    public void test1(){
//
//        //SimpleDateFormat format = (SimpleDateFormat)applicationContext.getBean("format");
//        SimpleDateFormat format = applicationContext.getBean(SimpleDateFormat.class);
//        System.out.println(format.format(new Date()));
//    }
//
//
//    @Autowired
//    private AlphaService alphaService;
//
//    @Test
//    public void test2(){
//        System.out.println(alphaService.alphaService());
//    }


    @Autowired
    private UserMapper userMapper;

    @Test
    public void test3(){
        User user1 = userMapper.selectById(1);
        System.out.println(user1);

        //System.out.println(userMapper.insertUser(user1));
    }

    @Test
    public void test5(){
        User liubei = userMapper.selectByName("liubei");
        System.out.println(liubei);
    }

    @Test
    public void test6(){
        User user1 = new User(null,"Tom111","ssss","fom","dsad",1,1,"hjuf","ihu", new Date());
        userMapper.insertUser(user1);
    }

    @Test
    public void test7(){
        System.out.println(userMapper.selectByEmail("nowcoder120@sina.com"));
    }

    @Test
    public void test8(){
        userMapper.updateStatus(151,0);
        userMapper.updateHeaderUrl(151,"newheadUrl");
        userMapper.updatePassword(151,"newPassword");
    }

    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Test
    public void test9(){
        List<DiscussPost> discussPosts = discussPostMapper.selectDiscussPost(149, 0, 10);
        System.out.println(discussPosts);
    }
    
    @Test
    public void test10(){
        System.out.println(discussPostMapper.selectDiscussPostRows(101));
    }




    @Autowired
    private DiscussPostService discussPostService;
    @Test
    public void test11(){
        discussPostService.page(1,5,null);
    }



}
