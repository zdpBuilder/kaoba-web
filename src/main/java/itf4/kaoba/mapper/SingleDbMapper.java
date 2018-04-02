package itf4.kaoba.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import itf4.kaoba.model.SingleDb;
import itf4.kaoba.model.SingleDbCustom;
import itf4.kaoba.model.SingleDbExample;
import itf4.kaoba.pojo.SelectCourseTreeJsonListPojo;

public interface SingleDbMapper {
    long countByExample(SingleDbExample example);

    int deleteByExample(SingleDbExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SingleDb record);

    int insertSelective(SingleDb record);

    List<SingleDb> selectByExample(SingleDbExample example);

    SingleDb selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SingleDb record, @Param("example") SingleDbExample example);

    int updateByExample(@Param("record") SingleDb record, @Param("example") SingleDbExample example);

    int updateByPrimaryKeySelective(SingleDb record);

    int updateByPrimaryKey(SingleDb record);
    
    List<SelectCourseTreeJsonListPojo> selectAllsingleDbMessageBycouseId(Integer couseId);
   
    List<SelectCourseTreeJsonListPojo> selectAllsingleDbMessageBypid(Integer pid);
    
    SingleDbCustom selectDbAnById(Integer id);
}