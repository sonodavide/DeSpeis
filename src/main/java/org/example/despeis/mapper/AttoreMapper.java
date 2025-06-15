package org.example.despeis.mapper;

import org.example.despeis.dto.AttoreDto;
import org.example.despeis.model.Attore;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AttoreMapper {
    @Mapping(target = "nome", expression = "java(attoreDto.getNome().trim())")
    @Mapping(target = "cognome", expression = "java(attoreDto.getCognome().trim())")
    Attore toEntity(AttoreDto attoreDto);

    AttoreDto toDto(Attore attore);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Attore partialUpdate(AttoreDto attoreDto, @MappingTarget Attore attore);
}