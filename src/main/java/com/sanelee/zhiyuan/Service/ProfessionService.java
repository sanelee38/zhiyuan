package com.sanelee.zhiyuan.Service;

import com.sanelee.zhiyuan.DTO.PaginationDTO;
import com.sanelee.zhiyuan.DTO.ProfessionQueryDTO;
import com.sanelee.zhiyuan.Mapper.ProfessionExtMapper;
import com.sanelee.zhiyuan.Model.Profession;
import com.sanelee.zhiyuan.Model.ProfessionExample;
import com.sanelee.zhiyuan.Model.School;
import com.sanelee.zhiyuan.Model.SchoolExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfessionService {
    @Autowired
    private ProfessionExtMapper professionExtMapper;

    public PaginationDTO list(Integer page, Integer size, String major, String search,String subject) {
        if (StringUtils.isNotBlank(search)) {
            String[] tags = StringUtils.split(search, " ");
            search = Arrays.stream(tags).collect(Collectors.joining("|"));
        }

        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalPage;
        ProfessionQueryDTO professionQueryDTO = new ProfessionQueryDTO();
        professionQueryDTO.setSearch(search);
        professionQueryDTO.setMajor(major);
        professionQueryDTO.setSubject(subject);

        Integer totalCount =  professionExtMapper.countBySomething(professionQueryDTO);

        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }
        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        if (totalPage == 0){
            page = 1;
        }
        paginationDTO.setPagination(totalPage, page);

        Integer offset = size * (page -1);
        ProfessionExample example = new ProfessionExample();
        example.setOrderByClause("pid asc");
        professionQueryDTO.setSize(size);
        professionQueryDTO.setPage(offset);
        List<Profession> professionList = professionExtMapper.selectBySomething(professionQueryDTO);
        paginationDTO.setProfessions(professionList);

        return paginationDTO;

    }

    public List<Profession> subjectList(String major) {
        List<Profession> subjectList = new ArrayList<>();
        if (major != null){
            major = StringUtils.substringBefore(major, ",");
            Profession example3 = new Profession();
            example3.setMajor(major);
            subjectList=professionExtMapper.selectSubjectByMajor(example3);
        }
        else {
            ProfessionExample example2 = new ProfessionExample();
            example2.setDistinct(true);
            example2.setOrderByClause("pid asc");
            subjectList = professionExtMapper.selectSubjectByExample(example2);
        }
        return subjectList;
    }
}
