package info.ognibeni.recipes.configuration

import info.ognibeni.recipes.persistence.RecipesRepository
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

/**
 * Enable JPA auditing and overwrite repository base class
 * See also [org.springframework.data.jpa.domain.support.AuditingEntityListener]
 */
@Configuration
@EnableJpaAuditing
@EnableJpaRepositories("info.ognibeni.recipes", repositoryBaseClass = RecipesRepository::class)
class JpaConfig
