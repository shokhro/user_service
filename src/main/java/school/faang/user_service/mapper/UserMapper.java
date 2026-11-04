package school.faang.user_service.mapper;

import org.mapstruct.Mapper;
import school.faang.user_service.dto.user.CreateUserDto;
import school.faang.user_service.dto.user.UserDto;
import school.faang.user_service.entity.User;

@Mapper
public interface UserMapper {

    User toUser(CreateUserDto userDto);

    UserDto toUserDto(User user);
}
