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
 * ѧ������
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

		// limit ÿҳ��ʾ����
		// page ��ǰҳ��
		StudentExample example = new StudentExample();
		// ���÷�ҳ��ѯ����
		example.setStartRow((page - 1) * limit);
		example.setPageSize(limit);
		example.setOrderByClause("create_time desc,update_time desc");
		Criteria criteria = example.createCriteria();

		if (keywords != null && keywords != "") {
			keywords = keywords.trim();
			keywords = "%" + keywords + "%";
			// and or���ϲ�ѯ
			example.or().andStuNameLike(keywords).andStatusEqualTo(1);
			example.or().andLoginIdLike(keywords).andStatusEqualTo(1);
		} else {
			criteria.andStatusEqualTo(1);// ����״̬
		}

		// ��ҳ��ѯ
		List<Student> sudentList = studentMapper.selectByExample(example);
		int count = (int) studentMapper.countByExample(example);

		ResponseJsonPageListBean listBean = new ResponseJsonPageListBean();
		listBean.setCode(0);
		listBean.setCount(count);
		listBean.setMsg("ѧ���б�");
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
		// �༭
		if (student.getId() != null && student.getId() > 0) {
			// teacher.setUpdater(currentLoginUser.getUserId() + "");
			student.setUpdateTime(sdf.format(new Date()));
			if (student.getStuPassword() != null) {
				student.setStuPassword(DigestUtils.md5DigestAsHex(student.getStuPassword().getBytes()));
			}
			count = studentMapper.updateByPrimaryKeySelective(student);
			// ���ǰ̨Json
			if (count > 0) {
				JsonPrintUtil.printObjDataWithKey(response, student, "data");
			}
			// ����
		} else {
			student.setStatus(1);// ����Ϊ1
			student.setStuPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
			// teacher.setCreater(currentLoginUser.getUserId() + "");
			student.setCreateTime(sdf.format(new Date()));
			count = studentMapper.insert(student);

			if (count > 0) {
				JsonPrintUtil.printObjDataWithKey(response, count, "data");
			}

		}

	}

	// ѧ����Ϣ�鿴
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

	// ѧ������ɾ��
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
				// ������ʦΪɾ��״̬
				int id = Integer.parseInt(idArr[i]);
				Student student = studentMapper.selectByPrimaryKey(id);
				student.setStatus(3);// 1���� 3��ɾ��
				student.setUpdateTime(sdf.format(new Date()));
				// teacher.setUpdater(currentLoginUser.getUserId() + "");
				studentMapper.updateByPrimaryKeySelective(student);
			}
			// ���ǰ̨Json
			JsonPrintUtil.printObjDataWithKey(response, 1, "data");
		} else {
			JsonPrintUtil.printObjDataWithKey(response, 0, "data");
		}
	}

	// ��������ѧ��
	@RequestMapping("/insertStudentsBatch")
	@ResponseBody
	public int impots(HttpServletRequest request, HttpSession session, Model model) throws Exception {
		// ��ȡ�ϴ����ļ�
		MultipartHttpServletRequest multipart = (MultipartHttpServletRequest) request;
		MultipartFile file = multipart.getFile("upfile");
		InputStream in = file.getInputStream();
		// ���ݵ���
		int result = studentService.importUsersExcelInfo(session, in, file);
		in.close();
		return result;
	}

	/*
	 * ��֤�γ��Ƿ��Ѿ���ӵ��γ̱���
	 * 
	 * @yangfan
	 */
	@RequestMapping("validateCourse")
	@ResponseBody
	public void validateCourse(int courseId, HttpServletRequest request, HttpServletResponse response,
			Student student) {

		// ƴװ��ѯ����
		StuTeaCouExample example = new StuTeaCouExample();
		itf4.kaoba.model.StuTeaCouExample.Criteria criteria = example.createCriteria();
		criteria.andCouIdEqualTo(courseId).andStuIdEqualTo(student.getId()).andStatusEqualTo(1);

		// ��ѯ
		List<StuTeaCou> list = stuTeaCouMapper.selectByExample(example);

		// ���ǰ̨Json
		if (list.size() > 0 && list != null) {
			JsonPrintUtil.printObjDataWithKey(response, 1, "data");
		} else {
			JsonPrintUtil.printObjDataWithKey(response, 0, "data");
		}

	}

	/*
	 * ���γ���ӵ�ѧ���γ̱���
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

		// �����ݲ��뵽���ݿ���
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

		// ���ؽ��
		JsonPrintUtil.printObjDataWithKey(response, count, "data");
	}

	/*
	 * ���γ̴ӿγ̱����Ƴ� ����ѧ��id�Ϳγ�id
	 * 
	 * @yangfan
	 */
	@RequestMapping("deleteCourseToStuTeaCou")
	@ResponseBody
	public void deleteCourseToStuTeaCou(String courseIds, HttpServletRequest request, HttpServletResponse response,
			Student student) {
		int count = 1;

		// �����߼�ɾ����Ϣ
		StuTeaCou stuTeaCou = new StuTeaCou();
		stuTeaCou.setUpdater(student.getStuName());
		stuTeaCou.setUpdateTime(DateUtil.DateToString(new Date(), "yyyy-mm-dd"));
		// �߼�ɾ��
		stuTeaCou.setStatus(0);

		// ���¿γ�
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

		// ���ؽ��
		JsonPrintUtil.printObjDataWithKey(response, count, "data");

	}

	/*
	 * ��ȡѧ��ѡ������п�Ŀ
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

	// ������Ȿ
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
			// �Ѵ��ڴ˴���
			if (list.get(0).getStatus() == 0) {
				stuError.setStatus(1);
				stuErrorMapper.updateByExampleSelective(stuError, example);
			}
			result = 2;
		} else {
			stuError.setCreateTime(DateUtil.DateToString(new Date(), "yyyy-MM-dd "));
			stuError.setStuErrorDbtype(0);
			stuError.setStatus(1);
			// �������ݿ�
			result = stuErrorMapper.insert(stuError);
		}
		// ����1����ӳɹ�
		JsonPrintUtil.printObjDataWithKey(response, result, "data");
	}

	// ��ѯ���Ȿ
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

	// �h������
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

	// �����������
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
			// ���н��ȣ�����
			stuPro.setUpdateTime(DateUtil.DateToString(new Date(), "yyyy-MM-dd "));
			result = stuProMapper.updateByExampleSelective(stuPro, example);
			if (result == 1) {
				result = 2;
			}
		} else {
			// �������
			stuPro.setCreateTime(DateUtil.DateToString(new Date(), "yyyy-MM-dd "));
			result = stuProMapper.insert(stuPro);
		}

		JsonPrintUtil.printObjDataWithKey(response, result, "data");
	}

	// �鿴�������
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

	//��ȡ��ҵ
	@RequestMapping(value = "getHouseWork")
	@ResponseBody
	public void getHouseWork(HttpServletRequest request, HttpServletResponse response, 
			int courseId,int studentId,String createTime) {
//		��ѯ��ʦid
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
			//��ѯ��ҵ
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
//		��ѯ��ʦid
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
//			��ѯ��ҵ
			HomeworkExample homeworkExample = new HomeworkExample();
			itf4.kaoba.model.HomeworkExample.Criteria criteria2 = homeworkExample.createCriteria();
			criteria2.andTeacherIdEqualTo(teaId);
			criteria2.andCourseIdEqualTo(courseId);
			criteria2.andStatusEqualTo(1);
			criteria2.andCreateTimeEqualTo(createTime);
			List<Homework> homeworks = homeworkMapper.selectByExample(homeworkExample);
			
			//��һ����ҵid
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
//		��ѯ��ʦid
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
//			��ѯ��ҵ
			HomeworkExample homeworkExample = new HomeworkExample();
			itf4.kaoba.model.HomeworkExample.Criteria criteria2 = homeworkExample.createCriteria();
			criteria2.andTeacherIdEqualTo(teaId);
			criteria2.andCourseIdEqualTo(courseId);
			criteria2.andStatusEqualTo(1);
			criteria2.andCreateTimeEqualTo(createTime);
			List<Homework> homeworks = homeworkMapper.selectByExample(homeworkExample);
			
			//��һ����ҵid
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
