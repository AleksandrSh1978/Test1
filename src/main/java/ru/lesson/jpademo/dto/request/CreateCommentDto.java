package ru.lesson.jpademo.dto.request;

//DTO-это паттерн, позволяющий переносить объекты и данные из одного слоя(части) программы в другой
//DTO -это пакет, отвечающий за хранение запроса(request) и ответа(response)
//это те модели, по которым общаются клиент и сервер
//в данном случае содержит поля модели для запроса

public class CreateCommentDto {

    Long authorId;
    Long contentId;
    String messageText;

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

}
