package com.cos.project1.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.project1.model.User;

import lombok.Data;

//시큐리티가 /login을 낚아채서 로그인 진행
//로그인이 성공이면 시큐리티의 session을 만듬 (원래개념의 session과 약간 다름) -> (Security ContexHolder의 키값에 담음)
//이 키값에 들어가는 오브젝트는 정해져 있음 -> Authentication 객체
//Authentication 객체에는 User정보가 있어야됨
//이 User 오브젝트 타입은 반드시 UserDetails타입의 객체어야함

//즉 Security Session => Authentication => UserDetails(PrincipalDetails) => User 오브젝트 접근
// PrincipalDetails가 끝나면 Authentication 객체를 만들어야함 
@Data
public class PrincipalDetails implements UserDetails {

	private User user; //콤포지션
	
	public PrincipalDetails(User user) { //생성자 추가 왜? 가입시마다 체크해야하니까
		this.user = user;
	}
	
	//해당 유저의 권한을 리턴하는 곳
	//하지만 JPA상 권한 타입을 collection으로 강제하기떄문에 String role을 바꿔야함
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collect = new ArrayList<GrantedAuthority>();
//		collect.add(new GrantedAuthority() {
//		@Override
//		public String getAuthority() {
//			return user.getRole();
//		}
	//});
		//return collect;
		collect.add(() -> { return user.getRole(); }); // 위의 짧은 버전
		return collect;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() { //계정 만료됬니?
		return true; //아니오
	}

	@Override
	public boolean isAccountNonLocked() { //계정 잠겼니?
		return true; //아니오
	}

	@Override
	public boolean isCredentialsNonExpired() { //비번 오래안됬니?
		return true; //아니오
	}

	@Override
	public boolean isEnabled() { //계정 활성화됬니?
		return true; //아니오
	}
	
	//그럼 언제 false로 리턴하나?
	//예: 사이트에 1년동안 회원이 로그인을 안하면 휴먼계정으로 돌릴때
	//현재시간 - 로긴시간 => 1년 초과하면 return false 
	

}
