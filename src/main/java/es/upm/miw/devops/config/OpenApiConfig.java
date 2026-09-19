package es.upm.miw.devops.config;

import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Springdoc groups Swagger UI operations by path (all methods of the same path stay together),
 * regardless of the declaration order in the controller. This customizer reorders the generated
 * paths so that Swagger UI shows: /user, /user/{id}, /user/{id}/active, followed by any other path.
 */
@Configuration
public class OpenApiConfig {

    private static final List<String> PATH_ORDER = List.of("/user", "/user/{id}", "/user/{id}/active");

    @Bean
    public OpenApiCustomizer pathOrderCustomizer() {
        return openApi -> {
            Paths originalPaths = openApi.getPaths();
            if (originalPaths == null) {
                return;
            }
            Paths orderedPaths = new Paths();
            for (String path : PATH_ORDER) {
                PathItem pathItem = originalPaths.remove(path);
                if (pathItem != null) {
                    orderedPaths.addPathItem(path, pathItem);
                }
            }
            originalPaths.forEach(orderedPaths::addPathItem);
            openApi.setPaths(orderedPaths);
        };
    }
}
