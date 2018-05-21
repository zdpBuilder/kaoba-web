package itf4.kaoba.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import itf4.kaoba.common.ResponseJsonPageListBean;
import itf4.kaoba.mapper.CourseMapper;
import itf4.kaoba.mapper.HomeworkMapper;
import itf4.kaoba.mapper.StuTeaCouMapper;
import itf4.kaoba.mapper.StudentMapper;
import itf4.kaoba.mapper.SubmitHomeworkMapper;
import itf4.kaoba.mapper.TeacherCourseMapper;
import itf4.kaoba.model.Course;
import itf4.kaoba.model.Homework;
import itf4.kaoba.model.HomeworkExample;
import itf4.kaoba.model.StuTeaCou;
import itf4.kaoba.model.StuTeaCouExample;
import itf4.kaoba.model.Student;
import itf4.kaoba.model.SubmitHomework;
import itf4.kaoba.model.SubmitHomeworkExample;
import itf4.kaoba.model.Teacher;
import itf4.kaoba.model.TeacherCourse;
import itf4.kaoba.model.TeacherCourseExample;
import itf4.kaoba.pojo.ExportHomeworkPojo;
import itf4.kaoba.pojo.StuHomworkPojo;
import itf4.kaoba.pojo.TreePojo;
import itf4.kaoba.util.DateUtil;
import itf4.kaoba.util.ExcelUtil;
import itf4.kaoba.util.JsonPrintUtil;

@Controller
@RequestMapping("homework")
public class HomeWorkController extends UploadController {
	@Autowired
	private SubmitHomeworkMapper submitHomeworkMapper;
	@Autowired
	private HomeworkMapper homeworkMapper;
	@Autowired
	private StudentMapper studentMapper;
	@Autowired
	private TeacherCourseMapper teacherCourseMapper;
	@Autowired
	private CourseMapper courseMapper;

	/***
	 * 查出某次作业下的学生名单
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param homeworkId
	 * @param limit
	 * @param page
	 */
	@RequestMapping("getStuList")
	private void getStuList(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			Integer homeworkId, int limit, int page) {

		SubmitHomeworkExample example = new SubmitHomeworkExample();
		// 设置分页查询参数
		example.setStartRow((page - 1) * limit);
		example.setPageSize(limit);
		example.setOrderByClause("create_time desc,update_time desc");
		SubmitHomeworkExample.Criteria criteria = example.createCriteria();
		criteria.andHomeworkIdEqualTo(homeworkId).andStatusEqualTo(1);

		// 分页查询
		List<SubmitHomework> submitHomeworks = submitHomeworkMapper.selectByExample(example);
		List<StuHomworkPojo> stuHomworkPojos = new ArrayList<StuHomworkPojo>();
		int count = (int) submitHomeworkMapper.countByExample(example);
		for (SubmitHomework sumHw : submitHomeworks) {
			Student student = studentMapper.selectByPrimaryKey(sumHw.getStuId());
			StuHomworkPojo skPojo = new StuHomworkPojo();
			skPojo.setHomeworkId(sumHw.getHomeworkId());
			skPojo.setLoginId(student.getLoginId());
			skPojo.setStuGrade(sumHw.getGrade());
			skPojo.setStuId(student.getId());
			skPojo.setStuName(student.getStuName());
			stuHomworkPojos.add(skPojo);
		}
		ResponseJsonPageListBean listBean = new ResponseJsonPageListBean();
		listBean.setCode(0);
		listBean.setCount(count);
		listBean.setMsg("课程列表");
		listBean.setData(stuHomworkPojos);

		// 日志记录及输出前台Json
		if (null != stuHomworkPojos && stuHomworkPojos.size() > 0) {
			JsonPrintUtil.printObjDataWithoutKey(response, listBean);
		} else {
			JsonPrintUtil.printObjDataWithoutKey(response, null);
		}
	}

