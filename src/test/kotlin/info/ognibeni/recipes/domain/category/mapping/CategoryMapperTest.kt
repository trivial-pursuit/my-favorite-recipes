package info.ognibeni.recipes.domain.category.mapping

import info.ognibeni.recipes.domain.category.api.ApiCategory
import info.ognibeni.recipes.domain.category.api.ApiCategoryCreate
import info.ognibeni.recipes.domain.category.domain.Category
import info.ognibeni.recipes.domain.category.domain.CategoryDescription
import info.ognibeni.recipes.domain.category.domain.CategoryTitle
import info.ognibeni.recipes.persistence.BaseEntity.Companion.NEW_ENTITY_ID
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CategoryMapperTest {

	@Test
	fun `maps category correctly`() {
		val category =
			Category(CategoryTitle("the title"), CategoryDescription("the description"))

		val expectedApiCategory =
			ApiCategory(NEW_ENTITY_ID, "the title", "the description")

		assertThat(category.toApiCategory())
			.isEqualTo(expectedApiCategory)
	}

	@Test
	fun `maps API category correctly`() {
		val apiCategoryCreate =
			ApiCategoryCreate("the title", "the description")

		val expectedCategory =
			Category(CategoryTitle("the title"), CategoryDescription("the description"))

		assertThat(apiCategoryCreate.toCategory())
			.isEqualTo(expectedCategory)
	}
}
