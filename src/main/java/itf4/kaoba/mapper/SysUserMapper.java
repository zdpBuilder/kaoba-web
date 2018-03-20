package itf4.kaoba.mapper;

import itf4.kaoba.model.SysUser;
import itf4.kaoba.model.SysUserExample;
import itf4.kaoba.pojo.JsonUsersId;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysUserMapper {
    long countByExample(SysUserExample example);

    int deleteByExample(SysUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    List<SysUser> selectByExample(SysUserExample example);

    SysUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SysUser record, @Param("example") SysUserExample example);

    int updateByExample(@Param("record") SysUser record, @Param("example") SysUserExample example);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);
    /**
	   * 根据查询条件查询出所有的记录,不用分页,用于excel导出功能 
	   * @param user
	   * @return
	   */
	    List<SysUser> getAllUsersForExcel(SysUser users);  
	    /**
	     * 用foreach插入数据库，实现excel批量插入用户信息功能
	     * @param userList
	     * @return
	     */
	     int  insertUsersBatch(List<SysUser> usersList);
	     /**
	      * 批量删除Users
	      * @param usersIdList
	      * @return
	      */
	     int delUsersBatch(List<JsonUsersId> usersIdList);
}