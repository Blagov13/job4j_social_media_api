package ru.job4j.job4j_social_media_api.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.job4j_social_media_api.model.User;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
    }

    @Test
    public void whenSavePersonThenFindById() {
        User user = createUser("John Doe", "john.doe@example.com", "123");
        User savedUser = userRepository.save(user);
        Optional<User> foundUser = userRepository.findById(savedUser.getId());
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUsername()).isEqualTo("John Doe");
    }

    @Test
    public void whenFindAllThenReturnAllPersons() {
        User user1 = createUser("John Doe", "john.doe@example.com", "123");
        User user2 = createUser("Alex", "alex@example.com", "134");
        userRepository.save(user1);
        userRepository.save(user2);
        List<User> users = userRepository.findAll();
        assertThat(users).hasSize(2);
        assertThat(users).extracting(User::getUsername).contains("John Doe", "Alex");
    }

    @Test
    void whenFindByUsernameAndPasswordHashThenReturnUser() {
        String username = "testUser";
        String passwordHash = "testPassword";
        User user = new User();
        user.setUsername(username);
        user.setEmail("test@example.com");
        user.setPasswordHash(passwordHash);
        userRepository.save(user);
        Optional<User> foundUser = userRepository.findByLevelInRange(username, passwordHash);
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUsername()).isEqualTo(username);
        assertThat(foundUser.get().getPasswordHash()).isEqualTo(passwordHash);
    }

    @Test
    void whenFindAllFollowersByUserIdThenReturnListOfFollowers() {
        User user1 = createUser("John Doe", "john.doe@example.com", "123");
        userRepository.save(user1);
        List<User> followers = userRepository.findAllFollowersByUserId(user1.getId());
        assertThat(followers).isNotNull().hasSize(0);
    }

    @Transactional
    @Test
    void whenUpdateUserNameAndEmailThenUserShouldBeUpdated() {
        User user1 = createUser("Ben", "Ben@example.com", "123");
        userRepository.save(user1);
        String newUsername = "newUsername";
        String newEmail = "new@example.com";
        userRepository.updateUserNameAndEmail(newUsername, newEmail, user1.getId());
        User updatedUser = userRepository.findById(user1.getId()).orElse(null);
        assertThat(updatedUser).isNotNull();
        assertThat(updatedUser.getUsername()).isEqualTo(newUsername);
        assertThat(updatedUser.getEmail()).isEqualTo(newEmail);
    }

    @Test
    void whenDeleteByIdThenUserShouldBeDeleted() {
        User user1 = createUser("Shon", "Shon@example.com", "123");
        userRepository.save(user1);
        userRepository.deleteById(user1.getId());
        assertThat(userRepository.findById(user1.getId())).isEmpty();
    }

    private User createUser(String username, String email, String passwordHash) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPasswordHash(passwordHash);
        return user;
    }
}