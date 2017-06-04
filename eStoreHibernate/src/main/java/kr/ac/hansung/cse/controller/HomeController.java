package kr.ac.hansung.cse.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(HttpServletRequest request) {
		Logger logger = LoggerFactory.getLogger(HomeController.class);
		// Logger logger = LoggerFactory.getLogger("kr.ac.hansung.cse.controller.HomeController");
		
		String url = request.getRequestURL().toString();
		String clientIPAddress = request.getRemoteAddr();
		
		logger.info("request url : "+url);
		logger.info("client ip : "+clientIPAddress);

		return "home";
	}
	
}
