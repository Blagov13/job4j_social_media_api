package ru.job4j.job4j_social_media_api.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import ru.job4j.job4j_social_media_api.Job4jSocialMediaApiApplication;
import ru.job4j.job4j_social_media_api.model.Post;
import ru.job4j.job4j_social_media_api.model.User;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = Job4jSocialMediaApiApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Transactional
    @Test
    public void whenFindByAuthorIdThenReturnPosts() {
        User author = createUser(1L, "John Doe", "john.doe@example.com", "123");
        User savedAuthor = userRepository.save(author);
        Post post1 = new Post(1L, "Title 1", "Text 1", "image1.jpg", LocalDateTime.now(), savedAuthor.getId());
        Post post2 = new Post(2L, "Title 2", "Text 2", "image2.jpg", LocalDateTime.now(), savedAuthor.getId());
        postRepository.save(post1);
        postRepository.save(post2);
        List<Post> foundPosts = postRepository.findByAuthor(savedAuthor.getId());
        assertThat(foundPosts).hasSize(2);
    }

    @Transactional
    @Test
    public void whenFindByCreatedAtBetweenThenReturnPosts() {
        User author = createUser(1L, "test3", "test3@example.com", "123");
        userRepository.save(author);
        LocalDateTime startDate = LocalDateTime.now().minusDays(1).withSecond(0).withNano(0);
        LocalDateTime endDate = LocalDateTime.now().withSecond(0).withNano(0);
        Post post1 = new Post(1L, "Title 1", "Text 1", "image1.jpg", startDate, author.getId());
        Post post2 = new Post(2L, "Title 2", "Text 2", "image2.jpg", endDate, author.getId());
        postRepository.save(post1);
        postRepository.save(post2);
        List<Post> foundPosts = postRepository.findByCreatedAtBetween(startDate, endDate);
        assertThat(foundPosts.get(0).getText()).contains(post1.getText());
        assertThat(foundPosts.get(1).getText()).contains(post2.getText());
    }

    @Transactional
    @Test
    public void whenFindAllByOrderByCreatedAtDescThenReturnOrderedPosts() {
        User author = createUser(1L, "Test333", "test333@example.com", "123");
        userRepository.save(author);
        Post post1 = new Post(1L, "Title 1", "Text 1", "image1.jpg", LocalDateTime.now().minusDays(3), author.getId());
        Post post2 = new Post(2L, "Title 2", "Text 2", "image2.jpg", LocalDateTime.now().minusDays(2), author.getId());
        Post post3 = new Post(3L, "Title 3", "Text 3", "image3.jpg", LocalDateTime.now().minusDays(1), author.getId());
        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);
        Page<Post> foundPostsPage = postRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(0, 10));
        assertThat(foundPostsPage.getContent().get(0).getText()).isEqualTo(post3.getText());
        assertThat(foundPostsPage.getContent().get(1).getText()).isEqualTo(post2.getText());
    }

    private User createUser(Long id, String username, String email, String passwordHash) {
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setEmail(email);
        user.setPasswordHash(passwordHash);
        return user;
    }
}

