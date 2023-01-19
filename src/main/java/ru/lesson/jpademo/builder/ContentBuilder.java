package ru.lesson.jpademo.builder;

// билдер для того что связано с комментариями
// преобразование из CreateContentDto(request) -> Content
// преобразование из Content -> ContentDto(response)


import org.jetbrains.annotations.NotNull;
import ru.lesson.jpademo.domain.Content;
import ru.lesson.jpademo.domain.Genre;
import ru.lesson.jpademo.domain.User;
import ru.lesson.jpademo.dto.response.CommentDto;
import ru.lesson.jpademo.dto.response.ContentDto;
import ru.lesson.jpademo.dto.response.GenreDto;
import ru.lesson.jpademo.service.CommentService;

import java.util.Date;
import java.util.Set;

import static ru.lesson.jpademo.builder.GenreBuilder.toGenreDto;
import static ru.lesson.jpademo.builder.GenreBuilder.toGenresDtos;
import static ru.lesson.jpademo.builder.UserBuilder.toUserShortDto;

public class ContentBuilder {
    /** request */
    public static Content toContent(String name, String filePatch, String discription, User author, Set<Genre> genres) {
        var content = new Content();//создаем экземпляр класса Сontent
        //задаем новому объекту(контенту) значение автора контента
        content.setAuthor(author);
        // задаем новому объекту название
        content.setName(name);
        //задаем новому объекту путь к файлу
        content.setFilePath(filePatch);
        //задаем новому объекту описание
        content.setDescription(discription);
        //задаем новому объекту дату создания
        content.setDateOfCreation(new Date());
        //задаем новому объекту жанр
        content.setGenres(genres);

        return content;
    }

    /** response*/
    public static @NotNull ContentDto toContentDto(Content content, long countComments, CommentDto lastComment, Set<GenreDto> genresDtos) {
        var contentDto = new ContentDto();
        contentDto.setId(content.getId());//передаем в contentDto Id из сущности content.getId())
        contentDto.setName(content.getName());
        contentDto.setFilePatch(content.getFilePath());
        contentDto.setDescription(content.getDescription());
        contentDto.setDateOfCreation(content.getDateOfCreation());
        contentDto.setAuthor(toUserShortDto(content.getAuthor()));
        contentDto.setGenreDtos(genresDtos);//передаем в contentDto набор жанров(GenresDtos) в виде
        contentDto.setCountComments(countComments);
        contentDto.setLastComment(lastComment);
        return contentDto;
    }

    public static ContentDto toContentDto(Content content) {
        var contentDto = new ContentDto();
        contentDto.setId(content.getId());//передаем в contentDto Id из сущности content.getId())
        contentDto.setName(content.getName());
        contentDto.setFilePatch(content.getFilePath());
        contentDto.setDescription(content.getDescription());
        contentDto.setDateOfCreation(content.getDateOfCreation());
        contentDto.setAuthor(toUserShortDto(content.getAuthor()));
        contentDto.setGenreDtos(toGenresDtos(content.getGenres()));
        return contentDto;
    }

}


