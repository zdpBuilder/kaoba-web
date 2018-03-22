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
import itf4.kaoba.mapper.TeacherCourseMapper;
import itf4.kaoba.mapper.TeacherMapper;
import itf4.kaoba.model.SysUser;
import itf4.kaoba.model.Teacher;
import itf4.kaoba.model.TeacherCourse;
import itf4.kaoba.model.TeacherCourseExample;
import itf4.kaoba.model.TeacherExample;
import itf4.kaoba.model.TeacherExample.Criteria;
import itf4.kaoba.service.TeacherService;
import itf4.kaoba.util.Const;
import itf4.kaoba.util.JsonPrintUtil;
/**
 * 老师管理
 * @author yangfan
 *
 */
@Controller
@RequestMapping("/teacher")
public class TeacherController {
	@Autowired
	private TeacherMapper teacherMapper;
	@Autowired
	private TeacherService teacherService;
	@Autowired
	private TeacherCourseMapper teacherCourseMapper;
	
	@RequestMapping("list")
	public void teacherList(HttpServletRequest request, HttpServletResponse response, String keywords, int limit,
 			int page) {
		
		// limit 每页显示数量
 		// page 当前页码
		TeacherExample example = new TeacherExample();
		// 设置分页查询参数
 		example.setStartRow((page - 1) * limit);
 		example.setPageSize(limit);
 		example.setOrderByClause("create_time desc,update_time desc");
 		Criteria criteria = example.createCriteria();
 		
 		if (keywords!=null&&keywords!="") {
 			keywords = keywords.trim();
 			keywords = "%" + keywords + "%";
 			// and or联合查询
 			example.or().andTeaNameLike(keywords).andStatusEqualTo(1);
 			example.or().andLoginIdLike(keywords).andStatusEqualTo(1);
 		} else {
 			criteria.andStatusEqualTo(1);// 正常状态
 		}
 		
 		// 分页查询
 		List<Teacher> teacherList = teacherMapper.selectByExample(example);
 		int count = (int) teacherMapper.countByExample(example);

 		ResponseJsonPageListBean listBean = new ResponseJsonPageListBean();
 		listBean.setCode(0);
 		listBean.setCount(count);
 		listBean.setMsg("老师列表");
 		listBean.setData(teacherList);

 		if (null != teacherList && teacherList.size() > 0) {
 			JsonPrintUtil.printObjDataWithoutKey(response, listBean);
 		} else {
 			JsonPrintUtil.printObjDataWithoutKey(response, null);
 		}
	}
	
	@RequestMapping("save")
	@ResponseBody
	public void save(Teacher teacher,HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		String CurrentUserName ="";
		if(request.getSession().getAttribute(Const.SESSION_USER_STATUS).equals("1")) { 
			  SysUser user = (SysUser)request.getSession().getAttribute(Const.SESSION_USER);  
			  CurrentUserName= user.getUserName();
		 }else if(request.getSession().getAttribute(Const.SESSION_USER_STATUS).equals("2")) {
			 Teacher Currteacher = (Teacher)request.getSession().getAttribute(Const.SESSION_USER);  
			  CurrentUserName= Currteacher.getTeaName();
		 }
		
		
		int count = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		//编辑
		if(teacher.getId()!=null &&teacher.getId()>0) {
 			Teacher userOld = teacherMapper.selectByPrimaryKey(teacher.getId());
			teacher.setUpdater(CurrentUserName);
 			teacher.setUpdateTime(sdf.format(new Date()));
 			if(!userOld.getTeaPassword().equals(teacher.getTeaPassword())) {
 				teacher.setTeaPassword(DigestUtils.md5DigestAsHex(teacher.getTeaPassword().getBytes()));
 			}
			count = teacherMapper.updateByPrimaryKeySelective(teacher);
			//输出前台Json
			if (count > 0) {
				JsonPrintUtil.printObjDataWithKey(response, teacher, "data");
			}
	    //保存
		}else {
			teacher.setStatus(1);//正常为1
			teacher.setTeaPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
			teacher.setCreater(CurrentUserName);
 			teacher.setCreateTime(sdf.format(new Date()));
 			count = teacherMapper.insert(teacher);
 			
 			if(count >0) {
 				JsonPrintUtil.printObjDataWithKey(response, count, "data");
 			}
			
		}
		
	}
	
