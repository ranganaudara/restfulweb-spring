package com.example.demo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserDaoService userDaoService;

    @GetMapping(path = "/users")
    public List<User> getAll(){
        return userDaoService.findAll();
    }

    @GetMapping(path = "/users/{id}")
    public User getOne(@PathVariable int id){
        return userDaoService.findOne(id);
    }

    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@RequestBody User user){
        User createdUser = userDaoService.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(createdUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }
}
