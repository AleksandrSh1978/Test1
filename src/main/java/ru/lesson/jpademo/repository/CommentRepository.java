package ru.lesson.jpademo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.lesson.jpademo.domain.Comment;
import ru.lesson.jpademo.dto.response.CommentDto;

import java.util.List;

//Repository наследуется от JpaRepository - это интерфейс для взаимодействия с базой данных
// содержит в себе и реализует основные базовые методы (добавления, получения, обновления, удаления и тд),
// а так же другие необходимые методы операций над сущностями базы данных
//взаимодействует в основном с сервисом
public interface CommentRepository extends JpaRepository<Comment, Long> {

    //"select * from comment where message_text = :text"
    @Query(value = "select * from comments where message_text like :text%",//4 показать сообщения по вводимым буквам
            nativeQuery = true)
    Page<Comment> getCommentsByText(@Param("text") String text, Pageable pageable);


    //1й вариант
//    @Query(value = "SELECT count(*) > 0 FROM comments c " +//5 значение = выбрать количество >0 из
//            "WHERE c.content.id = :contentId")
//    Boolean contentHaveСomments(@Param("contentId") Long contentId);

    //2й вариант
    Boolean existsByContentId(Long contentId);//5

    @Query(value = "SELECT COUNT(*) FROM comments c " +
            "WHERE c.content_id = :contentId", nativeQuery = true)
    long countByContentId(@Param("contentId") Long contentId);
    //метод получения количества комментариев из таблицы комментариев определенного контента

    @Query(value = "SELECT * FROM comments c WHERE c.content_id = :contentId ORDER BY id DESC LIMIT 1 ", nativeQuery = true)
    Comment findLastComment(@Param("contentId") Long contentId);
    //метод получения последнего комментария к контенту из таблицы комментариев
}