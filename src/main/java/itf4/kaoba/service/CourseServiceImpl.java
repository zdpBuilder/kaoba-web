package itf4.kaoba.service;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import itf4.kaoba.mapper.CourseMapper;
import itf4.kaoba.model.Course;
import itf4.kaoba.util.ExcelUtil;

@Service
public class CourseServiceImpl implements CourseService {

	@Autowired
	private CourseMapper courseMapper;

	public int importCourseExcelInfo(InputStream in, MultipartFile file) throws Exception {
		Date nowTime = new Date();
		SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<List<Object>> listob = ExcelUtil.getBankListByExcel(in, file.getOriginalFilename());
		List<Course> courseList = new ArrayList<Course>();
		// 遍历listob数据，把数据放到List中

		for (int i = 0; i < listob.size() - 1; i++) {

			List<Object> ob = listob.get(i);
			System.out.println(listob.get(i));
			Course course = new Course();
			course.setCourseName(String.valueOf(ob.get(0)));
			course.setCreater(String.valueOf(ob.get(1)));
			course.setStatus(1);
			course.setCreateTime(time.format(nowTime));
			/*
			 * users.setLoginId(String.valueOf(ob.get(0)));
			 * users.setUserName(String.valueOf(ob.get(1)));
			 * users.setUserPassword(DigestUtils.md5DigestAsHex(String.valueOf(ob.get(2)).
			 * getBytes())); users.setStatus(Integer.parseInt((String)ob.get(3)));
			 * users.setCreater(String.valueOf(ob.get(4)));
			 * users.setCreateTime(String.valueOf(ob.get(5)));
			 * users.setUpdater(String.valueOf(ob.get(6)));
			 * users.setUpdateTime(String.valueOf(ob.get(7)));
			 */
			courseList.add(course);
		}

		// 批量插入
		int result = courseMapper.insertCourseBatch(courseList);
		return result;
	}

}
