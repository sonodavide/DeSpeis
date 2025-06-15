package org.example.despeis.mapper;

import org.example.despeis.dto.SalaConPostiDto;
import org.example.despeis.model.Sala;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)public interface SalaConPostiMapper {
    Sala toEntity(SalaConPostiDto salaConPostiDto);

    @AfterMapping
    default void linkPostis(@MappingTarget Sala sala) {
        sala.getPostis().forEach(posti -> posti.setSala(sala));
    }

    SalaConPostiDto toDto(Sala sala);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)Sala partialUpdate(SalaConPostiDto salaConPostiDto, @MappingTarget Sala sala);
}