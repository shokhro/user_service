package school.faang.user_service.controller.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import school.faang.user_service.dto.user.CreateUserDto;
import school.faang.user_service.dto.user.SearchUserDto;
import school.faang.user_service.dto.user.UpdateUserDto;
import school.faang.user_service.dto.user.UserDto;
import school.faang.user_service.service.user.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody @Valid CreateUserDto userDto) {
        UserDto createdUser = userService.create(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(@PathVariable Long id,
                                          @RequestBody @Valid UpdateUserDto userDto) {
        UserDto createdUser = userService.update(id, userDto);
        return ResponseEntity.status(HttpStatus.OK).body(createdUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAll(@RequestBody @Valid SearchUserDto searchUserDto) {
        List<UserDto> users = userService.getUsers(searchUserDto);
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @PostMapping("/{id}/avatar")
    public void uploadAvatar(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        System.out.println("File name: " + file.getOriginalFilename());
    }

}



















