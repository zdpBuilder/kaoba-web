package itf4.kaoba.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import itf4.kaoba.common.ResponseJsonPageListBean;
import itf4.kaoba.mapper.StuTeaCouMapper;
import itf4.kaoba.mapper.StudentMapper;
import itf4.kaoba.mapper.SubmitHomeworkMapper;
import itf4.kaoba.model.Course;
import itf4.kaoba.model.CourseExample;
import itf4.kaoba.model.Homework;
import itf4.kaoba.model.HomeworkExample;
import itf4.kaoba.model.SysUser;
import itf4.kaoba.model.Teacher;
import itf4.kaoba.pojo.ExportHomeworkPojo;
import itf4.kaoba.pojo.StuHomworkPojo;
import itf4.kaoba.model.StuTeaCou;
import itf4.kaoba.model.StuTeaCouExample;
import itf4.kaoba.model.SubmitHomework;
import itf4.kaoba.model.SubmitHomeworkExample;
import itf4.kaoba.util.ExcelUtil;
import itf4.kaoba.util.JsonPrintUtil;

@Controller
@RequestMapping("homework")
public class HomeWorkController {
 @Autowired
 private SubmitHomeworkMapper submitHomeworkMapper;
 @Autowired 
 private StuTeaCouMapper stuTeaCouMapper;
 @Autowired
 private StudentMapper studentMapper;
 
 /***
  * 根据课程 id 当前教师id 查询该门课程下所有学生名单
  * @param request
  * @param response
  * @param session
  * @param courseId
  * @param keywords
  * @param limit
  * @param page
  */
 @RequestMapping("getStuList")
 private void getStuList(HttpServletRequest request, HttpServletResponse response,HttpSession session,Integer courseId, String keywords, int limit,
			int page) {
		Teacher currentLoginUser = (Teacher) session.getAttribute("CurrentLoginUserInfo");
	  StuTeaCouExample example= new StuTeaCouExample();
		// 设置分页查询参数
		example.setStartRow((page - 1) * limit);
		example.setPageSize(limit);
		example.setOrderByClause("create_time desc,update_time desc");
		StuTeaCouExample.Criteria criteria = example.createCriteria();
		criteria.andCouIdEqualTo(courseId).andTeaIdEqualTo(currentLoginUser.getId()).andStatusEqualTo(1);
		
		/*if (keywords!=null&&keywords!="") {
			keywords = keywords.trim();
			keywords = "%" + keywords + "%";
			// and or联合查询
			//criteria.andCouIdEqualTo(Integer.parseInt(keywords)).andStatusEqualTo(1);
		} */
		// 分页查询
		List<StuTeaCou> stuTeaCous = stuTeaCouMapper.selectByExample(example);
		List<StuHomworkPojo> exportHomeworkPojos=new ArrayList<StuHomworkPojo>();
		int count = (int) stuTeaCouMapper.countByExample(example);
         for (StuTeaCou stuTeaCou : stuTeaCous) {
        	 StuHomworkPojo exHP=new StuHomworkPojo();
        	 exHP.setCourseId(stuTeaCou.getCouId());
        	 exHP.setStuId(stuTeaCou.getStuId());
        	 //学生成绩
        	 SubmitHomeworkExample example2=new SubmitHomeworkExample();
        	 SubmitHomeworkExample.Criteria criteria2=example2.createCriteria();
        	 criteria2.andCourseIdEqualTo(stuTeaCou.getCouId()).andStuIdEqualTo(stuTeaCou.getStuId());
        	List<SubmitHomework> submitHomeworks= submitHomeworkMapper.selectByExample(example2);
        	 if(submitHomeworks.size()>0) {
        	  exHP.setStuGrade(submitHomeworks.get(0).getGrade());
        	 }
        	 //学生姓名
        	 exHP.setStuName(studentMapper.selectByPrimaryKey(stuTeaCou.getStuId()).getStuName());
        	 //学生学号
        	 exHP.setLoginId(studentMapper.selectByPrimaryKey(stuTeaCou.getStuId()).getLoginId());
        	 exportHomeworkPojos.add(exHP);
         }
		ResponseJsonPageListBean listBean = new ResponseJsonPageListBean();
		listBean.setCode(0);
		listBean.setCount(count);
		listBean.setMsg("课程列表");
		listBean.setData(exportHomeworkPojos);
    
		// 日志记录及输出前台Json
		if (null != exportHomeworkPojos && exportHomeworkPojos.size() > 0) {
			JsonPrintUtil.printObjDataWithoutKey(response, listBean);
		} else {
			JsonPrintUtil.printObjDataWithoutKey(response, null);
		}
 }
 
