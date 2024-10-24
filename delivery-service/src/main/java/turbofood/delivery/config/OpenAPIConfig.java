package turbofood.delivery.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(
    info = @Info(
        title = "Delivery API",
        version = "1.0"
    )
)
@SecurityScheme(
    type = SecuritySchemeType.OPENIDCONNECT,
    openIdConnectUrl = "${spring.security.oauth2.resourceserver.jwt.issuer-uri}/.well-known/openid-configuration",
    name = "openid-connect"
)
@Configuration
public class OpenAPIConfig {
}
