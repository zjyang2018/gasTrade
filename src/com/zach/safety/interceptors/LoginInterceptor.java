package com.zach.safety.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LoginInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {

		HttpSession session = request.getSession();
		String sessionId = session.getId();
		Object users = session.getServletContext().getAttribute("users");
		// Map<String, UserVo> userMap = null;
		// boolean isNotLogin = true;
		// if (users != null) {
		// userMap = (Map<String, UserVo>) users;
		// UserVo user = userMap.get(sessionId);
		// if (user != null) {
		// isNotLogin = false;
		// }
		// }
		// if (isNotLogin) {
		// request.getRequestDispatcher("/views/login.jsp").forward(request,
		// response);
		// return false;
		// }
		return true;
	}

}
