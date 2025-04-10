package info.ognibeni.recipes.configuration

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "springdoc.swagger-ui.oauth")
data class OpenApiProperties(
	val tokenUrl: String
)
