package com.Project.Personalized_Learning_System.user;

import com.Project.Personalized_Learning_System.user.userDto.UserDetailsDto;
import com.Project.Personalized_Learning_System.user.userDto.UserRequestDto;
import com.Project.Personalized_Learning_System.user.userDto.UserResponseDto;
import com.Project.Personalized_Learning_System.user.userDto.UserUpdateDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<PagedModel<UserResponseDto>> getAllUsers(Pageable pageable){
        Page<UserResponseDto> page = userService.getAllUsers(pageable);
        return ResponseEntity.ok(new PagedModel<>(page));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDetailsDto> getUserById(@PathVariable long userId){
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }

    @GetMapping("/search/byUsername")
    public ResponseEntity<UserDetailsDto> getUserByUsername(@RequestParam String username){
        return new ResponseEntity<>(userService.getUserByUsername(username), HttpStatus.OK);
    }

    @GetMapping("/search/byEmail")
    public ResponseEntity<UserDetailsDto> getUserByEmail(@RequestParam String email){
        return new ResponseEntity<>(userService.getUserByEmail(email), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDetailsDto> registerUser(@RequestBody @Valid UserRequestDto dto){
        return new ResponseEntity<>(userService.registerUser(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDetailsDto> updateUser(@RequestBody @Valid UserUpdateDto dto, @PathVariable long userId){
        return new ResponseEntity<>(userService.updateUser(dto, userId), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteById(@PathVariable long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

}
