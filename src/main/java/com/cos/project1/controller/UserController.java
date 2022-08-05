package com.cos.project1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.project1.model.User;
import com.cos.project1.repository.UserRepository;
import com.cos.project1.service.UserService;

@Controller // view(.jsp)를 리턴한다 
public class UserController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	
//	@GetMapping("/admin")
//	@PreAuthorize("hasRole('ROLE_ADMIN')")
//	public String admin() {
//		return "admin/main";
//	}
	
	@GetMapping("/")
	public String home() {
		return "home";
	}
	
//	@GetMapping("/user")
//	public String user() {
//		return "user";
//	}
//	
	
	@GetMapping("/loginForm") //로그인화면
	public String loginForm() {
		return "/user/loginForm";
	}
	
	@GetMapping("/joinForm") //회원가입 화면
	public String joinForm() {
		return "/user/joinForm";
	}
	
	
	//아이디 중복체크
	@GetMapping("joinIdDupChk")
	@ResponseBody
	public String joinIdDupChk( String id) {
		User user = userRepository.findByUsername(id);
		if(user == null) {
			return "1";
		} else {
			return "0";
		}
	}
	
	
	//주소검색
	@GetMapping("/jusoPopup")
	public String jusoPopup() {
		return "/user/jusoPopup";
	}

	@PostMapping("/jusoPopup")
	public String jusoPopup2() {
		return "/user/jusoPopup";
	}
	
	@PostMapping("/join") //회원가입
	@ResponseBody
	public String join(@RequestBody User user) {
		System.out.println(user);
		user.setRole("ROLE_USER"); //권한은 디비에서 바꾸는게 좋으므로 미리 롤을 부여하자!
		if(userRepository.findByUsername(user.getUsername()) != null)
			return "fail";
		userService.join(user);
		return "success";
	}
	
	//회원수정화면
	@GetMapping("update/{id}")
	public String update(Model model, @PathVariable int id) {
		model.addAttribute("user", userService.detail(id));
		return "/user/update";
	}
	
	//회원수정하기
	@PutMapping("update/{id}")
	@ResponseBody
	public String update(@RequestBody User user, String password) {
		String rawPassword = user.getPassword();
		String encPassword = encoder.encode(rawPassword);
		user.setPassword(encPassword);
		userService.update(user);
		return "success";
	}
	
	
	
	
//	//@PreAuthorize("hasRole('ROLE_USER')) -> 접근 금지 페이지에 잠시 권한 주고 들어가도록 만듬
//	@Secured("ROLE_ADMIN") // 어드민만 들어갈수있도록 페이지 설정 가능 
//	@GetMapping("/info")
//	@ResponseBody
//	public String info() {
//		return "User Info";
//	}
}
