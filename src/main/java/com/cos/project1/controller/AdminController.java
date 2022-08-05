package com.cos.project1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.project1.model.Board;
import com.cos.project1.model.User;
import com.cos.project1.service.BoardService;
import com.cos.project1.service.UserService;

@Controller
public class AdminController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private BoardService boardService;
	
	//어드민 로그인
	@GetMapping("/admin")
	public String admin() {
		return "home";
	}
	
	//유저리스트 화면
	@GetMapping("/admin/userlist")
	public String userlist(Model model,
			@PageableDefault(size = 3, sort = "id", direction = Sort.Direction.DESC)
	Pageable pageable)  {
		Page<User> lists = userService.list(pageable);
		model.addAttribute("lists", lists);
		model.addAttribute("count", userService.count()); // 6
		model.addAttribute("rowNo", userService.count() - (lists.getNumber() * 5)); // p2 ( 6-5
		return "/admin/userlist";
	}
	
	//유저삭제
	@DeleteMapping("/admin/delete/{id}")
	@ResponseBody
	public String delete(@PathVariable int id) {
		System.out.println("!!!!!!!!!!!!!!!!!!" + id);
		userService.delete(id);
		return "success";
	}
	
	//유저검색
	@GetMapping("admin/search")
	public String search(Model model, @RequestParam(value = "keyword") String keyword,
			@RequestParam(value = "searchgubun") String searchgubun,
			@PageableDefault(size = 3, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
		System.out.println(keyword);
		Page<User> lists = userService.findBySearchCondition(keyword, searchgubun, pageable);
		model.addAttribute("lists", lists);
		model.addAttribute("count", userService.count()); // 6
		model.addAttribute("rowNo", userService.count() - (lists.getNumber() * 5)); // p2 ( 6-5
		return "/admin/userlist";
	}
	
//	//게시판리스트 화면
//	@GetMapping("/admin/boardlist")
//	public String list(Model model,
//			@PageableDefault(size = 10, sort = "hitcount", direction = Sort.Direction.DESC) Pageable pageable) {
//		Page<Board> lists = boardService.findAll(pageable);
//		model.addAttribute("lists", lists);
//		model.addAttribute("count", boardService.count()); // 6
//		model.addAttribute("rowNo", boardService.count() - (lists.getNumber() * 5)); // p2 ( 6-5
//		return "/admin/boardlist";
//	}
//	
//	//게시물 삭제
//	@DeleteMapping("/admin/delete/{num}")
//	@ResponseBody
//	public String deleteB(@PathVariable int num) {
//		boardService.delete(num);
//		return "success";
//	}
	

}
