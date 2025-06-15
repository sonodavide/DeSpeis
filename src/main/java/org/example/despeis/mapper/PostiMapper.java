package org.example.despeis.mapper;

import org.example.despeis.dto.PostiDto;
import org.example.despeis.model.Posti;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PostiMapper {
    Posti toEntity(PostiDto postiDto);

    PostiDto toDto(Posti posti);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Posti partialUpdate(PostiDto postiDto, @MappingTarget Posti posti);
}