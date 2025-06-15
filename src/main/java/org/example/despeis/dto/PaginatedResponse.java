package org.example.despeis.dto;

import lombok.Value;

import java.io.Serializable;
import java.util.List;

@Value
public class PaginatedResponse<T> implements Serializable {
    List<T> content;
    private Integer totalPages;
    private Long totalElements;

}
