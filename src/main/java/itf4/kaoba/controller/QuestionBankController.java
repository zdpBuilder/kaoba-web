package itf4.kaoba.controller;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import itf4.kaoba.common.ResponseJsonPageListBean;
import itf4.kaoba.mapper.AnswerDbMapper;
import itf4.kaoba.mapper.CourseMapper;
import itf4.kaoba.mapper.SingleDbMapper;
import itf4.kaoba.mapper.TeacherCourseMapper;
import itf4.kaoba.model.AnswerDb;
import itf4.kaoba.model.AnswerDbExample;
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
import itf4.kaoba.service.SingleDbService;
import itf4.kaoba.util.Const;
import itf4.kaoba.util.DateUtil;
import itf4.kaoba.util.JsonPrintUtil;
import itf4.kaoba.util.JsonUtils;
import lombok.extern.log4j.Log4j;

@Log4j
@Controller
@RequestMapping("courseVsSingleDb")
public class QuestionBankController {

	@Autowired
	private CourseMapper courseMapper;
	@Autowired
	private TeacherCourseMapper teacherCourseMapper;
	@Autowired
	private SingleDbMapper singleDbMapper;
	@Autowired
	private AnswerDbMapper answerDbMapper;

	@Autowired
	private SingleDbService singleDbService;
  
	/**************** START 课程分类树状菜单操作 START *********************/

	// 树状菜单查询
	@RequestMapping("treeList")
	@ResponseBody
	public void treeList(HttpServletRequest request, HttpServletResponse response) {
		Teacher teacher = (Teacher) request.getSession().getAttribute("CurrentLoginUserInfo");

		List<TreePojo> treePojos = new ArrayList<TreePojo>();

		TeacherCourseExample example0 = new TeacherCourseExample();
		itf4.kaoba.model.TeacherCourseExample.Criteria critria0 = example0.createCriteria();
		critria0.andTeaIdEqualTo(teacher.getId());
		List<TeacherCourse> teaCourseList = teacherCourseMapper.selectByExample(example0);

		if (teaCourseList != null && teaCourseList.size() > 0) {
			String courseIds = teaCourseList.get(0).getCourseId();
			if (courseIds != null && courseIds != "") {
				String[] courseIdArray = courseIds.split(",");

				for (String courseId : courseIdArray) {
					CourseExample example = new CourseExample();
					Criteria criteria = example.createCriteria();
					criteria.andIdEqualTo(Integer.parseInt(courseId)).andStatusEqualTo(1);// 正常状态节点
					List<Course> list = courseMapper.selectByExample(example);
					// 放入课程
					if (list != null && list.size() > 0) {
						Course course = new Course();
						course = list.get(0);
						TreePojo treePojo = new TreePojo();
						treePojo.setId(course.getId());
						treePojo.setName(course.getCourseName());
						treePojo.setPid(course.getPid());
						treePojos.add(treePojo);

						// 查章节
						List<Course> courseList = getCourseTree(course.getId());
						for (Course course2 : courseList) {
							TreePojo treePojo2 = new TreePojo();
							treePojo2.setId(course2.getId());
							treePojo2.setName(course2.getCourseName());
							treePojo2.setPid(course2.getPid());
							treePojos.add(treePojo2);
						}

						/*
						 * CourseExample example2 = new CourseExample(); Criteria criteria2 =
						 * example2.createCriteria();
						 * criteria2.andPidEqualTo(course.getId()).andStatusEqualTo(1); List<Course>
						 * courseList = courseMapper.selectByExample(example2); if(courseList!=null &&
						 * courseList.size()>0) { for (Course course2 : courseList) { TreePojo
						 * treePojo2=new TreePojo(); treePojo2.setId(course2.getId());
						 * treePojo2.setName(course2.getCourseName());
						 * treePojo2.setPid(course2.getPid()); treePojos.add(treePojo2); } }
						 */
					}
					// log.debug("此次查询树节点数目: "+list.size());
					// 列表查询日志记录
					// SysUser currentLoginUser = (SysUser)
					// request.getSession().getAttribute("CurrentLoginUserInfo");
					// SysLog sysLog = SysLogUtil.getSysLog(request, currentLoginUser,
					// SysModuleConstant.M_EQUIPMENT_TYPE, SysLogConstant.SELECT_LIST, "",
					// "emergency_equipment", "课程分类树型菜单查询共 "+list.size()+" 条");
					/// sysLogMapper.insertSelective(sysLog);
				}
			}

		}

		JsonPrintUtil.printJsonArrayWithoutKey(response, treePojos);
	}
    
