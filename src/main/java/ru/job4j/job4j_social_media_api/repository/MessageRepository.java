package ru.job4j.job4j_social_media_api.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.job4j_social_media_api.model.Message;

public interface MessageRepository extends CrudRepository<Message, Long> {
}

