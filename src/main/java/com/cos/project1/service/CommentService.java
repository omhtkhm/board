package com.cos.project1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.project1.model.Board;
import com.cos.project1.model.Comment;
import com.cos.project1.repository.BoardRepository;
import com.cos.project1.repository.CommentRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {

	private final CommentRepository commentRepository;
	private final BoardRepository boardRepository;

	// 댓글쓰기
	@Transactional
	public void insert(Comment comment) {
		// Board commentCount 1증가하기
		Optional<Board> board = boardRepository.findById(comment.getBoard().getNum());
		Long commentCount = board.get().getCommentcount();
		if (commentCount == null) {
			commentCount = (long) 1;
		}
		board.get().setCommentcount(commentCount + 1);
		commentRepository.save(comment);
	}

	// 댓글리스트
	@Transactional
	public List<Comment> list(Long bnum) {
		return commentRepository.findByBnum(bnum);
	}
}