package org.example.despeis.mapper;

import org.example.despeis.dto.GenereDto;
import org.example.despeis.model.Genere;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface GenereMapper {
    Genere toEntity(GenereDto genereDto);

    GenereDto toDto(Genere genere);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Genere partialUpdate(GenereDto genereDto, @MappingTarget Genere genere);
}