package com.sanelee.zhiyuan.Mapper;

import com.sanelee.zhiyuan.DTO.GaoKaoDTO;
import com.sanelee.zhiyuan.Model.GaoKao;
import com.sanelee.zhiyuan.Model.GaoKaoExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface GaoKaoExtMapper {

    Integer countSelectSchoolByProname(GaoKaoDTO gaoKaoDTO);

    List<GaoKao> selectByProname(GaoKaoDTO gaoKaoDTO);
}