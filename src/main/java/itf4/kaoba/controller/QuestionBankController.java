package itf4.kaoba.controller;

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
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;

import itf4.kaoba.common.ResponseJsonPageListBean;
import itf4.kaoba.mapper.AnswerDbMapper;
import itf4.kaoba.mapper.CourseMapper;
import itf4.kaoba.mapper.SingleDbMapper;
import itf4.kaoba.mapper.TeacherCourseMapper;
import itf4.kaoba.model.Course;
import itf4.kaoba.model.CourseExample;
import itf4.kaoba.model.CourseExample.Criteria;
import itf4.kaoba.model.SingleDb;
import itf4.kaoba.model.SingleDbExample;
import itf4.kaoba.model.SysUser;
import itf4.kaoba.model.Teacher;
import itf4.kaoba.model.TeacherCourse;
import itf4.kaoba.model.TeacherCourseExample;
import itf4.kaoba.pojo.SelectCourseTreeJsonListPojo;
import itf4.kaoba.pojo.TreePojo;
import itf4.kaoba.util.Const;
import itf4.kaoba.util.DateUtil;
import itf4.kaoba.util.JsonPrintUtil;
import lombok.extern.log4j.Log4j;


@Log4j
@Controller
@RequestMapping("courseVsSingleDb")
public class QuestionBankController {

	@Autowired
	private  CourseMapper courseMapper;
	@Autowired
	private TeacherCourseMapper teacherCourseMapper;
	@Autowired
	private  SingleDbMapper singleDbMapper;
	@Autowired
	private AnswerDbMapper answerDbMapper;

	/****************START �γ̷�����״�˵����� START*********************/
	
	//��״�˵���ѯ
	@RequestMapping("treeList")
	@ResponseBody
	public void treeList( HttpServletRequest request,HttpServletResponse response) {
		Teacher teacher = (Teacher)request.getSession().getAttribute("CurrentLoginUserInfo");
       
        List<TreePojo> treePojos=new ArrayList<TreePojo>();
        
        TeacherCourseExample example0 = new TeacherCourseExample();
        itf4.kaoba.model.TeacherCourseExample.Criteria critria0 = example0.createCriteria();
        critria0.andTeaIdEqualTo(teacher.getId());
        List<TeacherCourse> teaCourseList = teacherCourseMapper.selectByExample(example0);
        
        if(teaCourseList !=null &&teaCourseList.size()>0 ) {
        	String courseIds = teaCourseList.get(0).getCourseId();
        	if(courseIds !=null && courseIds!="") {
        		String []courseIdArray = courseIds.split(",");
        		
        		for (String courseId : courseIdArray) {
        			CourseExample example = new CourseExample();
            		Criteria criteria = example.createCriteria();
        			criteria.andIdEqualTo(Integer.parseInt(courseId)).andStatusEqualTo(1);//����״̬�ڵ�
        			List<Course> list = courseMapper.selectByExample(example);
        			//����γ�
        			if(list !=null && list.size()>0) {
        				Course course =new Course();
        				course = list.get(0);
        				TreePojo treePojo=new TreePojo();
        				treePojo.setId(course.getId());
        				treePojo.setName(course.getCourseName());
        				treePojo.setPid(course.getPid());
        				treePojos.add(treePojo);
        				
        				//���½�
        				CourseExample example2 = new CourseExample();
        				Criteria criteria2 = example2.createCriteria();
        				criteria2.andPidEqualTo(course.getId()).andStatusEqualTo(1);
        				List<Course> courseList = courseMapper.selectByExample(example2);
        				if(courseList!=null && courseList.size()>0) {
        					for (Course course2 : courseList) {
        						TreePojo treePojo2=new TreePojo();
        						treePojo2.setId(course2.getId());
        						treePojo2.setName(course2.getCourseName());
        						treePojo2.setPid(course2.getPid());
                				treePojos.add(treePojo2);
							}
        				}
        			}
        			//log.debug("�˴β�ѯ���ڵ���Ŀ: "+list.size());
        			//�б��ѯ��־��¼
        			//SysUser currentLoginUser = (SysUser) request.getSession().getAttribute("CurrentLoginUserInfo");
        			//SysLog sysLog = SysLogUtil.getSysLog(request, currentLoginUser, SysModuleConstant.M_EQUIPMENT_TYPE, SysLogConstant.SELECT_LIST, "", "emergency_equipment", "�γ̷������Ͳ˵���ѯ�� "+list.size()+" ��");
        			///sysLogMapper.insertSelective(sysLog);
				}
        	}
        	
        	
        }
        
		JsonPrintUtil.printJsonArrayWithoutKey(response, treePojos);
	}
	
