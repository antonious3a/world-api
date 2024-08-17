package dev.antonio3a.worldapi.api.payloads;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

/**
 * DTO for {@link dev.antonio3a.worldapi.domain.entities.Country}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CountryDto extends RepresentationModel<CountryDto> implements Serializable {

    @Size(max = 3)
    private String code;
    @NotNull
    @Size(max = 52)
    private String name;
    @NotNull
    private String continent;
    @NotNull
    @Size(max = 26)
    private String region;
    @NotNull
    private Double surfaceArea;
    private Short independenceYear;
    @NotNull
    private Integer population;
    private Double lifeExpectancy;
    private Double gnp;
    private Double gnpOld;
    @NotNull
    @Size(max = 45)
    private String localName;
    @NotNull
    @Size(max = 45)
    private String governmentForm;
    @Size(max = 60)
    private String headOfState;
    private Integer capital;
    @NotNull
    @Size(max = 2)
    private String code2;
}