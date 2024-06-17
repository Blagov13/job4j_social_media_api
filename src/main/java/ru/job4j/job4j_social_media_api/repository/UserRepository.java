package ru.job4j.job4j_social_media_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
        SELECT u FROM User u JOIN u.followers f WHERE f.id = :userId
        """)
    List<User> findAllFollowersByUserId(@Param("userId") Long userId);

    /**
     * Найти в БД всех друзей пользователя.
     *
     * @param userId ID пользователя.
     * @return список всех друзей пользователя.
     */
    @Query("""
        SELECT u FROM User u JOIN u.friends f WHERE f.id = :userId
        """)
    List<User> findAllFriendsByUserId(@Param("userId") Long userId);

    /**
     * Обновить в БД логин и email пользователя.
     *
     * @param username логин пользователя.
     * @param email    email пользователя.
     * @param id       ID пользователя.
     */
    @Modifying(clearAutomatically = true)
    @Query("""
            update User user set user.username = :username, user.email = :email
            where user.id = :id
            """)
    void updateUserNameAndEmail(@Param("username") String username, @Param("email") String email, @Param("id") Long id);

    /**
     * Удаление из БД пользователя.
     *
     * @param userId ID пользователя.
     */
    @Modifying
    @Query("""
            DELETE FROM User u WHERE u.id = :userId
            """)
    void deleteById(@Param("userId") Long userId);
}

