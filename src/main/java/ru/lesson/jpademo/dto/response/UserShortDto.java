package ru.lesson.jpademo.dto.response;

//для создания контента используем маленькую модель пользователя так как остальные поля избыточны
public class UserShortDto {
    Long id;
    String name;

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
}
