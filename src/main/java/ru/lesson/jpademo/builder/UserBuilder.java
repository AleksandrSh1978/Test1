package ru.lesson.jpademo.builder;

import ru.lesson.jpademo.domain.SexType;
import ru.lesson.jpademo.domain.User;
import ru.lesson.jpademo.dto.response.UserDto;
import ru.lesson.jpademo.dto.response.UserShortDto;

import java.util.Date;

public class UserBuilder {//класс преобразования Usera

    public static UserShortDto toUserShortDto(User user) {//метод преобразования Usera в в моленькую модель userShortDto
        var userShortDto = new UserShortDto();//создаем экзкмпляр класса UserShortDto(маленькой модели usera)
        userShortDto.setId(user.getId());//задаем значение Id usera этому экземпляру
        userShortDto.setName(user.getName());//задаем значение name этому экзкмпляру
        return userShortDto;// возвращаем созданный экземпляр
    }

    public static User toUser(String name, String phoneNumber, String email, String nikName,
                              SexType sexType, Date dateOfBirth) {
        var user = new User();
        //задаем новому объекту(юзеру) значения соотв полей
        user.setName(name);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setNikName(nikName);
        user.setSexType(sexType);
        user.setDateOfBirth(dateOfBirth);
        return user;
    }


    /**
     * response
     */
    public static UserDto toUserDto(User user) {//метод преобразования user userDto(принимает user, возвращает userDto)
        var userDto = new UserDto();
        //задаем новому объекту userDto значения соотв полей user
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setNikName(user.getNikName());
        userDto.setSexType(user.getSexType());
        userDto.setDateOfBirth(user.getDateOfBirth());
        return userDto;
    }

}
