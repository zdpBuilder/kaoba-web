package itf4.kaoba.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import itf4.kaoba.mapper.SysUserMapper;
import itf4.kaoba.model.SysUser;
import itf4.kaoba.model.SysUserExample;
import itf4.kaoba.model.SysUserExample.Criteria;
@Service
public class LoginServiceImpl implements LoginService {
  
	@Autowired
	private SysUserMapper usersMapper;
	public SysUser  CheckUsers(String usersNumbers,String password,Integer usersState) {
		//��0��ʦ��1ѧ����2����Ա��3��ʦ�鳤
		SysUserExample example= new SysUserExample();
		 Criteria criteria=example.createCriteria();
		 criteria.andStatusEqualTo(usersState);
		  List<SysUser> usersList=usersMapper.selectByExample(example);
		  for (SysUser users : usersList) {
	
			  if (usersNumbers.equals(users.getLoginId())&&password.equals(users.getUserPassword())) {   
		         return users;
		      }
		}  
		  return null;
	}



}
