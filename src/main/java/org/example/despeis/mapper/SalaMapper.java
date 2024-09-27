package org.example.despeis.mapper;

import org.example.despeis.dto.SalaDto;
import org.example.despeis.model.Sala;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SalaMapper {
    Sala toEntity(SalaDto salaDto);

    @AfterMapping
    default void linkPostos(@MappingTarget Sala sala) {
        sala.getPostos().forEach(posto -> posto.setSala(sala));
    }

    SalaDto toDto(Sala sala);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Sala partialUpdate(SalaDto salaDto, @MappingTarget Sala sala);
}