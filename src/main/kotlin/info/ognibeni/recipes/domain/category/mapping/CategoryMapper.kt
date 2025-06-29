package info.ognibeni.recipes.domain.category.mapping

import info.ognibeni.recipes.domain.category.api.ApiCategory
import info.ognibeni.recipes.domain.category.api.ApiCategoryCreate
import info.ognibeni.recipes.domain.category.domain.Category
import info.ognibeni.recipes.domain.category.domain.CategoryDescription
import info.ognibeni.recipes.domain.category.domain.CategoryTitle
import info.ognibeni.recipes.domain.category.domain.CategoryUuid

fun ApiCategoryCreate.toCategory(): Category =
	Category(
		CategoryTitle(title),
		description?.let { CategoryDescription(it) }
	)

fun ApiCategory.toCategory(): Category =
	Category(
		CategoryTitle(title),
		description?.let { CategoryDescription(it) },
		CategoryUuid(uuid)
	)

fun Category.toApiCategory(): ApiCategory =
	ApiCategory(
		id,
		title.value,
		description?.value
	)
