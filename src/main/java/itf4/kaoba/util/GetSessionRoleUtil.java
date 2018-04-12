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
	private static Object object;
	private static String attribute = null;
	private static Method method =null;
	private static String preffix = "get";
	
	//��ȡ��¼��ɫ��Ϣ
	public static RolePojo getRole(HttpSession session) throws Exception {
		
		String CurrUserRoleFlag =(String)session.getAttribute("CurrUserRoleFlag");
		object = session.getAttribute("CurrentLoginUserInfo");
		
		if(object !=null) {
			clazz = object.getClass();
			//����Ա
			if(CurrUserRoleFlag.equals("1")) {
			    //����
				rolePojo.setLoginName(getAttribute("UserName"));
				//����
				rolePojo.setLoginPassword(getAttribute("UserPassword"));
			
			//��ʦ
			} else if(CurrUserRoleFlag.equals("2")) {
				//����
				rolePojo.setLoginName(getAttribute("TeaName"));
				//����
				rolePojo.setLoginPassword(getAttribute("TeaPassword"));
		    
				//ѧ��
			} else if(CurrUserRoleFlag.equals("3")) {
				//����
				rolePojo.setLoginName(getAttribute("StuName"));
				//����
				rolePojo.setLoginPassword(getAttribute("StuPassword"));
			}
		}
		
		//����
		rolePojo.setId(Integer.parseInt(getAttribute("Id")));
		//��־
		rolePojo.setStatus(Integer.parseInt(getAttribute("Status")));
		//������
		rolePojo.setCreater(getAttribute("Creater"));
		//����ʱ��
		rolePojo.setCreateTime(getAttribute("CreateTime"));
		//������
		rolePojo.setUpdater(getAttribute("Updater"));
		//����ʱ��
		rolePojo.setUpdateTime(getAttribute("UpdateTime"));
		
		return rolePojo;
	}
	
	//�����������ƣ���ȡ����ֵ
	private static String getAttribute(String name) throws Exception{
		method = clazz.getMethod(preffix +name, null);
		attribute = String.valueOf(method.invoke(object, null));
		return attribute;
		
	}
}

