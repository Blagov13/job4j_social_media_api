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
import ru.job4j.job4j_social_media_api.dto.UserPostsDTO;
import ru.job4j.job4j_social_media_api.model.Post;
import ru.job4j.job4j_social_media_api.service.PostService;

import java.util.List;

@Tag(name = "PostController", description = "PostController management APIs")
@AllArgsConstructor
@RestController
@RequestMapping("/api/post")
public class PostController {
    private final PostService postService;

    @Operation(
            summary = "Создать новый пост",
            description = "Создает новый пост на основе переданных данных.",
            tags = {"Post", "CreatePost"})
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Пост успешно создан",
                    content = {@Content(schema = @Schema(implementation = Post.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Неверный запрос",
                    content = {@Content(schema = @Schema())})})
    @PostMapping
    public ResponseEntity<Post> save(@Valid @RequestBody Post post) {
        postService.create(post);
        var uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(post.getId())
                .toUri();
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(uri)
                .body(post);
    }

    @Operation(
            summary = "Получить пост по postId",
            description = "Получите объект Post, указав его postId. Ответом будет объект Post с postId.",
            tags = {"Post", "get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Post.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())})})
    @GetMapping("/{postId}")
    public ResponseEntity<Post> get(@PathVariable Long postId) {
        return postService.findById(postId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Изменить название и текст поста по postId",
            description = "Измените объект Post, указав его postId, tittle, text.",
            tags = {"Post", "update"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Post.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())})})
    @PatchMapping("/{postId}")
    public ResponseEntity<Void> update(@PathVariable Long postId, @RequestParam String tittle, @RequestParam String text) {
        if (postService.update(tittle, text, postId)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "Удалить пост по postId",
            description = "Удалите объект Post, указав его postId.",
            tags = {"Post", "removeById"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Post.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())})})
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> removeById(@PathVariable Long postId) {
        if (postService.deletePostById(postId)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "Получить все посты пользователей по списку userId",
            description = "Получите список постов пользователей, указав их userId.",
            tags = {"List", "getUserPosts"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список постов пользователей",
                    content = {@Content(schema = @Schema(implementation = UserPostsDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Неверный запрос",
                    content = {@Content(schema = @Schema())})})
    @GetMapping("/users/posts")
    public ResponseEntity<List<UserPostsDTO>> getUserPosts(@RequestParam List<Long> userIds) {
        List<UserPostsDTO> userPosts = postService.getUserPosts(userIds);
        if (userPosts.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(userPosts);
    }
}