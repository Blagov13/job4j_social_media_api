package ru.job4j.job4j_social_media_api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Информация о модели постов")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank(message = "Заголовок не может быть пустым")
    @Size(min = 3, max = 100, message = "Заголовок должен содержать от 3 до 100 символов")
    private String title;

    @NotBlank(message = "Text не может быть пустым")
    private String text;

    @Column(name = "image_url")
    private String imageURL;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "author_id")
    private Long author;
}