	//老师信息查看
	@RequestMapping(value = "show", method = RequestMethod.POST)
	@ResponseBody
	public void show(int id, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		Teacher teacher = teacherMapper.selectByPrimaryKey(id);
		if(teacher !=null) {
			JsonPrintUtil.printObjDataWithKey(response, teacher, "data");
		} else {
			JsonPrintUtil.printObjDataWithKey(response, null, "data");
		}
	}
	
	//老师批量删除
	@RequestMapping(value = "deleteBatch", method = RequestMethod.POST)
 	@ResponseBody
 	public void deleteBatch(String idStr, HttpServletRequest request, HttpServletResponse response,
 			HttpSession session) {
		
		SysUser currentLoginUser = (SysUser) session.getAttribute("CurrentLoginUserInfo");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		TeacherCourseExample example = new TeacherCourseExample();
		itf4.kaoba.model.TeacherCourseExample.Criteria criteria = example.createCriteria();
		if (StringUtils.isNotBlank(idStr)) {
 			String[] idArr = idStr.split(",");
 			for (int i = 0; i < idArr.length; i++) {
 				// 更新老师为删除状态
 				int id = Integer.parseInt(idArr[i]);
 				Teacher teacher = teacherMapper.selectByPrimaryKey(id);
 				teacher.setStatus(3);// 1正常 3已删除
 				teacher.setUpdateTime(sdf.format(new Date()));
 				teacher.setUpdater(currentLoginUser.getUserName() + "");
 				teacherMapper.updateByPrimaryKeySelective(teacher);
 				//删除课程关联表信息
 				criteria.andTeaIdEqualTo(id);
 				teacherCourseMapper.deleteByExample(example);
 			}
 			// 输出前台Json
 			JsonPrintUtil.printObjDataWithKey(response, 1, "data");
 		} else {
 			JsonPrintUtil.printObjDataWithKey(response, 0, "data");
 		}
	}

	//批量导入老师
	@RequestMapping("/insertTeachersBatch")  
	@ResponseBody
	public int impots(HttpServletRequest request,HttpSession session, Model model) throws Exception{
		//获取上传的文件  
        MultipartHttpServletRequest multipart = (MultipartHttpServletRequest) request;  
        MultipartFile file = multipart.getFile("upfile");       
        InputStream in = file.getInputStream(); 
        //数据导入  
	    int result= teacherService.importUsersExcelInfo(session,in, file); 
	    in.close();  
	    return result;  
	}
	
	//将课程授权给老师
	@RequestMapping("grantCourse")
	@ResponseBody
	public void grantCourse(HttpServletResponse response,String courseIds,String teacherIds) {
		int count=0;
		TeacherCourse teacherCourse = new TeacherCourse();
		//批量授权有问题 
		
		
		if(teacherIds !=""&&courseIds!="") {
			String []teacherList = teacherIds.split(",");
			for (String techerId : teacherList) {
				TeacherCourse teaCourse = showTeacherCourse(Integer.parseInt(techerId));
				if(teaCourse!=null) {
					teaCourse.setCourseId(courseIds);
					count = teacherCourseMapper.updateByPrimaryKey(teaCourse);
				} else {
					teacherCourse.setTeaId(Integer.parseInt(techerId));
					teacherCourse.setCourseId(courseIds);
					count = teacherCourseMapper.insert(teacherCourse);
				}
				
			}
		}
		
		// 输出前台Json
		JsonPrintUtil.printObjDataWithKey(response, count, "data");
		
	}
	
	//查询老师是否有课程授权
	public TeacherCourse showTeacherCourse(int teaId) {
		TeacherCourseExample example = new TeacherCourseExample();
		itf4.kaoba.model.TeacherCourseExample.Criteria criteria = example.createCriteria();
		criteria.andTeaIdEqualTo(teaId);
		List<TeacherCourse> list = teacherCourseMapper.selectByExample(example);
		if(list!=null && list.size()>0) {
			return list.get(0);
		}
		return null;
		
	}
	//查询老师是否有课程授权url
	@RequestMapping("showTeacherCourse")
	@ResponseBody
	public void showCourse(HttpServletResponse response,int teacherId) {
		TeacherCourse teacherCourse = showTeacherCourse(teacherId);
		JsonPrintUtil.printObjDataWithKey(response, teacherCourse, "data");
	}
}
