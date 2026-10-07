package com.vitalminds.controller;
import com.vitalminds.model.User; import com.vitalminds.service.UserService;
import org.springframework.stereotype.Controller; import org.springframework.ui.Model; import org.springframework.web.bind.annotation.*;
@Controller public class AuthController{
 private final UserService service; public AuthController(UserService service){this.service=service;}
 @GetMapping({"/","/login"}) public String login(){return "login";}
 @GetMapping("/register") public String register(){return "register";}
 @PostMapping("/api/register") public String create(@RequestParam String name,@RequestParam String email,@RequestParam String password,Model m){
  try{User u=new User();u.setName(name);u.setEmail(email);u.setPassword(password);service.register(u);return "redirect:/login?registered";}catch(Exception e){m.addAttribute("error",e.getMessage());return "register";}
 }
}
