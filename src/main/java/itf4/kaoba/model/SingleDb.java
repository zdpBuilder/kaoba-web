package itf4.kaoba.model;

public class SingleDb {
    private Integer id;

    private Integer courseId;

    private String singledbTitle;

    private String singledbOptiona;

    private String singledbOptionb;

    private String singledbOptionc;

    private String singledbOptiond;

    private Integer status;

    private String creater;

    private String createTime;

    private String updater;

    private String updateTime;

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
        this.singledbTitle = singledbTitle == null ? null : singledbTitle.trim();
    }

    public String getSingledbOptiona() {
        return singledbOptiona;
    }

    public void setSingledbOptiona(String singledbOptiona) {
        this.singledbOptiona = singledbOptiona == null ? null : singledbOptiona.trim();
    }

    public String getSingledbOptionb() {
        return singledbOptionb;
    }

    public void setSingledbOptionb(String singledbOptionb) {
        this.singledbOptionb = singledbOptionb == null ? null : singledbOptionb.trim();
    }

    public String getSingledbOptionc() {
        return singledbOptionc;
    }

    public void setSingledbOptionc(String singledbOptionc) {
        this.singledbOptionc = singledbOptionc == null ? null : singledbOptionc.trim();
    }

    public String getSingledbOptiond() {
        return singledbOptiond;
    }

    public void setSingledbOptiond(String singledbOptiond) {
        this.singledbOptiond = singledbOptiond == null ? null : singledbOptiond.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater == null ? null : creater.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater == null ? null : updater.trim();
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime == null ? null : updateTime.trim();
    }
}