	// 每名教师的课程列表
		@RequestMapping("teaCouTreeList")
		@ResponseBody
		public void teaCouTreeList(HttpServletRequest request, HttpServletResponse response) {
			Teacher teacher = (Teacher) request.getSession().getAttribute("CurrentLoginUserInfo");
			List<TreePojo> treePojos = new ArrayList<TreePojo>();
		   List<Course> courses=new ArrayList<Course>();
		   
			TeacherCourseExample example=new TeacherCourseExample();
		   TeacherCourseExample.Criteria criteria=example.createCriteria();
		   criteria.andTeaIdEqualTo(teacher.getId());
		List<TeacherCourse> teacherCourseList=teacherCourseMapper.selectByExample(example);
        if(teacherCourseList.size()>0) {
          String[] coIdStrings=  teacherCourseList.get(0).getCourseId().split(",");
         for (String id : coIdStrings) {
        	 courses.add(courseMapper.selectByPrimaryKey(Integer.parseInt(id)));
		   }     
         for (Course course : courses) {
        	 TreePojo treePojo=new TreePojo();
        	 treePojo.setId(course.getId());
        	 treePojo.setPid(course.getPid());
        	 treePojo.setName(course.getCourseName());
        	 treePojos.add(treePojo);
		}       
       }

			JsonPrintUtil.printJsonArrayWithoutKey(response, treePojos);
		}
	// 递归查出章节
	public List<Course> getCourseTree(int pid) {
		List<Course> list = new ArrayList();
		CourseExample example = new CourseExample();
		Criteria criteria = example.createCriteria();
		criteria.andPidEqualTo(pid).andStatusEqualTo(1);
		List<Course> courseList = courseMapper.selectByExample(example);
		if (courseList != null && courseList.size() > 0) {
			for (Course course : courseList) {
				list.add(course);
				List<Course> list2 = new ArrayList();
				list2 = getCourseTree(course.getId());
				for (Course course2 : list2) {
					list.add(course2);
				}
			}
		}
		return list;
	}

	// 获取节点
	@RequestMapping("showNode")
	@ResponseBody
	public void showNode(HttpServletRequest request, HttpServletResponse response, Integer id, String nodeName) {
		SysUser user = (SysUser) request.getSession().getAttribute("CurrentLoginUserInfo");

		Course course = courseMapper.selectByPrimaryKey(id);
		if (null != course) {
			// 日志记录
			// SysLog sysLog = SysLogUtil.getSysLog(request, user,
			// SysModuleConstant.M_EQUIPMENT_TYPE, SysLogConstant.SELECT_ONE, et.getId()+"",
			// "emergency_equipment_type", "查询课程分类信息["+et.getName()+"]成功");
			// sysLogMapper.insertSelective(sysLog);
			JsonPrintUtil.printObjDataWithKey(response, course, "data");
		}
	}

	// 添加节点
	@RequestMapping("addTreeNode")
	@ResponseBody
	public void addTreeNode(HttpServletRequest request, HttpServletResponse response, Course record) {
		String CurrentUserName = "";
		if (request.getSession().getAttribute(Const.SESSION_USER_STATUS).equals("1")) {
			SysUser user = (SysUser) request.getSession().getAttribute(Const.SESSION_USER);
			CurrentUserName = user.getUserName();
		} else if (request.getSession().getAttribute(Const.SESSION_USER_STATUS).equals("2")) {
			Teacher teacher = (Teacher) request.getSession().getAttribute(Const.SESSION_USER);
			CurrentUserName = teacher.getTeaName();
		}

		record.setStatus(1);// 正常状态
		record.setCreater(CurrentUserName);
		record.setCreateTime(DateUtil.DateToString(new Date(), "yyyy-MM-dd "));
		int count = courseMapper.insert(record);
		if (count > 0) {
			// 日志记录
			// SysLog sysLog = SysLogUtil.getSysLog(request, user,
			// SysModuleConstant.M_EQUIPMENT_TYPE, SysLogConstant.CREATE, record.getId()+"",
			// "emergency_equipment_type", "新增课程分类名称["+nodeName+"]成功");
			/// sysLogMapper.insertSelective(sysLog);
			JsonPrintUtil.printObjDataWithKey(response, record, "data");
		}
	}

