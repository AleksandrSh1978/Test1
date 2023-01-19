package ru.lesson.jpademo.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.lesson.jpademo.builder.ContentBuilder;
import ru.lesson.jpademo.builder.GenreBuilder;
import ru.lesson.jpademo.domain.Comment;
import ru.lesson.jpademo.domain.Content;
import ru.lesson.jpademo.domain.Genre;
import ru.lesson.jpademo.domain.User;
import ru.lesson.jpademo.dto.request.CreateContentDto;
import ru.lesson.jpademo.dto.response.CommentDto;
import ru.lesson.jpademo.dto.response.GenreDto;
import ru.lesson.jpademo.dto.response.UserDto;
import ru.lesson.jpademo.repository.CommentRepository;
import ru.lesson.jpademo.repository.ContentRepository;
import ru.lesson.jpademo.repository.GenreRepository;
import ru.lesson.jpademo.repository.UserRepository;
import ru.lesson.jpademo.dto.response.ContentDto;
import ru.lesson.jpademo.service.ContentService;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;
import static ru.lesson.jpademo.builder.CommentBuilder.toCommentDto;
import static ru.lesson.jpademo.builder.ContentBuilder.toContentDto;
import static ru.lesson.jpademo.builder.UserBuilder.toUserDto;

@Service
public class ContentServiceImpl implements ContentService {
    private final ContentRepository contentRepository;
    private final UserRepository userRepository;
    private final GenreRepository genreRepository;
    private final CommentRepository commentRepository;

