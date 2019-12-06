package com.sanelee.zhiyuan.Controller;

import com.sanelee.zhiyuan.Mapper.SchoolMapper;
import com.sanelee.zhiyuan.Model.School;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SchoolHomepageController {
    @Autowired
    private SchoolMapper schoolMapper;

    @RequestMapping("/schoolHomepage/{scid}")
    public String schoolHomepage(@PathVariable(name = "scid") Integer scid,
                                 Model model){
        School school = schoolMapper.selectByPrimaryKey(scid);
        model.addAttribute("schools",school);
        return "schoolHomepage";
    }
}
