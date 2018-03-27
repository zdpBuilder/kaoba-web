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
	private static String attribute = null;
	private static Method method =null;
	private static String preffix = "get";
	
	//获取登录角色信息
	public static RolePojo getRole(HttpSession session) throws Exception {
		
		String CurrUserRoleFlag =(String)session.getAttribute("CurrUserRoleFlag");
		Object object = session.getAttribute("CurrentLoginUserInfo");
		
		if(object !=null) {
			Class<?> clazz = object.getClass();
			//管理员
			if(CurrUserRoleFlag.equals("1")) {
			    //姓名
				rolePojo.setLoginName(getAttribute("UserName",object));
				//密码
				rolePojo.setLoginPassword(getAttribute("UserPassword",object));
			
			//老师
			} else if(CurrUserRoleFlag.equals("2")) {
				//姓名
				rolePojo.setLoginName(getAttribute("TeaName",object));
				//密码
				rolePojo.setLoginPassword(getAttribute("TeaPassword",object));
		    
				//学生
			} else if(CurrUserRoleFlag.equals("3")) {
				//姓名
				rolePojo.setLoginName(getAttribute("StuName",object));
				//密码
				rolePojo.setLoginPassword(getAttribute("StuPassword",object));
			}
		}
		
		//主键
		rolePojo.setId(Integer.parseInt(getAttribute("Id",object)));
		//标志
		rolePojo.setStatus(Integer.parseInt(getAttribute("Status",object)));
		//创建者
		rolePojo.setCreater(getAttribute("Creater",object));
		//创建时间
		rolePojo.setCreateTime(getAttribute("CreateTime",object));
		//更新者
		rolePojo.setUpdater(getAttribute("Updater",object));
		//更新时间
		rolePojo.setUpdateTime(getAttribute("UpdateTime",object));
		
		return rolePojo;
	}
	
	//根据属性名称，获取属性值
	private static String getAttribute(String name,Object object) throws Exception{
		clazz = object.getClass();
		method = clazz.getMethod(preffix +name, null);
		attribute = String.valueOf(method.invoke(object, null));
		return attribute;
		
	}
}

