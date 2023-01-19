package ru.lesson.jpademo.builder;

import ru.lesson.jpademo.domain.Comment;
import ru.lesson.jpademo.domain.Content;
import ru.lesson.jpademo.domain.User;
import ru.lesson.jpademo.dto.response.CommentDto;

import java.util.Date;

import static ru.lesson.jpademo.builder.UserBuilder.toUserShortDto;

// билдер для того что связано с данной сущностью
// преобразование из CreateCommentDto(request) -> Comment
// преобразование из Comment -> CommentDto(response)

public class CommentBuilder {

    /** request */
    public static Comment toComment(String messageText, User author, Content content) {
        var comment = new Comment();
        //задаем новому объекту(комментарию) значение автора комментария
        comment.setAuthor(author);
        // new date = текущая дата на локальной машине
        comment.setDateOfCreation(new Date());
        //задаем новому объекту значение контента
        comment.setContent(content);
        //задаем новому объекту значение текста
        comment.setMessageText(messageText);
        return comment;
    }

    /** response */
    public static CommentDto toCommentDto(Comment comment) {
        var commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setContentId(comment.getContent().getId());
        commentDto.setAuthor(toUserShortDto(comment.getAuthor()));
        commentDto.setDateOfCreation(comment.getDateOfCreation());
        commentDto.setMessageText(comment.getMessageText());
        return commentDto;
    }
}
