package com.cos.project1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity //SecurityConfig가 스프링 필터체인에 등록이 된다. 			// prePostEnabled -> preAthorized 어노테이션 활성화하여 접근금지페이지에 잠시 가도록 권한 주기 가능
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true) //securedEnabled -> secured 어노테이션 활성화하여 페이지 접근 컨트롤 가능
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Bean
	public BCryptPasswordEncoder encodePwd() {
		return new BCryptPasswordEncoder();
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable(); //@EnableWebSecurity 기본 시큐리티 로그인창 제공하는데, .disable()로 없앨수있음. 
		http.authorizeRequests()
			.antMatchers("/user/**").authenticated()
			.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
			.anyRequest().permitAll()
			.and()
			.formLogin() // 위의 user/, admin/ 이하 요청이면 /login으로 강제 이동
			.loginPage("/loginForm")
			.loginProcessingUrl("/login") //login주소가 호출되면 시큐리티가 낚아채서 대신 로그인 진행
			.defaultSuccessUrl("/")//로그인성공시 메인페이지
			//시큐리티의 좋은점은 /user, /admin을 치면 로그인하라고하는데 로그인하면 바로 /.. 페이지로 보내준다. 
			//여기까지 세팅이 되면 admin으로 따로 가입후에 DB에서 ROLE을 직접 ADMIN으로 변경해줘야한다. 
			.and()
				.logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/")
				.invalidateHttpSession(true);
	}
}
