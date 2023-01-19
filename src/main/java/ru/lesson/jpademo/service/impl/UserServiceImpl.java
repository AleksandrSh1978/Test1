package ru.lesson.jpademo.service.impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.lesson.jpademo.builder.UserBuilder;
import ru.lesson.jpademo.domain.SexType;
import ru.lesson.jpademo.domain.User;
import ru.lesson.jpademo.dto.request.CreateUserDto;
import ru.lesson.jpademo.dto.response.UserDto;
import ru.lesson.jpademo.repository.CommentRepository;
import ru.lesson.jpademo.repository.ContentRepository;
import ru.lesson.jpademo.repository.UserRepository;
import ru.lesson.jpademo.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.lesson.jpademo.builder.UserBuilder.toUserDto;

@Service
public class UserServiceImpl implements UserService {

    private final ContentRepository contentRepository;

    private final UserRepository userRepository;

    private final CommentRepository commentRepository;

    public UserServiceImpl(ContentRepository contentRepository,
                           UserRepository userRepository,
                           CommentRepository commentRepository) {
        this.contentRepository = contentRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public UserDto create(CreateUserDto createUserDto) {//метод создает юзера

        // проверяем валидность введенного текста, если  null то ошибка
        if (createUserDto.getEmail() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (createUserDto.getPhoneNumber() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        // убедились что все связь может быть осуществлена,
        // поэтому обращаемся к билдеру для преобразования из тела запроса(CreateCommentDto) к модели Comment
        // для сохранения последней в базу
        User user = UserBuilder.toUser(createUserDto.getName(), createUserDto.getEmail(), createUserDto.getPhoneNumber(),
                createUserDto.getNikName(), createUserDto.getSexType(), createUserDto.getDateOfBirth());
        User userEntity = userRepository.save(user);
        return toUserDto(userEntity);
    }

    @Override
    public UserDto getById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден");
        }
        return toUserDto(userOptional.get());
    }

    @Override
    public UserDto updateById(Long id, CreateUserDto createUserDto) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        User user = optionalUser.get();
        if (createUserDto.getName() != null) {
            user.setName(createUserDto.getName());
        }
        if (createUserDto.getPhoneNumber() != null) {
            user.setPhoneNumber(createUserDto.getPhoneNumber());
        }
        if (createUserDto.getEmail() != null) {
            user.setEmail(createUserDto.getEmail());
        }
        if (createUserDto.getNikName() != null) {
            user.setNikName(createUserDto.getNikName());
        }
        if (createUserDto.getSexType() != null) {
            user.setSexType(createUserDto.getSexType());
        }
        if (createUserDto.getDateOfBirth() != null) {
            user.setDateOfBirth(createUserDto.getDateOfBirth());
        }
        return toUserDto(userRepository.save(user));
    }

    @Override
    public void deleteById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден");
        }
        userRepository.delete(userOptional.get());
    }


    @Override
    public List<UserDto> getAuthorsAgeOver18(int pageNumber, int pageSize) {//5 метод возвращает лист юзеров, которым исполнилось 18лет
        if (pageSize < 0 || pageNumber < 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return userRepository.findByAge(pageable).stream()
                .map(x -> toUserDto(x))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> getUsersByIdsAndSexType(Long[] authorsIds, SexType sexType, int pageNumber, int pageSize) {//6 метод возвращает
        // лист пользователей по введенному листу айдишников
        if (pageSize < 0 || pageNumber < 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return userRepository.getUsersByIdsSexType(authorsIds, sexType, pageable).stream()
                .map(x -> toUserDto(x))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> UsersHasContentHasCommentAndGenreDescriptionNoNull(int pageNumber, int pageSize) {
        if (pageSize < 0 || pageNumber < 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return userRepository.UsersHasContentHasCommentAndGenreDescriptionNoNull(pageable).stream()
                .map(x -> toUserDto(x))
                .collect(Collectors.toList());
    }

}