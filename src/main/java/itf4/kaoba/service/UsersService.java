package itf4.kaoba.service;

import java.io.InputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import itf4.kaoba.model.SysUser;
import itf4.kaoba.pojo.JsonUsersId;
public interface UsersService {
	public int importUsersExcelInfo(InputStream in, MultipartFile file) throws Exception;
	 public List<SysUser> getUserForExcel(SysUser users);
	 public List<SysUser> getUserList();
	 public int delUsersBatch(List<JsonUsersId> usersIdList);
	 public int save(SysUser users,Integer  userId);
}
