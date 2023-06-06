package com.nowcoder.community;

import com.nowcoder.community.dao.CommentMapper;
import com.nowcoder.community.dao.DiscussPostMapper;
import com.nowcoder.community.dao.LoginTicketMapper;
import com.nowcoder.community.dao.UserMapper;
import com.nowcoder.community.entity.*;
import com.nowcoder.community.service.imp.CommentServiceImp;
import com.nowcoder.community.service.imp.DisscussPostServiceImp;
import com.nowcoder.community.util.MailClient;
import com.nowcoder.community.util.SensitiveFilter;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.MailMessage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
class CommunityApplicationTests {
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
    public void test3() {
        User user1 = userMapper.selectById(1);
        System.out.println(user1);

        //System.out.println(userMapper.insertUser(user1));
    }

    @Test
    public void test5() {
        User liubei = userMapper.selectByName("liubei");
        System.out.println(liubei);
    }

    @Test
    public void test6() {
        User user1 = new User(null, "Tom111", "ssss", "fom", "dsad", 1, 1, "hjuf", "ihu", new Date());
        userMapper.insertUser(user1);
    }

    @Test
    public void test7() {
        System.out.println(userMapper.selectByEmail("nowcoder120@sina.com"));
    }

    @Test
    public void test8() {
        userMapper.updateStatus(151, 0);
        userMapper.updateHeaderUrl(151, "newheadUrl");
        userMapper.updatePassword(151, "newPassword");
    }

    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Test
    public void test9() {
        List<DiscussPost> discussPosts = discussPostMapper.selectDiscussPost(149, 0, 10);
        System.out.println(discussPosts);
    }

    @Test
    public void test10() {
        System.out.println(discussPostMapper.selectDiscussPostRows(101));
    }


    @Autowired
    private DisscussPostServiceImp discussPostService;

    @Test
    public void test11() {
        PageBean page = discussPostService.page(2, 5, 111);
        System.out.println(page);
    }

    @Autowired
    private MailClient mailClient;

    @Autowired(required = false)
    private TemplateEngine templateEngine;

    @Test
    public void test12() {
        mailClient.sendMail("cjiong1999@163.com", "test", "Welcome");
    }

    @Test
    public void test13() {
        Context contex = new Context();
        contex.setVariable("username", "CJ");

        String content = templateEngine.process("/mail/demo", contex);
        System.out.println(content);
        //mailClient.sendMail("cjiong1999@163.com","test",content);
    }

    @Autowired
    private LoginTicketMapper loginTicketMapper;

    @Test
    public void test14() {
        LoginTicket loginTicket1 =
                new LoginTicket(null, 101, "abc123", 0, new Date());

        loginTicketMapper.insertLoginTicket(loginTicket1);
    }

    @Test
    public void test15(){
        LoginTicket loginTicket = loginTicketMapper.selectByTicket("abc123");
        System.out.println(loginTicket);
        System.out.println(loginTicket.getTicket());

        System.out.println();
        loginTicketMapper.updateStatus(loginTicket.getTicket(),1);
        System.out.println(loginTicket.getTicket());

    }

    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Test
    public void test16(){
        String input = "abc123";
        String input2 = "这里可以吸毒、高考";
        String filter1 = sensitiveFilter.filter(input);
        String filter2 = sensitiveFilter.filter(input2);
        System.out.println(filter1);
        System.out.println(filter2);
    }


    @Autowired
    private CommentMapper commentMapper;

    @Test
    public void test17(){
        Comment comment = commentMapper.selectCommentById(36);
        System.out.println(comment);
    }

    @Test
    public void test18(){
        Comment comment1 = new Comment(null,158,2,
                280,157,"280号帖子 158回复157",0,new Date());

        commentMapper.insertComment(comment1);
    }

    @Test
    public void test19(){
        List<Comment> comments = commentMapper.selectCommentByEntity(1, 280);
        for(Comment c:comments){
            System.out.println(c);
        }
    }

    @Test
    public void test20(){
        System.out.println(commentMapper.selectCountByEntity(1, 280));
    }

    @Autowired
    private CommentServiceImp commentServiceImp;

    @Test
    public void test21(){
        PageBean commentBean = commentServiceImp.findCommentByEntity
                (1, 5, 1, 280);

        System.out.println(commentBean);
    }

    @Test
    public void test22(){
        System.out.println(commentServiceImp.findCommentCount(1, 280));
    }

}
