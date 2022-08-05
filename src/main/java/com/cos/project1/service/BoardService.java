package com.cos.project1.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cos.project1.model.Board;
import com.cos.project1.model.Image;
import com.cos.project1.model.Mylike;
import com.cos.project1.model.User;
import com.cos.project1.repository.BoardRepository;
import com.cos.project1.repository.MylikeRepository;
import com.cos.project1.util.FileHandler;

@Transactional(readOnly = true)
@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;
	@Autowired
	private MylikeRepository mylikeRepository;

	// 글쓰기화면
	@Transactional
	public void insert(Board board, User user, List<MultipartFile> files, HttpServletRequest req) {
		board.setUser(user);
		List<Image> imgs = FileHandler.generateImageList(files, req); // 파일클래스를 따로 만들어서 처리
		for (int i = 0; i < imgs.size(); i++) {
			Image image = new Image();
			image.setFileName(imgs.get(i).getFileName());
			image.setOriginalFileName(imgs.get(i).getOriginalFileName());
//			board.addImg(image);
			image.setBoard(board);
			board.getImgs().add(image);
		}
		board = boardRepository.save(board);
	}

	// 게시판 검색
//	@Transactional
	public Page<Board> findByTitleContaining(String keyword, Pageable pageable) {
//		return boardRepository.findByTitleContaining(keyword, pageable);
//		return boardRepository.findByTitleContainingOrWriterContaining(keyword, keyword, pageable);
		return boardRepository.findByTitleContainingOrWriterContainingOrContentContaining(keyword, keyword, keyword,
				pageable);
	}

	public Page<Board> findBySearchCondition(String keyword, String searchgubun, Pageable pageable) {
		switch (searchgubun) {
		case "title":
			return boardRepository.findByTitleContaining(keyword, pageable);
		case "content":
			return boardRepository.findByContentContaining(keyword, pageable);
		case "writer":
			return boardRepository.findByWriterContaining(keyword, pageable);
		case "titleplustwriter":
			return boardRepository.findByTitleContainingOrWriterContaining(keyword, keyword, pageable);
		}
		return boardRepository.findByTitleContainingOrWriterContainingOrContentContaining(keyword, keyword, keyword,
				pageable);
	}

	// 전체보기1
//	@Transactional
	public List<Board> findAll() {
		return boardRepository.findAll();
	}

	// 전체보기+페이징
	@Transactional
	public Page<Board> findAll(Pageable pageable) {
		return boardRepository.findAll(pageable);
	}

	// 게시물 개수
	@Transactional
	public Long count() {
		return boardRepository.count();
	}

	// 상세보기
	@Transactional
	public Board findById(int num) {
		Board board = boardRepository.findById(num).get();
		board.setHitcount(board.getHitcount() + 1);
		return board;
	}

	// 수정화면
	@Transactional
	public Board detail(int num) {
		Board board = boardRepository.findById(num).get();
		return board;
	}

	// 수정하기
	@Transactional
	public void update(Board board) {
		Board b = boardRepository.findById(board.getNum()).get();
		b.setTitle(board.getTitle());
		b.setContent(board.getContent());
		b.setPrice(board.getPrice());
//		boardRepository.save(b);
	}

	// 삭제
	@Transactional
	public void delete(int num) {
		boardRepository.deleteByNum(num);
	}
	
	//게시물 좋아요
	@Transactional
	public long like(int num, User user) {
		Mylike mylike = new Mylike();
		mylike.setUserid(user.getId());
		Board board = boardRepository.findById(num).get();
		mylike.setBoard(board);
		board.getLikes().add(mylike);
//		mylikeRepository.save(mylike);
		board.setReplycount(board.getReplycount()+1);
		boardRepository.save(board);
		return board.getReplycount();
	}
}
