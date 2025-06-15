package org.example.despeis.mapper;

import org.example.despeis.dto.PostispettacoloDto;
import org.example.despeis.model.Postispettacolo;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PostispettacoloMapper {
    Postispettacolo toEntity(PostispettacoloDto postispettacoloDto);

    PostispettacoloDto toDto(Postispettacolo postispettacolo);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Postispettacolo partialUpdate(PostispettacoloDto postispettacoloDto, @MappingTarget Postispettacolo postispettacolo);
}