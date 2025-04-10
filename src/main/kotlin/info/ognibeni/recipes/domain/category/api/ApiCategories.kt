package info.ognibeni.recipes.domain.category.api

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "Categories", description = "A collection of categories")
data class ApiCategories(

	@Schema(required = true, description = "A collection of categories")
	val categories: List<ApiCategory>
)
