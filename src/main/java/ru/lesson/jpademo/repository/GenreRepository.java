package ru.lesson.jpademo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lesson.jpademo.domain.Genre;

import java.util.Set;

public interface GenreRepository extends JpaRepository<Genre, Long> {

    Set<Genre> findByIdIn(Set<Long> ids);//метод, который принимает набор ключей жанра, и возвращает набор жанров
}
