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
    List<Post> findByUserId(Long userId);

    List<Post> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query("""
            update Post post set post.title = :title and post.text = :text
            where post.id = :id
            """)
    int updateTitleAndText(@Param("title") String title, @Param("text") String text, @Param("id") Long id);

    @Modifying
    @Query("""
            UPDATE Post p SET p.imageURL = null WHERE p.id = :postId
            """)
    void deleteImageByPostId(@Param("postId") Long postId);

    @Modifying
    @Query("""
            DELETE FROM Post p WHERE p.id = :postId
            """)
    void deletePostById(@Param("postId") Long postId);
}

