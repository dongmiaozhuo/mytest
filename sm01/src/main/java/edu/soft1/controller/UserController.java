package edu.soft1.controller;

import edu.soft1.pojo.User;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileSystemUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @RequestMapping(value = "hello")
    public String Hello(){
        System.out.println("---hello()---");
        return "hello";
    }

    @RequestMapping(value = "/login")
    public String login(User user, HttpSession session, HttpServletRequest request){
        System.out.println("---login()---");
        int flag =1;//调用业务层,获取flag的值
        if ( flag== 1) {
            session.setAttribute("user",user);//登录对象放入session
            return "welcome";//登录成功
        }
        System.out.println("登录失败，返回首页");
        request.setAttribute("error","用户名或密码不正确");
        return "forward:/index.jsp";//登录失败
//        return "redirect:/index.jsp";//登录失败
    }


    @RequestMapping(value = "/reg")
    public String reg(User user){//数据名与方法参数相同
        System.out.println("username="+user.getUsername());
        System.out.println("pwd="+user.getPwd());
        System.out.println("age="+user.getAge());
        System.out.println("birthday="+user.getBirthday());
        System.out.println("city="+user.getAddress().getCity());
        System.out.println("street="+user.getAddress().getStreet());
        System.out.println("phone="+user.getAddress().getPhone());
        return "hello";
    }
//    被拦截的方法
    @RequestMapping(value = "/delete")
    public String delete(HttpServletRequest request){
        System.out.println("---delete()---");
        request.setAttribute("CRUDmsg","删除功能执行完毕");
        return "hello";
    }

    /**
     * 单文件上传
     * @param image
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping(value="/upload.do",method={RequestMethod.POST})
    public String fileUpload(MultipartFile image, HttpServletRequest request) throws IOException {
        //获取文件的输入流对象
        InputStream is=image.getInputStream();
        //获取文件的真实名字
        String filename = image.getOriginalFilename();
        //在webapp文件夹下，新建一个images的文件夹，作为上传文件的存储位置，这里获取它的真实路径
        String realPath = request.getServletContext().getRealPath("/images");
        System.out.println("上传路径realPath="+realPath);
        //根据images文件夹的真实路径和文件的名字创建输出流对象
        OutputStream os = new FileOutputStream(new File(realPath, doFileName(filename)));
        //把输入流数据写入输出流,完成文件的上传
        int res = IOUtils.copy(is, os);
        //释放资源，原则：先开后关，后开先关
        os.close();
        is.close();
        System.out.println("上传文件size="+res+"Byte");
        return "welcome";
    }
//    private String doFileName(String fileName){
//        //获取文件名的后缀
//        String extension  = FilenameUtils.getExtension(fileName);
//        //获取uuid字符串，避免文件名重复。
//        String uuid = UUID.randomUUID().toString();
//        System.out.println("上传文件名称="+uuid);
//        return uuid+"."+extension;
//    }

    /**
     * 多文件上传
     * @param images
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping(value="/upload2.do",method={RequestMethod.POST})
    public String fileUpload2(MultipartFile[] images, HttpServletRequest request, Map<String,Object> map) throws IOException{
        InputStream is = null; OutputStream os = null;
        int count = 0;
        for (MultipartFile image :images) {
            is = image.getInputStream();//获取文件的输入流对象
            String filename = image.getOriginalFilename();//获取文件的真实名字
            System.out.println("文件原名称="+filename);
            if (filename.equals("")) {
                System.out.println("空字符串，进入下一轮循环");
                continue;//进入下一轮循环
            }
            String realPath = request.getServletContext().getRealPath("/images");
            System.out.println("上传路径realPath="+realPath);
            //根据images文件夹的真实路径和文件的名字创建输出流对象
            os = new FileOutputStream(new File(realPath,doFileName(filename)));
            int size= IOUtils.copy(is, os);//把输入流数据写入输出流,完成文件的上传
            if(size>0){
                count++;
            }
        }
        os.close(); is.close();//释放资源，原则：先开后关，后开先关
        map.put("msg2",count);
        System.out.println("上传成功"+count+"张图片");
        return "welcome";
    }

    private String doFileName(String fileName){
        //获取文件名的后缀
        String extension  = FilenameUtils.getExtension(fileName);
        //获取uuid字符串，避免文件名重复。
        String uuid = UUID.randomUUID().toString();
        System.out.println("上传文件名称="+uuid);
        return uuid+"."+extension;
    }
    @RequestMapping(value="/load.do/{fileName}",method={RequestMethod.GET})
    public void load(@PathVariable String fileName, HttpServletRequest request, HttpServletResponse response)
            throws IOException{
        //设置文件下载
        response.setHeader("Content-Disposition","attachment;filename="+doFileName2(request, fileName));
        //文件存储的真实位置
        String realPath = request.getServletContext().getRealPath("/images");
        System.out.println("下载路径realPath="+realPath);
        //根据存储的文件，获取输入流对象
        InputStream is = new FileInputStream(new File(realPath, fileName));
        //根据相应对象获取输出流对象
        OutputStream os = response.getOutputStream();
        //把输入流写入输出流
        IOUtils.copy(is, os);
        //释放资源，原则：先开后关，后开先关
        os.close();
        is.close();
    }
    //针对中文名称，需要分浏览器来处理
    public String doFileName2(HttpServletRequest request, String filename){
        try{
            //获取请求头部信息的User-Agent对应的值
            String userAgent=request.getHeader("User-Agent");
            if(userAgent.toUpperCase().indexOf("FIREFOX")>0){//火狐浏览器
                filename= "=?UTF-8?B?"+(new String(Base64.encodeBase64(filename.getBytes("utf-8"))))+"?=";
            }else{//其他浏览器
                filename  = URLEncoder.encode(filename,"utf-8");
            }
            System.out.println("下载文件名="+filename);
        }catch(Exception e){
            e.printStackTrace();
        }
        return filename;
    }
    @RequestMapping(value = "/welcome")
    public String welcome(){
        System.out.println("---welcome()---");
        return "welcome";
    }
   @RequestMapping(value = "/logout")
    public String logout(HttpSession session){
        session.invalidate();
       System.out.println("已登出~");
        return "redirect:/index.jsp";
   }

}
