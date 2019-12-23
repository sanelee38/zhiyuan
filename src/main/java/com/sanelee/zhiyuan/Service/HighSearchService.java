package com.sanelee.zhiyuan.Service;

import com.sanelee.zhiyuan.DTO.PaginationDTO;
import com.sanelee.zhiyuan.Mapper.GaoKaoExtMapper;
import com.sanelee.zhiyuan.Model.GaoKao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HighSearchService {
    @Autowired
    private GaoKaoExtMapper gaoKaoExtMapper;

    public List<GaoKao> schoolHighSearch(String area, String profession, Integer type) {
        if(StringUtils.isNotBlank(area)){
            String[] tags = StringUtils.split(area,",");
            area = Arrays.stream(tags).collect(Collectors.joining("|"));
        }
        Integer is211=null;
        Integer is985=null;
        Integer isDoubleFirstClass=null;
        if (type==985){
            is985=985;
        }else if(type==211){
            is211=211;
        }else if(type==1){
            isDoubleFirstClass=1;
        }else if(type==0){
            is211=0;
            is985=0;
            isDoubleFirstClass=0;
        }else if(type==-1){
            is211=null;
            is985=null;
            isDoubleFirstClass=null;
        }
        List<GaoKao> schoolSearchList = gaoKaoExtMapper.schoolHighSearch(area,profession,is211,is985,isDoubleFirstClass);

        return schoolSearchList;
    }

}
