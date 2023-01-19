package ru.lesson.jpademo.controller;

import org.springframework.web.bind.annotation.*;
import ru.lesson.jpademo.dto.request.CreateContentDto;
import ru.lesson.jpademo.dto.response.ContentDto;
import ru.lesson.jpademo.dto.response.UserDto;
import ru.lesson.jpademo.service.ContentService;

import java.util.List;

@RestController
@RequestMapping("content")
public class ContentController {
    private final ContentService contentService;

    public ContentController(ContentService contentService) {
        this.contentService = contentService;
    }

    @PostMapping
    public ContentDto create(@RequestBody CreateContentDto createContentDto) {
        return contentService.create(createContentDto);
    }

    @GetMapping("by-id")
    public ContentDto getById(Long id) {
        return contentService.getById(id);
    }

    @DeleteMapping("by-id")
    public void deleteById(Long id) {
        contentService.deleteById(id);
    }

    @PatchMapping("by-id")
    public ContentDto updateById(Long id, @RequestBody CreateContentDto createContentDto) {
        return contentService.updateById(id, createContentDto);
    }



    @GetMapping("count-content")//1
    public long countContent(){
        return contentService.countContent();
    }


    @GetMapping("by-author-id")//2
    public List<ContentDto> byAuthorId(@RequestParam Long authorId, int pageNumber, int pageSize){
        return contentService.getContentsByAuthorId(authorId, pageNumber, pageSize);
    }


    @GetMapping("all-woman-one-content")//7
    public List<UserDto> getUsersMensWhoHave1Content(int pageNumber, int pageSize){
        return contentService.getUsersWomanHasContent(pageNumber, pageSize);
    }
}

