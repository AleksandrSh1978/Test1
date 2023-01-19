package ru.lesson.jpademo.dto.response;

public class GenreDto {
    long id;
    String name;
    String description;
    long countContents;

    public long getCountContents() {
        return countContents;
    }

    public void setCountContents(long countContents) {
        this.countContents = countContents;
    }

    public String getDescription() {
        return description;
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

    public String getDescription(String description) {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
