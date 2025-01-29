package search.intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class MyIntercepter extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("Prehandler is performed..");

		String name = request.getParameter("user");
		

		if (name.startsWith("d")||name.startsWith("D")) {
			response.setContentType("text/html");
			response.getWriter().println("Invalid Input, Name should not starts with D/d..");

			return false;
		}

		System.out.println("name : " + name);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	System.out.println("this is post handler....");
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.println("this is after completion handler....");
		super.afterCompletion(request, response, handler, ex);
	}

	
}
