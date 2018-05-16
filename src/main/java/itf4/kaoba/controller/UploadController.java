package itf4.kaoba.controller;

import java.io.File;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import itf4.kaoba.util.DateUtil;
import lombok.extern.log4j.Log4j;

/**
 * @author:yang
 */
@Log4j
@Controller
public class UploadController {
	/** 允许文件格式 */
	private String allowSuffix = "gif,jpg,jpeg,bmp,png,GIF,JPG,JPEG,BMP,PNG";
	/** 允许文件大小10MB */
	private long allowSize = 10L;
	private String fileName;
	private String[] fileNames;
	/** 文件上传路径 */
	private String serverUploadFilePath;

	public String getAllowSuffix() {
		return allowSuffix;
	}

	public void setAllowSuffix(String allowSuffix) {
		this.allowSuffix = allowSuffix;
	}

	public long getAllowSize() {
		return allowSize * 1024 * 1024;
	}

	public void setAllowSize(long allowSize) {
		this.allowSize = allowSize;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String[] getFileNames() {
		return fileNames;
	}

	public void setFileNames(String[] fileNames) {
		this.fileNames = fileNames;
	}

	public String getServerUploadFilePath() {
		return serverUploadFilePath;
	}

	public void setServerUploadFilePath(String serverUploadFilePath) {
		this.serverUploadFilePath = serverUploadFilePath;
	}

	/**
	 * 命名服务器端新文件
	 */
	private String getFileNameNew() {
		String uuid = UUID.randomUUID().toString();
		return uuid;
	}

	/**
	 * 多个文件上传
	 */
	public void uploads(String businessType, MultipartFile[] files, HttpServletRequest request) {
		// 初始化设置文件上传路径
		serverUploadFilePath = request.getSession().getServletContext().getRealPath("/");

		try {
			fileNames = new String[files.length];
			int index = 0;
			for (MultipartFile file : files) {
				String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
				int length = getAllowSuffix().indexOf(suffix);
				if (length == -1) {
					log.error("请上传允许格式的文件！");
				}
				if (file.getSize() > getAllowSize()) {
					log.error("上传的文件大小超出系统允许范围！");
				}

				// String realPath =
				// request.getSession().getServletContext().getRealPath("/");
				File destFile = new File(serverUploadFilePath);
				if (!destFile.exists()) {
					destFile.mkdirs();
				}
				String fileNameNew = getFileNameNew() + "." + suffix;//
				File f = new File(destFile.getAbsoluteFile() + "\\" + fileNameNew);
				file.transferTo(f);
				f.createNewFile();
				fileNames[index++] = serverUploadFilePath + fileNameNew;
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	public String uploadToFileUrl(String businessType, MultipartFile file, HttpServletRequest request) {

		// 初始化设置文件上传路径
		serverUploadFilePath = request.getSession().getServletContext().getRealPath("/") + "upload_files/"
				+ businessType + "/";

		String fileUrl = "";
		try {
			String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
			int length = getAllowSuffix().indexOf(suffix);
			if (length == -1) {
				log.error("请上传允许格式的文件！");
			}
			if (file.getSize() > getAllowSize()) {
				log.error("上传的文件大小超出系统允许范围！");
			}

			// 格式化的年月日
			String nowDateTimeDir = DateUtil.DateToString(new Date(), "yyyy-MM-dd");
			// 根据年月日和taskId创建文件夹
			String path = serverUploadFilePath + nowDateTimeDir + "/";

			// String realPath =
			// request.getSession().getServletContext().getRealPath("/");
			File destFile = new File(path);
			if (!destFile.exists()) {
				destFile.mkdirs();
			}
			String fileNameNewStr = getFileNameNew();
			String fileNameNew = fileNameNewStr + "." + suffix;
			File f = new File(destFile.getAbsoluteFile() + "/" + fileNameNew);
			file.transferTo(f);
			f.createNewFile();

			fileUrl = nowDateTimeDir + "/" + fileNameNew;
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return fileUrl;
	}

	public String uploadToFilePath(String businessType, MultipartFile file, HttpServletRequest request) {

		// 初始化设置文件上传路径
		serverUploadFilePath = request.getSession().getServletContext().getRealPath("/") + "upload_files/"
				+ businessType + "/";

		String filePath = "";
		try {
			String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
			int length = getAllowSuffix().indexOf(suffix);
			if (length == -1) {
				log.error("请上传允许格式的文件！");
			}
			if (file.getSize() > getAllowSize()) {
				log.error("上传的文件大小超出系统允许范围！");
			}

			// 格式化的年月日
			String nowDateTimeDir = DateUtil.DateToString(new Date(), "yyyy-MM-dd");
			// 根据年月日和taskId创建文件夹
			String path = serverUploadFilePath + nowDateTimeDir + "/";

			// String realPath =
			// request.getSession().getServletContext().getRealPath("/");
			File destFile = new File(path);
			if (!destFile.exists()) {
				destFile.mkdirs();
			}
			String fileNameNewStr = getFileNameNew();
			String fileNameNew = fileNameNewStr + "." + suffix;
			File f = new File(destFile.getAbsoluteFile() + "/" + fileNameNew);
			file.transferTo(f);
			f.createNewFile();

			filePath = path + fileNameNew;
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return filePath;
	}
}
