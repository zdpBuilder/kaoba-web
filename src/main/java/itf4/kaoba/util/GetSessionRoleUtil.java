package itf4.kaoba.util;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.http.HttpSession;

import org.junit.Test;

import itf4.kaoba.model.Student;
import itf4.kaoba.pojo.RolePojo;

/**
 *获取登录用户的角色 
 * @author yangfan
 *
 */
public class GetSessionRoleUtil {
	private static RolePojo rolePojo = new RolePojo();
	private static Class<?> clazz;
	private static Object object;
	private static String attribute = null;
	private static Method method =null;
	private static String preffix = "get";
	
	//获取登录角色信息
	public static RolePojo getRole(HttpSession session) throws Exception {
		
		String CurrUserRoleFlag =(String)session.getAttribute("CurrUserRoleFlag");
		object = session.getAttribute("CurrentLoginUserInfo");
		
		if(object !=null) {
			clazz = object.getClass();
			//管理员
			if(CurrUserRoleFlag.equals("1")) {
			    //姓名
				rolePojo.setLoginName(getAttribute("UserName"));
				//密码
				rolePojo.setLoginPassword(getAttribute("UserPassword"));
			
			//老师
			} else if(CurrUserRoleFlag.equals("2")) {
				//姓名
				rolePojo.setLoginName(getAttribute("TeaName"));
				//密码
				rolePojo.setLoginPassword(getAttribute("TeaPassword"));
		    
				//学生
			} else if(CurrUserRoleFlag.equals("3")) {
				//姓名
				rolePojo.setLoginName(getAttribute("StuName"));
				//密码
				rolePojo.setLoginPassword(getAttribute("StuPassword"));
			}
		}
		
		//主键
		rolePojo.setId(Integer.parseInt(getAttribute("Id")));
		//标志
		rolePojo.setStatus(Integer.parseInt(getAttribute("Status")));
		//创建者
		rolePojo.setCreater(getAttribute("Creater"));
		//创建时间
		rolePojo.setCreateTime(getAttribute("CreateTime"));
		//更新者
		rolePojo.setUpdater(getAttribute("Updater"));
		//更新时间
		rolePojo.setUpdateTime(getAttribute("UpdateTime"));
		
		return rolePojo;
	}
	
	//根据属性名称，获取属性值
	private static String getAttribute(String name) throws Exception{
		method = clazz.getMethod(preffix +name, null);
		attribute = String.valueOf(method.invoke(object, null));
		return attribute;
		
	}
}

