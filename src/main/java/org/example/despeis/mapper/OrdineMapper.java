package org.example.despeis.mapper;

import org.example.despeis.dto.OrdineDto;
import org.example.despeis.model.Ordine;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = BigliettoMapper.class)
public interface OrdineMapper {

    @Mapping(source = "bigliettos", target = "bigliettos")
    OrdineDto toDto(Ordine ordine);

    @AfterMapping
    default void linkBigliettos(@MappingTarget Ordine ordine) {
        if (ordine.getBigliettos() != null) {
            ordine.getBigliettos().forEach(biglietto -> biglietto.setOrdine(ordine));
        }
    }

    Ordine toEntity(OrdineDto ordineDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Ordine partialUpdate(OrdineDto ordineDto, @MappingTarget Ordine ordine);
}