package com.vitalminds.service;
import com.vitalminds.model.User; import com.vitalminds.repository.UserRepository; import org.springframework.security.crypto.password.PasswordEncoder; import org.springframework.stereotype.Service; import java.util.Optional;
@Service public class UserService{
 private final UserRepository repo; private final PasswordEncoder encoder; public UserService(UserRepository repo,PasswordEncoder encoder){this.repo=repo;this.encoder=encoder;}
 public User register(User u){if(repo.existsByEmail(u.getEmail()))throw new IllegalArgumentException("Email already registered.");u.setPassword(encoder.encode(u.getPassword())); if(u.getRole()==null||u.getRole().isBlank()) u.setRole("USER"); return repo.save(u);}
 public Optional<User> findByEmail(String e){return repo.findByEmail(e);} public User save(User u){return repo.save(u);}
}
