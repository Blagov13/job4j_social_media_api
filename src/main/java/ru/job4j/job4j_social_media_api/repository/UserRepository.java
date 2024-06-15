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

    /**
     * Найти в БД пользователя по логину и паролю.
     *
     * @param username     логин пользователя.
     * @param passwordHash пароль пользователя.
     * @return пользователь.
     */
    @Query("""
            select user from User as user
            where user.username = ?1 and user.passwordHash = ?2
            """)
    Optional<User> findByLevelInRange(String username, String passwordHash);

    /**
     * Найти в БД всех подписчиков пользователя.
     *
     * @param userId ID пользователя.
     * @return список всех подписчиков пользователя.
     */
    @Query("""
            SELECT u.followers FROM User u WHERE u.id = :userId
            """)
    List<User> findAllFollowersByUserId(@Param("userId") Long userId);

    /**
     * Найти в БД всех друзей пользователя.
     *
     * @param userId ID пользователя.
     * @return список всех друзей пользователя.
     */
    @Query("""
            SELECT u.friends FROM User u WHERE u.id = :userId
            """)
    List<User> findAllFriendsByUserId(@Param("userId") Long userId);

    /**
     * Найти в БД все посты подписчиков пользователя отсортированных
     * от самых новых к старым с пагинацией.
     *
     * @param userId ID пользователя.
     * @return список всех постов подписчиков пользователя отсортированных
     * * от самых новых к старым с пагинацией.
     */
    @Query("""
            SELECT p FROM Post p WHERE p.author IN (SELECT u.followers FROM User u WHERE u.id = :userId) ORDER BY p.createdAt DESC
            """)
    Page<Post> findAllPostsFromFollowersByUserIdOrderByCreatedAtDesc(@Param("userId") Long userId, Pageable pageable);
}

