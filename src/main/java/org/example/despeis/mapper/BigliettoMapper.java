package org.example.despeis.mapper;

import org.example.despeis.dto.BigliettoDto;
import org.example.despeis.model.Biglietto;
import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface BigliettoMapper {

    @Mapping(source = "utente.id", target = "utenteId")
    @Mapping(source = "ordine.id", target = "ordineId")
    @Mapping(source = "postospettacolo.sedile", target = "postospettacoloSedile")
    @Mapping(source = "postospettacolo.fila", target = "postospettacoloFila")
    @Mapping(source = "postospettacolo.spettacolo.data", target = "postospettacoloSpettacoloData")
    @Mapping(source = "postospettacolo.spettacolo.ora", target = "postospettacoloSpettacoloOra")
    @Mapping(source = "postospettacolo.spettacolo.sala.id", target = "postospettacoloSpettacoloSalaId")
    @Mapping(source = "postospettacolo.spettacolo.film.titolo", target = "postospettacoloSpettacoloFilmTitolo")
    BigliettoDto toDto(Biglietto biglietto);

    @InheritInverseConfiguration
    Biglietto toEntity(BigliettoDto bigliettoDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(BigliettoDto bigliettoDto, @MappingTarget Biglietto biglietto);
}
