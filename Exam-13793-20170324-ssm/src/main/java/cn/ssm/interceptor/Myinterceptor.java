package cn.ssm.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class Myinterceptor implements HandlerInterceptor {

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		String contexpath=request.getContextPath();
		String url =request.getRequestURL().toString();
	    if(url.indexOf("login")>=0){
			return true;
		}
		
		HttpSession session=request.getSession();
		String username=(String) session.getAttribute("username");
		
		if(username!=null){
			return true;
		}
		request.getRequestDispatcher("/login.jsp").forward(request, response);
		//response.sendRedirect(contexpath+"/login.jsp");
		return false;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
		
	}

}
