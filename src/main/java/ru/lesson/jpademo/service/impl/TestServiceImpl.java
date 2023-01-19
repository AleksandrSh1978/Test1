package ru.lesson.jpademo.service.impl;

import org.springframework.stereotype.Service;
import ru.lesson.jpademo.domain.Content;
import ru.lesson.jpademo.domain.Genre;
import ru.lesson.jpademo.repository.ContentRepository;
import ru.lesson.jpademo.repository.GenreRepository;
import ru.lesson.jpademo.service.TestService;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TestServiceImpl implements TestService {
    private final GenreRepository genreRepository;
    private final ContentRepository contentRepository;

    public TestServiceImpl(GenreRepository genreRepository, ContentRepository contentRepository) {
        this.genreRepository = genreRepository;
        this.contentRepository = contentRepository;
    }

    @Override
    public Set<Content> getContentsByGenreId(Long genreId) {//метод возвращает набор контентов определенного жанра
        var genre = genreRepository.findById(genreId).orElse(null);
        if (genre == null) {
            return Set.of();
        }

        return genre.getContents();
    }

    @Override
    public Set<Genre> getGenresByContentId(Long contentId, String name) {//метод возвращает набор жанров определенного контента
        if (!name.equalsIgnoreCase("Admin")) {
            return new HashSet<>();
        }
        var content = contentRepository.findById(contentId).orElse(null);
        if (content == null) {
            return Set.of();
        }

        return content.getGenres();
    }
}
