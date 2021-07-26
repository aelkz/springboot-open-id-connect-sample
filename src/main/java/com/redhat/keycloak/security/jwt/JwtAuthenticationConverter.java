package com.redhat.keycloak.security.jwt;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final JwtGrantedAuthoritiesConverter defaultGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
    private final String clientId;

    public JwtAuthenticationConverter(String clientId) {
        this.clientId = clientId;
    }

    private static Collection<? extends GrantedAuthority> extractResourceRoles(final Jwt jwt, final String clientId) {
        Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
        Map<String, Object> resource;
        Collection<String> resourceRoles;

        if (resourceAccess != null
                && (resource = (Map<String, Object>) resourceAccess.get(clientId)) != null
                && (resourceRoles = (Collection<String>) resource.get("roles")) != null) {
            return resourceRoles.stream()
                .map(x -> new SimpleGrantedAuthority("ROLE_" + x))
                .collect(Collectors.toSet());
        }

        return Collections.emptySet();
    }

    @Override
    public AbstractAuthenticationToken convert(@Nonnull Jwt jwt) {

        jwt.getClaims().forEach((k,v) -> {
            System.out.println("["+k+"]=" + v);
        });

        jwt.getAudience().forEach(System.out::println);

        Collection<GrantedAuthority> authorities
            = Stream.concat(defaultGrantedAuthoritiesConverter.convert(jwt).stream(),
                extractResourceRoles(jwt, this.clientId).stream())
                .collect(Collectors.toSet());

        return new JwtAuthenticationToken(jwt, authorities);
    }


}