	// 修改节点
	@RequestMapping("editTreeNode")
	@ResponseBody
	public void editTreeNode(HttpServletRequest request, HttpServletResponse response, Integer id, String nodeName) {
		String CurrentUserName = "";
		if (request.getSession().getAttribute(Const.SESSION_USER_STATUS).equals("1")) {
			SysUser user = (SysUser) request.getSession().getAttribute(Const.SESSION_USER);
			CurrentUserName = user.getUserName();
		} else if (request.getSession().getAttribute(Const.SESSION_USER_STATUS).equals("2")) {
			Teacher teacher = (Teacher) request.getSession().getAttribute(Const.SESSION_USER);
			CurrentUserName = teacher.getTeaName();
		}
		Course record = new Course();
		record = courseMapper.selectByPrimaryKey(id);
		String oldNodeName = record.getCourseName();
		record.setCourseName(nodeName);
		record.setUpdater(CurrentUserName);
		record.setUpdateTime(DateUtil.DateToString(new Date(), "yyyy-MM-dd "));
		int count = courseMapper.updateByPrimaryKey(record);
		if (count > 0) {
			// 日志记录
			// SysLog sysLog = SysLogUtil.getSysLog(request, user,
			// SysModuleConstant.M_EQUIPMENT_TYPE, SysLogConstant.UPDATE, record.getId()+"",
			// "emergency_equipment_type", "修改课程分类名称["+oldNodeName+"]为["+nodeName+"]成功");
			// sysLogMapper.insertSelective(sysLog);
			JsonPrintUtil.printObjDataWithKey(response, "ok", "data");
		}
	}

	// 删除节点
	@RequestMapping("deleteTreeNode")
	@ResponseBody
	public void deleteTreeNode(HttpServletRequest request, HttpServletResponse response, Integer id) {
		String CurrentUserName = "";
		if (request.getSession().getAttribute(Const.SESSION_USER_STATUS).equals("1")) {
			SysUser user = (SysUser) request.getSession().getAttribute(Const.SESSION_USER);
			CurrentUserName = user.getUserName();
		} else if (request.getSession().getAttribute(Const.SESSION_USER_STATUS).equals("2")) {
			Teacher teacher = (Teacher) request.getSession().getAttribute(Const.SESSION_USER);
			CurrentUserName = teacher.getTeaName();
		}
		Course record = new Course();
		record = courseMapper.selectByPrimaryKey(id);
		record.setStatus(0);// 删除状态
		record.setUpdater(CurrentUserName);
		record.setUpdateTime(DateUtil.DateToString(new Date(), "yyyy-MM-dd "));
		int count = courseMapper.updateByPrimaryKey(record);
		SingleDbExample example = new SingleDbExample();
		SingleDbExample.Criteria criteria = example.createCriteria();
		criteria.andCourseIdEqualTo(record.getId());
		List<SingleDb> list = singleDbMapper.selectByExample(example);
		if (list.size() > 0) {
			for (SingleDb singleDb : list) {
				singleDb.setStatus(0);
				singleDbMapper.updateByPrimaryKey(singleDb);
			}
		}
		if (count > 0) {
			// 日志记录
			// SysLog sysLog = SysLogUtil.getSysLog(request, user,
			// SysModuleConstant.M_EQUIPMENT_TYPE, SysLogConstant.DELETE, record.getId()+"",
			// "emergency_equipment_type", "删除课程分类名称["+record.getName()+"]成功");
			// sysLogMapper.insertSelective(sysLog);
			JsonPrintUtil.printObjDataWithKey(response, "ok", "data");
		}
	}

	/**************** END 课程分类树状菜单操作 END *********************/

