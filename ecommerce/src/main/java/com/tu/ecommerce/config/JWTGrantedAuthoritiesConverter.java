package com.tu.ecommerce.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class JWTGrantedAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    private final String AUTHORITY_PREFIX = "SCOPE_";

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Collection<String> tokenScopes = this.parseScopesClaim(jwt);
        if (tokenScopes.isEmpty()) {
            return Collections.emptyList();
        }

        return tokenScopes.stream()
                .map(s -> AUTHORITY_PREFIX + s)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }

    private Collection<String> parseScopesClaim(Jwt jwt) {
        List<String> groups = jwt.getClaimAsStringList("groups");
        return groups;
    }
}
