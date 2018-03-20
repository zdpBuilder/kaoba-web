package itf4.kaoba.inteceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import itf4.kaoba.model.SysUser;
import itf4.kaoba.util.Const;

/**
 * 登陆拦截
 * 
 * @author zdp
 *
 */
public class LoginInteceptor implements HandlerInterceptor {

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String path = request.getServletPath();
		if(path.matches(Const.NO_INTERCEPTOR_PATH)){
			return true;
		}else{
			HttpSession session = request.getSession();
			SysUser users = (SysUser)session.getAttribute(Const.SESSION_USER);
			if(users!=null){
				return true;
			}else{
				response.sendRedirect(request.getContextPath()+"/login.jsp");
				return false;
			}
		}
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	}	   
