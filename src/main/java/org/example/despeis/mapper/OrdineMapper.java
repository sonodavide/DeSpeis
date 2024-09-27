package org.example.despeis.mapper;

import org.example.despeis.dto.OrdineDto;
import org.example.despeis.model.Ordine;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrdineMapper {
    Ordine toEntity(OrdineDto ordineDto);

    OrdineDto toDto(Ordine ordine);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Ordine partialUpdate(OrdineDto ordineDto, @MappingTarget Ordine ordine);
}