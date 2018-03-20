package itf4.kaoba.controller;

import java.io.InputStream;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.alibaba.fastjson.JSON;
import itf4.kaoba.common.ResponseJsonPageListBean;
import itf4.kaoba.mapper.SysUserMapper;
import itf4.kaoba.model.SysUser;
import itf4.kaoba.model.SysUserExample;
import itf4.kaoba.model.SysUserExample.Criteria;
import itf4.kaoba.service.UsersService;
import itf4.kaoba.util.DateUtil;
import itf4.kaoba.util.ExcelUtil;
import itf4.kaoba.util.JsonPrintUtil;
@Controller
@RequestMapping("/user")
public class UsersController {
	 @Autowired
	 private UsersService usersService;
	 @Autowired
	 private SysUserMapper sysUserMapper;
	
    
     
  // �û��б��ҳ��ѯ
 	@RequestMapping("list")
 	@ResponseBody
 	public void equipmentList(HttpServletRequest request, HttpServletResponse response, String keywords, int limit,
 			int page) {
 		// limit ÿҳ��ʾ����
 		// page ��ǰҳ��
 		SysUserExample example = new SysUserExample();
 		// ���÷�ҳ��ѯ����
 		example.setStartRow((page - 1) * limit);
 		example.setPageSize(limit);
 		example.setOrderByClause("create_time desc,update_time desc");
 		Criteria criteria = example.createCriteria();
 		if (keywords!=null&&keywords!="") {
 			keywords = keywords.trim();
 			keywords = "%" + keywords + "%";
 			// and or���ϲ�ѯ
 			example.or().andUserNameLike(keywords).andStatusEqualTo(1);
 			example.or().andLoginIdLike(keywords).andStatusEqualTo(1);
 		} else {
 			criteria.andStatusEqualTo(1);// ����״̬
 		}
 		// ��ҳ��ѯ
 		List<SysUser> sysUsers = sysUserMapper.selectByExample(example);
 		int count = (int) sysUserMapper.countByExample(example);

 		ResponseJsonPageListBean listBean = new ResponseJsonPageListBean();
 		listBean.setCode(0);
 		listBean.setCount(count);
 		listBean.setMsg("�û��б�");
 		listBean.setData(sysUsers);

 		// ��־��¼�����ǰ̨Json
 		if (null != sysUsers && sysUsers.size() > 0) {
 			JsonPrintUtil.printObjDataWithoutKey(response, listBean);
 		} else {
 			JsonPrintUtil.printObjDataWithoutKey(response, null);
 		}
 	}
 	
 // �û����� �༭
 	@RequestMapping(value = "save", method = RequestMethod.POST)
 	@ResponseBody
 	public void save(SysUser sysUser, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
 		int count = 0;
 		SysUser currentLoginUser = (SysUser) session.getAttribute("CurrentLoginUserInfo");
 		// �༭�û�
 		if (null != sysUser.getId()&& sysUser.getId() > 0) {
 			SysUser userOld = sysUserMapper.selectByPrimaryKey(sysUser.getId());
 			sysUser.setUpdater(currentLoginUser.getUserName() + "");
 			sysUser.setUpdateTime(DateUtil.DateToString(new Date(), "yyyy-MM-dd "));
 			if (!userOld.getUserPassword().equals(sysUser.getUserPassword())) {
 				sysUser.setUserPassword(DigestUtils.md5DigestAsHex(sysUser.getUserPassword().getBytes()));
 			}
 			count = sysUserMapper.updateByPrimaryKeySelective(sysUser);

 			//���ǰ̨Json
 			if (count > 0) {
 				JsonPrintUtil.printObjDataWithKey(response, sysUser, "data");
 			}
 		} else {
 			// �����û�
 			sysUser.setStatus(1);
 			sysUser.setCreater(currentLoginUser.getId() + "");
 			sysUser.setCreateTime(DateUtil.DateToString(new Date(), "yyyy-MM-dd "));
 			sysUser.setUserPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
 			count = sysUserMapper.insert(sysUser);
 			//���ǰ̨Json
 			if (count > 0) {
 				JsonPrintUtil.printObjDataWithKey(response, count, "data");
 			}
 		}
 	}

 	// �û�����ɾ��
 	@RequestMapping(value = "deleteBatch", method = RequestMethod.POST)
 	@ResponseBody
 	public void deleteBatch(String idStr, HttpServletRequest request, HttpServletResponse response,
 			HttpSession session) {
 		SysUser currentLoginUser = (SysUser) session.getAttribute("CurrentLoginUserInfo");
 		if (StringUtils.isNotBlank(idStr)) {
 			String[] idArr = idStr.split(",");
 			for (int i = 0; i < idArr.length; i++) {
 				// ������ѡ�豸����Ϊɾ��״̬
 				int id = Integer.parseInt(idArr[i]);
 				SysUser user = sysUserMapper.selectByPrimaryKey(id);
 				user.setStatus(3);// 1���� 3��ɾ��
 				user.setUpdateTime(DateUtil.DateToString(new Date(), "yyyy-MM-dd "));
 				user.setUpdater(currentLoginUser.getUserName() + "");
 				sysUserMapper.updateByPrimaryKeySelective(user);
 			}
 			// ���ǰ̨Json
 			JsonPrintUtil.printObjDataWithKey(response, 1, "data");
 		} else {
 			JsonPrintUtil.printObjDataWithKey(response, 0, "data");
 		}
 	}

 	// �û��鿴
 	@RequestMapping(value = "show", method = RequestMethod.POST)
 	@ResponseBody
 	public void showWH(int id, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
 		SysUser user = sysUserMapper.selectByPrimaryKey(id);
 		if (null != user) {		
 			JsonPrintUtil.printObjDataWithKey(response, user, "data");
 		} else {
 			JsonPrintUtil.printObjDataWithKey(response, null, "data");
 		}
 	}	
 	/**
	 * ��������
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insertUsersBatch")  
	 @ResponseBody
	 public int impots(HttpServletRequest request, Model model) throws Exception {  
	      //��ȡ�ϴ����ļ�  
	      MultipartHttpServletRequest multipart = (MultipartHttpServletRequest) request;  
	      MultipartFile file = multipart.getFile("upfile");       
	      InputStream in = file.getInputStream();  
	      //���ݵ���  
	     int result= usersService.importUsersExcelInfo(in, file); 
	      in.close();  
	      return result;  
	 }
	/**
	 * �û���Ϣ����
	 * @param request
	 * @param response
	 * @param queryJson
	 */
	 @RequestMapping(value = "/export", method = RequestMethod.GET)  
	    public void export(HttpServletRequest request, HttpServletResponse response,  
	                        @RequestParam(value = "queryJson") String queryJson) {  
	        
		 SysUser users = JSON.parseObject(queryJson, SysUser.class);  
	       
	        List<SysUser> usersList = usersService.getUserForExcel(users);
	       ExcelUtil<SysUser> export=new ExcelUtil<SysUser>();	       
	        String[] headers = { "�˺�", "����", "����", "״̬","������","����ʱ��","������","����ʱ��" };  
	        String fileName = "�û���Ϣ��";  
	        export.exportExcel(headers,usersList,fileName,response);  
	       
	    }  
}
