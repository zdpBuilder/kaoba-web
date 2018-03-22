package itf4.kaoba.pojo;

import java.util.List;

import itf4.kaoba.model.Course;
import lombok.Data;

/**
 * @author liuxun
 * 课程分类下拉列表
 */
@Data
public class SelectCourseTreeJsonListPojo {
	
	private int pid;
	private String name;
	private List<Course> childList;

}
