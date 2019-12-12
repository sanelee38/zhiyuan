package com.sanelee.zhiyuan.Mapper;

import com.sanelee.zhiyuan.Model.User;
import com.sanelee.zhiyuan.Model.UserExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface UserExtMapper {

    User findByToken(String token);
}