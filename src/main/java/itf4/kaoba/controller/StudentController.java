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
import itf4.kaoba.mapper.AnswerDbMapper;
import itf4.kaoba.mapper.HomeworkMapper;
import itf4.kaoba.mapper.SingleDbMapper;
import itf4.kaoba.mapper.StuErrorMapper;
import itf4.kaoba.mapper.StuProMapper;
import itf4.kaoba.mapper.StuTeaCouMapper;
import itf4.kaoba.mapper.StudentMapper;
import itf4.kaoba.mapper.StudentMapperCustom;
import itf4.kaoba.mapper.SubmitHomeworkMapper;
import itf4.kaoba.model.Course;
import itf4.kaoba.model.Homework;
import itf4.kaoba.model.HomeworkExample;
import itf4.kaoba.model.SingleDb;
import itf4.kaoba.model.SingleDbCustom;
import itf4.kaoba.model.StuError;
import itf4.kaoba.model.StuErrorExample;
import itf4.kaoba.model.StuPro;
import itf4.kaoba.model.StuProExample;
import itf4.kaoba.model.StuTeaCou;
import itf4.kaoba.model.StuTeaCouExample;
import itf4.kaoba.model.Student;
import itf4.kaoba.model.StudentExample;
import itf4.kaoba.model.Teacher;
import itf4.kaoba.model.StudentExample.Criteria;
import itf4.kaoba.model.SubmitHomework;
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
public class StudentController extends UploadController {
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
	@Autowired
	private StudentMapperCustom studentMapperCustom;
	@Autowired
	private AnswerDbMapper answerDbMapper;
	@Autowired
	private StuProMapper stuProMapper;
	@Autowired
	private HomeworkMapper homeworkMapper;
	@Autowired
	private SubmitHomeworkMapper submitHomeworkMapper;

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

