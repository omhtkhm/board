package com.cos.project1.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.project1.model.User;
import com.cos.project1.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;

// 시큐리티 설정에서 loginProcessingUrl("/login"); 세팅이 되어있다
// /login 요청이 오면 자동으로 UserDetailsService 타입으로 IoC 되어있는 loadUserByUsername 함수가 실행됨

@Service
public class PrincipalDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	
	@Override // 여기서는 view의 name을 받기때문에 반드시 통일!
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("loadUserByUsername"); // 함수 불러지는지 확인
		User user = userRepository.findByUsername(username); // name=username으로 값이 있는지 불러와야함
		System.out.println("username : " + user); // user가 있는지 찍어보자
		if (user == null)
			return null;
		PrincipalDetails pobject = new PrincipalDetails(user); 
		//PrincipalDetails에 선언되어있는 User값이 같이 들어가서 PrincipalDetails객체가 됨
		return pobject;
	}
	// 정리하면 Security Session(Authentication(내부 UserDetails)) => Authentication(내부 UserDetails) => UserDetails(PrincipalDetails) => User 오브젝트 접근
}
