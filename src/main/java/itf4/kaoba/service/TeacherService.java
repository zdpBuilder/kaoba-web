package itf4.kaoba.service;

import java.io.InputStream;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import itf4.kaoba.mapper.TeacherMapper;

public interface TeacherService {
	public int importUsersExcelInfo(HttpSession session,InputStream in, MultipartFile file) throws Exception;
	
}