		if (keywords != null && keywords != "") {
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
	public void save(Student student, HttpServletRequest request, HttpServletResponse response, HttpSession session) {

		int count = 0;
		// SysUser currentLoginUser = (SysUser)
		// session.getAttribute("CurrentLoginUserInfo");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		// 编辑
		if (student.getId() != null && student.getId() > 0) {
			// teacher.setUpdater(currentLoginUser.getUserId() + "");
			student.setUpdateTime(sdf.format(new Date()));
			if (student.getStuPassword() != null) {
				student.setStuPassword(DigestUtils.md5DigestAsHex(student.getStuPassword().getBytes()));
			}
			count = studentMapper.updateByPrimaryKeySelective(student);
			// 输出前台Json
			if (count > 0) {
				JsonPrintUtil.printObjDataWithKey(response, student, "data");
			}
			// 保存
		} else {
			student.setStatus(1);// 正常为1
			student.setStuPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
			// teacher.setCreater(currentLoginUser.getUserId() + "");
			student.setCreateTime(sdf.format(new Date()));
			count = studentMapper.insert(student);

			if (count > 0) {
				JsonPrintUtil.printObjDataWithKey(response, count, "data");
			}

		}

	}

	// 学生信息查看
	@RequestMapping(value = "show", method = RequestMethod.POST)
	@ResponseBody
	public void show(int id, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		Student student = studentMapper.selectByPrimaryKey(id);
		if (student != null) {
			JsonPrintUtil.printObjDataWithKey(response, student, "data");
		} else {
			JsonPrintUtil.printObjDataWithKey(response, null, "data");
		}
	}

	// 学生批量删除
	@RequestMapping(value = "deleteBatch", method = RequestMethod.POST)
	@ResponseBody
	public void deleteBatch(String idStr, HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {

		// SysUser currentLoginUser = (SysUser)
		// session.getAttribute("CurrentLoginUserInfo");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		if (StringUtils.isNotBlank(idStr)) {
			String[] idArr = idStr.split(",");
			for (int i = 0; i < idArr.length; i++) {
				// 更新老师为删除状态
				int id = Integer.parseInt(idArr[i]);
				Student student = studentMapper.selectByPrimaryKey(id);
				student.setStatus(3);// 1正常 3已删除
				student.setUpdateTime(sdf.format(new Date()));
				// teacher.setUpdater(currentLoginUser.getUserId() + "");
				studentMapper.updateByPrimaryKeySelective(student);
			}
			// 输出前台Json
			JsonPrintUtil.printObjDataWithKey(response, 1, "data");
		} else {
			JsonPrintUtil.printObjDataWithKey(response, 0, "data");
		}
	}

	// 批量导入学生
	@RequestMapping("/insertStudentsBatch")
	@ResponseBody
	public int impots(HttpServletRequest request, HttpSession session, Model model) throws Exception {
		// 获取上传的文件
		MultipartHttpServletRequest multipart = (MultipartHttpServletRequest) request;
		MultipartFile file = multipart.getFile("upfile");
		InputStream in = file.getInputStream();
		// 数据导入
		int result = studentService.importUsersExcelInfo(session, in, file);
		in.close();
		return result;
	}

	/*
	 * 验证课程是否已经添加到课程表中
	 * 
	 * @yangfan
	 */
	@RequestMapping("validateCourse")
	@ResponseBody
	public void validateCourse(int courseId, HttpServletRequest request, HttpServletResponse response,
			Student student) {

		// 拼装查询条件
		StuTeaCouExample example = new StuTeaCouExample();
		itf4.kaoba.model.StuTeaCouExample.Criteria criteria = example.createCriteria();
		criteria.andCouIdEqualTo(courseId).andStuIdEqualTo(student.getId()).andStatusEqualTo(1);

		// 查询
		List<StuTeaCou> list = stuTeaCouMapper.selectByExample(example);

		// 输出前台Json
		if (list.size() > 0 && list != null) {
			JsonPrintUtil.printObjDataWithKey(response, 1, "data");
		} else {
			JsonPrintUtil.printObjDataWithKey(response, 0, "data");
		}

	}

	/*
	 * 将课程添加到学生课程表中
	 * 
	 * @yangfa
	 */
	@RequestMapping("addCourseToStuTeaCou")
	@ResponseBody
	public void addCourseToStuTeaCou(String courseIds, int teaId, HttpServletRequest request,
			HttpServletResponse response, Student student) {

		int count = 1;
		StuTeaCou stuTeaCou = new StuTeaCou();
		stuTeaCou.setCreater(student.getStuName());
		stuTeaCou.setCreateTime(DateUtil.DateToString(new Date(), "yyyy-mm-dd"));
		stuTeaCou.setStatus(1);

		// 将数据插入到数据库中
		if (courseIds != null && courseIds != "") {
			String[] courseId = courseIds.split(",");
			for (String id : courseId) {
				stuTeaCou.setCouId(Integer.parseInt(id));
				count = count & stuTeaCouMapper.insert(stuTeaCou);
				if (count == 0) {
					break;
				}
			}
		}

		// 返回结果
		JsonPrintUtil.printObjDataWithKey(response, count, "data");
	}

	/*
	 * 将课程从课程表中移除 根据学生id和课程id
	 * 
	 * @yangfan
	 */
	@RequestMapping("deleteCourseToStuTeaCou")
	@ResponseBody
	public void deleteCourseToStuTeaCou(String courseIds, HttpServletRequest request, HttpServletResponse response,
			Student student) {
		int count = 1;

		// 设置逻辑删除信息
		StuTeaCou stuTeaCou = new StuTeaCou();
		stuTeaCou.setUpdater(student.getStuName());
		stuTeaCou.setUpdateTime(DateUtil.DateToString(new Date(), "yyyy-mm-dd"));
		// 逻辑删除
		stuTeaCou.setStatus(0);

		// 更新课程
		if (courseIds != null && courseIds != "") {
			String[] courseId = courseIds.split(",");
			for (String couId : courseId) {
				StuTeaCouExample example = new StuTeaCouExample();
				itf4.kaoba.model.StuTeaCouExample.Criteria criteria = example.createCriteria();
				criteria.andCouIdEqualTo(Integer.parseInt(couId)).andStuIdEqualTo(student.getId());
				count = count & stuTeaCouMapper.updateByExampleSelective(stuTeaCou, example);
				if (count == 0) {
					break;
				}
			}
		}

		// 返回结果
		JsonPrintUtil.printObjDataWithKey(response, count, "data");

	}

	/*
	 * 获取学生选择的所有科目
	 * 
	 * @yagnfan
	 */
	@RequestMapping("getSelectCourseByStudentId")
	@ResponseBody
	public void getSelectCourseByStudentId(HttpServletResponse response, Student student) {
		List<Course> list = studentMapperCustom.getSelectCourseByStudentId(student.getId());
		if (list != null && list.size() > 0) {
			JsonPrintUtil.printJsonArrayWithoutKey(response, list);
		} else {
			JsonPrintUtil.printJsonArrayWithoutKey(response, null);
		}
	}

	// 插入错题本
	@RequestMapping(value = "insertStuError")
	@ResponseBody
	public void insertStuError(HttpServletRequest request, HttpServletResponse response, StuError stuError) {
		int result = 0;
		StuErrorExample example = new StuErrorExample();
		itf4.kaoba.model.StuErrorExample.Criteria criteria = example.createCriteria();
		criteria.andDbIdEqualTo(stuError.getDbId());
		// criteria.andStatusEqualTo(1);
		List<StuError> list = stuErrorMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			// 已存在此错题
			if (list.get(0).getStatus() == 0) {
				stuError.setStatus(1);
				stuErrorMapper.updateByExampleSelective(stuError, example);
			}
			result = 2;
		} else {
			stuError.setCreateTime(DateUtil.DateToString(new Date(), "yyyy-MM-dd "));
			stuError.setStuErrorDbtype(0);
			stuError.setStatus(1);
			// 插入数据库
			result = stuErrorMapper.insert(stuError);
		}
		// 返回1，添加成功
		JsonPrintUtil.printObjDataWithKey(response, result, "data");
	}

