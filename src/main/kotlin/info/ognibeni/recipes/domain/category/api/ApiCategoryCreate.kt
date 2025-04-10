package info.ognibeni.recipes.domain.category.api

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "CategoryCreate", description = "Details about a new category to save")
data class ApiCategoryCreate(

	@Schema(required = false, description = "The title of the category", example = "Cakes")
	val title: String,

	@Schema(required = true, description = "An optional description of the category", example = "From stirred cakes all the way to tortes")
	val description: String?
)