	//��ȡ�ڵ�
	@RequestMapping("showNode")
	@ResponseBody
	public void showNode( HttpServletRequest request, HttpServletResponse response,Integer id,String nodeName) {
		SysUser user = (SysUser)request.getSession().getAttribute("CurrentLoginUserInfo");
		
		Course course = courseMapper.selectByPrimaryKey(id);
		if(null!=course){
			//��־��¼
			//SysLog sysLog = SysLogUtil.getSysLog(request, user, SysModuleConstant.M_EQUIPMENT_TYPE, SysLogConstant.SELECT_ONE, et.getId()+"", "emergency_equipment_type", "��ѯ�γ̷�����Ϣ["+et.getName()+"]�ɹ�");
			//sysLogMapper.insertSelective(sysLog);
			JsonPrintUtil.printObjDataWithKey(response, course,"data");
		}
	}
	
	//��ӽڵ�
	@RequestMapping("addTreeNode")
	@ResponseBody
	public void addTreeNode( HttpServletRequest request, HttpServletResponse response,Integer pid,String nodeName) {
		String CurrentUserName ="";
		if(request.getSession().getAttribute(Const.SESSION_USER_STATUS).equals("1")) { 
			  SysUser user = (SysUser)request.getSession().getAttribute(Const.SESSION_USER);  
			  CurrentUserName= user.getUserName();
		 }else if(request.getSession().getAttribute(Const.SESSION_USER_STATUS).equals("2")) {
			 Teacher teacher = (Teacher)request.getSession().getAttribute(Const.SESSION_USER);  
			  CurrentUserName= teacher.getTeaName();
		 }
		
		Course record = new Course();
		record.setPid(pid);
		record.setCourseName(nodeName);
		record.setStatus(1);//����״̬	 
		 record.setCreater(CurrentUserName);
		record.setCreateTime(DateUtil.DateToString(new Date(), "yyyy-MM-dd "));
		int count = courseMapper.insert(record);
		if(count>0){
			//��־��¼
			//SysLog sysLog = SysLogUtil.getSysLog(request, user, SysModuleConstant.M_EQUIPMENT_TYPE, SysLogConstant.CREATE, record.getId()+"", "emergency_equipment_type", "�����γ̷�������["+nodeName+"]�ɹ�");
			///sysLogMapper.insertSelective(sysLog);
			JsonPrintUtil.printObjDataWithKey(response, record,"data");
		}
	}
	
	//�޸Ľڵ�
	@RequestMapping("editTreeNode")
	@ResponseBody
	public void editTreeNode(HttpServletRequest request, HttpServletResponse response,Integer id,String nodeName) {
		String CurrentUserName ="";
		if(request.getSession().getAttribute(Const.SESSION_USER_STATUS).equals("1")) { 
			  SysUser user = (SysUser)request.getSession().getAttribute(Const.SESSION_USER);  
			  CurrentUserName= user.getUserName();
		 }else if(request.getSession().getAttribute(Const.SESSION_USER_STATUS).equals("2")) {
			 Teacher teacher = (Teacher)request.getSession().getAttribute(Const.SESSION_USER);  
			  CurrentUserName= teacher.getTeaName();
		 }
		Course record = new Course();
		record = courseMapper.selectByPrimaryKey(id);
		String oldNodeName = record.getCourseName(); 
		record.setCourseName(nodeName);
		record.setUpdater(CurrentUserName);
		record.setUpdateTime(DateUtil.DateToString(new Date(), "yyyy-MM-dd "));
		int count = courseMapper.updateByPrimaryKey(record);
		if(count>0){
			//��־��¼
			//SysLog sysLog = SysLogUtil.getSysLog(request, user, SysModuleConstant.M_EQUIPMENT_TYPE, SysLogConstant.UPDATE, record.getId()+"", "emergency_equipment_type", "�޸Ŀγ̷�������["+oldNodeName+"]Ϊ["+nodeName+"]�ɹ�");
			//sysLogMapper.insertSelective(sysLog);
			JsonPrintUtil.printObjDataWithKey(response, "ok", "data");
		}
	}
	
