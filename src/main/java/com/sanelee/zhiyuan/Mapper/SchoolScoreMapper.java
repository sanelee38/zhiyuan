package com.sanelee.zhiyuan.Mapper;

import com.sanelee.zhiyuan.Model.SchoolScore;
import com.sanelee.zhiyuan.Model.SchoolScoreExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface SchoolScoreMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table schoolscore
     *
     * @mbg.generated Tue Dec 10 20:47:45 CST 2019
     */
    long countByExample(SchoolScoreExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table schoolscore
     *
     * @mbg.generated Tue Dec 10 20:47:45 CST 2019
     */
    int deleteByExample(SchoolScoreExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table schoolscore
     *
     * @mbg.generated Tue Dec 10 20:47:45 CST 2019
     */
    int insert(SchoolScore record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table schoolscore
     *
     * @mbg.generated Tue Dec 10 20:47:45 CST 2019
     */
    int insertSelective(SchoolScore record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table schoolscore
     *
     * @mbg.generated Tue Dec 10 20:47:45 CST 2019
     */
    List<SchoolScore> selectByExampleWithRowbounds(SchoolScoreExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table schoolscore
     *
     * @mbg.generated Tue Dec 10 20:47:45 CST 2019
     */
    List<SchoolScore> selectByExample(SchoolScoreExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table schoolscore
     *
     * @mbg.generated Tue Dec 10 20:47:45 CST 2019
     */
    int updateByExampleSelective(@Param("record") SchoolScore record, @Param("example") SchoolScoreExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table schoolscore
     *
     * @mbg.generated Tue Dec 10 20:47:45 CST 2019
     */
    int updateByExample(@Param("record") SchoolScore record, @Param("example") SchoolScoreExample example);
}