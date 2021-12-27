package fourinarow.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import fourinarow.model.User;
import fourinarow.services.AuthenticationUtils;
import fourinarow.services.AuthenticationUtils.InvalidTokenException;
import fourinarow.services.AuthenticationUtils.MissingTokenException;

//Based on : https://devstory.net/11229/spring-mvc-interceptor
public class AuthInterceptor extends HandlerInterceptorAdapter{

	@Autowired
	private AuthenticationUtils authenticationUtils;

	
	/** Before the controller
	 *  @return 
	 *  	true : let it pass
	 *  	false : stop the request (don't forget to send error)
	 */
 	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        User user;
		try {
			user = authenticationUtils.getUserFomServletRequest(request);
			/* Identification log */
			System.out.println("[Auth-log] User "+user.getUsername()+" identified !");
			/* Put the user in the request, to get it in the API : 
			 * add HttpServletRequest request to parameters
			 * get user with request.getAttribute("user") */
	        request.setAttribute("user", user);
		} catch (MissingTokenException e) {
			//Transform in json response
			response.sendError(401, "Missing token");
	        return false;
		} catch (InvalidTokenException e) {
			response.sendError(401, "Invalid token");
	        return false;
		}
		return true;
    }

 	/* After the controller */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
    }
    
    /* After completion */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }
}
