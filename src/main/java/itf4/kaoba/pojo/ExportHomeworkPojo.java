package itf4.kaoba.pojo;

public class ExportHomeworkPojo {

	private String loginId;
	private String stuName;
	private Integer stuGrade;
	private String homworkName;
	private String exportTime;

	public String getStuName() {
		return stuName;
	}

	public void setStuName(String stuName) {
		this.stuName = stuName;
	}

	public Integer getStuGrade() {
		return stuGrade;
	}

	public void setStuGrade(Integer stuGrade) {
		this.stuGrade = stuGrade;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getHomworkName() {
		return homworkName;
	}

	public void setHomworkName(String homworkName) {
		this.homworkName = homworkName;
	}

	public String getExportTime() {
		return exportTime;
	}

	public void setExportTime(String exportTime) {
		this.exportTime = exportTime;
	}

}
