package org.nbrc.mobile.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AuthenticationInterceptor extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String path = request.getRequestURI();
		String cxt = request.getContextPath();
		HttpSession session = request.getSession();  
		path = path.replaceFirst(cxt, "");
		//System.out.println(path);
		if(!("/login".equals(path)|| "/reg".equals(path) || "/index".equals(path))){
			
			if(session.getAttribute(WebConstants.SESSION_KEY)==null){			
				String requestedWith = request.getHeader("x-requested-with");
				// ajax请求
				if (requestedWith != null && "XMLHttpRequest".equals(requestedWith)) {
					response.setHeader("session-status", "timeout");
					response.getWriter().print(WebConstants.TIME_OUT);
				} else {
					// 普通页面请求
					response.sendRedirect(cxt  + "/login");
				}
			}
		}
		return super.preHandle(request, response, handler);
	}
	
}