	/**************** START 题库信息操作 START *********************/
	// 课程分类列表查询
	@RequestMapping("list")
	@ResponseBody
	public void equipmentList(HttpServletRequest request, HttpServletResponse response, String keywords, int pid,
			int limit, int page) {
		SingleDbExample example = new SingleDbExample();
		example.setStartRow((page - 1) * limit);
		example.setPageSize(limit);
		example.setOrderByClause("create_time desc,update_time desc");
		SingleDbExample.Criteria criteria = example.createCriteria();

		if (StringUtils.isNotBlank(keywords)) {
			keywords = keywords.trim();
			keywords = "%" + keywords + "%";
			// and or联合查询
			example.or().andSingledbTitleLike(keywords).andStatusEqualTo(1);
		} else {
			criteria.andStatusEqualTo(1);// 正常状态
		}

		if (pid != 1) {
			// 查询节点所有子类
			CourseExample example1 = new CourseExample();
			Criteria criteria1 = example1.createCriteria();
			criteria1.andStatusEqualTo(1);// 正常状态节点
			criteria1.andPidEqualTo(pid);
			List<Course> coursesList = courseMapper.selectByExample(example1);
			List<Integer> valuesList = new ArrayList<Integer>();
			for (Course et : coursesList) {
				valuesList.add(et.getId());
			}
			valuesList.add(pid);
			criteria.andCourseIdIn(valuesList);
		} else {
			// 根节点查询所有 不加过滤条件
		}

		List<SingleDb> list = singleDbMapper.selectByExample(example);
		int count = (int) singleDbMapper.countByExample(example);
		// log.debug("此次查询列表size: "+count);

		ResponseJsonPageListBean listBean = new ResponseJsonPageListBean();
		listBean.setCode(0);
		listBean.setCount(count);
		listBean.setMsg("课程分类名称列表");

		/*
		 * List<EquipmentTypeJsonBean> typeJsonBeanList = new
		 * ArrayList<EquipmentTypeJsonBean>(); EquipmentTypeJsonBean typeJsonBean =
		 * null; for(EmergencyEquipment ee : list){ typeJsonBean = new
		 * EquipmentTypeJsonBean(); BeanUtil.copyProperties(ee, typeJsonBean);
		 * 
		 * String type =
		 * equipmentTypeMapper.selectByPrimaryKey(equipmentTypeMapper.selectByPrimaryKey
		 * (ee.getTypeId()).getPid()).getName(); String name =
		 * equipmentTypeMapper.selectByPrimaryKey(ee.getTypeId()).getName();
		 * typeJsonBean.setType(type); typeJsonBean.setName(name); String creater
		 * =sysUserMapper.selectByPrimaryKey(Integer.parseInt(ee.getCreater())).
		 * getUserName(); typeJsonBean.setCreater(creater);
		 * 
		 * DateTime dt = new DateTime(ee.getCreateTime());
		 * typeJsonBean.setCreateTime(dt.toString("yyyy-MM-dd HH:mm:ss"));
		 * 
		 * typeJsonBeanList.add(typeJsonBean); }
		 */

		listBean.setData(list);

		// 列表查询日志记录
		// SysUser currentLoginUser = (SysUser)
		// request.getSession().getAttribute("CurrentLoginUserInfo");
		// SysLog sysLog = SysLogUtil.getSysLog(request, currentLoginUser,
		// SysModuleConstant.M_EQUIPMENT_TYPE, SysLogConstant.SELECT_LIST, "",
		// "emergency_equipment", "列表查询共 "+typeJsonBeanList.size()+" 条");
		// sysLogMapper.insertSelective(sysLog);
		JsonPrintUtil.printObjDataWithoutKey(response, listBean);

	}

