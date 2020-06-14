package com.sanelee.zhiyuan.Controller;

import com.sanelee.zhiyuan.Mapper.UserExtMapper;
import com.sanelee.zhiyuan.Mapper.UserMapper;
import com.sanelee.zhiyuan.Model.User;
import com.sanelee.zhiyuan.Model.UserExample;
import com.sanelee.zhiyuan.Util.MD5Util;
import com.sanelee.zhiyuan.Util.PhoneCode;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/front/*")
public class indexController {
    @Autowired
    private UserExtMapper userExtMapper;

    @Autowired
    private UserMapper userMapper;

    @Data
    class UserInfo{
        private String phone;
        private String code;
    }

    @GetMapping("/")
    public String index(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie:cookies){
            if (cookie.getName().equals("token")){
                String token = cookie.getValue();
                User user = userExtMapper.findByToken(token);
                if (user != null){
                    request.getSession().setAttribute("user",user);
                }
            }
        }
        return "index";
    }

    //注册页面
    @GetMapping("/register")
    public String register(){
        return "register";
    }

    //登录页面
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    //手机登录页面
    @GetMapping("/sendcode")
    public String sendcode(){
        return "sendcode";
    }

    //验证码页面
    @GetMapping("/phonelogin")
    public String phonelogin(){
        return "phonelogin";
    }

    //注册方法
    @RequestMapping("/addregister")
    public String register(HttpServletRequest request, Map<String,Object> map,Model model){
        String username = request.getParameter("username");
        String userPhone = request.getParameter("userPhone");
        String userArea = request.getParameter("userArea");
        String userSort = request.getParameter("userSort");
        String password = request.getParameter("password");
        String password2 = request.getParameter("password2");

        if (userArea.equals("暂无")){
            map.put("msg","请选择考生所在省份!");
            return "register";
        }
        else if (userSort.equals("暂无")){
            map.put("msg","请选择考生文理科!");
            return "register";
        }
        UserExample userExample1 = new UserExample();
        UserExample userExample2 = new UserExample();
        userExample1.createCriteria()
                .andUsernameEqualTo(username);
        userExample2.createCriteria()
                .andUserphoneEqualTo(userPhone);
        List<User> users1 = userMapper.selectByExample(userExample1);
        List<User> users2 = userMapper.selectByExample(userExample2);
        if (password.equals(password2)&&users1.size()==0&&users2.size()==0){
            User user = new User();
            user.setUsername(username);
            user.setUserphone(userPhone);
            user.setUserarea(userArea);
            user.setUsersort(userSort);
            String pwd = MD5Util.string2MD5(password);
            user.setPassword(pwd);
            userMapper.insertSelective(user);
            System.out.println(username);
            System.out.println(userPhone);
            map.put("msg","注册成功,请登录！");
            return "login";
        }
        else if(users1.size()!=0){
            map.put("msg","该用户名已存在！");
            return "register";
        }else if(users2.size()!=0){
            map.put("msg","该手机号已经注册！");
            return "register";
        }
        else {
            map.put("msg","密码不一致或用户名已存在！");
            return "register";
        }
    }

    //登陆方法
    @RequestMapping("/addlogin")
    public String addlogin(HttpServletRequest request,
                           HttpServletResponse response,
                           Map<String,Object> map,
                           Model model){
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String pwd = MD5Util.string2MD5(password);
        HttpSession session = request.getSession();


        User user=userExtMapper.loginquery(username,pwd);

        if (user !=null){
            String token = UUID.randomUUID().toString();
            User updateUser = new User();
            updateUser.setToken(token);
            UserExample example = new UserExample();
            example.createCriteria().andUsernameEqualTo(user.getUsername());
            userMapper.updateByExampleSelective(updateUser, example);
            response.addCookie(new Cookie("token",token));
            session.setAttribute("loginUser",user);
            session.setAttribute(user.getUsername(),token);
            map.put("msg","登陆成功");
            model.addAttribute("user",username);
            System.out.println(session.getId());
            System.out.println(token);
            return "redirect:/";
        }else {
            map.put("msg","密码或账号错误！");
            return "login";
        }
    }

    @RequestMapping("/sendcode")
    public String sendcode(HttpServletRequest request,Map<String,Object> map){
        String phone = request.getParameter("phonenumber");
        User user = userExtMapper.selectByPhone(phone);
        if (user != null){
            HttpSession session = request.getSession();
            String code = PhoneCode.vcode();
            UserInfo userInfo = new UserInfo();
            userInfo.setPhone(phone);
            userInfo.setCode(code);
            String sms = PhoneCode.getPhonemsg(phone,code);
            if (sms.equals("-1")){
                map.put("msg","获取验证码失败,请稍后重试或联系管理员！");
                return "sendcode";
            }
            session.setAttribute("userInfo",userInfo);
            return "phonelogin";
        }else {
            map.put("msg","手机号未注册，请先注册！");
            return "sendcode";
        }

    }

    @RequestMapping("/phonelogin")
    public String phonelogin(HttpServletRequest request,
                           HttpServletResponse response,
                           Map<String,Object> map,
                           Model model){
        HttpSession session = request.getSession();
        String code = request.getParameter("code");
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
        if (!code.equals(userInfo.getCode())){
            map.put("msg","验证码输入错误！");
            return "phonelogin";
        }else {
            User user = userExtMapper.selectByPhone(userInfo.getPhone());
            session.setAttribute("loginUser",user);
            map.put("msg","登陆成功");
            return "redirect:/";
        }
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request){
        request.getSession().removeAttribute("loginUser");
        return "redirect:/";
    }
}
