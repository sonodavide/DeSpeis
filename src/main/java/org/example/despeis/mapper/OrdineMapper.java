package org.example.despeis.mapper;

import org.example.despeis.dto.OrdineDto;
import org.example.despeis.model.Ordine;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrdineMapper {
    Ordine toEntity(OrdineDto ordineDto);

    @AfterMapping
    default void linkBigliettos(@MappingTarget Ordine ordine) {
        ordine.getBigliettos().forEach(biglietto -> biglietto.setOrdine(ordine));
    }

    @Mapping(target = "bigliettos", source = "bigliettos") // Mappatura esplicita
    OrdineDto toDto(Ordine ordine);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Ordine partialUpdate(OrdineDto ordineDto, @MappingTarget Ordine ordine);
}