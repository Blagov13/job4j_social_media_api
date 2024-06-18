package ru.job4j.job4j_social_media_api.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.job4j_social_media_api.repository.PostRepository;
import ru.job4j.job4j_social_media_api.model.Post;

import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class PostService {
    private final PostRepository postRepository;

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
        int rsl = postRepository.updateTitleAndText(tittle, text, id);
        return rsl > 0;
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
        int rsl = postRepository.deletePostById(id);
        return rsl > 0;
    }

    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }
}