	/**
	 * 保存某次作业下的某一个学生成绩
	 * 
	 * @param stuHomworkPojo
	 * @param request
	 * @param response
	 * @param session
	 * @throws Exception
	 */
	@RequestMapping("save")
	@ResponseBody
	public void save(StuHomworkPojo stuHomworkPojo, HttpServletRequest request, HttpServletResponse response,
			HttpSession session) throws Exception {

		int count = 0;
		SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SubmitHomeworkExample example = new SubmitHomeworkExample();
		SubmitHomeworkExample.Criteria criteria = example.createCriteria();
		criteria.andStuIdEqualTo(stuHomworkPojo.getStuId()).andHomeworkIdEqualTo(stuHomworkPojo.getHomeworkId())
				.andStatusEqualTo(1);
		SubmitHomework submitHomework = new SubmitHomework();
		submitHomework.setGrade(stuHomworkPojo.getStuGrade());
		submitHomework.setUpdateTime(time.format(new Date()));
		count = submitHomeworkMapper.updateByExampleSelective(submitHomework, example);
		// 输出前台Json
		JsonPrintUtil.printObjDataWithKey(response, count, "data");
	}

	/**
	 * 得到该学生的作业
	 * 
	 * @param request
	 * @param response
	 * @param homeworkId
	 * @param studentId
	 */
	@RequestMapping("getStuHomeworkPhoto")
	@ResponseBody
	public void getHomeworkPhoto(HttpServletRequest request, HttpServletResponse response, Integer homeworkId,
			Integer studentId) {
		String[] URLPath = null;
		SubmitHomeworkExample example = new SubmitHomeworkExample();
		SubmitHomeworkExample.Criteria criteria = example.createCriteria();
		criteria.andStuIdEqualTo(studentId).andHomeworkIdEqualTo(homeworkId).andStatusEqualTo(1);

		List<SubmitHomework> submitHomeworkList = submitHomeworkMapper.selectByExample(example);
		if (submitHomeworkList.size() > 0) {
			URLPath = submitHomeworkList.get(0).getPhotoUrl().split(",");

		}

		JsonPrintUtil.printObjDataWithKey(response, URLPath, "data");

	}

	/***
	 * 导出某次作业的学生成绩
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param homeworkId
	 */
	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public void export(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			Integer homeworkId) {

		SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SubmitHomeworkExample example = new SubmitHomeworkExample();
		SubmitHomeworkExample.Criteria criteria = example.createCriteria();
		criteria.andHomeworkIdEqualTo(homeworkId).andStatusEqualTo(1);
		List<SubmitHomework> submitHomeworks = submitHomeworkMapper.selectByExample(example);
		List<ExportHomeworkPojo> exportHomeworkPojos = new ArrayList<ExportHomeworkPojo>();
		for (SubmitHomework sumHw : submitHomeworks) {
			Student student = studentMapper.selectByPrimaryKey(sumHw.getStuId());
			ExportHomeworkPojo expHPojo = new ExportHomeworkPojo();
			expHPojo.setLoginId(student.getLoginId());
			expHPojo.setStuGrade(sumHw.getGrade());
			expHPojo.setStuName(student.getStuName());
			expHPojo.setHomworkName(homeworkMapper.selectByPrimaryKey(homeworkId).getName());
			expHPojo.setExportTime(time.format(new Date()));
			exportHomeworkPojos.add(expHPojo);
		}

		ExcelUtil<ExportHomeworkPojo> ee = new ExcelUtil<ExportHomeworkPojo>();
		String[] headers = { "学号", "姓名", "分数", "作业名称", "导出时间" };
		String fileName = "学生成绩表";
		ee.exportExcel(headers, exportHomeworkPojos, fileName, response);
	}

