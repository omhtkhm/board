package com.cos.project1.service;

import java.util.List;

import com.cos.project1.model.Board;
import com.cos.project1.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.project1.model.User;
import com.cos.project1.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private BCryptPasswordEncoder bEncoder; // 암호화
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BoardRepository boardRepository;

	//회원가입
	@Transactional
	public void join(User user) {
		String rawPassword = user.getPassword();
		String bcePassword = bEncoder.encode(rawPassword);
		user.setPassword(bcePassword);
		userRepository.save(user);
	}
	
	//회원수정화면
	@Transactional
	public User detail(int id) {
		User user = userRepository.findById(id).get();
		return user;
	}
	
	//회원수정하기
	@Transactional
	public void update(User user) {
		User u = userRepository.findById(user.getId()).get();
		u.setPassword(user.getPassword());
		u.setEmail(user.getEmail());
		u.setAddress(user.getAddress());
	}
	
	
	//전체보기1
	@Transactional
	public List<User> list(){
		return userRepository.findAll();
	}
	
	//전체보기 페이징
	@Transactional
	public Page<User> list(Pageable pageable){
		return userRepository.findAll(pageable);
	}
	
	//유저수
	@Transactional
	public Long count() {
		return userRepository.count();
	}
	
	//유저검색
	public Page<User> findByIdContaining(String keyword, Pageable pageable){
		return userRepository.findByUsernameContaining (keyword, pageable);
	}
	
	public Page<User> findBySearchCondition(String keyword, String searchgubun, Pageable pageable){
		if (searchgubun != null) {
			return userRepository.findByUsernameContaining (keyword, pageable);
		}
		return userRepository.findByUsernameContaining (keyword, pageable);
	}
	
	@Transactional
	public void delete(int id) {
		// 유저를 삭제하기 전, 게시판 글에 해당 유저id를 null로 수정해야함.
		User user = new User();
		user.setId(id);
		List<Board> boards = boardRepository.findByUser(user);
		for(Board board : boards){
			board.setUser(null);
			boardRepository.save(board);
		}
		userRepository.deleteById(id);
	}
	
}