    public ContentServiceImpl(ContentRepository contentRepository, UserRepository userRepository, GenreRepository genreRepository, CommentRepository commentRepository) {
        this.contentRepository = contentRepository;
        this.userRepository = userRepository;
        this.genreRepository = genreRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public ContentDto create(CreateContentDto createContentDto) {//метод создает контент
        // так как контент не может быть создан без связи с user(кто создает контент) и без связи с жанром,
        // нам надо проверить существует ли запись с такими id из createContentDto

        // находим автора контента по id и передаем в создание контента
        var authorOptional = userRepository.findById(createContentDto.getAuthorId());

        //найти по ids жанры и передать в создание контента
        // проверяем жанр по аналогии
        Set<Genre> genres = new HashSet<>();//создаем пустой набор жанров
        if (createContentDto.getGenresIds() != null && !createContentDto.getGenresIds().isEmpty()) {// если набор ключей из
            //модели запроса не = null(существует) и если набор пустой, то
            genres = genreRepository.findByIdIn(createContentDto.getGenresIds());//набор ключей жанров принимает значение
            //набора жанров под этими ключами из репозитория
        }

        // так как jpa repository в методе findById возвращает optional<T> надо проверить есть ли
        // внутри найденный author и список жанров
//        if (authorOptional.isEmpty() || genres.isEmpty()) {
//            // если пусто, то отбрасываем exception 404
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
//        }

        // проверяем валидность введенных имени, пути к файлу и описания, если размер больше допустимого то ошибка
        if (createContentDto.getName() != null && createContentDto.getName().length() > 255) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        //todo  nullpoinex
        if (createContentDto.getFilePatch().length() > 255) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        //todo  nullpoinex
        if (createContentDto.getDescription().length() > 1000) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        // убедились что все связь может быть осуществлена,
        // поэтому обращаемся к билдеру для преобразования из тела запроса(CreateContentDto) к модели Content
        // для сохранения последней в базу
        Content content = ContentBuilder.toContent(createContentDto.getName(), createContentDto.getFilePatch(),
                createContentDto.getDescription(), authorOptional.get(), genres);
        Content contentDb = contentRepository.save(content);

        return toContentDto(contentDb);
    }


    @Override
    public ContentDto getById(Long id) {

        Optional<Content> contentOptional = contentRepository.findById(id);
        if (contentOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Set<Genre> genres = contentOptional.get().getGenres();//

        Set<GenreDto> genresDtos = genres.stream()  //создаем стрим из листа жанров, применяем к каждому
                // элементу метод(функцию), {который передаем в map}
                .map(genre -> {
                    long countContents = contentRepository.countContentByGenreId(genre.getId());
                    return GenreBuilder.toGenreDto(genre, countContents);
                })
                .collect(Collectors.toSet());//приводим к виду сета



//        List<GenreDto> result = new ArrayList<>(genres.size());
//        for (var genre : genres) {
//            long countContents = contentRepository.countContentByGenreId(genre.getId());
//            var dto = GenreBuilder.toGenreDto(genre, countContents);
//            result.add(dto);
//        }

        if (contentOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cоntent не найден");
        }
        long countComments = commentRepository.countByContentId(contentOptional.get().getId());//
        CommentDto lastComment = toCommentDto(commentRepository.findLastComment(contentOptional.get().getId()));
        return toContentDto(contentOptional.get(), countComments, lastComment, genresDtos);
    }


    @Override
    public ContentDto updateById(Long id, CreateContentDto createContentDto) {
        Optional<Content> optionalContent = contentRepository.findById(id);
        if (optionalContent.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Content content = optionalContent.get();
        if (createContentDto.getName() != null) {
            content.setName(createContentDto.getName());
        }
        if (createContentDto.getFilePatch() != null) {
            content.setFilePath(createContentDto.getFilePatch());
        }
        if (createContentDto.getDescription() != null) {
            content.setDescription(createContentDto.getDescription());
        }
        if (createContentDto.getDescription() != null) {
            content.setDescription(createContentDto.getDescription());
        }

        if (createContentDto.getAuthorId() != null) {
            Optional<User> optionalUser = userRepository.findById(createContentDto.getAuthorId());
            if (optionalUser.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
            content.setAuthor(optionalUser.get());
        }

        if (createContentDto.getGenresIds() != null) {
            Set<Genre> genres = genreRepository.findByIdIn(createContentDto.getGenresIds());
            content.setGenres(genres);
        }
        return toContentDto(contentRepository.save(content));
    }

    @Override
    public void deleteById(Long id) {
        Optional<Content> contentOptional = contentRepository.findById(id);
        if (contentOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Комментарий не найден");
        }
        contentRepository.delete(contentOptional.get());

    }


    @Override
    public long countContent() {
        return contentRepository.count();
    }//1метод возвращает количество контента


    @Override
    public List<ContentDto> getContentsByAuthorId(Long authorId, int pageNumber, int pageSize) {//2метод возвращает лист контента автора с определенным Id
        Optional<User> userOptional = userRepository.findById(authorId);
        if (userOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if (pageSize < 0 || pageNumber < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Pageable pageable = PageRequest.of(pageNumber,pageSize);//создаем новый несортированный список
        List<Content> contents = contentRepository.getContentsByAuthorId(authorId, pageable);
        return contents.stream().map(ContentBuilder::toContentDto).toList();
    }

//    @Override
//    public List<UserDto> getUsersWomanHasContent() {//7 метод находит всех пользователей женского пола,
//        // у кого есть хотя бы 1 контент
//        return userRepository.UsersWomanHasContent().stream()
//                .map(x -> toUserDto(x))
//                .collect(Collectors.toList());
//
//    }

    //вариант без стрима
    @Override
    public List<UserDto> getUsersWomanHasContent(int pageNumber, int pageSize) {//7 метод находит всех пользователей
        // женского пола, у кого есть хотя бы 1 контент
        if (pageSize < 0 || pageNumber < 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Pageable pageable = PageRequest.of(pageNumber,pageSize);
        Page<User> users = userRepository.UsersWomanHasContent(pageable);
        List<UserDto> userDtos = new ArrayList<>();
        for (User user:
                users) {
            UserDto userDto = toUserDto(user);
            userDtos.add(userDto);
        }

        return userDtos;
    }




}