	//ɾ���ڵ�
	@RequestMapping("deleteTreeNode")
	@ResponseBody
	public void deleteTreeNode(HttpServletRequest request, HttpServletResponse response,Integer id) {
		String CurrentUserName ="";
		if(request.getSession().getAttribute(Const.SESSION_USER_STATUS).equals("1")) { 
			  SysUser user = (SysUser)request.getSession().getAttribute(Const.SESSION_USER);  
			  CurrentUserName= user.getUserName();
		 }else if(request.getSession().getAttribute(Const.SESSION_USER_STATUS).equals("2")) {
			 Teacher teacher = (Teacher)request.getSession().getAttribute(Const.SESSION_USER);  
			  CurrentUserName= teacher.getTeaName();
		 }
		Course record = new Course();
		record = courseMapper.selectByPrimaryKey(id);
		record.setStatus(0);//ɾ��״̬
		record.setUpdater(CurrentUserName);
		record.setUpdateTime(DateUtil.DateToString(new Date(), "yyyy-MM-dd "));
		int count = courseMapper.updateByPrimaryKey(record);
		SingleDbExample example= new SingleDbExample();
		SingleDbExample.Criteria criteria= example.createCriteria();
		criteria.andCourseIdEqualTo(record.getId());
		List<SingleDb> list=singleDbMapper.selectByExample(example);
		if(list.size()>0) {
			for (SingleDb singleDb : list) {
				singleDb.setStatus(0);
				singleDbMapper.updateByPrimaryKey(singleDb);
			}
		}
		if(count>0){
			//��־��¼
			//SysLog sysLog = SysLogUtil.getSysLog(request, user, SysModuleConstant.M_EQUIPMENT_TYPE, SysLogConstant.DELETE, record.getId()+"", "emergency_equipment_type", "ɾ���γ̷�������["+record.getName()+"]�ɹ�");
			//sysLogMapper.insertSelective(sysLog);
			JsonPrintUtil.printObjDataWithKey(response, "ok", "data");
		}
	}
	/****************END �γ̷�����״�˵�����  END*********************/
	
	
	/****************START �����Ϣ���� START*********************/
	//�γ̷����б��ѯ
	@RequestMapping("list")
	@ResponseBody
	public void equipmentList( HttpServletRequest request,HttpServletResponse response,String keywords,int pid,int limit,int page) {
		SingleDbExample example = new SingleDbExample();
		example.setStartRow((page-1)*limit);
		example.setPageSize(limit);
		example.setOrderByClause("create_time desc,update_time desc");
		SingleDbExample.Criteria criteria = example.createCriteria();
		
		if(StringUtils.isNotBlank(keywords)){
			keywords = keywords.trim();
			keywords = "%" + keywords + "%";
			//and or���ϲ�ѯ
			example.or().andSingledbTitleLike(keywords).andStatusEqualTo(1);
		}else{
			criteria.andStatusEqualTo(1);//����״̬
		}
		
		if(pid!=1){
			//��ѯ�ڵ���������
			CourseExample example1 = new CourseExample();
			Criteria criteria1 = example1.createCriteria();
			criteria1.andStatusEqualTo(1);//����״̬�ڵ�
			criteria1.andPidEqualTo(pid);
			List<Course> coursesList = courseMapper.selectByExample(example1);
			List<Integer> valuesList = new ArrayList<Integer>();
			for(Course et: coursesList){
				valuesList.add(et.getId());
			}
			valuesList.add(pid);
			criteria.andCourseIdIn(valuesList);
		}else{
			//���ڵ��ѯ���� ���ӹ�������
		}
		
		
		List<SingleDb> list = singleDbMapper.selectByExample(example);
		int count = (int) singleDbMapper.countByExample(example);
		//log.debug("�˴β�ѯ�б�size: "+count);
		
		ResponseJsonPageListBean listBean = new ResponseJsonPageListBean();
		listBean.setCode(0);
		listBean.setCount(count);
		listBean.setMsg("�γ̷��������б�");
		
	/*	List<EquipmentTypeJsonBean> typeJsonBeanList = new ArrayList<EquipmentTypeJsonBean>();
		EquipmentTypeJsonBean typeJsonBean = null;
		for(EmergencyEquipment ee : list){
			typeJsonBean = new EquipmentTypeJsonBean();
			BeanUtil.copyProperties(ee, typeJsonBean);
			
			String type = equipmentTypeMapper.selectByPrimaryKey(equipmentTypeMapper.selectByPrimaryKey(ee.getTypeId()).getPid()).getName();
			String name = equipmentTypeMapper.selectByPrimaryKey(ee.getTypeId()).getName();
			typeJsonBean.setType(type);
			typeJsonBean.setName(name);
			String creater =sysUserMapper.selectByPrimaryKey(Integer.parseInt(ee.getCreater())).getUserName();
			typeJsonBean.setCreater(creater);
			
			DateTime dt = new DateTime(ee.getCreateTime());
			typeJsonBean.setCreateTime(dt.toString("yyyy-MM-dd HH:mm:ss"));
			
			typeJsonBeanList.add(typeJsonBean);
		}*/
		
		listBean.setData(list);
		
		//�б��ѯ��־��¼
		//SysUser currentLoginUser = (SysUser) request.getSession().getAttribute("CurrentLoginUserInfo");
		//SysLog sysLog = SysLogUtil.getSysLog(request, currentLoginUser, SysModuleConstant.M_EQUIPMENT_TYPE, SysLogConstant.SELECT_LIST, "", "emergency_equipment", "�б��ѯ�� "+typeJsonBeanList.size()+" ��");
		//sysLogMapper.insertSelective(sysLog);
		JsonPrintUtil.printObjDataWithoutKey(response, listBean);
		
	}
	
	
	/*//�γ����ͺͷ������������˵���ѯ
	@RequestMapping("selectList")
	@ResponseBody
	public void selectList( HttpServletRequest request,HttpServletResponse response) {
		
		CourseExample example = new CourseExample();
		Criteria criteria = example.createCriteria();
		criteria.andPidEqualTo(1);//��ѯһ���ڵ�
		criteria.andStatusEqualTo(1);//����״̬�ڵ�
		
		List<SelectCourseTreeJsonListPojo> selectBeanList = new ArrayList<SelectCourseTreeJsonListPojo>();
 		
		//һ���γ����ͽڵ��б�
		List<Course> list = courseMapper.selectByExample(example);
		SelectCourseTreeJsonListPojo selectBean = null;
		
		List<Course> childList = null;
		for(Course eeType : list){
			selectBean = new SelectCourseTreeJsonListPojo();
			selectBean.setPid(eeType.getId());
			selectBean.setName(eeType.getCourseName());
			CourseExample example2= new CourseExample();
			 Criteria criteria2=example2.createCriteria();
			 criteria2.andPidEqualTo(eeType.getId());
			childList = courseMapper.selectByExample(example2);
			selectBean.setChildList(childList);
			selectBeanList.add(selectBean);
		}
		
		//�б��ѯ��־��¼
		SysUser currentLoginUser = (SysUser) request.getSession().getAttribute("CurrentLoginUserInfo");
		//SysLog sysLog = SysLogUtil.getSysLog(request, currentLoginUser, SysModuleConstant.M_EQUIPMENT_TYPE, SysLogConstant.SELECT_LIST, "", "emergency_equipment_type", "�γ̷��������˵���ѯ�� "+selectBeanList.size()+" ��");
		///sysLogMapper.insertSelective(sysLog);
		//String selectBeanListJson = JSON.toJSONString(selectBeanList);
		//log.info(selectBeanListJson);
		JsonPrintUtil.printJsonArrayWithoutKey(response, selectBeanList);
	}
*/
	//�鿴������������Ϣ
	@RequestMapping(value = "show", method = RequestMethod.POST)
	@ResponseBody
	public void showEE(int id,HttpServletRequest request,HttpServletResponse response,HttpSession session) {
		//��ѯ�����γ̷�����Ϣ
		SingleDb eeShow =  singleDbMapper.selectByPrimaryKey(id);
		if(null!=eeShow){
			//��¼��־
			//Gson gson = new Gson();
			//String eeShowJson = gson.toJson(eeShow);
			//SysUser currentLoginUser = (SysUser) session.getAttribute("CurrentLoginUserInfo");
			//SysLog sysLog = SysLogUtil.getSysLog(request, currentLoginUser, SysModuleConstant.M_EQUIPMENT_TYPE, 
				//	SysLogConstant.SELECT_ONE, eeShow.getId()+"", "emergency_equipment", "��ѯ�Ŀγ̷�����ϢJson-->"+eeShowJson);
		//	sysLogMapper.insertSelective(sysLog);
			JsonPrintUtil.printObjDataWithKey(response, eeShow,"ee");
		}
	}
	