	/***
	 * 作业树
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("homeworkTreeList")
	@ResponseBody
	public void teaCouTreeList(HttpServletRequest request, HttpServletResponse response) {
		// 得到老师教的课程
		Teacher teacher = (Teacher) request.getSession().getAttribute("CurrentLoginUserInfo");
		List<TreePojo> treePojos = new ArrayList<TreePojo>();
		List<Course> courses = new ArrayList<Course>();
		TeacherCourseExample example = new TeacherCourseExample();
		TeacherCourseExample.Criteria criteria = example.createCriteria();
		criteria.andTeaIdEqualTo(teacher.getId());
		List<TeacherCourse> teacherCourseList = teacherCourseMapper.selectByExample(example);
		if (teacherCourseList.size() > 0) {
			String[] coIdStrings = teacherCourseList.get(0).getCourseId().split(",");
			for (String id : coIdStrings) {
				courses.add(courseMapper.selectByPrimaryKey(Integer.parseInt(id)));
			}
			for (Course course : courses) {
				TreePojo treePojo = new TreePojo();
				treePojo.setId(course.getId());
				treePojo.setPid(course.getPid());
				treePojo.setName(course.getCourseName());
				treePojos.add(treePojo);
			}
		}
		// 得到该课程下的作业
		for (Course course : courses) {
			HomeworkExample example2 = new HomeworkExample();
			HomeworkExample.Criteria criteria2 = example2.createCriteria();
			criteria2.andCourseIdEqualTo(course.getId()).andTeacherIdEqualTo(teacher.getId()).andStatusEqualTo(1);
			List<Homework> homeworks = homeworkMapper.selectByExample(example2);
			for (Homework homework : homeworks) {
				TreePojo treePojo = new TreePojo();
				treePojo.setId(homework.getId());
				treePojo.setPid(homework.getCourseId());
				treePojo.setName(homework.getName());
				treePojos.add(treePojo);
			}

		}

		JsonPrintUtil.printJsonArrayWithoutKey(response, treePojos);
	}
	
	/**
	 * 老师添加作业
	 * @param request
	 * @param response
	 * @param homework
	 */
	@RequestMapping("addHomeworkNode")
	@ResponseBody
	public void addHomework(HttpServletRequest request, HttpServletResponse response,
			Homework homework) {
		//判空处理
		if(StringUtils.isNotBlank(homework.getName()) && homework.getCourseId() !=null) {
			Teacher currentLoginUser = (Teacher) request.getSession().getAttribute("CurrentLoginUserInfo");
			
			homework.setStatus(1);
			homework.setTeacherId(currentLoginUser.getId());
			homework.setCreateTime(DateUtil.DateToString(new Date(), "yyyy-MM-dd"));
			
			int result = homeworkMapper.insert(homework);
			
			if(result > 0) {
				JsonPrintUtil.printObjDataWithKey(response, homework, "data");
			}
		}
		
	}
	
