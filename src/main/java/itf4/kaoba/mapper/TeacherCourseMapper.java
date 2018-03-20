package itf4.kaoba.mapper;

import itf4.kaoba.model.TeacherCourse;
import itf4.kaoba.model.TeacherCourseExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TeacherCourseMapper {
    long countByExample(TeacherCourseExample example);

    int deleteByExample(TeacherCourseExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TeacherCourse record);

    int insertSelective(TeacherCourse record);

    List<TeacherCourse> selectByExample(TeacherCourseExample example);

    TeacherCourse selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TeacherCourse record, @Param("example") TeacherCourseExample example);

    int updateByExample(@Param("record") TeacherCourse record, @Param("example") TeacherCourseExample example);

    int updateByPrimaryKeySelective(TeacherCourse record);

    int updateByPrimaryKey(TeacherCourse record);
}