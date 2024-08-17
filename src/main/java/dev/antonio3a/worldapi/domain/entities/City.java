package dev.antonio3a.worldapi.domain.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "city", schema = "world")
public class City extends RepresentationModel<City> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Size(max = 35)
    @NotNull
    @ColumnDefault("''")
    @Column(name = "Name", nullable = false, length = 35, columnDefinition = "char(35)")
    private String name;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @ColumnDefault("''")
    @JsonBackReference
    @JoinColumn(name = "CountryCode", nullable = false, columnDefinition = "char(3)")
    private Country country;

    @Size(max = 20)
    @NotNull
    @ColumnDefault("''")
    @Column(name = "District", nullable = false, length = 20, columnDefinition = "char(20)")
    private String district;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "Population", nullable = false)
    private Integer population;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        City city = (City) o;
        return getId() != null && Objects.equals(getId(), city.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}