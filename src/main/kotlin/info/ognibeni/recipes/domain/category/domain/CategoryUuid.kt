package info.ognibeni.recipes.domain.category.domain

import java.util.UUID

@JvmInline
value class CategoryUuid(val value: UUID)

fun UUID.toCategoryUuid(): CategoryUuid =
	CategoryUuid(this)
