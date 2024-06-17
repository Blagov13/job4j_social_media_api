package ru.job4j.job4j_social_media_api.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.job4j_social_media_api.model.User;
import ru.job4j.job4j_social_media_api.repository.UserRepository;

import java.util.Optional;

@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findById(long id) {
        return userRepository.findById(id);
    }

    public void update(String username, String email, Long id) {
        userRepository.updateUserNameAndEmail(username, email, id);
    }

    public void deleteById(long userId) {
        userRepository.deleteById(userId);
    }
}
