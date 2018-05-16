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
	/** �����ļ���ʽ */
	private String allowSuffix = "gif,jpg,jpeg,bmp,png,GIF,JPG,JPEG,BMP,PNG";
	/** �����ļ���С10MB */
	private long allowSize = 10L;
	private String fileName;
	private String[] fileNames;
	/** �ļ��ϴ�·�� */
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
	 * ���������������ļ�
	 */
	private String getFileNameNew() {
		String uuid = UUID.randomUUID().toString();
		return uuid;
	}

	/**
	 * ����ļ��ϴ�
	 */
	public void uploads(String businessType, MultipartFile[] files, HttpServletRequest request) {
		// ��ʼ�������ļ��ϴ�·��
		serverUploadFilePath = request.getSession().getServletContext().getRealPath("/");

		try {
			fileNames = new String[files.length];
			int index = 0;
			for (MultipartFile file : files) {
				String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
				int length = getAllowSuffix().indexOf(suffix);
				if (length == -1) {
					log.error("���ϴ������ʽ���ļ���");
				}
				if (file.getSize() > getAllowSize()) {
					log.error("�ϴ����ļ���С����ϵͳ����Χ��");
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

		// ��ʼ�������ļ��ϴ�·��
		serverUploadFilePath = request.getSession().getServletContext().getRealPath("/") + "upload_files/"
				+ businessType + "/";

		String fileUrl = "";
		try {
			String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
			int length = getAllowSuffix().indexOf(suffix);
			if (length == -1) {
				log.error("���ϴ������ʽ���ļ���");
			}
			if (file.getSize() > getAllowSize()) {
				log.error("�ϴ����ļ���С����ϵͳ����Χ��");
			}

			// ��ʽ����������
			String nowDateTimeDir = DateUtil.DateToString(new Date(), "yyyy-MM-dd");
			// ���������պ�taskId�����ļ���
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

		// ��ʼ�������ļ��ϴ�·��
		serverUploadFilePath = request.getSession().getServletContext().getRealPath("/") + "upload_files/"
				+ businessType + "/";

		String filePath = "";
		try {
			String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
			int length = getAllowSuffix().indexOf(suffix);
			if (length == -1) {
				log.error("���ϴ������ʽ���ļ���");
			}
			if (file.getSize() > getAllowSize()) {
				log.error("�ϴ����ļ���С����ϵͳ����Χ��");
			}

			// ��ʽ����������
			String nowDateTimeDir = DateUtil.DateToString(new Date(), "yyyy-MM-dd");
			// ���������պ�taskId�����ļ���
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
