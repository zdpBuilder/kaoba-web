package itf4.kaoba.controller;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import itf4.kaoba.common.ResponseJsonPageListBean;
import itf4.kaoba.mapper.StudentMapper;
import itf4.kaoba.model.Student;
import itf4.kaoba.model.StudentExample;
import itf4.kaoba.model.StudentExample.Criteria;
import itf4.kaoba.service.StudentService;
import itf4.kaoba.util.JsonPrintUtil;
/**
 * 学生管理
 * 
 */
@Controller
@RequestMapping("/student")
public class StudentController {
	@Autowired
	private StudentMapper studentMapper;
	@Autowired
	private StudentService studentService;
	
	@RequestMapping("list")
	public void studentList(HttpServletRequest request, HttpServletResponse response, String keywords, int limit,
 			int page) {
		
		// limit 每页显示数量
 		// page 当前页码
		StudentExample example = new StudentExample();
		// 设置分页查询参数
 		example.setStartRow((page - 1) * limit);
 		example.setPageSize(limit);
 		example.setOrderByClause("create_time desc,update_time desc");
 		Criteria criteria = example.createCriteria();
 		
 		if (keywords!=null&&keywords!="") {
 			keywords = keywords.trim();
 			keywords = "%" + keywords + "%";
 			// and or联合查询
 			example.or().andStuNameLike(keywords).andStatusEqualTo(1);
 			example.or().andLoginIdLike(keywords).andStatusEqualTo(1);
 		} else {
 			criteria.andStatusEqualTo(1);// 正常状态
 		}
 		
 		// 分页查询
 		List<Student> sudentList = studentMapper.selectByExample(example);
 		int count = (int) studentMapper.countByExample(example);

 		ResponseJsonPageListBean listBean = new ResponseJsonPageListBean();
 		listBean.setCode(0);
 		listBean.setCount(count);
 		listBean.setMsg("学生列表");
 		listBean.setData(sudentList);

 		if (null != sudentList && sudentList.size() > 0) {
 			JsonPrintUtil.printObjDataWithoutKey(response, listBean);
 		} else {
 			JsonPrintUtil.printObjDataWithoutKey(response, null);
 		}
	}
	
	@RequestMapping("save")
	@ResponseBody
	public void save(Student student,HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		
		int count = 0;
		//SysUser currentLoginUser = (SysUser) session.getAttribute("CurrentLoginUserInfo");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		//编辑
		if(student.getId()!=null &&student.getId()>0) {
			//teacher.setUpdater(currentLoginUser.getUserId() + "");
			student.setUpdateTime(sdf.format(new Date()));
 			if(student.getStuPassword() !=null) {
 				student.setStuPassword(DigestUtils.md5DigestAsHex(student.getStuPassword().getBytes()));
 			}
			count = studentMapper.updateByPrimaryKeySelective(student);
			//输出前台Json
			if (count > 0) {
				JsonPrintUtil.printObjDataWithKey(response, student, "data");
			}
	    //保存
		}else {
			student.setStatus(1);//正常为1
			student.setStuPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
			//teacher.setCreater(currentLoginUser.getUserId() + "");
			student.setCreateTime(sdf.format(new Date()));
 			count = studentMapper.insert(student);
 			
 			if(count >0) {
 				JsonPrintUtil.printObjDataWithKey(response, count, "data");
 			}
			
		}
		
	}
	
	//学生信息查看
	@RequestMapping(value = "show", method = RequestMethod.POST)
	@ResponseBody
	public void show(int id, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		Student student = studentMapper.selectByPrimaryKey(id);
		if(student !=null) {
			JsonPrintUtil.printObjDataWithKey(response, student, "data");
		} else {
			JsonPrintUtil.printObjDataWithKey(response, null, "data");
		}
	}
	
	//学生批量删除
	@RequestMapping(value = "deleteBatch", method = RequestMethod.POST)
 	@ResponseBody
 	public void deleteBatch(String idStr, HttpServletRequest request, HttpServletResponse response,
 			HttpSession session) {
		
		//SysUser currentLoginUser = (SysUser) session.getAttribute("CurrentLoginUserInfo");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		if (StringUtils.isNotBlank(idStr)) {
 			String[] idArr = idStr.split(",");
 			for (int i = 0; i < idArr.length; i++) {
 				// 更新老师为删除状态
 				int id = Integer.parseInt(idArr[i]);
 				Student student = studentMapper.selectByPrimaryKey(id);
 				student.setStatus(3);// 1正常 3已删除
 				student.setUpdateTime(sdf.format(new Date()));
 				//teacher.setUpdater(currentLoginUser.getUserId() + "");
 				studentMapper.updateByPrimaryKeySelective(student);
 			}
 			// 输出前台Json
 			JsonPrintUtil.printObjDataWithKey(response, 1, "data");
 		} else {
 			JsonPrintUtil.printObjDataWithKey(response, 0, "data");
 		}
	}

	//批量导入学生
	@RequestMapping("/insertStudentsBatch")  
	@ResponseBody
	public int impots(HttpServletRequest request,HttpSession session, Model model) throws Exception{
		//获取上传的文件  
        MultipartHttpServletRequest multipart = (MultipartHttpServletRequest) request;  
        MultipartFile file = multipart.getFile("upfile");       
        InputStream in = file.getInputStream(); 
        //数据导入  
	    int result= studentService.importUsersExcelInfo(session,in, file); 
	    in.close();  
	    return result;  
	}
	
}
