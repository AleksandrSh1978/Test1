package ru.lesson.jpademo.controller;

import org.springframework.web.bind.annotation.*;
import ru.lesson.jpademo.dto.request.CreateCommentDto;
import ru.lesson.jpademo.dto.response.CommentDto;
import ru.lesson.jpademo.service.CommentService;

import java.util.List;

//Класс Controller - это то, с чем взаимодействует пользователь. К нему идут запросы по протоколу HTTP(request)
//содержит методы по получению, сериализации, валидации и маршрутизации запроса
//содержит часть методов из сервиса, которые принимают тело запроса в виде созданного объекта модели DTO(для запроса на
//создание, или изменение), либо ID(для методов удаления и получения),
// а возвращает реализацию соотв методов из сервиса, то есть commentDto
@RequestMapping("comment")
@RestController
public class CommentController {
    private  final CommentService commentService;//объявляем соотв экземпляр класса сервис

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }//конструктор

    @PostMapping
    public CommentDto create(@RequestBody CreateCommentDto createCommentDto){//метод создает комментарий(принимает
        // тело запроса в виде модели для запроса)
        return commentService.create(createCommentDto);//возвращает реализацию метода create в сервисе
    }

    @GetMapping("by-id")
    public CommentDto getById(Long id){
        return commentService.getById(id);
    }

    @DeleteMapping("by-id")
    public void deleteById(Long id){
        commentService.deleteById(id);
    }

    @PatchMapping("by-id")
    public CommentDto updateById(Long id, @RequestBody CreateCommentDto createCommentDto){
        return commentService.updateById(id, createCommentDto);
    }




    @GetMapping("comment-by-text")//4
    public List <CommentDto> getCommentsByText(String text, int pageNumber, int pageSize){
        return commentService.getCommentsBySearchText(text, pageNumber, pageSize);
    }


    @GetMapping()//5
    public boolean TheContentHaveСomments(@RequestParam Long contentId){
        return commentService.doesTheContentHaveСomments(contentId);
    }
}
