package dev.antonio3a.worldapi.api.payloads;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dev.antonio3a.worldapi.domain.entities.City;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

/**
 * DTO for {@link City}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CityDto extends RepresentationModel<CityDto> implements Serializable {

    private Integer id;
    @NotNull
    @Size(max = 35)
    private String name;
    @NotNull
    @Size(max = 20)
    private String district;
    @NotNull
    private Integer population;
    @NotNull
    private String countryCode;
}