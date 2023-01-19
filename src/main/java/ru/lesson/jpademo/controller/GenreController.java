package ru.lesson.jpademo.controller;

import org.springframework.web.bind.annotation.*;
import ru.lesson.jpademo.domain.Genre;
import ru.lesson.jpademo.dto.request.CreateGenreDto;
import ru.lesson.jpademo.dto.response.GenreDto;
import ru.lesson.jpademo.service.GenreService;

import java.util.List;

@RequestMapping("genre")//аннотация для вывода "
@RestController
public class GenreController {
    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @PostMapping
    public GenreDto create(@RequestBody CreateGenreDto createGenreDto){
        return genreService.create(createGenreDto);
    }

    @GetMapping("by-id")
    public GenreDto getById(Long id){
        return genreService.getById(id);
    }

    @DeleteMapping("by-id")
    public void deleteById(Long id){
        genreService.deleteById(id);
    }

    @PatchMapping("by-id")
    public GenreDto updateById(Long id, @RequestBody CreateGenreDto createGenreDto){
        return genreService.updateById(id, createGenreDto);
    }



    @GetMapping
    public List<GenreDto> getList(){
        return genreService.getList();
    }

}
