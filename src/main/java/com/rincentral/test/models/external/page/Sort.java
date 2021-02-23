package com.rincentral.test.models.external.page;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Sort {
    @JsonProperty("sorted")
    private Boolean sorted;

    @JsonProperty("unsorted")
    private Boolean unsorted;

    @JsonProperty("empty")
    private Boolean empty;
}
