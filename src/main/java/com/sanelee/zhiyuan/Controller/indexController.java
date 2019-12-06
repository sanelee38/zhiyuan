package com.sanelee.zhiyuan.Controller;

import com.sanelee.zhiyuan.Mapper.UserMapper;
import com.sanelee.zhiyuan.Model.User;
import com.sanelee.zhiyuan.Model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class indexController {
    @Autowired
    private UserMapper userMapper;
    
    @GetMapping("/index")
    public String index(HttpServletRequest request){
        return "index";
    }

    //注册页面
    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    //注册方法
    @RequestMapping("/addregister")
    public String register(HttpServletRequest request, Map<String,Object> map){
        String username = request.getParameter("username");
        String userPhone = request.getParameter("userPhone");
        String password = request.getParameter("password");
        String password2 = request.getParameter("password2");
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
            user.setPassword(password);
            userMapper.insertSelective(user);
            System.out.println(username);
            System.out.println(userPhone);
            map.put("msg","注册成功,请登录！");
            return "login";
        }else if(userPhone.equals("")){
            map.put("msg","请输入手机号！");
            return "register";
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
    @RequestMapping(value = "/addlogin",method = RequestMethod.POST)
    public String addlogin(HttpServletRequest request,
                           HttpServletResponse response,
                           Map<String,Object> map,
                           Model model){
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();
        UserExample userExample = new UserExample();
        userExample.createCriteria().
                andUsernameEqualTo(username).
                andPasswordEqualTo(password);
        List<User> users = userMapper.selectByExample(userExample);
        User user = users.get(0);
        if (user !=null){
            String token = UUID.randomUUID().toString();
            User updateUser = new User();
            updateUser.setToken(token);
            UserExample example = new UserExample();
            example.createCriteria().andUsernameEqualTo(user.getUsername());
            userMapper.updateByExampleSelective(updateUser, example);
            response.addCookie(new Cookie("token",token));
            session.setAttribute("loginUser",user);
            map.put("msg","登陆成功");
            model.addAttribute("user",username);
            return "redirect:/";
        }else {
            map.put("msg","密码或账号错误！");
            return "login";
        }
    }
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request){
        request.getSession().removeAttribute("loginUser");
        return "redirect:/";
    }
}
