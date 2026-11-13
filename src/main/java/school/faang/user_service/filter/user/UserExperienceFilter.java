package school.faang.user_service.filter.user;

import org.springframework.stereotype.Component;
import school.faang.user_service.dto.user.SearchUserDto;
import school.faang.user_service.entity.User;

import java.util.stream.Stream;

@Component
public class UserExperienceFilter implements UserFilter{

    @Override
    public boolean isApplicable(SearchUserDto searchUserDto) {
        return searchUserDto.experience() != null;
    }

    @Override
    public Stream<User> apply(Stream<User> users, SearchUserDto searchUserDto) {
        return users
                .filter(user -> searchUserDto.experience().equals(user.getExperience()));
    }
}

