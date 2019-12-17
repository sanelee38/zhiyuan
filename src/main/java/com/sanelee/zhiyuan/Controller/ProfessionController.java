package com.sanelee.zhiyuan.Controller;

import com.sanelee.zhiyuan.DTO.PaginationDTO;
import com.sanelee.zhiyuan.Mapper.ProfessionExtMapper;
import com.sanelee.zhiyuan.Model.Profession;
import com.sanelee.zhiyuan.Model.ProfessionExample;
import com.sanelee.zhiyuan.Service.ProfessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class ProfessionController {
    @Autowired
    private ProfessionService professionService;
    @Autowired
    private ProfessionExtMapper professionExtMapper;

    @RequestMapping("/profession")
    public String Profession(Model model,
                             @RequestParam(name = "page",defaultValue = "1") Integer page,
                             @RequestParam(name = "size",defaultValue = "10") Integer size,
                             @RequestParam(name = "major",required = false) String major,
                             @RequestParam(name = "search",required = false) String search,
                             @RequestParam(name = "subject",required = false) String subject,
                             Map<String,Object> map){
        PaginationDTO pagination = professionService.list(page,size,major,search,subject);

        ProfessionExample example1 = new ProfessionExample();
        example1.setDistinct(true);
        example1.setOrderByClause("pid asc");
        List<Profession> majorList = professionExtMapper.selectMajorByExample(example1);
        List<Profession> subjectListByMajor = professionService.subjectList(major);
        map.put("majorinfo",subjectListByMajor.get(0));


        model.addAttribute("pagination",pagination);
        model.addAttribute("search",search);
        model.addAttribute("majorList",majorList);
        model.addAttribute("subjectList",subjectListByMajor);
        model.addAttribute("major",major);
        model.addAttribute("subject",subject);

        return "profession";
    }
}
