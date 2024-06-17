package ru.job4j.job4j_social_media_api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.job4j.job4j_social_media_api.model.Post;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    /**
     * Найти в БД список постов пользователя по id.
     *
     * @param author ID пользователя.
     * @return список всех постов пользователя.
     */
    List<Post> findByAuthor(Long author);

    /**
     * Найти в БД список постов в диапазоне даты.
     *
     * @param startDate начальная дата.
     * @param endDate   конечная дата.
     * @return список всех в диапазоне даты.
     */
    List<Post> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Найти в БД список постов отсортированный по дате с пагинацией.
     *
     * @param pageable "пачка" данных.
     * @return пачка данных отсортированных по дате.
     */
    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);

    /**
     * Обновить в БД имя и описания поста.
     *
     * @param title заголовок поста.
     * @param text  описание поста.
     * @param id    ID поста.
     * @return количество измененных данных.
     */
    @Modifying(clearAutomatically = true)
    @Query("""
            update Post post set post.title = :title, post.text = :text
            where post.id = :id
            """)
    int updateTitleAndText(@Param("title") String title, @Param("text") String text, @Param("id") Long id);

    /**
     * Удаление из БД изображения прикрепленного к посту.
     *
     * @param postId ID поста.
     */
    @Modifying
    @Query("""
            UPDATE Post p SET p.imageURL = null WHERE p.id = :postId
            """)
    int deleteImageByPostId(@Param("postId") Long postId);

    /**
     * Удаление из БД поста.
     *
     * @param postId ID поста.
     */
    @Modifying
    @Query("""
            DELETE FROM Post p WHERE p.id = :postId
            """)
    int deletePostById(@Param("postId") Long postId);
}

