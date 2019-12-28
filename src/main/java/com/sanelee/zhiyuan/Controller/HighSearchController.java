package com.sanelee.zhiyuan.Controller;

import com.sanelee.zhiyuan.DTO.PaginationDTO;
import com.sanelee.zhiyuan.Mapper.GaoKaoExtMapper;
import com.sanelee.zhiyuan.Mapper.ProfessionExtMapper;
import com.sanelee.zhiyuan.Mapper.ProfessionMapper;
import com.sanelee.zhiyuan.Mapper.SchoolExtMapper;
import com.sanelee.zhiyuan.Model.GaoKao;
import com.sanelee.zhiyuan.Model.Profession;
import com.sanelee.zhiyuan.Model.School;
import com.sanelee.zhiyuan.Service.HighSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
public class HighSearchController {

    @Autowired
    private ProfessionMapper professionMapper;
    @Autowired
    private ProfessionExtMapper professionExtMapper;
    @Autowired
    private GaoKaoExtMapper gaoKaoExtMapper;
    @Autowired
    private HighSearchService highSearchService;

    @RequestMapping("/highSearch")
    public String highSearch(Model model){
        List<Profession> professionList = professionExtMapper.selectProfession();
        model.addAttribute("professionList",professionList);
        return "highSearch";
    }
    @RequestMapping(value = "/search",method= RequestMethod.POST)
    public String search(Model model,
                         HttpServletRequest request,
                         Map<String,Object> map,
                         @RequestParam(name = "area",required = false) String area,
                         @RequestParam(name = "profession",required = false) String profession,
                         @RequestParam(name = "type",required = false) Integer type
                         ){

        if (profession.equals("null")){
            map.put("msg","专业为必选项");
            List<Profession> professionList = professionExtMapper.selectProfession();
            model.addAttribute("professionList", professionList);
            return "highSearch";
        }
        else{
            List<GaoKao> schoolSearchList = highSearchService.schoolHighSearch(area, profession, type);
            List<Profession> professionList = professionExtMapper.selectProfession();
            model.addAttribute("professionList", professionList);
            model.addAttribute("schoolList", schoolSearchList);
            model.addAttribute("area", area);
            model.addAttribute("profession", profession);
            model.addAttribute("type", type);
            return "highSearch";
        }
    }
}
