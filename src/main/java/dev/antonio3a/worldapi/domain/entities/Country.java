package dev.antonio3a.worldapi.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.hateoas.RepresentationModel;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "country", schema = "world")
public class Country extends RepresentationModel<Country> {

    @Id
    @Size(max = 3)
    @ColumnDefault("''")
    @Column(name = "Code", nullable = false, length = 3, columnDefinition = "char(3)")
    private String code;

    @Size(max = 52)
    @NotNull
    @ColumnDefault("''")
    @Column(name = "Name", nullable = false, length = 52, columnDefinition = "char(52)")
    private String name;

    @NotNull
    @ColumnDefault("'Asia'")
    @Column(name = "Continent", nullable = false, columnDefinition = "char(50)")
    private String continent;

    @Size(max = 26)
    @NotNull
    @ColumnDefault("''")
    @Column(name = "Region", nullable = false, length = 26, columnDefinition = "char(26)")
    private String region;

    @NotNull
    @ColumnDefault("0.00")
    @Column(name = "SurfaceArea", nullable = false, columnDefinition = "decimal(10,2)")
    private Double surfaceArea;

    @Column(name = "IndepYear")
    private Short independenceYear;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "Population", nullable = false)
    private Integer population;

    @Column(name = "LifeExpectancy", columnDefinition = "decimal(3,1)")
    private Double lifeExpectancy;

    @Column(name = "GNP", columnDefinition = "decimal(10,2)")
    private Double gnp;

    @Column(name = "GNPOld", columnDefinition = "decimal(10,2)")
    private Double gnpOld;

    @Size(max = 45)
    @NotNull
    @ColumnDefault("''")
    @Column(name = "LocalName", nullable = false, length = 45, columnDefinition = "char(45)")
    private String localName;

    @Size(max = 45)
    @NotNull
    @ColumnDefault("''")
    @Column(name = "GovernmentForm", nullable = false, length = 45, columnDefinition = "char(45)")
    private String governmentForm;

    @Size(max = 60)
    @Column(name = "HeadOfState", length = 60, columnDefinition = "char(60)")
    private String headOfState;

    @Column(name = "Capital")
    private Integer capital;

    @Size(max = 2)
    @NotNull
    @ColumnDefault("''")
    @Column(name = "Code2", nullable = false, length = 2, columnDefinition = "char(2)")
    private String code2;

    @OneToMany(mappedBy = "country", fetch = FetchType.LAZY)
    private Set<City> cities = new LinkedHashSet<>();

    @OneToMany(mappedBy = "country", fetch = FetchType.LAZY)
    private Set<CountryLanguage> languages = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Country country = (Country) o;
        return getCode() != null && Objects.equals(getCode(), country.getCode());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}