	/*
	 * //课程类型和分类名称下拉菜单查询
	 * 
	 * @RequestMapping("selectList")
	 * 
	 * @ResponseBody public void selectList( HttpServletRequest
	 * request,HttpServletResponse response) {
	 * 
	 * CourseExample example = new CourseExample(); Criteria criteria =
	 * example.createCriteria(); criteria.andPidEqualTo(1);//查询一级节点
	 * criteria.andStatusEqualTo(1);//正常状态节点
	 * 
	 * List<SelectCourseTreeJsonListPojo> selectBeanList = new
	 * ArrayList<SelectCourseTreeJsonListPojo>();
	 * 
	 * //一级课程类型节点列表 List<Course> list = courseMapper.selectByExample(example);
	 * SelectCourseTreeJsonListPojo selectBean = null;
	 * 
	 * List<Course> childList = null; for(Course eeType : list){ selectBean = new
	 * SelectCourseTreeJsonListPojo(); selectBean.setPid(eeType.getId());
	 * selectBean.setName(eeType.getCourseName()); CourseExample example2= new
	 * CourseExample(); Criteria criteria2=example2.createCriteria();
	 * criteria2.andPidEqualTo(eeType.getId()); childList =
	 * courseMapper.selectByExample(example2); selectBean.setChildList(childList);
	 * selectBeanList.add(selectBean); }
	 * 
	 * //列表查询日志记录 SysUser currentLoginUser = (SysUser)
	 * request.getSession().getAttribute("CurrentLoginUserInfo"); //SysLog sysLog =
	 * SysLogUtil.getSysLog(request, currentLoginUser,
	 * SysModuleConstant.M_EQUIPMENT_TYPE, SysLogConstant.SELECT_LIST, "",
	 * "emergency_equipment_type", "课程分类下拉菜单查询共 "+selectBeanList.size()+" 条");
	 * ///sysLogMapper.insertSelective(sysLog); //String selectBeanListJson =
	 * JSON.toJSONString(selectBeanList); //log.info(selectBeanListJson);
	 * JsonPrintUtil.printJsonArrayWithoutKey(response, selectBeanList); }
	 */
	// 查看单条题库分类信息
	@RequestMapping(value = "show", method = RequestMethod.POST)
	@ResponseBody
	public void showEE(int id, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		// 查询单条课程分类信息
		SingleDb eeShow = singleDbMapper.selectByPrimaryKey(id);
		if (null != eeShow) {
			// 记录日志
			// Gson gson = new Gson();
			// String eeShowJson = gson.toJson(eeShow);
			// SysUser currentLoginUser = (SysUser)
			// session.getAttribute("CurrentLoginUserInfo");
			// SysLog sysLog = SysLogUtil.getSysLog(request, currentLoginUser,
			// SysModuleConstant.M_EQUIPMENT_TYPE,
			// SysLogConstant.SELECT_ONE, eeShow.getId()+"", "emergency_equipment",
			// "查询的课程分类信息Json-->"+eeShowJson);
			// sysLogMapper.insertSelective(sysLog);
			JsonPrintUtil.printObjDataWithKey(response, eeShow, "ee");
		}
	}

	/*
	 * //新增和保存课程分类信息
	 * 
	 * @RequestMapping(value = "save", method = RequestMethod.POST)
	 * 
	 * @ResponseBody public void saveEE(SingleDb ee,HttpServletRequest
	 * request,HttpServletResponse response,HttpSession session) { int count = 0;
	 * String CurrentUserName ="";
	 * if(request.getSession().getAttribute(Const.SESSION_USER_STATUS).equals("1"))
	 * { SysUser user =
	 * (SysUser)request.getSession().getAttribute(Const.SESSION_USER);
	 * CurrentUserName= user.getUserName(); }else
	 * if(request.getSession().getAttribute(Const.SESSION_USER_STATUS).equals("2"))
	 * { Teacher teacher =
	 * (Teacher)request.getSession().getAttribute(Const.SESSION_USER);
	 * CurrentUserName= teacher.getTeaName(); } if(null!=ee.getId() &&
	 * ee.getId()>0){ //主键不为空 则编辑课程信息 SingleDb eeOld =
	 * singleDbMapper.selectByPrimaryKey(ee.getId()); //Gson gson = new Gson();
	 * //String eeOldJson = gson.toJson(eeOld); //BeanUtil.copyProperties(ee,
	 * eeOld); //String eeJson = gson.toJson(eeOld);
	 * eeOld.setUpdater(CurrentUserName);
	 * eeOld.setUpdateTime(DateUtil.DateToString(new Date(), "yyyy-MM-dd ")); count
	 * = singleDbMapper.updateByPrimaryKeySelective(eeOld);
	 * //updateByPrimaryKeySelective 前者只是更新新的model中不为空的字段 //updateByPrimaryKey
	 * 后者则会将为空的字段在数据库中置为NULL //记录编辑日志 if(count>0){ /// SysLog sysLog =
	 * SysLogUtil.getSysLog(request, currentLoginUser,
	 * SysModuleConstant.M_EQUIPMENT_TYPE, // SysLogConstant.UPDATE, ee.getId()+"",
	 * "emergency_equipment", "编辑前Json-->"+eeOldJson+"  编辑后Json-->"+eeJson); //
	 * sysLogMapper.insertSelective(sysLog);
	 * JsonPrintUtil.printObjDataWithKey(response, eeOld,"data"); } }else{ //主键为空
	 * 则添加课程分类信息 ee.setStatus(1); ee.setCreater(CurrentUserName);
	 * ee.setCreateTime(DateUtil.DateToString(new Date(), "yyyy-MM-dd ")); count =
	 * singleDbMapper.insert(ee); //记录添加日志 if(count>0){ Gson gson = new Gson();
	 * String eeAddJson = gson.toJson(ee); //SysLog sysLog =
	 * SysLogUtil.getSysLog(request, currentLoginUser,
	 * SysModuleConstant.M_EQUIPMENT_TYPE, // SysLogConstant.CREATE, ee.getId()+"",
	 * "emergency_equipment", "新增课程分类信息成功 Json-->"+eeAddJson);
	 * //sysLogMapper.insertSelective(sysLog);
	 * JsonPrintUtil.printObjDataWithKey(response, ee,"data"); } } }
	 */

