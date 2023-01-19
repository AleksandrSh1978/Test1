package ru.lesson.jpademo.service;

import ru.lesson.jpademo.domain.Content;
import ru.lesson.jpademo.domain.Genre;

import java.util.Set;

public interface TestService {

    Set<Content> getContentsByGenreId(Long genreId);//метод возвращает набор контентов определенного жанра

    Set<Genre> getGenresByContentId(Long contentId, String name);//метод возвращает набор жанров определенного контента
}
