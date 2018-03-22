package itf4.kaoba.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import itf4.kaoba.model.AnswerDb;
import itf4.kaoba.model.AnswerDbExample;

public interface AnswerDbMapper {
    long countByExample(AnswerDbExample example);

    int deleteByExample(AnswerDbExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AnswerDb record);

    int insertSelective(AnswerDb record);

    List<AnswerDb> selectByExample(AnswerDbExample example);

    AnswerDb selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AnswerDb record, @Param("example") AnswerDbExample example);

    int updateByExample(@Param("record") AnswerDb record, @Param("example") AnswerDbExample example);

    int updateByPrimaryKeySelective(AnswerDb record);

    int updateByPrimaryKey(AnswerDb record);
}