	// 新增和保存课程分类信息
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public void saveEE(SingleDb ee, AnswerDb answerDb, HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		int count = 0;
		int count1 = 0;
		String CurrentUserName = "";
		if (request.getSession().getAttribute(Const.SESSION_USER_STATUS).equals("1")) {
			SysUser user = (SysUser) request.getSession().getAttribute(Const.SESSION_USER);
			CurrentUserName = user.getUserName();
		} else if (request.getSession().getAttribute(Const.SESSION_USER_STATUS).equals("2")) {
			Teacher teacher = (Teacher) request.getSession().getAttribute(Const.SESSION_USER);
			CurrentUserName = teacher.getTeaName();
		}
		if (null != ee.getId() && ee.getId() > 0) {
			// 主键不为空 则编辑课程信息
			SingleDb eeOld = singleDbMapper.selectByPrimaryKey(ee.getId());
			// Gson gson = new Gson();
			// String eeOldJson = gson.toJson(eeOld);
			// BeanUtil.copyProperties(ee, eeOld);
			// String eeJson = gson.toJson(eeOld);
			eeOld.setSingledbTitle(ee.getSingledbTitle());
			eeOld.setSingledbOptiona(ee.getSingledbOptiona());
			eeOld.setSingledbOptionb(ee.getSingledbOptionb());
			eeOld.setSingledbOptionc(ee.getSingledbOptionc());
			eeOld.setSingledbOptiond(ee.getSingledbOptiond());
			eeOld.setUpdater(CurrentUserName);
			eeOld.setUpdateTime(DateUtil.DateToString(new Date(), "yyyy-MM-dd "));
			count = singleDbMapper.updateByPrimaryKeySelective(eeOld);

			AnswerDbExample example = new AnswerDbExample();
			itf4.kaoba.model.AnswerDbExample.Criteria criteria = example.createCriteria();
			criteria.andDbIdEqualTo(ee.getId()).andStatusEqualTo(1);
			List<AnswerDb> anList = answerDbMapper.selectByExample(example);
			answerDb.setId(anList.get(0).getId());
			answerDb.setUpdater(CurrentUserName);
			answerDb.setUpdateTime(DateUtil.DateToString(new Date(), "yyyy-MM-dd "));
			count1 = answerDbMapper.updateByPrimaryKeySelective(answerDb);
			// updateByPrimaryKeySelective 前者只是更新新的model中不为空的字段
			// updateByPrimaryKey 后者则会将为空的字段在数据库中置为NULL
			// 记录编辑日志
			if (count > 0 && count1 > 0) {
				/// SysLog sysLog = SysLogUtil.getSysLog(request, currentLoginUser,
				/// SysModuleConstant.M_EQUIPMENT_TYPE,
				// SysLogConstant.UPDATE, ee.getId()+"", "emergency_equipment",
				/// "编辑前Json-->"+eeOldJson+" 编辑后Json-->"+eeJson);
				// sysLogMapper.insertSelective(sysLog);
				JsonPrintUtil.printObjDataWithKey(response, count + count1, "data");
			}
		} else {
			// 主键为空 则添加课程分类信息
			ee.setStatus(1);
			ee.setCreater(CurrentUserName);
			ee.setCreateTime(DateUtil.DateToString(new Date(), "yyyy-MM-dd "));
			count = singleDbMapper.insert(ee);
			// System.out.println(ee.getId());

			answerDb.setDbId(ee.getId());
			answerDb.setAnswerdbDbtype(0);
			answerDb.setStatus(1);
			answerDb.setCreater(CurrentUserName);
			answerDb.setCreateTime(DateUtil.DateToString(new Date(), "yyyy-MM-dd "));
			count1 = answerDbMapper.insert(answerDb);

			/*
			 * //记录添加日志 if(count>0 && count1>0){ Gson gson = new Gson(); String eeAddJson =
			 * gson.toJson(ee); //SysLog sysLog = SysLogUtil.getSysLog(request,
			 * currentLoginUser, SysModuleConstant.M_EQUIPMENT_TYPE, //
			 * SysLogConstant.CREATE, ee.getId()+"", "emergency_equipment",
			 * "新增课程分类信息成功 Json-->"+eeAddJson); //sysLogMapper.insertSelective(sysLog);
			 * JsonPrintUtil.printObjDataWithKey(response, ee,"data"); }
			 */
			if (count > 0 && count1 > 0) {
				JsonPrintUtil.printObjDataWithKey(response, count + count1, "data");
			}
		}
	}

