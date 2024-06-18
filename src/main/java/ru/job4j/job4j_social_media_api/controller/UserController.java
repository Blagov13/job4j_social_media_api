package ru.job4j.job4j_social_media_api.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.job4j.job4j_social_media_api.model.User;
import ru.job4j.job4j_social_media_api.service.UserService;

@AllArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> save(@RequestBody User user) {
        userService.save(user);
        var uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(uri)
                .body(user);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> get(@PathVariable int userId) {
        return userService.findById(userId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping
    public ResponseEntity<Void> update(@PathVariable String username, String email, Long id) {
        if (userService.update(username, email, id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> removeById(@PathVariable int userId) {
        if (userService.deleteById(userId)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
