package ru.lesson.jpademo.service.impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.lesson.jpademo.builder.CommentBuilder;
import ru.lesson.jpademo.domain.Comment;
import ru.lesson.jpademo.dto.request.CreateCommentDto;
import ru.lesson.jpademo.dto.response.CommentDto;
import ru.lesson.jpademo.repository.CommentRepository;
import ru.lesson.jpademo.repository.ContentRepository;
import ru.lesson.jpademo.repository.UserRepository;
import ru.lesson.jpademo.service.CommentService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.lesson.jpademo.builder.CommentBuilder.toCommentDto;

//класс Service содержит реализацию методов из интерфейса Service, бизнес-логику работы над сущностями и их взаимодействием
//класс Service взаимодействует в основном с репозиторием

@Service
public class CommentServiceImpl implements CommentService {

    private final ContentRepository contentRepository;

    private final UserRepository userRepository;

    private final CommentRepository commentRepository;



    @Override
    public CommentDto create(CreateCommentDto createCommentDto) {//метод создает комментарий
        // так как комментарий не может быть создан без связи с user(кто создает коммент) и content, нам надо проверить
        // существует ли запись с такими id из createCommentDto

        // находим автора комментария по id
        var authorOptional = userRepository.findById(createCommentDto.getAuthorId());

        // проверяем контент по аналогии
        var contentOptional = contentRepository.findById(createCommentDto.getContentId());
        // так как jpa repository в методе findById возвращает optional<T> надо проверить есть ли
        // внутри найденный author и content
        if (authorOptional.isEmpty() || contentOptional.isEmpty()) {
            // если пусто, то отбрасываем exception 404
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        // проверяем валидность введенного текста, если размер больше допустимого то ошибка
        if (createCommentDto.getMessageText().length() > 199) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        // убедились что все связь может быть осуществлена,
        // поэтому обращаемся к билдеру для преобразования из тела запроса(CreateCommentDto) к модели Comment
        // для сохранения последней в базу
        Comment comment = CommentBuilder.toComment(createCommentDto.getMessageText(), authorOptional.get(), contentOptional.get());
        Comment commentDb = commentRepository.save(comment);
        return toCommentDto(commentDb);
    }

    @Override
    public CommentDto getById(Long id) {
        Optional<Comment> commentOptional = commentRepository.findById(id);
        if (commentOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Комментарий не найден");
        }
        return toCommentDto(commentOptional.get());
    }

    @Override
    public CommentDto updateById(Long id, CreateCommentDto createCommentDto) {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        if (optionalComment.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Comment comment = optionalComment.get();

        if (createCommentDto.getMessageText() != null) {
            comment.setMessageText(createCommentDto.getMessageText());
        }
        return toCommentDto(commentRepository.save(comment));
    }

    @Override
    public void deleteById(Long id) {
        Optional<Comment> commentOptional = commentRepository.findById(id);
        if (commentOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Комментарий не найден");
        }
        commentRepository.delete(commentOptional.get());
    }


    @Override
    public List<CommentDto> getCommentsBySearchText(String text,int pageNumber, int pageSize) {//4 метод поиска сообщений
        //возвращает лист комментариев по первым вводимым буквам постранично
        if (pageSize < 0 || pageNumber < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Pageable pageable = PageRequest.of(pageNumber, pageSize);//создаем новый несортированный PageRequest
        return commentRepository.getCommentsByText(text, pageable).stream()
                .map(x -> toCommentDto(x))
                .collect(Collectors.toList());
    }

    @Override
    public Boolean doesTheContentHaveСomments(Long contentId) {//5
        var commentsOptional = commentRepository.existsByContentId(contentId);
        if (commentsOptional) {
            return true;
        }
        return false;
    }

}
