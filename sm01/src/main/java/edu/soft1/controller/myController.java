package edu.soft1.controller;

import edu.soft1.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
@RequestMapping(value = "/param")
public class myController {

//    @RequestMapping("/hello")
//    public String hello(){
//        System.out.println("--hello--");
//        return "hello";
//    }
//    @RequestMapping(value = "/hello")
//    public ModelAndView hello(){
//        System.out.println("--hello--");
//        ModelAndView mav = new ModelAndView();
//        mav.setViewName("hello");
//        mav.addObject("msg","I'm peter!");
//        return mav;
//    }
//
//
//    @RequestMapping(value = "param1",method = {RequestMethod.GET})
//    public String param1(HttpServletRequest request){
//        //接收client发来的数据
//        String name = request.getParameter("name");//获取数据
//        System.out.println("name="+name);
//        request.setAttribute("name",name);//将数据存入request
//        //调用业务层的方法
//        //页面跳转
//        return "hello";
//    }
//    @RequestMapping(value = "param2",method = {RequestMethod.GET,RequestMethod.POST})
//    public String param2(HttpServletRequest request,HttpSession session){
//        //接收client发来的数据
//        String name = request.getParameter("username");//获取数据
//        String age = request.getParameter("age");//获取数据
//        System.out.println("age="+age+"name="+name);//打印数据
//        session.setAttribute("age",age);
//        request.setAttribute("name",name);//将数据存入request
//        //调用业务层的方法
//        //页面跳转
//        return "hello";
//    }
//    @RequestMapping(value = "param3",method = {RequestMethod.POST})
//    public String param3(String username,int age){//数据名与方法参数相同
//        System.out.println("-----param3()-----");
//        System.out.println("username="+username);
//        System.out.println("age="+age);
//        return "hello";
//    }
//
//    @RequestMapping(value = "param4",method = {RequestMethod.POST})
//    public String param4(@RequestParam(value = "username",required = false) String u, @RequestParam(value = "age",defaultValue = "18")int a){//数据名与方法参数不同
//        System.out.println("-----param4()-----");
//        System.out.println("username="+u);
//        System.out.println("age="+a);
//        return "redirect:hello";//url:/WEB-INF/jsp/hello.jsp(默认为转发跳转)
//    }
//    @RequestMapping(value = "test")
//    public String test(){
//        System.out.println("--test--");
//        return "hello";
//    }
//    @RequestMapping(value = "/reg")
//    public String reg(User user){//数据名与方法参数相同
//        System.out.println("username="+user.getUsername());
//        System.out.println("pwd="+user.getAge());
//        System.out.println("birthday="+user.getBirthday());
//        System.out.println("city="+user.getAddress().getCity());
//        System.out.println("street="+user.getAddress().getStreet());
//        System.out.println("phone="+user.getAddress().getPhone());
//        return "redirect:/param/test";
//    }
//    @RequestMapping(value = "param5",method = {RequestMethod.POST})
//    public String param5(User user,HttpSession session){//数据名与方法参数不同
//        System.out.println("-----param4()-----");
//        System.out.println("username="+user.getUsername());
//        System.out.println("age="+user.getAge());
//        session.setAttribute("name",user.getUsername());
//        return "redirect:hello";//url:/WEB-INF/jsp/hello.jsp(默认为转发跳转)
//    }

}
