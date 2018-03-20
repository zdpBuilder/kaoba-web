package itf4.kaoba.common;

import java.util.List;

import lombok.Data;

//前台页面接收的分页json列表 
@Data
public class ResponseJsonPageListBean {

	private int code;
	private String msg;
	private int count;
	private List<?> data;

}
