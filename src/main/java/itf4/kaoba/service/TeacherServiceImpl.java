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

import itf4.kaoba.mapper.TeacherMapper;
import itf4.kaoba.model.SysUser;
import itf4.kaoba.model.Teacher;
import itf4.kaoba.util.ExcelUtil;

@Service
public class TeacherServiceImpl implements TeacherService {

	@Autowired
	private TeacherMapper teacherMapper;
	
	/****
	 *从Excel批量导入老师信息
	 * @param in
	 * @param file
	 * @throws Exception
	 */
	public int importUsersExcelInfo(HttpSession session,InputStream in, MultipartFile file) throws Exception {
		List<List<Object>> listob = ExcelUtil.getBankListByExcel(in,file.getOriginalFilename());  
		List<Teacher> teacherList = new ArrayList();
		SysUser currentLoginUser = (SysUser) session.getAttribute("CurrentLoginUserInfo");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		int count = 0;
		
		//遍历listob数据，把数据放到List中  
	    for (int i = 0; i < listob.size(); i++) { 
	    	List<Object> ob = listob.get(i);
	        System.out.println(listob.get(i));
	        Teacher teacher = new Teacher();
	        teacher.setLoginId(String.valueOf(ob.get(0)));
	        teacher.setTeaName(String.valueOf(ob.get(1)));
	        teacher.setTeaPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
	        teacher.setStatus(1);
	        teacher.setCreater(currentLoginUser.getUserName() + "");
	        teacher.setCreateTime(sdf.format(new Date()));
	        count = teacherMapper.insert(teacher);
	        if(count == 0) {
	        	break;
	        }
	    }
		return count;
	}

}
