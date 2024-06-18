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

    public boolean update(String username, String email, Long id) {
        int result = userRepository.updateUserNameAndEmail(username, email, id);
        return result > 0;
    }

    public boolean deleteById(long userId) {
        int result = userRepository.deleteUserById(userId);
        return result > 0;
    }
}
