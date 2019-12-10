package com.sanelee.zhiyuan.Controller;

import com.sanelee.zhiyuan.Mapper.SchoolMapper;
import com.sanelee.zhiyuan.Mapper.SchoolScoreExtMapper;
import com.sanelee.zhiyuan.Model.School;
import com.sanelee.zhiyuan.Model.SchoolScore;
import com.sanelee.zhiyuan.Model.SchoolScoreExample;
import com.sanelee.zhiyuan.Service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SchoolHomepageController {
    @Autowired
    private SchoolMapper schoolMapper;
    @Autowired
    private SchoolScoreExtMapper schoolScoreExtMapper;

    @RequestMapping("/schoolHomepage/{scid}")
    public String schoolHomepage(@PathVariable(name = "scid") Integer scid,
                                 @RequestParam(name = "sort",defaultValue = "1") Integer sort,
                                 Model model){
        School school = schoolMapper.selectByPrimaryKey(scid);
        List<SchoolScore> schoolScore = schoolScoreExtMapper.selectByScidSort(scid,sort);
        model.addAttribute("schools",school);
        model.addAttribute("schoolscores",schoolScore);
        return "schoolHomepage";
    }
}
