package org.example.despeis.mapper;

import org.example.despeis.dto.SpettacoloDto;
import org.example.despeis.model.Spettacolo;
import org.mapstruct.*;
import org.springframework.context.annotation.Bean;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SpettacoloMapper {
    Spettacolo toEntity(SpettacoloDto spettacoloDto);

    SpettacoloDto toDto(Spettacolo spettacolo);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Spettacolo partialUpdate(SpettacoloDto spettacoloDto, @MappingTarget Spettacolo spettacolo);
}