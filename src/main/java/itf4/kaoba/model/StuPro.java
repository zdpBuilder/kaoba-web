package itf4.kaoba.model;

public class StuPro extends StuProKey {
    private Integer stuId;

    private Integer courseId;

    private Integer stuProDbtype;

    private Integer stuProType;

    private Integer status;

    private String creater;

    private String createTime;

    private String updater;

    private String updateTime;

    public Integer getStuId() {
        return stuId;
    }

    public void setStuId(Integer stuId) {
        this.stuId = stuId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getStuProDbtype() {
        return stuProDbtype;
    }

    public void setStuProDbtype(Integer stuProDbtype) {
        this.stuProDbtype = stuProDbtype;
    }

    public Integer getStuProType() {
        return stuProType;
    }

    public void setStuProType(Integer stuProType) {
        this.stuProType = stuProType;
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