package com.cos.project1.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cos.project1.config.auth.PrincipalDetails;
import com.cos.project1.dto.ResultDto;
import com.cos.project1.model.Board;
import com.cos.project1.model.Image;
import com.cos.project1.model.Mylike;
import com.cos.project1.service.BoardService;

@Controller
@RequestMapping("/board/*")
public class BoardController {

	@Autowired
	private BoardService boardService;

	// 글쓰기화면
	@GetMapping("insert")
	@PreAuthorize("isAuthenticated()")
	public String insert() {
		return "/board/insert";
	}

	// 글쓰기
	@PostMapping("insert")
	public String insert(@RequestParam("img") List<MultipartFile> files, Board board,
			@AuthenticationPrincipal PrincipalDetails principal, HttpServletRequest req) {
		System.out.println(board.toString());
		boardService.insert(board, principal.getUser(), files, req);
		return "redirect:/board/list";
	}

	// 게시판 메인 화면
	@GetMapping("list")
	public String list(Model model,
			@PageableDefault(size = 10, sort = "hitcount", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<Board> lists = boardService.findAll(pageable);
		model.addAttribute("lists", lists);
		model.addAttribute("count", boardService.count()); // 6
		model.addAttribute("rowNo", boardService.count() - (lists.getNumber() * 5)); // p2 ( 6-5
		return "/board/list";
	}

	// 게시물 상세보기
	@GetMapping("detail/{num}")
	public String detail(@PathVariable int num, Model model, @AuthenticationPrincipal PrincipalDetails principal) {
		Board board = boardService.findById(num);
		boolean isExit = false;
		if( principal != null) {
			int userid = principal.getUser().getId();
			List<Mylike> mylikes = board.getLikes();
			
			for (int i = 0; i < mylikes.size(); i++) {
				if (userid == mylikes.get(i).getUserid()) {
					isExit = true;
					break;
				}
//			System.out.println("**************"+ mylikes.get(i).getUserid());
			}
		} else {
			// 로그인이 없을시, 좋아요 버튼을 없애기 위한 조치
			isExit = true;
		}
		model.addAttribute("board", board);
		if(isExit) model.addAttribute("isExit", "true");
		else model.addAttribute("isExit", "false");
		return "/board/detail";
	}

	// 게시물 수정 화면
	@GetMapping("update/{num}")
	public String update(@PathVariable int num, Model model) {
		model.addAttribute("board", boardService.detail(num));
		return "/board/update";
	}

	// 게시물 수정
	@PutMapping("update/{num}")
	@ResponseBody
	public String update(@RequestBody Board board) {
		System.out.println(board);
		boardService.update(board);
		return "success";
	}

	// 게시물 삭제
	@DeleteMapping("delete/{num}")
	@ResponseBody
	public String delete(@PathVariable int num) {
		boardService.delete(num);
		return "success";
	}

	// 게시판 검색
	@GetMapping("board/search")
	public String search(Model model, @RequestParam(value = "keyword") String keyword,
			@RequestParam(value = "searchgubun") String searchgubun,
			@PageableDefault(size = 20, sort = "hitcount", direction = Sort.Direction.DESC) Pageable pageable) {
		System.out.println(keyword);
		Page<Board> lists = boardService.findBySearchCondition(keyword, searchgubun, pageable);
		model.addAttribute("lists", lists);
		model.addAttribute("count", boardService.count()); // 6
		model.addAttribute("rowNo", boardService.count() - (lists.getNumber() * 5)); // p2 ( 6-5
		return "/board/list";
	}
	
	@PostMapping("like/{num}")
	@ResponseBody
	public ResultDto like(@PathVariable int num, @AuthenticationPrincipal PrincipalDetails principal) {
		long replycount = boardService.like(num, principal.getUser());
		ResultDto resultDto = new ResultDto();
		resultDto.setResult("success");
		resultDto.setValue(Long.toString(replycount));
		return resultDto;
	}
}
