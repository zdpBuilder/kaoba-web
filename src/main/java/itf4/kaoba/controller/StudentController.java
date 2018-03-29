package itf4.kaoba.controller;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import itf4.kaoba.mapper.SingleDbMapper;
import itf4.kaoba.mapper.StuErrorMapper;
import itf4.kaoba.mapper.StuTeaCouMapper;
import itf4.kaoba.mapper.StudentMapper;
import itf4.kaoba.model.SingleDb;
import itf4.kaoba.model.StuError;
import itf4.kaoba.model.StuErrorExample;
import itf4.kaoba.model.StuTeaCou;
import itf4.kaoba.model.StuTeaCouExample;
import itf4.kaoba.model.Student;
import itf4.kaoba.model.StudentExample;
import itf4.kaoba.model.StudentExample.Criteria;
import itf4.kaoba.service.StudentService;
import itf4.kaoba.util.DateUtil;
import itf4.kaoba.util.JsonPrintUtil;
import itf4.kaoba.util.JsonUtils;
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
	@Autowired
	private StuTeaCouMapper stuTeaCouMapper;
	@Autowired
	private StuErrorMapper stuErrorMapper;
	@Autowired
	private SingleDbMapper singleDbMapper;
	
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
	
	//验证课程是否已经添加到课程表中
	@RequestMapping("validateCourse")
	@ResponseBody
	public void validateCourse(int courseId,HttpServletRequest request, HttpServletResponse response,
 			HttpSession session) {
		
		//获取登录学生信息
		Student student = (Student)session.getAttribute("CurrentLoginUserInfo");
		
		//拼装查询条件
		StuTeaCouExample example = new StuTeaCouExample();
		itf4.kaoba.model.StuTeaCouExample.Criteria criteria = example.createCriteria();
		criteria.andCouIdEqualTo(courseId).andStuIdEqualTo(student.getId()).andStatusEqualTo(1);
		
		//查询
		List<StuTeaCou> list = stuTeaCouMapper.selectByExample(example);
		
		// 输出前台Json
		if(list.size()>0 && list !=null) {
 			JsonPrintUtil.printObjDataWithKey(response, 1, "data");
		} else {
			JsonPrintUtil.printObjDataWithKey(response, 0, "data");
		}
		
	}
	
	//将课程添加到学生课程表中
	@RequestMapping("addCourseToStuTeaCou")
	@ResponseBody
	public void addCourseToStuTeaCou(String courseIds,int teaId,HttpServletRequest request, HttpServletResponse response,
 			HttpSession session) {
		
		//获取登录学生信息
		Student student = (Student)session.getAttribute("CurrentLoginUserInfo");
		int count =1;
		StuTeaCou stuTeaCou = new StuTeaCou();
		stuTeaCou.setCreater(student.getStuName());
		stuTeaCou.setCreateTime(DateUtil.DateToString(new Date(), "yyyy-mm-dd"));
		stuTeaCou.setStatus(1);
		
		//将数据插入到数据库中
		if(courseIds !=null && courseIds !="") {
			String []courseId = courseIds.split(",");
			for (String id : courseId) {
				stuTeaCou.setCouId(Integer.parseInt(id));
				count = count & stuTeaCouMapper.insert(stuTeaCou);
				if(count ==0) {
					break;
				}
			}
		}
		
		//返回结果
		JsonPrintUtil.printObjDataWithKey(response, count, "data");
	}
	
	//将课程从课程表中移除
	@RequestMapping("deleteCourseToStuTeaCou")
	@ResponseBody
	public void deleteCourseToStuTeaCou(String stuTeaCouIds,HttpServletRequest request, HttpServletResponse response
			,HttpSession session) {
		//获取登录学生信息
		Student student = (Student)session.getAttribute("CurrentLoginUserInfo");
		int count =1;
		StuTeaCou stuTeaCou = new StuTeaCou();
		stuTeaCou.setUpdater(student.getStuName());
		stuTeaCou.setUpdateTime(DateUtil.DateToString(new Date(), "yyyy-mm-dd"));
		//逻辑删除
		stuTeaCou.setStatus(0);
		
		//更新课程状态
		if(stuTeaCouIds !=null && stuTeaCouIds !="") {
			String []stuTeaCouId = stuTeaCouIds.split(",");
			for (String id : stuTeaCouId) {
				stuTeaCou.setId(Integer.parseInt(id));
				count = count & stuTeaCouMapper.updateByPrimaryKeySelective(stuTeaCou);
				if(count ==0) {
					break;
				}
			}
		}
		
		//返回结果
		JsonPrintUtil.printObjDataWithKey(response, count, "data");
		
	}
	
	//插入错题本
		@RequestMapping("/insertStuError")
		@ResponseBody
		public void insertStuError(HttpServletRequest request, HttpServletResponse response,StuError stuError) {
			Student student = (Student) request.getSession().getAttribute("CurrentLoginUserInfo");
			stuError.setStuId(student.getId());
			stuError.setCreater(student.getStuName());
			stuError.setCreateTime(DateUtil.DateToString(new Date(), "yyyy-MM-dd "));
			stuError.setStuErrorDbtype(0);
			stuError.setStatus(1);
			//课程主键
			//题主键
			//插入数据库
			int result = stuErrorMapper.insert(stuError);
			//返回1，添加成功
			JsonPrintUtil.printObjDataWithKey(response, result, "data");
		}
		
		// 查询错题本
		@RequestMapping("/StuErrorList")
		@ResponseBody
		public String StuErrorList(HttpServletRequest request, HttpServletResponse response, int courseId) {
			StuErrorExample example = new StuErrorExample();
			itf4.kaoba.model.StuErrorExample.Criteria criteria = example.createCriteria();
			criteria.andCourseIdEqualTo(courseId);
			List<StuError> list = stuErrorMapper.selectByExample(example);
			
			List<SingleDb> singleDbList = new ArrayList<SingleDb>();
			for (int i = 0; i < list.size() ; i++) {
				int dbId = list.get(i).getDbId();
				SingleDb singleDb = singleDbMapper.selectByPrimaryKey(dbId);
				singleDbList.add(singleDb);
			}
			
			String singleDbListJson =JsonUtils.listToJson(singleDbList);
			
			//JsonPrintUtil.printObjDataWithKey(response, singleDbListJson, "data");
			
			return singleDbListJson;
		}
		
		// 刪除错题
		@RequestMapping("/deleteStuError")
		@ResponseBody
		public void deleteStuError(HttpServletRequest request, HttpServletResponse response, int dbId) {
			Student student = (Student) request.getSession().getAttribute("CurrentLoginUserInfo");
			StuErrorExample example = new StuErrorExample();
			itf4.kaoba.model.StuErrorExample.Criteria criteria = example.createCriteria();
			criteria.andDbIdEqualTo(dbId);
			StuError stuError = new StuError();
			stuError.setStatus(0);
			stuError.setUpdater(student.getStuName());
			stuError.setUpdateTime(DateUtil.DateToString(new Date(), "yyyy-MM-dd "));
			
			int result = stuErrorMapper.updateByExample(stuError, example);
			
			JsonPrintUtil.printObjDataWithKey(response, result, "data");
		}
	
}
