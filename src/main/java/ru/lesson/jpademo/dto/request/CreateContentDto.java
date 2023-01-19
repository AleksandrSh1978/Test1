package ru.lesson.jpademo.dto.request;

import java.util.Set;

public class CreateContentDto {
    String name;
    String filePatch;//путь к файлу
    String description;

    Long authorId;
    Set<Long> genresIds;//поле набора ключей жанров

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

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Set<Long> getGenresIds() {
        return genresIds;
    }

    public void setGenresIds(Set<Long> genresIds) {
        this.genresIds = genresIds;
    }


}