	// 批量删除课程分类信息
	@RequestMapping("deleteBatch")
	@ResponseBody
	public void deleteBatch(String eeIds, HttpServletRequest request, HttpSession session,
			HttpServletResponse response) {
		String CurrentUserName = "";
		if (request.getSession().getAttribute(Const.SESSION_USER_STATUS).equals("1")) {
			SysUser user = (SysUser) request.getSession().getAttribute(Const.SESSION_USER);
			CurrentUserName = user.getUserName();
		} else if (request.getSession().getAttribute(Const.SESSION_USER_STATUS).equals("2")) {
			Teacher teacher = (Teacher) request.getSession().getAttribute(Const.SESSION_USER);
			CurrentUserName = teacher.getTeaName();
		}
		if (StringUtils.isNotBlank(eeIds)) {
			String[] eeIdArr = eeIds.split(",");
			for (int i = 0; i < eeIdArr.length; i++) {
				// 更新所选课程分类为删除状态
				int eeId = Integer.parseInt(eeIdArr[i]);
				SingleDb ee = singleDbMapper.selectByPrimaryKey(eeId);
				ee.setStatus(0);// 1正常 0已删除
				ee.setUpdateTime(DateUtil.DateToString(new Date(), "yyyy-MM-dd "));
				ee.setUpdater(CurrentUserName);
				singleDbMapper.updateByPrimaryKeySelective(ee);
				AnswerDbExample example = new AnswerDbExample();
				itf4.kaoba.model.AnswerDbExample.Criteria criteria = example.createCriteria();
				criteria.andDbIdEqualTo(eeId);
				AnswerDb record = new AnswerDb();
				record.setStatus(0);
				answerDbMapper.updateByExampleSelective(record, example);

			}
			// 记录批量删除日志
			// SysLog sysLog = SysLogUtil.getSysLog(request, currentLoginUser,
			// SysModuleConstant.M_EQUIPMENT_TYPE,
			// SysLogConstant.BATCH_DELETE, eeIds, "emergency_equipment", "批量删除课程分类信息成功");
			// sysLogMapper.insertSelective(sysLog);
			JsonPrintUtil.printObjDataWithKey(response, 1, "data");

		} else {
			JsonPrintUtil.printObjDataWithKey(response, 0, "data");
		}
	}

	// 课程默认照片上传
	@RequestMapping("uploadEEPhoto")
	@ResponseBody
	public void uploadPhoto(String businessType, HttpServletRequest request, HttpServletResponse response,
			MultipartFile file) {
		// SysUser currentLoginUser =
		// (SysUser)request.getSession().getAttribute("CurrentLoginUserInfo");
		// 仓库照片保存目录
		// String photoUrl = super.uploadToFileUrl("emergency_equipment", file ,
		// request);
		// JsonPrintUtil.printObjDataWithKey(response, photoUrl,"data");

		// 记录日志
		// SysLog sysLog = SysLogUtil.getSysLog(request, currentLoginUser,
		// SysModuleConstant.M_EQUIPMENT_TYPE,
		// SysLogConstant.UPLOAD, "", "emergency_equipment", "上传的课程照片保存路径-->"+photoUrl);
		// sysLogMapper.insertSelective(sysLog);
	}

