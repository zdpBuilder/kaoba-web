package itf4.kaoba.pojo;

public class SingledbVSAnswerPojo {
    
	    private Integer id;
       
	    private  Integer studentId;
	   
        private Integer courseId;

	    private String singledbTitle;

	    private String singledbOptiona;

	    private String singledbOptionb;

	    private String singledbOptionc;

	    private String singledbOptiond;

	    private Integer status;
	    
	    private String answerdbKey;

	    private String answerdbDetail;
	    
	    private String answerdbDetailPicPath;
	    
	    private Integer answerdbDbtype;

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public Integer getCourseId() {
			return courseId;
		}

		public void setCourseId(Integer courseId) {
			this.courseId = courseId;
		}

		public String getSingledbTitle() {
			return singledbTitle;
		}

		public void setSingledbTitle(String singledbTitle) {
			this.singledbTitle = singledbTitle;
		}

		public String getSingledbOptiona() {
			return singledbOptiona;
		}

		public void setSingledbOptiona(String singledbOptiona) {
			this.singledbOptiona = singledbOptiona;
		}

		public String getSingledbOptionb() {
			return singledbOptionb;
		}

		public void setSingledbOptionb(String singledbOptionb) {
			this.singledbOptionb = singledbOptionb;
		}

		public String getSingledbOptionc() {
			return singledbOptionc;
		}

		public void setSingledbOptionc(String singledbOptionc) {
			this.singledbOptionc = singledbOptionc;
		}

		public String getSingledbOptiond() {
			return singledbOptiond;
		}

		public void setSingledbOptiond(String singledbOptiond) {
			this.singledbOptiond = singledbOptiond;
		}

		public Integer getStatus() {
			return status;
		}

		public void setStatus(Integer status) {
			this.status = status;
		}

		public String getAnswerdbKey() {
			return answerdbKey;
		}

		public void setAnswerdbKey(String answerdbKey) {
			this.answerdbKey = answerdbKey;
		}

		public String getAnswerdbDetail() {
			return answerdbDetail;
		}

		public void setAnswerdbDetail(String answerdbDetail) {
			this.answerdbDetail = answerdbDetail;
		}

		public String getAnswerdbDetailPicPath() {
			return answerdbDetailPicPath;
		}

		public void setAnswerdbDetailPicPath(String answerdbDetailPicPath) {
			this.answerdbDetailPicPath = answerdbDetailPicPath;
		}

		public Integer getStudentId() {
			return studentId;
		}

		public void setStudentId(Integer studentId) {
			this.studentId = studentId;
		}

		public Integer getAnswerdbDbtype() {
			return answerdbDbtype;
		}

		public void setAnswerdbDbtype(Integer answerdbDbtype) {
			this.answerdbDbtype = answerdbDbtype;
		}
	    
	    

}
