package seals.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import seals.demo.models.app26a;
import seals.demo.repositories.app26aRepository;
import seals.demo.repositories.userRepository;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class App26aController {
    @Autowired
    private app26aRepository app26aRepository;
    @Autowired
    private userRepository userRepository;

    @GetMapping("/applications")
    public List<app26a> getApp26as() {
        return (List<app26a>) this.app26aRepository.findAll();
    }

    @PostMapping("/application")
    public app26a createApp26a(@RequestBody app26a app) {
        app.setRequestor(userRepository.findById(Long.parseLong("1")).get());
        app26aRepository.save(app);
        return app;
    }
}
