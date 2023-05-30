package com.nowcoder.community.controller;

import com.nowcoder.community.service.AlphaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * ClassName: AlphaController
 * Package: com.nowcoder.community.controller
 * Description:
 *
 * @Author CJ
 * @Create 2023/5/28 17:45
 * @Version 1.0
 */


@RestController
@RequestMapping("/alpha")
public class AlphaController {
    @Autowired
    private AlphaService alphaService;

    @RequestMapping("/hello")
    public String sayHello(){
        return "hello Spring Boot";
    }

    @RequestMapping("/find")
    public String find(){
        return alphaService.find();
    }

    //获取请求、响应体
    @RequestMapping("/http")
    public void http(HttpServletRequest request, HttpServletResponse response){
        //获取请求数据
        System.out.println(request.getMethod());
        System.out.println(request.getServletPath());
        Enumeration<String> enumeration = request.getHeaderNames();
        while(enumeration.hasMoreElements()){
            String name = enumeration.nextElement();
            String value = request.getHeader(name);
            System.out.println(name + ":" + value);
        }
        System.out.println(request.getParameter("code"));

        //返回响应数据
        response.setContentType("text/html;charset=utf-8");
        try(PrintWriter writer = response.getWriter();
        ) {
            writer.write("<h1>牛客网</h1>");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // 显示变量/students?current=1,limit=10
    @GetMapping("/students")
    public String getStudents(
            @RequestParam(name = "current",required = false,defaultValue = "1") Integer currentNow,
            @RequestParam(name = "limit",required = false,defaultValue = "10") Integer limitNow){
        System.out.println("current = " + currentNow);
        System.out.println("limit = " + limitNow);
        return "some students";
    }

    //路径变量 /student/3
    @GetMapping("/student/{id}")
    public String getStudent(@PathVariable("id") Integer id){
        return id + "student";
    }

    //post请求
    @PostMapping("/student")
    public String saveStudent(String name,Integer age){
        System.out.println(name);
        System.out.println(age);
        return "success";
    }


    //方式1
    @GetMapping("/teacher")
    public ModelAndView getTeacher(){
        ModelAndView mav = new ModelAndView();
        mav.addObject("name","航医");
        mav.addObject("age",30);
        mav.setViewName("demo/view");
        return mav;
    }

    //方式2

    @GetMapping("/school")
    public String getSchool(Model mod){
        mod.addAttribute("name","北大");
        mod.addAttribute("age","150");
        return "/demo/view.html";
    }


    @GetMapping("/emp")
    public Map<String,Object> getEmp(){
        Map<String,Object> empMap = new HashMap<>();
        empMap.put("name","张三");
        empMap.put("age",13);
        empMap.put("salary",800.00);
        return empMap;
    }

    @GetMapping("/emplist")
    public List<Map<String,Object>> getEmps(){
        List<Map<String,Object>> empList = new ArrayList<>();

        Map<String,Object> empMap = new HashMap<>();
        empMap.put("name","张三");
        empMap.put("age",13);
        empMap.put("salary",800.00);
        empList.add(empMap);

        Map<String,Object> empMap2 = new HashMap<>();
        empMap2.put("name","lucy");
        empMap2.put("age",28);
        empMap2.put("salary",1000.00);
        empList.add(empMap2);

        return empList;
    }




}

















