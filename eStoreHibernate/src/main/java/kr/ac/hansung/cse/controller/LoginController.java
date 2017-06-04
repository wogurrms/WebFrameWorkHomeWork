package kr.ac.hansung.cse.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

	@RequestMapping("/login")
	public String login(@RequestParam(value="error", required=false) String error,
			@RequestParam(value="logout", required=false) String logout, Model model) {
		if(error != null) {
			model.addAttribute("error", "Invalid username and password");
		}
		if(logout != null) {
			model.addAttribute("logout", "You have been logged out successfully");
		}
		
		return "login"; // view의 logical name은 tiles.xml에 등록해야한다.
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
		
		/* spring에 의해 logout 처리 */
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null){    
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
	    /* 로그인 success page 
	     * You can redirect wherever you want, but generally it's a good practice to show login screen again. */
	    return "redirect:/login?logout";
	}
}
