package itf4.kaoba.mapper;
  
import itf4.kaoba.model.Course;
import itf4.kaoba.model.CourseExample;

import java.io.InputStream;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

public interface CourseMapper {
    long countByExample(CourseExample example);

    int deleteByExample(CourseExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Course record);

    int insertSelective(Course record);

    List<Course> selectByExample(CourseExample example);

    Course selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Course record, @Param("example") CourseExample example);

    int updateByExample(@Param("record") Course record, @Param("example") CourseExample example);

    int updateByPrimaryKeySelective(Course record);

    int updateByPrimaryKey(Course record);

	int insertCourseBatch(List<Course> courseList);

}