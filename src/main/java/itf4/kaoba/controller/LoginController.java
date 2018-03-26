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

import itf4.kaoba.mapper.StudentMapper;
import itf4.kaoba.mapper.SysUserMapper;
import itf4.kaoba.mapper.TeacherMapper;
import itf4.kaoba.model.Student;
import itf4.kaoba.model.StudentExample;
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
	@Autowired
	private StudentMapper studentMapper;

	// 登陆验证
	@RequestMapping("login")
	@ResponseBody
	public void login(String loginId, String password, Integer status, HttpServletResponse response,
			HttpServletRequest request, HttpSession session) {
		JSONObject jsonObject = new JSONObject();
		//获取登录人的信息对象
		Object object = new Object();
		//登录角色的标志
		String CurrUserRoleFlag = "0";
		
		if(StringUtils.isNotBlank(loginId) && StringUtils.isNotBlank(password) && status > 0) {
			password = DigestUtils.md5DigestAsHex(password.getBytes());
			//管理员登录
			if(status == 1) {
				SysUserExample example = new SysUserExample();
				Criteria criteria = example.createCriteria();
				criteria.andUserPasswordEqualTo(password).andLoginIdEqualTo(loginId).andStatusEqualTo(1);
				List<SysUser> sysUsers = sysUserMapper.selectByExample(example);
				if(sysUsers.size()>0 && sysUsers !=null) {
					CurrUserRoleFlag = "1";
					object = sysUsers.get(0);
				}
				//老师登录
			}else if(status == 2) {
				TeacherExample example = new TeacherExample();
				itf4.kaoba.model.TeacherExample.Criteria criteria = example.createCriteria();
				criteria.andTeaPasswordEqualTo(password).andLoginIdEqualTo(loginId).andStatusEqualTo(1);
				List<Teacher> teachers = teacherMapper.selectByExample(example);
				if(teachers.size()>0 && teachers !=null) {
					CurrUserRoleFlag = "2";
					object = teachers.get(0);
				}
				//学生登录
			} else if(status == 3) {
				StudentExample example = new StudentExample();
				itf4.kaoba.model.StudentExample.Criteria criteria = example.createCriteria();
				criteria.andLoginIdEqualTo(loginId).andStuPasswordEqualTo(password).andStatusEqualTo(1);
				List<Student> list = studentMapper.selectByExample(example);
				if(list.size()>0 && list !=null) {
					CurrUserRoleFlag = "3";
					object = list.get(0);
				}
			}
		}
		
		//返回结果值
		if(CurrUserRoleFlag.equals("0")) {
			jsonObject.put("loginStatus", "0");
		} else {
			// 登陆成功
			jsonObject.put("loginStatus", CurrUserRoleFlag);
			session.setAttribute("CurrentLoginUserInfo", object);
			session.setAttribute("CurrUserRoleFlag", CurrUserRoleFlag);
		}
		JsonPrintUtil.printObjDataWithKey(response, jsonObject, "data");
		/*if (StringUtils.isNotBlank(loginId) && StringUtils.isNotBlank(password) && status > 0) {
			password = DigestUtils.md5DigestAsHex(password.getBytes());
			if (status == 1) {
				SysUserExample example = new SysUserExample();
				Criteria criteria = example.createCriteria();
				criteria.andUserPasswordEqualTo(password).andLoginIdEqualTo(loginId).andStatusEqualTo(1);
				List<SysUser> sysUsers = sysUserMapper.selectByExample(example);
				if (sysUsers.size() > 0) {
					if (null != sysUsers.get(0)) {
						// 登陆成功
						jsonObject.put("loginStatus", "1");
						session.setAttribute("CurrentLoginUserInfo", sysUsers.get(0));
						session.setAttribute("CurrUserRoleFlag", "1");
					} else {
						jsonObject.put("loginStatus", "0");
					}
				} else {
					jsonObject.put("loginStatus", "0");
				}
			} else if (status == 2) {
				TeacherExample example = new TeacherExample();
				itf4.kaoba.model.TeacherExample.Criteria criteria = example.createCriteria();
				criteria.andTeaPasswordEqualTo(password).andLoginIdEqualTo(loginId).andStatusEqualTo(1);
				List<Teacher> teachers = teacherMapper.selectByExample(example);
				if (teachers.size() > 0) {
					if (null != teachers.get(0)) {
						// 登陆成功
						jsonObject.put("loginStatus", "2");
						session.setAttribute("CurrentLoginUserInfo", teachers.get(0));
						session.setAttribute("CurrUserRoleFlag", "2");
					} else {
						jsonObject.put("loginStatus", "0");
					}
				} else {
					jsonObject.put("loginStatus", "0");
				}
			} else {
				// 帐号密码错误
				jsonObject.put("loginStatus", "0");
			}
		} else {
			jsonObject.put("loginStatus", "0");
		}

		JsonPrintUtil.printObjDataWithKey(response, jsonObject, "data");*/
	}

	// 退出系统
	@RequestMapping(value = "/logout")
	public String logout(HttpServletRequest request) {
		request.getSession().invalidate();
		return "login";
	}

}
