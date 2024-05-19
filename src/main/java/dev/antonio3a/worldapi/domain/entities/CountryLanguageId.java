package dev.antonio3a.worldapi.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class CountryLanguageId implements Serializable {

    @Serial
    private static final long serialVersionUID = -4696267870152059322L;

    @Size(max = 3)
    @NotNull
    @ColumnDefault("''")
    @Column(name = "CountryCode", nullable = false, length = 3, columnDefinition = "char(3)")
    private String countryCode;

    @Size(max = 30)
    @NotNull
    @ColumnDefault("''")
    @Column(name = "Language", nullable = false, length = 30, columnDefinition = "char(30)")
    private String language;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CountryLanguageId entity = (CountryLanguageId) o;
        return Objects.equals(this.countryCode, entity.countryCode) &&
                Objects.equals(this.language, entity.language);
    }

    @Override
    public int hashCode() {
        return Objects.hash(countryCode, language);
    }
}