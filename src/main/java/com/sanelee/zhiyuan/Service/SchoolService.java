package com.sanelee.zhiyuan.Service;


import com.sanelee.zhiyuan.DTO.PaginationDTO;
import com.sanelee.zhiyuan.DTO.SchoolQueryDTO;
import com.sanelee.zhiyuan.Mapper.SchoolExtMapper;
import com.sanelee.zhiyuan.Mapper.SchoolMapper;
import com.sanelee.zhiyuan.Model.School;
import com.sanelee.zhiyuan.Model.SchoolExample;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SchoolService {

    @Autowired
    private SchoolExtMapper schoolExtMapper;

    @Autowired
    private SchoolMapper schoolMapper;

    public PaginationDTO list(Integer page, Integer size,Integer areaid,String search,String type){
        if (StringUtils.isNotBlank(search)) {
            String[] tags = StringUtils.split(search, " ");
            search = Arrays.stream(tags).collect(Collectors.joining("|"));
        }
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalPage;
        SchoolQueryDTO schoolQueryDTO = new SchoolQueryDTO();
        schoolQueryDTO.setSearch(search);
        schoolQueryDTO.setAreaid(areaid);
        schoolQueryDTO.setType(type);

        Integer totalCount= schoolExtMapper.countBySomething(schoolQueryDTO);

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
        paginationDTO.setPagination(totalPage, page);
        Integer offset = size * (page -1);
        SchoolExample example = new SchoolExample();
        example.setOrderByClause("rank asc");
        schoolQueryDTO.setSize(size);
        schoolQueryDTO.setPage(offset);
        List<School> schoolList = schoolExtMapper.selectBySomething(schoolQueryDTO);
        paginationDTO.setSchools(schoolList);

        return paginationDTO;
    }
//    public PaginationDTO listByArea(Integer page, Integer size,Integer areaid){
//        PaginationDTO paginationDTO = new PaginationDTO();
//        SchoolQueryDTO schoolQueryDTO = new SchoolQueryDTO();
//        Integer totalCount=0;
//        if (StringUtils.isNotBlank(search)){
//            String[] tags = StringUtils.split(search," ");
//            search = Arrays.stream(tags).collect(Collectors.joining("|"));
//            schoolQueryDTO.setSearch(search);
//            schoolQueryDTO.setAreaid(areaid);
//            totalCount = schoolMapper.countAreaBysearch(schoolQueryDTO);
//        }else if(StringUtils.isBlank(search)){
//            totalCount = schoolMapper.countByArea(areaid);
//        }
//
//        paginationDTO.setPagination(totalCount,page,size);
//        if (page < 1) {
//            page = 1;
//        }
//        if (page > paginationDTO.getTotalPage()) {
//            page = paginationDTO.getTotalPage();
//        }
//
//        Integer offset = size * (page -1);
//
//        schoolQueryDTO.setPage(offset);
//        schoolQueryDTO.setSize(size);
//        schoolQueryDTO.setAreaid(areaid);
//        if (StringUtils.isNotBlank(search)) {
//            List<School> schools = schoolMapper.selectByAreaidAndSearch(schoolQueryDTO);
//            paginationDTO.setSchools(schools);
//        }else if (StringUtils.isBlank(search)){
//        List<School> schools = schoolMapper.selectByAreaid(areaid,offset,size);
//        paginationDTO.setSchools(schools);}
//
//        return paginationDTO;
//    }
//
//    public PaginationDTO listByType(Integer page, Integer size, String type, String search) {
//        PaginationDTO paginationDTO = new PaginationDTO();
//        SchoolQueryDTO schoolQueryDTO = new SchoolQueryDTO();
//        Integer totalCount=0;
//        if (StringUtils.isNotBlank(search)){
//            String[] tags = StringUtils.split(search," ");
//            search = Arrays.stream(tags).collect(Collectors.joining("|"));
//            schoolQueryDTO.setSearch(search);
//            schoolQueryDTO.setType(type);
//            totalCount = schoolMapper.countTypeBysearch(schoolQueryDTO);
//        }else if(StringUtils.isBlank(search)){
//            totalCount = schoolMapper.countByType(type);
//        }
//        paginationDTO.setPagination(totalCount,page,size);
//        if (page < 1) {
//            page = 1;
//        }
//        if (page > paginationDTO.getTotalPage()) {
//            page = paginationDTO.getTotalPage();
//        }
//
//        Integer offset = size * (page -1);
//
//        schoolQueryDTO.setPage(offset);
//        schoolQueryDTO.setSize(size);
//        schoolQueryDTO.setType(type);
//
//        if (StringUtils.isNotBlank(search)) {
//            List<School> schools = schoolMapper.selectByTypeAndSearch(schoolQueryDTO);
//            paginationDTO.setSchools(schools);
//        }else if (StringUtils.isBlank(search)){
//            List<School> schools = schoolMapper.selectByType(type,offset,size);
//            paginationDTO.setSchools(schools);}
//        return paginationDTO;
//    }
}
