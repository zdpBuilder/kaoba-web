package itf4.kaoba.mapper;

import itf4.kaoba.model.StuError;
import itf4.kaoba.model.StuErrorExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StuErrorMapper {
    long countByExample(StuErrorExample example);

    int deleteByExample(StuErrorExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(StuError record);

    int insertSelective(StuError record);

    List<StuError> selectByExample(StuErrorExample example);

    StuError selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") StuError record, @Param("example") StuErrorExample example);

    int updateByExample(@Param("record") StuError record, @Param("example") StuErrorExample example);

    int updateByPrimaryKeySelective(StuError record);

    int updateByPrimaryKey(StuError record);
}