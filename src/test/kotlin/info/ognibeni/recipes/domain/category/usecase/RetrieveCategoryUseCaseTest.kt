package info.ognibeni.recipes.domain.category.usecase

import info.ognibeni.recipes.domain.category.Fixtures.EXAMPLE_CATEGORY_UUID
import info.ognibeni.recipes.domain.category.Fixtures.exampleCategory
import info.ognibeni.recipes.domain.category.exception.CategoryNotFoundException
import info.ognibeni.recipes.domain.category.persistence.CategoryRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.dao.EmptyResultDataAccessException

class RetrieveCategoryUseCaseTest {

	private val categoryRepository: CategoryRepository = mockk()

	private val sut = RetrieveCategoryUseCase(categoryRepository)

	@Test
	fun `retrieving all categories succeeds`() {
		val expectedCategories = listOf(
				exampleCategory(),
				exampleCategory(title = "another title", description = null))

		every { categoryRepository.findAll() } returns expectedCategories

		val categories = sut.getAllCategories()

		verify(exactly = 1) { categoryRepository.findAll() }
		assertThat(categories)
				.hasSize(expectedCategories.size)
				.containsAll(expectedCategories)
	}

	@Test
	fun `retrieving an empty score list succeeds`() {
		every { categoryRepository.findAll() } returns emptyList()

		val categories = sut.getAllCategories()

		verify(exactly = 1) { categoryRepository.findAll() }
		assertThat(categories)
				.isEmpty()
	}

	@Test
	fun `retrieving an existing category succeeds`() {
		val expectedCategory = exampleCategory()
		every { categoryRepository.getReferenceById(any()) } returns expectedCategory

		val category = sut.getCategory(EXAMPLE_CATEGORY_UUID)

		verify(exactly = 1) { categoryRepository.getReferenceById(EXAMPLE_CATEGORY_UUID.value) }
		assertThat(category)
			.isEqualTo(expectedCategory)
	}

	@Test
	fun `retrieving a non-existing category fails`() {
		every { categoryRepository.getReferenceById(any()) } throws EmptyResultDataAccessException(1)

		assertThrows<CategoryNotFoundException> {
			sut.getCategory(EXAMPLE_CATEGORY_UUID)
		}

		verify(exactly = 1) { categoryRepository.getReferenceById(EXAMPLE_CATEGORY_UUID.value) }
	}
}