	// 查询错题本
	@RequestMapping(value = "StuErrorList")
	@ResponseBody
	public void StuErrorList(HttpServletRequest request, HttpServletResponse response, int courseId) {
		StuErrorExample example = new StuErrorExample();
		itf4.kaoba.model.StuErrorExample.Criteria criteria = example.createCriteria();
		criteria.andCourseIdEqualTo(courseId);
		criteria.andStatusEqualTo(1);
		List<StuError> list = stuErrorMapper.selectByExample(example);

		List<SingleDbCustom> singleDbList = new ArrayList<SingleDbCustom>();
		for (int i = 0; i < list.size(); i++) {
			int dbId = list.get(i).getDbId();
			SingleDbCustom singleDbCustom = singleDbMapper.selectDbAnById(dbId);
			singleDbList.add(singleDbCustom);
		}

		String singleDbListJson = JsonUtils.listToJson(singleDbList);

		JsonPrintUtil.printObjDataWithKey(response, singleDbListJson, "data");

	}

	// 刪除错题
	@RequestMapping(value = "deleteStuError")
	@ResponseBody
	public void deleteStuError(HttpServletRequest request, HttpServletResponse response, int dbId, String name) {
		// Student student = (Student)
		// request.getSession().getAttribute("CurrentLoginUserInfo");
		StuErrorExample example = new StuErrorExample();
		itf4.kaoba.model.StuErrorExample.Criteria criteria = example.createCriteria();
		criteria.andDbIdEqualTo(dbId);
		StuError stuError = new StuError();
		stuError.setStatus(0);
		stuError.setUpdater(name);
		stuError.setUpdateTime(DateUtil.DateToString(new Date(), "yyyy-MM-dd "));

		int result = stuErrorMapper.updateByExampleSelective(stuError, example);

		JsonPrintUtil.printObjDataWithKey(response, result, "data");
	}

	// 保存做题进度
	@RequestMapping(value = "insertStuPro")
	@ResponseBody
	public void insertStuPro(HttpServletRequest request, HttpServletResponse response, StuPro stuPro) {
		int result = 0;
		StuProExample example = new StuProExample();
		itf4.kaoba.model.StuProExample.Criteria criteria = example.createCriteria();
		criteria.andStuIdEqualTo(stuPro.getStuId());
		criteria.andCourseIdEqualTo(stuPro.getCourseId());
		criteria.andStuProDbtypeEqualTo(stuPro.getStuProDbtype());
		criteria.andStuProTypeEqualTo(stuPro.getStuProType());
		criteria.andStatusEqualTo(stuPro.getStatus());
		List<StuPro> list = stuProMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			// 已有进度，更新
			stuPro.setUpdateTime(DateUtil.DateToString(new Date(), "yyyy-MM-dd "));
			result = stuProMapper.updateByExampleSelective(stuPro, example);
			if (result == 1) {
				result = 2;
			}
		} else {
			// 插入进度
			stuPro.setCreateTime(DateUtil.DateToString(new Date(), "yyyy-MM-dd "));
			result = stuProMapper.insert(stuPro);
		}

