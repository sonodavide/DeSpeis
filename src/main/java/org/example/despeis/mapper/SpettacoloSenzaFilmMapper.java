package org.example.despeis.mapper;

import org.example.despeis.dto.SpettacoloSenzaFilmDto;
import org.example.despeis.model.Spettacolo;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SpettacoloSenzaFilmMapper {
    Spettacolo toEntity(SpettacoloSenzaFilmDto spettacoloSenzaFilmDto);

    SpettacoloSenzaFilmDto toDto(Spettacolo spettacolo);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Spettacolo partialUpdate(SpettacoloSenzaFilmDto spettacoloSenzaFilmDto, @MappingTarget Spettacolo spettacolo);
}