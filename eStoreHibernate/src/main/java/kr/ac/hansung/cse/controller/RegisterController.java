package kr.ac.hansung.cse.controller;


import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.ac.hansung.cse.model.Cart;
import kr.ac.hansung.cse.model.ShippingAddress;
import kr.ac.hansung.cse.model.User;
import kr.ac.hansung.cse.service.UserService;

@Controller
public class RegisterController {

	@Autowired
	UserService userService;
	
	@RequestMapping("/register")
	public String registerUser(Model model) {
		
		User user = new User();
		ShippingAddress shippingAddress = new ShippingAddress();
		
		user.setShippingAddress(shippingAddress);
		
		user.setUsername("hello");
		model.addAttribute("user", user);
		
		return "registerUser";
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public String registerUserPost(@Valid User user, BindingResult result, Model model) {
		// 사용자가 입력한 내용이 User 객체로 data-binding됨. // @Valid로 User클래스에 검증 애너테이션에따라 검증해 결과도 data-binding에 포함.
		
		if(result.hasErrors()) {
			return "registerUSer";
		}
		
		List<User> userList = userService.getAllUsers();
		for(int i=0; i<userList.size(); i++) {
			if(user.getUsername().equals(userList.get(i).getUsername())) {
				model.addAttribute("usernameMsg", "Username already exists");
				// 모델에는 user객체, result객체, usernameMsg객체가 모두 존재한다.
				return "registerUser";
			}
		}
		
		user.setEnabled(true);
		
		if(user.getUsername().equals("admin")) // 접근권한 저장
			user.setAuthority("ROLE_ADMIN");
		else
			user.setAuthority("ROLE_USER");
		
		Cart newCart = new Cart();
		user.setCart(newCart);
		
		userService.addUser(user); // 사용자 계정 저장
		
		return "registerUserSuccess";
	}
}
