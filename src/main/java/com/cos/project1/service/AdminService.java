package com.cos.project1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.project1.repository.BoardRepository;
import com.cos.project1.repository.UserRepository;

@Service
public class AdminService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BoardRepository boardRepository;
	
	@Transactional
	public void delete(int id) {
		userRepository.deleteById(id);
	}
	
	@Transactional
	public void deleteB(int num) {
		boardRepository.deleteByNum(num);
	}
}
