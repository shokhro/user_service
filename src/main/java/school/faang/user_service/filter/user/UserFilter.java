package school.faang.user_service.filter.user;

import school.faang.user_service.dto.user.SearchUserDto;
import school.faang.user_service.entity.User;

import java.util.stream.Stream;

public interface UserFilter {

    boolean isApplicable(SearchUserDto searchUserDto);

    Stream<User> apply(Stream<User> users, SearchUserDto searchUserDto);
}
