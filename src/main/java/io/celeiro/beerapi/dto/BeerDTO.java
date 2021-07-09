package io.celeiro.beerapi.dto;

import io.celeiro.beerapi.enums.BeerType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BeerDTO {

    private Long id;

    @NotEmpty
    @Size(min = 2, max = 100)
    private String name;

    @NotEmpty
    private String brand;

    @NotEmpty
    private int max;

    @NotEmpty
    private int quantity;

    @Valid
    @NotEmpty
    private BeerType type;
}
