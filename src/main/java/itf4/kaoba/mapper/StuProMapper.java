package itf4.kaoba.mapper;

import itf4.kaoba.model.StuPro;
import itf4.kaoba.model.StuProExample;
import itf4.kaoba.model.StuProKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StuProMapper {
    long countByExample(StuProExample example);

    int deleteByExample(StuProExample example);

    int deleteByPrimaryKey(StuProKey key);

    int insert(StuPro record);

    int insertSelective(StuPro record);

    List<StuPro> selectByExample(StuProExample example);

    StuPro selectByPrimaryKey(StuProKey key);

    int updateByExampleSelective(@Param("record") StuPro record, @Param("example") StuProExample example);

    int updateByExample(@Param("record") StuPro record, @Param("example") StuProExample example);

    int updateByPrimaryKeySelective(StuPro record);

    int updateByPrimaryKey(StuPro record);
}