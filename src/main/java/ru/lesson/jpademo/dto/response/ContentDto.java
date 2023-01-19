package ru.lesson.jpademo.dto.response;

import java.util.Date;
import java.util.Set;

public class ContentDto {
    long id;
    String name;
    String filePatch;
    String description;
    Date dateOfCreation;
    UserShortDto author;
    Set<GenreDto> genreDtos;
    long countComments;
    CommentDto lastComment;

    public long getCountComments() {
        return countComments;
    }

    public void setCountComments(long countComments) {
        this.countComments = countComments;
    }

    public CommentDto getLastComment() {
        return lastComment;
    }

    public void setLastComment(CommentDto lastComment) {
        this.lastComment = lastComment;
    }

    public Set<GenreDto> getGenreDtos() {
        return genreDtos;
    }

    public void setGenreDtos(Set<GenreDto> genreDtos) {
        this.genreDtos = genreDtos;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFilePatch() {
        return filePatch;
    }

    public void setFilePatch(String filePatch) {
        this.filePatch = filePatch;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public UserShortDto getAuthor() {
        return author;
    }

    public void setAuthor(UserShortDto author) {
        this.author = author;
    }
}
