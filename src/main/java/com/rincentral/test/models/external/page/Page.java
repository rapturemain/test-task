package com.rincentral.test.models.external.page;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rincentral.test.models.external.ExternalCar;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Page {
    @JsonProperty("content")
    private ExternalCar[] cars;

    @JsonProperty("pageable")
    private Pageable pageable;

    @JsonProperty("totalPages")
    private Integer totalPages;

    @JsonProperty("totalElements")
    private Integer totalElements;

    @JsonProperty("last")
    private Boolean last;

    @JsonProperty("number")
    private Integer number;

    @JsonProperty("sort")
    private Sort sort;

    @JsonProperty("size")
    private Integer size;

    @JsonProperty("numberOfElements")
    private Integer numberOfElements;

    @JsonProperty("first")
    private Boolean first;

    @JsonProperty("empty")
    private Boolean empty;


}
