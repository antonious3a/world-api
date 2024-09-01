package dev.antonio3a.worldapi.infra.util;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.jboss.logging.Logger;
import org.keycloak.adapters.authorization.integration.jakarta.ServletPolicyEnforcerFilter;
import org.keycloak.adapters.authorization.spi.ConfigurationResolver;

import java.io.IOException;
import java.util.Set;

public class MyServletPolicyEnforcerFilter extends ServletPolicyEnforcerFilter {

    private final String publicPaths;

    private final Logger logger = Logger.getLogger(getClass());

    public MyServletPolicyEnforcerFilter(ConfigurationResolver configResolver, String publicPaths) {
        super(configResolver);
        this.publicPaths = publicPaths;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        Set<String> publicPathsSet = Set.of(publicPaths.split(","));
        for (String publicPath : publicPathsSet) {
            if (request.getRequestURI().startsWith(publicPath)) {
                logger.infof("Public path [%s], request authorized, continuing the filter chain", request.getRequestURI());
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
        }
        super.doFilter(servletRequest, servletResponse, filterChain);
    }
}