		JsonPrintUtil.printObjDataWithKey(response, result, "data");
	}

	// 查看做题进度
	@RequestMapping(value = "selectStuPro")
	@ResponseBody
	public void selectStuPro(HttpServletRequest request, HttpServletResponse response, StuPro stuPro) {

		StuProExample example = new StuProExample();
		itf4.kaoba.model.StuProExample.Criteria criteria = example.createCriteria();
		criteria.andStuIdEqualTo(stuPro.getStuId());
		criteria.andCourseIdEqualTo(stuPro.getCourseId());
		criteria.andStuProDbtypeEqualTo(stuPro.getStuProDbtype());
		criteria.andStuProTypeEqualTo(stuPro.getStuProType());
		criteria.andStatusEqualTo(stuPro.getStatus());
		List<StuPro> list = stuProMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			StuPro stuProNow = list.get(0);
			int dbId = stuProNow.getDbId();
			JsonPrintUtil.printObjDataWithKey(response, dbId, "data");
		} else {
			JsonPrintUtil.printObjDataWithKey(response, null, "data");
		}
	}

	//获取作业
	@RequestMapping(value = "getHouseWork")
	@ResponseBody
	public void getHouseWork(HttpServletRequest request, HttpServletResponse response, 
			int courseId,int studentId,String createTime) {
//		查询老师id
		Integer teaId=null;
		StuTeaCouExample stuTeaCouExample = new StuTeaCouExample();
		itf4.kaoba.model.StuTeaCouExample.Criteria criteria = stuTeaCouExample.createCriteria();
		criteria.andCouIdEqualTo(courseId);
		criteria.andStuIdEqualTo(studentId);
		List<StuTeaCou> stuTeaCous = stuTeaCouMapper.selectByExample(stuTeaCouExample);
		if (stuTeaCous!=null && stuTeaCous.size()>0) {
			StuTeaCou stuTeaCou = stuTeaCous.get(0);
			teaId=stuTeaCou.getTeaId();
		}
		if (teaId!=null) {
			//查询作业
			HomeworkExample homeworkExample = new HomeworkExample();
			itf4.kaoba.model.HomeworkExample.Criteria criteria2 = homeworkExample.createCriteria();
			criteria2.andTeacherIdEqualTo(teaId);
			criteria2.andCourseIdEqualTo(courseId);
			criteria2.andStatusEqualTo(1);
			criteria2.andCreateTimeEqualTo(createTime);
			List<Homework> homeworks = homeworkMapper.selectByExample(homeworkExample);
			
			if(homeworks!=null && homeworks.size()>0) {
				JsonPrintUtil.printObjDataWithKey(response,homeworks, "data");
			}else {
				JsonPrintUtil.printObjDataWithKey(response,-1, "data");
			}
		}else {
			JsonPrintUtil.printObjDataWithKey(response,-1, "data");
		}
	}
	
	List<String> submitImageUrl = new ArrayList<String>();
	Integer imageCount = null;
	@RequestMapping("submitHomeworkPhoto")
    @ResponseBody
    public void uploadPhoto(HttpServletRequest request, HttpServletResponse response,MultipartFile file,String imageSum) {
			String photoUrl = super.uploadToFileUrl("submitHomework_photo", file, request);
			System.out.println(photoUrl);
			submitImageUrl.add(photoUrl);
			imageCount = Integer.parseInt(imageSum);
    }
	
	@RequestMapping("submitHomework")
    @ResponseBody
    public void submitHomework(HttpServletRequest request, HttpServletResponse response,int courseId,int stuId,String createTime) {
		int result = 0;
        SubmitHomework submitHomework = new SubmitHomework();
        if(submitImageUrl.size()<imageCount) {
        	JsonPrintUtil.printObjDataWithKey(response,"-1", "flag");
        	return;
        }
//		查询老师id
		Integer teaId=null;
		StuTeaCouExample stuTeaCouExample = new StuTeaCouExample();
		itf4.kaoba.model.StuTeaCouExample.Criteria criteria = stuTeaCouExample.createCriteria();
		criteria.andCouIdEqualTo(courseId);
		criteria.andStuIdEqualTo(stuId);
		List<StuTeaCou> stuTeaCous = stuTeaCouMapper.selectByExample(stuTeaCouExample);
		if (stuTeaCous!=null && stuTeaCous.size()>0) {
			StuTeaCou stuTeaCou = stuTeaCous.get(0);
			teaId=stuTeaCou.getTeaId();
		}
		if (teaId!=null) {
//			查询作业
			HomeworkExample homeworkExample = new HomeworkExample();
			itf4.kaoba.model.HomeworkExample.Criteria criteria2 = homeworkExample.createCriteria();
			criteria2.andTeacherIdEqualTo(teaId);
			criteria2.andCourseIdEqualTo(courseId);
			criteria2.andStatusEqualTo(1);
			criteria2.andCreateTimeEqualTo(createTime);
			List<Homework> homeworks = homeworkMapper.selectByExample(homeworkExample);
			
			//存一个作业id
			if(homeworks!=null && homeworks.size()>0) {
				Homework homework = homeworks.get(0);
				submitHomework.setTeacherId(homework.getTeacherId());
				submitHomework.setCourseId(courseId);
				submitHomework.setStuId(stuId);
				submitHomework.setHomeworkId(homework.getId());
				submitHomework.setStatus(1);
				submitHomework.setCreateTime(DateUtil.DateToString(new Date(), "yyyy-MM-dd "));
				//String photoUrl = super.uploadToFileUrl("submitHomework_photo", file, request);
				StringBuilder csvBuilder = new StringBuilder();
				for(String submitImageurl : submitImageUrl){
				  csvBuilder.append(submitImageurl);
				  csvBuilder.append(",");
				}
				String photoUrl = csvBuilder.toString();
				//String photoUrl = JsonUtils.listToJson(submitImageUrl);
				submitHomework.setPhotoUrl(photoUrl);
				submitHomework.setGrade(0);
				result = submitHomeworkMapper.insert(submitHomework);
				submitImageUrl.clear();
			}
		}
		if (result>0) {
			JsonPrintUtil.printObjDataWithKey(response,"1", "flag");
		} else {
			JsonPrintUtil.printObjDataWithKey(response,"-1", "flag");
		}
    }
	
	/*@RequestMapping("submitHomeworkPhoto")
    @ResponseBody
    public void uploadPhoto(HttpServletRequest request, HttpServletResponse response
                    ,MultipartFile file,int courseId,int stuId,String createTime) {
		int result = 0;
        SubmitHomework submitHomework = new SubmitHomework();
//		查询老师id
		Integer teaId=null;
		StuTeaCouExample stuTeaCouExample = new StuTeaCouExample();
		itf4.kaoba.model.StuTeaCouExample.Criteria criteria = stuTeaCouExample.createCriteria();
		criteria.andCouIdEqualTo(courseId);
		criteria.andStuIdEqualTo(stuId);
		List<StuTeaCou> stuTeaCous = stuTeaCouMapper.selectByExample(stuTeaCouExample);
		if (stuTeaCous!=null && stuTeaCous.size()>0) {
			StuTeaCou stuTeaCou = stuTeaCous.get(0);
			teaId=stuTeaCou.getTeaId();
		}
		if (teaId!=null) {
//			查询作业
			HomeworkExample homeworkExample = new HomeworkExample();
			itf4.kaoba.model.HomeworkExample.Criteria criteria2 = homeworkExample.createCriteria();
			criteria2.andTeacherIdEqualTo(teaId);
			criteria2.andCourseIdEqualTo(courseId);
			criteria2.andStatusEqualTo(1);
			criteria2.andCreateTimeEqualTo(createTime);
			List<Homework> homeworks = homeworkMapper.selectByExample(homeworkExample);
			
			//存一个作业id
			if(homeworks!=null && homeworks.size()>0) {
				Homework homework = homeworks.get(0);
				submitHomework.setTeacherId(homework.getTeacherId());
				submitHomework.setCourseId(courseId);
				submitHomework.setStuId(stuId);
				submitHomework.setHomeworkId(homework.getId());
				submitHomework.setStatus(1);
				submitHomework.setCreateTime(DateUtil.DateToString(new Date(), "yyyy-MM-dd "));
				String photoUrl = super.uploadToFileUrl("submitHomework_photo", file, request);
				submitHomework.setPhotoUrl(photoUrl);
				result = submitHomeworkMapper.insert(submitHomework);
			}
		}
		if (result>0) {
			JsonPrintUtil.printObjDataWithKey(response,"1", "flag");
		} else {
			JsonPrintUtil.printObjDataWithKey(response,"-1", "flag");
		}
    }*/

}
