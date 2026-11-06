package school.faang.user_service.filter.user;

import org.springframework.stereotype.Component;
import school.faang.user_service.dto.user.SearchUserDto;
import school.faang.user_service.entity.User;

import java.util.stream.Stream;

@Component
public class UserAboutMeFilter implements UserFilter{

    @Override
    public boolean isApplicable(SearchUserDto searchUserDto) {
        return searchUserDto.aboutMePattern() != null;
    }

    @Override
    public Stream<User> apply(Stream<User> users, SearchUserDto searchUserDto) {
        String pattern = searchUserDto.aboutMePattern().toLowerCase();

        return users
                .filter(user -> user.getAboutMe() != null
                        && user.getAboutMe().toLowerCase().contains(pattern));
    }
}

