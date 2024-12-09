package org.example.despeis.mapper;

import org.example.despeis.dto.RegistaDto;
import org.example.despeis.model.Regista;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface RegistaMapper {
    @Mapping(target = "nome", expression = "java(registaDto.getNome().trim())")
    @Mapping(target = "cognome", expression = "java(registaDto.getCognome().trim())")
    Regista toEntity(RegistaDto registaDto);

    RegistaDto toDto(Regista regista);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Regista partialUpdate(RegistaDto registaDto, @MappingTarget Regista regista);
}