package ru.job4j.job4j_social_media_api.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.job4j_social_media_api.model.User;
import ru.job4j.job4j_social_media_api.service.UserService;

@AllArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> save(@RequestBody User user) {
        return ResponseEntity.ok(userService.save(user));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> get(@PathVariable int userId) {
        return userService.findById(userId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody User user) {
        userService.save(user);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeById(@PathVariable int userId) {
        userService.deleteById(userId);
    }
}
