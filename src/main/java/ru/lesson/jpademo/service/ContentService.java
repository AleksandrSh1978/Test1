package ru.lesson.jpademo.service;

import org.springframework.data.domain.Pageable;
import ru.lesson.jpademo.domain.Content;
import ru.lesson.jpademo.dto.request.CreateContentDto;
import ru.lesson.jpademo.dto.response.CommentDto;
import ru.lesson.jpademo.dto.response.ContentDto;
import ru.lesson.jpademo.dto.response.UserDto;

import java.util.Date;
import java.util.List;

public interface ContentService {

    ContentDto create(CreateContentDto createContentDto);

    ContentDto getById(Long id);

    ContentDto updateById(Long id, CreateContentDto createContentDto);

    void deleteById(Long id);


    long countContent();//1 метод cчитает количество контента

    List<ContentDto> getContentsByAuthorId(Long authorId, int pageNumber, int pageSize);//2 метод возвращает лист контента по автору с определенным Id


    List<UserDto> getUsersWomanHasContent(int pageNumber, int pageSize);//7 метод находит всех пользователей женского пола,
    // у кого есть хотя бы 1 контент
}
