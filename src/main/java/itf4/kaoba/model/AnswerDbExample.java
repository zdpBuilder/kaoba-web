package itf4.kaoba.model;

import java.util.ArrayList;
import java.util.List;

public class AnswerDbExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public AnswerDbExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andDbIdIsNull() {
            addCriterion("db_id is null");
            return (Criteria) this;
        }

        public Criteria andDbIdIsNotNull() {
            addCriterion("db_id is not null");
            return (Criteria) this;
        }

        public Criteria andDbIdEqualTo(Integer value) {
            addCriterion("db_id =", value, "dbId");
            return (Criteria) this;
        }

        public Criteria andDbIdNotEqualTo(Integer value) {
            addCriterion("db_id <>", value, "dbId");
            return (Criteria) this;
        }

        public Criteria andDbIdGreaterThan(Integer value) {
            addCriterion("db_id >", value, "dbId");
            return (Criteria) this;
        }

        public Criteria andDbIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("db_id >=", value, "dbId");
            return (Criteria) this;
        }

        public Criteria andDbIdLessThan(Integer value) {
            addCriterion("db_id <", value, "dbId");
            return (Criteria) this;
        }

        public Criteria andDbIdLessThanOrEqualTo(Integer value) {
            addCriterion("db_id <=", value, "dbId");
            return (Criteria) this;
        }

        public Criteria andDbIdIn(List<Integer> values) {
            addCriterion("db_id in", values, "dbId");
            return (Criteria) this;
        }

        public Criteria andDbIdNotIn(List<Integer> values) {
            addCriterion("db_id not in", values, "dbId");
            return (Criteria) this;
        }

        public Criteria andDbIdBetween(Integer value1, Integer value2) {
            addCriterion("db_id between", value1, value2, "dbId");
            return (Criteria) this;
        }

        public Criteria andDbIdNotBetween(Integer value1, Integer value2) {
            addCriterion("db_id not between", value1, value2, "dbId");
            return (Criteria) this;
        }

        public Criteria andAnswerdbKeyIsNull() {
            addCriterion("answerdb_key is null");
            return (Criteria) this;
        }

        public Criteria andAnswerdbKeyIsNotNull() {
            addCriterion("answerdb_key is not null");
            return (Criteria) this;
        }

        public Criteria andAnswerdbKeyEqualTo(String value) {
            addCriterion("answerdb_key =", value, "answerdbKey");
            return (Criteria) this;
        }

        public Criteria andAnswerdbKeyNotEqualTo(String value) {
            addCriterion("answerdb_key <>", value, "answerdbKey");
            return (Criteria) this;
        }

        public Criteria andAnswerdbKeyGreaterThan(String value) {
            addCriterion("answerdb_key >", value, "answerdbKey");
            return (Criteria) this;
        }

        public Criteria andAnswerdbKeyGreaterThanOrEqualTo(String value) {
            addCriterion("answerdb_key >=", value, "answerdbKey");
            return (Criteria) this;
        }

        public Criteria andAnswerdbKeyLessThan(String value) {
            addCriterion("answerdb_key <", value, "answerdbKey");
            return (Criteria) this;
        }

        public Criteria andAnswerdbKeyLessThanOrEqualTo(String value) {
            addCriterion("answerdb_key <=", value, "answerdbKey");
            return (Criteria) this;
        }

        public Criteria andAnswerdbKeyLike(String value) {
            addCriterion("answerdb_key like", value, "answerdbKey");
            return (Criteria) this;
        }

        public Criteria andAnswerdbKeyNotLike(String value) {
            addCriterion("answerdb_key not like", value, "answerdbKey");
            return (Criteria) this;
        }

        public Criteria andAnswerdbKeyIn(List<String> values) {
            addCriterion("answerdb_key in", values, "answerdbKey");
            return (Criteria) this;
        }

        public Criteria andAnswerdbKeyNotIn(List<String> values) {
            addCriterion("answerdb_key not in", values, "answerdbKey");
            return (Criteria) this;
        }

        public Criteria andAnswerdbKeyBetween(String value1, String value2) {
            addCriterion("answerdb_key between", value1, value2, "answerdbKey");
            return (Criteria) this;
        }

        public Criteria andAnswerdbKeyNotBetween(String value1, String value2) {
            addCriterion("answerdb_key not between", value1, value2, "answerdbKey");
            return (Criteria) this;
        }

        public Criteria andAnswerdbDetailIsNull() {
            addCriterion("answerdb_detail is null");
            return (Criteria) this;
        }

        public Criteria andAnswerdbDetailIsNotNull() {
            addCriterion("answerdb_detail is not null");
            return (Criteria) this;
        }

        public Criteria andAnswerdbDetailEqualTo(String value) {
            addCriterion("answerdb_detail =", value, "answerdbDetail");
            return (Criteria) this;
        }

        public Criteria andAnswerdbDetailNotEqualTo(String value) {
            addCriterion("answerdb_detail <>", value, "answerdbDetail");
            return (Criteria) this;
        }

        public Criteria andAnswerdbDetailGreaterThan(String value) {
            addCriterion("answerdb_detail >", value, "answerdbDetail");
            return (Criteria) this;
        }

        public Criteria andAnswerdbDetailGreaterThanOrEqualTo(String value) {
            addCriterion("answerdb_detail >=", value, "answerdbDetail");
            return (Criteria) this;
        }

        public Criteria andAnswerdbDetailLessThan(String value) {
            addCriterion("answerdb_detail <", value, "answerdbDetail");
            return (Criteria) this;
        }

        public Criteria andAnswerdbDetailLessThanOrEqualTo(String value) {
            addCriterion("answerdb_detail <=", value, "answerdbDetail");
            return (Criteria) this;
        }

        public Criteria andAnswerdbDetailLike(String value) {
            addCriterion("answerdb_detail like", value, "answerdbDetail");
            return (Criteria) this;
        }

        public Criteria andAnswerdbDetailNotLike(String value) {
            addCriterion("answerdb_detail not like", value, "answerdbDetail");
            return (Criteria) this;
        }

        public Criteria andAnswerdbDetailIn(List<String> values) {
            addCriterion("answerdb_detail in", values, "answerdbDetail");
            return (Criteria) this;
        }

        public Criteria andAnswerdbDetailNotIn(List<String> values) {
            addCriterion("answerdb_detail not in", values, "answerdbDetail");
            return (Criteria) this;
        }

        public Criteria andAnswerdbDetailBetween(String value1, String value2) {
            addCriterion("answerdb_detail between", value1, value2, "answerdbDetail");
            return (Criteria) this;
        }

        public Criteria andAnswerdbDetailNotBetween(String value1, String value2) {
            addCriterion("answerdb_detail not between", value1, value2, "answerdbDetail");
            return (Criteria) this;
        }

        public Criteria andAnswerdbDetailPicPathIsNull() {
            addCriterion("answerdb_detail_pic_path is null");
            return (Criteria) this;
        }

        public Criteria andAnswerdbDetailPicPathIsNotNull() {
            addCriterion("answerdb_detail_pic_path is not null");
            return (Criteria) this;
        }

        public Criteria andAnswerdbDetailPicPathEqualTo(String value) {
            addCriterion("answerdb_detail_pic_path =", value, "answerdbDetailPicPath");
            return (Criteria) this;
        }

        public Criteria andAnswerdbDetailPicPathNotEqualTo(String value) {
            addCriterion("answerdb_detail_pic_path <>", value, "answerdbDetailPicPath");
            return (Criteria) this;
        }

        public Criteria andAnswerdbDetailPicPathGreaterThan(String value) {
            addCriterion("answerdb_detail_pic_path >", value, "answerdbDetailPicPath");
            return (Criteria) this;
        }

        public Criteria andAnswerdbDetailPicPathGreaterThanOrEqualTo(String value) {
            addCriterion("answerdb_detail_pic_path >=", value, "answerdbDetailPicPath");
            return (Criteria) this;
        }

        public Criteria andAnswerdbDetailPicPathLessThan(String value) {
            addCriterion("answerdb_detail_pic_path <", value, "answerdbDetailPicPath");
            return (Criteria) this;
        }

        public Criteria andAnswerdbDetailPicPathLessThanOrEqualTo(String value) {
            addCriterion("answerdb_detail_pic_path <=", value, "answerdbDetailPicPath");
            return (Criteria) this;
        }

        public Criteria andAnswerdbDetailPicPathLike(String value) {
            addCriterion("answerdb_detail_pic_path like", value, "answerdbDetailPicPath");
            return (Criteria) this;
        }

        public Criteria andAnswerdbDetailPicPathNotLike(String value) {
            addCriterion("answerdb_detail_pic_path not like", value, "answerdbDetailPicPath");
            return (Criteria) this;
        }

        public Criteria andAnswerdbDetailPicPathIn(List<String> values) {
            addCriterion("answerdb_detail_pic_path in", values, "answerdbDetailPicPath");
            return (Criteria) this;
        }

        public Criteria andAnswerdbDetailPicPathNotIn(List<String> values) {
            addCriterion("answerdb_detail_pic_path not in", values, "answerdbDetailPicPath");
            return (Criteria) this;
        }

        public Criteria andAnswerdbDetailPicPathBetween(String value1, String value2) {
            addCriterion("answerdb_detail_pic_path between", value1, value2, "answerdbDetailPicPath");
            return (Criteria) this;
        }

        public Criteria andAnswerdbDetailPicPathNotBetween(String value1, String value2) {
            addCriterion("answerdb_detail_pic_path not between", value1, value2, "answerdbDetailPicPath");
            return (Criteria) this;
        }

        public Criteria andAnswerdbDbtypeIsNull() {
            addCriterion("answerdb_dbtype is null");
            return (Criteria) this;
        }

        public Criteria andAnswerdbDbtypeIsNotNull() {
            addCriterion("answerdb_dbtype is not null");
            return (Criteria) this;
        }

        public Criteria andAnswerdbDbtypeEqualTo(Integer value) {
            addCriterion("answerdb_dbtype =", value, "answerdbDbtype");
            return (Criteria) this;
        }

        public Criteria andAnswerdbDbtypeNotEqualTo(Integer value) {
            addCriterion("answerdb_dbtype <>", value, "answerdbDbtype");
            return (Criteria) this;
        }

        public Criteria andAnswerdbDbtypeGreaterThan(Integer value) {
            addCriterion("answerdb_dbtype >", value, "answerdbDbtype");
            return (Criteria) this;
        }

        public Criteria andAnswerdbDbtypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("answerdb_dbtype >=", value, "answerdbDbtype");
            return (Criteria) this;
        }

        public Criteria andAnswerdbDbtypeLessThan(Integer value) {
            addCriterion("answerdb_dbtype <", value, "answerdbDbtype");
            return (Criteria) this;
        }

        public Criteria andAnswerdbDbtypeLessThanOrEqualTo(Integer value) {
            addCriterion("answerdb_dbtype <=", value, "answerdbDbtype");
            return (Criteria) this;
        }

        public Criteria andAnswerdbDbtypeIn(List<Integer> values) {
            addCriterion("answerdb_dbtype in", values, "answerdbDbtype");
            return (Criteria) this;
        }

        public Criteria andAnswerdbDbtypeNotIn(List<Integer> values) {
            addCriterion("answerdb_dbtype not in", values, "answerdbDbtype");
            return (Criteria) this;
        }

        public Criteria andAnswerdbDbtypeBetween(Integer value1, Integer value2) {
            addCriterion("answerdb_dbtype between", value1, value2, "answerdbDbtype");
            return (Criteria) this;
        }

        public Criteria andAnswerdbDbtypeNotBetween(Integer value1, Integer value2) {
            addCriterion("answerdb_dbtype not between", value1, value2, "answerdbDbtype");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andCreaterIsNull() {
            addCriterion("creater is null");
            return (Criteria) this;
        }

        public Criteria andCreaterIsNotNull() {
            addCriterion("creater is not null");
            return (Criteria) this;
        }

        public Criteria andCreaterEqualTo(String value) {
            addCriterion("creater =", value, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterNotEqualTo(String value) {
            addCriterion("creater <>", value, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterGreaterThan(String value) {
            addCriterion("creater >", value, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterGreaterThanOrEqualTo(String value) {
            addCriterion("creater >=", value, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterLessThan(String value) {
            addCriterion("creater <", value, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterLessThanOrEqualTo(String value) {
            addCriterion("creater <=", value, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterLike(String value) {
            addCriterion("creater like", value, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterNotLike(String value) {
            addCriterion("creater not like", value, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterIn(List<String> values) {
            addCriterion("creater in", values, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterNotIn(List<String> values) {
            addCriterion("creater not in", values, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterBetween(String value1, String value2) {
            addCriterion("creater between", value1, value2, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterNotBetween(String value1, String value2) {
            addCriterion("creater not between", value1, value2, "creater");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(String value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(String value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(String value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(String value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(String value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(String value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLike(String value) {
            addCriterion("create_time like", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotLike(String value) {
            addCriterion("create_time not like", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<String> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<String> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(String value1, String value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(String value1, String value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andUpdaterIsNull() {
            addCriterion("updater is null");
            return (Criteria) this;
        }

        public Criteria andUpdaterIsNotNull() {
            addCriterion("updater is not null");
            return (Criteria) this;
        }

        public Criteria andUpdaterEqualTo(String value) {
            addCriterion("updater =", value, "updater");
            return (Criteria) this;
        }

        public Criteria andUpdaterNotEqualTo(String value) {
            addCriterion("updater <>", value, "updater");
            return (Criteria) this;
        }

        public Criteria andUpdaterGreaterThan(String value) {
            addCriterion("updater >", value, "updater");
            return (Criteria) this;
        }

        public Criteria andUpdaterGreaterThanOrEqualTo(String value) {
            addCriterion("updater >=", value, "updater");
            return (Criteria) this;
        }

        public Criteria andUpdaterLessThan(String value) {
            addCriterion("updater <", value, "updater");
            return (Criteria) this;
        }

        public Criteria andUpdaterLessThanOrEqualTo(String value) {
            addCriterion("updater <=", value, "updater");
            return (Criteria) this;
        }

        public Criteria andUpdaterLike(String value) {
            addCriterion("updater like", value, "updater");
            return (Criteria) this;
        }

        public Criteria andUpdaterNotLike(String value) {
            addCriterion("updater not like", value, "updater");
            return (Criteria) this;
        }

        public Criteria andUpdaterIn(List<String> values) {
            addCriterion("updater in", values, "updater");
            return (Criteria) this;
        }

        public Criteria andUpdaterNotIn(List<String> values) {
            addCriterion("updater not in", values, "updater");
            return (Criteria) this;
        }

        public Criteria andUpdaterBetween(String value1, String value2) {
            addCriterion("updater between", value1, value2, "updater");
            return (Criteria) this;
        }

        public Criteria andUpdaterNotBetween(String value1, String value2) {
            addCriterion("updater not between", value1, value2, "updater");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(String value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(String value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(String value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(String value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(String value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(String value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLike(String value) {
            addCriterion("update_time like", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotLike(String value) {
            addCriterion("update_time not like", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<String> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<String> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(String value1, String value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(String value1, String value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}