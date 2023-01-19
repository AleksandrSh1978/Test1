package ru.lesson.jpademo.domain;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "content")
public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "file_path", nullable = false)
    private String filePath;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "date_of_creation")
    private Date dateOfCreation;

    @ManyToOne// тип связи между таблицей контент и табл пользователь(связь по уникальным ключам id(primary key)
    @JoinColumn(name = "author_id")// название ключа из табл юзер(мы здесь хотим хранить связь)
    private User author;

    @ManyToMany// тип связи между таблицей контент и табл жанр(связь по уникальным ключам id(primary key)
    @JoinTable(
            name = "content_genre",// название связующей таблицы с таблицей жанров(мы здесь хотим хранить связь)
            joinColumns = @JoinColumn(name = "content_id"), // id текущей таблицы
            inverseJoinColumns = @JoinColumn(name = "genre_id")) // id таблицы с которой хотим связать
    @JsonIgnore
    Set<Genre> genres;

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
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

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
