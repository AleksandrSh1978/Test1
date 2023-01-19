package ru.lesson.jpademo.domain;

import javax.persistence.*;
import java.util.Date;
// Описываем здесь Сущность(Entity) коммента, а именно-поля, имеющиеся взаимосвязи между ними, а так же методы get и set
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue
    @Column(name ="id")
    private long id;

    @Column(name = "message_text", length = 199)
    private String messageText;

    @ManyToOne// тип связи между таблицей коммент и табл пользователь(связь по уникальным ключам id(primary key)
    @JoinColumn(name = "author_id")// название ключа из табл юзер(мы здесь хотим хранить связь)
    private User author;

    @ManyToOne// тип связи между таблицей коммент и табл контент(связь по уникальным ключам id(primary key)
    @JoinColumn(name = "content_id")// название ключа из табл контент(мы здесь хотим хранить связь)
    private Content content;

    @Column(name = "date_of_creation")
    private Date dateOfCreation;

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }
}
