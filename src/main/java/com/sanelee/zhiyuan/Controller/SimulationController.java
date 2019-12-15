package com.sanelee.zhiyuan.Controller;

import com.sanelee.zhiyuan.Mapper.UserMapper;
import com.sanelee.zhiyuan.Model.GaoKao;
import com.sanelee.zhiyuan.Model.User;
import com.sanelee.zhiyuan.Model.UserExample;
import com.sanelee.zhiyuan.Service.GaoKaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class SimulationController {
    @Autowired
    private GaoKaoService gaoKaoService;
    @Autowired
    private UserMapper userMapper;

    @RequestMapping("simulation")
    public String simulation(HttpServletRequest request,Model model){
//        User user = (User)request.getSession().getAttribute("loginUser");
//        model.addAttribute("user",user);
        return "simulation";
    }
    @GetMapping("/simulationResult")
    public String major(Map<String,Object> map,
                        HttpServletRequest request,
                        @RequestParam(name="score",required = false) Integer score,
                        Model model) {
        if (score != null) {
            User user = (User)request.getSession().getAttribute("loginUser");
            List<GaoKao> gaokaoList = gaoKaoService.gaokaoQuery(score, user.getUserarea(), user.getUsersort());
            model.addAttribute("gaokao", gaokaoList);
            return "simulationResult";
        }else {
            map.put("msg","分数不能为空!");
            return "simulation";
        }
    }
}
