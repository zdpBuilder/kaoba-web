package itf4.kaoba.util;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.http.HttpSession;

import org.junit.Test;

import itf4.kaoba.model.Student;
import itf4.kaoba.pojo.RolePojo;

/**
 *��ȡ��¼�û��Ľ�ɫ 
 * @author yangfan
 *
 */
public class GetSessionRoleUtil {
	private static RolePojo rolePojo = new RolePojo();
	private static Class<?> clazz;
	private static String attribute = null;
	private static Method method =null;
	private static String preffix = "get";
	
	//��ȡ��¼��ɫ��Ϣ
	public static RolePojo getRole(HttpSession session) throws Exception {
		
		String CurrUserRoleFlag =(String)session.getAttribute("CurrUserRoleFlag");
		Object object = session.getAttribute("CurrentLoginUserInfo");
		
		if(object !=null) {
			Class<?> clazz = object.getClass();
			//����Ա
			if(CurrUserRoleFlag.equals("1")) {
			    //����
				rolePojo.setLoginName(getAttribute("UserName",object));
				//����
				rolePojo.setLoginPassword(getAttribute("UserPassword",object));
			
			//��ʦ
			} else if(CurrUserRoleFlag.equals("2")) {
				//����
				rolePojo.setLoginName(getAttribute("TeaName",object));
				//����
				rolePojo.setLoginPassword(getAttribute("TeaPassword",object));
		    
				//ѧ��
			} else if(CurrUserRoleFlag.equals("3")) {
				//����
				rolePojo.setLoginName(getAttribute("StuName",object));
				//����
				rolePojo.setLoginPassword(getAttribute("StuPassword",object));
			}
		}
		
		//����
		rolePojo.setId(Integer.parseInt(getAttribute("Id",object)));
		//��־
		rolePojo.setStatus(Integer.parseInt(getAttribute("Status",object)));
		//������
		rolePojo.setCreater(getAttribute("Creater",object));
		//����ʱ��
		rolePojo.setCreateTime(getAttribute("CreateTime",object));
		//������
		rolePojo.setUpdater(getAttribute("Updater",object));
		//����ʱ��
		rolePojo.setUpdateTime(getAttribute("UpdateTime",object));
		
		return rolePojo;
	}
	
	//�����������ƣ���ȡ����ֵ
	private static String getAttribute(String name,Object object) throws Exception{
		clazz = object.getClass();
		method = clazz.getMethod(preffix +name, null);
		attribute = String.valueOf(method.invoke(object, null));
		return attribute;
		
	}
}

