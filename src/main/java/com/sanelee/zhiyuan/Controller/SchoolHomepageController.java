package com.sanelee.zhiyuan.Controller;

import com.sanelee.zhiyuan.Mapper.GaoKaoMapper;
import com.sanelee.zhiyuan.Mapper.SchoolExtMapper;
import com.sanelee.zhiyuan.Mapper.SchoolMapper;
import com.sanelee.zhiyuan.Mapper.SchoolScoreExtMapper;
import com.sanelee.zhiyuan.Model.*;
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
    private SchoolExtMapper schoolExtMapper;
    @Autowired
    private SchoolScoreExtMapper schoolScoreExtMapper;
    @Autowired
    private GaoKaoMapper gaoKaoMapper;

    @RequestMapping("/schoolHomepage/{scid}")
    public String schoolHomepage(@PathVariable(name = "scid") Integer scid,
                                 @RequestParam(name = "province",defaultValue = "陕西") String province,
                                 @RequestParam(name = "sort",defaultValue = "1") Integer sort,
                                 Model model){
        School school = schoolMapper.selectByPrimaryKey(scid);
        List<SchoolScore> schoolScore = schoolScoreExtMapper.selectByScidSort(scid,sort);
        SchoolExample example1 = new SchoolExample();
        example1.setDistinct(true);
        example1.setOrderByClause("areaid asc");
        List<School> areaList = schoolExtMapper.selectAreaByExample(example1);

        GaoKaoExample example = new GaoKaoExample();

        example.createCriteria().andScidEqualTo(scid).andLocalProvinceNameEqualTo(province).andLocalTypeNameEqualTo(sort).andYearEqualTo(2018);
        List<GaoKao> gaoKao = gaoKaoMapper.selectByExample(example);
        model.addAttribute("schools",school);
        model.addAttribute("schoolscores",schoolScore);
        model.addAttribute("sort",sort);
        model.addAttribute("area",areaList);
        model.addAttribute("scores",gaoKao);

        return "schoolHomepage";
    }
}
