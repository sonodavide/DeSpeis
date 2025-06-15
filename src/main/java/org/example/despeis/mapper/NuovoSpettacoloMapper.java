package org.example.despeis.mapper;

import org.example.despeis.dto.NuovoSpettacoloDto;
import org.example.despeis.model.Spettacolo;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface NuovoSpettacoloMapper {
    Spettacolo toEntity(NuovoSpettacoloDto nuovoSpettacoloDto);

    NuovoSpettacoloDto toDto(Spettacolo spettacolo);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Spettacolo partialUpdate(NuovoSpettacoloDto nuovoSpettacoloDto, @MappingTarget Spettacolo spettacolo);
}