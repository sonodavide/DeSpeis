package org.example.despeis.mapper;

import org.example.despeis.dto.AttoreDto;
import org.example.despeis.model.Attore;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AttoreMapper {
    Attore toEntity(AttoreDto attoreDto);

    AttoreDto toDto(Attore attore);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Attore partialUpdate(AttoreDto attoreDto, @MappingTarget Attore attore);
}