	/**
	 * 修改作业名称
	 * @param request
	 * @param response
	 * @param homework
	 */
	@RequestMapping("editHomeworkNode")
	@ResponseBody
	public void editHomework(HttpServletRequest request, HttpServletResponse response, Homework homework) {
		//判空处理
		if(homework.getId() !=null && StringUtils.isNotBlank(homework.getName())) {
			homework.setUpdateTime(DateUtil.DateToString(new Date(), "yyyy-MM-dd"));
			
			int result = homeworkMapper.updateByPrimaryKeySelective(homework);
			
			if(result >0) {
				JsonPrintUtil.printObjDataWithKey(response, "ok", "data");
			}
		}
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param homework
	 */
	@RequestMapping("deleteHomeworkNode")
	@ResponseBody
	public void deleteHomework(HttpServletRequest request, HttpServletResponse response, Homework homework) {
		//判空处理
		if(homework.getId() !=null) {
			homework.setStatus(0);
			homework.setUpdateTime(DateUtil.DateToString(new Date(), "yyyy-MM-dd"));
			
			int result = homeworkMapper.updateByPrimaryKeySelective(homework);
			
			if(result >0) {
				JsonPrintUtil.printObjDataWithKey(response, "ok", "data");
			}
		}
	}
	
	/**
	 * 作业上传
	 * @param courseId
	 * @param request
	 * @param response
	 * @param file
	 */
    @RequestMapping("uploadHomeworkPhoto")
    @ResponseBody
    public void uploadPhoto(String homeworkId, HttpServletRequest request, HttpServletResponse response
                    ,MultipartFile file) {
        Teacher currentLoginUser = (Teacher) request.getSession().getAttribute("CurrentLoginUserInfo");
        int result = 1;
        // 照片保存目录
        String photoUrl = "";
        if(StringUtils.isNotBlank(homeworkId)) {
        	//正则校验
        	Homework homework = homeworkMapper.selectByPrimaryKey(Integer.parseInt(homeworkId));
        	photoUrl = super.uploadToFileUrl("homework_photo", file, request);
        	homework.setCreateTime(DateUtil.DateToString(new Date(), "yyyy-MM-dd"));
        	if(StringUtils.isNotBlank(homework.getPhotoUrl())) {
        		photoUrl = homework.getPhotoUrl() +","+photoUrl;
        	}
        	homework.setPhotoUrl(photoUrl);
        	result = homeworkMapper.updateByPrimaryKeySelective(homework);
            
        } else {
        	result = 2;
        }
        if(result == 1) {
        	JsonPrintUtil.printObjDataWithKey(response, photoUrl, "data");
        } else {
        	JsonPrintUtil.printObjDataWithKey(response, result, "data");
        }
        
    }

    /**
     * 得到作业的照片
     * @param homeworkId
     * @param request
     * @param response
     */
    @RequestMapping("getHomeworkPhoto")
    @ResponseBody
    public void getHomeworkPhoto(String homeworkId, HttpServletRequest request, HttpServletResponse response) {
    	
    	boolean flag = false;
    	Homework homework=null;
    	//判空处理
    	if(StringUtils.isNotBlank(homeworkId)) {
    		//正则校验?
    	    homework = homeworkMapper.selectByPrimaryKey(Integer.parseInt(homeworkId));
    		if(homework!=null && StringUtils.isNotBlank(homework.getPhotoUrl())) {
    			flag = true;
    		}
    	}
    	
    	if(flag) {
    		JsonPrintUtil.printObjDataWithKey(response, homework, "data");
    	}else {
    		JsonPrintUtil.printObjDataWithKey(response, null, "data");
    	}
    }
    
    /**
     * 删除作业照片
     * @param homeworkId
     * @param request
     * @param response
     */
    @RequestMapping("deleteHomeworkPhoto")
    @ResponseBody
    public void deleteHomework(HttpServletRequest request, HttpServletResponse response,
    		String homeworkId,String photoUrl) {
    	int result = 0;
    	
    	//判空
    	if(StringUtils.isNotBlank(photoUrl) && StringUtils.isNotBlank(homeworkId)) {
    		//正则校验？
    		Homework homework = homeworkMapper.selectByPrimaryKey(Integer.parseInt(homeworkId));
    		
    		if(homework !=null && StringUtils.isNotBlank(homework.getPhotoUrl())) {
    			String[] photoUrls = homework.getPhotoUrl().split(",");
    			String homeworkUrl = "";
    			for(int i=0;i<photoUrls.length;i++) {
    				if(photoUrls[i].equals(photoUrl)) {
    					continue;
    				}
    				homeworkUrl+=photoUrls[i]+",";
    			}
    			
    			if(StringUtils.isNotBlank(homeworkUrl)) {
    				homeworkUrl = homeworkUrl.substring(0, homeworkUrl.length()-1);
    			}
    			
    			homework.setPhotoUrl(homeworkUrl);
    		}
    		homework.setId(Integer.parseInt(homeworkId));
    		homework.setUpdateTime(DateUtil.DateToString(new Date(), "yyyy-MM-dd"));
        	
    		result = homeworkMapper.updateByPrimaryKeySelective(homework);
    	}
    	
    	JsonPrintUtil.printObjDataWithKey(response, result, "data");
    }
}
