package dev.antonio3a.worldapi.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "country", schema = "world")
public class Country {

    @Id
    @Size(max = 3)
    @ColumnDefault("''")
    @Column(name = "Code", nullable = false, length = 3)
    private String code;

    @Size(max = 52)
    @NotNull
    @ColumnDefault("''")
    @Column(name = "Name", nullable = false, length = 52)
    private String name;

    @NotNull
    @ColumnDefault("'Asia'")
    @Lob
    @Column(name = "Continent", nullable = false)
    private String continent;

    @Size(max = 26)
    @NotNull
    @ColumnDefault("''")
    @Column(name = "Region", nullable = false, length = 26)
    private String region;

    @NotNull
    @ColumnDefault("0.00")
    @Column(name = "SurfaceArea", nullable = false)
    private Double surfaceArea;

    @Column(name = "IndepYear")
    private Short independenceYear;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "Population", nullable = false)
    private Integer population;

    @Column(name = "LifeExpectancy")
    private Double lifeExpectancy;

    @Column(name = "GNP")
    private Double gnp;

    @Column(name = "GNPOld")
    private Double gnpOld;

    @Size(max = 45)
    @NotNull
    @ColumnDefault("''")
    @Column(name = "LocalName", nullable = false, length = 45)
    private String localName;

    @Size(max = 45)
    @NotNull
    @ColumnDefault("''")
    @Column(name = "GovernmentForm", nullable = false, length = 45)
    private String governmentForm;

    @Size(max = 60)
    @Column(name = "HeadOfState", length = 60)
    private String headOfState;

    @Column(name = "Capital")
    private Integer capital;

    @Size(max = 2)
    @NotNull
    @ColumnDefault("''")
    @Column(name = "Code2", nullable = false, length = 2)
    private String code2;

    @OneToMany(mappedBy = "country")
    private Set<City> cities = new LinkedHashSet<>();

    @OneToMany(mappedBy = "country")
    private Set<CountryLanguage> languages = new LinkedHashSet<>();
}