package ru.lesson.jpademo.service;

import ru.lesson.jpademo.domain.Content;
import ru.lesson.jpademo.dto.request.CreateCommentDto;
import ru.lesson.jpademo.dto.response.CommentDto;

import java.util.List;

//интерфейс сервиса для сущности коммент. Здесь содержатся сигнатуры методов(в том числе CRUD), а так же другие данные,
// необходимые нам для работы с сущностями

public interface CommentService {
    CommentDto create(CreateCommentDto createCommentDto);

    CommentDto getById(Long id);

    CommentDto updateById(Long id, CreateCommentDto createCommentDto);

    void deleteById(Long id);


    List<CommentDto> getCommentsBySearchText(String text, int pageNumber, int pageSize);//4 метод возвращает лист комментариев по искомому тексту

    Boolean doesTheContentHaveСomments(Long contentId);//5 метод определяет есть ли комментарии у контента



}
