package dev.antonio3a.worldapi.infra.util;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

@Component
public class MyJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

    private static final String PRINCIPAL_CLAIM = "preferred_username";

    @Override
    public AbstractAuthenticationToken convert(@NonNull Jwt jwt) {
        Collection<GrantedAuthority> authorities = Stream.concat(
                jwtGrantedAuthoritiesConverter.convert(jwt).stream(),
                extractResourceRoles(jwt).stream()
        ).toList();

        return new JwtAuthenticationToken(jwt, authorities, getPrincipalNAme(jwt));
    }

    private Collection<? extends GrantedAuthority> extractResourceRoles(Jwt jwt) {
        Map<String, Object> realmAccessMap = jwt.getClaimAsMap("realm_access");
        Map<String, Object> resourceAccessMap = jwt.getClaimAsMap("resource_access");

        AtomicReference<Map<String, Collection<String>>> resource = new AtomicReference<>();
        Collection<String> roles = new ArrayList<>();

        realmAccessMap.forEach(
                (key, value) -> roles.addAll((Collection<String>) value)
        );
        resourceAccessMap.forEach(
                (key, value) -> {
                    resource.set((Map<String, Collection<String>>) value);
                    resource.get().forEach(
                            (k, v) -> roles.addAll(v)
                    );
                }
        );

        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.toUpperCase()))
                .toList();
    }

    private String getPrincipalNAme(Jwt jwt) {
        if(!jwt.hasClaim(PRINCIPAL_CLAIM)) {
            return jwt.getSubject();
        }
        return jwt.getClaim(PRINCIPAL_CLAIM);
    }
}
