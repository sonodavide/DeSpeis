package org.example.despeis.mapper;

import org.example.despeis.dto.FilmDto;
import org.example.despeis.model.Film;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)public interface FilmMapper {
    @Mapping(target = "titolo", expression = "java(filmDto.getTitolo().trim())")
    @Mapping(target = "trama", expression = "java(filmDto.getTrama().trim())")
    Film toEntity(FilmDto filmDto);

    FilmDto toDto(Film film);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)Film partialUpdate(FilmDto filmDto, @MappingTarget Film film);
}