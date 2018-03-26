package itf4.kaoba.mapper;

import itf4.kaoba.model.StuTeaCou;
import itf4.kaoba.model.StuTeaCouExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StuTeaCouMapper {
    long countByExample(StuTeaCouExample example);

    int deleteByExample(StuTeaCouExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(StuTeaCou record);

    int insertSelective(StuTeaCou record);

    List<StuTeaCou> selectByExample(StuTeaCouExample example);

    StuTeaCou selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") StuTeaCou record, @Param("example") StuTeaCouExample example);

    int updateByExample(@Param("record") StuTeaCou record, @Param("example") StuTeaCouExample example);

    int updateByPrimaryKeySelective(StuTeaCou record);

    int updateByPrimaryKey(StuTeaCou record);
}