package com.sanelee.zhiyuan.Controller;

import com.sanelee.zhiyuan.Mapper.UserExtMapper;
import com.sanelee.zhiyuan.Mapper.UserMapper;
import com.sanelee.zhiyuan.Model.User;
import com.sanelee.zhiyuan.Model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserExtMapper userExtMapper;
    //定义页面
    @RequestMapping("/userBasicInformation")
    public String userBasicInformation(HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("loginUser");
        User userIdentify = userExtMapper.findByUserNameAndUserPhone(user.getUserrealname(),user.getUserphone());
        if (userIdentify==null){
            return "userBasicInformation";
        }
        else {
            return "reportZhiyuan";
        }

    }

    @RequestMapping(value="/adduserBasicInformation",method= RequestMethod.POST)
    public String userBasicInformation(Map<String,Object> map,
                                       @RequestParam(name="userRealname",required = false) String userRealname,
                                       @RequestParam(name="userGender",required = false) String userGender,
                                       @RequestParam(name="userPhone",required = false) String userPhone,
                                       @RequestParam(name="userWechat",required = false) String userWechat,
                                       @RequestParam(name="userScore",required = false) Integer userScore,
                                       @RequestParam(name="userRank",required = false) Integer userRank,
                                       @RequestParam(name="userArea",required = false) String userArea,
                                       @RequestParam(name="userSort",required = false) String userSort){

        User userIdentify = userExtMapper.findByUserNameAndUserPhone(userRealname,userPhone);
        if (userIdentify==null&&userRealname!=("")&&userPhone!=("")&&userWechat!=("")&&userScore!=null&&userRank!=null){
            User user= new User();
            user.setUserrealname(userRealname);
            user.setUsergender(userGender);
            user.setUserphone(userPhone);
            user.setUserwechat(userWechat);
            user.setUserscore(userScore);
            user.setUserrank(userRank);
            user.setUserarea(userArea);
            user.setUsersort(userSort);

            userExtMapper.saveUser(user);
            return "reportZhiyuan";
        }
        else if (userIdentify!=null){

            return "reportZhiyuan";
        }
        else if(userRealname.equals("")){
            map.put("msg","姓名不能为空！");
            return "userBasicInformation";
        }
        else if(userPhone.equals("")){
            map.put("msg","电话不能为空！");
            return "userBasicInformation";
        }
        else if(userWechat.equals("")){
            map.put("msg","微信号不能为空！");
            return "userBasicInformation";
        }
        else if(userScore==null){
            map.put("msg","分数不能为空！");
            return "userBasicInformation";
        }
        else if(userRank==null){
            map.put("msg","位次不能为空！");
            return "userBasicInformation";
        }
        else {
            map.put("msg","信息填写错误，请检查无误后重新提交！");
            return "userBasicInformation";
        }


    }
}