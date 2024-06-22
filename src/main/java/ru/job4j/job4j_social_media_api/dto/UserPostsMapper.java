package ru.job4j.job4j_social_media_api.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.job4j.job4j_social_media_api.model.Post;
import ru.job4j.job4j_social_media_api.model.User;

import java.util.List;

@Mapper
public interface UserPostsMapper {
    UserPostsMapper INSTANCE = Mappers.getMapper(UserPostsMapper.class);

    @Mapping(source = "id", target = "userId")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "posts", target = "posts")
    UserPostsDTO toUserPostsDTO(User user, List<Post> posts);
}
