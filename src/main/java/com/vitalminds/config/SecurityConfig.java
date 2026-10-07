package com.vitalminds.config;
import com.vitalminds.repository.UserRepository;
import org.springframework.context.annotation.*; import org.springframework.security.config.annotation.web.builders.HttpSecurity; import org.springframework.security.core.userdetails.*; import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; import org.springframework.security.crypto.password.PasswordEncoder; import org.springframework.security.web.SecurityFilterChain;
@Configuration public class SecurityConfig{
 @Bean PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}
 @Bean UserDetailsService userDetailsService(UserRepository repo){return username->repo.findByEmail(username).map(u->User.withUsername(u.getEmail()).password(u.getPassword()).roles(u.getRole()==null?"USER":u.getRole()).build()).orElseThrow(()->new UsernameNotFoundException("User not found"));}
 @Bean SecurityFilterChain filterChain(HttpSecurity http)throws Exception{
  http.authorizeHttpRequests(a->a.requestMatchers("/","/login","/register","/api/register","/app.css","/js/**").permitAll().requestMatchers("/admin/**").hasRole("ADMIN").anyRequest().authenticated())
   .formLogin(f->f.loginPage("/login").defaultSuccessUrl("/dashboard",true).permitAll())
   .logout(l->l.logoutSuccessUrl("/login?logout").permitAll()).csrf(c->c.disable());
  return http.build();
 }
}
