package itf4.kaoba.controller;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONObject;
import itf4.kaoba.mapper.SysUserMapper;
import itf4.kaoba.mapper.TeacherMapper;
import itf4.kaoba.model.SysUser;
import itf4.kaoba.model.SysUserExample;
import itf4.kaoba.model.SysUserExample.Criteria;
import itf4.kaoba.model.Teacher;
import itf4.kaoba.model.TeacherExample;
import itf4.kaoba.util.JsonPrintUtil;
@Controller
public class LoginController {
	@Autowired
	private SysUserMapper sysUserMapper;
	@Autowired
	private TeacherMapper teacherMapper;
	// ��½��֤
		@RequestMapping("login")
		@ResponseBody
		public void login(String loginId, String password,Integer status, HttpServletResponse response, HttpServletRequest request,
				HttpSession session) {
			JSONObject jsonObject = new JSONObject();
			if(StringUtils.isNotBlank(loginId)&&StringUtils.isNotBlank(password)&&status>0) {
         password=DigestUtils.md5DigestAsHex(password.getBytes());
				if(status==1) {
	  SysUserExample example=new SysUserExample();
		Criteria criteria=example.createCriteria();
		criteria.andUserPasswordEqualTo(password).andLoginIdEqualTo(loginId).andStatusEqualTo(1);
		List<SysUser> sysUsers=sysUserMapper.selectByExample(example);
		  if(sysUsers.size()>0) {
		  if (null != sysUsers.get(0)) {
				// ��½�ɹ�
				jsonObject.put("loginStatus", "1");
     session.setAttribute("CurrentLoginUserInfo", sysUsers.get(0));
     session.setAttribute("CurrUserRoleFlag", "1");
		} else {
			jsonObject.put("loginStatus", "0");
		}
		  }else {
			  jsonObject.put("loginStatus", "0");
		  }
  }else if(status==2) {
	  TeacherExample example=new TeacherExample();
	  itf4.kaoba.model.TeacherExample.Criteria criteria=example.createCriteria();
	  criteria.andTeaPasswordEqualTo(password).andLoginIdEqualTo(loginId).andStatusEqualTo(1);
	  List<Teacher> teachers=teacherMapper.selectByExample(example);
	   if(teachers.size()>0) {
           if(null!=teachers.get(0)) {
        	// ��½�ɹ�
			jsonObject.put("loginStatus", "2"); 
		session.setAttribute("CurrentLoginUserInfo", teachers.get(0));
		session.setAttribute("CurrUserRoleFlag", "2");
           }		   
           else {
   			jsonObject.put("loginStatus", "0");
   		}
   		  }else {
   			  jsonObject.put("loginStatus", "0");
   		  }
  }else {  
     		// �ʺ��������
			jsonObject.put("loginStatus", "0");
  }
			}else {
				jsonObject.put("loginStatus", "0");
			}
			
  JsonPrintUtil.printObjDataWithKey(response, jsonObject, "data");
		}
	
	  //�˳�ϵͳ
	  @RequestMapping(value = "/logout")
	  public String logout(HttpServletRequest request) {
	      request.getSession().invalidate();
	      return "login";
	  }
	
}
