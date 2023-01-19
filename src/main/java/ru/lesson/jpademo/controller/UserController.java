package ru.lesson.jpademo.controller;

import org.springframework.web.bind.annotation.*;
import ru.lesson.jpademo.domain.SexType;
import ru.lesson.jpademo.dto.request.CreateUserDto;
import ru.lesson.jpademo.dto.response.UserDto;
import ru.lesson.jpademo.repository.UserRepository;
import ru.lesson.jpademo.service.UserService;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {
    private final UserService userService;//объявляем соотв экземпляр класса
    private final UserRepository userRepository;//объявляем соотв экземпляр класса

    public UserController(UserService userService, UserRepository userRepository) {//конструктор
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping
    public UserDto create(@RequestBody CreateUserDto createUserDto) {
        return userService.create(createUserDto);
    }

    @GetMapping("by-id")
    public UserDto getById(Long id){
        return userService.getById(id);
    }

    @DeleteMapping("by-id")
    public void deleteById(Long id){
        userService.deleteById(id);
    }

    @PatchMapping("by-id")
    public UserDto updateById(Long id, @RequestBody CreateUserDto createUserDto){
        return userService.updateById(id, createUserDto);
    }



    @GetMapping("author-age-over-18")//5
    public List<UserDto>getAuthorsAgeOver18(int pageNumber, int pageSize){
        return userService.getAuthorsAgeOver18(pageNumber, pageSize);
    }

    @GetMapping("users-by-ids-sextype")//6
    public List<UserDto> getUsersByIdsSexType(@RequestParam Long[] authorsIds, @RequestParam SexType sexType, int pageNumber, int pageSize) {
        return userService.getUsersByIdsAndSexType(authorsIds, sexType, pageNumber, pageSize);
    }

    @GetMapping("user-content-genre-comment")//5
    public List<UserDto>UsersHasContentHasCommentAndGenreDescriptionNoNull(int pageNumber, int pageSize) {
        return userService.UsersHasContentHasCommentAndGenreDescriptionNoNull(pageNumber, pageSize);
    }
}