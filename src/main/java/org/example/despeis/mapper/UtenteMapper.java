package org.example.despeis.mapper;

import org.example.despeis.dto.UtenteDto;
import org.example.despeis.model.Utente;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UtenteMapper {
    Utente toEntity(UtenteDto utenteDto);

    UtenteDto toDto(Utente utente);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Utente partialUpdate(UtenteDto utenteDto, @MappingTarget Utente utente);
}