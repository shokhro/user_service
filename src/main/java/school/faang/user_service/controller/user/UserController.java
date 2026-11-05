package school.faang.user_service.controller.user;

import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import school.faang.user_service.dto.user.CreateUserDto;
import school.faang.user_service.dto.user.UpdateUserDto;
import school.faang.user_service.dto.user.UserDto;
import school.faang.user_service.exception.DataValidationException;
import school.faang.user_service.exception.ErrorResponse;
import school.faang.user_service.service.user.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody CreateUserDto userDto) {
        if (userDto.getUsername() == null || userDto.getUsername().isBlank()) {
            throw new DataValidationException("Username should be present!");
        }
        if (userDto.getEmail() == null || userDto.getEmail().isBlank()) {
            throw new DataValidationException("Email should be present!");
        }
        if (userDto.getPassword() == null || userDto.getPassword().isBlank()) {
            throw new DataValidationException("Password should be present!");
        }
        if (userDto.getCountryId() == null) {
            throw new DataValidationException("Country should be present!");
        }

        UserDto createdUser = userService.create(userDto);
        return ResponseEntity.status(HttpStatus.SC_CREATED).body(createdUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(@RequestParam Long id,
                                          @RequestBody UpdateUserDto userDto) {
        if (userDto.getUsername() == null || userDto.getUsername().isBlank()) {
            throw new DataValidationException("Username should be present!");
        }
        if (userDto.getEmail() == null || userDto.getEmail().isBlank()) {
            throw new DataValidationException("Email should be present!");
        }
        if (userDto.getCountryId() == null) {
            throw new DataValidationException("Country should be present!");
        }
        UserDto createdUser = userService.update(id, userDto);
        return ResponseEntity.status(HttpStatus.SC_OK).body(createdUser);
    }

    public ResponseEntity<UserDto> getById(Long id) {
        return ResponseEntity.status(HttpStatus.SC_OK).body(userService.getById(id));
    }

}



















