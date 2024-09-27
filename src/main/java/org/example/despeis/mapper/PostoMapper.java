package org.example.despeis.mapper;

import org.example.despeis.dto.PostoDto;
import org.example.despeis.model.Posto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PostoMapper {
    Posto toEntity(PostoDto postoDto);

    PostoDto toDto(Posto posto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Posto partialUpdate(PostoDto postoDto, @MappingTarget Posto posto);
}