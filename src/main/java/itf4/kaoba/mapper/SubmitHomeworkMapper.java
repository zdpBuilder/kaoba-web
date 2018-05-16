package itf4.kaoba.mapper;

import itf4.kaoba.model.SubmitHomework;
import itf4.kaoba.model.SubmitHomeworkExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SubmitHomeworkMapper {
    int countByExample(SubmitHomeworkExample example);

    int deleteByExample(SubmitHomeworkExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SubmitHomework record);

    int insertSelective(SubmitHomework record);

    List<SubmitHomework> selectByExample(SubmitHomeworkExample example);

    SubmitHomework selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SubmitHomework record, @Param("example") SubmitHomeworkExample example);

    int updateByExample(@Param("record") SubmitHomework record, @Param("example") SubmitHomeworkExample example);

    int updateByPrimaryKeySelective(SubmitHomework record);

    int updateByPrimaryKey(SubmitHomework record);
}