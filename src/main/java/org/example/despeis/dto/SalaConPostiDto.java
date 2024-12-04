package org.example.despeis.dto;

import lombok.Value;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * DTO for {@link org.example.despeis.model.Sala}
 */
@Value
public class SalaConPostiDto implements Serializable {
    Integer id;
    List<PostiDto> postis;
}