package org.example.despeis.mapper;

import org.example.despeis.dto.SpettacoloSenzaFilmTagsDto;
import org.example.despeis.model.Spettacolo;
import org.mapstruct.*;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SpettacoloSenzaFilmTagsMapper {
    Spettacolo toEntity(SpettacoloSenzaFilmTagsDto spettacoloSenzaFilmTagsDto);
    @Mapping(source = "film.id", target = "filmId")
    @Mapping(source = "film.titolo", target = "filmTitolo")
    @Mapping(source = "sala.id", target = "salaId")
    SpettacoloSenzaFilmTagsDto toDto(Spettacolo spettacolo);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)Spettacolo partialUpdate(SpettacoloSenzaFilmTagsDto spettacoloSenzaFilmTagsDto, @MappingTarget Spettacolo spettacolo);
}