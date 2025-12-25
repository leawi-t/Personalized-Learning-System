package com.Project.Personalized_Learning_System.controller;

import com.Project.Personalized_Learning_System.dto.categoryDto.CategoryDetailDto;
import com.Project.Personalized_Learning_System.dto.categoryDto.CategoryResponseDto;
import com.Project.Personalized_Learning_System.dto.categoryDto.CreateCategoryDto;
import com.Project.Personalized_Learning_System.dto.userDto.*;
import com.Project.Personalized_Learning_System.service.CategoryService;
import com.Project.Personalized_Learning_System.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final CategoryService categoryService;

    @Autowired
    public UserController(UserService userService, CategoryService categoryService){
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUser(){
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
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

    @GetMapping("/{userId}/categories")
    public ResponseEntity<List<CategoryResponseDto>> getCategoriesByUser(@PathVariable long userId){
        return new ResponseEntity<>(categoryService.getCategoryByUser(userId), HttpStatus.OK);
    }

    @GetMapping("/{userId}/categories/{categoryId}")
    public ResponseEntity<CategoryDetailDto> getSpecificCategoryByUser(@PathVariable long userId, @PathVariable long categoryId){
        return new ResponseEntity<>(categoryService.findByUserIdAndId(userId, categoryId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDetailsDto> createUser(@RequestBody CreateUserDto dto){
        return new ResponseEntity<>(userService.createUser(dto), HttpStatus.CREATED);
    }

    @PostMapping("/{userId}/categories")
    public ResponseEntity<CategoryDetailDto> createCategory(@RequestBody CreateCategoryDto dto, @PathVariable long userId){
        return new ResponseEntity<>(categoryService.addCategory(dto, userId), HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDetailsDto> updateUser(@RequestBody UserUpdateDto dto, @PathVariable long userId){
        return new ResponseEntity<>(userService.updateUser(dto, userId), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteById(@PathVariable long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

}
