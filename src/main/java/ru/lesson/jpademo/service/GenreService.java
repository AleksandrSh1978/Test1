package ru.lesson.jpademo.service;

import ru.lesson.jpademo.domain.Genre;
import ru.lesson.jpademo.dto.request.CreateGenreDto;
import ru.lesson.jpademo.dto.response.GenreDto;

import java.util.List;

public interface GenreService {

    GenreDto create(CreateGenreDto createGenreDto);

    GenreDto getById(Long id);

    GenreDto updateById(Long id, CreateGenreDto createGenreDto );

    void deleteById(Long id);


    List<GenreDto> getList();//метод возвращает лист жанров

}
