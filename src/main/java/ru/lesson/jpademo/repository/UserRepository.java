package ru.lesson.jpademo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.lesson.jpademo.domain.SexType;
import ru.lesson.jpademo.domain.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = """
            select * from users where ((current_date - date_of_birth) >= '18 year')
            """, nativeQuery = true)//5
    Page<User> findByAge(Pageable pageable);


    @Query(value = "select u from User u where u.id in :authorsIds and u.sexType = :sexType")//6) :присваивает значение параметра
    Page<User> getUsersByIdsSexType(@Param("authorsIds") Long[] authorsIds, @Param("sexType") SexType sexType, Pageable pageable);

    @Query(value = """
            select u.* from users u where sex_type = 'FEMALE' and exists(select * from content c where c.author_id = u.id)
            """, nativeQuery = true)//7метод находит всех пользователей женского пола, у кого есть хотя бы 1 контент
    Page<User> UsersWomanHasContent(Pageable pageable);

    //2й вариант
    //select users.* from content inner join users on content.author_id = users.id where sex_type = 'FEMALE'


    @Query(value = """
            SELECT DISTINCT users.* FROM users\s
            JOIN content ON users.id = content.author_id
            JOIN comments ON content.id = comments.content_id
            JOIN content_genre ON content.id = content_genre.content_id
            JOIN genre ON genre.id = content_genre.genre_id
            WHERE (users.id IN (SELECT author_id FROM content GROUP BY author_id HAVING count(*)>=3))\s
            AND (genre.description is not null)
            AND (exists(select * from comments where comments.content_id = content.id))
            AND (exists(select * from genre, content_genre where content_genre.content_id = content.id))
            """, nativeQuery = true) //метод находит всех(лист) пользователей у кого есть более трех контентов,
        // у которых есть более одного комментария и более одного жанра(у которого поле description != null)
    Page<User> UsersHasContentHasCommentAndGenreDescriptionNoNull(Pageable pageable);
}


//    @Query(value = "select * from users where (id = any(array [3,1])) and  (sex_type = 'FEMALE'))",//6
//            nativeQuery = true)
//    List<User>getUsersByIdsSexType(@Param("authorsIds")Long[]authorsIds, @Param("sex_type")SexType sexType);
