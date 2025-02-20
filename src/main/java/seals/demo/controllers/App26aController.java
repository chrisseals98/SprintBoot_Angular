package seals.demo.controllers;

import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import seals.demo.models.app26a;
import seals.demo.models.user;
import seals.demo.repositories.app26aRepository;
import seals.demo.repositories.userRepository;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/application")
public class App26aController {
    @Autowired
    private app26aRepository app26aRepository;
    @Autowired
    private userRepository userRepository;

    @GetMapping
    public List<app26a> getApp26as() {
        return (List<app26a>) this.app26aRepository.findAll();
    }

    @PostMapping
    public app26a createApp26a(@RequestBody app26a app, @AuthenticationPrincipal OAuth2User oAuthUser) {
        Stream<user> stream = StreamSupport.stream(userRepository.findAll().spliterator(), false);
        Stream<user> filteredStream = stream.filter(user -> user.getEmail().equals(oAuthUser.getAttribute("email")));
        List<user> list = filteredStream.toList();

        if(list.size() > 0) {
            app.setRequestor(list.get(0));
        }
        else {
            user newUser = new user(oAuthUser.getAttribute("name"), oAuthUser.getAttribute("name"), oAuthUser.getAttribute("email"));
            user createdUser = userRepository.save(newUser);
            app.setRequestor(createdUser);
        }
        app26aRepository.save(app);
        return app;
    }

    @DeleteMapping
    public ResponseEntity<String> deleteApp26a(@RequestParam Long id) {
        this.app26aRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
