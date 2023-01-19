package ru.lesson.jpademo.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.lesson.jpademo.domain.Content;
import ru.lesson.jpademo.domain.Genre;
import ru.lesson.jpademo.domain.User;

import java.util.List;
import java.util.Set;

public interface ContentRepository extends JpaRepository<Content, Long> {

    Set<Genre> findByIdIn(Set<Long> ids);

    @Query(value = "Select * from content " +//2
            "where author_id = :authorId",
            nativeQuery = true)
    List<Content> getContentsByAuthorId(@Param("authorId") Long authorId, Pageable pageable);

    @Query(value = "select count(*) from content_genre c " +
            "where c.genre_id = :genreId", nativeQuery = true)
    long countContentByGenreId(@Param("genreId") Long genreId);
    //метод получения количества контента из таблицы контента определенного жанра

}
