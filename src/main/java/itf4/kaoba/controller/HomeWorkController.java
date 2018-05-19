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
import itf4.kaoba.util.ExcelUtil;
import itf4.kaoba.util.JsonPrintUtil;

@Controller
@RequestMapping("homework")
public class HomeWorkController {
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
	 * ���ĳ����ҵ�µ�ѧ������
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
		// ���÷�ҳ��ѯ����
		example.setStartRow((page - 1) * limit);
		example.setPageSize(limit);
		example.setOrderByClause("create_time desc,update_time desc");
		SubmitHomeworkExample.Criteria criteria = example.createCriteria();
		criteria.andHomeworkIdEqualTo(homeworkId).andStatusEqualTo(1);

		// ��ҳ��ѯ
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
		listBean.setMsg("�γ��б�");
		listBean.setData(stuHomworkPojos);

		// ��־��¼�����ǰ̨Json
		if (null != stuHomworkPojos && stuHomworkPojos.size() > 0) {
			JsonPrintUtil.printObjDataWithoutKey(response, listBean);
		} else {
			JsonPrintUtil.printObjDataWithoutKey(response, null);
		}
	}

	/**
	 * ����ĳ����ҵ�µ�ĳһ��ѧ���ɼ�
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
		criteria.andStuIdEqualTo(stuHomworkPojo.getStuId()).andHomeworkIdEqualTo(stuHomworkPojo.getHomeworkId()).andStatusEqualTo(1);
		SubmitHomework submitHomework = new SubmitHomework();
		submitHomework.setGrade(stuHomworkPojo.getStuGrade());
		submitHomework.setUpdateTime(time.format(new Date()));
		count = submitHomeworkMapper.updateByExampleSelective(submitHomework, example);
		// ���ǰ̨Json
		JsonPrintUtil.printObjDataWithKey(response, count, "data");
	}
/**
 *�õ���ѧ������ҵ
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
	 * ����ĳ����ҵ��ѧ���ɼ�
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
		String[] headers = { "ѧ��", "����", "����", "��ҵ����", "����ʱ��" };
		String fileName = "ѧ���ɼ���";
		ee.exportExcel(headers, exportHomeworkPojos, fileName, response);
	}

	/***
	 * ��ҵ��
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("homeworkTreeList")
	@ResponseBody
	public void teaCouTreeList(HttpServletRequest request, HttpServletResponse response) {
		//�õ���ʦ�̵Ŀγ�
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
		//�õ��ÿγ��µ���ҵ
		for (Course course : courses) {	
			HomeworkExample example2=new HomeworkExample();
			HomeworkExample.Criteria  criteria2= example2.createCriteria();
			 criteria2.andCourseIdEqualTo(course.getId()).andTeacherIdEqualTo(teacher.getId()).andStatusEqualTo(1);
			List<Homework> homeworks= homeworkMapper.selectByExample(example2);
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
}