	//�����ͱ���γ̷�����Ϣ
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public void saveEE(SingleDb ee,HttpServletRequest request,HttpServletResponse response,HttpSession session) {
		int count = 0;
		String CurrentUserName ="";
		if(request.getSession().getAttribute(Const.SESSION_USER_STATUS).equals("1")) { 
			  SysUser user = (SysUser)request.getSession().getAttribute(Const.SESSION_USER);  
			  CurrentUserName= user.getUserName();
		 }else if(request.getSession().getAttribute(Const.SESSION_USER_STATUS).equals("2")) {
			 Teacher teacher = (Teacher)request.getSession().getAttribute(Const.SESSION_USER);  
			  CurrentUserName= teacher.getTeaName();
		 }		
		if(null!=ee.getId() && ee.getId()>0){
			//������Ϊ�� ��༭�γ���Ϣ
			SingleDb eeOld = singleDbMapper.selectByPrimaryKey(ee.getId());
			//Gson gson = new Gson();
			//String eeOldJson = gson.toJson(eeOld);
			//BeanUtil.copyProperties(ee, eeOld);
			//String eeJson = gson.toJson(eeOld);
			eeOld.setUpdater(CurrentUserName);
			eeOld.setUpdateTime(DateUtil.DateToString(new Date(), "yyyy-MM-dd "));
			count = singleDbMapper.updateByPrimaryKeySelective(eeOld);
			//updateByPrimaryKeySelective ǰ��ֻ�Ǹ����µ�model�в�Ϊ�յ��ֶ�
			//updateByPrimaryKey ������ὫΪ�յ��ֶ������ݿ�����ΪNULL
			//��¼�༭��־
			if(count>0){
			///	SysLog sysLog = SysLogUtil.getSysLog(request, currentLoginUser, SysModuleConstant.M_EQUIPMENT_TYPE, 
			//			SysLogConstant.UPDATE, ee.getId()+"", "emergency_equipment", "�༭ǰJson-->"+eeOldJson+"  �༭��Json-->"+eeJson);
			//	sysLogMapper.insertSelective(sysLog);
				JsonPrintUtil.printObjDataWithKey(response, eeOld,"data");
			}
		}else{
			//����Ϊ�� ����ӿγ̷�����Ϣ
			ee.setStatus(1);
			ee.setCreater(CurrentUserName);
			ee.setCreateTime(DateUtil.DateToString(new Date(), "yyyy-MM-dd "));
			count = singleDbMapper.insert(ee);
			//��¼�����־
			if(count>0){
				Gson gson = new Gson();
				String eeAddJson = gson.toJson(ee);
				//SysLog sysLog = SysLogUtil.getSysLog(request, currentLoginUser, SysModuleConstant.M_EQUIPMENT_TYPE, 
					//	SysLogConstant.CREATE, ee.getId()+"", "emergency_equipment", "�����γ̷�����Ϣ�ɹ� Json-->"+eeAddJson);
				//sysLogMapper.insertSelective(sysLog);
				JsonPrintUtil.printObjDataWithKey(response, ee,"data");
			}
		}
	}
	
