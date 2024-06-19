package ru.job4j.job4j_social_media_api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Table(name = "friend_requests")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Schema(description = "Информация о модели подписчиков и друзей")
public class FriendRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotNull(message = "Идентификатор отправителя не может быть нулевым")
    private Long sender;

    @NotNull(message = "Идентификатор получателя не может быть нулевым")
    private Long receiver;

    @NotBlank(message = "Статус не может быть пустым")
    private String status;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt = LocalDateTime.now();
}