	/***
	 * 保存学生成绩
	 * @param exportHomeworkPojo
	 * @param request
	 * @param response
	 * @param session
	 * @throws Exception
	 */
	@RequestMapping("save")
	@ResponseBody
	public void save(StuHomworkPojo exportHomeworkPojo, HttpServletRequest request, HttpServletResponse response, HttpSession session)  throws Exception{
		int count = 0;
		Teacher currentLoginUser = (Teacher) session.getAttribute("CurrentLoginUserInfo");
		SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		SubmitHomeworkExample example =new SubmitHomeworkExample();
		SubmitHomeworkExample.Criteria criteria =example.createCriteria();
		criteria.andCourseIdEqualTo(exportHomeworkPojo.getCourseId()).andStuIdEqualTo(exportHomeworkPojo.getStuId()).andTeacherIdEqualTo(currentLoginUser.getId());
		SubmitHomework submitHomework=new SubmitHomework();
		submitHomework.setGrade(exportHomeworkPojo.getStuGrade());
		submitHomework.setUpdateTime(time.format(new Date()));
		count= submitHomeworkMapper.updateByExampleSelective(submitHomework, example);	
			//输出前台Json
			if (count > 0) {
				JsonPrintUtil.printObjDataWithKey(response, count, "data");
			}
	}
	 
	  @RequestMapping("getStuHomeworkPhoto")
	    @ResponseBody
	    public void getHomeworkPhoto( HttpServletRequest request, HttpServletResponse response,
	    		int courseId,Integer studentId,String startDate,String endDate) {
		     String[] URLPath=null;
	    	Teacher currentLoginUser = (Teacher) request.getSession().getAttribute("CurrentLoginUserInfo");
	    	SubmitHomeworkExample example = new SubmitHomeworkExample();
	    	
	 		SubmitHomeworkExample.Criteria criteria = example.createCriteria();
	    	criteria.andTeacherIdEqualTo(currentLoginUser.getId()).andCourseIdEqualTo(courseId).andStuIdEqualTo(studentId).andStatusEqualTo(1);
	    	if(StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
	    		criteria.andCreateTimeBetween(startDate, endDate);
	    	}
	    	
	    	List<SubmitHomework> submitHomeworkList = submitHomeworkMapper.selectByExample(example);
	 		if(submitHomeworkList.size()>0) {
	 	        URLPath =	submitHomeworkList.get(0).getPhotoUrl().split(",");
	 		
	 		}
	 		
			JsonPrintUtil.printObjDataWithKey(response, URLPath, "data");

	    }
	    
	/**
	 * 导出指定时间段的学生成绩
	 * @param request
	 * @param response
	 * @param session
	 * @param startDate
	 * @param endDate
	 * @param courseId
	 */
	 @RequestMapping(value = "/export", method = RequestMethod.GET)  
	    public void export(HttpServletRequest request, HttpServletResponse response,HttpSession session,String startDate ,String endDate, Integer courseId) {  
			Teacher currentLoginUser = (Teacher) session.getAttribute("CurrentLoginUserInfo");
		 StuTeaCouExample example= new StuTeaCouExample();
		 StuTeaCouExample.Criteria criteria = example.createCriteria();
		 criteria.andCouIdEqualTo(courseId).andTeaIdEqualTo(currentLoginUser.getId()).andCreateTimeBetween(startDate, endDate).andStatusEqualTo(1);
		 List<StuTeaCou> stuTeaCous = stuTeaCouMapper.selectByExample(example);	
		 List<ExportHomeworkPojo> exportHomeworkPojos=new ArrayList<ExportHomeworkPojo>();
	         for (StuTeaCou stuTeaCou : stuTeaCous) {
	        	 ExportHomeworkPojo exHP=new ExportHomeworkPojo();	     
	        	 //学生成绩
	        	 SubmitHomeworkExample example2=new SubmitHomeworkExample();
	        	 SubmitHomeworkExample.Criteria criteria2=example2.createCriteria();
	        	 criteria2.andCourseIdEqualTo(stuTeaCou.getCouId()).andStuIdEqualTo(stuTeaCou.getStuId());
	        	List<SubmitHomework> submitHomeworks= submitHomeworkMapper.selectByExample(example2);
	        	 if(submitHomeworks.size()>0) {
	        	  exHP.setStuGrade(submitHomeworks.get(0).getGrade());
	        	 }
	        	 //学生姓名
	        	 exHP.setStuName(studentMapper.selectByPrimaryKey(stuTeaCou.getStuId()).getStuName());
	            //学生学号
	        	 exHP.setLoginId(studentMapper.selectByPrimaryKey(stuTeaCou.getStuId()).getLoginId());
	             exHP.setTime(stuTeaCou.getCreateTime());
	        	 exportHomeworkPojos.add(exHP);
	         }		          
	        ExcelUtil<ExportHomeworkPojo> ee= new ExcelUtil<ExportHomeworkPojo>();  
	        String[] headers = { "学号","姓名", "分数","时间"};  
	        String fileName = "学生成绩表";  
	        ee.exportExcel(headers,exportHomeworkPojos,fileName,response);   
	    }  
	
}
