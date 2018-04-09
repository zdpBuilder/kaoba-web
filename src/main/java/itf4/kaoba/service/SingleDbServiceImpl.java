package itf4.kaoba.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import itf4.kaoba.mapper.AnswerDbMapper;
import itf4.kaoba.mapper.SingleDbMapper;
import itf4.kaoba.model.AnswerDb;
import itf4.kaoba.model.SingleDb;
import itf4.kaoba.model.SysUser;
import itf4.kaoba.model.Teacher;
import itf4.kaoba.util.Const;
import itf4.kaoba.util.DateUtil;
import itf4.kaoba.util.ExcelUtil;
@Service
public class SingleDbServiceImpl implements SingleDbService{
	
	@Autowired
	private SingleDbMapper singleDbMapper;
	@Autowired
	private AnswerDbMapper answerDbMapper;
	
	public int importUsersExcelInfo(HttpServletRequest request,InputStream in, MultipartFile file, String courseId) throws Exception {
		int result=0;
		int sum=0;
		int num=0;
		String CurrentUserName = "";
		if (request.getSession().getAttribute(Const.SESSION_USER_STATUS).equals("1")) {
			SysUser user = (SysUser) request.getSession().getAttribute(Const.SESSION_USER);
			CurrentUserName = user.getUserName();
		} else if (request.getSession().getAttribute(Const.SESSION_USER_STATUS).equals("2")) {
			Teacher teacher = (Teacher) request.getSession().getAttribute(Const.SESSION_USER);
			CurrentUserName = teacher.getTeaName();
		}
		
		List<List<Object>> listob = ExcelUtil.getBankListByExcel(in,file.getOriginalFilename());
		num=listob.size();
	    List<SingleDb> singleList = new ArrayList<SingleDb>();
	    List<AnswerDb> answerDbList = new ArrayList<AnswerDb>();
	    //遍历listob数据，把数据放到List中  
	    for (int i = 0; i < listob.size(); i++) {  
	    	
	        List<Object> ob = listob.get(i);
	        System.out.println(listob.get(i));
	        SingleDb singleDb = new SingleDb();
	        AnswerDb answerDb = new AnswerDb();
	        
	        singleDb.setCourseId(Integer.parseInt(courseId));
	        singleDb.setSingledbTitle(String.valueOf(ob.get(0)));
	        singleDb.setSingledbOptiona(String.valueOf(ob.get(1)));
	        singleDb.setSingledbOptionb(String.valueOf(ob.get(2)));
	        singleDb.setSingledbOptionc(String.valueOf(ob.get(3)));
	        singleDb.setSingledbOptiond(String.valueOf(ob.get(4)));
	        singleDb.setStatus(1);
	        singleDb.setCreater(CurrentUserName);
	        singleDb.setCreateTime(DateUtil.DateToString(new Date(), "yyyy-MM-dd "));
	        
	        result= singleDbMapper.insert(singleDb);
	        sum = sum + result;
	        System.out.println(singleDb.getId());
	        answerDb.setDbId(singleDb.getId());
	        answerDb.setAnswerdbKey(String.valueOf(ob.get(5)));
	        answerDb.setAnswerdbDetail(String.valueOf(ob.get(6)));
	        answerDb.setAnswerdbDbtype(0);
	        answerDb.setStatus(1);
	        answerDb.setCreater(CurrentUserName);
	        answerDb.setCreateTime(DateUtil.DateToString(new Date(), "yyyy-MM-dd "));
	        
	        result=answerDbMapper.insert(answerDb);
	        sum = sum + result;
	    }
	    if ((2*num) == sum) {
			return 1;
		}else {
			return 0;
		}
	}


}
