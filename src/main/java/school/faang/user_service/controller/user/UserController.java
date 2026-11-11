package school.faang.user_service.controller.user;

import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import school.faang.user_service.dto.user.CreateUserDto;
import school.faang.user_service.dto.user.SearchUserDto;
import school.faang.user_service.dto.user.UpdateUserDto;
import school.faang.user_service.dto.user.UserDto;
import school.faang.user_service.exception.DataValidationException;
import school.faang.user_service.service.user.UserService;
import school.faang.user_service.validator.user.UserValidator;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final UserValidator userValidator;

    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody CreateUserDto userDto) {
        userValidator.validateCreate(userDto);
        UserDto createdUser = userService.create(userDto);
        return ResponseEntity.status(HttpStatus.SC_CREATED).body(createdUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(@PathVariable Long id,
                                          @RequestBody UpdateUserDto userDto) {
        userValidator.validateUpdate(userDto);
        UserDto createdUser = userService.update(id, userDto);
        return ResponseEntity.status(HttpStatus.SC_OK).body(createdUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.SC_OK).body(userService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAll(@RequestBody SearchUserDto searchUserDto) {
        List<UserDto> users = userService.getUsers(searchUserDto);
        return ResponseEntity.status(200).body(users);
    }

}



















