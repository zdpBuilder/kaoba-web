package itf4.kaoba.mapper;

import java.util.List;

import itf4.kaoba.model.Course;

public interface StudentMapperCustom {
	
	//根据学生id，获取所选的课程
	List<Course> getSelectCourseByStudentId(Integer studentId);

}
