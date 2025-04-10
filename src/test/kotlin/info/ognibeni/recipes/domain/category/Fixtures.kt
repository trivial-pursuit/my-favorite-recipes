package info.ognibeni.recipes.domain.category

import info.ognibeni.recipes.domain.category.api.ApiCategoryCreate
import info.ognibeni.recipes.domain.category.domain.Category
import info.ognibeni.recipes.domain.category.domain.CategoryDescription
import info.ognibeni.recipes.domain.category.domain.CategoryTitle
import info.ognibeni.recipes.domain.category.domain.toCategoryUuid
import java.util.UUID

object Fixtures {
	val EXAMPLE_CATEGORY_UUID = UUID.fromString("00000000-0000-0000-0000-000000000001").toCategoryUuid()

	fun exampleCategory(
		title: String = "example title",
		description: String? = "example description"
	): Category =
		Category(
			title = CategoryTitle(title),
			description = description?.let { CategoryDescription(it) }
		)

	fun exampleApiCategoryCreate(
		title: String = "example title",
		description: String? = "example description"
	): ApiCategoryCreate =
		ApiCategoryCreate(
			title = title,
			description = description
		)
}
