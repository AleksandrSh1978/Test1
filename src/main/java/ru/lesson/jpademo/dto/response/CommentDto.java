package ru.lesson.jpademo.dto.response;

import java.util.Date;
//DTO-это паттерн, позволяющий переносить объекты и данные из одного слоя(части) программы в другой
//DTO -это пакет, отвечающий за хранение запроса(request) и ответа(response)
//это те модели, по которым общаются клиент и сервер
//в данном случае содержит поля модели для ответа

public class CommentDto {
    long id;
    UserShortDto author;
    Long contentId;
    Date dateOfCreation;
    String messageText;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserShortDto getAuthor() {
        return author;
    }

    public void setAuthor(UserShortDto author) {
        this.author = author;
    }

    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }
}
