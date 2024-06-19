package ru.job4j.job4j_social_media_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.job4j.job4j_social_media_api.model.Post;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPostsDTO {
    private Long userId;

    @NotBlank(message = "username не может быть пустым")
    @Size(min = 3, max = 50, message = "Username не может быть меньше 3 символов и больше 50")
    private String username;

    private List<Post> posts;
}
