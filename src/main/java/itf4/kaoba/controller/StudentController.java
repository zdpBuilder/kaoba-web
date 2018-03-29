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
 * ѧ������
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
		
		// limit ÿҳ��ʾ����
 		// page ��ǰҳ��
		StudentExample example = new StudentExample();
		// ���÷�ҳ��ѯ����
 		example.setStartRow((page - 1) * limit);
 		example.setPageSize(limit);
 		example.setOrderByClause("create_time desc,update_time desc");
 		Criteria criteria = example.createCriteria();
 		
 		if (keywords!=null&&keywords!="") {
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
	public void save(Student student,HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		
		int count = 0;
		//SysUser currentLoginUser = (SysUser) session.getAttribute("CurrentLoginUserInfo");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		//�༭
		if(student.getId()!=null &&student.getId()>0) {
			//teacher.setUpdater(currentLoginUser.getUserId() + "");
			student.setUpdateTime(sdf.format(new Date()));
 			if(student.getStuPassword() !=null) {
 				student.setStuPassword(DigestUtils.md5DigestAsHex(student.getStuPassword().getBytes()));
 			}
			count = studentMapper.updateByPrimaryKeySelective(student);
			//���ǰ̨Json
			if (count > 0) {
				JsonPrintUtil.printObjDataWithKey(response, student, "data");
			}
	    //����
		}else {
			student.setStatus(1);//����Ϊ1
			student.setStuPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
			//teacher.setCreater(currentLoginUser.getUserId() + "");
			student.setCreateTime(sdf.format(new Date()));
 			count = studentMapper.insert(student);
 			
 			if(count >0) {
 				JsonPrintUtil.printObjDataWithKey(response, count, "data");
 			}
			
		}
		
	}
	
	//ѧ����Ϣ�鿴
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
	
	//ѧ������ɾ��
	@RequestMapping(value = "deleteBatch", method = RequestMethod.POST)
 	@ResponseBody
 	public void deleteBatch(String idStr, HttpServletRequest request, HttpServletResponse response,
 			HttpSession session) {
		
		//SysUser currentLoginUser = (SysUser) session.getAttribute("CurrentLoginUserInfo");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		if (StringUtils.isNotBlank(idStr)) {
 			String[] idArr = idStr.split(",");
 			for (int i = 0; i < idArr.length; i++) {
 				// ������ʦΪɾ��״̬
 				int id = Integer.parseInt(idArr[i]);
 				Student student = studentMapper.selectByPrimaryKey(id);
 				student.setStatus(3);// 1���� 3��ɾ��
 				student.setUpdateTime(sdf.format(new Date()));
 				//teacher.setUpdater(currentLoginUser.getUserId() + "");
 				studentMapper.updateByPrimaryKeySelective(student);
 			}
 			// ���ǰ̨Json
 			JsonPrintUtil.printObjDataWithKey(response, 1, "data");
 		} else {
 			JsonPrintUtil.printObjDataWithKey(response, 0, "data");
 		}
	}

	//��������ѧ��
	@RequestMapping("/insertStudentsBatch")  
	@ResponseBody
	public int impots(HttpServletRequest request,HttpSession session, Model model) throws Exception{
		//��ȡ�ϴ����ļ�  
        MultipartHttpServletRequest multipart = (MultipartHttpServletRequest) request;  
        MultipartFile file = multipart.getFile("upfile");       
        InputStream in = file.getInputStream(); 
        //���ݵ���  
	    int result= studentService.importUsersExcelInfo(session,in, file); 
	    in.close();  
	    return result;  
	}
	
	//��֤�γ��Ƿ��Ѿ���ӵ��γ̱���
	@RequestMapping("validateCourse")
	@ResponseBody
	public void validateCourse(int courseId,HttpServletRequest request, HttpServletResponse response,
 			HttpSession session) {
		
		//��ȡ��¼ѧ����Ϣ
		Student student = (Student)session.getAttribute("CurrentLoginUserInfo");
		
		//ƴװ��ѯ����
		StuTeaCouExample example = new StuTeaCouExample();
		itf4.kaoba.model.StuTeaCouExample.Criteria criteria = example.createCriteria();
		criteria.andCouIdEqualTo(courseId).andStuIdEqualTo(student.getId()).andStatusEqualTo(1);
		
		//��ѯ
		List<StuTeaCou> list = stuTeaCouMapper.selectByExample(example);
		
		// ���ǰ̨Json
		if(list.size()>0 && list !=null) {
 			JsonPrintUtil.printObjDataWithKey(response, 1, "data");
		} else {
			JsonPrintUtil.printObjDataWithKey(response, 0, "data");
		}
		
	}
	
	//���γ���ӵ�ѧ���γ̱���
	@RequestMapping("addCourseToStuTeaCou")
	@ResponseBody
	public void addCourseToStuTeaCou(String courseIds,int teaId,HttpServletRequest request, HttpServletResponse response,
 			HttpSession session) {
		
		//��ȡ��¼ѧ����Ϣ
		Student student = (Student)session.getAttribute("CurrentLoginUserInfo");
		int count =1;
		StuTeaCou stuTeaCou = new StuTeaCou();
		stuTeaCou.setCreater(student.getStuName());
		stuTeaCou.setCreateTime(DateUtil.DateToString(new Date(), "yyyy-mm-dd"));
		stuTeaCou.setStatus(1);
		
		//�����ݲ��뵽���ݿ���
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
		
		//���ؽ��
		JsonPrintUtil.printObjDataWithKey(response, count, "data");
	}
	
	//���γ̴ӿγ̱����Ƴ�
	@RequestMapping("deleteCourseToStuTeaCou")
	@ResponseBody
	public void deleteCourseToStuTeaCou(String stuTeaCouIds,HttpServletRequest request, HttpServletResponse response
			,HttpSession session) {
		//��ȡ��¼ѧ����Ϣ
		Student student = (Student)session.getAttribute("CurrentLoginUserInfo");
		int count =1;
		StuTeaCou stuTeaCou = new StuTeaCou();
		stuTeaCou.setUpdater(student.getStuName());
		stuTeaCou.setUpdateTime(DateUtil.DateToString(new Date(), "yyyy-mm-dd"));
		//�߼�ɾ��
		stuTeaCou.setStatus(0);
		
		//���¿γ�״̬
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
		
		//���ؽ��
		JsonPrintUtil.printObjDataWithKey(response, count, "data");
		
	}
	
	//������Ȿ
		@RequestMapping("/insertStuError")
		@ResponseBody
		public void insertStuError(HttpServletRequest request, HttpServletResponse response,StuError stuError) {
			Student student = (Student) request.getSession().getAttribute("CurrentLoginUserInfo");
			stuError.setStuId(student.getId());
			stuError.setCreater(student.getStuName());
			stuError.setCreateTime(DateUtil.DateToString(new Date(), "yyyy-MM-dd "));
			stuError.setStuErrorDbtype(0);
			stuError.setStatus(1);
			//�γ�����
			//������
			//�������ݿ�
			int result = stuErrorMapper.insert(stuError);
			//����1����ӳɹ�
			JsonPrintUtil.printObjDataWithKey(response, result, "data");
		}
		
		// ��ѯ���Ȿ
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
		
		// �h������
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
