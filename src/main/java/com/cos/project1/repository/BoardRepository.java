package com.cos.project1.repository;

import java.util.List;

import com.cos.project1.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.project1.model.Board;

public interface BoardRepository extends JpaRepository<Board, Integer> {
	public void deleteByNum(int num);

	public Page<Board> findByTitleContaining(String keyword, Pageable pageable);
	
	public Page<Board> findByContentContaining(String keyword, Pageable pageable);
	
	public Page<Board> findByTitleContainingOrWriterContainingOrContentContaining(String keyword, String keyword1, String keyword2, Pageable pageable);
	
	//제목 + 작성자 + 내용
	
	
	public Page<Board> findByWriterContaining(String keyword,  Pageable pageable);
	public Page<Board> findByTitleContainingOrWriterContaining(String keyword, String keyword1, Pageable pageable);

	public List<Board> findByUser(User user);
	
}
