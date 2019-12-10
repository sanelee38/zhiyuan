package com.sanelee.zhiyuan.Controller;

import com.sanelee.zhiyuan.DTO.PaginationDTO;
import com.sanelee.zhiyuan.Mapper.*;
import com.sanelee.zhiyuan.Model.*;
import com.sanelee.zhiyuan.Service.GaoKaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class ProfessionHomepageController {
    @Autowired
    private ProfessionMapper professionMapper;
    @Autowired
    private ProfessionExtMapper professionExtMapper;
    @Autowired
    private GaoKaoExtMapper gaoKaoExtMapper;
    @Autowired
    private GaoKaoService gaoKaoService;

    @RequestMapping("/professionHomepage/{proname}")
    public String professionHomepage(Model model,
                                    @RequestParam(name = "page",defaultValue = "1") Integer page,
                                    @RequestParam(name = "size",defaultValue = "5") Integer size,
                                    @PathVariable(name = "proname") String proname,
                                     Map<String,Object> map){
        Profession profession = professionExtMapper.selectByProname(proname);

        PaginationDTO pagination = gaoKaoService.list(page,size,proname);

        model.addAttribute("profession",profession);
        model.addAttribute("professionSchool",pagination);
        model.addAttribute("proname",proname);

        model.addAttribute("size",size);
        return "professionHomepage";
    }
}
