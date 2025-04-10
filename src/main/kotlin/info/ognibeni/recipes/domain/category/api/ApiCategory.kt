package info.ognibeni.recipes.domain.category.api

import io.swagger.v3.oas.annotations.media.Schema
import java.util.UUID

@Schema(name = "Category", description = "Details about a specific category")
data class ApiCategory(

	@Schema(required = false, description = "Unique identifier of the category", example = "00000000-aaaa-4bbb-8ccc-111111111111")
	val uuid: UUID,

	@Schema(required = false, description = "The title of the category", example = "Cakes")
	val title: String,

	@Schema(required = true, description = "An optional description of the category", example = "From stirred cakes all the way to tortes")
	val description: String?
)
