package ru.lesson.jpademo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "genre")
public class Genre {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Column(name = "name",nullable = false, length = 60)
    private String name;

    @Column(name = "description", length = 255)
    private String description;

    @ManyToMany// тип связи между таблицей контент и табл жанр(связь по уникальным ключам id(primary key)
    @JoinTable(
            name = "content_genre",// название связующий таблицы с таблицей жанров(мы здесь хотим хранить связь)
            joinColumns = @JoinColumn(name = "genre_id"), // id текущей таблицы
            inverseJoinColumns = @JoinColumn(name = "content_id")) // id таблицы с которой хотим связать
    @JsonIgnore
    Set<Content> contents;

    public Set<Content> getContents() {
        return contents;
    }

    public void setContents(Set<Content> contents) {
        this.contents = contents;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
