package itf4.kaoba.mapper;

import java.util.List;

import itf4.kaoba.model.Course;

public interface StudentMapperCustom {
	
	//����ѧ��id����ȡ��ѡ�Ŀγ�
	List<Course> getSelectCourseByStudentId(Integer studentId);

}
