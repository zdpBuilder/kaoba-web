package itf4.kaoba.service;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

public interface SingleDbService {

	int importUsersExcelInfo(HttpServletRequest request,InputStream in, MultipartFile file, String courseId) throws Exception;

}