	/**************** END 课程分类信息操作 END *********************/

	// 查询答案
	@RequestMapping(value = "showAnswer", method = RequestMethod.POST)
	@ResponseBody
	public void showAnswer(String courseId, HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		// 查询单条课程分类信息
		AnswerDbExample example = new AnswerDbExample();
		itf4.kaoba.model.AnswerDbExample.Criteria criteria = example.createCriteria();
		criteria.andDbIdEqualTo(Integer.parseInt(courseId));
		List<AnswerDb> anList = answerDbMapper.selectByExample(example);
		if (anList.size() > 0 && anList != null) {
			AnswerDb answerDb = anList.get(0);
			JsonPrintUtil.printObjDataWithKey(response, answerDb, "data");
		}
	}

	// 编辑回显 试题 | 试题答案
	@RequestMapping(value = "showSingleDb", method = RequestMethod.POST)
	@ResponseBody
	public void showSingleDb(String id, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		// 查询单条课程分类信息
		SingleDb eeOld = singleDbMapper.selectByPrimaryKey(Integer.parseInt(id));

		AnswerDbExample example = new AnswerDbExample();
		itf4.kaoba.model.AnswerDbExample.Criteria criteria = example.createCriteria();
		criteria.andDbIdEqualTo(Integer.parseInt(id)).andStatusEqualTo(1);
		List<AnswerDb> anList = answerDbMapper.selectByExample(example);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ee", eeOld);
		map.put("answerdb", anList.get(0));
		JsonPrintUtil.printObjDataWithKey(response, map, "data");
	}

	// 批量添加试题
	@RequestMapping("/insertDBsBatch")
	@ResponseBody
	public int impots(String courseId, HttpServletRequest request, Model model) throws Exception {
		// String courseId = request.getParameter("courseId");
		// 获取上传的文件
		MultipartHttpServletRequest multipart = (MultipartHttpServletRequest) request;
		MultipartFile file = multipart.getFile("upfile");
		InputStream in = file.getInputStream();
		// 数据导入
		int result = singleDbService.importUsersExcelInfo(request, in, file, courseId);
		in.close();
		System.out.println(result);
		return result;
	}

	/****************
	 * START 专项练习(Special Exercise) AND 综合练习(Comprehensive Exercise) START
	 *********************/
//	根据课程id查询第一层章
	@RequestMapping("Chaptorlist")
	@ResponseBody
	public void Chaptor(HttpServletRequest request, HttpServletResponse response, Integer couseId) {

		CourseExample example = new CourseExample();
		Criteria criteria = example.createCriteria();
		criteria.andPidEqualTo(couseId);
		criteria.andStatusEqualTo(1);
		List<Course> list = courseMapper.selectByExample(example);

		String courseListJson = JsonUtils.listToJson(list);
		JsonPrintUtil.printObjDataWithKey(response, courseListJson, "data");
	}

	// 课程分类列表查询
	@RequestMapping("SElist")
	@ResponseBody
	public void couseSE(HttpServletRequest request, HttpServletResponse response, Integer couseId, Integer pid) {
		int count = 0;
		List<SelectCourseTreeJsonListPojo> list = null;

		if (pid != null && couseId == null) {
			// 综合练习
			list = singleDbMapper.selectAllsingleDbMessageBypid(pid);

		} else {
			// 专项练习
			list = singleDbMapper.selectAllsingleDbMessageBycouseId(couseId);
		}
		/*
		 * count=list.size(); ResponseJsonPageListBean listBean = new
		 * ResponseJsonPageListBean(); listBean.setCode(0); listBean.setCount(count);
		 * listBean.setMsg("所选课程章节单项选择题所有信息列表"); listBean.setData(list);
		 * JsonPrintUtil.printObjDataWithoutKey(response, listBean);
		 */

		String singleDbListJson = JsonUtils.listToJson(list);
		JsonPrintUtil.printObjDataWithKey(response, singleDbListJson, "data");
	}

	/****************
	 * END 专项练习(Special Exercise) AND 综合练习(Comprehensive Exercise) END
	 *********************/
}
