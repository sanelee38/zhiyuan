package com.sanelee.zhiyuan.Controller;

import com.sanelee.zhiyuan.Mapper.GaoKaoExtMapper;
import com.sanelee.zhiyuan.Mapper.ProfessionExtMapper;
import com.sanelee.zhiyuan.Mapper.ProfessionMapper;
import com.sanelee.zhiyuan.Mapper.SchoolExtMapper;
import com.sanelee.zhiyuan.Model.GaoKao;
import com.sanelee.zhiyuan.Model.Profession;
import com.sanelee.zhiyuan.Model.School;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class HighSearchController {

    @Autowired
    private ProfessionMapper professionMapper;
    @Autowired
    private ProfessionExtMapper professionExtMapper;
    @Autowired
    private GaoKaoExtMapper gaoKaoExtMapper;

    @RequestMapping("/highSearch")
    public String highSearch(Model model){
        List<Profession> professionList = professionExtMapper.selectProfession();
        model.addAttribute("professionList",professionList);
        return "highSearch";
    }
    @RequestMapping(value = "/search",method= RequestMethod.POST)
    public String search(Model model,
                         HttpServletRequest request,
                         @RequestParam(name = "area",required = false) String area,
                         @RequestParam(name = "profession",required = false) String profession,
                         @RequestParam(name = "type",required = false) Integer type
                         ){
        Integer is211=null;
        Integer is985=null;
        Integer isDoubleFirstClass=null;
        if (type==985){
            is985=985;
        }else if(type==211){
            is211=211;
        }else if(type==1){
            isDoubleFirstClass=1;
        }else {
            is211=null;
            is985=null;
            isDoubleFirstClass=null;
        }
        List<GaoKao> schoolSearchList = gaoKaoExtMapper.schoolHighSearch(area,profession,is211,is985,isDoubleFirstClass);
        List<Profession> professionList = professionExtMapper.selectProfession();
        model.addAttribute("professionList",professionList);
        model.addAttribute("schoolList",schoolSearchList);
        model.addAttribute("area",area);
        model.addAttribute("profession",profession);
        model.addAttribute("type",type);
        return "highSearch";
    }
}
