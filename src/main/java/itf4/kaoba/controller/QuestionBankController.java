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

	/****************START 课程分类树状菜单操作 START*********************/
	
	//树状菜单查询
	@RequestMapping("treeList")
	@ResponseBody
	public void treeList( HttpServletRequest request,HttpServletResponse response) {
		Teacher teacher = (Teacher)request.getSession().getAttribute("CurrentLoginUserInfo");
        TeacherCourseExample example0 = new TeacherCourseExample();
        List<TreePojo> treePojos=new ArrayList<TreePojo>();
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
        			criteria.andIdEqualTo(Integer.parseInt(courseId)).andStatusEqualTo(1);//正常状态节点
        			List<Course> list = courseMapper.selectByExample(example);
        			//log.debug("此次查询树节点数目: "+list.size());
        			//列表查询日志记录
        			//SysUser currentLoginUser = (SysUser) request.getSession().getAttribute("CurrentLoginUserInfo");
        			//SysLog sysLog = SysLogUtil.getSysLog(request, currentLoginUser, SysModuleConstant.M_EQUIPMENT_TYPE, SysLogConstant.SELECT_LIST, "", "emergency_equipment", "课程分类树型菜单查询共 "+list.size()+" 条");
        			///sysLogMapper.insertSelective(sysLog);
        			for (Course course : list) {
        				TreePojo treePojo=new TreePojo();
        				treePojo.setId(course.getId());
        				treePojo.setName(course.getCourseName());
        				treePojo.setPid(course.getPid());
        				treePojos.add(treePojo);
        			}
				}
        	}
        	
        	
        }
        
		JsonPrintUtil.printJsonArrayWithoutKey(response, treePojos);
	}
	
	//获取节点
	@RequestMapping("showNode")
	@ResponseBody
	public void showNode( HttpServletRequest request, HttpServletResponse response,Integer id,String nodeName) {
		SysUser user = (SysUser)request.getSession().getAttribute("CurrentLoginUserInfo");
		
		Course course = courseMapper.selectByPrimaryKey(id);
		if(null!=course){
			//日志记录
			//SysLog sysLog = SysLogUtil.getSysLog(request, user, SysModuleConstant.M_EQUIPMENT_TYPE, SysLogConstant.SELECT_ONE, et.getId()+"", "emergency_equipment_type", "查询课程分类信息["+et.getName()+"]成功");
			//sysLogMapper.insertSelective(sysLog);
			JsonPrintUtil.printObjDataWithKey(response, course,"data");
		}
	}
	
	//添加节点
	@RequestMapping("addTreeNode")
	@ResponseBody
	public void addTreeNode( HttpServletRequest request, HttpServletResponse response,Integer pid,String nodeName) {
		SysUser user = (SysUser)request.getSession().getAttribute("CurrentLoginUserInfo");
		Course record = new Course();
		record.setPid(pid);
		record.setCourseName(nodeName);
		record.setStatus(1);//正常状态
		record.setCreater(user.getUserName()+"");
		record.setCreateTime(DateUtil.DateToString(new Date(), "yyyy-MM-dd "));
		int count = courseMapper.insert(record);
		if(count>0){
			//日志记录
			//SysLog sysLog = SysLogUtil.getSysLog(request, user, SysModuleConstant.M_EQUIPMENT_TYPE, SysLogConstant.CREATE, record.getId()+"", "emergency_equipment_type", "新增课程分类名称["+nodeName+"]成功");
			///sysLogMapper.insertSelective(sysLog);
			JsonPrintUtil.printObjDataWithKey(response, record,"data");
		}
	}
	
	//修改节点
	@RequestMapping("editTreeNode")
	@ResponseBody
	public void editTreeNode(HttpServletRequest request, HttpServletResponse response,Integer id,String nodeName) {
		SysUser user = (SysUser)request.getSession().getAttribute("CurrentLoginUserInfo");
		Course record = new Course();
		record = courseMapper.selectByPrimaryKey(id);
		String oldNodeName = record.getCourseName(); 
		record.setCourseName(nodeName);
		record.setUpdater(user.getUserName()+"");
		record.setUpdateTime(DateUtil.DateToString(new Date(), "yyyy-MM-dd "));
		int count = courseMapper.updateByPrimaryKey(record);
		if(count>0){
			//日志记录
			//SysLog sysLog = SysLogUtil.getSysLog(request, user, SysModuleConstant.M_EQUIPMENT_TYPE, SysLogConstant.UPDATE, record.getId()+"", "emergency_equipment_type", "修改课程分类名称["+oldNodeName+"]为["+nodeName+"]成功");
			//sysLogMapper.insertSelective(sysLog);
			JsonPrintUtil.printObjDataWithKey(response, "ok", "data");
		}
	}
	
	//删除节点
	@RequestMapping("deleteTreeNode")
	@ResponseBody
	public void deleteTreeNode(HttpServletRequest request, HttpServletResponse response,Integer id) {
		SysUser user = (SysUser)request.getSession().getAttribute("CurrentLoginUserInfo");
		Course record = new Course();
		record = courseMapper.selectByPrimaryKey(id);
		record.setStatus(0);//删除状态
		record.setUpdater(user.getUserName()+"");
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
			//日志记录
			//SysLog sysLog = SysLogUtil.getSysLog(request, user, SysModuleConstant.M_EQUIPMENT_TYPE, SysLogConstant.DELETE, record.getId()+"", "emergency_equipment_type", "删除课程分类名称["+record.getName()+"]成功");
			//sysLogMapper.insertSelective(sysLog);
			JsonPrintUtil.printObjDataWithKey(response, "ok", "data");
		}
	}
	/****************END 课程分类树状菜单操作  END*********************/
	
	
	/****************START 题库信息操作 START*********************/
	//课程分类列表查询
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
			//and or联合查询
			example.or().andSingledbTitleLike(keywords).andStatusEqualTo(1);
		}else{
			criteria.andStatusEqualTo(1);//正常状态
		}
		
		if(pid!=1){
			//查询节点所有子类
			CourseExample example1 = new CourseExample();
			Criteria criteria1 = example1.createCriteria();
			criteria1.andStatusEqualTo(1);//正常状态节点
			criteria1.andPidEqualTo(pid);
			List<Course> coursesList = courseMapper.selectByExample(example1);
			List<Integer> valuesList = new ArrayList<Integer>();
			for(Course et: coursesList){
				valuesList.add(et.getId());
			}
			valuesList.add(pid);
			criteria.andCourseIdIn(valuesList);
		}else{
			//根节点查询所有 不加过滤条件
		}
		
		
		List<SingleDb> list = singleDbMapper.selectByExample(example);
		int count = (int) singleDbMapper.countByExample(example);
		//log.debug("此次查询列表size: "+count);
		
		ResponseJsonPageListBean listBean = new ResponseJsonPageListBean();
		listBean.setCode(0);
		listBean.setCount(count);
		listBean.setMsg("课程分类名称列表");
		
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
		
		//列表查询日志记录
		SysUser currentLoginUser = (SysUser) request.getSession().getAttribute("CurrentLoginUserInfo");
		//SysLog sysLog = SysLogUtil.getSysLog(request, currentLoginUser, SysModuleConstant.M_EQUIPMENT_TYPE, SysLogConstant.SELECT_LIST, "", "emergency_equipment", "列表查询共 "+typeJsonBeanList.size()+" 条");
		//sysLogMapper.insertSelective(sysLog);
		JsonPrintUtil.printObjDataWithoutKey(response, listBean);
		
	}
	
	
	//课程类型和分类名称下拉菜单查询
	@RequestMapping("selectList")
	@ResponseBody
	public void selectList( HttpServletRequest request,HttpServletResponse response) {
		
		CourseExample example = new CourseExample();
		Criteria criteria = example.createCriteria();
		criteria.andPidEqualTo(1);//查询一级节点
		criteria.andStatusEqualTo(1);//正常状态节点
		
		List<SelectCourseTreeJsonListPojo> selectBeanList = new ArrayList<SelectCourseTreeJsonListPojo>();
 		
		//一级课程类型节点列表
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
		
		//列表查询日志记录
		SysUser currentLoginUser = (SysUser) request.getSession().getAttribute("CurrentLoginUserInfo");
		//SysLog sysLog = SysLogUtil.getSysLog(request, currentLoginUser, SysModuleConstant.M_EQUIPMENT_TYPE, SysLogConstant.SELECT_LIST, "", "emergency_equipment_type", "课程分类下拉菜单查询共 "+selectBeanList.size()+" 条");
		///sysLogMapper.insertSelective(sysLog);
		//String selectBeanListJson = JSON.toJSONString(selectBeanList);
		//log.info(selectBeanListJson);
		JsonPrintUtil.printJsonArrayWithoutKey(response, selectBeanList);
	}

	//查看单条题库分类信息
	@RequestMapping(value = "show", method = RequestMethod.POST)
	@ResponseBody
	public void showEE(int id,HttpServletRequest request,HttpServletResponse response,HttpSession session) {
		//查询单条课程分类信息
		SingleDb eeShow =  singleDbMapper.selectByPrimaryKey(id);
		if(null!=eeShow){
			//记录日志
			//Gson gson = new Gson();
			//String eeShowJson = gson.toJson(eeShow);
			//SysUser currentLoginUser = (SysUser) session.getAttribute("CurrentLoginUserInfo");
			//SysLog sysLog = SysLogUtil.getSysLog(request, currentLoginUser, SysModuleConstant.M_EQUIPMENT_TYPE, 
				//	SysLogConstant.SELECT_ONE, eeShow.getId()+"", "emergency_equipment", "查询的课程分类信息Json-->"+eeShowJson);
		//	sysLogMapper.insertSelective(sysLog);
			JsonPrintUtil.printObjDataWithKey(response, eeShow,"ee");
		}
	}
	
	//新增和保存课程分类信息
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public void saveEE(SingleDb ee,HttpServletRequest request,HttpServletResponse response,HttpSession session) {
		int count = 0;
		SysUser currentLoginUser = (SysUser) session.getAttribute("CurrentLoginUserInfo");
		if(null!=ee.getId() && ee.getId()>0){
			//主键不为空 则编辑课程信息
			SingleDb eeOld = singleDbMapper.selectByPrimaryKey(ee.getId());
			//Gson gson = new Gson();
			//String eeOldJson = gson.toJson(eeOld);
			//BeanUtil.copyProperties(ee, eeOld);
			//String eeJson = gson.toJson(eeOld);
			eeOld.setUpdater(currentLoginUser.getUserName()+"");
			eeOld.setUpdateTime(DateUtil.DateToString(new Date(), "yyyy-MM-dd "));
			count = singleDbMapper.updateByPrimaryKeySelective(eeOld);
			//updateByPrimaryKeySelective 前者只是更新新的model中不为空的字段
			//updateByPrimaryKey 后者则会将为空的字段在数据库中置为NULL
			//记录编辑日志
			if(count>0){
			///	SysLog sysLog = SysLogUtil.getSysLog(request, currentLoginUser, SysModuleConstant.M_EQUIPMENT_TYPE, 
			//			SysLogConstant.UPDATE, ee.getId()+"", "emergency_equipment", "编辑前Json-->"+eeOldJson+"  编辑后Json-->"+eeJson);
			//	sysLogMapper.insertSelective(sysLog);
				JsonPrintUtil.printObjDataWithKey(response, eeOld,"data");
			}
		}else{
			//主键为空 则添加课程分类信息
			ee.setStatus(1);
			ee.setCreater(currentLoginUser.getUserName()+"");
			ee.setCreateTime(DateUtil.DateToString(new Date(), "yyyy-MM-dd "));
			count = singleDbMapper.insert(ee);
			//记录添加日志
			if(count>0){
				Gson gson = new Gson();
				String eeAddJson = gson.toJson(ee);
				//SysLog sysLog = SysLogUtil.getSysLog(request, currentLoginUser, SysModuleConstant.M_EQUIPMENT_TYPE, 
					//	SysLogConstant.CREATE, ee.getId()+"", "emergency_equipment", "新增课程分类信息成功 Json-->"+eeAddJson);
				//sysLogMapper.insertSelective(sysLog);
				JsonPrintUtil.printObjDataWithKey(response, ee,"data");
			}
		}
	}
	
	//批量删除课程分类信息
	@RequestMapping("deleteBatch")
	@ResponseBody
	public void deleteBatch(String eeIds,HttpServletRequest request,HttpSession session,HttpServletResponse response) {
		SysUser currentLoginUser = (SysUser) session.getAttribute("CurrentLoginUserInfo");
		if(StringUtils.isNotBlank(eeIds)){
			String[] eeIdArr = eeIds.split(",");
			for(int i=0;i<eeIdArr.length;i++){
				//更新所选课程分类为删除状态
				int eeId = Integer.parseInt(eeIdArr[i]);
				SingleDb ee = singleDbMapper.selectByPrimaryKey(eeId);
				ee.setStatus(3);//1正常  3已删除 
				ee.setUpdateTime(DateUtil.DateToString(new Date(), "yyyy-MM-dd "));
				ee.setUpdater(currentLoginUser.getUserName()+"");
				singleDbMapper.updateByPrimaryKeySelective(ee);
			}
			//记录批量删除日志
			//SysLog sysLog = SysLogUtil.getSysLog(request, currentLoginUser, SysModuleConstant.M_EQUIPMENT_TYPE, 
			//			SysLogConstant.BATCH_DELETE, eeIds, "emergency_equipment", "批量删除课程分类信息成功");
			//sysLogMapper.insertSelective(sysLog);
			JsonPrintUtil.printObjDataWithKey(response, 1,"data");
			
		}else{
			JsonPrintUtil.printObjDataWithKey(response, 0,"data");
		}
	}
	
	//课程默认照片上传
	@RequestMapping("uploadEEPhoto")
	@ResponseBody
	public void uploadPhoto(String businessType,HttpServletRequest request,HttpServletResponse response,MultipartFile file) {  
		SysUser currentLoginUser = (SysUser)request.getSession().getAttribute("CurrentLoginUserInfo");
		//仓库照片保存目录
	/////	String photoUrl = super.uploadToFileUrl("emergency_equipment", file , request);
		//JsonPrintUtil.printObjDataWithKey(response, photoUrl,"data");
		
		//记录日志
		//SysLog sysLog = SysLogUtil.getSysLog(request, currentLoginUser, SysModuleConstant.M_EQUIPMENT_TYPE, 
		//		SysLogConstant.UPLOAD, "", "emergency_equipment", "上传的课程照片保存路径-->"+photoUrl);
	//	sysLogMapper.insertSelective(sysLog);	
	}
	
	/****************END 课程分类信息操作 END*********************/
	
	
	
	
}
