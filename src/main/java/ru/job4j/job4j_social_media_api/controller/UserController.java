package ru.job4j.job4j_social_media_api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.job4j.job4j_social_media_api.model.User;
import ru.job4j.job4j_social_media_api.service.UserService;

@Tag(name = "UserController", description = "UserController management APIs")
@AllArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @Operation(
            summary = "Создать нового пользователя",
            description = "Создает нового пользователя на основе переданных данных.",
            tags = {"User", "CreateUser"})
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Пользователь успешно создан",
                    content = {@Content(schema = @Schema(implementation = User.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Неверный запрос",
                    content = {@Content(schema = @Schema())})})
    @PostMapping
    public ResponseEntity<User> save(@Valid @RequestBody User user) {
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

    @Operation(
            summary = "Получить пользователя по userId",
            description = "Получите объект User, указав его userId. Ответом будет объект User с userId, именем пользователя и датой создания.",
            tags = {"User", "get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = User.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())})})
    @GetMapping("/{userId}")
    public ResponseEntity<User> get(@PathVariable int userId) {
        return userService.findById(userId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Обновить информацию о пользователе",
            description = "Обновляет информацию пользователя по указанному userId.",
            tags = {"User", "UpdateUser"})
    @PatchMapping
    public ResponseEntity<Void> update(@PathVariable String username, String email, Long id) {
        if (userService.update(username, email, id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "Удалить пользователя по userId",
            description = "Удаляет пользователя по указанному userId.",
            tags = {"User", "DeleteUser"})
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> removeById(@PathVariable int userId) {
        if (userService.deleteById(userId)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}