	//����ɾ���γ̷�����Ϣ
	@RequestMapping("deleteBatch")
	@ResponseBody
	public void deleteBatch(String eeIds,HttpServletRequest request,HttpSession session,HttpServletResponse response) {
		String CurrentUserName ="";
		if(request.getSession().getAttribute(Const.SESSION_USER_STATUS).equals("1")) { 
			  SysUser user = (SysUser)request.getSession().getAttribute(Const.SESSION_USER);  
			  CurrentUserName= user.getUserName();
		 }else if(request.getSession().getAttribute(Const.SESSION_USER_STATUS).equals("2")) {
			 Teacher teacher = (Teacher)request.getSession().getAttribute(Const.SESSION_USER);  
			  CurrentUserName= teacher.getTeaName();
		 }		
		if(StringUtils.isNotBlank(eeIds)){
			String[] eeIdArr = eeIds.split(",");
			for(int i=0;i<eeIdArr.length;i++){
				//������ѡ�γ̷���Ϊɾ��״̬
				int eeId = Integer.parseInt(eeIdArr[i]);
				SingleDb ee = singleDbMapper.selectByPrimaryKey(eeId);
				ee.setStatus(3);//1����  3��ɾ�� 
				ee.setUpdateTime(DateUtil.DateToString(new Date(), "yyyy-MM-dd "));
				ee.setUpdater(CurrentUserName);
				singleDbMapper.updateByPrimaryKeySelective(ee);
			}
			//��¼����ɾ����־
			//SysLog sysLog = SysLogUtil.getSysLog(request, currentLoginUser, SysModuleConstant.M_EQUIPMENT_TYPE, 
			//			SysLogConstant.BATCH_DELETE, eeIds, "emergency_equipment", "����ɾ���γ̷�����Ϣ�ɹ�");
			//sysLogMapper.insertSelective(sysLog);
			JsonPrintUtil.printObjDataWithKey(response, 1,"data");
			
		}else{
			JsonPrintUtil.printObjDataWithKey(response, 0,"data");
		}
	}
	
	//�γ�Ĭ����Ƭ�ϴ�
	@RequestMapping("uploadEEPhoto")
	@ResponseBody
	public void uploadPhoto(String businessType,HttpServletRequest request,HttpServletResponse response,MultipartFile file) {  
		//SysUser currentLoginUser = (SysUser)request.getSession().getAttribute("CurrentLoginUserInfo");
		//�ֿ���Ƭ����Ŀ¼
	    //String photoUrl = super.uploadToFileUrl("emergency_equipment", file , request);
		//JsonPrintUtil.printObjDataWithKey(response, photoUrl,"data");
		
		//��¼��־
		//SysLog sysLog = SysLogUtil.getSysLog(request, currentLoginUser, SysModuleConstant.M_EQUIPMENT_TYPE, 
		//		SysLogConstant.UPLOAD, "", "emergency_equipment", "�ϴ��Ŀγ���Ƭ����·��-->"+photoUrl);
	//	sysLogMapper.insertSelective(sysLog);	
	}
	
	/****************END �γ̷�����Ϣ���� END*********************/
	
	
	
	
}
