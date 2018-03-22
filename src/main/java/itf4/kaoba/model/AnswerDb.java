package itf4.kaoba.model;

public class AnswerDb {
    private Integer id;

    private Integer dbId;

    private String answerdbKey;

    private String answerdbDetail;

    private String answerdbDetailPicPath;

    private Integer answerdbDbtype;

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

    public Integer getDbId() {
        return dbId;
    }

    public void setDbId(Integer dbId) {
        this.dbId = dbId;
    }

    public String getAnswerdbKey() {
        return answerdbKey;
    }

    public void setAnswerdbKey(String answerdbKey) {
        this.answerdbKey = answerdbKey == null ? null : answerdbKey.trim();
    }

    public String getAnswerdbDetail() {
        return answerdbDetail;
    }

    public void setAnswerdbDetail(String answerdbDetail) {
        this.answerdbDetail = answerdbDetail == null ? null : answerdbDetail.trim();
    }

    public String getAnswerdbDetailPicPath() {
        return answerdbDetailPicPath;
    }

    public void setAnswerdbDetailPicPath(String answerdbDetailPicPath) {
        this.answerdbDetailPicPath = answerdbDetailPicPath == null ? null : answerdbDetailPicPath.trim();
    }

    public Integer getAnswerdbDbtype() {
        return answerdbDbtype;
    }

    public void setAnswerdbDbtype(Integer answerdbDbtype) {
        this.answerdbDbtype = answerdbDbtype;
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