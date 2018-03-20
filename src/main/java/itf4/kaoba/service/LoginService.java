package itf4.kaoba.service;
import itf4.kaoba.model.SysUser;

public interface LoginService {
   
  SysUser  CheckUsers(String usersNumbers,String password,Integer usersState);
	
}
