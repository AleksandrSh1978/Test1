package ru.lesson.jpademo.builder;

import ru.lesson.jpademo.domain.Genre;
import ru.lesson.jpademo.dto.response.GenreDto;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class GenreBuilder {
    //*request*/
    public static Genre toGenre(String name, String description) {
        var genre = new Genre();
        genre.setName(name);
        genre.setDescription(description);
        return genre;
    }

    //**response*/
    public static GenreDto  toGenreDto(Genre genre, long countContents){
        var genreDto = new GenreDto();
        genreDto.setId(genre.getId());
        genreDto.setName(genre.getName());
        genreDto.getDescription(genre.getDescription());
        genreDto.setCountContents(countContents);
        return genreDto;
    }

    //**response*/
    public static GenreDto toGenreDto(Genre genre){
        var genreDto = new GenreDto();
        genreDto.setId(genre.getId());
        genreDto.setName(genre.getName());
        genreDto.getDescription(genre.getDescription());
        genreDto.setCountContents(0);
        return genreDto;
    }

    public static Set<GenreDto> toGenresDtos(Collection<Genre> genres){

        // method with stream
        //Set<GenreDto> genreDtos1 = genres.stream().map(GenreBuilder::toGenreDto).collect(Collectors.toSet());

        Set<GenreDto> genreDtos = new HashSet<>();
        for (Genre genre: genres) {
            genreDtos.add(toGenreDto(genre));
        }
        return genreDtos;
    }
}
