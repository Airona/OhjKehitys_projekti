package fi.swd.projektityo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

//import fi.swd.Bookstore.web.UserDetailServiceImpl;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import fi.swd.projektityo.web.UserDetailServiceImpl;

@Configuration
//@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    
	@Autowired
    private UserDetailServiceImpl userDetailsService;	
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http.csrf().disable();
    	
    	http
//        .authorizeRequests().antMatchers("/signup", "/saveuser").permitAll()
//        .and()
        .authorizeRequests().antMatchers("/css/**").permitAll()
        .and()
      .authorizeRequests()
    	.antMatchers("/").permitAll()
        .anyRequest().authenticated()
        .and()
      .formLogin()
          .loginPage("/login")
          .defaultSuccessUrl("/react.html")
          .permitAll()
          .and()
      .logout()
          .permitAll();
    }
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//    	auth
//    		.inMemoryAuthentication()
//    			.withUser("user").password("userpw").roles("USER");
//    	auth
//		.inMemoryAuthentication()
//			.withUser("admin").password("adminpw").roles("ADMIN");
    	auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }
}