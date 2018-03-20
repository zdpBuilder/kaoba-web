package itf4.kaoba.util;

import org.springframework.context.ApplicationContext;

public class Const {
		//public static final String SESSION_SECURITY_CODE = "sessionSecCode";
		public static final String SESSION_USER = "CurrentLoginUserInfo";
		//public static final String SESSION_USER_RIGHTS = "sessionUserRights";
		//public static final String SESSION_ROLE_RIGHTS = "sessionRoleRights";
		public static final String NO_INTERCEPTOR_PATH = ".*/((login)|(logout)|(code)).*";	
		public static ApplicationContext WEB_APP_CONTEXT = null; 
	}



