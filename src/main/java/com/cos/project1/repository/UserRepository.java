package com.cos.project1.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.project1.model.User;

//기본적인 CRUD 함수를 JpaRepository가 들고있다!!
//@Repository가 없이 IoC가 되는 이유는 JpaRepository를 상속 했기 때문
public interface UserRepository extends JpaRepository<User, Integer>{
	
	//findBy -> 규칙, Username -> 문법
	//이것을 통해 'select * from user where username =?" 이 쿼리가 작동
	// =? 에 이 name=username이 들어감 
	User findByUsername(String username);
	
	
	//회원삭제
	public void deleteById(int id);
	
	//회원조회
	public Page<User> findByUsernameContaining(String keyword, Pageable pageable);
	
}
