package com.twitter.clone.authentication.api.mapper;

import com.google.inject.Inject;
import com.twitter.clone.authentication.api.dto.UserDto;
import com.twitter.clone.authentication.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class UserMapper {
    private final ModelMapper mapper;

    public UserDto userToUserDto(User source){
        if (source == null) return null;
        return mapper.map(source, UserDto.class);
    }

    public List<UserDto> userToUserDtoList(List<User> source){
        if (source == null || source.isEmpty()) {
            return Collections.emptyList();
        }
        return source
                .stream()
                .map(user -> mapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    public User userDtoToUser(UserDto source){
        if (source == null) return null;
        return mapper.map(source,User.class);
    }
}
