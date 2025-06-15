package org.example.despeis.mapper;

import org.example.despeis.dto.BigliettoDto;
import org.example.despeis.model.Biglietto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface BigliettoMapper {
    Biglietto toEntity(BigliettoDto bigliettoDto);

    BigliettoDto toDto(Biglietto biglietto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Biglietto partialUpdate(BigliettoDto bigliettoDto, @MappingTarget Biglietto biglietto);
}