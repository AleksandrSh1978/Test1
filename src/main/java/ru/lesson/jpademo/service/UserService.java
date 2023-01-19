package ru.lesson.jpademo.service;

import ru.lesson.jpademo.domain.SexType;
import ru.lesson.jpademo.dto.request.CreateUserDto;
import ru.lesson.jpademo.dto.response.UserDto;

import java.util.Date;
import java.util.List;

public interface UserService {
    UserDto create(CreateUserDto createUserDto);

    UserDto getById(Long id);

    UserDto updateById(Long id, CreateUserDto createUserDto);

    void deleteById(Long id);


    List<UserDto> getAuthorsAgeOver18(int pageNumber, int pageSize); //3 метод возвращает лист юзеров, которым исполнилось 18лет

    List<UserDto> getUsersByIdsAndSexType(Long[] authorsIds, SexType sexType, int pageNumber, int pageSize);//6 метод возвращает лист пользователей
    // по введенному листу айдишников и определенного пола

    List<UserDto> UsersHasContentHasCommentAndGenreDescriptionNoNull(int pageNumber, int pageSize); // метод находит всех(лист) пользователей у кого есть более трех контентов,
    // у которых есть более одного комментария и более одного жанра(у которого поле description != null





}
