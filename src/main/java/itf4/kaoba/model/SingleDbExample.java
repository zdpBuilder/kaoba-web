package itf4.kaoba.model;

import java.util.ArrayList;
import java.util.List;

public class SingleDbExample extends BaseExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SingleDbExample()  {
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

        public Criteria andCourseIdIsNull() {
            addCriterion("course_id is null");
            return (Criteria) this;
        }

        public Criteria andCourseIdIsNotNull() {
            addCriterion("course_id is not null");
            return (Criteria) this;
        }

        public Criteria andCourseIdEqualTo(Integer value) {
            addCriterion("course_id =", value, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdNotEqualTo(Integer value) {
            addCriterion("course_id <>", value, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdGreaterThan(Integer value) {
            addCriterion("course_id >", value, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("course_id >=", value, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdLessThan(Integer value) {
            addCriterion("course_id <", value, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdLessThanOrEqualTo(Integer value) {
            addCriterion("course_id <=", value, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdIn(List<Integer> values) {
            addCriterion("course_id in", values, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdNotIn(List<Integer> values) {
            addCriterion("course_id not in", values, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdBetween(Integer value1, Integer value2) {
            addCriterion("course_id between", value1, value2, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdNotBetween(Integer value1, Integer value2) {
            addCriterion("course_id not between", value1, value2, "courseId");
            return (Criteria) this;
        }

        public Criteria andSingledbTitleIsNull() {
            addCriterion("singledb_title is null");
            return (Criteria) this;
        }

        public Criteria andSingledbTitleIsNotNull() {
            addCriterion("singledb_title is not null");
            return (Criteria) this;
        }

        public Criteria andSingledbTitleEqualTo(String value) {
            addCriterion("singledb_title =", value, "singledbTitle");
            return (Criteria) this;
        }

        public Criteria andSingledbTitleNotEqualTo(String value) {
            addCriterion("singledb_title <>", value, "singledbTitle");
            return (Criteria) this;
        }

        public Criteria andSingledbTitleGreaterThan(String value) {
            addCriterion("singledb_title >", value, "singledbTitle");
            return (Criteria) this;
        }

        public Criteria andSingledbTitleGreaterThanOrEqualTo(String value) {
            addCriterion("singledb_title >=", value, "singledbTitle");
            return (Criteria) this;
        }

        public Criteria andSingledbTitleLessThan(String value) {
            addCriterion("singledb_title <", value, "singledbTitle");
            return (Criteria) this;
        }

        public Criteria andSingledbTitleLessThanOrEqualTo(String value) {
            addCriterion("singledb_title <=", value, "singledbTitle");
            return (Criteria) this;
        }

        public Criteria andSingledbTitleLike(String value) {
            addCriterion("singledb_title like", value, "singledbTitle");
            return (Criteria) this;
        }

        public Criteria andSingledbTitleNotLike(String value) {
            addCriterion("singledb_title not like", value, "singledbTitle");
            return (Criteria) this;
        }

        public Criteria andSingledbTitleIn(List<String> values) {
            addCriterion("singledb_title in", values, "singledbTitle");
            return (Criteria) this;
        }

        public Criteria andSingledbTitleNotIn(List<String> values) {
            addCriterion("singledb_title not in", values, "singledbTitle");
            return (Criteria) this;
        }

        public Criteria andSingledbTitleBetween(String value1, String value2) {
            addCriterion("singledb_title between", value1, value2, "singledbTitle");
            return (Criteria) this;
        }

        public Criteria andSingledbTitleNotBetween(String value1, String value2) {
            addCriterion("singledb_title not between", value1, value2, "singledbTitle");
            return (Criteria) this;
        }

        public Criteria andSingledbOptionaIsNull() {
            addCriterion("singledb_optionA is null");
            return (Criteria) this;
        }

        public Criteria andSingledbOptionaIsNotNull() {
            addCriterion("singledb_optionA is not null");
            return (Criteria) this;
        }

        public Criteria andSingledbOptionaEqualTo(String value) {
            addCriterion("singledb_optionA =", value, "singledbOptiona");
            return (Criteria) this;
        }

        public Criteria andSingledbOptionaNotEqualTo(String value) {
            addCriterion("singledb_optionA <>", value, "singledbOptiona");
            return (Criteria) this;
        }

        public Criteria andSingledbOptionaGreaterThan(String value) {
            addCriterion("singledb_optionA >", value, "singledbOptiona");
            return (Criteria) this;
        }

        public Criteria andSingledbOptionaGreaterThanOrEqualTo(String value) {
            addCriterion("singledb_optionA >=", value, "singledbOptiona");
            return (Criteria) this;
        }

        public Criteria andSingledbOptionaLessThan(String value) {
            addCriterion("singledb_optionA <", value, "singledbOptiona");
            return (Criteria) this;
        }

        public Criteria andSingledbOptionaLessThanOrEqualTo(String value) {
            addCriterion("singledb_optionA <=", value, "singledbOptiona");
            return (Criteria) this;
        }

        public Criteria andSingledbOptionaLike(String value) {
            addCriterion("singledb_optionA like", value, "singledbOptiona");
            return (Criteria) this;
        }

        public Criteria andSingledbOptionaNotLike(String value) {
            addCriterion("singledb_optionA not like", value, "singledbOptiona");
            return (Criteria) this;
        }

        public Criteria andSingledbOptionaIn(List<String> values) {
            addCriterion("singledb_optionA in", values, "singledbOptiona");
            return (Criteria) this;
        }

        public Criteria andSingledbOptionaNotIn(List<String> values) {
            addCriterion("singledb_optionA not in", values, "singledbOptiona");
            return (Criteria) this;
        }

        public Criteria andSingledbOptionaBetween(String value1, String value2) {
            addCriterion("singledb_optionA between", value1, value2, "singledbOptiona");
            return (Criteria) this;
        }

        public Criteria andSingledbOptionaNotBetween(String value1, String value2) {
            addCriterion("singledb_optionA not between", value1, value2, "singledbOptiona");
            return (Criteria) this;
        }

        public Criteria andSingledbOptionbIsNull() {
            addCriterion("singledb_optionB is null");
            return (Criteria) this;
        }

        public Criteria andSingledbOptionbIsNotNull() {
            addCriterion("singledb_optionB is not null");
            return (Criteria) this;
        }

        public Criteria andSingledbOptionbEqualTo(String value) {
            addCriterion("singledb_optionB =", value, "singledbOptionb");
            return (Criteria) this;
        }

        public Criteria andSingledbOptionbNotEqualTo(String value) {
            addCriterion("singledb_optionB <>", value, "singledbOptionb");
            return (Criteria) this;
        }

        public Criteria andSingledbOptionbGreaterThan(String value) {
            addCriterion("singledb_optionB >", value, "singledbOptionb");
            return (Criteria) this;
        }

        public Criteria andSingledbOptionbGreaterThanOrEqualTo(String value) {
            addCriterion("singledb_optionB >=", value, "singledbOptionb");
            return (Criteria) this;
        }

        public Criteria andSingledbOptionbLessThan(String value) {
            addCriterion("singledb_optionB <", value, "singledbOptionb");
            return (Criteria) this;
        }

        public Criteria andSingledbOptionbLessThanOrEqualTo(String value) {
            addCriterion("singledb_optionB <=", value, "singledbOptionb");
            return (Criteria) this;
        }

        public Criteria andSingledbOptionbLike(String value) {
            addCriterion("singledb_optionB like", value, "singledbOptionb");
            return (Criteria) this;
        }

        public Criteria andSingledbOptionbNotLike(String value) {
            addCriterion("singledb_optionB not like", value, "singledbOptionb");
            return (Criteria) this;
        }

        public Criteria andSingledbOptionbIn(List<String> values) {
            addCriterion("singledb_optionB in", values, "singledbOptionb");
            return (Criteria) this;
        }

        public Criteria andSingledbOptionbNotIn(List<String> values) {
            addCriterion("singledb_optionB not in", values, "singledbOptionb");
            return (Criteria) this;
        }

        public Criteria andSingledbOptionbBetween(String value1, String value2) {
            addCriterion("singledb_optionB between", value1, value2, "singledbOptionb");
            return (Criteria) this;
        }

        public Criteria andSingledbOptionbNotBetween(String value1, String value2) {
            addCriterion("singledb_optionB not between", value1, value2, "singledbOptionb");
            return (Criteria) this;
        }

        public Criteria andSingledbOptioncIsNull() {
            addCriterion("singledb_optionC is null");
            return (Criteria) this;
        }

        public Criteria andSingledbOptioncIsNotNull() {
            addCriterion("singledb_optionC is not null");
            return (Criteria) this;
        }

        public Criteria andSingledbOptioncEqualTo(String value) {
            addCriterion("singledb_optionC =", value, "singledbOptionc");
            return (Criteria) this;
        }

        public Criteria andSingledbOptioncNotEqualTo(String value) {
            addCriterion("singledb_optionC <>", value, "singledbOptionc");
            return (Criteria) this;
        }

        public Criteria andSingledbOptioncGreaterThan(String value) {
            addCriterion("singledb_optionC >", value, "singledbOptionc");
            return (Criteria) this;
        }

        public Criteria andSingledbOptioncGreaterThanOrEqualTo(String value) {
            addCriterion("singledb_optionC >=", value, "singledbOptionc");
            return (Criteria) this;
        }

        public Criteria andSingledbOptioncLessThan(String value) {
            addCriterion("singledb_optionC <", value, "singledbOptionc");
            return (Criteria) this;
        }

        public Criteria andSingledbOptioncLessThanOrEqualTo(String value) {
            addCriterion("singledb_optionC <=", value, "singledbOptionc");
            return (Criteria) this;
        }

        public Criteria andSingledbOptioncLike(String value) {
            addCriterion("singledb_optionC like", value, "singledbOptionc");
            return (Criteria) this;
        }

        public Criteria andSingledbOptioncNotLike(String value) {
            addCriterion("singledb_optionC not like", value, "singledbOptionc");
            return (Criteria) this;
        }

        public Criteria andSingledbOptioncIn(List<String> values) {
            addCriterion("singledb_optionC in", values, "singledbOptionc");
            return (Criteria) this;
        }

        public Criteria andSingledbOptioncNotIn(List<String> values) {
            addCriterion("singledb_optionC not in", values, "singledbOptionc");
            return (Criteria) this;
        }

        public Criteria andSingledbOptioncBetween(String value1, String value2) {
            addCriterion("singledb_optionC between", value1, value2, "singledbOptionc");
            return (Criteria) this;
        }

        public Criteria andSingledbOptioncNotBetween(String value1, String value2) {
            addCriterion("singledb_optionC not between", value1, value2, "singledbOptionc");
            return (Criteria) this;
        }

        public Criteria andSingledbOptiondIsNull() {
            addCriterion("singledb_optionD is null");
            return (Criteria) this;
        }

        public Criteria andSingledbOptiondIsNotNull() {
            addCriterion("singledb_optionD is not null");
            return (Criteria) this;
        }

        public Criteria andSingledbOptiondEqualTo(String value) {
            addCriterion("singledb_optionD =", value, "singledbOptiond");
            return (Criteria) this;
        }

        public Criteria andSingledbOptiondNotEqualTo(String value) {
            addCriterion("singledb_optionD <>", value, "singledbOptiond");
            return (Criteria) this;
        }

        public Criteria andSingledbOptiondGreaterThan(String value) {
            addCriterion("singledb_optionD >", value, "singledbOptiond");
            return (Criteria) this;
        }

        public Criteria andSingledbOptiondGreaterThanOrEqualTo(String value) {
            addCriterion("singledb_optionD >=", value, "singledbOptiond");
            return (Criteria) this;
        }

        public Criteria andSingledbOptiondLessThan(String value) {
            addCriterion("singledb_optionD <", value, "singledbOptiond");
            return (Criteria) this;
        }

        public Criteria andSingledbOptiondLessThanOrEqualTo(String value) {
            addCriterion("singledb_optionD <=", value, "singledbOptiond");
            return (Criteria) this;
        }

        public Criteria andSingledbOptiondLike(String value) {
            addCriterion("singledb_optionD like", value, "singledbOptiond");
            return (Criteria) this;
        }

        public Criteria andSingledbOptiondNotLike(String value) {
            addCriterion("singledb_optionD not like", value, "singledbOptiond");
            return (Criteria) this;
        }

        public Criteria andSingledbOptiondIn(List<String> values) {
            addCriterion("singledb_optionD in", values, "singledbOptiond");
            return (Criteria) this;
        }

        public Criteria andSingledbOptiondNotIn(List<String> values) {
            addCriterion("singledb_optionD not in", values, "singledbOptiond");
            return (Criteria) this;
        }

        public Criteria andSingledbOptiondBetween(String value1, String value2) {
            addCriterion("singledb_optionD between", value1, value2, "singledbOptiond");
            return (Criteria) this;
        }

        public Criteria andSingledbOptiondNotBetween(String value1, String value2) {
            addCriterion("singledb_optionD not between", value1, value2, "singledbOptiond");
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