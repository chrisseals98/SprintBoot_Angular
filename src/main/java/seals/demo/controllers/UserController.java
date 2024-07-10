package seals.demo.controllers;

import org.springframework.web.bind.annotation.RestController;

import seals.demo.models.user;
import seals.demo.repositories.userRepository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class UserController {
    @Autowired
    private userRepository userRepository;

    @GetMapping("/users")
    public List<user> getUsers() {
        return (List<user>) this.userRepository.findAll();
    }

    @GetMapping("/user")
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
        return principal.getAttributes();
    }
    
}
