package ru.job4j.job4j_social_media_api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.job4j.job4j_social_media_api.model.Post;
import ru.job4j.job4j_social_media_api.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("""
            select user from User as user
            where user.username = ?1 and user.passwordHash = ?2
            """)
    Optional<User> findByLevelInRange(String username, String passwordHash);

    @Query("""
            SELECT u.followers FROM User u WHERE u.id = :userId
            """)
    List<User> findAllFollowersByUserId(@Param("userId") Long userId);

    @Query("""
            SELECT u.friends FROM User u WHERE u.id = :userId
            """)
    List<User> findAllFriendsByUserId(@Param("userId") Long userId);

    @Query("""
            SELECT p FROM Post p WHERE p.author IN (SELECT u.followers FROM User u WHERE u.id = :userId) ORDER BY p.createdAt DESC
            """)
    Page<Post> findAllPostsFromFollowersByUserIdOrderByCreatedAtDesc(@Param("userId") Long userId, Pageable pageable);
}

