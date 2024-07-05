package seals.demo.controllers;

import org.springframework.web.bind.annotation.RestController;

import seals.demo.models.user;
import seals.demo.repositories.userRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class UserController {
    @Autowired
    private userRepository userRepository;

    @GetMapping("/users")
    public List<user> getUsers() {
        return (List<user>) this.userRepository.findAll();
    }
}
