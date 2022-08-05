package com.cos.project1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.project1.config.auth.PrincipalDetails;
import com.cos.project1.model.Board;
import com.cos.project1.model.Comment;
import com.cos.project1.service.CommentService;

@RequestMapping("/reply/*")
@RestController
public class CommentController {
	@Autowired
	private CommentService commentService;

	// 댓글추가
	@PostMapping("insert/{num}")
	public ResponseEntity<String> commentInsert(@PathVariable int num, @RequestBody Comment comment,
			@AuthenticationPrincipal PrincipalDetails principal) {
		Board board = new Board();
		board.setNum(num);
		comment.setBoard(board);
		System.out.println("principal.getUser() : " + principal.getUser());
		comment.setUser(principal.getUser());
		commentService.insert(comment);
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}

	@GetMapping("list/{num}")
	public List<Comment> list(@PathVariable Long num) {
		List<Comment> clist = commentService.list(num);
//		for (int i=0; i<clist.size(); i++) {
//			System.out.println("******************"+clist.get(i).toString());
//		}
		return clist;
	}
}
