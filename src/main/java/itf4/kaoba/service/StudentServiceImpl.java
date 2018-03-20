package itf4.kaoba.service;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import itf4.kaoba.mapper.StudentMapper;
import itf4.kaoba.model.Student;
import itf4.kaoba.model.Teacher;
import itf4.kaoba.util.ExcelUtil;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentMapper studentMapper;
	
	/****
	 *从Excel批量导入学生信息
	 * @param in
	 * @param file
	 * @throws Exception
	 */
	public int importUsersExcelInfo(HttpSession session,InputStream in, MultipartFile file) throws Exception {
		List<List<Object>> listob = ExcelUtil.getBankListByExcel(in,file.getOriginalFilename());  
		List<Student> studentList = new ArrayList();
		//SysUser currentLoginUser = (SysUser) session.getAttribute("CurrentLoginUserInfo");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		int count = 0;
		
		//遍历listob数据，把数据放到List中  
	    for (int i = 0; i < listob.size(); i++) { 
	    	List<Object> ob = listob.get(i);
	        System.out.println(listob.get(i));
	        Student student = new Student();
	        student.setLoginId(String.valueOf(ob.get(0)));
	        student.setStuName(String.valueOf(ob.get(1)));
	        student.setStuPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
	        student.setStatus(1);
	        student.setCreateTime(sdf.format(new Date()));
	        count = studentMapper.insert(student);
	        if(count == 0) {
	        	break;
	        }
	    }
		return count;
	}

}
