package com.rincentral.test.models.external.page;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Pageable {
    @JsonProperty("sort")
    private Sort sort;

    @JsonProperty("offset")
    private Integer offset;

    @JsonProperty("pageNumber")
    private Integer pageNumber;

    @JsonProperty("pageSize")
    private Integer pageSize;

    @JsonProperty("paged")
    private Boolean paged;

    @JsonProperty("unpaged")
    private Boolean unpaged;
}
