package itf4.kaoba.controller;
 
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
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
import itf4.kaoba.mapper.CourseMapper;
import itf4.kaoba.model.Course;
import itf4.kaoba.model.CourseExample;
import itf4.kaoba.model.SysUser;
import itf4.kaoba.model.CourseExample.Criteria;
import itf4.kaoba.service.CourseService;
import itf4.kaoba.util.JsonPrintUtil;
 
@Controller
@RequestMapping("/course")
public class CourseController {

	@Autowired
	private CourseService courseService;
	
	@Autowired
	private CourseMapper courseMapper;
	
	// �γ��б��ҳ��ѯ
	 	@RequestMapping(value ="list")
	 	@ResponseBody
	 	public void equipmentList(HttpServletRequest request, HttpServletResponse response, String keywords, int limit,
	 			int page) throws Exception {
	 		// limit ÿҳ��ʾ����
	 		// page ��ǰҳ��
	 		CourseExample example = new CourseExample();
	 		// ���÷�ҳ��ѯ����
	 		example.setStartRow((page - 1) * limit);
	 		example.setPageSize(limit);
	 		example.setOrderByClause("create_time desc,update_time desc");
	 		Criteria criteria = example.createCriteria();
	 		
	 		keywords = new String(keywords.getBytes("iso8859-1"),"utf-8");
	 		//System.out.println(keywords);
	 		if (keywords!=null&&keywords!="") {
	 			keywords = keywords.trim();
	 			keywords = "%" + keywords + "%";
	 			// and or���ϲ�ѯ
	 			example.or().andPidEqualTo(0).andCourseNameLike(keywords).andStatusEqualTo(1);
	 		} else {
	 			criteria.andPidEqualTo(0).andStatusEqualTo(1);// ����״̬
	 		}
	 		// ��ҳ��ѯ
	 		List<Course> courses = courseMapper.selectByExample(example);
	 		int count = (int) courseMapper.countByExample(example);

	 		ResponseJsonPageListBean listBean = new ResponseJsonPageListBean();
	 		listBean.setCode(0);
	 		listBean.setCount(count);
	 		listBean.setMsg("�γ��б�");
	 		listBean.setData(courses);

	 		// ��־��¼�����ǰ̨Json
	 		if (null != courses && courses.size() > 0) {
	 			JsonPrintUtil.printObjDataWithoutKey(response, listBean);
	 		} else {
	 			JsonPrintUtil.printObjDataWithoutKey(response, null);
	 		}
	 	}
	 	
	 	
	 	@RequestMapping(value = "save", method = RequestMethod.POST)
	 	@ResponseBody
	 	public void save(Course course, HttpServletRequest request, HttpServletResponse response, HttpSession session)  throws Exception{
	 		int count = 0;
	 		SysUser currentLoginUser = (SysUser) session.getAttribute("CurrentLoginUserInfo");
	 		Date nowTime=new Date(); 
	 		//System.out.println(nowTime); 
	 		SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	 		//System.out.println(time.format(nowTime)); 
	 		//SysUser currentLoginUser = (SysUser) session.getAttribute("CurrentLoginUserInfo");
	 		// �༭�û�
	 		if (null != course.getId()&& course.getId() > 0) {
	 			//˭���µ�
	 			course.setUpdater(currentLoginUser.getUserName());
	 			course.setUpdateTime(time.format(nowTime));
	 			count = courseMapper.updateByPrimaryKeySelective(course);

	 			//���ǰ̨Json
	 			if (count > 0) {
	 				JsonPrintUtil.printObjDataWithKey(response, course, "data");
	 			}
	 		} else {
	 			// �����û�
	 			course.setStatus(1);
	 			course.setPid(0);
	 			course.setCreateTime(time.format(nowTime));
	 			//˭������
	 			course.setCreater(currentLoginUser.getUserName());
	 			
	 			count = courseMapper.insert(course);
	 			//���ǰ̨Json
	 			if (count > 0) {
	 				JsonPrintUtil.printObjDataWithKey(response, count, "data");
	 			}
	 		}
	 	}
	 	
	 	
	 	@RequestMapping(value = "show", method = RequestMethod.POST)
	 	@ResponseBody
	 	public void showWH(int id, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
	 		Course course = courseMapper.selectByPrimaryKey(id);
	 		if (null != course) {		
	 			JsonPrintUtil.printObjDataWithKey(response, course, "data");
	 		} else {
	 			JsonPrintUtil.printObjDataWithKey(response, null, "data");
	 		}
	 	}	
	 	
	 	
	 	
	 	
	 	@RequestMapping(value = "deleteBatch", method = RequestMethod.POST)
	 	@ResponseBody
	 	public void deleteBatch(String idStr, HttpServletRequest request, HttpServletResponse response,
	 			HttpSession session) {
	 		Date nowTime=new Date(); 
	 		//System.out.println(nowTime); 
	 		SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	 		SysUser currentLoginUser = (SysUser) session.getAttribute("CurrentLoginUserInfo");
	 		if (StringUtils.isNotBlank(idStr)) {
	 			String[] idArr = idStr.split(",");
	 			for (int i = 0; i < idArr.length; i++) {
	 				// ������ѡ�豸����Ϊɾ��״̬
	 				int id = Integer.parseInt(idArr[i]);
	 				Course course = courseMapper.selectByPrimaryKey(id);
	 				course.setStatus(0);// 1���� 0��ɾ��
	 				course.setUpdater(currentLoginUser.getUserName());
	 				course.setUpdateTime(time.format(nowTime));
	 				//user.setUpdater(currentLoginUser.getUserId() + "");
	 				courseMapper.updateByPrimaryKeySelective(course);
	 			}
	 			// ���ǰ̨Json
	 			JsonPrintUtil.printObjDataWithKey(response, 1, "data");
	 		} else {
	 			JsonPrintUtil.printObjDataWithKey(response, 0, "data");
	 		}
	 	}
	
	 	@RequestMapping("/insertCourseBatch")  
		 @ResponseBody
		 public int impots(HttpServletRequest request, Model model) throws Exception {  
		      //��ȡ�ϴ����ļ�  
		      MultipartHttpServletRequest multipart = (MultipartHttpServletRequest) request;  
		      MultipartFile file = multipart.getFile("upfile");       
		      InputStream in = file.getInputStream();  
		      //���ݵ���  
		     int result= courseService.importCourseExcelInfo(in, file); 
		      in.close();  
		      return result;  
		 }
	 	
}
