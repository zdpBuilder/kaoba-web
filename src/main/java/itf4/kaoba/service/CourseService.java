package itf4.kaoba.service;

import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;
  
public interface CourseService {

	int importCourseExcelInfo(InputStream in, MultipartFile file) throws Exception;

}
