package es.upm.miw.devops.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import org.junit.jupiter.api.Test;
import org.springdoc.core.customizers.OpenApiCustomizer;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OpenApiConfigTest {

    private final OpenApiConfig openApiConfig = new OpenApiConfig();

    @Test
    void testPathOrderCustomizerReordersKnownPathsAndKeepsExtraPathsAtTheEnd() {
        Paths paths = new Paths();
        paths.addPathItem("/version-badge", new PathItem());
        paths.addPathItem("/user/{id}/active", new PathItem());
        paths.addPathItem("/user/{id}", new PathItem());
        paths.addPathItem("/user", new PathItem());
        OpenAPI openApi = new OpenAPI();
        openApi.setPaths(paths);

        OpenApiCustomizer customizer = this.openApiConfig.pathOrderCustomizer();
        customizer.customise(openApi);

        List<String> orderedKeys = new ArrayList<>(openApi.getPaths().keySet());
        assertThat(orderedKeys).containsExactly("/user", "/user/{id}", "/user/{id}/active", "/version-badge");
    }

    @Test
    void testPathOrderCustomizerDoesNothingWhenPathsIsNull() {
        OpenAPI openApi = new OpenAPI();

        OpenApiCustomizer customizer = this.openApiConfig.pathOrderCustomizer();
        customizer.customise(openApi);

        assertThat(openApi.getPaths()).isNull();
    }

    @Test
    void testPathOrderCustomizerIgnoresMissingKnownPaths() {
        Paths paths = new Paths();
        paths.addPathItem("/user", new PathItem());
        paths.addPathItem("/other", new PathItem());
        OpenAPI openApi = new OpenAPI();
        openApi.setPaths(paths);

        OpenApiCustomizer customizer = this.openApiConfig.pathOrderCustomizer();
        customizer.customise(openApi);

        List<String> orderedKeys = new ArrayList<>(openApi.getPaths().keySet());
        assertThat(orderedKeys).containsExactly("/user", "/other");
    }
}
