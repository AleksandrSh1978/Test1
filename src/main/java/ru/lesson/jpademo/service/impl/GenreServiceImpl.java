package ru.lesson.jpademo.service.impl;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.lesson.jpademo.builder.GenreBuilder;
import ru.lesson.jpademo.domain.Genre;
import ru.lesson.jpademo.dto.request.CreateGenreDto;
import ru.lesson.jpademo.dto.response.GenreDto;
import ru.lesson.jpademo.repository.ContentRepository;
import ru.lesson.jpademo.repository.GenreRepository;
import ru.lesson.jpademo.service.GenreService;

import java.util.List;
import java.util.Optional;

import static ru.lesson.jpademo.builder.GenreBuilder.toGenreDto;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;
    private final ContentRepository contentRepository;

    public GenreServiceImpl(GenreRepository genreRepository, ContentRepository contentRepository, ContentRepository contentRepository1) {

        this.genreRepository = genreRepository;

        this.contentRepository = contentRepository1;
    }

    @Override
    public GenreDto create(CreateGenreDto createGenreDto) {
//        var genre = new Genre();
        if (createGenreDto.getName() == null || createGenreDto.getName().length() > 60) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (createGenreDto.getDescription().length() > 255) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Genre genre = GenreBuilder.toGenre(createGenreDto.getName(), createGenreDto.getDescription());
        var genreEntity = genreRepository.save(genre);

        return toGenreDto(genreEntity);

    }

    @Override
    public GenreDto getById(Long id) {
        Optional<Genre> genreOptional = genreRepository.findById(id);
        if (genreOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Комментарий не найден");
        }

        long countContents = contentRepository.countContentByGenreId(genreOptional.get().getId());
        //количество контента определенного жанра
        return toGenreDto(genreOptional.get(), countContents);
    }

    @Override
    public GenreDto updateById(Long id, CreateGenreDto createGenreDto) {
        Optional<Genre> optionalGenre = genreRepository.findById(id);
        if (optionalGenre.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Genre genre = optionalGenre.get();
        if (createGenreDto.getName() != null) {
            genre.setName(createGenreDto.getName());
        }
        if (createGenreDto.getDescription() != null) {
            genre.setDescription(createGenreDto.getDescription());
        }
        return toGenreDto(genreRepository.save(genre));
    }

    @Override
    public void deleteById(Long id) {
        Optional<Genre> genreOptional = genreRepository.findById(id);
        if (genreOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Комментарий не найден");
        }
        genreRepository.delete(genreOptional.get());
    }






    @Override
    public List<GenreDto> getList() {//метод возвращает лист жанров
        List<Genre> genres = genreRepository.findAll(Sort.by("name"));//лист жанров принимает значение результата метода
        //findAll(найти все с сортировкой по имени) репозитория жанров
        return genres.stream().map(x -> toGenreDto(x)).toList();//возвращаем
    }
}
