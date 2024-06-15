package ru.job4j.job4j_social_media_api.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.job4j_social_media_api.repository.PostRepository;
import ru.job4j.job4j_social_media_api.model.Post;

import javax.transaction.Transactional;

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

    /**
     * Удаление поста.
     *
     * @param postId ID поcта.
     */
    public void deleteById(Long postId) {
        postRepository.deleteById(postId);
    }
}