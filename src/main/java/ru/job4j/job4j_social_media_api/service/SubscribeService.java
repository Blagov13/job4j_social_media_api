package ru.job4j.job4j_social_media_api.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.job4j_social_media_api.model.User;
import ru.job4j.job4j_social_media_api.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@AllArgsConstructor
@Service
public class SubscribeService {
    private final UserRepository userRepository;

    /**
     * Удаление друга, так же отписка от друга.
     * При этом друг остается подписчиком.
     *
     * @param userId   ID пользователя.
     * @param friendId ID друга.
     */
    @Transactional
    public void removeFriend(Long userId, Long friendId) {
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<User> friendOpt = userRepository.findById(friendId);
        if (userOpt.isPresent() && friendOpt.isPresent()) {
            User user = userOpt.get();
            User friend = friendOpt.get();
            user.getFriends().remove(friend);
            friend.getFriends().remove(user);
            user.getFollowing().remove(friend);
            friend.getFollowers().remove(user);
            userRepository.save(user);
            userRepository.save(friend);
        }
    }

    /**
     * Отправка заявки в друзья.
     *
     * @param senderId   ID пользователя отправившего заявку.
     * @param receiverId ID пользователя кому отправили заявку.
     */
    @Transactional
    public void sendFriendRequest(Long senderId, Long receiverId) {
        Optional<User> senderOpt = userRepository.findById(senderId);
        Optional<User> receiverOpt = userRepository.findById(receiverId);
        if (senderOpt.isPresent() && receiverOpt.isPresent()) {
            User sender = senderOpt.get();
            User receiver = receiverOpt.get();
            sender.getFollowing().add(receiver);
            receiver.getFollowers().add(sender);
            userRepository.save(sender);
            userRepository.save(receiver);
        }
    }

    /**
     * Принятие заявки в друзья.
     *
     * @param senderId   ID пользователя отправившего заявку.
     * @param receiverId ID пользователя кому отправили заявку.
     */
    @Transactional
    public void acceptFriendRequest(Long senderId, Long receiverId) {
        Optional<User> senderOpt = userRepository.findById(senderId);
        Optional<User> receiverOpt = userRepository.findById(receiverId);
        if (senderOpt.isPresent() && receiverOpt.isPresent()) {
            User sender = senderOpt.get();
            User receiver = receiverOpt.get();
            sender.getFriends().add(receiver);
            receiver.getFriends().add(sender);
            sender.getFollowing().add(receiver);
            receiver.getFollowers().add(sender);
            userRepository.save(sender);
            userRepository.save(receiver);
        }
    }

    /**
     * Отклонение заявки в друзья.
     *
     * @param senderId   ID пользователя отправившего заявку.
     * @param receiverId ID пользователя кому отправили заявку.
     */
    @Transactional
    public void rejectFriendRequest(Long senderId, Long receiverId) {
        Optional<User> senderOpt = userRepository.findById(senderId);
        Optional<User> receiverOpt = userRepository.findById(receiverId);
        if (senderOpt.isPresent() && receiverOpt.isPresent()) {
            User sender = senderOpt.get();
            User receiver = receiverOpt.get();
            sender.getFollowing().add(receiver);
            receiver.getFollowers().add(sender);
            userRepository.save(sender);
            userRepository.save(receiver);
        }
    }
}
