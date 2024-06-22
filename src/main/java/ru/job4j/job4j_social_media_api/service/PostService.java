package ru.job4j.job4j_social_media_api.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.job4j_social_media_api.dto.UserPostsDTO;
import ru.job4j.job4j_social_media_api.dto.UserPostsMapper;
import ru.job4j.job4j_social_media_api.model.User;
import ru.job4j.job4j_social_media_api.repository.PostRepository;
import ru.job4j.job4j_social_media_api.model.Post;
import ru.job4j.job4j_social_media_api.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    /**
     * Создание нового поста.
     *
     * @param post поcт.
     */
    public Post create(Post post) {
        return postRepository.save(post);
    }

    /**
     * Изменение поста.
     *
     * @param updatedPost изменяемый поcт.
     */
    public void update(Post updatedPost) {
        postRepository.save(updatedPost);
    }

    public boolean update(String tittle, String text, Long id) {
        return postRepository.updateTitleAndText(tittle, text, id) > 0;
    }

    /**
     * Удаление поста.
     *
     * @param postId ID поcта.
     */
    public void deleteById(Long postId) {
        postRepository.deleteById(postId);
    }

    public boolean deletePostById(Long id) {
        return postRepository.deletePostById(id) > 0;
    }

    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }

    public List<UserPostsDTO> getUserPosts(List<Long> userIds) {
        UserPostsMapper mapper = UserPostsMapper.INSTANCE;
        return userIds.stream()
                .map(userRepository::findById)
                .filter(java.util.Optional::isPresent)
                .map(userOpt -> {
                    User user = userOpt.get();
                    List<Post> posts = postRepository.findByAuthor(user.getId());
                    return mapper.toUserPostsDTO(user, posts);
                })
                .collect(Collectors.toList());
    }
}
