package itf4.kaoba.service;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;

import org.omg.CORBA.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.druid.util.StringUtils;
import com.fasterxml.jackson.annotation.JsonFormat.Value;

import itf4.kaoba.mapper.SysUserMapper;

import itf4.kaoba.model.SysUser;
import itf4.kaoba.pojo.JsonUsersId;
import itf4.kaoba.util.ExcelUtil;
import itf4.kaoba.util.JsonUtils;

@Service
public class UsersServiceImpl implements UsersService {

	@Autowired
	private SysUserMapper sysUserMapper;
	
	/****
	 *��Excel���������û���Ϣ
	 * @param in
	 * @param file
	 * @throws Exception
	 */
	public int importUsersExcelInfo(InputStream in, MultipartFile file) throws Exception {
		  List<List<Object>> listob = ExcelUtil.getBankListByExcel(in,file.getOriginalFilename());  
		    List<SysUser> UsersList = new ArrayList<SysUser>();  
		    //����listob���ݣ������ݷŵ�List��  
		    for (int i = 0; i < listob.size(); i++) {  
		    	
		        List<Object> ob = listob.get(i);
		        System.out.println(listob.get(i));
		        SysUser users = new SysUser(); 
		        users.setLoginId(String.valueOf(ob.get(0)));	
		         users.setUserName(String.valueOf(ob.get(1)));     
		         users.setUserPassword(DigestUtils.md5DigestAsHex(String.valueOf(ob.get(2)).getBytes()));
		         users.setStatus(Integer.parseInt((String)ob.get(3)));
		         users.setCreater(String.valueOf(ob.get(4)));
		         users.setCreateTime(String.valueOf(ob.get(5)));
		         users.setUpdater(String.valueOf(ob.get(6)));
		         users.setUpdateTime(String.valueOf(ob.get(7)));
		        UsersList.add(users);  
		    }  
		    //��������  
		   int result= sysUserMapper.insertUsersBatch(UsersList); 	
	    return result;
	}

	public List<SysUser> getUserForExcel(SysUser users) {
		 List<SysUser> list = sysUserMapper.getAllUsersForExcel(users);  
	       /* Integer order;  
	        for (int i = 0; i < list.size(); i++) {  
	            order = i + 1;  
	              list.get(i).  
	           //���⴦��������
	        }  */
	        return list;  
	}

	public List<SysUser> getUserList() {
		List<SysUser> list=sysUserMapper.selectByExample(null);
		return list;
	}

	public int delUsersBatch(List<JsonUsersId> usersIdList) {
		int result=sysUserMapper.delUsersBatch(usersIdList);
		return result;
	}

	public int save(SysUser users,Integer  userId) {
		int result=0;
		if(userId>0) {
			//ֻ���¸ı���ֶ�
			result=sysUserMapper.updateByPrimaryKeySelective(users);
		}else {
			result=sysUserMapper.insert(users);
		}
		return result;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*	@Autowired
	UsersCustomMapper usersCustomMapper;
	@Autowired
	SysUserMapper usersMapper;
	
	*//***
	 *��Excel���������û���Ϣ
	 * @param in
	 * @param file
	 * @throws Exception
	 *//*
	public int importUsersExcelInfo(InputStream in, MultipartFile file)
			throws Exception {
		  List<List<Object>> listob = ExcelUtil.getBankListByExcel(in,file.getOriginalFilename());  
		    List<SysUser> UsersList = new ArrayList<SysUser>();  
		    //����listob���ݣ������ݷŵ�List��  
		    for (int i = 0; i < listob.size(); i++) {  
		    	
		        List<Object> ob = listob.get(i);
		        System.out.println(listob.get(i));
		        SysUser users = new SysUser(); 
		        users.setUsersNumbers(String.valueOf(ob.get(0)));	
		         users.setUsersName(String.valueOf(ob.get(1)));     
		         users.setUsersPassword(String.valueOf(ob.get(2)));
		         users.setUsersStatus(Integer.valueOf(3));
		         users.setUsersOptiondate(String.valueOf(ob.get(4)));
		        UsersList.add(users);  
		    }  
		    //��������  
		   int result= usersCustomMapper.insertUsersBatch(UsersList); 	
	    return result;
	}  
	*//** 
     * ���ݲ�ѯ������ѯ�����еļ�¼,���÷�ҳ,����excel��������   
     * @param 
     * @return 
     *//*  
    public List<SysUser> getUserForExcel(SysUser users) {  
        List<SysUser> list = usersCustomMapper.getAllUsersForExcel(users);  
        Integer order;  
        for (int i = 0; i < list.size(); i++) {  
            order = i + 1;  
              list.get(i).setOrder(order.toString());  
           if (list.get(i).getSex().equals("1")) {  
                list.get(i).setSex("��");  
            } else {  
                list.get(i).setSex("Ů"); 
            }  
        }  
        return list;  
    }
	public List<SysUser> getAllUser() {
		
		List<SysUser> list= usersMapper.selectByExample(null);
		return list;
	}
	public int insertUsersSingle(SysUser users) {
		 int result=usersMapper.insert(users);
		return result;
	}
	public int delUsersSingle(int usersId) {
		int result=usersMapper.deleteByPrimaryKey(usersId);
		return result;
	}
	public int delUsersBatch(List<JsonUsersId> usersIdList) {
		int result=usersCustomMapper.delUsersBatch(usersIdList);
		return result;
	}
	public int editUsers(SysUser users) {
	int result=usersMapper.updateByPrimaryKeySelective(users);
		return result;
	}
	
	*/
	
}
