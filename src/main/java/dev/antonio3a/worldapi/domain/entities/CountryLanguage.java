package dev.antonio3a.worldapi.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "countrylanguage", schema = "world")
public class CountryLanguage extends RepresentationModel<CountryLanguage> {

    @EmbeddedId
    private CountryLanguageId id;

    @MapsId("countryCode")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @ColumnDefault("''")
    @JoinColumn(name = "CountryCode", nullable = false, columnDefinition = "char(3)")
    @JsonIgnore
    private Country country;

    @NotNull
    @ColumnDefault("'F'")
    @Column(name = "IsOfficial", nullable = false, columnDefinition = "char(3)")
    private Boolean isOfficial;

    @NotNull
    @ColumnDefault("0.0")
    @Column(name = "Percentage", nullable = false, columnDefinition = "decimal(4,1)")
    private Double percentage;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CountryLanguage countryLanguage = (CountryLanguage) o;
        return getId() != null && Objects.equals(getId(), countryLanguage.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}